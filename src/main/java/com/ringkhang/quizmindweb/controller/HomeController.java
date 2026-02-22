package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.model.UserDetailsTable;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    private UsersDetailsService service;

    @GetMapping("/")
    @Hidden
    public void Home(HttpServletResponse response) throws IOException {
//        response.sendRedirect("swagger-ui.html");
        response.sendRedirect("index.html");
    }

    @PostMapping("/register")
    public void register (@RequestBody UserDetailsTable user){
        service.register(user);
    }

    @GetMapping("/users")
    public UserDetailsTable getUserDetails(){
        return service.getCurrentUserDetails();
    }

    @PostMapping("/login/auth")
    public String login(@RequestParam String username, @RequestParam String pass){
        return service.varify(username,pass);
    }
}