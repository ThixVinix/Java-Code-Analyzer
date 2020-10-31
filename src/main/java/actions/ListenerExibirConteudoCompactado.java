package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaPesquisaArquivo;

public class ListenerExibirConteudoCompactado implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerExibirConteudoCompactado(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {
		if (tela.radioButtonConteudoCompactado.isSelected()) {
			tela.radioButtonConteudoCompactado.setEnabled(false);
			tela.radioButtonConteudoCompleto.setSelected(false);
			tela.radioButtonConteudoCompleto.setEnabled(true);
			tela.radioButtonConteudoFormatado.setSelected(false);
			tela.radioButtonConteudoFormatado.setEnabled(true);
			tela.textAreaConteudo.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getConteudoCompactado());
			tela.textAreaConteudo.repaint();
		}
	}

}