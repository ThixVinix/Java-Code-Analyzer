package utilitarios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	private Util() {
	}

	/**
	 * Verifica nulidade de um objeto ou se uma String é vazia.
	 */
	public static boolean isNotNullOrEmpty(Object pObj) {

		if (pObj == null) {
			return false;
		}

		if (pObj instanceof String) {
			return !((String) pObj).trim().equals("");
		}

		if (pObj instanceof Object[]) {
			Object[] array = (Object[]) pObj;
			if (array.length > 0) {
				boolean vazio = false;
				for (int i = 0; i < array.length; i++) {
					if (array[i] != null) {
						vazio = true;
						break;
					}
				}
				return vazio;
			} else {
				return false;
			}

		}

		return true;
	}

	/**
	 * Verifica nulidade de um objeto ou se um String é vazia.
	 */
	public static boolean isNullOrEmpty(Object pObj) {

		if (pObj == null) {
			return true;
		}

		if (pObj instanceof String) {
			return ((String) pObj).trim().equals("");
		}

		if (pObj instanceof Object[]) {
			Object[] array = (Object[]) pObj;
			if (array.length > 0) {
				boolean vazio = true;
				for (int i = 0; i < array.length; i++) {
					if (array[i] != null) {
						vazio = false;
						break;
					}
				}
				return vazio;
			} else {
				return true;
			}

		}

		return false;
	}

	/**
	 * Verifica se um numero do tipo "Long" é zero ou nulo.
	 * 
	 * @param pNumber
	 * @return true or false
	 */
	public static boolean isNullOrZeroNumber(Long pNumber) {
		return pNumber == null || pNumber == 0l;
	}

	/**
	 * Verifica se um numero do tipo "Double" é zero ou nulo.
	 * 
	 * @param pNumber
	 * @return true or false
	 */
	public static boolean isNullOrZeroNumber(Double pNumber) {
		return pNumber == null || pNumber == 0d;
	}

	/**
	 * Verifica se um numero do tipo "Integer" é zero ou nulo.
	 * 
	 * @param pNumber
	 * @return true or false
	 */
	public static boolean isNullOrZeroNumber(Integer pNumber) {
		return pNumber == null || pNumber == 0;
	}

	/**
	 * Verifica se um o número do tipo "Long" não é zero ou não é nulo.
	 * 
	 * @param pNumber
	 * @return true or false
	 */
	public static boolean isNotNullOrZeroNumber(Long pNumber) {
		return !isNullOrZeroNumber(pNumber);
	}

	/**
	 * Verifica se um o número do tipo "Double" não é zero ou não é nulo.
	 * 
	 * @param pNumber
	 * @return true or false
	 */
	public static boolean isNotNullOrZeroNumber(Double pNumber) {
		return !isNullOrZeroNumber(pNumber);
	}

	/**
	 * Verifica se um o número do tipo "Integer" não é zero ou não é nulo.
	 * 
	 * @param pNumber
	 * @return true or false
	 */
	public static boolean isNotNullOrZeroNumber(Integer pNumber) {
		return !isNullOrZeroNumber(pNumber);
	}

	/**
	 * Verifica se uma string é um valor númerico
	 * 
	 * @param valor String
	 * @return boolean
	 */
	public static boolean isNumerico(String valor) {
		boolean valido = true;

		if (isNullOrEmpty(valor)) {
			return false;
		}

		for (int i = 0; i < valor.length(); i++) {
			Character caractere = valor.charAt(i);
			if (!Character.isDigit(caractere)) {
				valido = false;
				break;
			}
		}
		return valido;
	}

	/**
	 * Verificar a quantidade minima de palavras dentro da string
	 * 
	 * @param texto       - texto a ser analizado
	 * @param qtdPalavras - quantidade minima de palavras necessarias
	 * @return -flag
	 */
	public static boolean verificarQuantidadeMinimaPalavras(String texto, int qtdPalavras) {

		boolean qtdMinimaAtingida = false;

		if (Util.isNotNullOrEmpty(texto) && !texto.isBlank()) {
			String[] tokens = texto.split(" ");
			if (tokens.length < qtdPalavras) {
				return qtdMinimaAtingida;
			} else {
				qtdMinimaAtingida = true;
			}

		}

		return qtdMinimaAtingida;
	}

	/**
	 * Insere quebras de linha após determinado número de caracteres em sequência
	 * 
	 * @param linha
	 * @param limite
	 * @return
	 */
	public static String quebraLinha(String linha, Integer limite) {
		if (linha.length() > limite) {

			Pattern p = Pattern.compile("\\w{" + (limite + 1) + "}");
			Matcher m = p.matcher(linha);

			if (m.find(0)) {
				int breakPos = m.start();
				return quebraLinha(linha.substring(0, breakPos) + linha.substring(breakPos, breakPos + limite)
						+ System.getProperty("line.separator") + linha.substring(breakPos + limite, linha.length()),
						limite);
			}
		}
		return linha;
	}

	/**
	 * Completar a string com zeros a esquerda para atingir o tamanho desejado.
	 * 
	 * @param s
	 * @param tamanho
	 * @return
	 */
	public static String getStringComZerosEsquerda(String s, int tamanho) {
		if (s.isBlank()) {
			s = "";
		}
		int tam = tamanho - s.length();
		if (tam <= 0) {
			s = s.substring(0, tamanho).trim();
			tam = tamanho - s.length();
		}
		String palavra = s;
		for (int i = 1; i <= tam; i++) {
			palavra = "0" + palavra;
		}
		return palavra;

	}

	/**
	 * Verifica se a string tem o tamanho minimo desejado
	 * 
	 * @param texto
	 * @param tamanhoMinimo
	 * @return
	 */
	public static boolean verificarTamanhoMinimo(String texto, int tamanhoMinimo) {

		boolean tamanhoMinimoAtingido = false;
		if (!texto.isBlank() && texto.trim().length() >= tamanhoMinimo) {
			tamanhoMinimoAtingido = true;
		}
		return tamanhoMinimoAtingido;
	}

	public static Long converterStringEmLong(String numero, int tamanhoMaximo) {
		Long numeroConvertido = null;

		if (!numero.isBlank()) {

			String numeroAConverter = "";
			if (!numero.isBlank()) {
				if (numero.trim().length() > tamanhoMaximo) {
					numeroAConverter = numero.trim().substring(0, tamanhoMaximo);
				} else {
					numeroAConverter = numero;
				}

				try {
					numeroConvertido = Long.parseLong(numeroAConverter);
				} catch (NumberFormatException e) {
				}

			}
		}

		return numeroConvertido;
	}

	/**
	 * metodo criado para concatenar valores a esquerda
	 * 
	 * @param str     string a ser concatenada
	 * @param padChar caracter que será inserido a esquerda
	 * @param length  tamanho maximo que string deve ter
	 * @return
	 */
	public static String padLeft(String str, char padChar, int length) {
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() < length) {
			sb.insert(0, padChar);
		}
		return sb.toString();
	}

	/**
	 * metodo criado para concatenar valores a direita
	 * 
	 * @param str     string a ser concatenada
	 * @param padChar caracter que será inserido a esquerda
	 * @param length  tamanho maximo que string deve ter
	 * @return
	 */
	public static String padRigth(String str, char padChar, int length) {
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() < length) {
			sb.insert(sb.length(), padChar);
		}
		return sb.toString();
	}

	/**
	 * Retira todos os caracteres que não são números.
	 * 
	 * @param texto String
	 * @return String
	 */
	public static String somenteNumero(String texto) {
		String novoTexto = "";

		// Caso seja nulo ou vazio retorna em branco.
		if (texto == null || texto.equals("")) {
			return novoTexto;
		}
		// Percorre o texto caractere a caractere.
		for (int i = 0; i < texto.length(); i++) {
			Character caractere = texto.charAt(i);

			// Se o caractere for número.
			if (Character.isDigit(caractere)) {
				novoTexto += texto.charAt(i);
			}
		}
		return novoTexto;
	}

	/**
	 * Verifica se a string está vazia caso contrário retorna a string com trim e
	 * uppercase.
	 * 
	 * @param pParametro
	 * @return String
	 */
	public static String getStringTrimUpercase(String pParametro) {
		if (isNullOrEmpty(pParametro)) {
			return "";
		}
		pParametro = pParametro.trim();
		pParametro = pParametro.toUpperCase();

		return pParametro;
	}

	/**
	 * Verifica se a string está vazia caso contrário retorna a string com trim e
	 * lowercase.
	 * 
	 * @param pParametro
	 * @return String
	 */
	public static String getStringTrimLowerCase(String pParametro) {
		if (isNullOrEmpty(pParametro)) {
			return "";
		}
		return pParametro.trim().toLowerCase();
	}

}
