package com.ringkhang.quizmindweb.repo;

import com.ringkhang.quizmindweb.model.UserDetailsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetailsTable,Integer> {

    UserDetailsTable findByUsername(String username);

    @Query(value = "select username from user_details where username=:name", nativeQuery = true)
    String getUsernameByUsername(@Param("name") String name);

}