package bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import abstracts.AbstractArquivoMetrica;
import enums.ValidacaoArquivoEnum;
import interfaces.InterfaceArquivoMetrica;
import utilitarios.Constante;
import utilitarios.DirectoryUtil;
import utilitarios.Util;

public class ArquivoMetricaBean extends AbstractArquivoMetrica implements InterfaceArquivoMetrica {

	private static final Logger LOGGER = LogManager.getLogger(ArquivoMetricaBean.class.getName());

	public ArquivoMetricaBean() {
		super();
	}

	public ValidacaoArquivoEnum validarDiretorio(String diretorioString) {
		LOGGER.info("Validando campo do diretório...");

		if (Util.isNullOrEmpty(diretorioString)) {
			LOGGER.info(Constante.PREENCHIMENTO_OBRIGATORIO_DIRETORIO);
			return ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_DIRETORIO;
		}

		Path caminho = DirectoryUtil.obterPathPeloDiretorioString(diretorioString.trim());

		if (Util.isNullOrEmpty(caminho)) {
			LOGGER.info(Constante.ERRO_CONVERSAO_DIRETORIO);
			return ValidacaoArquivoEnum.ERRO_CONVERSAO_DIRETORIO;
		}
		if (!DirectoryUtil.validarDiretorioCompleto(caminho)) {
			LOGGER.info(Constante.DIRETORIO_NAO_ENCONTRADO);
			return ValidacaoArquivoEnum.DIRETORIO_NAO_ENCONTRADO;
		}

		if (!DirectoryUtil.isJavaFile(caminho.toFile())) {
			LOGGER.info(Constante.TIPO_ARQUIVO_INCORRETO);
			return ValidacaoArquivoEnum.TIPO_ARQUIVO_INCORRETO;
		}

		LOGGER.info("Validação do diretório efetuada com sucesso.");
		return ValidacaoArquivoEnum.SUCESSO;
	}

	@Override
	public ValidacaoArquivoEnum validarCamposEntidadesDeusas(String qtdMinMetodoDeusString,
			String qtdMinClasseDeusString) {
		LOGGER.info("Validando campos das entidades deusas...");

		if (Util.isNullOrEmpty(qtdMinMetodoDeusString)) {
			LOGGER.info(Constante.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS);
			return ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS;
		}

		if (Util.isNullOrEmpty(qtdMinClasseDeusString)) {
			LOGGER.info(Constante.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS);
			return ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS;
		}
		if (!Util.isNumerico(qtdMinMetodoDeusString.trim())) {
			LOGGER.info(Constante.PREENCHIMENTO_INCORRETO_METODO_DEUS);
			return ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS;
		}
		if (!Util.isNumerico(qtdMinClasseDeusString.trim())) {
			LOGGER.info(Constante.PREENCHIMENTO_INCORRETO_CLASSE_DEUS);
			return ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS;
		}

		LOGGER.info("Validação das entidades deusas efetuada com sucesso.");
		return ValidacaoArquivoEnum.SUCESSO;
	}

	@Override
	public void iniciarAnalise(File arquivo, Integer limiteMinMetodoDeus, Integer limiteMinClasseDeus)
			throws IOException {

		if (Util.isNullOrEmpty(limiteMinMetodoDeus) || limiteMinMetodoDeus < Constante.NUMBER_ZERO_INT)
			limiteMinMetodoDeus = Constante.NUMBER_ZERO_INT;

		if (Util.isNullOrEmpty(limiteMinClasseDeus) || limiteMinClasseDeus < Constante.NUMBER_ZERO_INT)
			limiteMinClasseDeus = Constante.NUMBER_ZERO_INT;

		if (Util.isNullOrEmpty(arquivo)) {
			LOGGER.warn("Arquivo para análise foi passado como nulo.");
			salvarMetricas();
			return;
		}

		BufferedReader arquivoReader = DirectoryUtil.lerArquivo(arquivo);

		if (Util.isNullOrEmpty(arquivoReader)) {
			LOGGER.warn("Não é possível realizar a leitura linha à linha do arquivo.");
			salvarMetricas();
			return;
		}

		String line;

		String message = String.format("Iniciando análise das métricas do arquivo: \"%s\"...", arquivo.getName());

		LOGGER.info(message);
		while ((line = arquivoReader.readLine()) != null) {
			analisarLinhaAtual(line);
		}
		LOGGER.info("Análise das métricas finalizada.");

		arquivoReader.close();

		LOGGER.info("Iniciando análise de métodos deuses...");
		qtdMetodoDeus = verificarEntidadesDeusas(limiteMinMetodoDeus, true);
		LOGGER.info("Análise de métodos deuses finalizada.");

		LOGGER.info("Iniciando análise de classes deusas...");
		qtdClasseDeus = verificarEntidadesDeusas(limiteMinClasseDeus, false);
		LOGGER.info("Análise de classes deusas finalizada.");

		salvarMetricas();

		resetarMetricasProvisorias();

	}

}