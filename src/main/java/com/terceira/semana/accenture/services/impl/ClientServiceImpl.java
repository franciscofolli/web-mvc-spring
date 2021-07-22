package com.terceira.semana.accenture.services.impl;

import ch.qos.logback.core.net.server.Client;
import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.exceptions.ClientNotFoundException;
import com.terceira.semana.accenture.mappers.ClientMapper;
import com.terceira.semana.accenture.models.ClientModel;
import com.terceira.semana.accenture.repositories.ClientRepository;
import com.terceira.semana.accenture.services.ClientService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.criterion.LikeExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.http.HttpClient;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;


    @Override
    public ClientDTO createNewClient(ClientDTO clientDTO) {
        try {
            final ClientModel client = this.clientMapper.fromDTOToEntity(clientDTO);
            this.clientRepository.save(client);
            clientDTO.setMessage("Success - New client created successfully!");
            clientDTO.setStatusCode(201);
            return clientDTO;
        } catch (ConstraintViolationException e) {
            List<String> invalidFields = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            clientDTO.setMessage("Error - " + invalidFields.stream().map(value -> value+" ").collect(Collectors.joining(",")));
            clientDTO.setStatusCode(400);
            return clientDTO;
        } catch (Exception e) {
            clientDTO.setMessage("Error - New client cannot be created!");
            clientDTO.setStatusCode(500);
            return clientDTO;
        }
    }

    @Override
    public ResponseDTO alterClientData(int id, ClientDTO clientDTO) {
        try {
            final ClientModel clientMapped = this.clientMapper.fromDTOToEntity(clientDTO);
            final Optional<ClientModel> clientModel = this.clientRepository.findById(id);


            if(clientModel.isPresent()) {
                if (clientMapped.getNome() != null && !clientMapped.getNome().isEmpty()) clientModel.get().setNome(clientMapped.getNome());
                if (clientMapped.getCpfCnpj() != null && !clientMapped.getCpfCnpj().isEmpty()) clientModel.get().setCpfCnpj(clientMapped.getCpfCnpj());
                if (clientMapped.getLogradouro() != null && !clientMapped.getLogradouro().isEmpty()) clientModel.get().setLogradouro(clientMapped.getLogradouro());
                if (clientMapped.getCidade() != null && !clientMapped.getCidade().isEmpty()) clientModel.get().setCidade(clientMapped.getCidade());
                if (clientMapped.getUf() != null && !clientMapped.getUf().isEmpty()) clientModel.get().setUf(clientMapped.getUf());
                if (clientMapped.getCep() != null && !clientMapped.getCep().isEmpty()) clientModel.get().setCep(clientMapped.getCep());
                if (clientMapped.getTelefone() != null && !clientMapped.getTelefone().isEmpty()) clientModel.get().setTelefone(clientMapped.getTelefone());
                if (clientMapped.getEmail() != null && !clientMapped.getEmail().isEmpty()) clientModel.get().setEmail(clientMapped.getEmail());

                this.clientRepository.save(clientModel.get());
            } else {
                throw new ClientNotFoundException();
            }

            final ResponseDTO response = new ResponseDTO();
            response.setMessage("Success - Client data updated successfully!");
            response.setStatusCode(200);
            return response;

        } catch (ClientNotFoundException e) {
            final ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - Client to be updated not found!");
            response.setStatusCode(404);
            return response;
        }
        catch (Exception e) {
            final ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - Client data could not be updated!");
            response.setStatusCode(400);
            return response;
        }

    }

    @Override
    public ResponseDTO deleteClient(int id) {
        try {
            this.clientRepository.deleteById(id);
            ResponseDTO response = new ResponseDTO();
            response.setMessage("Client Deleted Successfully!");
            response.setStatusCode(200);
            return response;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            ResponseDTO response = new ResponseDTO();
            response.setMessage("Client not Found");
            response.setStatusCode(404);
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            ResponseDTO response = new ResponseDTO();
            response.setMessage("Error - Client could not be deleted.");
            response.setStatusCode(500);
            return response;
        }
    }

    @Override
    public ClientDTO findClient(int id) {
        try {
            final Optional<ClientModel> client = this.clientRepository.findById(id);
            if(client.isPresent()){
                final ClientDTO response = this.clientMapper.fromEntityToDTO(client.get());
                response.setMessage("Client Founded Successfully!");
                response.setStatusCode(200);
                return response;
            } else {
                throw new ClientNotFoundException();
            }

        } catch (ClientNotFoundException e) {
            final ClientDTO response = new ClientDTO();
            response.setMessage(e.getMessage());
            response.setStatusCode(404);
            return response;
        }
    }

    @Override
    public List<ClientDTO> listAllClients(String nome, String cpfCnpj, String cidade, String uf) {
        final ClientModel model = new ClientModel();

        model.setNome(nome);
        model.setCpfCnpj(cpfCnpj);
        model.setCidade(cidade);
        model.setUf(uf);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        final Example<ClientModel> modelExample = Example.of(model,matcher);


        return this.clientMapper.fromEntityListToDTOList(this.clientRepository.findAll(modelExample));

    }

    @Override
    public ResponseDTO exportAllClients(String nome, String cpfCnpj, String cidade, String uf, String reportFormat) {
        final ClientModel model = new ClientModel();
        String path = "C:\\Reports";

        model.setNome(nome);
        model.setCpfCnpj(cpfCnpj);
        model.setCidade(cidade);
        model.setUf(uf);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        final Example<ClientModel> modelExample = Example.of(model,matcher);

        List<ClientDTO> response = this.clientMapper.fromEntityListToDTOList(this.clientRepository.findAll(modelExample));


        response.remove(0); // Removendo ResponseDTO que coloquei no topo da lista
        try {

            File file = ResourceUtils.getFile("classpath:clientList.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(response);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Created By", "Francisco Folli");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters , dataSource);
            if(reportFormat.equalsIgnoreCase("html")){
                JasperExportManager.exportReportToHtmlFile(jasperPrint, path+"\\clientList.html");
            }
            if(reportFormat.equalsIgnoreCase("pdf")){
                JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\clientList.pdf");
            }
            return new ResponseDTO("report generated in path:"+path, 200);
        } catch (FileNotFoundException | JRException e) {
            e.printStackTrace();
            return new ResponseDTO(e.getMessage(), 500);
        }



    }
}
