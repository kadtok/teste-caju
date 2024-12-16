package com.caju.efetivador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caju.efetivador.model.EfetivadorRequest;
import com.caju.efetivador.model.EfetivadorResponse;
import com.caju.efetivador.service.EfetivadorService;

@RestController
@RequestMapping("/efetivadorcartao")
public class EfetivadorController {

	@Autowired
	EfetivadorService efetivadorService;
	
	@PostMapping("/efetivartransacao")
	public ResponseEntity<EfetivadorResponse> efetivaCompraCartao(@RequestBody EfetivadorRequest request) {
		EfetivadorResponse response = efetivadorService.efetivaCompra(request);
		return ResponseEntity.ok(response);
	}
	
}
