package ru.nkz.ivcgzo.clientManager.common.customFrame;

import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

public class HelpForm extends JDialog {
	private static final long serialVersionUID = -3095501803314345336L;
	private JEditorPane editorPane;
	
	public HelpForm() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Просмотр справки");
		setBounds(100, 100, 800, 600);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
	}
	
	public void showHelp(String url) {
		try {
			editorPane.setDocument(new HTMLDocument());
			editorPane.setPage(url);
			HTMLDocument doc = (HTMLDocument) editorPane.getDocument();
			while (doc.getElement("top-menu") == null) {
				Thread.sleep(100);
			}
			doc.removeElement(doc.getElement("top-menu"));
			doc.removeElement(doc.getElement("header"));
			doc.removeElement(doc.getElement("sidebar"));
			doc.removeElement(doc.getElement(doc.getDefaultRootElement(), HTML.Attribute.CLASS, "contextual"));
			doc.removeElement(doc.getElement(doc.getDefaultRootElement(), HTML.Attribute.CLASS, "attachments"));
			doc.removeElement(doc.getElement("ajax-indicator"));
			Enumeration<?> sen = doc.getStyleNames();
			while (sen.hasMoreElements())
				doc.removeStyle(((String) sen.nextElement()));
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Fuck you.");
		}
	}
}
