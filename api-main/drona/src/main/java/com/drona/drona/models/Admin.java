package com.drona.drona.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The `Admin` class represents an administrator in the system.
 */
@Data
@AllArgsConstructor
@Document(collection = "admin")
public class Admin extends User{


    @DBRef
    private List<Coupon> coupons; // List of coupons managed by the admin.


    /**
     * Default constructor for the `Admin` class.
     */
    public Admin()
    {
        coupons=new ArrayList<>();
    }
}
