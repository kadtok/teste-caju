package com.caju.efetivador.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caju.efetivador.dao.ContaDao;
import com.caju.efetivador.dao.EstabelecimentoDao;
import com.caju.efetivador.dao.OperacaoDao;
import com.caju.efetivador.model.Conta;
import com.caju.efetivador.model.EfetivadorRequest;
import com.caju.efetivador.model.EfetivadorResponse;
import com.caju.efetivador.model.StatusTransacao;

@Service
public class EfetivadorService {
	@Autowired
	OperacaoDao operacaoDao;

	@Autowired
	ContaDao contaDao;

	@Autowired
	EstabelecimentoDao estabelecimentoDao;

	public EfetivadorResponse efetivaCompra(EfetivadorRequest request) {
		try {
			String tipoOperacao = validarOperacao(request);
			Conta conta = contaDao.getContaById(request.getAccount());
			
			String codigoRetorno = conta.pagar(request.getTotalAmount(), tipoOperacao);
			contaDao.salvaConta(conta);

			return new EfetivadorResponse(codigoRetorno);
		} catch (Exception e) {
			return new EfetivadorResponse(StatusTransacao.ERRO_DESCONHECIDO.getValue());
		}
	}

	private String validarOperacao(EfetivadorRequest request) throws Exception {
		String operacao = estabelecimentoDao.getMccByNomeEstabelecimento(request.getMerchant());

		// se não tem definição para o estabelecimento usa do payload
		if (operacao == null)
			operacao = operacaoDao.getTipoOperacaoByMcc(request.getMcc());

		// se não tem nem no estabelecimento e não está cadastrado no banco, vai para o
		// padrão (cash)
		if (operacao == null)
			operacao = "CASH";

		return operacao;
	}
}
