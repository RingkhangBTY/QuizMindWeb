package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.model.UserDetailsTable;
import com.ringkhang.quizmindweb.service.GeminiService;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HomeController {

    @Autowired
    private UsersDetailsService service;

    @GetMapping("/")
    @Hidden
    public void Home(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }

    @PostMapping("/register")
    public void register (@RequestBody UserDetailsTable user){
        service.register(user);
    }
}