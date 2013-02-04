package ru.nkz.ivcgzo.clientManager.common.customFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.sun.webpane.webkit.dom.CSSRuleImpl;
import com.sun.webpane.webkit.dom.CSSStyleSheetImpl;
import com.sun.webpane.webkit.dom.HTMLDocumentImpl;

public class HelpForm extends JDialog {
	private static final long serialVersionUID = -3095501803314345336L;
	private String redmineServerAddr;
	private JFXPanel fxPanel;
	
	public HelpForm(String redmineServerAddr) {
		this.redmineServerAddr = redmineServerAddr;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Просмотр справки");
		setBounds(100, 100, 800, 600);
		
		fxPanel = new JFXPanel();
		getContentPane().add(fxPanel, BorderLayout.CENTER);
	}
	
	public void showHelp(final String url) {
		try {
	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
					final WebView webView = new WebView();
					webView.setContextMenuEnabled(false);
					webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
						@Override
						public void changed(ObservableValue<? extends State> arg0, State arg1, State arg2) {
							if (arg2 == State.SUCCEEDED) {
								Element re;
								Element ce;
								HTMLDocumentImpl d = (HTMLDocumentImpl) webView.getEngine().getDocument();
								
								re = d.getElementById("wrapper3");
								ce = d.getElementById("top-menu");
								if (ce != null) re.removeChild(ce);
								
								re = d.getElementById("header");
								ce = d.getElementById("quick-search");
								if (ce != null) re.removeChild(ce);
								ce = d.getElementById("main-menu");
								if (ce != null) re.removeChild(ce);
								
								re = d.getElementById("main");
								ce = d.getElementById("sidebar");
								if (ce != null) re.removeChild(ce);
								
								re = d.getElementById("content");
								ce = (Element) d.getElementsByClassName("contextual").item(0);
								if (ce != null) re.removeChild(ce);
								ce = (Element) d.getElementsByClassName("attachments").item(0);
								if (ce != null) re.removeChild(ce);
								
								for (int i = 0; i < d.getStyleSheets().getLength(); i++) {
									CSSStyleSheetImpl ss = (CSSStyleSheetImpl) d.getStyleSheets().item(i);
									
									for (int j = 0; j < ss.getRules().getLength(); j++) {
										if (((CSSRuleImpl) ss.getRules().item(j)).getCssText().endsWith("width: 70%; overflow-x: auto; overflow-y: auto; background-position: initial initial; background-repeat: initial initial; }")) {
											ss.removeRule(j);
											ss.insertRule("#content { background-image: initial; background-attachment: initial; background-origin: initial; background-clip: initial; background-color: rgb(255, 255, 255); border-right-width: 1px; border-right-style: solid; border-right-color: rgb(187, 187, 187); border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(187, 187, 187); width: 98%; overflow-x: auto; overflow-y: auto; background-position: initial initial; background-repeat: initial initial; }", j);
										}
									}
								}
								
								EventListener el = new EventListener() {
									@Override
									public void handleEvent(Event evt) {
										StringSelection ss = new StringSelection(((Element) evt.getTarget()).toString());
										evt.preventDefault();
										Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
										JOptionPane.showMessageDialog(HelpForm.this, "Переходить по внешним ссылкам нельзя.\nСсылка скопирована в буфер обмена.");
									}
								};
								
								NodeList al = d.getElementsByTagName("a");
								for (int i = 0; i < al.getLength(); i++) {
									if (!(al.item(i).toString().startsWith(redmineServerAddr) || al.item(i).toString().toLowerCase().equals("javascript:history.back()")))
										((EventTarget) al.item(i)).addEventListener("click", el, true);
								}
								
								EventQueue.invokeLater(new Runnable() {
									@Override
									public void run() {
										setVisible(true);
									}
								});
							}
						}
					});
					webView.getEngine().load(redmineServerAddr + '/' + url);
					fxPanel.setScene(new Scene(webView));
	            }
	        });
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Справка недоступна.");
		}
	}
}
