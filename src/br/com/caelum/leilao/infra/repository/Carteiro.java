package br.com.caelum.leilao.infra.repository;

import br.com.caelum.leilao.dominio.Leilao;

public interface Carteiro {
	void envia(Leilao leilao);
}
