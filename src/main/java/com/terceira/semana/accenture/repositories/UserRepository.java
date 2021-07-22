package com.terceira.semana.accenture.repositories;

import com.terceira.semana.accenture.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {


    List<UserModel> findByNomeContains(String nome);

    List<UserModel> findByEmailContains(String email);

    //@Query("FROM UserModel u WHERE u.nome LIKE CONCAT('%',?1,'%') AND u.email LIKE CONCAT('%',?2,'%')")
    List<UserModel> findByNomeContainsAndEmailContains(String nome, String email);

    UserModel findByLoginAndSenha(String login, String senha);

}
