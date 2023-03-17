package com.alissonlimabr.resetpasswordapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alissonlimabr.resetpasswordapi.model.User;
import com.alissonlimabr.resetpasswordapi.repository.UserRepository;

@Service
public class UserService {

  // injeta a dependência do repositório de usuário
  @Autowired
  private UserRepository userRepository;

  // salva um objeto usuário no banco de dados
  public User save(User objeto) {

    // verifica se a senha do objeto é nula
    if (objeto.getPassword() == null) {

      // recupera um usuário pelo id
      User user = userRepository.findById(objeto.getId()).orElse(null);

      // se encontrar um usuário, define a senha do objeto como a do usuário
      // recuperado
      if (user != null) {
        objeto.setPassword(user.getPassword(), false);
      }
    }

    // salva o objeto usuário no banco de dados
    return userRepository.save(objeto);
  }

  public User getByEmail(String email) {
    User user = userRepository.findByEmail(email);
    return user;
  }

  // verifica se um determinado email já está cadastrado no banco de dados
  public boolean checkEmail(String email) {

    // busca um usuário pelo email
    return userRepository.findByEmail(email) != null;
  }

}
