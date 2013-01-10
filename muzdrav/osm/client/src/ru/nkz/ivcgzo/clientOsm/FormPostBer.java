package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.KartaBer;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PrdslNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;
import javax.swing.border.LineBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
//import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
//import ru.nkz.ivcgzo.;

public class FormPostBer extends JFrame {
	private static final long serialVersionUID = -1244773743749481104L;
	public static RdSlStruct rdSlStruct;
	private JPanel contentPane;
	private JTextField TNKart;
    private int oslrod;
    private int osostp;
    private int or1;
    private int or2;
    private int or3;
    private int or4;
    private int or5;
    private int or6;
    private int or7;
    private int or8;
    private int iw1;
    private int iw2;
    private int iw3;
    private String oslname;
    private String oslcode;
    private FormRdInf inform;
    private FormRdDin dinform;
    private JSpinner SVes;
    private JSpinner SDsp;
    private JSpinner SDcr;
    private JSpinner SDtroch;
    private JSpinner SCext;
    private JSpinner SindSol;
    private CustomDateEditor SDataPos;
    private CustomDateEditor SDataOsl;
    private CustomDateEditor SDataM;
    private CustomDateEditor TDataSn;
    private CustomDateEditor SDataRod;
    private CustomDateEditor SDataSert;
    private JSpinner SYavka;
    private JSpinner SCDiag;
    private JSpinner SCvera;
    private JSpinner SRost;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBRod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPrishSn;
	/**
	 * Launch the application.
	 */
	private JCheckBox CBKontr;
	private JTextField fam;
	private JTextField im;
	private JTextField ot;
	private JTextField TSSert;
	private JTextField TNSert;
	private int mes;
	private Date datr;
	private JCheckBox ChBeko;
	private JCheckBox ChBPred;
	private JCheckBox ChBRub;
	private JCheckBox CHosp1;
	private JCheckBox CHosp2;
	private JCheckBox CHosp3;
	private JCheckBox CHosp4;
	private JCheckBox CHosp5;
	private JCheckBox CHosp6;
	private JCheckBox CHosp7;
	private JCheckBox CHosp8;
	private JCheckBox CHosp9;
	private JCheckBox CHosp10;
	private JPanel panel_7;
	private JLabel label;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel panel_10;

	/**
	 * Create the frame.
	 */
	public FormPostBer() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Данные о случае беременности");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				System.out.println("постановка по +");		
				System.out.println(Vvod.zapVr.getNpasp());		
				fam.setText(Vvod.zapVr.getFam());
				im.setText(Vvod.zapVr.getIm());
				ot.setText(Vvod.zapVr.getOth());
				
