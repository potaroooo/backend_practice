package com.example.demo.api.response;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RestaurantDetailedResponse {

    private Long id;
    private String name;
    private String address;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<Menu> menus;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Menu {

        private final Long id;
        private final String name;
        private final Integer price;
        private final ZonedDateTime createdAt;
        private final ZonedDateTime updatedAt;

    }

}
