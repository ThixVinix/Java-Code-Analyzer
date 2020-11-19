package interfaces;

import java.io.File;
import java.io.IOException;

import enums.ValidacaoArquivoEnum;

public interface InterfaceArquivoMetrica {

	public ValidacaoArquivoEnum validarCamposEntidadesDeusas(String qtdMinMetodoDeusString,
			String qtdMinClasseDeusString);

	public void iniciarAnalise(File arquivo, Integer limiteMinMetodoDeus, Integer limiteMinClasseDeus)
			throws IOException;

}
