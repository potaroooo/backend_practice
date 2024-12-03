package com.example.demo.api.response;

import com.example.demo.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantReviewResponse {

    private Double avgScore;
    private List<Review> reviews;
    private ReviewResponsePage page;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ReviewResponsePage {

        private Integer offset;
        private Integer limit;

    }

}
