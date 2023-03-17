package com.alissonlimabr.resetpasswordapi.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alissonlimabr.resetpasswordapi.service.PasswordResetService;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {

  // Injeção de dependência do PasswordResetService
  @Autowired
  private PasswordResetService passwordResetService;

  // Rota para enviar um e-mail de redefinição de senha
  @PostMapping("/forgot")
  public ResponseEntity<Map<String, String>> forgotPassword(@RequestParam("email") String email)
      throws MessagingException {
    // Chama o método sendPasswordResetEmail do PasswordResetService para enviar o
    // e-mail
    passwordResetService.sendPasswordResetEmail(email);
    // Cria um Map com a mensagem de sucesso e retorna uma resposta HTTP 200 OK com
    // o Map no corpo da resposta
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("message", "Solicitação de alteração de senha enviada com sucesso.");
    return ResponseEntity.ok(responseBody);
  }

  // Rota para redefinir a senha com base no token enviado pelo e-mail
  @PostMapping("/reset/{token}")
  public ResponseEntity<Map<String, String>> resetPassword(@PathVariable String token,
      @RequestBody Map<String, String> newPassword) {
    try {
      // Chama o método changePassword do PasswordResetService para alterar a senha do
      // usuário com base no token e na nova senha fornecidos
      passwordResetService.changePassword(token, newPassword.get("password"));
      // Cria um Map com a mensagem de sucesso e retorna uma resposta HTTP 200 OK com
      // o Map no corpo da resposta
      Map<String, String> response = new HashMap<>();
      response.put("message", "Senha alterada com sucesso.");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (MessagingException e) {
      // Se ocorrer uma exceção InvalidAttributeValueException, significa que o token
      // não é válido ou expirou, ou que o usuário associado ao token não foi
      // encontrado
      // Cria um Map com a mensagem de erro e retorna uma resposta HTTP 400 Bad
      // Request com o Map no corpo da resposta
      Map<String, String> response = new HashMap<>();
      response.put("error", e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }
}
