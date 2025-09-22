package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.model.UserDetailsTable;
import com.ringkhang.quizmindweb.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService {

    @Autowired
    private UserDetailsRepo repo;

    public void register(UserDetailsTable user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        if (isUsernameFree(user.getUsername())){
            user.setPass(encoder.encode(user.getPass()));
            repo.save(user);
        }else {
            System.out.println("User already exist!");
        }
    }

    private boolean isUsernameFree(String username){
        String userName = repo.getUsernameByUsername(username);

        if (userName == null){
            return true;
        }
        return false;
    }
}