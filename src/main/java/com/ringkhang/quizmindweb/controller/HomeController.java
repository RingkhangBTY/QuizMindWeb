package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.DTO.InitialAppPayloadDTO;
import com.ringkhang.quizmindweb.DTO.UserDetailsDTO;
import com.ringkhang.quizmindweb.model.UserDetailsTable;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HomeController {

    private final UsersDetailsService service;

    public HomeController(UsersDetailsService service) {
        this.service = service;
    }

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
    public ResponseEntity<UserDetailsDTO> getUserDetails(){
        return service.getCurrentUserDetailsWithoutPass();
    }

    @PostMapping("/login/auth")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String pass){
        return service.varify(username,pass);
    }

    @GetMapping("/initial_data")
    public ResponseEntity<InitialAppPayloadDTO> getInitialData(){
        return service.getInitialUserData();
    }
}