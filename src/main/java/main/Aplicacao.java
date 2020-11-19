package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import telas.MenuInicial;
import utilitarios.LayoutPersonalizado;

class Aplicacao {

	private static final Logger LOGGER = LogManager.getLogger(Aplicacao.class.getName());

	public static void main(String[] args) {
		try {
			LOGGER.info("Iniciando aplicação...");
			MenuInicial window = new MenuInicial();
			LayoutPersonalizado.determinarLayoutPersonalizado("Nimbus");
			window.initialize();
			window.frameTelaInicial.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}