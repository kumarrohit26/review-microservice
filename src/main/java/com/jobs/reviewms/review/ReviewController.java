package com.jobs.reviewms.review;

import com.jobs.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService,
                            ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        Review review = reviewService.getReviewById(reviewId);
        if(review != null){
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Review> createReviews(@RequestParam Long companyId, @RequestBody Review review){
        Review created_review = reviewService.addReview(companyId, review);
        if(created_review != null)
        {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>(created_review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestBody Review review){
        Review updatedReview = reviewService.updateReview(reviewId, review);
        if(updatedReview != null){
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if(isReviewDeleted){
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Deletion failed", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviews(companyId);
        return reviewList.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
