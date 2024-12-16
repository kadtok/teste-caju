package com.caju.efetivador.dao;

import org.springframework.stereotype.Repository;

@Repository
public class OperacaoDao {

	public String getTipoOperacaoByMcc(String mcc) throws Exception {
		try {
			switch (mcc) {
			case "5411":
				return "FOOD";
			case "5412":
				return "FOOD";
			case "5811":
				return "MEAL";
			case "5812":
				return "MEAL";
			default:
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
