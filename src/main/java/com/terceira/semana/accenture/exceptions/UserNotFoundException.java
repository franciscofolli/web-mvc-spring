package com.terceira.semana.accenture.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("Error - User Not Founded!");
    }

}
