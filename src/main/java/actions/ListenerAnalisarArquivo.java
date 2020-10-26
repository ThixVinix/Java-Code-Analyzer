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

public class ListenerAnalisarArquivo implements ActionListener {

	private TelaPesquisaArquivo tela;

	public ListenerAnalisarArquivo(TelaPesquisaArquivo telaPesquisaArquivo) {
		this.tela = telaPesquisaArquivo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ValidacaoArquivoEnum validacaoDiretorio;
		ValidacaoArquivoEnum validacaoEntidadesDeusas;

		validacaoDiretorio = tela.arquivoMetricaBean.validarDiretorio(tela.campoDiretorio.getText().trim());

		if (validacaoDiretorio != ValidacaoArquivoEnum.SUCESSO) {
			switch (validacaoDiretorio) {
			case ERRO_CONVERSAO_DIRETORIO:
			case ARQUIVO_NAO_SUPORTADO:
				tela.exibirMensagemErro(tela.mapValidations.get(validacaoDiretorio));
				break;
			default:
				tela.exibirMensagemAlerta(tela.mapValidations.get(validacaoDiretorio));
			}
			return;
		}

		Path path = DirectoryUtil.obterPathPeloDiretorioString(tela.campoDiretorio.getText().trim());

		validacaoEntidadesDeusas = tela.arquivoMetricaBean.validarCamposEntidadesDeusas(
				tela.campoLimiteMetodoDeus.getText(), tela.campoLimiteClasseDeus.getText());

		if (tela.checkBoxEntidadeDeus.isSelected() && validacaoEntidadesDeusas != ValidacaoArquivoEnum.SUCESSO) {
			tela.exibirMensagemAlerta(tela.mapValidations.get(validacaoEntidadesDeusas));
			return;
		}

		if (tela.campoDiretorio.getText().trim().equalsIgnoreCase(tela.arquivoAnteriorAnalisado)
				&& tela.campoLimiteMetodoDeus.getText().trim().equals(tela.limiteMetodoDeusAnterior)
				&& tela.campoLimiteClasseDeus.getText().trim().equals(tela.limiteClasseDeusAnterior)) {
			tela.exibirMensagemInformativa(tela.mapValidations.get(ValidacaoArquivoEnum.ARQUIVO_ANALISADO));
			return;
		}

		atualizarRadioButtonsConteudoArquivo();

		File file = DirectoryUtil.obterArquivoPorDiretorio(path);
		Integer campoLimiteMetodoDeus = Integer.parseInt(tela.campoLimiteMetodoDeus.getText());
		Integer campoLimiteClasseDeus = Integer.parseInt(tela.campoLimiteClasseDeus.getText());

		tela.arquivoMetricaBean = new ArquivoMetricaBean();

		tela.arquivoMetricaBean.arquivoPesquisado.setCaminho(path);
		tela.arquivoMetricaBean.arquivoPesquisado.setArquivo(file);

		try {
			tela.arquivoMetricaBean.lerConteudoArquivoAlvo(file, campoLimiteMetodoDeus, campoLimiteClasseDeus);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		tela.arquivoAnteriorAnalisado = tela.campoDiretorio.getText().trim();
		tela.limiteMetodoDeusAnterior = tela.campoLimiteMetodoDeus.getText().trim();
		tela.limiteClasseDeusAnterior = tela.campoLimiteClasseDeus.getText().trim();

		tela.arquivoMetricaBean.salvarMetricas();

		atualizarMetricasTela();

		exibirMetricasAtualizadas();

		tela.botaoLimpar.setEnabled(true);
		tela.comboBoxGrafico.setEnabled(true);
		tela.botaoGerarGrafico.setEnabled(true);

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
		tela.textAreaConteudo.setText(tela.arquivoMetricaBean.arquivoPesquisado.getConteudoCompleto());

		tela.campoCaminho.setText(tela.arquivoMetricaBean.arquivoPesquisado.obterNomeDiretorio());

		tela.campoPastasAcessadas
				.setText(tela.arquivoMetricaBean.arquivoPesquisado.obterQtdDiretoriosPassados().toString());

		tela.campoArquivo.setText(tela.arquivoMetricaBean.arquivoPesquisado.obterNomeArquivo());

		tela.campoLOC.setText(tela.arquivoMetricaBean.arquivoPesquisado.getQtdLoc().toString());

		tela.campoClasses.setText(tela.arquivoMetricaBean.arquivoPesquisado.getQtdClasses().toString());

		tela.campoMetodos.setText(tela.arquivoMetricaBean.arquivoPesquisado.getQtdMetodos().toString());

		tela.campoComentarios.setText(tela.arquivoMetricaBean.arquivoPesquisado.getQtdComentarios().toString());

		tela.campoMetodoDeus.setText(tela.arquivoMetricaBean.arquivoPesquisado.getQtdMetodoDeus().toString());

		tela.campoClasseDeus.setText(tela.arquivoMetricaBean.arquivoPesquisado.getQtdClasseDeus().toString());
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
