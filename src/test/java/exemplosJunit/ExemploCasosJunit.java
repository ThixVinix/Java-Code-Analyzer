package exemplosJunit;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ExemploCasosJunit {

	/** Equivalente � 10 segundos (tempo em milisegundos) */
//	private static final long TEMPO_LIMITE_EXECUCAO = 10000l;

	/**
	 * A anota��o "@BeforeAll" � executada antes dos testes da classe serem
	 * executados.
	 * 
	 * Este m�todo pode inicializar recursos necess�rios para realia��o dos testes.
	 * 
	 * O m�todo deve ser public, static, void e sem par�metros.
	 */
	@BeforeAll
	public static void inicializar() {
		// Inicializa��o de recursos.
	}

	/**
	 * A anota��o "@AfterAll" � usada no m�todo que deve ser executado depois dos
	 * testes da classe serem executados.
	 * 
	 * Este m�todo pode destruir recursos abertos pelo m�todo anotado com
	 * "@BeforeClass".
	 * 
	 * O m�todo dever public, static, void e sem par�metros.
	 */
	@AfterAll
	public static void destruir() {
		// Destrui��o de recursos.
	}

	/**
	 * M�todo anotas com "@BeforeEach" s�o executados toda vez que um novo teste da
	 * classe est� prestes a ser executado.
	 * 
	 * O m�todo deve ser public, void e sem par�metros
	 */
	@BeforeEach
	public void executarAntesDoTeste() {
		// Inicializa��o antes de executar cada teste.
		System.out.println("Iniciando o teste");
	}

	/**
	 * M�todo anotas com "@AfterEach" s�o executados toda vez que um novo teste da
	 * classe termina de ser executado.
	 * 
	 * O m�todo deve ser public, void e sem par�metros
	 */
	@AfterEach
	public void executarDepoisDoTeste() {
		// Destrui��o depois de executar cada teste.
		System.out.println("Teste finalizado");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	/**
	 * Se o m�todo deve lan�ar uma exce��o, deve especificar qual exce��o � essa.
	 * 
	 * Se a exce��o especificada n�o for lan�ada, o m�todo falha.
	 */
//	@Test(expected = Exception.class)
	public void test2() throws Exception {
		throw new Exception();
	}

	/**
	 * Indica quanto tempo o m�todo pode levar para executar no m�ximo. Se este
	 * tempo for ultrapassado, o teste falha.
	 */
	@Test
	public void test3() {
		assertTimeout(ofMillis(10), () -> {
			Thread.sleep(100);
		});
	}

//	@Test(expected = Exception.class, timeout = TEMPO_LIMITE_EXECUCAO)
	public void test4() {
		fail("Not yet implemented");
	}

	/**
	 * Ignora o caso de teste em espec�fico temporariamente. o {@code @Disabled}
	 * tamb�m pode ser utilizado na classe de testes.
	 */
	@Test
	@Disabled
	public void test5() {
		fail("Not yet implemented");
	}

	/**
	 * Funcionalidades:
	 * 
	 * fail([message]): pode ser usado para verificar se uma determinada parte do
	 * c�digo n�o � atingida ou para ter um teste com falha antes que o c�digo de
	 * teste seja implementado. O par�metro da mensagem � opcional.
	 * 
	 * assertTrue([message,] boolean condition): verifica se a condi��o booleana �
	 * verdadeira.
	 * 
	 * assertFalse([message,] boolean condition): verifica se a condi��o booleana �
	 * falsa.
	 * 
	 * assertEquals([message,] expected, actual): testa se dois valores (esperado e
	 * atual) s�o os mesmos. No caso de arrays, a verifica��o � em rela��o �
	 * refer�ncia e n�o ao conte�do.
	 * 
	 * assertEquals([message,] expected, actual, tolerance): testa se dois valores
	 * float ou double correspondem. A toler�ncia � o n�mero de casas decimais que
	 * devem ser consideradas na compara��o.
	 * 
	 * assertNull([message,] object): verifica se o objeto � nulo.
	 * 
	 * assertNotNull([message,] object): verifica se o objeto n�o � nulo.
	 * 
	 * assertSame([message,] expected, actual): verifica se ambas as vari�veis se
	 * referem ao mesmo objeto.
	 * 
	 * assertNotSame([message,] expected, actual): verifica se ambas as vari�veis se
	 * referem a objetos diferentes.
	 */

}