				try {
					rdSlStruct = new RdSlStruct();
					setDefaultValues();
					RdInfStruct rdinf = new RdInfStruct();
					rdinf.setNpasp(Vvod.zapVr.getNpasp());
					rdinf.setDataz(System.currentTimeMillis());
		            MainForm.tcl.AddRdInf(rdinf);
					rdSlStruct = MainForm.tcl.getRdSlInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
					setPostBerData();
				} 
//				catch (PrdslNotFoundException e1) {
//					try {
//						rdSlStruct.setId(MainForm.tcl.AddRdSl(rdSlStruct));
//						setPostBerData();
//					} catch (KmiacServerException e2) {
//						JOptionPane.showMessageDialog(FormPostBer.this, "Не удалось поставить на учет", "Ошибка", JOptionPane.ERROR_MESSAGE);
//					} catch (TException e2) {
//						e2.printStackTrace();
//						MainForm.conMan.reconnect(e2);
//					}
//				} 
				catch (KmiacServerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(FormPostBer.this, e1.getLocalizedMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
				
				setVisible(true);	
			}
		});
		
		setTitle("Данные о случае беременности");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1032, 853);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setDefaultValues();
					rdSlStruct.setId(MainForm.tcl.AddRdSl(rdSlStruct));
					setPostBerData();
				} catch (KmiacServerException e2) {
					JOptionPane.showMessageDialog(FormPostBer.this, "Не удалось поставить на учет", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e2) {
					e2.printStackTrace();
					MainForm.conMan.reconnect(e2);
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		btnNewButton.setToolTipText("Постановка на учет");
		
		JButton ButSave = new JButton("");
		ButSave.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		ButSave.setToolTipText("Сохранить");
		ButSave.addActionListener(new ActionListener() {
            private void calcOslrod(){
//    			oslrod=0;
//            if (CBKrov.isSelected()){oslrod=oslrod+1;}
//            if (CBEkl.isSelected()){oslrod=oslrod+2;}
//            if (CBGnoin.isSelected()){oslrod=oslrod+4;}
//            if (CBTromb.isSelected()){oslrod=oslrod+8;}
//            if (CDKesar.isSelected()){oslrod=oslrod+16;}
//            if (CBAkush.isSelected()){oslrod=oslrod+32;}
//            if (CBIiiiv.isSelected()){oslrod=oslrod+64;}
//            if (CBRazrProm.isSelected()){oslrod=oslrod+128;}
//			System.out.println("состояние плода");		
            osostp = 0;
//			System.out.println(osostp);		
            if (CHosp1.isSelected()){osostp=osostp+1;}
            if (CHosp2.isSelected()){osostp=osostp+2;}
            if (CHosp3.isSelected()){osostp=osostp+4;}
            if (CHosp4.isSelected()){osostp=osostp+8;}
            if (CHosp5.isSelected()){osostp=osostp+16;}
            if (CHosp6.isSelected()){osostp=osostp+32;}
            if (CHosp7.isSelected()){osostp=osostp+64;}
            if (CHosp8.isSelected()){osostp=osostp+128;}
            if (CHosp9.isSelected()){osostp=osostp+256;}
            if (CHosp10.isSelected()){osostp=osostp+512;}
            };
			public void actionPerformed(ActionEvent arg0) {
				try {
//					System.out.println("сохранение данных номер визита");		
//					System.out.println(Vvod.zapVr.id_pvizit);		
			rdSlStruct.setId_pvizit(Vvod.zapVr.id_pvizit);
//			rdSlStruct.setAbort((int) SKolAb.getValue());
//			rdSlStruct.setCmer((int) SMert.getValue());
			rdSlStruct.setCext((int) SCext.getModel().getValue());
 			if (SDataM.getDate() != null)
			rdSlStruct.setDataM( SDataM.getDate().getTime());
 			if (SDataOsl.getDate() != null)
			rdSlStruct.setDataosl( SDataOsl.getDate().getTime());
 			if (TDataSn.getDate() != null)
			rdSlStruct.setDatasn( TDataSn.getDate().getTime());
 			if (SDataRod.getDate() != null)
			rdSlStruct.setDataZs( SDataRod.getDate().getTime());
 			if (SDataSert.getDate() != null)
			rdSlStruct.setDatasert( SDataSert.getDate().getTime());
			rdSlStruct.setSsert(getTextOrNull(TSSert.getText()));
			rdSlStruct.setNsert(getTextOrNull(TNSert.getText()));
//			rdSlStruct.setPrrod(getTextOrNull(TPrRod.getText()));
//			rdSlStruct.setPrrod(TPrRod.getText());
// 			if (TDataab.getDate() != null)
//			rdSlStruct.setDataab( TDataab.getDate().getTime());
// 			rdSlStruct.setSrokab((int) SSrokA.getModel().getValue());
			rdSlStruct.setSsert(getTextOrNull(TSSert.getText()));
			rdSlStruct.setNsert(getTextOrNull(TNSert.getText()));
 			if (SDataPos.getDate() != null)
			rdSlStruct.setDatay(SDataPos.getDate().getTime());
			rdSlStruct.setKont(CBKontr.isSelected());
			rdSlStruct.setEko(ChBeko.isSelected());
			rdSlStruct.setRub(ChBRub.isSelected());
			rdSlStruct.setPredp(ChBPred.isSelected());
//			rdSlStruct.setDeti((int) SKolDet.getModel().getValue());
			rdSlStruct.setDsp((int) (SDsp.getModel()).getValue());
			rdSlStruct.setDsr((int) SDcr.getModel().getValue());
			rdSlStruct.setDTroch((int) SDtroch.getModel().getValue());
			rdSlStruct.setIndsol((int) SindSol.getModel().getValue());
//			rdSlStruct.setShet((int) SKolBer.getModel().getValue());
//			rdSlStruct.setKolrod((int) SParRod.getModel().getValue());
//			rdSlStruct.setPolj((int) SPolJ.getModel().getValue());
//			rdSlStruct.setPrmen((int) SMenC.getModel().getValue());
			rdSlStruct.setRost((int) SRost.getModel().getValue());
			rdSlStruct.setVesd((Double) SVes.getModel().getValue());
			rdSlStruct.setYavka1((int) SYavka.getModel().getValue());
           	rdSlStruct.setCdiagt((int) SCDiag.getModel().getValue());
           	rdSlStruct.setCvera((int) SCvera.getModel().getValue());
           	rdSlStruct.setDataz(System.currentTimeMillis());
			calcOslrod();
			rdSlStruct.setOslrod(oslrod);
			rdSlStruct.setOsp(osostp);
//			if (CBOslAb.getSelectedPcod() != null)
//				rdSlStruct.setOslab(CBOslAb.getSelectedPcod());
//				else rdSlStruct.unsetOslab();
			if (CBRod.getSelectedPcod() != null)
				rdSlStruct.setPlrod(CBRod.getSelectedPcod());
				else rdSlStruct.unsetPlrod();
			if (CBPrishSn.getSelectedPcod() != null)
				rdSlStruct.setIshod(CBPrishSn.getSelectedPcod());
				else rdSlStruct.unsetIshod();
	System.out.println(rdSlStruct);		
		//		JOptionPane.showMessageDialog(FormPostBer.this, "Ошибка обновления");
				MainForm.tcl.UpdateRdSl(rdSlStruct);
			} catch (KmiacServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} 		
		});
		
		JButton ButDelete = new JButton("");
		ButDelete.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		ButDelete.setToolTipText("Удалить");
		ButDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainForm.tcl.DeleteRdSl(rdSlStruct.getId(), rdSlStruct.getNpasp());
					MainForm.tcl.DeleteRdDin(rdSlStruct.getId());
									} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					MainForm.conMan.reconnect(e);
				}
			}
		});
		
		
		fam = new JTextField();
		fam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		fam.setColumns(10);
