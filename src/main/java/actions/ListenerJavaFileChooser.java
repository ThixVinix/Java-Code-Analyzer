package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import telas.TelaPesquisaArquivo;
import utilitarios.Constante;

public class ListenerJavaFileChooser implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerJavaFileChooser(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		FileNameExtensionFilter filtro;
		
		filtro = new FileNameExtensionFilter("Arquivos \"" + Constante.ARQUIVO_TIPO_JAVA + "\"", "java");

		tela.choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
		tela.choose.setAcceptAllFileFilterUsed(false);
		tela.choose.addChoosableFileFilter(filtro);

		String caminhoArquivo = "";

		int retorno = tela.choose.showOpenDialog(null);

		if (retorno == JFileChooser.APPROVE_OPTION) {
			caminhoArquivo = tela.choose.getSelectedFile().getAbsolutePath();
			tela.campoDiretorio.resetKeyboardActions();
			tela.campoDiretorio.setText(caminhoArquivo);
			tela.campoDiretorio.repaint();

		}
	}

}
