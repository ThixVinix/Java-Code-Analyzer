package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import telas.TelaPesquisaArquivo;
import utilitarios.Constante;

public class ListenerDefinirEntidadesDeus implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerDefinirEntidadesDeus(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {
		if (tela.checkBoxEntidadeDeus.isSelected()) {
			tela.campoLimiteMetodoDeus.setText("");
			tela.campoLimiteMetodoDeus.setEditable(true);
			tela.campoLimiteMetodoDeus.repaint();
			tela.campoLimiteClasseDeus.setText("");
			tela.campoLimiteClasseDeus.setEditable(true);
			tela.campoLimiteClasseDeus.repaint();
		} else {
			tela.campoLimiteMetodoDeus.setText(Constante.QTD_MIN_LINHAS_METODO_DEUS.toString());
			tela.campoLimiteMetodoDeus.setEditable(false);
			tela.campoLimiteMetodoDeus.repaint();
			tela.campoLimiteClasseDeus.setText(Constante.QTD_MIN_LINHAS_CLASSE_DEUS.toString());
			tela.campoLimiteClasseDeus.setEditable(false);
			tela.campoLimiteClasseDeus.repaint();
		}
	}
}
