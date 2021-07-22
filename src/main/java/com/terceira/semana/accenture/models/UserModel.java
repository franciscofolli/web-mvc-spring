package com.terceira.semana.accenture.models;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuario")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The Cad Data cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    @NotNull(message = "The Name cannot be null")
    @Size(max = 30)
    private String nome;

    @NotNull(message = "The Login cannot be null")
    @Size(max = 15)
    @Column(nullable = false, unique = true)
    private String login;

    @NotNull(message = "The password cannot be null")
    @Size(max = 10)
    private String senha;

    @Size(max = 11)
    private String telefone;

    @Size(max = 100)
    private String email;

    @NotNull(message = "The perfil cannot be null")
    @Column(columnDefinition = "CHAR (1) CHECK (perfil = 'A' OR perfil = 'O')")
    @Size(max = 1)
    private String perfil;

    @NotNull(message = "The status cannot be null")
    @Column(columnDefinition = "CHAR (1) CHECK (status = 'A' OR status = 'C')")
    @Size(max = 1)
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

//    public String getSenha() {
//        return senha;
//    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
