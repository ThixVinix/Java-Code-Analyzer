package telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import actions.ListenerAnalisarArquivo;
import actions.ListenerDefinirEntidadesDeus;
import actions.ListenerExibirConteudoCompactado;
import actions.ListenerExibirConteudoCompleto;
import actions.ListenerExibirConteudoFormatado;
import actions.ListenerGerarGraficoMetricas;
import actions.ListenerJavaFileChooser;
import actions.ListenerLimpar;
import actions.ListenerVoltar;
import bean.ArquivoMetricaBean;
import plainDocuments.DocumentoCampoNumerico;
import utilitarios.Constante;

public class TelaPesquisaArquivo extends JFrame {

	private static final Logger LOGGER = LogManager.getLogger(TelaPesquisaArquivo.class.getName());
	private static final long serialVersionUID = 5988972284355233140L;
	
	public JFileChooser choose = new JFileChooser(Constante.CURRENT_DIRETORY_PATH_C);
	public JPanel frmAnaliseArquivo;
	public JFrame frameAnterior;
	public ArquivoMetricaBean arquivoMetricaBean;
	public Grafico grafico;
	public JTextField campoDiretorio;
	public JTextField campoCaminho;
	public JTextField campoPastasAcessadas;
	public JTextField campoArquivo;
	public JTextField campoLOC;
	public JTextField campoClasses;
	public JTextField campoMetodos;
	public JTextField campoComentarios;
	public JTextField campoClasseDeus;
	public JTextField campoMetodoDeus;
	public JTextField campoLimiteMetodoDeus;
	public JTextField campoLimiteClasseDeus;
	public JRadioButton radioButtonConteudoCompleto;
	public JRadioButton radioButtonConteudoFormatado;
	public JRadioButton radioButtonConteudoCompactado;
	public JButton botaoVoltar;
	public JButton botaoLimpar;
	public JButton botaoAnalise;
	public JButton botaoBuscar;
	public JButton botaoGerarGrafico;
	public TextArea textAreaConteudo;
	public JCheckBox checkBoxEntidadeDeus;
	public JComboBox<String> comboBoxGrafico;
	public String arquivoAnteriorAnalisado;
	public String limiteMetodoDeusAnterior;
	public String limiteClasseDeusAnterior;

