package com.edu.Ecom_product.Controller;

import com.edu.Ecom_product.Model.Users;
import com.edu.Ecom_product.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return userService.registerUser(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return userService.verifyUser(users);
    }
}
