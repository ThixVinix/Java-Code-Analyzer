package bean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enums.ValidacaoArquivoEnum;
import utilitarios.DirectoryUtil;
import utilitarios.Util;

@DisplayName("Testes da classe: ArquivoMetricaBean")
class ArquivoMetricaBeanTest {

	private static ArquivoMetricaBean arquivo;

	private static final Logger LOGGER = LogManager.getLogger(ArquivoMetricaBeanTest.class.getName());
	private static final String INITIALIZE_MESSAGE = "Inicializando teste...";
	
	private static final String DIRETORIO_FORA_DO_PADRAO = "()diretorio@fora$do*padrao&";
	private static final String DIRETORIO_NAO_EXISTENTE = "C:\\Users\\Public\\Documents\\nonexistentfile.java";
	private static final String DIRETORIO_INCOMPLETO = "C:\\Users\\Public\\Documents";
	private static final String DIRETORIO_NOME_ARQUIVO_JAVA = "C:\\Users\\Public\\Documents\\fileJunitTest.java";
	private static final String DIRETORIO_NOME_ARQUIVO_TXT = "C:\\Users\\Public\\Documents\\fileJunitTest.txt";
	private static final String DIRETORIO_ARQUIVO_JAVA_TESTE = "..\\Java-Code-Analyzer\\src\\test\\resources\\exemplosArquivosJavaTest\\Exemplo2.java";

	private static final String VAZIO = "";
	private static final String SPECIAL_CHARACTERS = "@#$%!";
	private static final String LETTERS = "abc";

	private static final String NUMBER_ZERO_STRING = "0";
	private static final String NUMBER_ONE_NEGATIVE_STRING = "-1";
	private static final String NUMBER_ONE_POSITIVE_STRING = "1";

	private static final Long NUMBER_ZERO_LONG = 0L;
	private static final Integer NUMBER_ZERO_INT = 0;
	private static final Integer NUMBER_ONE_NEGATIVE_INT = -1;
	private static final Integer NUMBER_ONE_POSITIVE_INT = 1;

	private static File fileJavaVazio;
	private static File fileTxtVazio;
	private static File fileJavaComConteudo;
	private static Path pathFileJava;
	private static Path pathFileTxt;

	@BeforeAll
	public static void inicializar() {
		LOGGER.info("Inicializando configurações da classe de teste " + ArquivoMetricaBean.class.getName() + "...");

		try {
			fileJavaVazio = DirectoryUtil.criarArquivo(DIRETORIO_NOME_ARQUIVO_JAVA);
			pathFileJava = DirectoryUtil.obterPathPeloDiretorioString(fileJavaVazio.toString());

			if (Util.isNullOrEmpty(pathFileJava))
				throw new NullPointerException();

			fileTxtVazio = DirectoryUtil.criarArquivo(DIRETORIO_NOME_ARQUIVO_TXT);
			pathFileTxt = DirectoryUtil.obterPathPeloDiretorioString(fileTxtVazio.toString());

			if (Util.isNullOrEmpty(pathFileTxt))
				throw new NullPointerException();

		} catch (NullPointerException e) {
			LOGGER.throwing(e);
		}

		Path pathFileJavaComConteudo = DirectoryUtil.obterPathPeloDiretorioString(DIRETORIO_ARQUIVO_JAVA_TESTE);
		fileJavaComConteudo = DirectoryUtil.obterArquivoPorDiretorio(pathFileJavaComConteudo);

	}

	@BeforeEach
	public void executarAntesDoTeste() {
		arquivo = new ArquivoMetricaBean();
	}

	@AfterEach
	public void executarDepoisDoTeste() {
	LOGGER.info("Teste finalizado com sucesso.");
	}

	@AfterAll
	public static void finalizar() {
		fileJavaVazio.delete();
		fileTxtVazio.delete();
	}

	// --------------- Method validarDiretorio ---------------

