package com.example.samuraitravel.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
   
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	
	public ReviewController(ReviewRepository reviewRepository , ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
	}
	
	
	@GetMapping
	public String index(Model model) {
		List<Review> reviews = reviewRepository.findAll();
		
		model.addAttribute("reviews" ,reviews);
		
		return "/reviews/index";  /*  requestMapping似合わせる。ここの情報がページとして表示される*/
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("reviewRegisterForm" , new ReviewRegisterForm());
		return "reviews/register";
	}
  
	
	@PostMapping("/register")
	public String create(@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "reviews/register";
		}
		
		reviewService.create(reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを登録しました。");
		
		return "reviews";
	}
	
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        Review review = reviewRepository.getReferenceById(id);
        					
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), review.getName(), review.getCommentText(), review.getValue());
        
      
        model.addAttribute("reviewEditForm", reviewEditForm);
        
        return "reviews";
    } 
	
}

