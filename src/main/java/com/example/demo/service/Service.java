package com.example.demo.service;

import com.example.demo.api.request.RestaurantRequest;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Restaurant;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class Service {

    private final RestaurantRepository restaurantRepository;

    private final MenuRepository menuRepository;

    private final ReviewRepository reviewRepository;

    @Transactional
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
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

    @Transactional
    public RestaurantRequest getRestaurant(
            final Long restaurantId
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("존재하지 않는 레스토랑입니다."));
        List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        return new RestaurantRequest(restaurant.getName(), restaurant.getAddress(), menus);
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
    }
}
