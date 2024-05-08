package com.jobs.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long id);

    Review getReviewById(Long reviewId);

    Review addReview(Long companyId, Review review);

    Review updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
