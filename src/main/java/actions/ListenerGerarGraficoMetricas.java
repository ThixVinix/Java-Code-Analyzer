package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import enums.ValidacaoArquivoEnum;
import telas.Grafico;
import telas.TelaPesquisaArquivo;

public class ListenerGerarGraficoMetricas implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerGerarGraficoMetricas(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {
		tela.grafico = new Grafico(tela.arquivoMetricaBean.getArquivoPesquisado().obterNomeArquivo(),
				tela.arquivoMetricaBean.getArquivoPesquisado().getQtdLoc().doubleValue(),
				tela.arquivoMetricaBean.getArquivoPesquisado().getQtdClasses().doubleValue(),
				tela.arquivoMetricaBean.getArquivoPesquisado().getQtdMetodos().doubleValue(),
				tela.arquivoMetricaBean.getArquivoPesquisado().getQtdComentarios().doubleValue());

		switch (tela.comboBoxGrafico.getSelectedIndex()) {
		case 1:
			tela.grafico.criarGraficoPizza();
			break;
		case 2:
			tela.grafico.criarGraficoBarrasHorizontal();
			break;
		case 3:
			tela.grafico.criarGraficoBarrasVertical();
			break;
		default:
			tela.exibirMensagemAlerta(ValidacaoArquivoEnum.MESSAGE_SELECIONE_GRAFICO.getDescricao());
		}
	}

}
