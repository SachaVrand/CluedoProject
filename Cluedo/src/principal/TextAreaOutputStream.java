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
		this.textArea.append(String.valueOf((char) b));
		this.textArea.setCaretPosition(textArea.getDocument().getLength());	
	}

}
