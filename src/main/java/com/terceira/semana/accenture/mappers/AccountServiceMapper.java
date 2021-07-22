package com.terceira.semana.accenture.mappers;

import com.terceira.semana.accenture.dtos.AccountServiceDTO;
import com.terceira.semana.accenture.dtos.CashBookDTO;
import com.terceira.semana.accenture.dtos.ContabilDTO;
import com.terceira.semana.accenture.models.ClientModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountServiceMapper {

    public List<AccountServiceDTO> toContabilList(List<CashBookDTO> cashBookDTOList, ClientModel clientModel){
        final List<AccountServiceDTO> response = new ArrayList<>();
        final AccountServiceDTO data = new AccountServiceDTO();
        final List<ContabilDTO> cashBookData = new ArrayList<>();
        final AccountServiceDTO responseMessageStatus = new AccountServiceDTO();
        double lastSaldo = 0.0;

        data.setId(clientModel.getId());
        data.setName(clientModel.getNome());
        data.setCpfCnpj(clientModel.getCpfCnpj());
        data.setCellPhone(clientModel.getTelefone());

        for(CashBookDTO cashBookDTO : cashBookDTOList){
            ContabilDTO controlContabil = new ContabilDTO();
            controlContabil.setReleaseDate(cashBookDTO.getReleaseDate());
            controlContabil.setDescription(cashBookDTO.getDescription());
            controlContabil.setType(cashBookDTO.getType());
            controlContabil.setValue(cashBookDTO.getValue());
            if(controlContabil.getType().equals("C")){
                lastSaldo += cashBookDTO.getValue();
                controlContabil.setSaldo(lastSaldo);
            } else {
                lastSaldo -= cashBookDTO.getValue();
                controlContabil.setSaldo(lastSaldo);
            }

            cashBookData.add(controlContabil);
        }

        data.setContabil(cashBookData);

        responseMessageStatus.setMessage("Contabil founded successfully!");
        responseMessageStatus.setStatusCode(200);

        response.add(responseMessageStatus);
        response.add(data);



        return response;
    }
}
