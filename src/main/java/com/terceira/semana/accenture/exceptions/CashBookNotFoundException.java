package com.terceira.semana.accenture.exceptions;

public class CashBookNotFoundException extends RuntimeException{

    public CashBookNotFoundException(){
        super("Error - CashBook Not founded");
    }
}