//		fam.setText(Vvod.zapVr.fam);
		
		im = new JTextField();
		im.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
//		im.setText("");
		im.setColumns(10);
//		im.setText(Vvod.zapVr.im);
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
//		ot.setText("");
		ot.setColumns(10);
		
		JButton BPeshOK = new JButton("Печать обменной карты");
		BPeshOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					KartaBer kartaber = new KartaBer();
					kartaber.setId_pos(Vvod.pvizitAmb.getId());
					kartaber.setId_pvizit(Vvod.pvizit.getId());
					kartaber.setNpasp(Vvod.zapVr.getNpasp());
					kartaber.setId_rd_sl(0);
					String servPath = MainForm.tcl.printKartaBer(kartaber);
					String cliPath;
					oslname = "kartl"+String.valueOf(rdSlStruct.getId());
					cliPath = File.createTempFile(oslname, ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInEditor(cliPath, false);

			}
			catch (TException e1) {
				e1.printStackTrace();
				MainForm.conMan.reconnect(e1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			}
		});
		BPeshOK.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		
		JButton btnNewButton_1 = new JButton("Динамическое наблюдение");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dinform = new FormRdDin();
				dinform.setVisible(true);
}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
//		ot.setText(Vvod.zapVr.oth);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ButSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ButDelete)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(BPeshOK, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1011, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(3, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(BPeshOK)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_1))
						.addComponent(btnNewButton)
						.addComponent(ButDelete)
						.addComponent(ButSave))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 624, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(57, Short.MAX_VALUE))
		);
//		CBKontr.setSelected(rdSlStruct.kont == true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(212, 208, 200));
		
		JLabel LVes = new JLabel("Вес при 1 явке");
		LVes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LTaz = new JLabel("Таз:");
		LTaz.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblDsp = new JLabel("DSP");
		lblDsp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblDcr = new JLabel("DCR");
		lblDcr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblDtroch = new JLabel("DTROCH");
		lblDtroch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblCext = new JLabel("C.ext");
		lblCext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LIndSol = new JLabel("Индекс Соловьева");
		LIndSol.setFont(new Font("Tahoma", Font.PLAIN, 12));

		SVes = new JSpinner();
		SVes.setFont(new Font("Tahoma", Font.BOLD, 12));
		SVes.setModel(new SpinnerNumberModel(0.0, 0.0, 250.0, 1.0));
		
		SDsp = new JSpinner();
		SDsp.setFont(new Font("Tahoma", Font.BOLD, 12));
		SDsp.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		
		SDcr = new JSpinner();
		SDcr.setFont(new Font("Tahoma", Font.BOLD, 12));
		SDcr.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		
		SDtroch = new JSpinner();
		SDtroch.setFont(new Font("Tahoma", Font.BOLD, 12));
		SDtroch.setModel(new SpinnerNumberModel(0,0,33,1));
		
		SCext = new JSpinner();
		SCext.setFont(new Font("Tahoma", Font.BOLD, 12));
		SCext.setModel(new SpinnerNumberModel(0, 0, 35, 1));
		
		SindSol = new JSpinner();
		SindSol.setFont(new Font("Tahoma", Font.BOLD, 12));
		SindSol.setModel(new SpinnerNumberModel(0, 0, 20,1));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(0, 0, 0)));
		
