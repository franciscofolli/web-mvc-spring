package com.terceira.semana.accenture.repositories;

import com.terceira.semana.accenture.models.CashBookModel;
import com.terceira.semana.accenture.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CashBookRepository extends JpaRepository<CashBookModel, Integer> {

    CashBookModel findById(int id);

    List<CashBookModel> findByIdCliente(ClientModel idCliente);

    List<CashBookModel> findByIdClienteAndDataLancamentoBetween(ClientModel idCliente, LocalDate initialDate, LocalDate finalDate);

}
