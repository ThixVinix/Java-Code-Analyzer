package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaPesquisaArquivo;

public class ListenerExibirConteudoFormatado implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerExibirConteudoFormatado(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {
		if (tela.radioButtonConteudoFormatado.isSelected()) {
			tela.radioButtonConteudoFormatado.setEnabled(false);
			tela.radioButtonConteudoCompleto.setSelected(false);
			tela.radioButtonConteudoCompleto.setEnabled(true);
			tela.radioButtonConteudoCompactado.setSelected(false);
			tela.radioButtonConteudoCompactado.setEnabled(true);
			tela.textAreaConteudo.setText(tela.arquivoMetricaBean.arquivoPesquisado.getConteudoFormatado());
			tela.textAreaConteudo.repaint();
		}
	}

}