//		JLabel LDataSn = new JLabel("New label");
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addGap(69)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(panel_8, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
										.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)))
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(635))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addGap(136))
		);
		
		JLabel LPrish = new JLabel("Причина снятия с учета");
		LPrish.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LDataSn = new JLabel("Дата снятия с учета");
		LDataSn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TDataSn = new CustomDateEditor();
		TDataSn.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBPrishSn = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db7);
		CBPrishSn.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGap(33)
							.addComponent(LDataSn))
						.addGroup(gl_panel_10.createSequentialGroup()
							.addContainerGap()
							.addComponent(LPrish)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addComponent(TDataSn, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel_10.setVerticalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LPrish, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_10.createParallelGroup(Alignment.BASELINE)
						.addComponent(TDataSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LDataSn, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_10.setLayout(gl_panel_10);
		panel_10.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{LPrish, LDataSn, CBPrishSn, TDataSn}));
		
		JLabel lblNewLabel = new JLabel("Дата выдачи родового сертификата");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_1 = new JLabel("Серия");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_2 = new JLabel("Номер");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		SDataSert = new CustomDateEditor();
		SDataSert.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		TSSert = new JTextField();
		TSSert.setColumns(10);
		
		TNSert = new JTextField();
		TNSert.setFont(new Font("Tahoma", Font.BOLD, 12));
		TNSert.setColumns(10);
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_9.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_9.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(lblNewLabel_2)
							.addGap(18)
							.addComponent(TNSert, 0, 0, Short.MAX_VALUE)))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_9.setLayout(gl_panel_9);
		panel_9.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, lblNewLabel_1, lblNewLabel_2, SDataSert, TSSert, TNSert}));
		
		JLabel LPlanRod = new JLabel("Планируемые роды");
		LPlanRod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LDataOsl = new JLabel("Дата 1-го шевеления плода");
		LDataOsl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LDataPlRod = new JLabel("Планируемая дата родов");
		LDataPlRod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		CBRod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db8);
		CBRod.setIllegibleSearch(true);
		CBRod.setForeground(Color.BLACK);
		CBRod.setBackground(Color.BLACK);
		CBRod.setFont(new Font("Tahoma", Font.BOLD, 12));
		
				SDataRod = new CustomDateEditor();
				SDataRod.setFont(new Font("Tahoma", Font.BOLD, 12));
				SDataRod.setColumns(10);
		
		SDataOsl = new CustomDateEditor();
		SDataOsl.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addComponent(LDataOsl)
						.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel_8.createSequentialGroup()
								.addComponent(LPlanRod, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addGap(18))
							.addComponent(LDataPlRod, Alignment.LEADING)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(SDataRod, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
							.addComponent(SDataOsl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LPlanRod))
					.addGap(5)
					.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addComponent(LDataOsl)
						.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap(23, Short.MAX_VALUE)
					.addComponent(LDataPlRod, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(42))
		);
		panel_8.setLayout(gl_panel_8);
		panel_8.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{LDataOsl, LPlanRod, LDataPlRod, CBRod, SDataRod, SDataOsl}));
		
		 CHosp1 = new JCheckBox("Несоответствие ВДМ гистационному сроку");
		 CHosp1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp2 = new JCheckBox("Отставание фетометрических показателей от гест. срока");
		 CHosp2.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp3 = new JCheckBox("ЧСС плода 110 ударов  и менее");
		 CHosp3.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp4 = new JCheckBox("ЧСС плода 160 ударов  и более");
		 CHosp4.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp5 = new JCheckBox("Многоводие");
		 CHosp5.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp6 = new JCheckBox("Маловодие");
		 CHosp6.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp7 = new JCheckBox("Нарушение кровотока в артерии пуповины");
		 CHosp7.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp8 = new JCheckBox("Нулевой или реверсивный кровоток");
		 CHosp8.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp9 = new JCheckBox("Средняя оценка КТГ по Fisher 6 и менее ");
		 CHosp9.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 CHosp10 = new JCheckBox("Ареактивный нестрессовый тест");
		 CHosp10.setFont(new Font("Tahoma", Font.ITALIC, 12));
		 
		 label = new JLabel("Оценка состояния плода");
		 label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		 GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		 gl_panel_7.setHorizontalGroup(
		 	gl_panel_7.createParallelGroup(Alignment.TRAILING)
		 		.addGroup(gl_panel_7.createSequentialGroup()
		 			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 				.addGroup(gl_panel_7.createSequentialGroup()
		 					.addContainerGap()
		 					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 						.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 							.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 								.addGroup(gl_panel_7.createSequentialGroup()
		 									.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 										.addComponent(CHosp1)
		 										.addComponent(CHosp3))
		 									.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE))
		 								.addGroup(gl_panel_7.createSequentialGroup()
		 									.addComponent(CHosp9)
		 									.addGap(31)))
		 							.addGroup(gl_panel_7.createSequentialGroup()
		 								.addComponent(CHosp5)
		 								.addPreferredGap(ComponentPlacement.RELATED)))
		 						.addGroup(Alignment.TRAILING, gl_panel_7.createSequentialGroup()
		 							.addComponent(CHosp8)
		 							.addGap(120)))
		 					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 						.addComponent(CHosp7, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
		 						.addComponent(CHosp10)
		 						.addComponent(CHosp6)
		 						.addComponent(CHosp4)
		 						.addComponent(CHosp2)))
		 				.addGroup(gl_panel_7.createSequentialGroup()
		 					.addGap(264)
		 					.addComponent(label, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)))
		 			.addContainerGap())
		 );
		 gl_panel_7.setVerticalGroup(
		 	gl_panel_7.createParallelGroup(Alignment.LEADING)
		 		.addGroup(gl_panel_7.createSequentialGroup()
		 			.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
		 			.addPreferredGap(ComponentPlacement.RELATED)
		 			.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
		 				.addComponent(CHosp1)
		 				.addComponent(CHosp2))
		 			.addPreferredGap(ComponentPlacement.RELATED)
		 			.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
		 				.addComponent(CHosp4)
		 				.addComponent(CHosp3))
		 			.addPreferredGap(ComponentPlacement.RELATED)
		 			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 				.addComponent(CHosp6)
		 				.addComponent(CHosp5))
		 			.addPreferredGap(ComponentPlacement.RELATED)
		 			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 				.addComponent(CHosp8)
		 				.addComponent(CHosp7))
		 			.addPreferredGap(ComponentPlacement.UNRELATED)
		 			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
		 				.addComponent(CHosp9)
		 				.addComponent(CHosp10))
		 			.addContainerGap(11, Short.MAX_VALUE))
		 );
		 panel_7.setLayout(gl_panel_7);
		 panel_7.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{label, CHosp1, CHosp2, CHosp3, CHosp4, CHosp5, CHosp6, CHosp7, CHosp8, CHosp9, CHosp10}));
		
		JLabel LNslu = new JLabel("Номер обменной карты");
		LNslu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TNKart = new JTextField();
		TNKart.setFont(new Font("Tahoma", Font.BOLD, 12));
		TNKart.setColumns(10);
		
		JLabel LDatap = new JLabel("Дата первого посещения");
		LDatap.setFont(new Font("Tahoma", Font.PLAIN, 12));
		SDataPos = new CustomDateEditor();
		SDataPos.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel LYavka = new JLabel("Первая явка (недель)");
		LYavka.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
				SYavka = new JSpinner();
				SYavka.setFont(new Font("Tahoma", Font.BOLD, 12));
				SYavka.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent arg0) {
					/*	if (rdSlStruct.getDataZs() = null)*/ {
				        rdSlStruct.setDataZs(rdSlStruct.getDatay()+(280-(rdSlStruct.getYavka1()*7))*864*100000);
					SDataRod.setDate(rdSlStruct.getDataZs());}
					}
				});
				SYavka.setModel(new SpinnerNumberModel(0,0, 40,1));
		
		JLabel LDataMes = new JLabel("Дата последних месячных");
		LDataMes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		SDataM = new CustomDateEditor();
		SDataM.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(LDataMes)
								.addComponent(LYavka)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(8)
							.addComponent(LNslu))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(7)
							.addComponent(LDatap)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
							.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(LNslu)
							.addGap(9)))
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(LDatap)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(LYavka)
						.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(LDataMes)
						.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		panel_4.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{LNslu, LDatap, LDataMes, LYavka, TNKart, SDataPos, SYavka, SDataM}));
		
		 CBKontr = new JCheckBox("Контрацепция");
		 CBKontr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBeko = new JCheckBox("Беременность после ЕКО");
		ChBeko.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBPred = new JCheckBox("Предгравидарная подготовка");
		ChBPred.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBRub = new JCheckBox("Рубец на матке");
		ChBRub.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(ChBeko)
						.addComponent(CBKontr))
					.addGap(56)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(ChBPred)
						.addComponent(ChBRub))
					.addContainerGap(145, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(ChBPred)
						.addComponent(CBKontr))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(ChBRub)
						.addComponent(ChBeko))
					.addContainerGap(64, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("C.Diag");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblCvera = new JLabel("C.vera");
		lblCvera.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		SCDiag = new JSpinner();
		SCDiag.setFont(new Font("Tahoma", Font.BOLD, 12));
		SCDiag.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		SCvera = new JSpinner();
		SCvera.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel LRost = new JLabel("Рост");
		LRost.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		SRost = new JSpinner();
		SRost.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(LTaz)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(LIndSol)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(LRost)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SRost, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
								.addComponent(LVes)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDsp, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblDtroch)
										.addComponent(lblDcr))
									.addGap(4)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(SDtroch, Alignment.LEADING)
											.addComponent(SDcr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))))
								.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(lblCvera)
										.addGap(18)
										.addComponent(SCvera))
									.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
										.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblCext)
											.addComponent(lblNewLabel_3))
										.addGap(18)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
											.addComponent(SCDiag)
											.addComponent(SCext, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(SindSol)
								.addComponent(SVes, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LRost)
						.addComponent(SRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LVes)
						.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LIndSol)
						.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(LTaz)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDsp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDcr)
						.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDtroch)
						.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCext)
						.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_3)
						.addComponent(SCDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCvera)
						.addComponent(SCvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		panel_1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblDcr, LRost, lblCext, LTaz, lblDsp, lblNewLabel_3, LVes, LIndSol, lblDtroch, lblCvera, SRost, SVes, SindSol, SDsp, SDcr, SDtroch, SCext, SCDiag, SCvera}));
		panel.setLayout(gl_panel);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel_4, panel_3, panel_1, panel_8, panel_9, panel_7, panel_10, LDataOsl, LPlanRod, LDataPlRod, CBRod, SDataRod, SDataOsl, CHosp1, CHosp2, CHosp3, CHosp4, CHosp5, CHosp6, CHosp8, CHosp10, CHosp9, CHosp7, label, LNslu, LDatap, LDataMes, LYavka, TNKart, SDataM, SDataPos, SYavka, CBKontr, ChBeko, ChBPred, ChBRub, lblDcr, LRost, SRost, lblCext, LTaz, lblDsp, SCext, lblNewLabel_3, SDsp, LVes, SVes, SCDiag, SDcr, LIndSol, lblDtroch, lblCvera, SindSol, SCvera, SDtroch, lblNewLabel, SDataSert, lblNewLabel_1, TSSert, lblNewLabel_2, TNSert, LPrish, LDataSn, TDataSn, CBPrishSn}));
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setDefaultValues() {
	try {
//		System.out.println("начальные значения");		
	System.out.println(Vvod.zapVr.id_pvizit);		
		rdSlStruct.setId_pvizit(Vvod.zapVr.getId_pvizit());
		rdSlStruct.setNpasp(Vvod.zapVr.getNpasp());
		rdSlStruct.setCext(25);
		rdSlStruct.setDsp(25);
		rdSlStruct.setDsr(28);
		rdSlStruct.setDTroch(31);
		rdSlStruct.setIndsol(15);
		rdSlStruct.setKolrod(1);
		rdSlStruct.setShet(1);
		rdSlStruct.setAbort(0);
		rdSlStruct.setDeti(0);
		rdSlStruct.setCmer(0);
		rdSlStruct.setRost(160);
		rdSlStruct.setYavka1(4);
		rdSlStruct.setVozmen(11);
		rdSlStruct.setPrmen(28);
		rdSlStruct.setPolj(18);
		rdSlStruct.setCdiagt(5);
		rdSlStruct.setCvera(11);
		rdSlStruct.setVesd(60);
		rdSlStruct.setOslab(null);
		rdSlStruct.setPrrod(null);
//		rdSlStruct.setIshod((Integer) null);
		rdSlStruct.setNsert(null);
		rdSlStruct.setSsert(null);
//		rdSlStruct.setDatasert((Long) null);
//		rdSlStruct.setDatasn((Long) null);
		rdSlStruct.setSrokab(0);
		rdSlStruct.setEko(false);
		rdSlStruct.setRub(false);
		rdSlStruct.setPredp(false);
		rdSlStruct.setOsp(0);
		rdSlStruct.setDataM(System.currentTimeMillis());
		rdSlStruct.setDatay(System.currentTimeMillis());
//		rdSlStruct.setDataosl(System.currentTimeMillis());
//		rdSlStruct.setDatasn(System.currentTimeMillis());
		rdSlStruct.setDataz(System.currentTimeMillis());
        rdSlStruct.setDataZs(System.currentTimeMillis()+217728*100000);
//		Calendar cal1 = Calendar.getInstance();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка создания записи", JOptionPane.ERROR_MESSAGE);
		
	}
	
	}

	private void setPostBerData() {
		//SRost.setValue(rdSlStruct.getRost());
		try {
			SVes.setValue(rdSlStruct.getVesd());
			SDsp.setValue(rdSlStruct.getDsp());
			SDcr.setValue(rdSlStruct.getDsr());
			SDtroch.setValue(rdSlStruct.getDTroch());
			SCext.setValue(rdSlStruct.getCext());
//			SMert.setValue(rdSlStruct.getCmer());
			SindSol.setValue(rdSlStruct.getIndsol());
			SDataPos.setDate(rdSlStruct.getDatay());
			if (rdSlStruct.getDatay() == 0)
			SDataPos.setText(null);
			SDataRod.setDate(rdSlStruct.getDataZs());
			if (rdSlStruct.getDataZs() == 0)
			SDataRod.setText(null);
			SDataSert.setDate(rdSlStruct.getDatasert());
			if (rdSlStruct.getDatasert() == 0)
			SDataSert.setText(null);
			TSSert.setText(rdSlStruct.ssert);
			TNSert.setText(rdSlStruct.nsert);
//			SParRod.setValue(rdSlStruct.getKolrod());
//			SKolBer.setValue(rdSlStruct.getShet());
//			TDataab.setDate(rdSlStruct.getDataab());
//			if (rdSlStruct.getDataab() == 0)
//			TDataab.setText(null);
			TDataSn.setDate(rdSlStruct.getDatasn());
			if (rdSlStruct.getDatasn() == 0)
			TDataSn.setText(null);
			TSSert.setText(rdSlStruct.ssert);
			TNSert.setText(rdSlStruct.nsert);
//			TPrRod.setText(rdSlStruct.prrod);
//			SParRod.setValue(rdSlStruct.getKolrod());
//			SKolBer.setValue(rdSlStruct.getShet());
			SDataOsl.setDate(rdSlStruct.getDataosl());
			if (rdSlStruct.getDataosl() == 0)
			SDataOsl.setText(null);	
			SYavka.setValue(rdSlStruct.getYavka1());
			SRost.setValue(rdSlStruct.getRost());
			SDataM.setDate(rdSlStruct.getDataM());
			if (rdSlStruct.getDataM() == 0)
			SDataM.setText(null);
//			SKolAb.setValue(rdSlStruct.getAbort());
//			SVozMen.setValue(rdSlStruct.getVozmen());
//			SMenC.setValue(rdSlStruct.getPrmen());
//			SKolDet.setValue(rdSlStruct.getDeti());
//			SPolJ.setValue(rdSlStruct.getPolj());
//			SSrokA.setValue(rdSlStruct.getSrokab());
			SCDiag.setValue(rdSlStruct.getCdiagt());
			SCvera.setValue(rdSlStruct.getCvera());
			oslrod = rdSlStruct.getOslrod();
			osostp = rdSlStruct.getOsp();
//			if(rdSlStruct.isSetOslab())
//			CBOslAb.setSelectedPcod(rdSlStruct.getOslab());
//			else CBOslAb.setSelectedItem(null);
			if (rdSlStruct.isSetPlrod())
			CBRod.setSelectedPcod(rdSlStruct.getPlrod());
			else CBRod.setSelectedItem(null);
			if (rdSlStruct.isSetIshod())
			CBPrishSn.setSelectedPcod(rdSlStruct.getIshod());
			else CBPrishSn.setSelectedItem(null);
			TNKart.setText(String.valueOf(rdSlStruct.getId()));
			method2();
/*			CBKrov.setSelected(or1 == 1);
			CBEkl.setSelected(or2 == 1);
			CBGnoin.setSelected(or3 == 1);
			CBTromb.setSelected(or4 == 1);
			CDKesar.setSelected(or5 == 1);
			CBAkush.setSelected(or6 == 1);
			CBIiiiv.setSelected(or7 == 1);
			CBRazrProm.setSelected(or8 == 1);*/
			CBKontr.setSelected(rdSlStruct.isKont());
			ChBeko.setSelected(rdSlStruct.isEko());
			ChBRub.setSelected(rdSlStruct.isRub());
			ChBPred.setSelected(rdSlStruct.isPredp());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка ссылки на запись", JOptionPane.ERROR_MESSAGE);
		}
}
	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
	private void method2(){
//		if ((oslrod-128)>=0)
//		{CBRazrProm.setSelected(true);   iw1=oslrod-128;}
//		else {CBRazrProm.setSelected(false);iw1=oslrod;}
//		if ((iw1-64)>=0)
//		{CBIiiiv.setSelected(true);		iw1=iw1-64;}
//		else {CBIiiiv.setSelected(false);iw1=iw1;}
//		if ((iw1-32)>=0)
//		{CBAkush.setSelected(true);		iw1=iw1-32;}	
//		else {CBAkush.setSelected(false);	iw1=iw1;}
//		if ((iw1-16)>=0)
//		{CDKesar.setSelected(true);		iw1=iw1-16;}	
//		else {CDKesar.setSelected(false);	iw1=iw1;}
//		if ((iw1-8)>=0)
//		{CBTromb.setSelected(true);		iw1=iw1-8;}	
//		else {CBTromb.setSelected(false);	iw1=iw1;}
//		if ((iw1-4)>=0) 
//		{CBGnoin.setSelected(true);		iw1=iw1-4;}	
//		else {CBGnoin.setSelected(false);	iw1=iw1;}
//		if ((iw1-2)>=0)
//		{CBEkl.setSelected(true);		iw1=iw1-2;}	
//		else CBEkl.setSelected(false);
//		CBKrov.setSelected(iw1 ==1 );
		if ((osostp-512)>=0)
		{CHosp10.setSelected(true);   iw1=osostp-512;}	
		else {CHosp10.setSelected(false);iw1=osostp;}
		if ((iw1-256)>=0)
		{CHosp9.setSelected(true);   iw1=iw1-256;}	
		else {CHosp9.setSelected(false);iw1=iw1;}
		if ((iw1-128)>=0)
		{CHosp8.setSelected(true);   iw1=iw1-128;}	
		else {CHosp8.setSelected(false); iw1=iw1;}
		if ((iw1-64)>=0)
		{CHosp7.setSelected(true);   iw1=iw1-64;}	
		else {CHosp7.setSelected(false);iw1=iw1;}
		if ((iw1-32)>=0)
		{CHosp6.setSelected(true);   iw1=iw1-32;}	
		else {CHosp6.setSelected(false);iw1=iw1;}
		if ((iw1-16)>=0)
		{CHosp5.setSelected(true);   iw1=iw1-16;}	
		else {CHosp5.setSelected(false);iw1=iw1;}
		if ((iw1-8)>=0)
		{CHosp4.setSelected(true);   iw1=iw1-8;}	
		else {CHosp4.setSelected(false); iw1=iw1;}
		if ((iw1-4)>=0)
		{CHosp3.setSelected(true);   iw1=iw1-4;}	
		else {CHosp3.setSelected(false);iw1=iw1;}
//		System.out.println("расчет сост. плода");		
//		System.out.println(iw1);		
		if ((iw1-2)>=0)
		{CHosp2.setSelected(true);   iw1=iw1-2;}
		else CHosp2.setSelected(false);
		if (iw1 == 1) CHosp1.setSelected(true);
	}
	
	public void showForm() {
		System.out.println("постановка на входе");		
		System.out.println(Vvod.zapVr.getNpasp());		
		fam.setText(Vvod.zapVr.getFam());
		im.setText(Vvod.zapVr.getIm());
		ot.setText(Vvod.zapVr.getOth());
		
		try {
			rdSlStruct = new RdSlStruct();
			setDefaultValues();
			RdInfStruct rdinf = new RdInfStruct();
			rdinf.setNpasp(Vvod.zapVr.getNpasp());
			rdinf.setDataz(System.currentTimeMillis());
            MainForm.tcl.AddRdInf(rdinf);
			rdSlStruct = MainForm.tcl.getRdSlInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
			setPostBerData();
		} 
//		catch (PrdslNotFoundException e1) 
//		{
//			try {
//				rdSlStruct.setId(MainForm.tcl.AddRdSl(rdSlStruct));
//				setPostBerData();
//			} catch (KmiacServerException e2) {
//				JOptionPane.showMessageDialog(FormPostBer.this, "Не удалось поставить на учет", "Ошибка", JOptionPane.ERROR_MESSAGE);
//			} catch (TException e2) {
//				e2.printStackTrace();
//				MainForm.conMan.reconnect(e2);
//			}
//		} 
		catch (KmiacServerException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(FormPostBer.this, e1.getLocalizedMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		}
		
		setVisible(true);	
	}
	
	public void onConnect() throws PatientNotFoundException {
		method2();	
	}
}
