package com.example.demo.repository;

import com.example.demo.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepositoryCustom {

    Double getAvgScoreByRestaurantId(final Long restaurantId);

    Slice<Review> findSliceByRestaurantId(final Long restaurantId, Pageable page);

}
