package com.example.oauth2.Repository;

import com.example.oauth2.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1")
    public User findByUsername(String username);

    @Query("select u from User u where u.tokenEmail = ?1")
    public User findByTokenEmail(String token);
}
