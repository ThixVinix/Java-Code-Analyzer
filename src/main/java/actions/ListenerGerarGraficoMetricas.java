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
		tela.grafico = new Grafico(tela.arquivoMetricaBean.arquivoPesquisado.obterNomeArquivo(),
				tela.arquivoMetricaBean.arquivoPesquisado.getQtdLoc().doubleValue(),
				tela.arquivoMetricaBean.arquivoPesquisado.getQtdClasses().doubleValue(),
				tela.arquivoMetricaBean.arquivoPesquisado.getQtdMetodos().doubleValue(),
				tela.arquivoMetricaBean.arquivoPesquisado.getQtdComentarios().doubleValue());

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
			tela.exibirMensagemAlerta(tela.mapValidations.get(ValidacaoArquivoEnum.MESSAGE_SELECIONE_GRAFICO));
		}
	}

}
