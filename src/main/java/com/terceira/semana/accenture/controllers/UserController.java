package com.terceira.semana.accenture.controllers;

import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.dtos.UserDTO;
import com.terceira.semana.accenture.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController extends ResponseDTO{

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseDTO> getUser(@PathVariable("id") int id) {
        final UserDTO response = this.userService.findUser(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsersList(@RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "email", required = false) String email) {
        if (nome == null) nome = "";
        if (email == null) email = "";
        List<UserDTO> response = this.userService.getAllUsers(nome,email);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.get(0).getStatusCode()));
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDTO> setNewUser(@Valid @RequestBody UserDTO user) {
        UserDTO response = this.userService.createUser(user);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseDTO> alterUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO user) {
        final UserDTO response = this.userService.alterUser(id,user);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("id") int id) {
        ResponseDTO response = this.userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/user/login")
    public ResponseEntity<ResponseDTO> userLogin(@Valid @RequestBody UserDTO user) {
        final UserDTO response = this.userService.userLogin(user.getLogin(), user.getPassword());
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
    }

}
