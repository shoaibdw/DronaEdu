package com.drona.drona.controllers;


import com.drona.drona.models.Coupon;
import com.drona.drona.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/coupon")
public class CouponController {


    @Autowired
    private CouponService couponService;

    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    /**
     * Create a new coupon with the provided details.
     *
     * @param coupon   The coupon to create.
     * @param username The username of the admin creating the coupon.
     * @return A response entity containing the created coupon.
     */
    @PostMapping("/createCoupon/{username}")
    public ResponseEntity<String> createCoupon(@RequestBody Coupon coupon , @PathVariable String username)
    {
        couponService.createCoupon(coupon,username);
        return new ResponseEntity<>(coupon.toString(), HttpStatus.OK);
    }


    /**
     * Retrieve the details of a specific coupon using its unique coupon code.
     *
     * @param couponCode The unique coupon code for the coupon.
     * @return A response entity containing the details of the specified coupon.
     */
    @GetMapping("/couponDetails/{couponCode}")
    public ResponseEntity<Coupon> getCouponDetails(@PathVariable String couponCode) {
        Coupon coupon = couponService.getCouponDetails(couponCode);
        return new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    /**
     * Get a list of all available coupons.
     *
     * @return A response entity containing a list of all available coupons.
     */
    @GetMapping("/allCoupons")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }


}
