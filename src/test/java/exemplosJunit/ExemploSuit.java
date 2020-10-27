package exemplosJunit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*Test Suites:
 * 
 * Test suites agrupam diversos "test cases" de classes de testes existentes no projeto.
 * 
 * Facilitam a execução. Pois executam todas as classes de teste declaradas no "@SuiteClasses".
 * 
 * Não há necessidade de implementação para a classe "Suit"*/
@RunWith(Suite.class)
@SuiteClasses({ ExemploCasosJunit.class })
public class ExemploSuit {

}
