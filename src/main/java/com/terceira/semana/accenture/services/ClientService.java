package com.terceira.semana.accenture.services;

import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.ResponseDTO;

import java.util.List;

public interface ClientService {

    default ClientDTO createNewClient(ClientDTO clientDTO){
        throw new UnsupportedOperationException();
    }

    default ResponseDTO alterClientData(int id, ClientDTO clientDTO){
        throw new UnsupportedOperationException();
    }

    default ResponseDTO deleteClient(int id){
        throw new UnsupportedOperationException();
    }

    default ClientDTO findClient(int id){
        throw new UnsupportedOperationException();
    }

    default List<ClientDTO> listAllClients(String nome, String cpfCnpj, String cidade, String uf) {
        throw new UnsupportedOperationException();
    }

    default ResponseDTO exportAllClients(String nome, String cpfCnpj, String cidade, String uf, String reportFormat) {
        throw new UnsupportedOperationException();
    }

}
