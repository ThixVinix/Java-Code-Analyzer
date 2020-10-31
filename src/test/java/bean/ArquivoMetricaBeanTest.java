package bean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import enums.ValidacaoArquivoEnum;
import utilitarios.DirectoryUtil;

@DisplayName("Testes da classe: ArquivoMetricaBean")
class ArquivoMetricaBeanTest {

	private static ArquivoMetricaBean arquivo;

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
	public static void inicializar() throws IOException {
		fileJavaVazio = DirectoryUtil.criarArquivo(DIRETORIO_NOME_ARQUIVO_JAVA);
		pathFileJava = fileJavaVazio.toPath();
		fileTxtVazio = DirectoryUtil.criarArquivo(DIRETORIO_NOME_ARQUIVO_TXT);
		pathFileTxt = fileTxtVazio.toPath();
		Path pathFileJavaComConteudo = DirectoryUtil.obterPathPeloDiretorioString(DIRETORIO_ARQUIVO_JAVA_TESTE);
		fileJavaComConteudo = DirectoryUtil.obterArquivoPorDiretorio(pathFileJavaComConteudo);
	}

	@BeforeEach
	public void executarAntesDoTeste() {
		arquivo = new ArquivoMetricaBean();
	}

	@AfterEach
	public void executarDepoisDoTeste() {
	}

	@AfterAll
	public static void finalizar() {
		fileJavaVazio.delete();
		fileTxtVazio.delete();
	}

	// --------------- Method validarDiretorio ---------------