	/**
	 * Create the application.
	 */
	public TelaPesquisaArquivo(JFrame frameAnterior) {
		LOGGER.info("Inicializando tela de análise por arquivo único");
		this.arquivoMetricaBean = new ArquivoMetricaBean();
		this.frameAnterior = frameAnterior;
		setResizable(false);
		setTitle(Constante.TITLE_PESQUISA_ARQUIVO);
		arquivoAnteriorAnalisado = "";
		limiteClasseDeusAnterior = "";
		limiteMetodoDeusAnterior = "";
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParseException
	 */
	private void initialize() {
		createJanela();
		createRotulos();
		createCampos();
		createSeparadores();
		createCombosBox();
		createRadionButtons();
		createChecksBox();
		createTextsArea();
		createBotoes();
	}

	private void createJanela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 1182, 766);
		frmAnaliseArquivo = new JPanel();
		frmAnaliseArquivo.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		setContentPane(frmAnaliseArquivo);
		frmAnaliseArquivo.setLayout(null);
	}

	public void exibirMensagemInformativa(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
	}

	public void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public void exibirMensagemAlerta(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
	}

	private void createRotulos() {
		createRotuloDiretorio();
		createRotuloConteudo();
		createRotuloCaminho();
		createRotuloPastasAcessadas();
		createRotuloArquivo();
		createRotuloLoc();
		createRotuloClasse();
		createRotuloMetodo();
		createRotuloComentario();
		createRotuloClasseDeus();
		createRotuloMetodoDeus();
		createRotuloInformativo();
		createRotuloLimiteMetodoDeus();
		createRotuloLimiteClasseDeus();
	}

	private void createCampos() {
		createCampoDiretorio();
		createCampoCaminho();
		createCampoPastasAcessadas();
		createCampoArquivo();
		createCampoLoc();
		createCampoClasse();
		createCampoMetodo();
		createCampoComentario();
		createCampoClasseDeus();
		createCampoMetodoDeus();
		createCampoLimiteMetodoDeus();
		createCampoLimiteClasseDeus();
	}

	private void createSeparadores() {
		createSeparador();
	}

	private void createCombosBox() {
		createComboBoxGrafico();
	}

	private void createRadionButtons() {
		createRadioButtonConteudoCompleto();
		createRadioButtonConteudoFormatado();
		createRadioButtonConteudoCompactado();
	}

	private void createChecksBox() {
		createCheckBoxDefinirLimites();
	}

	private void createTextsArea() {
		createTextAreaConteudo();
	}

	private void createBotoes() {
		createBotaoGerarGrafico();
		createBotaoVoltar();
		createBotaoLimpar();
		createBotaoIniciarAnalise();
		createBotaoBuscar();
	}

	private void createRotuloDiretorio() {
		JLabel labelDiretorio = new JLabel(Constante.LABEL_CAMPO_DIRETORIO_COMPLETO);
		labelDiretorio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelDiretorio.setBounds(25, 11, 402, 17);
		frmAnaliseArquivo.add(labelDiretorio);
	}

	private void createRotuloConteudo() {
		JLabel labelConteudo = new JLabel(Constante.LABEL_CONTEUDO_ARQUIVO);
		labelConteudo.setBounds(10, 193, 119, 14);
		frmAnaliseArquivo.add(labelConteudo);
	}

	private void createRotuloCaminho() {
		JLabel labelCaminho = new JLabel(Constante.LABEL_CAMINHO_DIRETORIO);
		labelCaminho.setBounds(10, 581, 136, 14);
		frmAnaliseArquivo.add(labelCaminho);
	}

	private void createRotuloPastasAcessadas() {
		JLabel labelPastasAcessadas = new JLabel(Constante.LABEL_QTD_PASTAS_ACESSADAS);
		labelPastasAcessadas.setBounds(10, 622, 152, 14);
		frmAnaliseArquivo.add(labelPastasAcessadas);
	}

	private void createRotuloArquivo() {
		JLabel labelArquivo = new JLabel(Constante.LABEL_NOME_ARQUIVO);
		labelArquivo.setBounds(10, 664, 119, 14);
		frmAnaliseArquivo.add(labelArquivo);
	}

	private void createRotuloLoc() {
		JLabel labelLOC = new JLabel(Constante.LABEL_QTD_LINHAS_CODIGO);
		labelLOC.setBounds(432, 622, 201, 14);
		frmAnaliseArquivo.add(labelLOC);
	}

	private void createRotuloClasse() {
		JLabel labelClasses = new JLabel(Constante.LABEL_QTD_CLASSES);
		labelClasses.setBounds(770, 622, 136, 14);
		frmAnaliseArquivo.add(labelClasses);
	}

	private void createRotuloMetodo() {
		JLabel labelMetodos = new JLabel(Constante.LABEL_QTD_METODOS);
		labelMetodos.setBounds(469, 664, 152, 14);
		frmAnaliseArquivo.add(labelMetodos);
	}

	private void createRotuloComentario() {
		JLabel labelComentarios = new JLabel(Constante.LABEL_QTD_COMENTARIOS);
		labelComentarios.setBounds(770, 664, 136, 14);
		frmAnaliseArquivo.add(labelComentarios);
	}

	private void createRotuloClasseDeus() {
		JLabel labelClasseDeus = new JLabel(Constante.LABEL_QTD_CLASSES_DEUSAS);
		labelClasseDeus.setBounds(770, 703, 145, 14);
		frmAnaliseArquivo.add(labelClasseDeus);
	}

	private void createRotuloMetodoDeus() {
		JLabel labelMetodoDeus = new JLabel(Constante.LABEL_QTD_METODOS_DEUSES);
		labelMetodoDeus.setBounds(469, 703, 152, 14);
		frmAnaliseArquivo.add(labelMetodoDeus);
	}

	private void createRotuloInformativo() {
		JLabel labelInformativo = new JLabel(Constante.LABEL_INFORMATIVA);
		labelInformativo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelInformativo.setForeground(Color.BLUE);
		labelInformativo.setBounds(20, 514, 675, 30);
		frmAnaliseArquivo.add(labelInformativo);
	}

	private void createRotuloLimiteMetodoDeus() {
		JLabel labelLimiteMetodoDeus = new JLabel(Constante.LABEL_LIMITE_METODO_DEUS);
		labelLimiteMetodoDeus.setBounds(598, 50, 199, 14);
		frmAnaliseArquivo.add(labelLimiteMetodoDeus);
	}

	private void createRotuloLimiteClasseDeus() {
		JLabel labelLimiteClasseDeus = new JLabel(Constante.LABEL_LIMITE_CLASSE_DEUSA);
		labelLimiteClasseDeus.setBounds(598, 97, 216, 14);
		frmAnaliseArquivo.add(labelLimiteClasseDeus);
	}

	private void createCampoLimiteClasseDeus() {
		campoLimiteClasseDeus = new JTextField();
		campoLimiteClasseDeus.setEditable(false);
		campoLimiteClasseDeus.setBounds(788, 86, 45, 36);
		campoLimiteClasseDeus.setDocument(new DocumentoCampoNumerico(4));
		campoLimiteClasseDeus.setText(Constante.QTD_MIN_LINHAS_CLASSE_DEUS.toString());
		frmAnaliseArquivo.add(campoLimiteClasseDeus);
		campoLimiteClasseDeus.setColumns(10);
	}

	private void createCampoLimiteMetodoDeus() {
		campoLimiteMetodoDeus = new JTextField();
		campoLimiteMetodoDeus.setEditable(false);
		campoLimiteMetodoDeus.setBounds(788, 39, 45, 36);
		campoLimiteMetodoDeus.setDocument(new DocumentoCampoNumerico(4));
		campoLimiteMetodoDeus.setText(Constante.QTD_MIN_LINHAS_METODO_DEUS.toString());
		frmAnaliseArquivo.add(campoLimiteMetodoDeus);
		campoLimiteMetodoDeus.setColumns(10);
	}

	private void createCampoMetodoDeus() {
		campoMetodoDeus = new JTextField();
		campoMetodoDeus.setEditable(false);
		campoMetodoDeus.setBounds(631, 694, 86, 32);
		campoMetodoDeus.setDocument(new DocumentoCampoNumerico());
		campoMetodoDeus.setColumns(10);
		frmAnaliseArquivo.add(campoMetodoDeus);
	}

	private void createCampoClasseDeus() {
		campoClasseDeus = new JTextField();
		campoClasseDeus.setEditable(false);
		campoClasseDeus.setBounds(916, 694, 86, 32);
		campoClasseDeus.setDocument(new DocumentoCampoNumerico());
		campoClasseDeus.setColumns(10);
		frmAnaliseArquivo.add(campoClasseDeus);
	}

	private void createCampoComentario() {
		campoComentarios = new JTextField();
		campoComentarios.setEditable(false);
		campoComentarios.setBounds(916, 652, 86, 31);
		campoComentarios.setDocument(new DocumentoCampoNumerico());
		campoComentarios.setColumns(10);
		frmAnaliseArquivo.add(campoComentarios);
	}

	private void createCampoMetodo() {
		campoMetodos = new JTextField();
		campoMetodos.setEditable(false);
		campoMetodos.setBounds(631, 652, 86, 31);
		campoMetodos.setDocument(new DocumentoCampoNumerico());
		campoMetodos.setColumns(10);
		frmAnaliseArquivo.add(campoMetodos);
	}

	private void createCampoClasse() {
		campoClasses = new JTextField();
		campoClasses.setEditable(false);
		campoClasses.setBounds(916, 611, 86, 29);
		campoClasses.setDocument(new DocumentoCampoNumerico());
		campoClasses.setColumns(10);
		frmAnaliseArquivo.add(campoClasses);
	}

	private void createCampoLoc() {
		campoLOC = new JTextField();
		campoLOC.setEditable(false);
		campoLOC.setBounds(631, 611, 86, 30);
		campoLOC.setDocument(new DocumentoCampoNumerico());
		campoLOC.setColumns(10);
		frmAnaliseArquivo.add(campoLOC);
	}

	private void createCampoArquivo() {
		campoArquivo = new JTextField();
		campoArquivo.setEditable(false);
		campoArquivo.setBounds(172, 652, 238, 31);
		campoArquivo.setColumns(10);
		frmAnaliseArquivo.add(campoArquivo);
	}

	private void createCampoPastasAcessadas() {
		campoPastasAcessadas = new JTextField();
		campoPastasAcessadas.setEditable(false);
		campoPastasAcessadas.setBounds(172, 611, 238, 30);
		campoPastasAcessadas.setColumns(10);
		frmAnaliseArquivo.add(campoPastasAcessadas);
	}

	private void createCampoCaminho() {
		campoCaminho = new JTextField();
		campoCaminho.setEditable(false);
		campoCaminho.setBounds(172, 568, 982, 32);
		campoCaminho.setColumns(10);
		frmAnaliseArquivo.add(campoCaminho);
	}

	private void createCampoDiretorio() {
		campoDiretorio = new JTextField();
		campoDiretorio.setBounds(25, 39, 464, 36);
		frmAnaliseArquivo.add(campoDiretorio);
		campoDiretorio.setColumns(10);
	}

	private void createCheckBoxDefinirLimites() {
		checkBoxEntidadeDeus = new JCheckBox(Constante.LABEL_DEFINIR_ENTIDADES_DEUSES);
		checkBoxEntidadeDeus.setBounds(592, 10, 282, 23);
		checkBoxEntidadeDeus.addActionListener(new ListenerDefinirEntidadesDeus(this));
		frmAnaliseArquivo.add(checkBoxEntidadeDeus);
	}

	private void createRadioButtonConteudoCompactado() {
		radioButtonConteudoCompactado = new JRadioButton(Constante.LABEL_EXIBIR_CONTEUDO_COMPACTADO);
		radioButtonConteudoCompactado.setBounds(188, 173, 184, 23);
		radioButtonConteudoCompactado.setVisible(false);
		radioButtonConteudoCompactado.addActionListener(new ListenerExibirConteudoCompactado(this));
		frmAnaliseArquivo.add(radioButtonConteudoCompactado);
	}

	private void createRadioButtonConteudoFormatado() {
		radioButtonConteudoFormatado = new JRadioButton(Constante.LABEL_EXIBIR_CONTEUDO_FORMATADO);
		radioButtonConteudoFormatado.setBounds(188, 149, 184, 23);
		radioButtonConteudoFormatado.setVisible(false);
		radioButtonConteudoFormatado.addActionListener(new ListenerExibirConteudoFormatado(this));
		frmAnaliseArquivo.add(radioButtonConteudoFormatado);
	}

	private void createRadioButtonConteudoCompleto() {
		radioButtonConteudoCompleto = new JRadioButton(Constante.LABEL_EXIBIR_CONTEUDO_COMPLETO);
		radioButtonConteudoCompleto.addActionListener(new ListenerExibirConteudoCompleto(this));
		radioButtonConteudoCompleto.setSelected(true);
		radioButtonConteudoCompleto.setEnabled(false);
		radioButtonConteudoCompleto.setVisible(false);
		radioButtonConteudoCompleto.setBounds(188, 123, 170, 23);
		frmAnaliseArquivo.add(radioButtonConteudoCompleto);
	}

	private void createSeparador() {
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 555, 1144, 2);
		frmAnaliseArquivo.add(separator);
	}

	private void createTextAreaConteudo() {
		textAreaConteudo = new TextArea();
		textAreaConteudo.setForeground(Color.WHITE);
		textAreaConteudo.setBackground(Color.BLACK);
		textAreaConteudo.setEditable(false);
		textAreaConteudo.setBounds(10, 213, 1144, 295);
		frmAnaliseArquivo.add(textAreaConteudo);
	}

	private void createComboBoxGrafico() {
		comboBoxGrafico = new JComboBox<>();
		comboBoxGrafico.setEnabled(false);
		comboBoxGrafico.setBounds(953, 127, 201, 22);
		comboBoxGrafico.addItem(Constante.GRAFICO_SELECT_OPTION);
		comboBoxGrafico.addItem(Constante.GRAFICO_PIZZA);
		comboBoxGrafico.addItem(Constante.GRAFICO_BARRA_HORIZONTAL);
		comboBoxGrafico.addItem(Constante.GRAFICO_BARRA_VERTICAL);
		comboBoxGrafico.setSelectedIndex(0);
		frmAnaliseArquivo.add(comboBoxGrafico);
	}

	private void createBotaoBuscar() {
		botaoBuscar = new JButton(Constante.BUTTON_TEXT_BUSCAR);
		botaoBuscar.addActionListener(new ListenerJavaFileChooser(this));
		botaoBuscar.setBounds(499, 46, 76, 23);
		frmAnaliseArquivo.add(botaoBuscar);
	}

	private void createBotaoIniciarAnalise() {
		botaoAnalise = new JButton(Constante.BUTTON_TEXT_INICIAR_ANALISE);
		botaoAnalise.addActionListener(new ListenerAnalisarArquivo(this));
		botaoAnalise.setBounds(465, 93, 110, 23);
		frmAnaliseArquivo.add(botaoAnalise);
	}

	private void createBotaoLimpar() {
		botaoLimpar = new JButton(Constante.BUTTON_TEXT_LIMPAR);
		botaoLimpar.setEnabled(false);
		botaoLimpar.addActionListener(new ListenerLimpar(this));
		botaoLimpar.setBounds(221, 93, 89, 23);
		frmAnaliseArquivo.add(botaoLimpar);
	}

	private void createBotaoVoltar() {
		botaoVoltar = new JButton(Constante.BUTTON_TEXT_VOLTAR);
		botaoVoltar.addActionListener(new ListenerVoltar(this));
		botaoVoltar.setBounds(10, 93, 89, 23);
		frmAnaliseArquivo.add(botaoVoltar);
	}

	private void createBotaoGerarGrafico() {
		botaoGerarGrafico = new JButton(Constante.BUTTON_TEXT_GERAR_GRAFICO);
		botaoGerarGrafico.setEnabled(false);
		botaoGerarGrafico.addActionListener(new ListenerGerarGraficoMetricas(this));
		botaoGerarGrafico.setBounds(1018, 160, 136, 23);
		frmAnaliseArquivo.add(botaoGerarGrafico);
	}

}