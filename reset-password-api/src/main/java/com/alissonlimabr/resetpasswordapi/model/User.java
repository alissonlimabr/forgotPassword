package com.alissonlimabr.resetpasswordapi.model;

import java.io.Serializable;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  public void setPassword(String password) {
    setPassword(password, true);
  }

  public void setPassword(String password, boolean encriptar) {
    if (password != null && encriptar) {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      password = passwordEncoder.encode(password);
    }
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }

  // Demais colunas...
}