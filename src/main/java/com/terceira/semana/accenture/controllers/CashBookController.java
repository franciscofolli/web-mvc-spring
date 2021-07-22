package com.terceira.semana.accenture.controllers;

import com.terceira.semana.accenture.dtos.AccountServiceDTO;
import com.terceira.semana.accenture.dtos.CashBookDTO;
import com.terceira.semana.accenture.dtos.ClientDTO;
import com.terceira.semana.accenture.dtos.ResponseDTO;
import com.terceira.semana.accenture.services.CashBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CashBookController {

    @Autowired
    private CashBookService cashBookService;


    @PostMapping("/cashBook")
    public ResponseEntity<ResponseDTO> createNewCashBook(@Valid @RequestBody CashBookDTO cashBookDTO){
        final CashBookDTO response = this.cashBookService.createNewCashBook(cashBookDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/cashBook/{id}")
    public ResponseEntity<ResponseDTO> alterCashBook(@PathVariable("id") Integer id, @RequestBody CashBookDTO cashBookDTO){
        final ResponseDTO response = this.cashBookService.alterCashBookData(id,cashBookDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/cashBook/{id}")
    public ResponseEntity<ResponseDTO> deleteCashBook(@PathVariable("id") Integer id) {
        ResponseDTO response = this.cashBookService.deleteCashBook(id);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));

    }

    @GetMapping("/cashBook/{id}")
    public ResponseEntity<ResponseDTO> getCashBook(@PathVariable("id") Integer id) {
        final CashBookDTO response = this.cashBookService.findCashBook(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/cashBooks")
    public ResponseEntity<List<CashBookDTO>> getCashBooksList(@RequestParam(value = "idCliente", required = false) Integer idCliente) {
        List<CashBookDTO> response = this.cashBookService.listAllCashBooks(idCliente);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.get(0).getStatusCode()));
    }

    @GetMapping("/contabil")
    public ResponseEntity<List<AccountServiceDTO>> getServicosContabeis(@RequestParam(value = "idCliente") Integer idCliente,
                                                             @RequestParam(value = "dataInicial") String dataInicial,
                                                             @RequestParam(value = "dataFinal") String dataFinal){
        List<AccountServiceDTO> response = this.cashBookService.ListContService(idCliente, dataInicial, dataFinal);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.get(0).getStatusCode()));
    }

}
