package br.com.caelum.leilao.servico;

import java.util.Calendar;
import java.util.List;
import javax.swing.event.CellEditorListener;

import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.infra.relogio.Relogio;
import br.com.caelum.leilao.infra.relogio.RelogioDoSistema;
import br.com.caelum.leilao.infra.repository.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.repository.RepositorioDePagamentos;

public class GeradorDePagamento {
	private RepositorioDeLeiloes leiloes;
	private Avaliador avaliador;
	private RepositorioDePagamentos pagamentos;
	private Relogio relogio;

	public GeradorDePagamento(RepositorioDeLeiloes leiloes, RepositorioDePagamentos pagamentos, Avaliador avaliador) {
		this(leiloes, pagamentos, avaliador, new RelogioDoSistema());
	}

	public GeradorDePagamento(RepositorioDeLeiloes leiloes, RepositorioDePagamentos pagamentos, Avaliador avaliador,
			Relogio relogio) {
		this.leiloes = leiloes;
		this.pagamentos = pagamentos;
		this.avaliador = avaliador;
		this.relogio = relogio;
	}

	public void gera() {
		List<Leilao> leiloesEncerrados = this.leiloes.encerrados();
		for (Leilao leilao : leiloesEncerrados) {
			this.avaliador.avalia(leilao);
			Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(), primeiroDiaUtil());
			this.pagamentos.salvar(novoPagamento);
		}
	}

	private Calendar primeiroDiaUtil() {
		Calendar data = relogio.hoje();
		int diaSemana = data.get(Calendar.DAY_OF_WEEK);
		if (diaSemana == Calendar.SATURDAY)
			data.add(Calendar.DAY_OF_MONTH, 2);
		else if (diaSemana == Calendar.SUNDAY)
			data.add(Calendar.DAY_OF_MONTH, 1);
		return data;
	}

}
