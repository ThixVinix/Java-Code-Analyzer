package telas;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bean.PesquisaUrlBean;

public class TelaPesquisaURL extends JFrame {

	private static final long serialVersionUID = 4870716113147027443L;
	private JPanel contentPane;
	private JTextField campoUrl;
	private PesquisaUrlBean pesquisaUrlBean = new PesquisaUrlBean();
	private JFrame frameAnterior;

	public TelaPesquisaURL(JFrame frameAnterior) {
		setTitle("Pesquisar por URL");
		this.frameAnterior = frameAnterior;
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final TextArea textoUrl = new TextArea();
		textoUrl.setFont(new Font("Arial", Font.PLAIN, 18));
		textoUrl.setEditable(false);
		textoUrl.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent e) {
			}
		});
		textoUrl.setBounds(10, 130, 814, 471);
		contentPane.add(textoUrl);

		campoUrl = new JTextField();
		campoUrl.setBounds(155, 36, 472, 23);
		contentPane.add(campoUrl);
		campoUrl.setColumns(10);

		JLabel labelUrl = new JLabel("Digite a URL:");
		labelUrl.setBounds(155, 11, 100, 14);
		contentPane.add(labelUrl);

		JButton voltar = new JButton("Voltar");
		voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		voltar.setBounds(155, 85, 89, 23);
		contentPane.add(voltar);

		JButton exibirConteudo = new JButton("Exibir conte\u00FAdo");
		exibirConteudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URL url = pesquisaUrlBean.digitarURL(campoUrl.getText());
				StringBuilder sb = null;
				try {
					sb = pesquisaUrlBean.lerConteudoURL(url);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String conteudo = sb.toString();
				textoUrl.append(conteudo);
				textoUrl.repaint();
			}
		});
		exibirConteudo.setBounds(483, 85, 144, 23);
		contentPane.add(exibirConteudo);
	}
}
