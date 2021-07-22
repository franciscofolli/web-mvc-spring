package com.terceira.semana.accenture.exceptions;

import com.terceira.semana.accenture.dtos.ClientDTO;
import org.springframework.dao.EmptyResultDataAccessException;

public class ClientNotFoundException extends RuntimeException {


    public ClientNotFoundException() throws RuntimeException {
        super("Error - Client Not Founded!");
    }


}
