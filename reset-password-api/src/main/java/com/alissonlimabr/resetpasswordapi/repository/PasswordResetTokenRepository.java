package com.alissonlimabr.resetpasswordapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alissonlimabr.resetpasswordapi.model.PasswordResetToken;
import com.alissonlimabr.resetpasswordapi.model.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

  PasswordResetToken findByToken(String token);

  PasswordResetToken findByUser(User user);
}
