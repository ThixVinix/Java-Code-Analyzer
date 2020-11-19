package plainDocuments;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import utilitarios.Util;

public class DocumentoCampoNumerico extends PlainDocument {

	private static final long serialVersionUID = 4443168774261991896L;
	private Integer tamanhoMax;

	public DocumentoCampoNumerico(Integer tamanhoMax) {
		this.tamanhoMax = tamanhoMax;
	}
	
	public DocumentoCampoNumerico() {
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		if (!Util.isNumerico(str))
			return;

		String stringAntiga = getText(0, getLength());
		int tamanhoNovo = stringAntiga.length() + str.length();

		if (tamanhoMax != null) {
			if (tamanhoNovo <= tamanhoMax) {
				super.insertString(offs, str, a);
			} else {
				super.insertString(offs, "", a);
			}
		}
	}
}
