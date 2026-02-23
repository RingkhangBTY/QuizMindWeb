package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.DTO.InitialAppPayloadDTO;
import com.ringkhang.quizmindweb.DTO.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.DTO.UserDetailsDTO;
import com.ringkhang.quizmindweb.model.*;
import com.ringkhang.quizmindweb.repo.ScoreHistoryRepo;
import com.ringkhang.quizmindweb.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceTransformer;

import java.util.List;

@Service
public class UsersDetailsService {

    private final UserDetailsRepo repo;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenService jwtTokenService;
    private final ScoreHistoryRepo scoreHistoryRepo;

    public UsersDetailsService(UserDetailsRepo repo, AuthenticationManager authenticationManager, JWTTokenService jwtTokenService, ScoreHistoryRepo scoreHistoryRepo) {
        this.repo = repo;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.scoreHistoryRepo = scoreHistoryRepo;
    }

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

        return userName == null;
    }

    public int getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsersPrincipal principal = (UsersPrincipal) authentication.getPrincipal();
        return principal.getUser().getId();
    }
    public UserDetailsTable getCurrentUserDetails() {
        return repo.findById(getCurrentUserId()).orElse(new UserDetailsTable());
    }
    //Varifies the current users login request and send back jwt token if varified
    public ResponseEntity<String> varify(String username, String pass) {
        Authentication authentication = authenticationManager.
                authenticate(
                        new UsernamePasswordAuthenticationToken(username,pass)
                );

        if (authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(jwtTokenService.getToken(username));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fails to generate JTW token/auth fails");
    }

    public ResponseEntity<InitialAppPayloadDTO> getInitialUserData() {
        UserDetailsTable userDetailsTable = getCurrentUserDetails();
        List<ScoreHistoryTable> scoreHistoryTable = scoreHistoryRepo.getScoreHistoriesByUserId(userDetailsTable.getId());

        InitialAppPayloadDTO initialAppPayloadDTO = new InitialAppPayloadDTO(
                new UserDetailsDTO(
                        userDetailsTable.getId(),
                        userDetailsTable.getUsername(),
                        userDetailsTable.getEmail()
                ),
                new ScoreHistoryDisplay(
                        scoreHistoryTable.getLast().getScoreId(),
                        userDetailsTable.getUsername(),
                        scoreHistoryTable.getLast().getTotal_question(),
                        scoreHistoryTable.getLast().getCorrect_ans(),
                        scoreHistoryTable.getLast().getTest_score(),
                        scoreHistoryTable.getLast().getFeedback(),
                        scoreHistoryTable.getLast().getTopic_sub(),
                        scoreHistoryTable.getLast().getLevel(),
                        scoreHistoryTable.getLast().getShort_des(),
                        scoreHistoryTable.getLast().getTime_stamp()
                ),
                scoreHistoryTable.size(),
                getAvgScore(scoreHistoryTable),
                getEasyQuizCount(scoreHistoryTable),
                getMediumQuizCount(scoreHistoryTable),
                getHardQuizCount(scoreHistoryTable),
                getHardPlusQuizCount(scoreHistoryTable)
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(initialAppPayloadDTO);
    }

    private int getHardPlusQuizCount(List<ScoreHistoryTable> scoreHistoryTable) {
        int quizCount = 0;
        for (ScoreHistoryTable sht:scoreHistoryTable){
            if (sht.getLevel().equalsIgnoreCase("hardPlus")){
                quizCount+=sht.getTotal_question();
            }
        }
        return quizCount;
    }

    private int getHardQuizCount(List<ScoreHistoryTable> scoreHistoryTable) {
        int quizCount = 0;
        for (ScoreHistoryTable sht:scoreHistoryTable){
            if (sht.getLevel().equalsIgnoreCase("hard")){
                quizCount+=sht.getTotal_question();
            }
        }
        return quizCount;
    }

    private int getMediumQuizCount(List<ScoreHistoryTable> scoreHistoryTable) {
        int quizCount = 0;
        for (ScoreHistoryTable sht:scoreHistoryTable){
            if (sht.getLevel().equalsIgnoreCase("medium")){
                quizCount+=sht.getTotal_question();
            }
        }
        return quizCount;
    }

    private int getEasyQuizCount(List<ScoreHistoryTable> scoreHistoryTable) {
        int quizCount = 0;
        for (ScoreHistoryTable sht:scoreHistoryTable){
            if (sht.getLevel().equalsIgnoreCase("easy")){
                quizCount+=sht.getTotal_question();
            }
        }
        return quizCount;
    }

    private float getAvgScore(List<ScoreHistoryTable> historyTables) {
        float sum = 0;
        for (ScoreHistoryTable sht : historyTables){
            sum+= sht.getTest_score();
        }
        return sum/(historyTables.size());
    }

    public ResponseEntity<UserDetailsDTO> getCurrentUserDetailsWithoutPass(){
        UserDetailsTable userDetailsTable = getCurrentUserDetails();
        if (userDetailsTable == null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new UserDetailsDTO());
        }
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(
                userDetailsTable.getId(),
                userDetailsTable.getUsername(),
                userDetailsTable.getEmail()
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDetailsDTO);
    }
}