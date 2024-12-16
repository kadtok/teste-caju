package com.caju.efetivador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.caju.efetivador.dao.ContaDao;
import com.caju.efetivador.dao.EstabelecimentoDao;
import com.caju.efetivador.dao.OperacaoDao;
import com.caju.efetivador.model.Conta;
import com.caju.efetivador.model.EfetivadorRequest;
import com.caju.efetivador.model.EfetivadorResponse;
import com.caju.efetivador.model.StatusTransacao;
import com.caju.efetivador.service.EfetivadorService;

@SpringBootTest
public class EfetivadorServiceTests {
	@Mock
	private OperacaoDao operacaoDao;

	@Mock
	private ContaDao contaDao;

	@Mock
	private EstabelecimentoDao estabelecimentoDao;

	@InjectMocks
	private EfetivadorService efetivadorService;

	private String idConta = "123";
	private String mccDefault = "5411";

	@BeforeEach
	public void beforeEach() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testaEfetivacaoTipoOperacaoEstabelecimentoCadastrado() throws Exception {
		Conta contaPadrao = new Conta(idConta, 100f, 100f, 100f);
		when(estabelecimentoDao.getMccByNomeEstabelecimento("Estabelecimento teste")).thenReturn("MEAL");
		when(operacaoDao.getTipoOperacaoByMcc(mccDefault)).thenReturn("FOOD");
		when(contaDao.getContaById(idConta)).thenReturn(contaPadrao);

		EfetivadorRequest request = new EfetivadorRequest(idConta, 100f, mccDefault, "Estabelecimento teste");
		EfetivadorResponse response = efetivadorService.efetivaCompra(request);

		assertEquals(StatusTransacao.APROVADA.getValue(), response.getCode());

		verify(contaDao).salvaConta(contaPadrao);
	}

	@Test
	public void testaEfetivacaoBemSucedida() throws Exception {
		Conta contaPadrao = new Conta(idConta, 100f, 100f, 100f);
		when(operacaoDao.getTipoOperacaoByMcc(mccDefault)).thenReturn("FOOD");
		when(contaDao.getContaById(idConta)).thenReturn(contaPadrao);
		EfetivadorRequest request = new EfetivadorRequest(idConta, 100f, mccDefault, "Mercadinho X");
		EfetivadorResponse response = efetivadorService.efetivaCompra(request);

		assertEquals(StatusTransacao.APROVADA.getValue(), response.getCode());

		verify(contaDao).salvaConta(contaPadrao);
	}

	@Test
	public void testaEfetivacaoBemSucedidaFallback() throws Exception {
		Conta contaPadrao = new Conta(idConta, 100f, 100f, 100f);

		when(operacaoDao.getTipoOperacaoByMcc(mccDefault)).thenReturn("FOOD");
		when(contaDao.getContaById(idConta)).thenReturn(contaPadrao);
		EfetivadorRequest request = new EfetivadorRequest(idConta, 150f, mccDefault, "Mercadinho X");
		EfetivadorResponse response = efetivadorService.efetivaCompra(request);

		assertEquals(StatusTransacao.APROVADA.getValue(), response.getCode());

		verify(contaDao).salvaConta(contaPadrao);
	}

	@Test
	public void testaEfetivacaoMalSucedida() throws Exception {
		Conta contaPadrao = new Conta(idConta, 100f, 100f, 100f);

		when(operacaoDao.getTipoOperacaoByMcc(mccDefault)).thenReturn("FOOD");
		when(contaDao.getContaById(idConta)).thenReturn(contaPadrao);
		EfetivadorRequest request = new EfetivadorRequest(idConta, 10000f, mccDefault, "Mercadinho X");
		EfetivadorResponse response = efetivadorService.efetivaCompra(request);

		assertEquals(StatusTransacao.REJEITADA.getValue(), response.getCode());

		verify(contaDao).salvaConta(contaPadrao);
	}

	@Test
	public void testaEfetivacaoErroAleatorio() throws Exception {
		when(operacaoDao.getTipoOperacaoByMcc(mccDefault)).thenReturn("FOOD");
		when(contaDao.getContaById(idConta)).thenReturn(null);
		EfetivadorRequest request = new EfetivadorRequest(idConta, 100f, mccDefault, "Mercadinho X");
		EfetivadorResponse response = efetivadorService.efetivaCompra(request);

		assertEquals(StatusTransacao.ERRO_DESCONHECIDO.getValue(), response.getCode());
	}
}
