package ru.nkz.ivcgzo.clientOsm;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JLabel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

public class ShablonTextField extends JEditorPane {
	private static final long serialVersionUID = 2672332140862439691L;
	private JLabel label;
	public static ShablonTextField instance;
	private final int id_razd;
	private final int id_pok;
	private final ThriftIntegerClassifierList shabList;
	
	public ShablonTextField(int id_razd, int id_pok, ThriftIntegerClassifierList shabList) {
		super(); //вызов наследуемого конструктора (JEditorPane)
		setFocusAction();
		
		label = new JLabel(getLabelName(id_pok));
		
		this.id_razd = id_razd;
		this.id_pok = id_pok;
		this.shabList = shabList;
	}
	
	private void setFocusAction() {
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				try {
					instance = ShablonTextField.this;
					List<IntegerClassifier> textList = MainForm.tcl.getShablonTexts(id_razd, id_pok, MainForm.authInfo.getCdol());
					shabList.setData(textList);
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	private String getLabelName(int id_pok) {
		if (Vvod.pokNames != null)
			for (IntegerClassifier pok : Vvod.pokNames) {
				if (pok.getPcod() == id_pok)
					return pok.getName();
			}
		
		return String.format("Показатель №%d", id_pok);
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		label.setVisible(aFlag);
	}
	
	public int getIdPok() {
		return id_pok;
	}
}
