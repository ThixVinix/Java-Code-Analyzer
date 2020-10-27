package bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import enums.ValidacaoArquivoEnum;
import model.ArquivoMetrica;
import model.Auxiliar;
import utilitarios.Constante;
import utilitarios.DirectoryUtil;
import utilitarios.Util;

public class ArquivoMetricaBean implements Serializable {

	private static final long serialVersionUID = -1433406600601635150L;
	private int qtdLinhasDeCodigo;
	private int qtdClasses;
	private int qtdMetodos;
	private int qtdComentarios;
	private int qtdMetodoDeus;
	private int qtdClasseDeus;
	public ArquivoMetrica arquivoPesquisado;
	private StringBuilder conteudoCompleto;
	private StringBuilder conteudoFormatado;
	private StringBuilder conteudoCompactado;
	private boolean verificadorMultiComentario = false;

	public ArquivoMetricaBean() {
		arquivoPesquisado = new ArquivoMetrica();
		arquivoPesquisado.setQtdLoc(0);
		arquivoPesquisado.setQtdClasseDeus(0);
		arquivoPesquisado.setQtdMetodos(0);
		arquivoPesquisado.setQtdComentarios(0);
		arquivoPesquisado.setQtdClasseDeus(0);
		arquivoPesquisado.setQtdMetodoDeus(0);
	}

	public void salvarMetricas() {

		if (Util.isNotNullOrEmpty(conteudoCompleto.toString()))
			arquivoPesquisado.setConteudoCompleto(conteudoCompleto.toString());

		if (Util.isNotNullOrEmpty(conteudoFormatado.toString()))
			arquivoPesquisado.setConteudoFormatado(conteudoFormatado.toString());

		if (Util.isNotNullOrEmpty(conteudoCompactado.toString()))
			arquivoPesquisado.setConteudoCompactado(conteudoCompactado.toString());

		if (Util.isNotNullOrZeroNumber(qtdLinhasDeCodigo))
			arquivoPesquisado.setQtdLoc(qtdLinhasDeCodigo);

		if (Util.isNotNullOrZeroNumber(qtdClasses))
			arquivoPesquisado.setQtdClasses(qtdClasses);

		if (Util.isNotNullOrZeroNumber(qtdMetodos))
			arquivoPesquisado.setQtdMetodos(qtdMetodos);

		if (Util.isNotNullOrZeroNumber(qtdComentarios))
			arquivoPesquisado.setQtdComentarios(qtdComentarios);

		if (Util.isNotNullOrZeroNumber(qtdMetodoDeus))
			arquivoPesquisado.setQtdMetodoDeus(qtdMetodoDeus);

		if (Util.isNotNullOrZeroNumber(qtdClasseDeus))
			arquivoPesquisado.setQtdClasseDeus(qtdClasseDeus);

	}

	public ValidacaoArquivoEnum validarDiretorio(String diretorioString) {

		if (Util.isNullOrEmpty(diretorioString.trim()))
			return ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_DIRETORIO;

		Path caminho = DirectoryUtil.obterPathPeloDiretorioString(diretorioString);

		if (Util.isNullOrEmpty(caminho))
			return ValidacaoArquivoEnum.ERRO_CONVERSAO_DIRETORIO;

		if (!DirectoryUtil.validarDiretorioCompleto(caminho))
			return ValidacaoArquivoEnum.DIRETORIO_NAO_ENCONTRADO;

		if (!DirectoryUtil.isJavaFile(caminho.toFile()))
			return ValidacaoArquivoEnum.ARQUIVO_NAO_SUPORTADO;

		return ValidacaoArquivoEnum.SUCESSO;
	}

	public ValidacaoArquivoEnum validarCamposEntidadesDeusas(String qtdMinMetodoDeusString,
			String qtdMinClasseDeusString) {

		if (Util.isNullOrEmpty(qtdMinMetodoDeusString.trim()))
			return ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_METODO_DEUS;

		if (Util.isNullOrEmpty(qtdMinClasseDeusString.trim()))
			return ValidacaoArquivoEnum.PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS;

		if (!Util.isNumerico(qtdMinMetodoDeusString.trim()))
			return ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_METODO_DEUS;

		if (!Util.isNumerico(qtdMinClasseDeusString.trim()))
			return ValidacaoArquivoEnum.PREENCHIMENTO_INCORRETO_CLASSE_DEUS;

		return ValidacaoArquivoEnum.SUCESSO;
	}

