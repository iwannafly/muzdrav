package ru.nkz.ivcgzo.ldsclient;

import javax.sound.midi.SysexMessage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.chainsaw.Main;
import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.DocumentPrinter;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper.DefaultLanguage;
import ru.nkz.ivcgzo.ldsThrift.DIslExistsException;
import ru.nkz.ivcgzo.ldsThrift.DIslNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.DiagIsl;
import ru.nkz.ivcgzo.ldsThrift.IslExistsException;
import ru.nkz.ivcgzo.ldsThrift.LIslExistsException;
import ru.nkz.ivcgzo.ldsThrift.LIslNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.LabIsl;
import ru.nkz.ivcgzo.ldsThrift.Metod;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;
import ru.nkz.ivcgzo.ldsThrift.PatientNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.S_ot01;
import ru.nkz.ivcgzo.ldsThrift.Sh_lds;
import ru.nkz.ivcgzo.ldsThrift.Sh_ldsNotFoundException;
//import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
//import sun.text.resources.FormatData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import javax.swing.JTextArea;

public class PIslForm {
	
	ShabOpRezName winShab;
	ViborIslMet winVib;
	
	public JFrame frame;
	private CustomTable<Patient, Patient._Fields> tpatient;
	private CustomDateEditor tFdatap;
	private CustomDateEditor tFdatav;
	private CustomTextField tFnprob;
	private CustomTextField tFnaprotd;
	private CustomTextField tFdiag;
	private CustomTable<ObInfIsl, ObInfIsl._Fields> tn_ldi;
	private CustomTable<LabIsl, LabIsl._Fields> tlab_isl;
	private CustomTextField tFkodisl;
	private CustomTextField tFTalon;
	public ThriftStringClassifierCombobox<StringClassifier> cBpcisl;
	public ThriftStringClassifierCombobox<StringClassifier> cBkodisl;
	public ThriftStringClassifierCombobox<StringClassifier> cBpcod_m;
	public ThriftStringClassifierCombobox<StringClassifier> cBLpcod_m;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBprichina;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBSvrach;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBVrach;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBpopl;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBnapravl;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBvopl;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBrez;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBCuser;
	private JTable table;
	public JTabbedPane tabbedPane;
	public JTextPane tPop_name;
	public JTextPane tFrez_name;
	public JSpinner spkol;
	public JButton btnNewButton_9;

	
	/**
	 * Create the application.
	 */
	public PIslForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 855, 731);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		winShab = new ShabOpRezName();
		winVib = new ViborIslMet();
		
		JPanel panel = new JPanel();
		
		JSplitPane splitPane = new JSplitPane();
		
		splitPane.setResizeWeight(0.5);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE))
		);
		
		JButton btnNewButton = new JButton("Поиск");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] npasp = MainForm.conMan.showPatientSearchForm("Поиск пациента", true, true);
				
				if (npasp != null){
					try {
						tpatient.setData(MainForm.ltc.getPatient(Arrays.toString(npasp).replace(']', ')').replace('[', '(')));
						tpatient.requestFocus();
						tpatient.setRowSelectionInterval(0, 0);
					} catch (PatientNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			}
		});
		
		JButton btnNewButton_8 = new JButton("Протокол");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			String path;
			//try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = "c:\\Протокол\\123.htm"), "utf-8")) {

				try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = DocumentPrinter.createReportFile("pisl")), "utf-8")) {
					//AutoCloseableResultSet acrs;
					
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
				sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
				sb.append("<title>Протокол исследований…</title>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<div>");
			
			sb.append("<p style=\"text-align:center\"><b>"+MainForm.authInfo.clpu_name +"</b></p>");
			//sb.append("<p><b>"+MainForm.authInfo.clpu_name+"</b></p>");
			sb.append("<p style=\"text-align:right\">Протокол №_____________</p>");
			sb.append(String.format("<p style=\"text-align:right\">Дата исследования: %1$td.%1$tm.%1$tY</p>", tn_ldi.getSelectedItem().datav));
			
			if(!PostPer.tip.equals("Л")){
				sb.append("<p></p><p></p>");
				sb.append("<p style=\"text-align:center\">"+cBkodisl.getSelectedItem().name+"</p>");
				sb.append("<p></p><p></p>");
			}
			
			sb.append("<p>Ф.И.О.:&nbsp;" + tpatient.getSelectedItem().fam +"&nbsp;"+tpatient.getSelectedItem().im+"&nbsp;"+tpatient.getSelectedItem().ot+"</p>");
			int age = (int) ((tn_ldi.getSelectedItem().datav - tpatient.getSelectedItem().datar) / 31556952000L);
			sb.append("<p>Возраст:&nbsp;"+age+"&nbsp; лет(года)</p>");
			
			
			sb.append("<p>Амб. Карта № ________________________________ История болезни №_______________________________ </p>");

			if (PostPer.tip.equals("Л")){
				sb.append("<p style=\"text-align:right\">Врач:"+ cBSvrach.getSelectedItem().name+"</p>");
				sb.append("<table cellpadding=\"0\" cellspacing=\"0\">");
					sb.append("<tbody>");
			
						sb.append("<tr valign=\"top\">");
							sb.append("<td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 5px;\" width=\"60%\">");
								sb.append("<p style=\"text-align:center\"><b>Наименование показателя<o:p></o:p></b></p>");
							sb.append("</td>");
							sb.append("<td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: none; border-right: 1px solid black; padding: 5px;\" width=\"20%\">");
								sb.append("<p style=\"text-align:center\"><b>Значение<o:p></o:p></b></p>");
							sb.append("</td>");
							sb.append("<td style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: none; border-right: 1px solid black; padding: 5px;\" width=\"20%\">");
								sb.append("<p style=\"text-align:center\"><b>Норма<o:p></o:p></b></p>");
							sb.append("</td>");				
						sb.append("</tr>");
				
				
						for(int i = 0; i<tlab_isl.getRowCount(); i++){
					
							sb.append("<tr valign=\"top\">");
								sb.append("<td style=\"border-top: none; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 5px;\" width=\"60%\">");
									sb.append("<p style=\"text-align:left\">"+tlab_isl.getData().get(i).name+"<o:p></o:p></b></p>");
								sb.append("</td>");
								sb.append("<td style=\"border-top: none; border-bottom: 1px solid black; border-left: none; border-right: 1px solid black; padding: 5px;\" width=\"20%\">");
									sb.append("<p style=\"text-align:center\">"+tlab_isl.getData().get(i).zpok+"<o:p></o:p></b></p>");
								sb.append("</td>");
								sb.append("<td style=\"border-top: none; border-bottom: 1px solid black; border-left: none; border-right: 1px solid black; padding: 5px;\" width=\"20%\">");
									if (tlab_isl.getData().get(i).norma != null){
									sb.append("<p style=\"text-align:center\">"+tlab_isl.getData().get(i).norma+"<o:p></o:p></b></p>");
									}else{
										sb.append("<p><o:p></o:p></b></p>");
									}
									
								sb.append("</td>");				
							sb.append("</tr>");					
						}
			
					sb.append("</tbody>");
				sb.append("</table>");
			}else{
				
				sb.append("<p></p><p>Описание:&nbsp;"+tPop_name.getText()+"</p><p></p>");
				sb.append("<p>Заключение:&nbsp;"+tFrez_name.getText()+"</p><p></p>");
				sb.append("<p style=\"text-align:right\">Врач:"+ cBSvrach.getSelectedItem().name+"</p>");
			}
			sb.append("</div>");
			sb.append("</body>");
			sb.append("</html>");
			
			
			osw.write(sb.toString());
			
			MainForm.conMan.openFileInTextProcessor(path, false);
			//System.out.print(MainForm.authInfo.clpu_name);
			//return path;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
				
			}
		});
		
		btnNewButton_9 = new JButton("Информация о пациенте");
		btnNewButton_9.setVisible(false);
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				MainForm.conMan.showPatientInfoForm("Информация о пациенте", tpatient.getSelectedItem().npasp);
				
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(18)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_8)
					.addPreferredGap(ComponentPlacement.RELATED, 514, Short.MAX_VALUE)
					.addComponent(btnNewButton_9)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_8)
						.addComponent(btnNewButton_9))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		splitPane.setLeftComponent(scrollPane);
		
		tpatient = new CustomTable<>(false, true, Patient.class, 0, "Код", 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Дата рождения", 7, "Город проп.", 8, "Ул. проп.", 9, "Дом проп.", 10, "Кв. проп.", 11, "Город прож.", 12, "Ул. прож.", 13, "Дом прож.", 14, "Кв. прож.");
		tpatient.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tpatient.setDateField(4);
		
		tpatient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
			
				try {
					
					if (tpatient.getSelectedItem() != null){
					//System.out.print(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
					tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
					}else{
						tn_ldi.setData(new ArrayList<ObInfIsl>());
					}
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		tpatient.setDateField(4);
		scrollPane.setViewportView(tpatient);

		
		
		JPanel panel1 = new JPanel();
		splitPane.setRightComponent(panel1);		
		
		
		JSplitPane splitPane_1 = new JSplitPane();
		
		splitPane_1.setResizeWeight(0.3);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
/*		tFnaprotd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ((tFnaprotd.getText() != null)||(tFnaprotd.getText() !="")){
					try {
						cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		});*/
		
		JButton btnNewButton_3 = new JButton(">>");
		
		JPanel panel_5 = new JPanel();
		splitPane_1.setLeftComponent(panel_5);
		
		JPanel panel_6 = new JPanel();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
				.addComponent(panel_6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
		);
		
		JButton btnNewButton_1 = new JButton("Добавить");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
			try {
				
//				ObInfIsl pisl_ld = new ObInfIsl(tpatient.getSelectedItem().npasp, ,MainForm.authInfo.cpodr, null, null, null, System.currentTimeMillis(), System.currentTimeMillis(), null, null, 2, 0, null, 2, null, MainForm.authInfo.pcod, null, null, System.currentTimeMillis());
				ObInfIsl pisl_ld = new ObInfIsl();
				pisl_ld.setNpasp(tpatient.getSelectedItem().npasp);
				//pisl_ld.setNisl(2);
				pisl_ld.setKodotd(MainForm.authInfo.cpodr);
				pisl_ld.setDatap(System.currentTimeMillis());
				pisl_ld.setDatav(System.currentTimeMillis());
				pisl_ld.setNapravl(2);
				pisl_ld.setNaprotd(0);
				pisl_ld.setVopl(2);
				pisl_ld.setDataz(System.currentTimeMillis());				
				pisl_ld.setCuser(MainForm.authInfo.pcod);
				pisl_ld.setKod_ter(10);
				
				//System.out.println("Добавление = "+pisl_ld);
				
				pisl_ld.setNisl(MainForm.ltc.AddIsl(pisl_ld));
				tn_ldi.addItem(pisl_ld);
				//System.out.print(tn_ldi.getSelectedItem().nisl);
				
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("Сохранить");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			try{	
				
				ObInfIsl upnisl = new ObInfIsl(/*tn_ldi.getSelectedItem().npasp, tn_ldi.getSelectedItem().nisl, tn_ldi.getSelectedItem().kodotd, "nprob", "pcisl", "cisl", "datap", "datav", "prichina", "popl", "napravl", "naprotd", "vrach", "vopl", "diag", "kodvr", "dataz", "cuser"*/);
				
				upnisl.setNisl(tn_ldi.getSelectedItem().nisl);
				
				if (tFnprob.getText() != null){
					upnisl.setNprob(Integer.parseInt(tFnprob.getText()));
				}
				
				if (tFTalon.getText() != null){
					upnisl.setTalon(tFTalon.getText());
				}
				
				upnisl.setPcisl(cBpcisl.getSelectedPcod());
				
				
				upnisl.setDatap(tFdatap.getDate().getTime());
				upnisl.setDatav(tFdatav.getDate().getTime());
				
				if (cBprichina.getSelectedPcod() != null){
					upnisl.setPrichina(cBprichina.getSelectedPcod());
				}
				
				if (cBpopl.getSelectedPcod() != null){
					upnisl.setPopl(cBpopl.getSelectedPcod());
				}
				
				if (cBnapravl.getSelectedPcod() != null){
					upnisl.setNapravl(cBnapravl.getSelectedPcod());
				}
				
				upnisl.setKod_ter(tn_ldi.getSelectedItem().kod_ter);
				
				if (tFnaprotd.getText() != null){	
					upnisl.setNaprotd(Integer.parseInt(tFnaprotd.getText()));
				}
				if (cBVrach.getSelectedPcod() != null){
					upnisl.setVrach(cBVrach.getSelectedPcod());
				}else {upnisl.setVrach(0);}
				
				if (cBvopl.getSelectedPcod() != null){
					upnisl.setVopl(cBvopl.getSelectedPcod());
				}else {
					upnisl.setVopl(2);
				}
				
				upnisl.setDiag(tFdiag.getText());
				
				if (cBSvrach.getSelectedPcod() != null){
				upnisl.setKodvr(cBSvrach.getSelectedPcod());
				}
				
				upnisl.setCuser(cBCuser.getSelectedPcod());
				
				//System.out.println("UPDATE = "+upnisl);
				
// , , , , , , , , , , , 				
				MainForm.ltc.UpdIsl(upnisl);
				
				tn_ldi.updateChangedSelectedItem();
				
				if (tFnprob.getText() != null){
					tn_ldi.getSelectedItem().setNprob(Integer.parseInt(tFnprob.getText()));
				}
				
				tn_ldi.getSelectedItem().setTalon(tFTalon.getText());
				
				tn_ldi.getSelectedItem().setPcisl(cBpcisl.getSelectedPcod());
				tn_ldi.getSelectedItem().setDatap(tFdatap.getDate().getTime());
				tn_ldi.getSelectedItem().setDatav(tFdatav.getDate().getTime());
				
				if (cBprichina.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setPrichina(cBprichina.getSelectedPcod());
				}
				
				if (cBpopl.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setPopl(cBpopl.getSelectedPcod());
				}
				
				if (cBnapravl.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setNapravl(cBnapravl.getSelectedPcod());
				}
				
				if (tFnaprotd.getText() != null){	
					tn_ldi.getSelectedItem().setNaprotd(Integer.parseInt(tFnaprotd.getText()));
				}
				if (cBVrach.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setVrach(cBVrach.getSelectedPcod());
				}else{tn_ldi.getSelectedItem().setVrach(0);}
				
				if (cBvopl.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setVopl(cBvopl.getSelectedPcod());
				}else{
					tn_ldi.getSelectedItem().setVopl(2);
				}
				
				tn_ldi.getSelectedItem().setDiag(tFdiag.getText());

				if (cBCuser.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setCuser(cBCuser.getSelectedPcod());
				}
				
				if (cBSvrach.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setKodvr(cBSvrach.getSelectedPcod());
				}				
				
				tn_ldi.getSelectedItem();
				tn_ldi.repaint();
//UPDATE p_isl_ld SET nprob = ?, pcisl = ?, datap = ?, datav = ?, prichina = ?, popl = ?, napravl = ?, naprotd = ?, vrach = ?, vopl = ?, diag = ?, kodvr = ?, WHERE nisl = ?				
			}catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			if((cBSvrach.getSelectedPcod() == null)||(tFdiag.getText().equals(""))){	
				String mess = "Поля для реестров:\n";
				if (cBvopl.getSelectedPcod() == null) mess=mess+"Вид оплаты\n";
				if (cBSvrach.getSelectedPcod() == null) mess=mess+"Код врача\n";
				if (tFdiag.getText().equals("")) mess=mess+"Диагноз\n";
				
			
				mess= mess+"не заполнены";
				
				JOptionPane.showMessageDialog(frame, mess);
			}
			
			if (!PostPer.tip.equals("Л")){			
				//System.out.print(PostPer.tip);
				DiagIsl spDIsl;
				try {
					spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
				
				
					if (spDIsl.isSetNisl() == false){
					
						DiagIsl inDisl = new DiagIsl();
					
						try {
						
							inDisl.setNpasp(tn_ldi.getSelectedItem().npasp);
							inDisl.setNisl(tn_ldi.getSelectedItem().nisl);
							inDisl.setKol(1);
							spkol.setValue(1);
						
							//System.out.print(inDisl);
						
							MainForm.ltc.AddDIsl(inDisl);
						} catch (DIslExistsException e) {
						// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
				
				
					}
				
				
				} catch (DIslNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				
				
				List<LabIsl> lbIs;
				
				try {
					lbIs = MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl, tn_ldi.getSelectedItem().pcisl);
					
					if (lbIs.size() == 0){
					
					
						List<StringClassifier> sot01;
					
						sot01 = MainForm.ltc.GetKlasIsS_ot01(MainForm.authInfo.cpodr, tn_ldi.getSelectedItem().pcisl);
						
						if (sot01.size() > 0) {
							
							List<Metod> srS_ot01;
							srS_ot01 = MainForm.ltc.GetLabStoim(tn_ldi.getSelectedItem().pcisl, MainForm.authInfo.cpodr);
							
							//System.out.print("srS_ot01= "+srS_ot01);
							
							LabIsl addLbIsl = new LabIsl();
							
							for ( int i = 0; i<sot01.size(); i++){
								
								/*addLbIsl.setNpasp(tn_ldi.getSelectedItem().npasp);
								addLbIsl.setNisl(tn_ldi.getSelectedItem().nisl);
								addLbIsl.setCpok(sot01.get(i).pcod);*/
								
								for (int j = 0; j<srS_ot01.size(); j++){
									
									if (sot01.get(i).pcod.equals(srS_ot01.get(j).pcod)){
										//System.out.print(sot01.get(i).pcod+" == "+srS_ot01.get(j).pcod);
										addLbIsl.setNpasp(tn_ldi.getSelectedItem().npasp);
										addLbIsl.setNisl(tn_ldi.getSelectedItem().nisl);
										addLbIsl.setCpok(sot01.get(i).pcod);
										addLbIsl.setPcod_m(srS_ot01.get(j).c_obst);
										addLbIsl.setStoim(srS_ot01.get(j).stoim);
	
										try {
											
											MainForm.ltc.AddLIsl(addLbIsl);
											
										} catch (LIslExistsException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										if ((j < srS_ot01.size()-1)&&(!sot01.get(i).pcod.equals(srS_ot01.get(j+1).pcod))){
											break;
										} 
									}
									
								}
								
								//System.out.print(addLbIsl);
								//addLbIsl.setNpasp(tn_ldi.getSelectedItem().npasp);
/*								try {
	
									MainForm.ltc.AddLIsl(addLbIsl);
									
								} catch (LIslExistsException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}*/
							}
							lbIs = MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl,tn_ldi.getSelectedItem().pcisl);
							
							tlab_isl.setData(lbIs);
							
						}
					}
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}	
			
			
			}
		});
		
		JButton btnNewButton_4 = new JButton("Удалить");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					if (!PostPer.tip.equals("Л")){
						MainForm.ltc.DelDIslP(tn_ldi.getSelectedItem().nisl);
					}else{
						MainForm.ltc.DelLIslD(tn_ldi.getSelectedItem().nisl);
					}
					
					MainForm.ltc.DelIsl(tn_ldi.getSelectedItem().nisl);
					tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
					
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//(MainForm.authInfo.cpodr, tn_ldi.getSelectedItem().pcod, n_nz1.getSelectedPcod());
				
			}
		});
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap(308, Short.MAX_VALUE)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_4)
					.addGap(220))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1, Alignment.CENTER)
						.addComponent(btnNewButton_2, Alignment.CENTER)
						.addComponent(btnNewButton_4, Alignment.CENTER))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		tn_ldi = new CustomTable<>(false, true, ObInfIsl.class, 2, "Код отделения", 3, "№ пробы", 4, "Орган. и системы", 6, "Дата пост.", 7, "Дата выпол.", 8, "Причина", 9, "Обстоятельства", 10, "Направлен", 21, "Направлен из города", 11, "Код направ. ЛПУ", 12, "ФИО направ. врача", 13, "Вид оплаты", 14, "Диагноз", 15, "Код врача", 16, "Дата за полнения");
		tn_ldi.setFillsViewportHeight(true);
		
		tn_ldi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
			
			  if (tn_ldi.getSelectedItem() != null){	
				if (tn_ldi.getSelectedItem().pcisl != null ){
					cBpcisl.setSelectedPcod(tn_ldi.getSelectedItem().pcisl);
				} else{
					cBpcisl.setSelectedItem(null);
				}
				
				SimpleDateFormat dp = new SimpleDateFormat("dd.MM.yyyy");
				
				if (tn_ldi.getSelectedItem().isSetDatap()){
					tFdatap.setText(dp.format(tn_ldi.getSelectedItem().datap));
				}else{
					tFdatap.setDate(System.currentTimeMillis());
				}
				
				if (tn_ldi.getSelectedItem().isSetDatav()){
					tFdatav.setText(dp.format(tn_ldi.getSelectedItem().datav));
				}else{
					tFdatav.setDate(System.currentTimeMillis());
				}
				
				tFnprob.setText(String.valueOf(tn_ldi.getSelectedItem().nprob));
				
				tFTalon.setText(tn_ldi.getSelectedItem().talon);
								
				if(tn_ldi.getSelectedItem().prichina != 0){
					cBprichina.setSelectedPcod(tn_ldi.getSelectedItem().prichina);
				}else{
					cBprichina.setSelectedItem(null);
				}
				
				if (tn_ldi.getSelectedItem().popl !=0){
					cBpopl.setSelectedPcod(tn_ldi.getSelectedItem().popl);
				}else{
					cBpopl.setSelectedItem(null);
				}
				
				if(tn_ldi.getSelectedItem().napravl !=0){
					cBnapravl.setSelectedPcod(tn_ldi.getSelectedItem().napravl);
				}else{
					cBnapravl.setSelectedPcod(2);
				}
				//if (tn_ldi.getSelectedItem().naprotd != 0)
				tFnaprotd.setText(String.valueOf(tn_ldi.getSelectedItem().naprotd));
				

				if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText() !="")&&(tFnaprotd.getText() != "0")){
					try {
						cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if ((tn_ldi.getSelectedItem().vrach != 0)&&(cBVrach.getSize()!= null) ){
					try{
						cBVrach.setSelectedPcod(tn_ldi.getSelectedItem().vrach);
					}catch(Exception e){
						cBVrach.setSelectedItem(null);
					}
				} else{
					cBVrach.setSelectedItem(null);
				}
				
				if (tn_ldi.getSelectedItem() != null){
					if ((tn_ldi.getSelectedItem().cuser !=0) && (String.valueOf(tn_ldi.getSelectedItem().cuser) != null)){
						cBCuser.setSelectedPcod(tn_ldi.getSelectedItem().cuser);
				
					} else {
						cBCuser.setSelectedPcod(MainForm.authInfo.pcod);
					}
				}else{
					cBCuser.setSelectedItem(null);
				}
				
				
				if ((tn_ldi.getSelectedItem().vopl != 0)&&(String.valueOf(tn_ldi.getSelectedItem().vopl) != null)){
					cBvopl.setSelectedPcod(tn_ldi.getSelectedItem().vopl);
				}else{
					cBvopl.setSelectedPcod(2);
				}
				
				if ((tn_ldi.getSelectedItem().kodvr !=0) && (String.valueOf(tn_ldi.getSelectedItem().kodvr) != null)){
				cBSvrach.setSelectedPcod(tn_ldi.getSelectedItem().kodvr);
				} else {cBSvrach.setSelectedItem(null);}
				
				if (tn_ldi.getSelectedItem().diag != null){
					tFdiag.setText(tn_ldi.getSelectedItem().diag);
				}else{
					tFdiag.setText(null);
				}
				
				
				
					//DiagIsl spDisl = new DiagIsl();
				if (!PostPer.tip.equals("Л")){	
					DiagIsl spDisl;
					try {
						spDisl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);

						
						if (String.valueOf(spDisl.getNpasp()) != null) {
							
							if(spDisl.kodisl != null){ 
								cBkodisl.setSelectedPcod(spDisl.kodisl);
								tFkodisl.setText(spDisl.getKodisl());
							}else{
								cBkodisl.setSelectedItem(null);
								tFkodisl.setText(null);
								}
							
							spkol.setValue(spDisl.kol);
							cBrez.setSelectedPcod(spDisl.rez);
							
							if (spDisl.pcod_m != null){
								cBpcod_m.setSelectedPcod(spDisl.pcod_m);
							}else{
								cBpcod_m.setSelectedItem(null);
							}
							
							tPop_name.setText(spDisl.op_name);
							tFrez_name.setText(spDisl.rez_name);
						}					
					
					
					
					} catch (DIslNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{
					try {
						
						tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl,tn_ldi.getSelectedItem().pcisl));
						
						if ((tn_ldi.getSelectedItem().isSetId_gosp())||(tn_ldi.getSelectedItem().isSetId_pos())){
							
							 LabIsl uplab = new LabIsl();
							 List<Metod> stoim;
							 stoim = MainForm.ltc.GetLabStoim(tn_ldi.getSelectedItem().pcisl, tn_ldi.getSelectedItem().kodotd);
							 Boolean ch = false;
							 
							 for (int i = 0; i<tlab_isl.getData().size(); i++){
								if (!tlab_isl.getData().get(i).isSetPcod_m()){
									ch = false;
									for (int j = 0; j<stoim.size(); j++){
										
										
										
										if ((tlab_isl.getData().get(i).cpok.equals(stoim.get(j).pcod))&&(ch == false)){
											ch = true;
										
											uplab.setId(tlab_isl.getData().get(i).id);
							     			uplab.setCpok(tlab_isl.getData().get(i).cpok);
											uplab.setZpok(tlab_isl.getData().get(i).zpok);
											uplab.setNisl(tn_ldi.getSelectedItem().nisl);
											uplab.setNpasp(tn_ldi.getSelectedItem().npasp);
											uplab.setStoim(stoim.get(j).stoim);
											uplab.setPcod_m(stoim.get(j).c_obst);

											MainForm.ltc.UpdLIsl(uplab);
											
											if ((j+1<stoim.size())&&(!tlab_isl.getData().get(i).cpok.equals(stoim.get(j+1).pcod))){
												break;
											}
											
										}
										else{
											if (tlab_isl.getData().get(i).cpok.equals(stoim.get(j).pcod)&&(ch == true)){
											
												
								     			uplab.setCpok(stoim.get(j).pcod);
												uplab.setNisl(tn_ldi.getSelectedItem().nisl);
												uplab.setNpasp(tn_ldi.getSelectedItem().npasp);
												uplab.setStoim(stoim.get(j).stoim);
												uplab.setPcod_m(stoim.get(j).c_obst);
												
												MainForm.ltc.AddLIsl(uplab);
												
												if ((j+1<stoim.size())&&(!tlab_isl.getData().get(i).cpok.equals(stoim.get(j+1).pcod))){
													break;
												}												
												
											}

											
										}
									}
									
									
									
								}
							}
							tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl,tn_ldi.getSelectedItem().pcisl));
						}
						
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
					

				
			}else{
					cBpcisl.setSelectedItem(0);
					tFdatap.setText(null);
					tFdatav.setText(null);
					cBprichina.setSelectedItem(0);
					cBpopl.setSelectedItem(0);
					cBvopl.setSelectedItem(0);
					cBVrach.setSelectedItem(0);
					cBSvrach.setSelectedItem(0);
					cBnapravl.setSelectedItem(0);					
					tFnaprotd.setText(null);
					tFnprob.setText(null);
					tFdiag.setText(null);
					
					
					if(PostPer.tip.equals("Л")){
						List<LabIsl> nSpis = new ArrayList<>();
						
						tlab_isl.setData(nSpis);
						cBLpcod_m.setSelectedItem(0);
					}else{
						cBkodisl.setSelectedItem(0);
						tFkodisl.setText(null);
						cBpcod_m.setSelectedItem(0);
						tFrez_name.setText(null);
						spkol.setValue(0);
						tPop_name.setText(null);
						cBrez.setSelectedItem(0);
						
					}
				
				
			}
			  
			  
			  
			  
			  
			}
		});
		
		
		
		tn_ldi.setDateField(3);
		tn_ldi.setDateField(4);
		tn_ldi.setDateField(14);
		
		scrollPane_1.setViewportView(tn_ldi);
		panel_5.setLayout(gl_panel_5);
		GroupLayout gl_panel1 = new GroupLayout(panel1);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addGap(5)
					.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE))
		);
		
		JPanel panel_13 = new JPanel();
		splitPane_1.setRightComponent(panel_13);
		
		JSplitPane splitPane_2 = new JSplitPane();		
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setResizeWeight(0);
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13.setHorizontalGroup(
			gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_2, GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
		);
		gl_panel_13.setVerticalGroup(
			gl_panel_13.createParallelGroup(Alignment.TRAILING)
				.addComponent(splitPane_2, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
		);
		
		JPanel panel_14 = new JPanel();
		splitPane_2.setLeftComponent(panel_14);
		
		JPanel panel_1 = new JPanel();
		
		JLabel lblNewLabel = new JLabel("Органы и системы");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		cBpcisl = new ThriftStringClassifierCombobox<>(true);
		cBpcisl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (cBpcisl.getSelectedItem() != null){
				 try {
					cBkodisl.setData(MainForm.ltc.GetKlasIsS_ot01(MainForm.authInfo.cpodr, cBpcisl.getSelectedPcod()));
					 //cBkodisl.setData(MainForm.ltc.GetKlasIsS_ot01(2000004, cBpcisl.getSelectedPcod()));
					// System.out.print(cBpcisl.getSelectedPcod());
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
			}
		});
		
			
			
			
			JLabel lblNewLabel_1 = new JLabel("Дата поступления");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			JLabel lblNewLabel_2 = new JLabel("Дата выполнения");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			tFdatap = new CustomDateEditor();
			tFdatap.setColumns(10);
			
			tFdatav = new CustomDateEditor();
			tFdatav.setColumns(10);
			
			JLabel lblNewLabel_3 = new JLabel("Номер пробы");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			tFnprob = new CustomTextField();
			tFnprob.setColumns(10);
			
			JLabel lblNewLabel_4 = new JLabel("Причина обращения");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			JLabel lblNewLabel_5 = new JLabel("Обстоятельства обращения");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_5.setVisible(false);
			
			cBprichina = new ThriftIntegerClassifierCombobox<>(true);
			
			cBpopl = new ThriftIntegerClassifierCombobox<>(true);
			cBpopl.setVisible(false);
			
			JLabel lblNewLabel_6 = new JLabel("Кем направлен");
			lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			JLabel lblNewLabel_7 = new JLabel("Направившее ЛПУ");
			lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			cBnapravl = new ThriftIntegerClassifierCombobox<>(true);
			
			tFnaprotd = new CustomTextField();
			tFnaprotd.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					Boolean ch = true;  
					
					try {					
											
						if (cBnapravl.getSelectedPcod() == 1){
							List<IntegerClassifier> Pus;

								Pus = MainForm.ltc.GetKlasPrvO00(MainForm.authInfo.clpu, Integer.valueOf(tFnaprotd.getText()));
						
							if (Pus.size()==0){
								//System.out.println(Pus.size());
								ch = false;
							}
						
						}else{
							if (cBnapravl.getSelectedPcod() == 2){
								List <IntegerClassifier> Pus = MainForm.ltc.GetKlasPrvN00(Integer.valueOf(tFnaprotd.getText()));
							
								if (Pus.size()==0){
									//System.out.println(Pus.size());
									ch = false;
								}	
							}else{
								List <IntegerClassifier> Pus = MainForm.ltc.GetKlasPrvM00(Integer.valueOf(tFnaprotd.getText()));
							
								if (Pus.size()==0){
									//System.out.println(Pus.size());
									ch = false;
								}	
							}
						
						}
					
												
					} catch (NumberFormatException | TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					if ((ch == false)&&(tFnaprotd.getText().trim().isEmpty()==false)){
						if (cBnapravl.getSelectedPcod() == 1){
							IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_o00);
						
							if (res != null)
								tFnaprotd.setText(String.valueOf(res.pcod));
						}else{
						
							if (cBnapravl.getSelectedPcod() == 2){
								IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_n00);
							
									if (res != null)
										tFnaprotd.setText(String.valueOf(res.pcod));
							}else{
									IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_m00);
							
									if (res != null)
										tFnaprotd.setText(String.valueOf(res.pcod));
							}
						
						}
					}
					
					
				}
			});
			tFnaprotd.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					
						try {
							//System.out.print("tFnaprotd.getText() = "+tFnaprotd.getText()+ ";");
							if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText().length() > 0)&&(Integer.parseInt(tFnaprotd.getText()) != 0)){
								
								cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
								
							}else {cBVrach.setSelectedItem(null); }
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					
				}
				
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText() !="")&&(tFnaprotd.getText() != "0")){
						try {
							cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText() !="")&&(tFnaprotd.getText() != "0")){
						try {
							cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			});
			tFnaprotd.setColumns(10);
			
			JButton btnnaprotd = new JButton(">>");
			btnnaprotd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (cBnapravl.getSelectedPcod() == 1){
						IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_o00);
						
						if (res != null)
							tFnaprotd.setText(String.valueOf(res.pcod));
					}else{
						if (cBnapravl.getSelectedPcod() == 2){
							IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_n00);
							
							if (res != null)
								tFnaprotd.setText(String.valueOf(res.pcod));
						}else{
								/*IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_m00);
								//IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(MainForm.ltc.GetKlasNsipol(cBKod_ter.getSelectedPcod()));
								if (res != null)
									tFnaprotd.setText(String.valueOf(res.pcod));*/
							
							
		                	int[] res = null;
		                	if (tFnaprotd.getText() != null )
		                		res = MainForm.conMan.showPolpTreeForm("Классификатор подразделений ЛПУ", 0, 0, 0);
		                	else
		                		res = MainForm.conMan.showPolpTreeForm("Классификатор подразделений ЛПУ", 0, 0, 0);
		                    if (res != null) {
		                    	tFnaprotd.setText(Integer.toString(res[2]));
		                    	//cBKod_ter.setSelectedPcod(res[0]);
		                    	tn_ldi.getSelectedItem().setKod_ter(res[0]);
		                    	
		                    }
							
							
							
							
						}
						
					}
					
				}
			});
			
			JLabel lblNewLabel_8 = new JLabel("Ф.И.О. направившего врача");
			lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			JLabel lblNewLabel_9 = new JLabel("Вид оплаты");
			lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			JLabel lblNewLabel_10 = new JLabel("Код врача");
			lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			cBvopl = new ThriftIntegerClassifierCombobox<>(true);
			
			JLabel lblNewLabel_14 = new JLabel("Диагноз");
			lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			tFdiag = new CustomTextField();
			tFdiag.setDefaultLanguage(DefaultLanguage.English);
			tFdiag.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					if (arg0.getClickCount() == 2) {
						StringClassifier res = MainForm.conMan.showMkbTreeForm("Диагнозы", null);//(StringClassifiers.n_c00);
						
						if (res != null)
							tFdiag.setText(String.valueOf(res.pcod));
					}
					
				}
			});
			tFdiag.setColumns(10);
			
			cBSvrach = new ThriftIntegerClassifierCombobox<>(true);
			
			cBVrach = new ThriftIntegerClassifierCombobox<>(true);
			
			JLabel label_2 = new JLabel("Исследовал");
			label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			cBCuser = new ThriftIntegerClassifierCombobox<>(true);
			
			JLabel lblNewLabel_11 = new JLabel("№ талона");
			
			tFTalon = new CustomTextField();
			tFTalon.setColumns(10);
			
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(121)
								.addComponent(lblNewLabel)
								.addGap(4)
								.addComponent(cBpcisl, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel_1)
								.addGap(4)
								.addComponent(tFdatap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(lblNewLabel_2)
								.addGap(4)
								.addComponent(tFdatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(lblNewLabel_3)
								.addGap(4)
								.addComponent(tFnprob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_11)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tFTalon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel_4)
								.addGap(4)
								.addComponent(cBprichina, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
								.addGap(4)
								.addComponent(lblNewLabel_5)
								.addGap(4)
								.addComponent(cBpopl, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel_6)
								.addGap(10)
								.addComponent(cBnapravl, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_7)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tFnaprotd, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnnaprotd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addGap(84))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel_8)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cBVrach, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cBvopl, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addContainerGap()
								.addComponent(lblNewLabel_10)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cBSvrach, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_14)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(label_2)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cBCuser, 0, 241, Short.MAX_VALUE)))
						.addGap(50))
			);
			gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(lblNewLabel))
							.addComponent(cBpcisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(lblNewLabel_1))
							.addComponent(tFdatap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(lblNewLabel_2))
							.addComponent(tFdatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(lblNewLabel_3))
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(tFnprob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_11)
								.addComponent(tFTalon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(2)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(5)
								.addComponent(lblNewLabel_4))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(2)
								.addComponent(cBprichina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(2)
								.addComponent(cBpopl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(1)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(4)
								.addComponent(lblNewLabel_6))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(1)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
									.addComponent(cBnapravl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_7)
									.addComponent(tFnaprotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnnaprotd))))
						.addGap(8)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_8))
								.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_9)
									.addComponent(cBvopl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(cBVrach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_10)
							.addComponent(cBSvrach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_14)
							.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_2)
							.addComponent(cBCuser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(0))
			);
			panel_1.setLayout(gl_panel_1);
			GroupLayout gl_panel_14 = new GroupLayout(panel_14);
			gl_panel_14.setHorizontalGroup(
				gl_panel_14.createParallelGroup(Alignment.LEADING)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
			);
			gl_panel_14.setVerticalGroup(
				gl_panel_14.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_14.createSequentialGroup()
						.addGap(1)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			panel_14.setLayout(gl_panel_14);
		
		JPanel panel_15 = new JPanel();
		splitPane_2.setRightComponent(panel_15);
		
		
		JPanel panel2 = new JPanel();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Диагностика", null, layeredPane, null);
		
		JPanel panel_10 = new JPanel();
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 750, Short.MAX_VALUE)
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_10, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 230, Short.MAX_VALUE)
		);
		
		JPanel panel_4 = new JPanel();
		
		JLabel label = new JLabel("Исследование");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		tFkodisl = new CustomTextField();
		tFkodisl.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				
				if ((tFkodisl.getText() != null)&&(cBkodisl.getSelectedPcod() == null)){
					
					cBkodisl.setSelectedPcod(tFkodisl.getText());
					
				}
				
			}
		});
		tFkodisl.setDefaultLanguage(DefaultLanguage.English);
		tFkodisl.setColumns(10);
		
		cBkodisl = new ThriftStringClassifierCombobox<>(true);
		cBkodisl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				tFkodisl.setText(cBkodisl.getSelectedPcod());

				try {
					if ((cBpcisl.getSelectedItem() != null) && (cBkodisl.getSelectedItem() != null)){
						int age = (int) ((tn_ldi.getSelectedItem().datav - tpatient.getSelectedItem().datar) / 31556952000L);
						
						
						if (age < 18 ){
							cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod(),2));
							
							if (cBpcod_m.getItemCount()==0)
								
								cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod(),3));
						}else{
							
							cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod(),1));
							
							if (cBpcod_m.getItemCount()==0)
								
								cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod(),3));
							
						}
						
						//cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(2000004, cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod()));
						//System.out.print(cBpcisl.getSelectedPcod() + "  " + cBkodisl.getSelectedPcod());
						
					}
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JLabel label_1 = new JLabel("Количество");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		spkol = new JSpinner();
		
		JLabel label_3 = new JLabel("Результат");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		cBrez = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel label_4 = new JLabel("Стоимость");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//tFrez_name.setColumns(10);
		
		JButton button_4 = new JButton("Выбрать");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			
			if(cBkodisl.getSelectedItem() != null){
				
				
				try {
					winShab.listName.setData(MainForm.ltc.GetShab_lds(cBkodisl.getSelectedPcod()));

					if (winShab.listName.getData().size()>0){
						winShab.setVisible(true);
						tPop_name.setText(winShab.VozvOpis());
						tFrez_name.setText(winShab.VozvZak());
					}else{
						
						JOptionPane.showMessageDialog(frame, "Нет шаблонов на данное исследование.");
						
					}
					
				
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
				
			}else{
				JOptionPane.showMessageDialog(frame, "Не введено исследование");
			}
				
			}
		});
		
		cBpcod_m = new ThriftStringClassifierCombobox<>(true);
		
		JLabel label_7 = new JLabel("Заключение");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel label_8 = new JLabel("Описание");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton button = new JButton("Сохранить");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					List<DiagIsl> spDisl =MainForm.ltc.GetDiagIsl(tn_ldi.getSelectedItem().nisl) ;

				
					if (spDisl.size()<1){			
						//System.out.print(PostPer.tip);
						DiagIsl spDIsl;
						try {
							spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
					
					
							if (spDIsl.isSetNisl() == false){
						
								DiagIsl inDisl = new DiagIsl();
						
								try {
							
									inDisl.setNpasp(tn_ldi.getSelectedItem().npasp);
									inDisl.setNisl(tn_ldi.getSelectedItem().nisl);
									inDisl.setKol(1);
									spkol.setValue(1);
							
								//System.out.print(inDisl);
							
									MainForm.ltc.AddDIsl(inDisl);
								} catch (DIslExistsException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						
								spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
								
					
							}
					
					
				} catch (DIslNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
			} catch (TException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 	
				
				
				
				DiagIsl upDisl = new DiagIsl();
				if (cBkodisl.getSelectedPcod() != null){
					upDisl.setKodisl(cBkodisl.getSelectedPcod());
					tFkodisl.setText(cBkodisl.getSelectedPcod());
				} else{
						if ((cBkodisl.getSelectedPcod() == null)&&(tFkodisl.getText() != null)){
							upDisl.setKodisl(tFkodisl.getText());
							cBkodisl.setSelectedPcod(tFkodisl.getText());
						}
				}
				
				List<DiagIsl> spDisl;
				try {
					spDisl = MainForm.ltc.GetDiagIsl(tn_ldi.getSelectedItem().nisl);
					upDisl.setId(spDisl.get(0).id);
					
				} catch (TException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				
				upDisl.setNisl(tn_ldi.getSelectedItem().nisl);
				upDisl.setOp_name(tPop_name.getText());
				upDisl.setRez_name(tFrez_name.getText());
				upDisl.setRez(cBrez.getSelectedPcod());
				upDisl.setKol(Integer.parseInt(spkol.getValue().toString()));
				upDisl.setPcod_m(cBpcod_m.getSelectedPcod());
				if ((cBkodisl.getSelectedPcod() != null) && (cBpcod_m.getSelectedPcod() != null)){
					List<Metod> Cena;

					try {
						Cena = MainForm.ltc.GetStoim(cBkodisl.getSelectedPcod(), cBpcod_m.getSelectedPcod(), MainForm.authInfo.cpodr);
						if (Cena.size() !=0){
							upDisl.setStoim(Cena.get(0).stoim);
						}
					} catch (TException e) {
						// TODO Auto-generated catch block
				 		e.printStackTrace();
					} 
								
				}
				 				
				try {
					//System.out.println(upDisl);
					MainForm.ltc.UpdDIsl(upDisl);
				} catch (DIslExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
			
				List<S_ot01> PosKod;
			
				try {
				
					PosKod = MainForm.ltc.GetS_ot01(tn_ldi.getSelectedItem().kodotd, "51");
				
				
					if (PosKod.size()>0){	
						
						//System.out.println("pcod = "+PosKod.get(0).pcod+"; datav = "+tn_ldi.getSelectedItem().datav+"; npasp = "+tn_ldi.getSelectedItem().npasp+ "; kodotd = "+tn_ldi.getSelectedItem().kodotd);
						
						
						DiagIsl Digisp = MainForm.ltc.GetDIslPos(PosKod.get(0).pcod, tn_ldi.getSelectedItem().datav, tn_ldi.getSelectedItem().npasp, tn_ldi.getSelectedItem().kodotd);
				
						if (!Digisp.isSetKodisl()){
							
							DiagIsl spDIsl;
							try {
								spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
															
									DiagIsl inDisl = new DiagIsl();
							
									try {
								
										inDisl.setNpasp(tn_ldi.getSelectedItem().npasp);
										inDisl.setNisl(tn_ldi.getSelectedItem().nisl);
										inDisl.setKol(1);
										spkol.setValue(1);
										
										inDisl.setKodisl(PosKod.get(0).pcod);
										
										
 
										
										if (tpatient.getSelectedItem().ter_liv > 0 ){
												
												inDisl.setPcod_m(PosKod.get(0).c_obst);
												
												List<Metod> Cena;

												try {
													Cena = MainForm.ltc.GetStoim(PosKod.get(0).pcod, PosKod.get(0).c_obst, MainForm.authInfo.cpodr);
													if (Cena.size() !=0){
														inDisl.setStoim(Cena.get(0).stoim);
													}
												} catch (TException e) {
													// TODO Auto-generated catch block
											 		e.printStackTrace();
												}												
											
										}else{
											inDisl.setPcod_m(PosKod.get(1).c_obst);
											
											List<Metod> Cena;

											try {
												Cena = MainForm.ltc.GetStoim(PosKod.get(1).pcod, PosKod.get(1).c_obst, MainForm.authInfo.cpodr);
												if (Cena.size() !=0){
													inDisl.setStoim(Cena.get(0).stoim);
												}
											} catch (TException e) {
												// TODO Auto-generated catch block
										 		e.printStackTrace();
											}												
											
										}
											
										
										//System.out.print(inDisl);
								
										MainForm.ltc.AddDIsl(inDisl);
									} catch (DIslExistsException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							
									spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
									
						
								
						
						
							} catch (DIslNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (TException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
					
					}else{
						JOptionPane.showMessageDialog(frame, "В настройках не выбрано посещение на " + MainForm.authInfo.cpodr_name);
					}
				
				
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
				
				
			}
		});
		
		JButton button_1 = new JButton("Удалить");
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainForm.ltc.DelDIsl(tn_ldi.getSelectedItem().nisl, tFkodisl.getText());
				} catch (TException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				cBkodisl.setSelectedItem(0);
				tFkodisl.setText(null);
				cBpcod_m.setSelectedItem(0);
				tFrez_name.setText(null);
				spkol.setValue(0);
				tPop_name.setText(null);
				cBrez.setSelectedItem(0);
			}
		});
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setAutoscrolls(true);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label)
							.addGap(4)
							.addComponent(tFkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBkodisl, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
							.addGap(182))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(label_8)
									.addGap(18)
									.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(label_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spkol, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_3)
									.addGap(4)
									.addComponent(cBrez, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(label_4)
									.addGap(6)
									.addComponent(cBpcod_m, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(label_7)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
									.addGap(1)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_4)
							.addGap(187))))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(266)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(418, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(tFkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cBkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_1))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_3))
						.addComponent(cBrez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_4))
						.addComponent(cBpcod_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spkol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGap(48)
									.addComponent(label_8))
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGap(29)
									.addComponent(label_7))
								.addGroup(gl_panel_4.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))))
						.addComponent(button_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button)
						.addComponent(button_1))
					.addGap(24))
		);
		
		tFrez_name = new JTextPane();
		scrollPane_4.setViewportView(tFrez_name);
		
		tPop_name = new JTextPane();
		scrollPane_3.setViewportView(tPop_name);
		panel_4.setLayout(gl_panel_4);
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		gl_panel_10.setVerticalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addGap(1)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 229, Short.MAX_VALUE))
		);
		panel_10.setLayout(gl_panel_10);
		layeredPane.setLayout(gl_layeredPane);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Лаборатория", null, layeredPane_2, null);
		
		JPanel panel_2 = new JPanel();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_8 = new JPanel();
		panel_8.setVisible(false);
		
		JPanel panel_9 = new JPanel();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(5))
		);
		
		JButton btnNewButton_5 = new JButton("Сохранить");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tlab_isl.updateSelectedItem();
				
				LabIsl uplab = new LabIsl();
				
				for (int i=0; i<tlab_isl.getRowCount(); i++){
					if (tlab_isl.getData().get(i).zpok != null){
						uplab.setCpok(tlab_isl.getData().get(i).cpok);
						uplab.setZpok(tlab_isl.getData().get(i).zpok);
						uplab.setStoim(tlab_isl.getData().get(i).stoim);
						uplab.setNisl(tn_ldi.getSelectedItem().nisl);
						uplab.setNpasp(tn_ldi.getSelectedItem().npasp);
						uplab.setPcod_m(tlab_isl.getData().get(i).pcod_m);
						uplab.setStoim(tlab_isl.getData().get(i).stoim);
						uplab.setId(tlab_isl.getData().get(i).id);
						
						//System.out.print(uplab);
						try {
							MainForm.ltc.UpdLIsl(uplab);
						} catch (LIslExistsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					/*if (tlab_isl.getData().get(i).zpok == null){
						
					}*/
					}else{
						if (((String.valueOf(tn_ldi.getSelectedItem().id_gosp)==null)||(tn_ldi.getSelectedItem().id_gosp==0))
								&&(String.valueOf(tn_ldi.getSelectedItem().id_pos)==null)||(tn_ldi.getSelectedItem().id_pos==0)){
							try {
								MainForm.ltc.DelLIsl(tn_ldi.getSelectedItem().nisl, tlab_isl.getData().get(i).cpok, tlab_isl.getData().get(i).id);
							} catch (TException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				try {
					tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl, tn_ldi.getSelectedItem().pcisl));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton btnNewButton_6 = new JButton("Удалить");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					MainForm.ltc.DelLIsl(tn_ldi.getSelectedItem().nisl, tlab_isl.getSelectedItem().cpok, tlab_isl.getSelectedItem().id);
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					
					tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl,tn_ldi.getSelectedItem().pcisl));
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton btnNewButton_7 = new JButton("Добавить");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
/*				List<StringClassifier> dnli;
				List<Metod> dstoim;
				LabIsl nLI = new LabIsl();*/


				try {
					/*dnli = MainForm.ltc.GetKlasIsS_ot01(MainForm.authInfo.cpodr, tn_ldi.getSelectedItem().pcisl);
					
					
					dstoim = MainForm.ltc.GetLabStoim(tn_ldi.getSelectedItem().pcisl, MainForm.authInfo.cpodr);
					boolean check;
					
					for(int i = 0; i<dnli.size(); i++){
						check = false;
						for (int j = 0; j<tlab_isl.getRowCount(); j++){
							if (dnli.get(i).pcod.equals(tlab_isl.getData().get(j).cpok)){
								check = true;
								break;
							}
						}
						
						if (check == false){
							
						
							for (int j = 0; j<dstoim.size(); j++){
								
								if (dnli.get(i).pcod.equals(dstoim.get(j).pcod)){
									nLI.setNisl(tn_ldi.getSelectedItem().nisl);
									nLI.setNpasp(tn_ldi.getSelectedItem().npasp);
									nLI.setCpok(dnli.get(i).pcod);
									nLI.setPcod_m(dstoim.get(j).c_obst);
									nLI.setStoim(dstoim.get(j).stoim);
									
									try {
										
										MainForm.ltc.AddLIsl(nLI);
										
									} catch (LIslExistsException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									if ((j<dstoim.size()-1)&&(!dnli.get(i).pcod.equals(dstoim.get(j+1).pcod))){
										break;
									}
								}
								
							}
						}
						
					}*/	
					
					
					if ((tn_ldi.getSelectedItem().isSetPcisl()) &&(tn_ldi.getSelectedItem().isSetKodotd())){
						
						
						winVib.tLabMet.setData(MainForm.ltc.GetS_ot01IsMet(tn_ldi.getSelectedItem().kodotd, tn_ldi.getSelectedItem().pcisl));
						winVib.npasp = tn_ldi.getSelectedItem().npasp;
						winVib.nisl = tn_ldi.getSelectedItem().nisl;
						
						
						String si= null;
						String sm= null;
						
						String si1= null;
						String sm1= null;
						
						for (int i = 0; i< tlab_isl.getData().size(); i++){
							si = tlab_isl.getData().get(i).cpok;
							sm = tlab_isl.getData().get(i).pcod_m;
							
							for (int j = 0; j< winVib.tLabMet.getData().size(); j++){
								si1 = winVib.tLabMet.getData().get(j).pcod;
								sm1 = winVib.tLabMet.getData().get(j).pcod_m;								
								
								if (si.equals(si1)&&(sm.equals(sm1))){
									winVib.tLabMet.getModel().setValueAt(true, j, 3);
									
									break;
									
								}
									
									
							}
						}
						
						
						winVib.setVisible(true);
						
						
						
					
					
						tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl, tn_ldi.getSelectedItem().pcisl));
					}
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addGap(205)
					.addComponent(btnNewButton_7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_6)
					.addContainerGap(314, Short.MAX_VALUE))
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_5)
						.addComponent(btnNewButton_6)
						.addComponent(btnNewButton_7))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_9.setLayout(gl_panel_9);
		
		JLabel lblNewLabel_23 = new JLabel("Метод исследования");
		lblNewLabel_23.setVisible(false);
		
		cBLpcod_m = new ThriftStringClassifierCombobox<>(true);
		cBLpcod_m.setVisible(false);
		cBLpcod_m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
			if((cBLpcod_m.getSelectedItem() != null)&&(tlab_isl.getSelectedItem() != null)
				&&((tlab_isl.getSelectedItem().pcod_m == null)||(!tlab_isl.getSelectedItem().pcod_m.equals(cBLpcod_m.getSelectedPcod())))){

				LabIsl upLabMet = new LabIsl();
				try {
					upLabMet.setPcod_m(cBLpcod_m.getSelectedPcod());
					upLabMet.setZpok(tlab_isl.getSelectedItem().zpok);
					upLabMet.setNisl(tn_ldi.getSelectedItem().nisl);
					upLabMet.setCpok(tlab_isl.getSelectedItem().cpok);
					upLabMet.setId(tlab_isl.getSelectedItem().id);
						List<Metod> Cena;
						
						try {
							Cena = MainForm.ltc.GetStoim(tlab_isl.getSelectedItem().cpok, cBLpcod_m.getSelectedPcod(), MainForm.authInfo.cpodr);
							
							if (Cena.size() !=0){
								upLabMet.setStoim(Cena.get(0).stoim);
								tlab_isl.getData().get(tlab_isl.getSelectedRow()).setStoim(Cena.get(0).stoim);
							}
							
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					
					
					
					
					//System.out.print(upLabMet);
				
					MainForm.ltc.UpdLIsl(upLabMet);
				
					//tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
				
					tlab_isl.setValueAt(cBLpcod_m.getSelectedItem().name, tlab_isl.getSelectedRow(), 2);
				
					tlab_isl.getData().get(tlab_isl.getSelectedRow()).setPcod_m(cBLpcod_m.getSelectedPcod());
				
					tlab_isl.updateSelectedItem();
				
				
				} catch (LIslExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
								
			/*	}else{
					if ((tlab_isl.getSelectedItem() != null)&&(tlab_isl.getSelectedItem().pcod_m == null)){
						LabIsl upLabMet = new LabIsl();
						try {
							upLabMet.setPcod_m(cBLpcod_m.getSelectedPcod());
							upLabMet.setZpok(tlab_isl.getSelectedItem().zpok);
							upLabMet.setNisl(tn_ldi.getSelectedItem().nisl);
							upLabMet.setCpok(tlab_isl.getSelectedItem().cpok);
							
								List<Metod> Cena;
								
								try {
									Cena = MainForm.ltc.GetStoim(tlab_isl.getSelectedItem().cpok, cBLpcod_m.getSelectedPcod(), MainForm.authInfo.cpodr);
									
									if (Cena.size() !=0){
										upLabMet.setStoim(Cena.get(0).stoim);
										tlab_isl.getData().get(tlab_isl.getSelectedRow()).setStoim(Cena.get(0).stoim);
									}
									
								} catch (TException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
							
							
							
							
							//System.out.print(upLabMet);
						
							MainForm.ltc.UpdLIsl(upLabMet);
						
							//tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
							if (cBLpcod_m.getSelectedItem()!=null)	
							tlab_isl.setValueAt(cBLpcod_m.getSelectedItem().name, tlab_isl.getSelectedRow(), 2);
						
							tlab_isl.getData().get(tlab_isl.getSelectedRow()).setPcod_m(cBLpcod_m.getSelectedPcod());
						
							tlab_isl.updateSelectedItem();
						
						
						} catch (LIslExistsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								
						
					}*/
				}	
			}
		});
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addGap(207)
					.addComponent(lblNewLabel_23)
					.addGap(10)
					.addComponent(cBLpcod_m, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(207, Short.MAX_VALUE))
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_23))
						.addComponent(cBLpcod_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_8.setLayout(gl_panel_8);
		panel_3.setLayout(gl_panel_3);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
		);
		
		tlab_isl = new CustomTable<>(true, true, LabIsl.class, 2, "Код показателя", 3, "Наименование показателя", 8, "Метод исследования", 4, "Значение");
		
		
		
		tlab_isl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
                try {
                    if ((cBpcisl.getSelectedItem() != null) && (tlab_isl.getSelectedItem() !=null)){
                       // cBLpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), tlab_isl.getSelectedItem().cpok));
    					int age = (int) ((tn_ldi.getSelectedItem().datav - tpatient.getSelectedItem().datar) / 31556952000L);
    						
    						
    					if (age < 18 ){
    						cBLpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), tlab_isl.getSelectedItem().cpok,2));
    							
    						if (cBpcod_m.getItemCount()==0)
    								
    							cBLpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), tlab_isl.getSelectedItem().cpok,3));
    					}else{
    							
    						cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod(),1));
    							
    						if (cBpcod_m.getItemCount()==0)
    								
    							cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod(),3));
    							
    					}
    						
                    	if ((tlab_isl.getSelectedItem().pcod_m != null)&&( tlab_isl.getSelectedItem().pcod_m.trim() !="")){
                        	try{
                        		cBLpcod_m.setSelectedPcod(tlab_isl.getSelectedItem().pcod_m);
                        	}catch (Exception e){
                        		cBLpcod_m.setSelectedItem(0);
                        	}
                        	
                        }else{
                        	cBLpcod_m.setSelectedItem(0);
                        }
                    }else{
                    	cBLpcod_m.setSelectedItem(0);
                    }
                } catch (TException e) {
                    // TODO Auto-generated catch block
                	cBLpcod_m.setSelectedItem(1);
                    e.printStackTrace();
                }
				
				
				
			}	
		});
		//tlab_isl.setDate
		
		scrollPane_2.setViewportView(tlab_isl);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_layeredPane_2 = new GroupLayout(layeredPane_2);
		gl_layeredPane_2.setHorizontalGroup(
			gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGap(1)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
					.addGap(1))
		);
		gl_layeredPane_2.setVerticalGroup(
			gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addGap(1)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
					.addGap(1))
		);
		layeredPane_2.setLayout(gl_layeredPane_2);
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.TRAILING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
		);
		panel2.setLayout(gl_panel2);
		GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(
			gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_15.createSequentialGroup()
					.addGap(1)
					.addComponent(panel2, GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
		);
		gl_panel_15.setVerticalGroup(
			gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_15.createSequentialGroup()
					.addGap(1)
					.addComponent(panel2, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
		);
		panel_15.setLayout(gl_panel_15);
		panel_13.setLayout(gl_panel_13);
		panel1.setLayout(gl_panel1);
		frame.getContentPane().setLayout(groupLayout);
		
	
	}
	
	public void filtPat() {
		try {
			//tpatient.setData(MainForm.ltc.getPatDat(new SimpleDateFormat("dd.MM.yyyy").parse("14.08.2012").getTime(), 2000004));
				
				//System.out.println(System.currentTimeMillis());
				tpatient.setData(MainForm.ltc.getPatDat(MainForm.authInfo.cpodr, System.currentTimeMillis()));
			
			//tn_ldi.setData(MainForm.ltc.GetObInfIslt( tpatient.getSelectedItem().npasp, 2000004));
			if (tpatient.getSelectedItem()!= null){
			//System.out.print(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
			tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
			}
			
		} catch (PatientNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}



