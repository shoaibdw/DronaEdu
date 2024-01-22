package com.drona.drona.repositories;

import com.drona.drona.models.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


/**
 * Repository for interacting with the "coupon" collection in the MongoDB database.
 */
public interface CouponRepository extends MongoRepository<Coupon,String> {

    /**
     * Find a coupon by its coupon code.
     *
     * @param couponCode The coupon code to search for.
     * @return An optional containing the coupon if found, or empty if not found.
     */
    Optional<Coupon> findCouponByCouponCode(String couponCode);
}
