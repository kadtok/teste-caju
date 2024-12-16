package com.caju.efetivador.model;

public enum StatusTransacao {
	REJEITADA("51"), APROVADA("00"), ERRO_DESCONHECIDO("07");

	private final String value;

	StatusTransacao(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
