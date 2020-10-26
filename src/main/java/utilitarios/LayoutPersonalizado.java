package utilitarios;

public class LayoutPersonalizado {

	public static void determinarLayoutPersonalizado(String layout) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if (layout.equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}

}
