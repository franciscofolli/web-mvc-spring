package com.terceira.semana.accenture.services;

import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.dtos.UserDTO;
import org.springframework.http.ResponseEntity;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface UserService {

    default UserDTO createUser(UserDTO user) {
        throw new UnsupportedOperationException();
    }

    default UserDTO alterUser(Integer id, UserDTO user){
        throw new UnsupportedOperationException();
    }

    default ResponseDTO deleteUser(Integer id){
        throw new UnsupportedOperationException();
    }

    default UserDTO findUser(Integer id){
        throw new UnsupportedOperationException();
    }

    default List<UserDTO> getAllUsers(String nome, String email) {
        throw new UnsupportedOperationException();
    }

    default UserDTO userLogin(String login, String password){
        throw new UnsupportedOperationException();
    };











}
