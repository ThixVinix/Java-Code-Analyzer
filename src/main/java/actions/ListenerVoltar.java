package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaPesquisaArquivo;

public class ListenerVoltar implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerVoltar(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tela.setVisible(false);
		tela.frmAnaliseArquivo.resetKeyboardActions();
		tela.frameAnterior.setVisible(true);
	}

}
