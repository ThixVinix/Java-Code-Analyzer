package telas;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Grafico extends JFrame {

	private static final long serialVersionUID = 656583759885949289L;
	private JPanel contentPane;
	private String nomeArquivo;
	private Double qtdLoc;
	private Double qtdClasses;
	private Double qtdMetodos;
	private Double qtdComentarios;
	private Double somatorio;
	private Double porcentagemLoc;
	private Double porcentagemClasses;
	private Double porcentagemMetodos;
	private Double porcentagemComentarios;
	private boolean contemClasses;
	private boolean contemMetodos;
	private boolean contemComentarios;

	public Grafico(String nomeArquivo, Double qtdLoc, Double qtdClasses, Double qtdMetodos, Double qtdComentarios) {
		this.nomeArquivo = nomeArquivo;
		this.qtdLoc = qtdLoc;
		this.qtdClasses = qtdClasses;
		this.qtdMetodos = qtdMetodos;
		this.qtdComentarios = qtdComentarios;
		this.somatorio = 0.0;
		this.porcentagemLoc = 0.0;
		this.porcentagemClasses = 0.0;
		this.porcentagemComentarios = 0.0;
		this.contemClasses = false;
		this.contemMetodos = false;
		this.contemComentarios = false;

	}

	private void criarFrameGrafico() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 983, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setLocationRelativeTo(null);
		setContentPane(contentPane);
		this.setVisible(true);
	}

	public void criarGraficoPizza() {

		if (!validarCalculoPorcentagem()) {
			JOptionPane.showMessageDialog(null,
					"Não é possível gerar o gráfico, pois não há informações suficientes para a criação.");
			return;
		}

		DefaultPieDataset pizza = new DefaultPieDataset();

		pizza.setValue("Quantidade de Linhas(LOC)", qtdLoc);

		if (contemClasses)
			pizza.setValue("Quantidade de Classes", qtdClasses);

		if (contemMetodos)
			pizza.setValue("Quantidade de Métodos", qtdMetodos);

		if (contemComentarios)
			pizza.setValue("Quantidade de Comentários", qtdComentarios);

		JFreeChart grafico = ChartFactory.createPieChart3D("Análise do arquivo " + nomeArquivo, pizza, true, true,
				false);

//		PiePlot fatia = (PiePlot) grafico.getPlot();
//		fatia.setSectionPaint("Quantidade de Linhas(LOC)", Color.BLUE);
//		fatia.setSectionPaint("Quantidade de Classes", Color.CYAN);
//		fatia.setSectionPaint("Quantidade de Métodos", Color.BLACK);
//		fatia.setSectionPaint("Quantidade de Comentários", Color.WHITE);

		ChartPanel painel = new ChartPanel(grafico);
		criarFrameGrafico();
		painel.setVisible(true);
		add(painel);
	}

	public void criarGraficoBarrasVertical() {

		if (!validarCalculoPorcentagem()) {
			JOptionPane.showMessageDialog(null,
					"Não é possível gerar o gráfico, pois não há informações suficientes para a criação.");
			return;
		}

		DefaultCategoryDataset barra = new DefaultCategoryDataset();

		barra.setValue(qtdLoc, "Quantidade de linhas(LOC)", Math.round(porcentagemLoc) + "%");

		if (contemClasses)
			barra.setValue(qtdClasses, "Quantidade de Classes", Math.round(porcentagemClasses) + "%");

		if (contemMetodos)
			barra.setValue(qtdMetodos, "Quantidade de Métodos", Math.round(porcentagemMetodos) + "%");

		if (contemComentarios)
			barra.setValue(qtdComentarios, "Quantidade de Comentários", Math.round(porcentagemComentarios) + "%");

		JFreeChart grafico = ChartFactory.createBarChart("Análise do arquivo " + nomeArquivo, "Porcentagem(%)",
				"Quantidade", barra, PlotOrientation.VERTICAL, true, true, false);

//		CategoryPlot barraItem = grafico.getCategoryPlot();
//		barraItem.getRenderer().setSeriesPaint(0, Color.BLUE);
//		barraItem.getRenderer().setSeriesPaint(1, Color.GREEN);
//		barraItem.getRenderer().setSeriesPaint(2, Color.ORANGE);
//		barraItem.getRenderer().setSeriesPaint(3, Color.RED);

		ChartPanel painel = new ChartPanel(grafico);
		criarFrameGrafico();
		painel.setVisible(true);
		add(painel);

	}

	public void criarGraficoBarrasHorizontal() {

		if (!validarCalculoPorcentagem()) {
			JOptionPane.showMessageDialog(null,
					"Não é possível gerar o gráfico, pois não há informações suficientes para a criação.");
			return;
		}

		DefaultCategoryDataset barra = new DefaultCategoryDataset();

		barra.setValue(qtdLoc, "Quantidade de linhas(LOC)", Math.round(porcentagemLoc) + "%");

		if (contemClasses)
			barra.setValue(qtdClasses, "Quantidade de Classes", Math.round(porcentagemClasses) + "%");

		if (contemMetodos)
			barra.setValue(qtdMetodos, "Quantidade de Métodos", Math.round(porcentagemMetodos) + "%");

		if (contemComentarios)
			barra.setValue(qtdComentarios, "Quantidade de Comentários", Math.round(porcentagemComentarios) + "%");

		JFreeChart grafico = ChartFactory.createBarChart("Análise do arquivo " + nomeArquivo, "Porcentagem(%)",
				"Quantidade", barra, PlotOrientation.HORIZONTAL, true, true, false);

//		CategoryPlot barraItem = grafico.getCategoryPlot();
//		barraItem.getRenderer().setSeriesPaint(0, Color.BLACK);
//		barraItem.getRenderer().setSeriesPaint(1, Color.WHITE);
//		barraItem.getRenderer().setSeriesPaint(2, Color.GRAY);
//		barraItem.getRenderer().setSeriesPaint(3, Color.CYAN);

		ChartPanel painel = new ChartPanel(grafico);

		criarFrameGrafico();
		painel.setVisible(true);
		add(painel);

	}

	private boolean validarCalculoPorcentagem() {

		if (qtdLoc == null || qtdLoc == 0.0) {
			return false;
		} else {
			somatorio += qtdLoc;
		}

		if (qtdClasses != null && qtdClasses > 0.0) {
			somatorio += qtdClasses;
			contemClasses = true;
		}

		if (qtdMetodos != null && qtdMetodos > 0.0) {
			somatorio += qtdMetodos;
			contemMetodos = true;
		}

		if (qtdComentarios != null && qtdComentarios > 0.0) {
			somatorio += qtdComentarios;
			contemComentarios = true;
		}

		if (contemClasses && !contemMetodos && !contemComentarios)
			return false;

		porcentagemLoc = (qtdLoc * 100) / somatorio;

		if (contemClasses)
			porcentagemClasses = (qtdClasses * 100) / somatorio;

		if (contemMetodos)
			porcentagemMetodos = (qtdMetodos * 100) / somatorio;

		if (contemComentarios)
			porcentagemComentarios = (qtdComentarios * 100) / somatorio;

		return true;
	}

}
