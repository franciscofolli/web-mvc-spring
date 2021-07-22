package com.terceira.semana.accenture.dtos;

import java.util.List;

public class AccountServiceDTO extends ResponseDTO{

    private Integer id;

    private String name;

    private String cpfCnpj;

    private String cellPhone;

    private List<ContabilDTO> contabil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public List<ContabilDTO> getContabil() {
        return contabil;
    }

    public void setContabil(List<ContabilDTO> contabil) {
        this.contabil = contabil;
    }
}
