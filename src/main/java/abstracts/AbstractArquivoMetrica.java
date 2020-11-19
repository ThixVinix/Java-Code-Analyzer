package abstracts;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.ArquivoMetrica;
import model.Auxiliar;
import utilitarios.Constante;
import utilitarios.Util;

public abstract class AbstractArquivoMetrica {

	private static final Logger LOGGER = LogManager.getLogger(AbstractArquivoMetrica.class.getName());

	protected Long qtdLinhasDeCodigo;
	protected Long qtdClasses;
	protected Long qtdMetodos;
	protected Long qtdComentarios;
	protected Long qtdMetodoDeus;
	protected Long qtdClasseDeus;
	protected ArquivoMetrica arquivoPesquisado;
	protected StringBuilder conteudoCompleto;
	protected StringBuilder conteudoFormatado;
	protected StringBuilder conteudoCompactado;
	protected boolean verificadorMultiComentario;
	protected String oldLine;

	public AbstractArquivoMetrica() {
		this.qtdLinhasDeCodigo = Constante.NUMBER_ZERO_LONG;
		this.qtdClasses = Constante.NUMBER_ZERO_LONG;
		this.qtdMetodos = Constante.NUMBER_ZERO_LONG;
		this.qtdComentarios = Constante.NUMBER_ZERO_LONG;
		this.qtdMetodoDeus = Constante.NUMBER_ZERO_LONG;
		this.qtdClasseDeus = Constante.NUMBER_ZERO_LONG;

		arquivoPesquisado = new ArquivoMetrica();
		getArquivoPesquisado().setQtdLoc(Constante.NUMBER_ZERO_LONG);
		getArquivoPesquisado().setQtdClasseDeus(Constante.NUMBER_ZERO_LONG);
		getArquivoPesquisado().setQtdMetodos(Constante.NUMBER_ZERO_LONG);
		getArquivoPesquisado().setQtdComentarios(Constante.NUMBER_ZERO_LONG);
		getArquivoPesquisado().setQtdClasseDeus(Constante.NUMBER_ZERO_LONG);
		getArquivoPesquisado().setQtdMetodoDeus(Constante.NUMBER_ZERO_LONG);

		conteudoCompleto = new StringBuilder();
		conteudoCompactado = new StringBuilder();
		conteudoFormatado = new StringBuilder();

		verificadorMultiComentario = false;
		oldLine = Constante.VAZIO;

	}

	public void analisarLinhaAtual(String line) {
		conteudoCompleto.append(line + Constante.QUEBRA_LINHA);

		if (line.trim().equals(Constante.VAZIO)) {
			if (!oldLine.trim().equals(Constante.VAZIO)) {
				conteudoFormatado.append(line + Constante.QUEBRA_LINHA);
			}
			return;
		}

		if (removerComentarios(line)) {
			return;
		}

		String newLine = removerComentariosAposCodigo(line);
		if (!newLine.equals(line)) {
			line = newLine;
			if (line.trim().equals(Constante.VAZIO)) {
				if (!oldLine.trim().equals(Constante.VAZIO)) {
					conteudoFormatado.append(line + Constante.QUEBRA_LINHA);
				}
				return;
			}
			oldLine = line;
		}

		verificarQtdMetodos(line, oldLine);
		verificarQtdClasses(line);

		conteudoCompactado.append(line + Constante.QUEBRA_LINHA);
		conteudoFormatado.append(line + Constante.QUEBRA_LINHA);

		qtdLinhasDeCodigo++;
		oldLine = line;
	}

	private boolean removerComentarios(String line) {
		if (line.trim().endsWith(Constante.COMENTARIO_MULTI_LINHA_3)) {
			verificadorMultiComentario = false;
			qtdComentarios++;
			return true;
		} else if (verificadorMultiComentario) {
			qtdComentarios++;
			return true;
		} else if (line.trim().startsWith(Constante.COMENTARIO_MULTI_LINHA_1)) {
			qtdComentarios++;
			verificadorMultiComentario = true;
			return true;
		} else if (line.trim().startsWith(Constante.COMENTARIO_MULTI_LINHA_1)
				&& line.trim().endsWith(Constante.COMENTARIO_MULTI_LINHA_3)) {
			qtdComentarios++;
			return true;
		} else if (line.trim().startsWith(Constante.COMENTARIO_MULTI_LINHA_2)) {
			qtdComentarios++;
			return true;
		} else if (line.trim().startsWith(Constante.COMENTARIO_SIMPLES)) {
			qtdComentarios++;
			return true;
		} else {
			return false;
		}
	}

