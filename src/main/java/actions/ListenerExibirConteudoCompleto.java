package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaPesquisaArquivo;

public class ListenerExibirConteudoCompleto implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerExibirConteudoCompleto(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {
		if (tela.radioButtonConteudoCompleto.isSelected()) {
			tela.radioButtonConteudoCompleto.setEnabled(false);
			tela.radioButtonConteudoFormatado.setSelected(false);
			tela.radioButtonConteudoFormatado.setEnabled(true);
			tela.radioButtonConteudoCompactado.setSelected(false);
			tela.radioButtonConteudoCompactado.setEnabled(true);
			tela.textAreaConteudo.setText(tela.arquivoMetricaBean.arquivoPesquisado.getConteudoCompleto());
			tela.textAreaConteudo.repaint();
		}
	}
}