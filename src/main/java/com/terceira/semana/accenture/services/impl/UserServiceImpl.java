package com.terceira.semana.accenture.services.impl;

import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.dtos.UserDTO;
import com.terceira.semana.accenture.exceptions.UserNotFoundException;
import com.terceira.semana.accenture.mappers.UserMapper;
import com.terceira.semana.accenture.models.UserModel;
import com.terceira.semana.accenture.repositories.UserRepository;
import com.terceira.semana.accenture.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO){
        try {
            final UserModel user = this.userMapper.fromDTOToEntity(userDTO);
            this.userRepository.save(user);
            userDTO.setMessage("Success - New User created Successfully");
            userDTO.setStatusCode(201);
            return userDTO;
        } catch (ConstraintViolationException e) {
            List<String> invalidFields = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            userDTO.setMessage("Error - " + invalidFields.stream().map(value -> value+" ").collect(Collectors.joining(",")));
            userDTO.setStatusCode(400);
            return userDTO;
        }
    }

    @Override
    public UserDTO alterUser(Integer id, UserDTO userDTO) {
        try {
            final Optional<UserModel> user = this.userRepository.findById(id);
            final UserModel mappedUser = this.userMapper.fromDTOToEntity(userDTO);


            if (user.isPresent()){
                if(mappedUser.getEmail() != null && !mappedUser.getEmail().isEmpty()) user.get().setEmail(mappedUser.getEmail());
                if(mappedUser.getLogin() != null && !mappedUser.getLogin().isEmpty()) user.get().setLogin(mappedUser.getLogin());
                if(mappedUser.getNome() != null && !mappedUser.getNome().isEmpty()) user.get().setNome(mappedUser.getNome());
                if(userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) { user.get().setSenha(userDTO.getPassword()); }
                if(mappedUser.getPerfil() != null && !mappedUser.getPerfil().isEmpty()) user.get().setPerfil(mappedUser.getPerfil());
                if(mappedUser.getStatus() != null && !mappedUser.getStatus().isEmpty()) user.get().setStatus(mappedUser.getStatus());
                if(mappedUser.getTelefone() != null && !mappedUser.getTelefone().isEmpty()) user.get().setTelefone(mappedUser.getTelefone());

                this.userRepository.save(user.get());
            } else {
                throw new UserNotFoundException();
            }

            final UserDTO response = new UserDTO();
            response.setMessage("User Updated Successfully!");
            response.setStatusCode(200);
            return response;

        } catch (UserNotFoundException e) {
            final UserDTO response = new UserDTO();
            response.setMessage("Error - User to be updated was not founded!");
            response.setStatusCode(404);
            return response;
        } catch (Exception e) {
            final UserDTO response = new UserDTO();
            response.setMessage("Error - User data cannot be updated!");
            response.setStatusCode(404);
            return response;
        }
    }

    @Override
    public ResponseDTO deleteUser(Integer id) {
        try {
            this.userRepository.deleteById(id);
            ResponseDTO response = new ResponseDTO();
            response.setMessage("User Deleted Successfully!");
            response.setStatusCode(200);
            return response;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            ResponseDTO response = new ResponseDTO();
            response.setMessage("User not Found");
            response.setStatusCode(404);
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - Client could not be deleted.");
            response.setStatusCode(500);
            return response;
        }

    }

    @Override
    public UserDTO findUser(Integer id) {
        try {
            final Optional<UserModel> user = this.userRepository.findById(id);
            if(user.isPresent()){
                final UserDTO response = this.userMapper.fromEntityToDTO(user.get());
                response.setMessage("User Founded Successfully!");
                response.setStatusCode(200);
                return response;
            } else {
                throw new UserNotFoundException();
            }

        } catch (UserNotFoundException e) {
            final UserDTO response = new UserDTO();
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return response;
        }
    }

    @Override
    public List<UserDTO> getAllUsers(String nome, String email){
        try {
            if (!nome.isEmpty() && !email.isEmpty()) {
                final List<UserModel> users = this.userRepository.findByNomeContainsAndEmailContains(nome, email);
                return this.userMapper.fromEntityListToDTOList(users);
            } else if (!nome.isEmpty()){
                final List<UserModel> users = this.userRepository.findByNomeContains(nome);
                return this.userMapper.fromEntityListToDTOList(users);
            } else if (!email.isEmpty()) {
                final List<UserModel> users = this.userRepository.findByEmailContains(email);
                return this.userMapper.fromEntityListToDTOList(users);
            } else {
                final List<UserModel> users = this.userRepository.findAll();
                return this.userMapper.fromEntityListToDTOList(users);
            }
        } catch (Exception e) {
            e.printStackTrace();
            List<UserDTO> response = new ArrayList<>();
            response.add(new UserDTO());
            response.get(0).setMessage("Error - An Error ocurred");
            response.get(0).setStatusCode(500);
            return response;
        }
    }


    @Override
    public UserDTO userLogin(String login, String password) {
        try {
            final UserDTO response = this.userMapper.fromEntityToDTO(this.userRepository.findByLoginAndSenha(login, password));
            if(!response.getStatus().equals("A")) {
                response.setMessage("User is not Active");
                response.setStatusCode(401);
            } else {
                response.setMessage("User successfully logged in");
                response.setStatusCode(200);
            }
            return response;
        } catch (Exception e) {
            final UserDTO response = new UserDTO();
            response.setMessage("Invalid Login or Password");
            response.setStatusCode(401);
            return response;
        }
    }


}