	public void lerConteudoArquivoAlvo(File arquivo, Integer limiteMinMetodoDeus, Integer limiteMinClasseDeus)
			throws IOException {

		FileInputStream stream;

		stream = new FileInputStream(arquivo);

		InputStreamReader reader = new InputStreamReader(stream);
		BufferedReader br = new BufferedReader(reader);

		String line;
		String oldLine = "";
		conteudoCompleto = new StringBuilder();
		conteudoCompactado = new StringBuilder();
		conteudoFormatado = new StringBuilder();
		while ((line = br.readLine()) != null) {
			conteudoCompleto.append(line + "\n");

			if (line.trim().equals("")) {
				if (!oldLine.trim().equals("")) {
					conteudoFormatado.append(line + "\n");
				}
				continue;
			}

			if (removerComentarios(line)) {
				continue;
			}

			String newLine = removerComentariosAposCodigo(line);
			if (!newLine.equals(line)) {
				line = newLine;
				if (line.trim().equals("")) {
					if (!oldLine.trim().equals("")) {
						conteudoFormatado.append(line + "\n");
					}
					continue;
				}
				oldLine = line;
			}

			verificarQtdMetodos(line, oldLine);
			verificarQtdClasses(line);

			conteudoCompactado.append(line + "\n");
			conteudoFormatado.append(line + "\n");

			qtdLinhasDeCodigo++;
			oldLine = line;
		}

		reader.close();

		qtdMetodoDeus = verificarEntidadesDeuses(limiteMinMetodoDeus, true);
		qtdClasseDeus = verificarEntidadesDeuses(limiteMinClasseDeus, false);

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
			int countAspas = 0;

			for (int i = 0; i < line.trim().length(); i++) {

				if (line.trim().charAt(i) == Constante.ASPAS_DUPLAS && countAspas == 1
						&& line.trim().charAt(i - 1) != Constante.BARRA_INVERTIDA) {
					countAspas++;
				}

				if (line.trim().charAt(i) == Constante.ASPAS_DUPLAS && countAspas == 0) {
					countAspas++;
				}

				if (countAspas == 2) {
					countAspas = 0;
				}

				if (line.trim().charAt(i) == Constante.BARRA && countAspas == 0) {
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	private int verificarEntidadesDeuses(int limit, boolean isMethod) {

		int contGod = 0;
		List<Auxiliar> contadores = new ArrayList<>();
		Auxiliar auxiliar;
		String conteudoString = conteudoCompactado.toString();
		StringTokenizer st = new StringTokenizer(conteudoString, "\n");
		String oldLine = "";

		while (st.hasMoreTokens()) {
			String line = st.nextToken();

			if (!contadores.isEmpty() || contadores != null) {
				for (Auxiliar contAuxiliar : contadores) {
					if (contAuxiliar.getContador() != 0) {
						contAuxiliar.setLinha(contAuxiliar.getLinha() + 1);
					}
				}
			}
			if (line.contains(Constante.ABRE_CHAVES)) {
				if (!contadores.isEmpty() || contadores != null) {
					for (Auxiliar contAuxiliar : contadores) {
						if (contAuxiliar.getContador() != 0) {
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
						if (contAuxiliar.getContador() != 0) {
							contAuxiliar.setContador(contAuxiliar.getContador() - 1);
						}
					}
				}
			}
			if (!contadores.isEmpty() || contadores != null) {
				for (Auxiliar contAuxiliar : contadores) {
					if (contAuxiliar.getContador() == 0) {
						if (contAuxiliar.getLinha() > limit) {
							contGod++;
							contAuxiliar.setLinha(0);
						} else {
							contAuxiliar.setLinha(0);
						}
					}
				}
			}

			oldLine = line;

		}
		return contGod;
	}

}