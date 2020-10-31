package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import bean.ArquivoMetricaBean;
import enums.ValidacaoArquivoEnum;
import telas.TelaPesquisaArquivo;
import utilitarios.DirectoryUtil;
import utilitarios.Util;

public class ListenerAnalisarArquivo implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerAnalisarArquivo(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	public void actionPerformed(ActionEvent e) {

		if (!validarDiretorio())
			return;

		if (!validarEntidadesDeusas())
			return;

		if (!compararAnaliseAnteriorComAtual())
			return;

		Path path = DirectoryUtil.obterPathPeloDiretorioString(tela.campoDiretorio.getText().trim());

		atualizarRadioButtonsConteudoArquivo();

		File file = DirectoryUtil.obterArquivoPorDiretorio(path);
		Integer campoLimiteMetodoDeus = Util.converterStringEmInteger(tela.campoLimiteMetodoDeus.getText());
		Integer campoLimiteClasseDeus = Util.converterStringEmInteger(tela.campoLimiteClasseDeus.getText());

		tela.arquivoMetricaBean = new ArquivoMetricaBean();

		tela.arquivoMetricaBean.salvarDiretorioAndArquivo(path, file);

		try {
			tela.arquivoMetricaBean.lerConteudoArquivoAlvo(file, campoLimiteMetodoDeus, campoLimiteClasseDeus);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		tela.arquivoAnteriorAnalisado = tela.campoDiretorio.getText().trim();
		tela.limiteMetodoDeusAnterior = tela.campoLimiteMetodoDeus.getText().trim();
		tela.limiteClasseDeusAnterior = tela.campoLimiteClasseDeus.getText().trim();

		atualizarMetricasTela();

		exibirMetricasAtualizadas();

		tela.botaoLimpar.setEnabled(true);
		tela.comboBoxGrafico.setEnabled(true);
		tela.botaoGerarGrafico.setEnabled(true);

	}

	private boolean validarDiretorio() {

		ValidacaoArquivoEnum validacaoDiretorio;

		validacaoDiretorio = tela.arquivoMetricaBean.validarDiretorio(tela.campoDiretorio.getText().trim());

		if (validacaoDiretorio != ValidacaoArquivoEnum.SUCESSO) {
			switch (validacaoDiretorio) {
			case ERRO_CONVERSAO_DIRETORIO:
			case ARQUIVO_NAO_SUPORTADO:
				tela.exibirMensagemErro(tela.arquivoMetricaBean.getMapValidations().get(validacaoDiretorio));
				break;
			default:
				tela.exibirMensagemAlerta(tela.arquivoMetricaBean.getMapValidations().get(validacaoDiretorio));
			}

			return false;
		}

		return true;
	}

	private boolean validarEntidadesDeusas() {

		ValidacaoArquivoEnum validacaoEntidadesDeusas;

		validacaoEntidadesDeusas = tela.arquivoMetricaBean.validarCamposEntidadesDeusas(
				tela.campoLimiteMetodoDeus.getText(), tela.campoLimiteClasseDeus.getText());

		if (tela.checkBoxEntidadeDeus.isSelected() && validacaoEntidadesDeusas != ValidacaoArquivoEnum.SUCESSO) {
			tela.exibirMensagemAlerta(tela.arquivoMetricaBean.getMapValidations().get(validacaoEntidadesDeusas));
			return false;
		}

		return true;
	}

	private boolean compararAnaliseAnteriorComAtual() {
		if (tela.campoDiretorio.getText().trim().equalsIgnoreCase(tela.arquivoAnteriorAnalisado)
				&& tela.campoLimiteMetodoDeus.getText().trim().equals(tela.limiteMetodoDeusAnterior)
				&& tela.campoLimiteClasseDeus.getText().trim().equals(tela.limiteClasseDeusAnterior)) {
			tela.exibirMensagemInformativa(tela.arquivoMetricaBean.getMapValidations().get(ValidacaoArquivoEnum.ARQUIVO_ANALISADO));
			return false;
		}

		return true;
	}

	private void atualizarRadioButtonsConteudoArquivo() {
		tela.radioButtonConteudoCompleto.setSelected(true);
		tela.radioButtonConteudoFormatado.setSelected(false);
		tela.radioButtonConteudoCompactado.setSelected(false);

		tela.radioButtonConteudoCompleto.setEnabled(false);
		tela.radioButtonConteudoFormatado.setEnabled(true);
		tela.radioButtonConteudoCompactado.setEnabled(true);

		tela.radioButtonConteudoCompleto.setVisible(true);
		tela.radioButtonConteudoFormatado.setVisible(true);
		tela.radioButtonConteudoCompactado.setVisible(true);
	}

	private void atualizarMetricasTela() {
		tela.textAreaConteudo.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getConteudoCompleto());

		tela.campoCaminho.setText(tela.arquivoMetricaBean.getArquivoPesquisado().obterNomeDiretorio());

		tela.campoPastasAcessadas
				.setText(tela.arquivoMetricaBean.getArquivoPesquisado().obterQtdDiretoriosPassados().toString());

		tela.campoArquivo.setText(tela.arquivoMetricaBean.getArquivoPesquisado().obterNomeArquivo());

		tela.campoLOC.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getQtdLoc().toString());

		tela.campoClasses.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getQtdClasses().toString());

		tela.campoMetodos.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getQtdMetodos().toString());

		tela.campoComentarios.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getQtdComentarios().toString());

		tela.campoMetodoDeus.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getQtdMetodoDeus().toString());

		tela.campoClasseDeus.setText(tela.arquivoMetricaBean.getArquivoPesquisado().getQtdClasseDeus().toString());
	}

	private void exibirMetricasAtualizadas() {
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
	}

}
