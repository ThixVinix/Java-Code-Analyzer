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

	/** Equivalente à 10 segundos (tempo em milisegundos) */
//	private static final long TEMPO_LIMITE_EXECUCAO = 10000l;

	/**
	 * A anotação "@BeforeAll" é executada antes dos testes da classe serem
	 * executados.
	 * 
	 * Este método pode inicializar recursos necessários para realiação dos testes.
	 * 
	 * O método deve ser public, static, void e sem parâmetros.
	 */
	@BeforeAll
	public static void inicializar() {
		// Inicialização de recursos.
	}

	/**
	 * A anotação "@AfterAll" é usada no método que deve ser executado depois dos
	 * testes da classe serem executados.
	 * 
	 * Este método pode destruir recursos abertos pelo método anotado com
	 * "@BeforeClass".
	 * 
	 * O método dever public, static, void e sem parâmetros.
	 */
	@AfterAll
	public static void destruir() {
		// Destruição de recursos.
	}

	/**
	 * Método anotas com "@BeforeEach" são executados toda vez que um novo teste da
	 * classe está prestes a ser executado.
	 * 
	 * O método deve ser public, void e sem parâmetros
	 */
	@BeforeEach
	public void executarAntesDoTeste() {
		// Inicialização antes de executar cada teste.
		System.out.println("Iniciando o teste");
	}

	/**
	 * Método anotas com "@AfterEach" são executados toda vez que um novo teste da
	 * classe termina de ser executado.
	 * 
	 * O método deve ser public, void e sem parâmetros
	 */
	@AfterEach
	public void executarDepoisDoTeste() {
		// Destruição depois de executar cada teste.
		System.out.println("Teste finalizado");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	/**
	 * Se o método deve lançar uma exceção, deve especificar qual exceção é essa.
	 * 
	 * Se a exceção especificada não for lançada, o método falha.
	 */
//	@Test(expected = Exception.class)
	public void test2() throws Exception {
		throw new Exception();
	}

	/**
	 * Indica quanto tempo o método pode levar para executar no máximo. Se este
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
	 * Ignora o caso de teste em específico temporariamente. o {@code @Disabled}
	 * também pode ser utilizado na classe de testes.
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
	 * código não é atingida ou para ter um teste com falha antes que o código de
	 * teste seja implementado. O parâmetro da mensagem é opcional.
	 * 
	 * assertTrue([message,] boolean condition): verifica se a condição booleana é
	 * verdadeira.
	 * 
	 * assertFalse([message,] boolean condition): verifica se a condição booleana é
	 * falsa.
	 * 
	 * assertEquals([message,] expected, actual): testa se dois valores (esperado e
	 * atual) são os mesmos. No caso de arrays, a verificação é em relação à
	 * referência e não ao conteúdo.
	 * 
	 * assertEquals([message,] expected, actual, tolerance): testa se dois valores
	 * float ou double correspondem. A tolerância é o número de casas decimais que
	 * devem ser consideradas na comparação.
	 * 
	 * assertNull([message,] object): verifica se o objeto é nulo.
	 * 
	 * assertNotNull([message,] object): verifica se o objeto não é nulo.
	 * 
	 * assertSame([message,] expected, actual): verifica se ambas as variáveis se
	 * referem ao mesmo objeto.
	 * 
	 * assertNotSame([message,] expected, actual): verifica se ambas as variáveis se
	 * referem a objetos diferentes.
	 */

}
