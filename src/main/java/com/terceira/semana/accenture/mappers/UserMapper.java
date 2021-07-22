package com.terceira.semana.accenture.mappers;

import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.UserDTO;
import com.terceira.semana.accenture.models.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public UserDTO fromEntityToDTO (UserModel user) { // mapeando banco para API para apresentação dos dados
        final UserDTO response = new UserDTO();


        response.setId(user.getId());
        response.setCadData(user.getDataCadastro().format(datePattern));
        response.setLogin(user.getLogin());
        response.setName(user.getNome());
        response.setTel(user.getTelefone());
        response.setEmail(user.getEmail());
        response.setProfile(user.getPerfil());
        response.setStatus(user.getStatus());

        return response;
    }

    public UserModel fromDTOToEntity(UserDTO user) {
        final UserModel response = new UserModel();

        if(user.getCadData() != null && !user.getCadData().isEmpty()){
            response.setDataCadastro(LocalDateTime.parse(user.getCadData(), datePattern));
        }
        if(user.getLogin() != null && !user.getLogin().isEmpty())response.setLogin(user.getLogin());
        if(user.getName() != null && !user.getName().isEmpty())response.setNome(user.getName());
        if(user.getPassword() != null && !user.getPassword().isEmpty())response.setSenha(user.getPassword());
        if(user.getTel() != null && !user.getTel().isEmpty())response.setTelefone(user.getTel());
        if(user.getEmail() != null && !user.getEmail().isEmpty())response.setEmail(user.getEmail());
        if(user.getProfile() != null && !user.getProfile().isEmpty())response.setPerfil(user.getProfile());
        if(user.getStatus() != null && !user.getStatus().isEmpty())response.setStatus(user.getStatus());

        return response;

    }

    public List<UserDTO> fromEntityListToDTOList(List<UserModel> users) {
        List<UserDTO> response = users.stream().map(this::fromEntityToDTO).collect(Collectors.toList());

        if(response.size() > 0){
            UserDTO status = new UserDTO();
            status.setMessage("Users founded successfully!");
            status.setStatusCode(200);
            response.add(0, status);
            return response;
        } else {
            UserDTO status = new UserDTO();
            status.setMessage("Users Not Founded");
            status.setStatusCode(404);
            response.add(0, status);
            return response;
        }
    }

}
