package br.com.caelum.leilao.infra.repository;

import br.com.caelum.leilao.dominio.Leilao;

public interface EnviadorDeEmail {
	void envia(Leilao leilao);
}
