package principal;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream{

	private JTextArea textArea;
	
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