	private String removerComentariosAposCodigo(String line) {

		if ((line.contains(Constante.COMENTARIO_MULTI_LINHA_1)
				&& !line.trim().startsWith(Constante.COMENTARIO_MULTI_LINHA_1))
				|| (line.contains(Constante.COMENTARIO_SIMPLES)
						&& !line.trim().startsWith(Constante.COMENTARIO_SIMPLES))) {
			StringBuilder sb = new StringBuilder();
			int countAspas = Constante.NUMBER_ZERO_INT;

			for (int i = 0; i < line.trim().length(); i++) {

				if (line.trim().charAt(i) == Constante.ASPAS_DUPLAS && countAspas == 1
						&& line.trim().charAt(i - 1) != Constante.BARRA_INVERTIDA) {
					countAspas++;
				}

				if (line.trim().charAt(i) == Constante.ASPAS_DUPLAS && countAspas == Constante.NUMBER_ZERO_INT) {
					countAspas++;
				}

				if (countAspas == 2) {
					countAspas = Constante.NUMBER_ZERO_INT;
				}

				if (line.trim().charAt(i) == Constante.BARRA && countAspas == Constante.NUMBER_ZERO_INT) {
					sb.append(line.charAt(i));
					if (line.trim().charAt(i + 1) == Constante.ASTERISCO) {

						if (!line.trim().endsWith(Constante.COMENTARIO_MULTI_LINHA_3)) {
							verificadorMultiComentario = true;
						}

						qtdComentarios++;
						line = sb.toString();
						break;
					}
					if (line.trim().charAt(i + 1) == Constante.BARRA) {
						line = sb.toString();
						qtdComentarios++;
						break;
					}
				}

				sb.append(line.charAt(i));
			}
			line = sb.toString();
		}
		return line;
	}

	private void verificarQtdMetodos(String line, String oldLine) {

		try {
			Pattern padrao = Pattern.compile(Constante.REGEX_METODO);
			Matcher encontrador = padrao.matcher(line);
			while (encontrador.find()) {
				if (!oldLine.trim().equals(Constante.OVERRIDE) && (!line.trim().contains(Constante.ABSTRACT)
						|| !line.trim().endsWith(Constante.PONTO_VIRGULA))) {
					System.out.println(line);
					qtdMetodos++;
				}
			}
		} catch (PatternSyntaxException e) {
			LOGGER.throwing(e);
		}
	}

	private void verificarQtdClasses(String linha) {

		try {
			Pattern padrao = Pattern.compile(Constante.REGEX_CLASSE);
			Matcher encontrador = padrao.matcher(linha);
			while (encontrador.find()) {
				qtdClasses++;
			}

		} catch (PatternSyntaxException e) {
			LOGGER.throwing(e);
		}
	}