	@DisplayName("Valida��o de um diret�rio(String) = null")
	@Test
	void validarDiretorioNulo() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(null);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_DIRETORIO, resultado);
	}

	@DisplayName("Valida��o de um diret�rio(String) = \"\" (vazio)")
	@Test
	void validarDiretorioVazio() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio("");
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_DIRETORIO, resultado);
	}

	@DisplayName("Valida��o de um diret�rio(String) que n�o pode ser convertido para \"Path\"")
	@Test
	void validarDiretorioForaDoPadrao() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(DIRETORIO_FORA_DO_PADRAO);
		assertEquals(ValidacaoArquivoEnum.ERRO_CONVERSAO_DIRETORIO, resultado);
	}

	@DisplayName("Valida��o de um diret�rio(String) que n�o existe")
	@Test
	void validarDiretorioArquivoNaoEncontrado() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(DIRETORIO_NAO_EXISTENTE);
		assertEquals(ValidacaoArquivoEnum.DIRETORIO_NAO_ENCONTRADO, resultado);
	}

	@DisplayName("Valida��o de um diret�rio(String) sem arquivo alvo")
	@Test
	void validarDiretorioArquivoIncompleto() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(DIRETORIO_INCOMPLETO);
		assertEquals(ValidacaoArquivoEnum.ARQUIVO_NAO_SUPORTADO, resultado);
	}

	@DisplayName("Valida��o de um diret�rio(String) com arquivo alvo n�o suportado (.txt)")
	@Test
	void validarDiretorioArquivoNaoSuportado() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(pathFileTxt.toString());
		assertEquals(ValidacaoArquivoEnum.ARQUIVO_NAO_SUPORTADO, resultado);
	}

	@DisplayName("Valida��o de um diret�rio(String) com arquivo alvo suportado (.java)")
	@Test
	void validarDiretorioCorreto() {
		ValidacaoArquivoEnum resultado = arquivo.validarDiretorio(pathFileJava.toString());
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	// ---------------------------------------------------------------------------------------------

	// --------------- Method validarCamposEntidadesDeusas ---------------

	@DisplayName("Valida��o passando a Qtd. Lim. Min. m�todo deus = null")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteNulo() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(null, NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. m�todo deus = \"\" (vazio)")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteVazio() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(VAZIO, NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. classe deusa = null")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteNulo() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING, null);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. classe deusa = \"\" (vazio)")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteVazio() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING, VAZIO);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. m�todo deus = -1 (valor negativo)")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteNegativo() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_NEGATIVE_STRING,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. classe deusa = -1 (valor negativo)")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteNegativo() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				NUMBER_ONE_NEGATIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. m�todo deus com letras")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteComLetras() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(LETTERS, NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. classe deusa com letras")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteComLetras() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING, LETTERS);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. m�todo deus com caracteres especiais")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteComCaracteresEspeciais() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(SPECIAL_CHARACTERS,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. classe deusa com caracteres especiais")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteComCaracteresEspeciais() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				SPECIAL_CHARACTERS);
		assertEquals(ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. m�todo deus = 0")
	@Test
	void validarCamposEntidadesDeusasMetodoLimiteZero() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ZERO_STRING,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. classe deusa = 0")
	@Test
	void validarCamposEntidadesDeusasClasseLimiteZero() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				NUMBER_ZERO_STRING);
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	@DisplayName("Valida��o passando a Qtd. Lim. Min. das entidades deusas com valores positivos")
	@Test
	void validarCamposEntidadesDeusasLimitesPositivos() {
		ValidacaoArquivoEnum resultado = arquivo.validarCamposEntidadesDeusas(NUMBER_ONE_POSITIVE_STRING,
				NUMBER_ONE_POSITIVE_STRING);
		assertEquals(ValidacaoArquivoEnum.SUCESSO, resultado);
	}

	// ---------------------------------------------------------------------------------------------

	// --------------- Method lerConteudoArquivoAlvo ---------------

	@DisplayName("An�lise do arquivo alvo passando a Qtd. Lim. Min. m�todo deus = 0 e aquivo(File) com conte�do")
	@Test
	void lerConteudoArquivoAlvoComConteudoMetodoLimiteZero() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaComConteudo, NUMBER_ZERO_INT, NUMBER_ONE_POSITIVE_INT);
		assertNotEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus());
	}

	@DisplayName("An�lise do arquivo alvo passando a Qtd. Lim. Min. classe deusa = 0 e aquivo(File) com conte�do")
	@Test
	void lerConteudoArquivoAlvoComConteudoClasseLimiteZero() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaComConteudo, NUMBER_ONE_POSITIVE_INT, NUMBER_ZERO_INT);
		assertNotEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus());
	}

	@DisplayName("An�lise do arquivo alvo passando a Qtd. Lim. Min. m�todo deus = 0 e aquivo(File) vazio")
	@Test
	void lerConteudoArquivoAlvoSemConteudoMetodoLimiteZero() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaVazio, NUMBER_ZERO_INT, NUMBER_ONE_POSITIVE_INT);
		assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus());
	}

	@DisplayName("An�lise do arquivo alvo passando a Qtd. Lim. Min. classe deusa = 0 e aquivo(File) vazio")
	@Test
	void lerConteudoArquivoAlvoSemConteudoClasseLimiteZero() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaVazio, NUMBER_ONE_POSITIVE_INT, NUMBER_ZERO_INT);
		assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus());
	}

	@DisplayName("An�lise do arquivo alvo passando a Qtd. Lim. Min. das entidades deusas com valores negativos")
	@Test
	void lerConteudoArquivoAlvoComConteudoEntidadesDeusasNegativas() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaVazio, NUMBER_ONE_NEGATIVE_INT, NUMBER_ONE_NEGATIVE_INT);
		assertAll(() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus()),
				() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus()));
	}

	@DisplayName("An�lise do arquivo alvo passando a Qtd. Lim. Min. das entidades deusas com valores = null")
	@Test
	void lerConteudoArquivoAlvoComConteudoEntidadesDeusasNulas() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaVazio, null, null);
		assertAll(() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdMetodoDeus()),
				() -> assertEquals(NUMBER_ZERO_LONG, arquivo.getArquivoPesquisado().getQtdClasseDeus()));
	}

	@DisplayName("An�lise do arquivo alvo = null")
	@Test
	void lerConteudoArquivoAlvoNulo() throws IOException {
		arquivo.lerConteudoArquivoAlvo(null, NUMBER_ONE_POSITIVE_INT, NUMBER_ONE_POSITIVE_INT);
		assertAll(() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompleto()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoFormatado()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompactado()));
	}

	@DisplayName("An�lise do arquivo alvo = \"\" (vazio)")
	@Test
	void lerConteudoArquivoAlvoVazio() throws IOException {
		arquivo.lerConteudoArquivoAlvo(fileJavaVazio, NUMBER_ONE_POSITIVE_INT, NUMBER_ONE_POSITIVE_INT);
		assertAll(() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompleto()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoFormatado()),
				() -> assertEquals(VAZIO, arquivo.getArquivoPesquisado().getConteudoCompactado()));
	}

}