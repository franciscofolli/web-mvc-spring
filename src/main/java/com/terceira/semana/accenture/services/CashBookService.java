package com.terceira.semana.accenture.services;


import com.terceira.semana.accenture.dtos.AccountServiceDTO;
import com.terceira.semana.accenture.dtos.CashBookDTO;
import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.models.ClientModel;

import java.time.LocalDate;
import java.util.List;

public interface CashBookService {

    default CashBookDTO createNewCashBook(CashBookDTO cashBookDTO){
        throw new UnsupportedOperationException();
    }

    default ResponseDTO alterCashBookData(Integer id, CashBookDTO cashBookDTO){
        throw new UnsupportedOperationException();
    }

    default ResponseDTO deleteCashBook(Integer id){
        throw new UnsupportedOperationException();
    }

    default CashBookDTO findCashBook(Integer id){
        throw new UnsupportedOperationException();
    }

    default List<CashBookDTO> listAllCashBooks(Integer idCliente) {
        throw new UnsupportedOperationException();
    }

    default List<AccountServiceDTO> ListContService(Integer idCliente, String dataInicial, String dataFinal) {
        throw new UnsupportedOperationException();
    }

    default List<CashBookDTO> listAllCashBooksBetween(Integer idCliente, LocalDate datainicial, LocalDate dataFinal){
        throw new UnsupportedOperationException();
    }


}
