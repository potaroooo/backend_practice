package com.example.demo.service;

import com.example.demo.api.request.RestaurantRequest;
import com.example.demo.api.request.ReviewRequest;
import com.example.demo.api.response.RestaurantDetailedResponse;
import com.example.demo.api.response.RestaurantResponse;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Restaurant;
import com.example.demo.entity.Review;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class Service {

    private final RestaurantRepository restaurantRepository;

    private final MenuRepository menuRepository;

    private final ReviewRepository reviewRepository;


    @Transactional(readOnly = true)
    public List<RestaurantResponse> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurant -> RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .build()
                ).toList();
    }

    @Transactional
    public Restaurant createRestaurant(
            final RestaurantRequest request
            ) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
        restaurantRepository.save(restaurant);

        request.getMenus().forEach(menu -> {
            Menu newMenu = Menu.builder()
                    .restaurantId(restaurant.getId())
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();
            menuRepository.save(newMenu);
        });

        return restaurant;
    }

    @Transactional(readOnly = true)
    public RestaurantDetailedResponse getRestaurantDetail(
            final Long restaurantId
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        return RestaurantDetailedResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address((restaurant.getAddress()))
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .menus(
                        menus.stream().map(menu -> RestaurantDetailedResponse.Menu.builder()
                                        .id(menu.getId())
                                        .name(menu.getName())
                                        .price(menu.getPrice())
                                        .createdAt(menu.getCreatedAt())
                                        .updatedAt(menu.getUpdatedAt())
                                        .build()
                        ).toList()
                )
                .build();
    }

    @Transactional
    public void updateRestaurant(
        final Long restaurantId, final RestaurantRequest request
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("존재하지 않는 레스토랑입니다."));
        restaurant.updateNameAndAddress(request.getName(), request.getAddress());
        restaurantRepository.save(restaurant);

        List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);

        request.getMenus().forEach(menu -> {
            Menu newMenu = Menu.builder()
                    .name(menu.getName())
                    .price(menu.getPrice())
                    .restaurantId(restaurant.getId())
                    .createdAt(ZonedDateTime.now())
                    .updatedAt(ZonedDateTime.now())
                    .build();
            menuRepository.save(newMenu);
        });
    }

    @Transactional
    public void deleteRestaurant(
        final Long restaurantId
    ) {
        restaurantRepository.deleteById(restaurantId);
        List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        menuRepository.deleteAll(menus);
        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);
        reviewRepository.deleteAll(reviews);
    }

    @Transactional
    public void createReview(
        final ReviewRequest request
    ) {
        Review review = Review.builder()
                .restaurantId(request.getRestaurantId())
                .score(request.getScore())
                .content(request.getContent())
                .createdAt(ZonedDateTime.now())
                .build();
        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(
        final Long reviewId
    ) {
        reviewRepository.deleteById(reviewId);
    }

    @Transactional(readOnly = true)
    public void getRestaurantReviews(

    ) {

    }
}
