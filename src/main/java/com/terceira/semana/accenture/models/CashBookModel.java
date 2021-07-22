package com.terceira.semana.accenture.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "LivroCaixa")
public class CashBookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The Client Id cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ClientModel idCliente;

    @NotNull(message = "The Launch Date cannot be null")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dataLancamento;

    @NotNull(message = "The Description cannot be null")
    @Size(max = 50)
    private String descricao;

    @NotNull(message = "The type cannot be null")
    @Column(columnDefinition = "CHAR(1) CHECK (tipo = 'D' OR tipo = 'C')")
    @Size(max = 1)
    private String tipo;

    @NotNull(message = "The value cannot be null")
    @Column(columnDefinition = "DECIMAL(12,2)")
    private Double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientModel getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ClientModel idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
