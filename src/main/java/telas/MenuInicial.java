package telas;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import utilitarios.Constante;

public class MenuInicial {

	public JFrame frameTelaInicial;
	private TelaPesquisaURL frameTelaPesquisaURL;
	private TelaPesquisaArquivo frameTelaPesquisaArquivo;
	private ImageIcon imagem = new ImageIcon(getClass().getResource(Constante.IMAGEM_MENU_INICIAL));
	private JLabel imagemMenuInicial = new JLabel(imagem);

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {

		frameTelaInicial = new JFrame();
		frameTelaInicial.setTitle(Constante.TITLE_ANALIZADOR_CODIGO_JAVA);
		frameTelaInicial.setResizable(false);
		frameTelaInicial.setBounds(100, 100, 600, 450);
		frameTelaInicial.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameTelaInicial.setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		frameTelaInicial.setJMenuBar(menuBar);

		JMenuItem menuItemSearchURL = new JMenuItem("Pesquisar por URL");
		menuItemSearchURL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
		menuItemSearchURL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							frameTelaPesquisaURL = new TelaPesquisaURL(frameTelaInicial);
							frameTelaPesquisaURL.setVisible(true);
							frameTelaInicial.setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		menuBar.add(menuItemSearchURL);

		JMenuItem menuItemSearchUniqueFile = new JMenuItem(Constante.MENU_TITLE_PESQUISAR_UNICO_ARQUIVO);
		menuItemSearchUniqueFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameTelaPesquisaArquivo = new TelaPesquisaArquivo(frameTelaInicial);
				frameTelaPesquisaArquivo.setVisible(true);
				frameTelaInicial.setVisible(false);
			}
		});
		menuItemSearchUniqueFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));
		menuBar.add(menuItemSearchUniqueFile);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0 };
		gridBagLayout.rowHeights = new int[] { 0 };
		gridBagLayout.columnWeights = new double[] { Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { Double.MIN_VALUE };
		frameTelaInicial.getContentPane().setLayout(gridBagLayout);

		frameTelaInicial.getContentPane().add(imagemMenuInicial);
	}
}
