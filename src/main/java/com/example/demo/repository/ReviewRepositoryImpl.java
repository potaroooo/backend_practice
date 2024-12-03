package com.example.demo.repository;

import com.example.demo.entity.QReview;
import com.example.demo.entity.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.querydsl.core.QueryModifiers.limit;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Double getAvgScoreByRestaurantId(Long restaurantId) {
        return queryFactory.select(QReview.review.score.avg())
                .from(QReview.review)
                .where(QReview.review.restaurantId.eq(restaurantId))
                .fetchFirst();
    }

    @Override
    public Slice<Review> findSliceByRestaurantId(Long restaurantId, Pageable page) {
        List<Review> reviews = queryFactory.select(QReview.review)
                .from(QReview.review)
                .where(QReview.review.restaurantId.eq(restaurantId))
                .offset((long) page.getPageNumber() * (long) page.getPageSize())
                .limit(page.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(
                reviews.stream().limit(page.getPageSize()).toList(),
                page,
                reviews.size() > page.getPageSize()
        );
    }

}
