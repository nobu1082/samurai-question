package com.example.samuraitravel.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.service.ReviewService;

@Controller
public class ReviewController {
   
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	@Autowired
	public ReviewController(ReviewRepository reviewRepository , ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	
	@GetMapping("/reviews")
	public String index(Model model) {
		List<Review> listReviews = reviewService.getAllReviews();
		
		model.addAttribute("listreviews" , listReviews);
		
		return "reviews/index";  /*  requestMapping似合わせる。ここの情報がページとして表示される*/
	}
	
	@GetMapping("/reviewsforall")
	public String index2(Model model) {
		List<Review> listReviews = reviewService.getAllReviews();
		
		model.addAttribute("listreviews" , listReviews);
		
		return "reviews/indexforall";  /*  requestMapping似合わせる。ここの情報がページとして表示される*/
	}
	
	@GetMapping("/reviews/showNewReviewForm")
	public String showNewRevieForm(Model model) {
		Review review = new Review();
		model.addAttribute("Review" , review);
		return "reviews/create";
	}
	

	@PostMapping("/saveReview")
	public String saveReview(@ModelAttribute("review") Review review) {
		reviewService.saveReview(review);		
		return "redirect:/";
	}
	
    @PostMapping("/showFormForUpdata_{id}")
    public String showFormForUpdate(@PathVariable(name = "id") Integer id, Model model) {
    	Review review = reviewService.getReviewById(id).orElse(null);
        // 取得した商品情報を画面（ビュー）に渡します。
        model.addAttribute("Review", review);
        // 商品情報を更新する画面（'edit'）を表示します。
        return "reviews/edit";
    }   

  	@GetMapping("/deleteReview/{id}")
      public String deleteReview(@PathVariable(value = "id") Integer id) {
          this.reviewService.deleteReview(id);
          // 削除が完了したら、ホームページに戻ります。
          return "redirect:/";
      }
  	
  	
  	@GetMapping("/reviewDetail/{id}")
  	public String reviewDetail(@PathVariable(value ="id")Integer id, Model model) {
  		Review review  = reviewService.getReviewById(id).orElse(null);
  		
  		model.addAttribute("Review" , review);
  		
  		return "reviews/detail";
  	}
  	
  	/**
  	@GetMapping("/reviews/{houseid}")
  	public String show(@PathVariable(name = "houseid")Integer houseid, Model model) {
  		Review reviewhouse = reviewRepository.getReferenceById(houseid);
  		model.addAttribute("reviewhouse",reviewhouse);
  		
  		return "reviews/show";
  	} **/
  	@GetMapping("reviews/{houseid}")
  	public Optional<Review> getReviewByhouseId(@PathVariable Integer houseid){
  		return reviewService.getReviewByHouseId(houseid);
  	}
  	
 }


