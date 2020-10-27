package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bean.ArquivoMetricaBean;
import telas.TelaPesquisaArquivo;
import utilitarios.Constante;

public class ListenerLimpar implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerLimpar(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {
		tela.botaoLimpar.setEnabled(false);
		tela.comboBoxGrafico.setSelectedIndex(0);
		tela.comboBoxGrafico.setEnabled(false);
		tela.botaoGerarGrafico.setEnabled(false);

		tela.radioButtonConteudoCompleto.setSelected(true);
		tela.radioButtonConteudoCompleto.setVisible(false);
		tela.radioButtonConteudoFormatado.setSelected(false);
		tela.radioButtonConteudoFormatado.setVisible(false);
		tela.radioButtonConteudoCompactado.setSelected(false);
		tela.radioButtonConteudoCompactado.setVisible(false);

		tela.checkBoxEntidadeDeus.setSelected(false);

		tela.arquivoAnteriorAnalisado = "";
		tela.limiteClasseDeusAnterior = "";
		tela.limiteMetodoDeusAnterior = "";

		tela.arquivoMetricaBean = new ArquivoMetricaBean();

		tela.campoDiretorio.setText("");
		tela.textAreaConteudo.setText("");
		tela.campoCaminho.setText("");
		tela.campoPastasAcessadas.setText("");
		tela.campoArquivo.setText("");
		tela.campoLOC.setText("");
		tela.campoClasses.setText("");
		tela.campoMetodos.setText("");
		tela.campoComentarios.setText("");
		tela.campoMetodoDeus.setText("");
		tela.campoClasseDeus.setText("");
		tela.campoLimiteMetodoDeus.setText(Constante.QTD_MIN_LINHAS_METODO_DEUS.toString());
		tela.campoLimiteClasseDeus.setText(Constante.QTD_MIN_LINHAS_CLASSE_DEUS.toString());

		tela.campoDiretorio.repaint();
		tela.textAreaConteudo.repaint();
		tela.campoCaminho.repaint();
		tela.campoPastasAcessadas.repaint();
		tela.campoArquivo.repaint();
		tela.campoLOC.repaint();
		tela.campoClasses.repaint();
		tela.campoMetodos.repaint();
		tela.campoComentarios.repaint();
		tela.campoMetodoDeus.repaint();
		tela.campoClasseDeus.repaint();
		tela.campoLimiteMetodoDeus.repaint();
		tela.campoLimiteClasseDeus.repaint();
	}

}
