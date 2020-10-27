package exemplosJunit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*Test Suites:
 * 
 * Test suites agrupam diversos "test cases" de classes de testes existentes no projeto.
 * 
 * Facilitam a execu��o. Pois executam todas as classes de teste declaradas no "@SuiteClasses".
 * 
 * N�o h� necessidade de implementa��o para a classe "Suit"*/
@RunWith(Suite.class)
@SuiteClasses({ ExemploCasosJunit.class })
public class ExemploSuit {

}
