package com.drona.drona.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


/**
 * The `Coupon` class represents a discount coupon that can be issued to users for discounts on courses.
 * Coupons are created and managed by administrators.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    private String id;                 // Unique identifier for the coupon.
    private String couponCode;         // Unique code for the coupon.
    private String issuerName;         // Name of the entity that issued the coupon.
    private double discountPercentage; // Percentage discount offered by the coupon.
    private int redemptionLimit;       // Maximum number of times the coupon can be redeemed.
    private int timesUsed;             // Number of times the coupon has been used.
}