	@DisplayName("Validação de um diretório(String) = null")
	@Test
	void validarDiretorioNulo() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(null);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_DIRETORIO, resultado);
	}

	@DisplayName("Validação de um diretório(String) = \"\" (vazio)")
	@Test
	void validarDiretorioVazio() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio("");
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_DIRETORIO, resultado);
	}

	@DisplayName("Validação de um diretório(String) que não pode ser convertido para \"Path\"")
	@Test
	void validarDiretorioForaDoPadrao() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(DIRETORIO_FORA_DO_PADRAO);
		assertEquals(ValidacaoArquivoEnum.ERRO_CONVERSAO_DIRETORIO, resultado);
	}

	@DisplayName("Validação de um diretório(String) que não existe")
	@Test
	void validarDiretorioArquivoNaoEncontrado() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(DIRETORIO_NAO_EXISTENTE);
		assertEquals(ValidacaoArquivoEnum.DIRETORIO_NAO_ENCONTRADO, resultado);
	}

	@DisplayName("Validação de um diretório(String) sem arquivo alvo")
	@Test
	void validarDiretorioArquivoIncompleto() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(DIRETORIO_INCOMPLETO);
		assertEquals(ValidacaoArquivoEnum.TIPO_ARQUIVO_INCORRETO, resultado);
	}

	@DisplayName("Validação de um diretório(String) com arquivo alvo não suportado (.txt)")
	@Test
	void validarDiretorioArquivoNaoSuportado() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(pathFileTxt.toString());
		assertEquals(ValidacaoArquivoEnum.TIPO_ARQUIVO_INCORRETO, resultado);
	}

	@DisplayName("Validação de um diretório(String) com arquivo alvo suportado (.java)")
	@Test
	void validarDiretorioCorreto() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(pathFileJava.toString());
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	// ---------------------------------------------------------------------------------------------

	// --------------- Method validarCamposEntidadesDeusas ---------------

	@DisplayName("Validação passando a Qtd. Lim. Min. método deus = null")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteNulo() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(null, NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. método deus = \"\" (vazio)")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteVazio() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(VAZIO, NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. classe deusa = null")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteNulo() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING, null);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. classe deusa = \"\" (vazio)")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteVazio() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING, VAZIO);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. método deus = -1 (valor negativo)")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteNegativo() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_NEGATIVE_STRING,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. classe deusa = -1 (valor negativo)")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteNegativo() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				NUMBER_ONE_NEGATIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. método deus com letras")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteComLetras() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(LETTERS, NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. classe deusa com letras")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteComLetras() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING, LETTERS);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. método deus com caracteres especiais")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteComCaracteresEspeciais() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(SPECIAL_CHARACTERS,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. classe deusa com caracteres especiais")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteComCaracteresEspeciais() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				SPECIAL_CHARACTERS);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. método deus = 0")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteZero() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ZERO_STRING,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. classe deusa = 0")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteZero() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				NUMBER_ZERO_STRING);
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	@DisplayName("Validação passando a Qtd. Lim. Min. das entidades deusas com valores positivos")
	@Test
	void validarCamposEntidadesDeusasLimitesPositivos() {
		LOGGER.info(INITIALIZE_MESSAGE);
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	// ---------------------------------------------------------------------------------------------

	// --------------- Method iniciarAnalise ---------------

	@DisplayName("Análise do arquivo alvo passando a Qtd. Lim. Min. método deus = 0 e aquivo(File) com conteúdo")
	@Test
	void iniciarAnaliseArquivoAlvoComConteudoMetodoLimiteZero() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaComConteudo, NUMBER_ZERO_INT, NUMBER_ONE_POSITIVE_INT);
		assertNotEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus());
	}

	@DisplayName("Análise do arquivo alvo passando a Qtd. Lim. Min. classe deusa = 0 e aquivo(File) com conteúdo")
	@Test
	void iniciarAnaliseArquivoAlvoComConteudoClasseLimiteZero() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaComConteudo, NUMBER_ONE_POSITIVE_INT, NUMBER_ZERO_INT);
		assertNotEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus());
	}

	@DisplayName("Análise do arquivo alvo passando a Qtd. Lim. Min. método deus = 0 e aquivo(File) vazio")
	@Test
	void iniciarAnaliseArquivoAlvoSemConteudoMetodoLimiteZero() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaVazio, NUMBER_ZERO_INT, NUMBER_ONE_POSITIVE_INT);
		assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus());
	}

	@DisplayName("Análise do arquivo alvo passando a Qtd. Lim. Min. classe deusa = 0 e aquivo(File) vazio")
	@Test
	void iniciarAnaliseArquivoAlvoSemConteudoClasseLimiteZero() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaVazio, NUMBER_ONE_POSITIVE_INT, NUMBER_ZERO_INT);
		assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus());
	}

	@DisplayName("Análise do arquivo alvo passando a Qtd. Lim. Min. das entidades deusas com valores negativos")
	@Test
	void iniciarAnaliseArquivoAlvoComConteudoEntidadesDeusasNegativas() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaVazio, NUMBER_ONE_NEGATIVE_INT, NUMBER_ONE_NEGATIVE_INT);
		assertAll(() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus()),
				() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus()));
	}

	@DisplayName("Análise do arquivo alvo passando a Qtd. Lim. Min. das entidades deusas com valores = null")
	@Test
	void iniciarAnaliseArquivoAlvoComConteudoEntidadesDeusasNulas() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaVazio, null, null);
		assertAll(() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus()),
				() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus()));
	}

	@DisplayName("Análise do arquivo alvo = null")
	@Test
	void iniciarAnaliseArquivoAlvoNulo() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(null, NUMBER_ONE_POSITIVE_INT, NUMBER_ONE_POSITIVE_INT);
		assertAll(() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompleto()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoFormatado()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompactado()));
	}

	@DisplayName("Análise do arquivo alvo = \"\" (vazio)")
	@Test
	void iniciarAnaliseArquivoAlvoVazio() throws IOException {
		LOGGER.info(INITIALIZE_MESSAGE);
		arquivo.iniciarAnalise(fileJavaVazio, NUMBER_ONE_POSITIVE_INT, NUMBER_ONE_POSITIVE_INT);
		assertAll(() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompleto()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoFormatado()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompactado()));
	}

}