package com.terceira.semana.accenture.repositories;

import com.terceira.semana.accenture.exceptions.ClientNotFoundException;
import com.terceira.semana.accenture.models.ClientModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Integer> {



}
