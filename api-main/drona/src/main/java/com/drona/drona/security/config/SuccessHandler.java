package com.drona.drona.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.drona.drona.models.UserRole;
import com.drona.drona.repositories.UserRepository;
import com.drona.drona.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.drona.drona.models.User;

@Component
public class  SuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = null;
        if(authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User  userDetails = (DefaultOAuth2User ) authentication.getPrincipal();
            String username = userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"@gmail.com" ;
            if(userRepository.findUserByEmail(username) == null) {
                User user = new User();
                user.setEmail(username);
                user.setUsername(userDetails.getAttribute("email") !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login"));
                user.setPassword(("Dummy"));
                user.setUserRole(UserRole.STUDENT);
                userRepository.save(user);
            }
        }  redirectUrl = "/api/user/dashboard";
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}