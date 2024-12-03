package com.example.demo.api;

import com.example.demo.api.request.RestaurantRequest;
import com.example.demo.entity.Restaurant;
import com.example.demo.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Api {

    private final Service service;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants() {
        return service.getRestaurants();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public RestaurantRequest getRestaurantInformation(
            @PathVariable(name = "restaurantId") Long restaurantId
    ) {
        return service.getRestaurant(restaurantId);
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
    public String createReview(
            @RequestBody Object obj
    ) {
        return "리뷰 등록";
    }

    @DeleteMapping("/review/{reviewId}")
    public String deleteReview(
            @PathVariable(name = "reviewId") Long reviewId
    ) {
        return "리뷰 삭제";
    }

    @GetMapping("/restaurant/{restaurantId}/reviews")
        public String getRestaurantReviews(
                @PathVariable(name = "restaurantId") Long restaurantId
    ) {
        return "리뷰 목록";
    }
}
