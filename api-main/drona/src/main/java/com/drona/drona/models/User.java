package com.drona.drona.models;

import com.drona.drona.services.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Document(collection="users")
@Data
@AllArgsConstructor

public class User {

    @Id
    private String id;

    @NotBlank(message = "username can't be blank")
    @Size(max = 15)
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(max = 80)
    private String Password;

    @Field("userRole")
    private UserRole userRole;

//    //for checking whether the account is locked or no
//    private Boolean locked=false;
//    private Boolean enabled=false;





    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", Password='" + Password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
    public User() {

    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority=new SimpleGrantedAuthority(userRole.name());
//        return Collections.singletonList(authority);
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !locked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
}
