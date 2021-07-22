package com.terceira.semana.accenture.mappers;


import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.UserDTO;
import com.terceira.semana.accenture.models.ClientModel;
import com.terceira.semana.accenture.models.UserModel;
import org.springframework.stereotype.Component;

import java.nio.channels.ClosedByInterruptException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public ClientDTO fromEntityToDTO(ClientModel clientModel) {
        final ClientDTO response = new ClientDTO();

        response.setId(clientModel.getId());
        response.setRegistDate(clientModel.getDataCadastro().format(datePattern));
        response.setName(clientModel.getNome());
        response.setcpfCnpj(clientModel.getCpfCnpj());
        response.setAddress(clientModel.getLogradouro());
        response.setCity(clientModel.getCidade());
        response.setUf(clientModel.getUf());
        response.setCep(clientModel.getCep());
        response.setCellPhone(clientModel.getTelefone());
        response.setEmail(clientModel.getEmail());

        return response;

    }

    public ClientModel fromDTOToEntity(ClientDTO clientDTO){
        final ClientModel response = new ClientModel();

        if (clientDTO.getRegistDate() != null && !clientDTO.getRegistDate().isEmpty()) response.setDataCadastro(LocalDateTime.parse(clientDTO.getRegistDate(),datePattern));
        if (clientDTO.getName() != null && !clientDTO.getName().isEmpty()) response.setNome(clientDTO.getName());
        if (clientDTO.getcpfCnpj() != null && !clientDTO.getcpfCnpj().isEmpty()) {
            if(clientDTO.getcpfCnpj().contains(".") || clientDTO.getcpfCnpj().contains("-")){
                response.setCpfCnpj(clientDTO.getcpfCnpj());
                clientDTO.setMessage("Error - '.' or '-' is not allowed on CPF/CNPJ");
            } else {
                response.setCpfCnpj(clientDTO.getcpfCnpj());
            }
        }
        if (clientDTO.getAddress() != null && !clientDTO.getAddress().isEmpty()) response.setLogradouro(clientDTO.getAddress());
        if (clientDTO.getCity() != null && !clientDTO.getCity().isEmpty()) response.setCidade(clientDTO.getCity());
        if (clientDTO.getUf() != null && !clientDTO.getUf().isEmpty()) response.setUf(clientDTO.getUf());
        if (clientDTO.getCep() != null && !clientDTO.getCep().isEmpty()) response.setCep(clientDTO.getCep());
        if (clientDTO.getCellPhone() != null && !clientDTO.getCellPhone().isEmpty()) response.setTelefone(clientDTO.getCellPhone());
        if (clientDTO.getEmail() != null && !clientDTO.getEmail().isEmpty()) response.setEmail(clientDTO.getEmail());

        return response;

    }

    public List<ClientDTO> fromEntityListToDTOList(List<ClientModel> clients) {
        List<ClientDTO> response = clients.stream().map(this::fromEntityToDTO).collect(Collectors.toList());

        if(response.size() > 0){
            ClientDTO status = new ClientDTO();
            status.setMessage("Clients founded successfully!");
            status.setStatusCode(200);
            response.add(0, status);
            return response;
        } else {
            ClientDTO status = new ClientDTO();
            status.setMessage("Clients Not Founded");
            status.setStatusCode(404);
            response.add(0, status);
            return response;
        }
    }

}
