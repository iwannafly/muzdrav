package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.Pbol;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class BolListForm extends ModalForm {
	private static final long serialVersionUID = 4909914531060012030L;
	private CustomTable<Pbol, Pbol._Fields> tblPbol;
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnUpd;
	
	private int npasp;
	private int idObr;
	private int idGosp;

	public BolListForm() {
		super(true);
		
		setTitle("Больничный лист");
		setBounds(100, 100, 640, 480);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(horizontalBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontalBox.add(scrollPane);
		
		tblPbol = new CustomTable<>(true, true, Pbol.class, 4, "Причина открытия", 5, "Дата открытия", 6, "Дата закрытия", 7, "Пол (по уходу)", 8, "Возраст (по уходу)", 9, "Номер б/л");
		tblPbol.setDateField(1);
		tblPbol.setDateField(2);		
		tblPbol.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblPbol);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setAlignmentY(Component.TOP_ALIGNMENT);
		horizontalBox.add(verticalBox);
		
		btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(BolListForm.class.getResource("/ru/nkz/ivcgzo/clientViewSelect/resources/1331789242_Add.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pbol pbol = new Pbol();
				pbol.setNpasp(npasp);
				pbol.setPcod(MainForm.authInfo.getCpodr());
				pbol.setId_obr(idObr);
				pbol.setId_gosp(idGosp);
				pbol.setDataz(System.currentTimeMillis());
				pbol.setCdol(MainForm.authInfo.getCdol());
				pbol.setCod_sp(MainForm.authInfo.getPcod());
				try {
					pbol.setId(MainForm.tcl.AddPbol(pbol));
					tblPbol.addItem(pbol);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(BolListForm.this, "Не удалось добавить запись.");
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		btnAdd.setAlignmentY(Component.TOP_ALIGNMENT);
		verticalBox.add(btnAdd);
		
		btnDel = new JButton("");
		btnDel.setIcon(new ImageIcon(BolListForm.class.getResource("/ru/nkz/ivcgzo/clientViewSelect/resources/1331789259_Delete.png")));
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblPbol.getSelectedItem()!= null)
  					if (JOptionPane.showConfirmDialog(BolListForm.this, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						try {
							MainForm.tcl.DeletePbol(tblPbol.getSelectedItem().getId());
						} catch (KmiacServerException e1) {
							JOptionPane.showMessageDialog(BolListForm.this, "Не удалось удалить запись.");
							e1.printStackTrace();
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
							e1.printStackTrace();
						}
						
						try {
							tblPbol.setData(MainForm.tcl.getPbol(npasp));
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(BolListForm.this, "Не удалось загрузить больничный лист.");
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
							e1.printStackTrace();
						}
  					}
			}
		});
		btnDel.setAlignmentY(Component.TOP_ALIGNMENT);
		verticalBox.add(btnDel);
		
		btnUpd = new JButton("");
		btnUpd.setIcon(new ImageIcon(BolListForm.class.getResource("/ru/nkz/ivcgzo/clientViewSelect/resources/1341981970_Accept.png")));
		btnUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!DatComp()) {
						JOptionPane.showMessageDialog(BolListForm.this, "Дата начала больничного не может быть больше даты конца больничного", "Предупреждение", JOptionPane.ERROR_MESSAGE);
							return;
					}
					if (tblPbol.getSelectedItem() != null)
						MainForm.tcl.UpdatePbol(tblPbol.getSelectedItem());
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(BolListForm.this, "Не удалось сохранить запись.");
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		btnUpd.setAlignmentY(Component.TOP_ALIGNMENT);
		verticalBox.add(btnUpd);
	}
	
	@Override
	public Object getResults() {
		return null;
	}
	
	public void prepareForm(int npasp, int idObr, int idGosp) throws Exception {
		boolean modEnabled = (idObr != 0) || (idGosp != 0);
		this.npasp = npasp;
		this.idObr = idObr;
		this.idGosp = idGosp;
		
		try {
			tblPbol.setIntegerClassifierSelector(0, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_bl1));
			tblPbol.setIntegerClassifierSelector(3, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_z30));
			tblPbol.setData(MainForm.tcl.getPbol(npasp));
			btnAdd.setEnabled(modEnabled);
			btnDel.setEnabled(modEnabled);
			btnUpd.setEnabled(modEnabled);
		} catch (KmiacServerException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(BolListForm.this, "Не удалось загрузить больничный лист.");
			throw new Exception();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
			throw new Exception();
		}
	}
	
	public boolean DatComp() throws TException{
		if (tblPbol.getData().size() > 0){
			if ((tblPbol.getSelectedItem().getS_bl()>tblPbol.getSelectedItem().getPo_bl())&&(tblPbol.getSelectedItem().getPo_bl()!=0)) return false;
		}
		return true;
	}
}
