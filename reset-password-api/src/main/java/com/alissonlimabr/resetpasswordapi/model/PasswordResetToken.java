package com.alissonlimabr.resetpasswordapi.model;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

  private static final int EXPIRATION = 60 * 30; // tempo de expiração do token: 30 minutos

  @Id // indica que esse campo é uma chave primária
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Chave primária gerada automaticamente pelo BD.
  private Long id;

  private String token; // token de redefinição de senha

  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER) // relacionamento 1:1 com a entidade User, recuperado do
                                                                // BD.
  @JoinColumn(nullable = false, name = "user_id") // indica que este campo é uma chave estrangeira referenciando a
                                                  // tabela User.
  private User user; // usuário associado ao token.

  private Date expiryDate; // data de expiração do token

  // Esse método cria um novo token com a data de expiração calculada
  public PasswordResetToken() {
    this.expiryDate = calculateExpiryDate(EXPIRATION);
  }

  public PasswordResetToken(User user, String token) {
    this.user = user; // associa um usuário ao token
    this.token = token; // define o token de redefinição de senha
    this.expiryDate = calculateExpiryDate(EXPIRATION); // cria um novo token com a data de expiração calculada
  }

  private Date calculateExpiryDate(final int expiryTimeInMinutes) {
    final Calendar cal = Calendar.getInstance(); // obtém uma instância do calendário atual
    cal.setTimeInMillis(new Date().getTime()); // define o tempo atual como o tempo do calendário
    cal.add(Calendar.MINUTE, expiryTimeInMinutes); // adiciona o tempo de expiração do token em minutos
    return new Date(cal.getTime().getTime()); // retorna a data com a expiração do token
  }
}
