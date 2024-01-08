package com.example.samuraitravel.service;
 
 import org.springframework.stereotype.Service;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.ReviewRepository;

import jakarta.transaction.Transactional;
 
 @Service
 public class ReviewService {
     private final ReviewRepository reviewRepository;    
     
     public ReviewService(ReviewRepository reviewRepository) {
         this.reviewRepository = reviewRepository;        
     }
     
 @Transactional
 public void create(ReviewRegisterForm reviewRegisterForm) {
	 Review review = new Review();
	 
	 review.setName(reviewRegisterForm.getName());
	 review.setCommentText(reviewRegisterForm.getCommentText());
	 review.setValue(reviewRegisterForm.getValue());
	 
	 reviewRepository.save(review);
 	}
 
 }
