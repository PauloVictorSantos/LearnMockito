package br.com.caelum.leilao.servico;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.repository.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.repository.RepositorioDePagamentos;
import static junit.framework.Assert.*;

import static org.mockito.Mockito.*;

import java.util.Arrays;

public class GeradorDePagamentoTest {

	@Test
	public void deveGerarPagamentoParaUmLeilaoEncerrado() {
		RepositorioDeLeiloes leiloes = mock(RepositorioDeLeiloes.class);
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);

        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation")
                .lance(new Usuario("José da Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();
        when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
        GeradorDePagamento gerador = 
                new GeradorDePagamento(leiloes, pagamentos, new Avaliador());
            gerador.gera();


		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salvar(argumento.capture());
		Pagamento pagamentoGerado = argumento.getValue();
		assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);
	}
}
