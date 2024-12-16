package com.caju.efetivador.dao;

import org.springframework.stereotype.Repository;

@Repository
public class EstabelecimentoDao {

	public String getMccByNomeEstabelecimento(String estabelecimento) throws Exception {
		if (estabelecimento == "Estabelecimento Teste MCC")
			return "5811";
		else
			return null;
	}
}
