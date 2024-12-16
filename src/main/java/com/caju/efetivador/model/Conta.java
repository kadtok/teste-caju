package com.caju.efetivador.model;

public class Conta {
	private String idConta;
	private Float valorFood;
	private Float valorMeal;
	private Float valorCash;

	public String getIdConta() {
		return idConta;
	}

	public void setIdConta(String idConta) {
		this.idConta = idConta;
	}

	public Float getValorFood() {
		return valorFood;
	}

	public void setValorFood(Float valorFood) {
		this.valorFood = valorFood;
	}

	public Float getValorMeal() {
		return valorMeal;
	}

	public void setValorMeal(Float valorMeal) {
		this.valorMeal = valorMeal;
	}

	public Float getValorCash() {
		return valorCash;
	}

	public void setValorCash(Float valorCash) {
		this.valorCash = valorCash;
	}

	public Conta(String idConta, Float valorFood, Float valorMeal, Float valorCash) {
		this.idConta = idConta;
		this.valorFood = valorFood;
		this.valorMeal = valorMeal;
		this.valorCash = valorCash;
	}

	/**
	 * remove valor da conta de acordo com a categoria, caso a categoria não seja CASH, 
	 * e o valor exceder o disponível na categoria, o restante será debitado do valor disponível geral (CASH)
	 * @param valor
	 * @param categoria
	 * @return
	 */
	public String pagar(Float valor, String categoria) {

		switch (categoria) {
		case "FOOD":
			return pagarFood(valor);
		case "MEAL":
			return pagarMeal(valor);
		default:
			return pagarCash(valor);
		}
	}

	public String pagarFood(Float valor) {
		if (this.valorFood >= valor) {
			this.valorFood -= valor;
			return StatusTransacao.APROVADA.getValue();
		}

		if (this.valorCash + this.valorFood >= valor) {
			this.valorFood = 0f;
			this.valorCash -= valor - this.valorFood;
			return StatusTransacao.APROVADA.getValue();
		}

		return StatusTransacao.REJEITADA.getValue();

	}

	public String pagarMeal(Float valor) {
		if (this.valorMeal >= valor) {
			this.valorMeal -= valor;
			return StatusTransacao.APROVADA.getValue();
		} else {
			if (this.valorCash + this.valorMeal >= valor) {
				this.valorMeal = 0f;
				this.valorCash -= valor - this.valorMeal;
				return StatusTransacao.APROVADA.getValue();
			} else {
				return StatusTransacao.REJEITADA.getValue();
			}
		}
	}

	public String pagarCash(Float valor) {
		if (this.valorCash >= valor) {
			this.valorCash -= valor;
			return StatusTransacao.APROVADA.getValue();
		} else {
			return StatusTransacao.REJEITADA.getValue();
		}
	}
}
