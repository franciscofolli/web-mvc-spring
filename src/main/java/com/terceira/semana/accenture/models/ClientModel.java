package com.terceira.semana.accenture.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Clientes")
public class ClientModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The Cad Data cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

    @NotNull(message = "The Name cannot be null")
    @Size(max = 30)
    private String nome;

    @NotNull(message = "The CPF/CNPJ cannot be null")
    @Column(columnDefinition = "VARCHAR (14) CHECK (cpf_cnpj NOT LIKE ('%.%') AND cpf_cnpj NOT LIKE ('%-%'))")
    @Size(max = 14)
    private String cpfCnpj;

    @NotNull(message = "The Address cannot be null")
    @Size(max = 50)
    private String logradouro;

    @NotNull(message = "The city cannot be null")
    @Size(max = 40)
    private String cidade;

    @NotNull(message = "The uf cannot be null")
    @Column(columnDefinition = "char(2)")
    @Size(max=2)
    private String uf;

    @NotNull(message = "The cep cannot be null")
    @Column(columnDefinition = "char(8)")
    @Size(max=8)
    private String cep;

    @Size(max = 11)
    private String telefone;

    @Size(max = 100)
    private String email;

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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
}
