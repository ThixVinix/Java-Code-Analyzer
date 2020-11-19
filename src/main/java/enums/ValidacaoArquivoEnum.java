package enums;

public enum ValidacaoArquivoEnum {
	
	SUCESSO(""), 
	ARQUIVO_ANALISADO("Este arquivo j� foi analisado com as configura��es atuais.\nEscolha outro arquivo ou altere alguma configura��o."),
	
	PREENCHIMENTO_OBRIGATORIO_DIRETORIO("Favor, preencher o campo do diret�rio para poder iniciar a an�lise."), 
	DIRETORIO_NAO_ENCONTRADO("O diret�rio n�o foi encontrado ou est� incompleto.\n Tente digitar novamente."),
	TIPO_ARQUIVO_INCORRETO("O arquivo n�o possui o tipo permitido \".java\".\n Escolha outro arquivo do tipo \".java\"."),
	ERRO_CONVERSAO_DIRETORIO("Diret�rio inv�lido! Tente novamente digitar um diret�rio."),
	ARQUIVO_NAO_SUPORTADO(""),
	
	PREENCHIMENTO_OBRIGATORIO_METODO_DEUS("Campo \"Limite m�nimo para m�todo deus\" � de preenchimento obrigat�rio."),
	PREENCHIMENTO_OBRIGATORIO_CLASSE_DEUS("Campo \"Limite m�nimo para classe deusa\" � de preenchimento obrigat�rio."), 
	PREENCHIMENTO_INCORRETO_METODO_DEUS("Campo \"Limite m�nimo para m�todo deus\" deve ser um campo preenchido somente com n�meros."),
	PREENCHIMENTO_INCORRETO_CLASSE_DEUS("Campo \"Limite m�nimo para classe deusa\" deve ser um campo preenchido somente com n�meros."), 
	
	MESSAGE_SELECIONE_GRAFICO("Selecione um modelo de gr�fico.");
	
	private String descricao;
	
	private ValidacaoArquivoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	
    public String getDescricao() {
        return descricao;
    }

}