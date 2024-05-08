package com.jobs.reviewms.review.impl;

import com.jobs.reviewms.review.Review;
import com.jobs.reviewms.review.ReviewRepository;
import com.jobs.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long id) {
        return reviewRepository.findByCompanyId(id);
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Review addReview(Long companyId, Review review) {

        if(companyId != null && review != null){
            review.setCompanyId(companyId);
            return reviewRepository.save(review);
        }
        return null;
    }

    @Override
    public Review updateReview(Long reviewId, Review updatedReview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
                review.setTitle(updatedReview.getTitle());
                review.setDescription(updatedReview.getDescription());
                review.setRating(updatedReview.getRating());
                review.setCompanyId(updatedReview.getCompanyId());
                reviewRepository.save(review);
                return review;
        }
        return null;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review != null){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
