package com.terceira.semana.accenture.services.impl;

import com.terceira.semana.accenture.dtos.*;
import com.terceira.semana.accenture.exceptions.CashBookNotFoundException;
import com.terceira.semana.accenture.exceptions.ClientNotFoundException;
import com.terceira.semana.accenture.mappers.AccountServiceMapper;
import com.terceira.semana.accenture.mappers.CashBookMapper;
import com.terceira.semana.accenture.models.CashBookModel;
import com.terceira.semana.accenture.models.ClientModel;
import com.terceira.semana.accenture.repositories.CashBookRepository;
import com.terceira.semana.accenture.repositories.ClientRepository;
import com.terceira.semana.accenture.services.CashBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CashBookImpl implements CashBookService {

    @Autowired
    private CashBookRepository cashBookRepository;

    @Autowired
    private CashBookMapper cashBookMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountServiceMapper accountServiceMapper;

    private final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public CashBookDTO createNewCashBook(CashBookDTO cashBookDTO) {
        try {
            final CashBookModel cashBookModel = this.cashBookMapper.fromDTOToEntity(cashBookDTO);
            this.cashBookRepository.save(cashBookModel);
            cashBookDTO.setMessage("Success - New CashBook created successfully!");
            cashBookDTO.setStatusCode(201);
            return cashBookDTO;
        } catch (ConstraintViolationException e) {
            List<String> invalidFields = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            cashBookDTO.setMessage("Error - " + invalidFields.stream().map(value -> value+" ").collect(Collectors.joining(",")));
            cashBookDTO.setStatusCode(400);
            return cashBookDTO;
        } catch (Exception e) {
            cashBookDTO.setMessage("Error - New CashBook cannot be created!");
            cashBookDTO.setStatusCode(400);
            return cashBookDTO;
        }
    }

    @Override
    public ResponseDTO alterCashBookData(Integer id, CashBookDTO cashBookDTO) {
        try {
            final CashBookModel cashBookMapped = this.cashBookMapper.fromDTOToEntity(cashBookDTO);
            final Optional<CashBookModel> cashBookModel = this.cashBookRepository.findById(id);

            if(cashBookModel.isPresent()) {
                if (cashBookMapped.getIdCliente() != null) cashBookModel.get().setIdCliente(cashBookMapped.getIdCliente());
                if (cashBookMapped.getDataLancamento() != null) cashBookModel.get().setDataLancamento(cashBookMapped.getDataLancamento());
                if (cashBookMapped.getDescricao() != null && !cashBookMapped.getDescricao().isEmpty()) cashBookModel.get().setDescricao(cashBookMapped.getDescricao());
                if (cashBookMapped.getTipo() != null && !cashBookMapped.getTipo().isEmpty()) cashBookModel.get().setTipo(cashBookMapped.getTipo());
                if (cashBookMapped.getValor() != null && !cashBookMapped.getValor().isNaN()) cashBookModel.get().setValor(cashBookMapped.getValor());

                this.cashBookRepository.save(cashBookModel.get());
            } else {
                throw new CashBookNotFoundException();
            }

            final ResponseDTO response = new ResponseDTO();
            response.setMessage("Success - CashBook data updated successfully!");
            response.setStatusCode(200);
            return response;

        } catch (CashBookNotFoundException e) {
            final ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - CashBook to be updated not found!");
            response.setStatusCode(404);
            return response;
        }
        catch (Exception e) {
            final ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - CashBook data could not be updated!");
            response.setStatusCode(400);
            return response;
        }
    }

    @Override
    public ResponseDTO deleteCashBook(Integer id) {
        try {
            this.cashBookRepository.deleteById(id);
            ResponseDTO response = new ResponseDTO();
            response.setMessage("CashBook Deleted Successfully!");
            response.setStatusCode(200);
            return response;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            ResponseDTO response = new ResponseDTO();
            response.setMessage("CashBook not Found");
            response.setStatusCode(404);
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - CashBook could not be deleted.");
            response.setStatusCode(500);
            return response;
        }
    }

    @Override
    public CashBookDTO findCashBook(Integer id) {
        try {
            final Optional<CashBookModel> cashBookModel = this.cashBookRepository.findById(id);
            if(cashBookModel.isPresent()){
                final CashBookDTO response = this.cashBookMapper.fromEntityToDTO(cashBookModel.get());
                response.setMessage("CashBook Founded Successfully!");
                response.setStatusCode(200);
                return response;
            } else {
                throw new CashBookNotFoundException();
            }

        } catch (CashBookNotFoundException e) {
            final CashBookDTO response = new CashBookDTO();
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return response;
        }
    }

    @Override
    public List<CashBookDTO> listAllCashBooks(Integer idCliente) {
        final CashBookModel model = new CashBookModel();

        if (idCliente != null) {
            final Optional<ClientModel> clientModel = this.clientRepository.findById(idCliente);

            if(clientModel.isPresent()){
                return this.cashBookMapper.fromEntityListToDTOList(this.cashBookRepository.findByIdCliente(clientModel.get()));
            } else {
                throw new ClientNotFoundException();
            }
        } else {
            return this.cashBookMapper.fromEntityListToDTOList(this.cashBookRepository.findAll());
        }

    }

    @Override
    public List<CashBookDTO> listAllCashBooksBetween(Integer idCliente, LocalDate datainicial, LocalDate dataFinal) {
        final CashBookModel model = new CashBookModel();

        if (idCliente != null) {
            final Optional<ClientModel> clientModel = this.clientRepository.findById(idCliente);

            if(clientModel.isPresent()){
                return this.cashBookMapper.fromEntityListToDTOList(this.cashBookRepository.findByIdClienteAndDataLancamentoBetween(clientModel.get(),datainicial,dataFinal));
            } else {
                throw new ClientNotFoundException();
            }
        } else {
            return this.cashBookMapper.fromEntityListToDTOList(new ArrayList<>());
        }

    }

    @Override
    public List<AccountServiceDTO> ListContService(Integer idCliente, String dataInicial, String dataFinal){
        try{
            LocalDate convertedInitialDate = LocalDate.parse(dataInicial,datePattern);
            LocalDate convertedFinalDate = LocalDate.parse(dataFinal,datePattern);
            final Optional<ClientModel> clientModel = this.clientRepository.findById(idCliente);

            if(clientModel.isPresent()){
                List<CashBookDTO> cashBookDTOList = listAllCashBooksBetween(idCliente,convertedInitialDate,convertedFinalDate);
                cashBookDTOList.remove(0); // removendo response que é colocado por padrão
                return this.accountServiceMapper.toContabilList(cashBookDTOList, clientModel.get());
            } else {
                throw new ClientNotFoundException();
            }
        }  catch (ClientNotFoundException e) {
            final  List<AccountServiceDTO> response = new ArrayList<>();
            response.add(new AccountServiceDTO());
            response.get(0).setMessage(e.getMessage());
            response.get(0).setStatusCode(404);
            return response;
        }
    }
}
