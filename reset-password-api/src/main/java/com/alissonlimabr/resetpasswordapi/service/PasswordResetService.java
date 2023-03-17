package com.alissonlimabr.resetpasswordapi.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alissonlimabr.resetpasswordapi.model.PasswordResetToken;
import com.alissonlimabr.resetpasswordapi.model.User;
import com.alissonlimabr.resetpasswordapi.repository.PasswordResetTokenRepository;
import com.alissonlimabr.resetpasswordapi.repository.UserRepository;

import jakarta.mail.MessagingException;

@Service
public class PasswordResetService {

  @Autowired
  private UserRepository userRepository; // Repositório para acesso aos usuários

  @Autowired
  private PasswordResetTokenRepository tokenRepository; // Repositório para acesso aos tokens de reset de senha

  @Autowired
  private EmailService emailService; // Serviço para envio de e-mails

  // Método para criar um token de reset de senha para um usuário
  public void createPasswordResetTokenForUser(User user, String token) {
    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setUser(user); // Define o usuário associado ao token
    resetToken.setToken(token);
    tokenRepository.save(resetToken); // Salva o token no repositório
  }

  // Método para obter um token de reset de senha a partir do seu valor
  public PasswordResetToken getPasswordResetToken(String token) {
    return tokenRepository.findByToken(token);
  }

  // Método para enviar um e-mail de reset de senha para um usuário
  public void sendPasswordResetEmail(String email) throws MessagingException {
    User user = userRepository.findByEmail(email);
    if (user == null || user.getEmail() == null) {
      return;
    }
    String token = UUID.randomUUID().toString(); // Gerar um novo token
    createPasswordResetTokenForUser(user, token); // Criar um token para o usuário
    emailService.sendPasswordResetEmail(user.getEmail(), token); // Enviar o e-mail com o token
  }

  // Método para alterar a senha de um usuário a partir de um token de reset de
  // senha e uma nova senha
  public void changePassword(String token, String newPassword) throws MessagingException {
    PasswordResetToken resetToken = tokenRepository.findByToken(token);
    if (resetToken == null) { // Verificar se o token é válido
      throw new MessagingException("Token inválido ou expirado!");
    }

    User user = resetToken.getUser();
    if (user == null) { // Verificar se o usuário existe
      throw new MessagingException("Usuário não encontrado!");
    }

    user.setPassword(newPassword); // Alterar a senha do usuário
    userRepository.save(user); // Salvar as alterações no repositório
    tokenRepository.delete(resetToken); // Remover o token utilizado
  }

}
