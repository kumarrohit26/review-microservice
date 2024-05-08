package com.jobs.reviewms.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // custom method
    List<Review> findByCompanyId(Long id);
}
