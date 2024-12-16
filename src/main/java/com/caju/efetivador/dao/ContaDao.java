package com.caju.efetivador.dao;

import org.springframework.stereotype.Repository;

import com.caju.efetivador.model.Conta;

@Repository
public class ContaDao {

	public Conta getContaById(String conta) throws Exception {
		try {
			return new Conta(conta, 1000f, 1000f, 1000f);
		} catch (Exception e) {
			throw e;
		}
	}

	public void salvaConta(Conta conta) throws Exception {
		try {
			// salva
		} catch (Exception e) {
			throw e;
		}

	}

}
