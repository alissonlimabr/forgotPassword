package com.alissonlimabr.resetpasswordapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alissonlimabr.resetpasswordapi.model.User;
import com.alissonlimabr.resetpasswordapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService; // Injeção de dependência do UserService

  @PostMapping("/sign-up")
  public ResponseEntity<User> insert(@RequestBody User objeto) {
    // Chama o método "save" do "userService" passando o objeto do tipo "User" como
    // parâmetro
    User registro = userService.save(objeto);
    // Retorna uma resposta HTTP com o objeto "registro" e status "CREATED"
    return new ResponseEntity<>(registro, HttpStatus.CREATED);
  }

  @GetMapping("/check-email/{email}")
  public ResponseEntity<User> checkEmail(@PathVariable("email") String email) {
    // Chama o método "checkEmail" do "userService" passando o email como parâmetro
    boolean emailExists = userService.checkEmail(email);
    // Se o email já existir, retorna uma resposta HTTP com o status "BAD_REQUEST"
    if (emailExists == true)
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // Caso contrário, retorna uma resposta HTTP com o status "OK"
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
