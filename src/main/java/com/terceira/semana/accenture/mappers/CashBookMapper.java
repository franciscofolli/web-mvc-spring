package com.terceira.semana.accenture.mappers;

import com.terceira.semana.accenture.dtos.CashBookDTO;
import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.exceptions.ClientNotFoundException;
import com.terceira.semana.accenture.models.CashBookModel;
import com.terceira.semana.accenture.models.ClientModel;
import com.terceira.semana.accenture.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CashBookMapper {


    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    private ClientRepository clientRepository;

    public CashBookDTO fromEntityToDTO (CashBookModel cBModel) {
        final CashBookDTO response = new CashBookDTO();

        response.setId(cBModel.getId());
        response.setIdClient(cBModel.getIdCliente().getId());
        response.setReleaseDate(cBModel.getDataLancamento().format(datePattern));
        response.setDescription(cBModel.getDescricao());
        response.setType(cBModel.getTipo());
        response.setValue(cBModel.getValor());


        return response;
    }

    public CashBookModel fromDTOToEntity (CashBookDTO cBDTO) {
        final CashBookModel response = new CashBookModel();

        if (cBDTO.getIdClient() != null) {
            Optional<ClientModel> client = this.clientRepository.findById(cBDTO.getIdClient());
            if(client.isPresent()){
                response.setIdCliente(client.get());
            } else {
                throw new ClientNotFoundException();
            }
        }
        if (cBDTO.getReleaseDate() != null && !cBDTO.getReleaseDate().isEmpty()) response.setDataLancamento(LocalDate.parse(cBDTO.getReleaseDate(),datePattern));
        if (cBDTO.getDescription() != null && !cBDTO.getDescription().isEmpty()) response.setDescricao(cBDTO.getDescription());
        if (cBDTO.getType() != null && !cBDTO.getType().isEmpty()) response.setTipo(cBDTO.getType());
        if (cBDTO.getValue() != null && !cBDTO.getValue().isNaN()) response.setValor(cBDTO.getValue());

        return response;

    }

    public List<CashBookDTO> fromEntityListToDTOList(List<CashBookModel> cashBookModels) {
        List<CashBookDTO> response = cashBookModels.stream().map(this::fromEntityToDTO).collect(Collectors.toList());

        if(response.size() > 0){
            CashBookDTO status = new CashBookDTO();
            status.setMessage("CashBooks founded successfully!");
            status.setStatusCode(200);
            response.add(0, status);
            return response;
        } else {
            CashBookDTO status = new CashBookDTO();
            status.setMessage("CashBooks Not Founded");
            status.setStatusCode(404);
            response.add(0, status);
            return response;
        }
    }

}
