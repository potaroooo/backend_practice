package com.example.demo.api;

import com.example.demo.api.request.RestaurantRequest;
import com.example.demo.api.request.ReviewRequest;
import com.example.demo.api.response.RestaurantDetailedResponse;
import com.example.demo.api.response.RestaurantResponse;
import com.example.demo.api.response.RestaurantReviewResponse;
import com.example.demo.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Api {

    private final Service service;

    @GetMapping("/restaurants")
    public List<RestaurantResponse> getRestaurants() {
        return service.getRestaurants();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public RestaurantDetailedResponse getRestaurantDetail(
            @PathVariable(name = "restaurantId") Long restaurantId
    ) {
        return service.getRestaurantDetail(restaurantId);
    }

    @PostMapping("/restaurant")
    public void createNewRestaurant(
            @RequestBody RestaurantRequest request
            ) {
        service.createRestaurant(request);
    }

    @PutMapping("/restaurant/{restaurantId}")
    public void updateRestaurantInformation(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @RequestBody RestaurantRequest request
    ) {
        service.updateRestaurant(restaurantId, request);
    }

    @DeleteMapping("/restaurant/{restaurantId}")
    public void DeleteRestaurant(
            @PathVariable(name = "restaurantId") Long restaurantId
    ) {
        service.deleteRestaurant(restaurantId);
    }

    @PostMapping("/review")
    public void createReview(
            @RequestBody ReviewRequest request
            ) {
        service.createReview(request);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(
            @PathVariable(name = "reviewId") Long reviewId
    ) {
        service.deleteReview(reviewId);
    }

    @GetMapping("/restaurant/{restaurantId}/reviews")
        public RestaurantReviewResponse getRestaurantReviews(
                @PathVariable(name = "restaurantId") Long restaurantId,
                @RequestParam("offset") Integer offset,
                @RequestParam("limit") Integer limit
    ) {
        return service.getRestaurantReviews(restaurantId, PageRequest.of(offset / limit, limit));
    }

}
