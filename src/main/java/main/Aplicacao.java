package main;

import telas.MenuInicial;
import utilitarios.LayoutPersonalizado;

class Aplicacao {

	public static void main(String[] args) {
		try {
			MenuInicial window = new MenuInicial();
			LayoutPersonalizado.determinarLayoutPersonalizado("Nimbus");
			window.initialize();
			window.frameTelaInicial.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}