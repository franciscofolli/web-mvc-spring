package com.terceira.semana.accenture.controllers;

import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.dtos.UserDTO;
import com.terceira.semana.accenture.services.ClientService;
import com.terceira.semana.accenture.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<ResponseDTO> createNewClient(@Valid @RequestBody ClientDTO clientDTO){
        final ClientDTO response = this.clientService.createNewClient(clientDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<ResponseDTO> alterClient(@PathVariable("id") Integer id, @RequestBody ClientDTO clientDTO){
        final ResponseDTO response = this.clientService.alterClientData(id,clientDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<ResponseDTO> deleteClient(@PathVariable("id") Integer id) {
        ResponseDTO response = this.clientService.deleteClient(id);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));

    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ResponseDTO> getClient(@PathVariable("id") Integer id) {
        final ClientDTO response = this.clientService.findClient(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDTO>> getClientsList(@RequestParam(value = "nome", required = false) String nome,
                                        @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
                                        @RequestParam(value = "cidade", required = false) String cidade,
                                        @RequestParam(value = "uf", required = false) String uf
    ) {
        List<ClientDTO> response = this.clientService.listAllClients(nome,cpfCnpj,cidade,uf);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.get(0).getStatusCode()));
    }

    @GetMapping("/exportClients/{fileType}")
    public ResponseEntity<ResponseDTO> exportClientsList(@RequestParam(value = "nome", required = false) String nome,
                                                             @RequestParam(value = "cpfCnpj", required = false) String cpfCnpj,
                                                             @RequestParam(value = "cidade", required = false) String cidade,
                                                             @RequestParam(value = "uf", required = false) String uf,
                                                             @PathVariable("fileType") String fileType
    ) {
        ResponseDTO response = this.clientService.exportAllClients(nome,cpfCnpj,cidade,uf,fileType);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }


}