	protected Long verificarEntidadesDeusas(int limit, boolean isMethod) {
		Long contGod = Constante.NUMBER_ZERO_LONG;
		List<Auxiliar> contadores = new ArrayList<>();
		Auxiliar auxiliar;
		String conteudoString = conteudoCompactado.toString();
		StringTokenizer st = new StringTokenizer(conteudoString, Constante.QUEBRA_LINHA);
		oldLine = Constante.VAZIO;

		while (st.hasMoreTokens()) {
			String line = st.nextToken();

			if (!contadores.isEmpty() || contadores != null) {
				for (Auxiliar contAuxiliar : contadores) {
					if (contAuxiliar.getContador() != Constante.NUMBER_ZERO_INT) {
						contAuxiliar.setLinha(contAuxiliar.getLinha() + 1);
					}
				}
			}
			if (line.contains(Constante.ABRE_CHAVES)) {
				if (!contadores.isEmpty() || contadores != null) {
					for (Auxiliar contAuxiliar : contadores) {
						if (contAuxiliar.getContador() != Constante.NUMBER_ZERO_INT) {
							contAuxiliar.setContador(contAuxiliar.getContador() + 1);
						}
					}
				}
				if (line.trim().matches(isMethod ? Constante.REGEX_METODO : Constante.REGEX_CLASSE)) {
					if (isMethod && !oldLine.trim().equals(Constante.OVERRIDE)
							&& (!line.trim().contains(Constante.ABSTRACT)
									|| !line.trim().endsWith(Constante.PONTO_VIRGULA))
							|| !isMethod) {
						auxiliar = new Auxiliar();
						auxiliar.setContador(auxiliar.getContador() + 1);
						contadores.add(auxiliar);
					}

				}
			}
			if (line.contains(Constante.FECHA_CHAVES)) {

				if (!contadores.isEmpty() || contadores != null) {
					for (Auxiliar contAuxiliar : contadores) {
						if (contAuxiliar.getContador() != Constante.NUMBER_ZERO_INT) {
							contAuxiliar.setContador(contAuxiliar.getContador() - 1);
						}
					}
				}
			}
			if (!contadores.isEmpty() || contadores != null) {
				for (Auxiliar contAuxiliar : contadores) {
					if (contAuxiliar.getContador() == Constante.NUMBER_ZERO_INT) {
						if (contAuxiliar.getLinha() > limit) {
							contGod++;
							contAuxiliar.setLinha(Constante.NUMBER_ZERO_INT);
						} else {
							contAuxiliar.setLinha(Constante.NUMBER_ZERO_INT);
						}
					}
				}
			}

			oldLine = line;

		}
		return contGod;
	}

	public void salvarDiretorioAndArquivo(Path diretorio, File arquivo) {
		getArquivoPesquisado().setCaminho(diretorio);
		getArquivoPesquisado().setArquivo(arquivo);
	}

	protected void salvarMetricas() {

		if (Util.isNotNullOrZeroNumber(qtdLinhasDeCodigo))
			getArquivoPesquisado().setQtdLoc(qtdLinhasDeCodigo);

		if (Util.isNotNullOrZeroNumber(qtdClasses))
			getArquivoPesquisado().setQtdClasses(qtdClasses);

		if (Util.isNotNullOrZeroNumber(qtdMetodos))
			getArquivoPesquisado().setQtdMetodos(qtdMetodos);

		if (Util.isNotNullOrZeroNumber(qtdComentarios))
			getArquivoPesquisado().setQtdComentarios(qtdComentarios);

		if (Util.isNotNullOrZeroNumber(qtdMetodoDeus))
			getArquivoPesquisado().setQtdMetodoDeus(qtdMetodoDeus);

		if (Util.isNotNullOrZeroNumber(qtdClasseDeus))
			getArquivoPesquisado().setQtdClasseDeus(qtdClasseDeus);

		if (Util.isNotNullOrEmpty(conteudoCompleto)) {
			getArquivoPesquisado().setConteudoCompleto(conteudoCompleto.toString());
		} else {
			getArquivoPesquisado().setConteudoCompleto(Constante.VAZIO);
		}

		if (Util.isNotNullOrEmpty(conteudoFormatado))
			getArquivoPesquisado().setConteudoFormatado(conteudoFormatado.toString());
		else {
			getArquivoPesquisado().setConteudoFormatado(Constante.VAZIO);
		}

		if (Util.isNotNullOrEmpty(conteudoCompactado))
			getArquivoPesquisado().setConteudoCompactado(conteudoCompactado.toString());
		else {
			getArquivoPesquisado().setConteudoCompactado(Constante.VAZIO);
		}

		LOGGER.info("As métricas de software foram salvas com sucesso.");
	}

	protected void resetarMetricasProvisorias() {
		this.qtdLinhasDeCodigo = Constante.NUMBER_ZERO_LONG;
		this.qtdClasses = Constante.NUMBER_ZERO_LONG;
		this.qtdMetodos = Constante.NUMBER_ZERO_LONG;
		this.qtdComentarios = Constante.NUMBER_ZERO_LONG;
		this.qtdMetodoDeus = Constante.NUMBER_ZERO_LONG;
		this.qtdClasseDeus = Constante.NUMBER_ZERO_LONG;

		conteudoCompleto = new StringBuilder();
		conteudoCompactado = new StringBuilder();
		conteudoFormatado = new StringBuilder();

		verificadorMultiComentario = false;
		oldLine = Constante.VAZIO;
	}

	public ArquivoMetrica getArquivoPesquisado() {
		return arquivoPesquisado;
	}

}
