package principal;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * Classe représentant un flux qui écrira dans la textArea passée en paramètre.
 * @author Sacha
 *
 */
public class TextAreaOutputStream extends OutputStream{

	/**
	 * TextArea où sera écrit les différents octects du flux.
	 */
	private JTextArea textArea;
	
	/**
	 * Constructeur de la classe TextAreaOutputStream.
	 * @param textArea TextArea où sera écrit les différents octects du flux.
	 */
	public TextAreaOutputStream(JTextArea textArea) {
		this.textArea = textArea;
		
	}
	
	@Override
	public void write(int b) throws IOException {
		String s = new String(new byte[]{(byte)b},"ISO8859_1");
		this.textArea.append(s);
		this.textArea.setCaretPosition(textArea.getDocument().getLength());
	}

}
