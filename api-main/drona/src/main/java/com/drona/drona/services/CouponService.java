package com.drona.drona.services;
import com.drona.drona.models.Admin;
import com.drona.drona.models.Coupon;
import com.drona.drona.repositories.AdminRepository;
import com.drona.drona.repositories.CouponRepository;
import com.drona.drona.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


/**
 * Service class for managing coupons and their interactions with admins.
 * Coupons can be created, retrieved by code, and listed.
 */
@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private AdminRepository adminRepository;


    /**
     * Create a new coupon and associate it with an admin.
     *
     * @param coupon   The coupon to be created.
     * @param username The username of the admin to associate the coupon with.
     */
    public void createCoupon(Coupon coupon,String username)
    {
        Admin admin=adminRepository.findAdminByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No admin with this username exists: " + username));
        couponRepository.save(coupon);
        admin.getCoupons().add(coupon);
        adminRepository.save(admin);
        couponRepository.save(coupon);

    }

    /**
     * Retrieve details of a coupon by its coupon code.
     *
     * @param couponCode The coupon code to retrieve details for.
     * @return The coupon details.
     * @throws NoSuchElementException if the coupon is not found.
     */
    public Coupon getCouponDetails(String couponCode) {
        return couponRepository.findCouponByCouponCode(couponCode)
                .orElseThrow(() -> new NoSuchElementException("Coupon not found for code: " + couponCode));
    }

    /**
     * Get a list of all available coupons.
     *
     * @return The list of all available coupons.
     */
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}
