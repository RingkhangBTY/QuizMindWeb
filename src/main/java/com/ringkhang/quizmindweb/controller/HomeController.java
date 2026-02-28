package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.DTO.InitialAppPayloadDTO;
import com.ringkhang.quizmindweb.DTO.UserDetailsDTO;
import com.ringkhang.quizmindweb.model.UserDetailsTable;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public ResponseEntity<String> register (@RequestBody UserDetailsTable user){
        ResponseEntity<String> response = service.register(user);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)){
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.INFO,"User registered successfully...");
        }else{
            Logger.getLogger(HomeController.class.getName())
                    .log(Level.SEVERE,"User registered failed...");
        }
        return response;
    }

    @GetMapping("/users")
    public ResponseEntity<UserDetailsDTO> getUserDetails(){
        Logger.getLogger(HomeController.class.getName())
                .log(Level.INFO,"Requested current user details....");

        return service.getCurrentUserDetailsWithoutPass();
    }

    @PostMapping("/login/auth")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String pass){
        Logger.getLogger(HomeController.class.getName())
                .log(Level.INFO,"Requested for login...");

        return service.varify(username,pass);
    }

    @GetMapping("/initial_data")
    public ResponseEntity<InitialAppPayloadDTO> getInitialData(){
        Logger.getLogger(HomeController.class.getName())
                .log(Level.INFO,"Initial data requested.....");

        return service.getInitialUserData();
    }
}