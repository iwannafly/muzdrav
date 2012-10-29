package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.AnamZab;
import ru.nkz.ivcgzo.thriftOsm.Cgosp;
import ru.nkz.ivcgzo.thriftOsm.Cotd;
import ru.nkz.ivcgzo.thriftOsm.IsslInfo;
import ru.nkz.ivcgzo.thriftOsm.IsslMet;
import ru.nkz.ivcgzo.thriftOsm.Napr;
import ru.nkz.ivcgzo.thriftOsm.NaprKons;
import ru.nkz.ivcgzo.thriftOsm.PNapr;
import ru.nkz.ivcgzo.thriftOsm.P_isl_ld;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Pdisp;
import ru.nkz.ivcgzo.thriftOsm.PdispNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PokazMet;
import ru.nkz.ivcgzo.thriftOsm.Prez_d;
import ru.nkz.ivcgzo.thriftOsm.Prez_l;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Protokol;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Shablon;
import ru.nkz.ivcgzo.thriftOsm.ShablonText;
import ru.nkz.ivcgzo.thriftOsm.Vypis;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

public class Vvod extends JFrame {
	private static final long serialVersionUID = 4761424994673488103L;
	private CustomTable<PvizitAmb, PvizitAmb._Fields> tblPos;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbVidOpl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbCelObr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbRez;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbMobs;
	private CustomTextField tbShabSrc;
	private CustomTextField tbStatTemp;
	private CustomTextField tbStatAd;
	private CustomTextField tbStatRost;
	private CustomTextField tbStatVes;
	private CustomTextField tbStatChss;
	private CustomTable<PdiagAmb, PdiagAmb._Fields> tblDiag;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbDiagVidTr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbDiagObstReg;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbDiagDispGrup;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbDiagDispIsh;
	private CustomDateEditor tbDiagDispDatVz;
	private CustomDateEditor tbDiagDispDatIsh;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbNaprMesto;
	private ThriftStringClassifierCombobox<StringClassifier> cmbOrgan;
	private CustomTable<PokazMet, PokazMet._Fields> tblNaprPokazMet;
	private CustomTextField tbNaprKab;
	private JTextArea tbJal;
	private JTextArea tbLoc;
	private JTextArea tbFiz;
	private JTextArea tbOcen;
	private JTextArea tbStat;
	private JTextArea tbZaklRek;
	private JTextArea tbZakl;
	private JTextArea tbAnam;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbZaklIsh;
	private JTextArea tbDiagOpis;
	private ButtonGroup bgDiagStat;
	private ButtonGroup bgDiagPredv;
	private ButtonGroup bgDiagStadZab;
	private ButtonGroup bgDiagHarZab;
	private JRadioButton rbtDiagOsn;
	private JRadioButton rbtDiagSop;
	private JRadioButton rbtDiagOsl;
	private JRadioButton rbtDiagPredv;
	private JRadioButton rbtDiagZakl;
	private JRadioButton rbtDiagHarOstr;
	private JRadioButton rbtDiagHarHron;
	private JRadioButton rbtDiagStadRan;
	private JRadioButton rbtDiagStadPoz;
	private JButton btnDispHron;
	private JCheckBox chbDiagBoe;
	private JCheckBox chbDiagInv;
	private JCheckBox chbDiagBer;
	private JTextArea tbLech;
	private JButton btnRecPriem;
	private JButton btnBer;
	private JButton btnPrint;
	private JLabel lblLastShab;
	private ThriftIntegerClassifierList lbShabSrc;
	private ShablonSearchListener shablonSearchListener;
	private JButton btnPosAdd;
	private JButton btnPosSave;
	private ButtonGroup bgInvUst;
	private JRadioButton rbtInvUst1;
	private JRadioButton rbtInvUst2;
	private JPanel pnlInvUst;
	private CustomTextField tfNuch;
	
	private List<IntegerClassifier> listVidIssl;
	public static ZapVr zapVr;
	public static PvizitAmb pvizitAmb;
	private Priem priem;
	private AnamZab anamZab;
	public static Pvizit pvizit;
	private PdiagAmb diagamb;
	public static PdiagZ pdiag;
	public static Pdisp pdisp;
	
	private FormSign sign;
	private FormPostBer postber;
	private ShablonForm shablonform;
	private DispHron disphron;
	private Color defCol = UIManager.getColor("TabbedPane.foreground");
	private Color selCol = Color.red;
	private CustomTextField tfNewDs;
	private CustomDateEditor tfDataIzmNewDs;
	private String diag_named;
	private Priem priemCopy;
	private AnamZab anamZabCopy;
	private Pvizit pvizitCopy;
	private PvizitAmb pvizitAmbCopy;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbLpu;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode issinfo;
	private JTree treeRezIssl;
	private StringBuilder sb;
	private JEditorPane epTxtRezIssl;
	private JTextArea tbRecom;
	private static final String lineSep = System.lineSeparator();
	private JComboBox<String> cmbConsVidNapr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbVidStacionar;
	private JTextArea tbKonsObosnov;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbKonsMesto;
	private JLabel lblVidStacionar;
	private JLabel lblKonsMesto;
	
	/**
	 * Create the dialog.
	 */
	public Vvod() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
				try {
					if (!checkInput()) {
						JOptionPane.showMessageDialog(Vvod.this, "Пациент не записан на следующий прием, ему не проставлен исход случая обращения или не поставлен диагноз.", "Предупреждение", JOptionPane.ERROR_MESSAGE);
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
					
					if (!checkTalInput()) {
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
					
					if (checkDataChanged()) {
						int res = JOptionPane.showConfirmDialog(Vvod.this, "Данные изменились, но не были сохранены. Сохранить?", "Подтверждение", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
						
						if (res == JOptionPane.YES_OPTION) {
							btnPosSave.doClick();
						} else if (res == JOptionPane.NO_OPTION) {
							
						} else {
							setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							return;
						}
					}
					
					MainForm.instance.updateZapList();
					MainForm.instance.setVisible(true);
				} catch (TException e1) {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		setExtendedState(Frame.MAXIMIZED_BOTH);
		sign = new FormSign();
		MainForm.instance.addChildFrame(sign);
		
		postber = new FormPostBer();
		MainForm.instance.addChildFrame(postber);
		
		shablonform = new ShablonForm();
		MainForm.instance.addChildFrame(shablonform);
		
		disphron = new DispHron();
		MainForm.instance.addChildFrame(disphron);
		
		setTitle("Врачебный осмотр");
		setBounds(100, 100, 1024, 755);
		
		JButton btnAnam = new JButton("Анамнез жизни");
		btnAnam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sign.showPsign();
			}
		});
		
		JPanel panel = new JPanel();
		
		JPanel pnlTalon = new JPanel();
		pnlTalon.setBorder(new TitledBorder(null, "Талон пациента", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		
		JButton btnProsm = new JButton("Просмотр");
		btnProsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainForm.conMan.showPatientInfoForm(String.format("Просмотр информации на пациента %s %s %s", zapVr.fam, zapVr.im, zapVr.oth), zapVr.npasp);
			}
		});
		
		btnBer = new JButton("Наблюдение за беременными");
		btnBer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		       		postber.setVisible(true);	
			}
	});
		btnPrint = new JButton("Печатные формы");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JPopupMenu menu = new JPopupMenu();
	            
	       		JMenuItem mi1 = new JMenuItem("Случай заболевания");
	       		mi1.addActionListener(new ActionListener() {
	       			@Override
	       			public void actionPerformed(ActionEvent arg0) {
	       				try{
       						Protokol protokol = new Protokol();
       						protokol.setUserId(MainForm.authInfo.getUser_id());
       						protokol.setNpasp(Vvod.zapVr.getNpasp());
       						protokol.setPvizit_id(tblPos.getSelectedItem().id_obr);
       						protokol.setPvizit_ambId(tblPos.getSelectedItem().id);
       						protokol.setCpol(MainForm.authInfo.getCpodr());
       						String servPath = MainForm.tcl.printProtokol(protokol);
       						String cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
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
	       		menu.add(mi1);
	       		
				JMenuItem mi2 = new JMenuItem("Выписка из карты");
				mi2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
							Vypis vp = new Vypis();
							vp.setNpasp(Vvod.zapVr.getNpasp());
							vp.setPvizit_id(tblPos.getSelectedItem().id_obr);
							vp.setUserId(MainForm.authInfo.getUser_id());
							vp.setCpodr_name(MainForm.authInfo.getCpodr_name());
							vp.setClpu_name(MainForm.authInfo.getClpu_name());
							
							String servPath = MainForm.tcl.printVypis(vp);
							String cliPath = File.createTempFile("vypis", ".htm").getAbsolutePath();
							MainForm.conMan.transferFileFromServer(servPath, cliPath);
       						MainForm.conMan.openFileInEditor(cliPath, false);
						}
						catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				menu.add(mi2);
				
				JMenuItem mi3 = new JMenuItem("Протокол заключения КЭК");
				mi3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
								String servPath = MainForm.tcl.printKek(Vvod.zapVr.getNpasp(), tblPos.getSelectedItem().id_obr);
								String cliPath = File.createTempFile("kek", ".htm").getAbsolutePath();
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
				menu.add(mi3);
				
				JMenuItem mi4 = new JMenuItem("Направление на МСЭК");
				mi4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
								String servPath = MainForm.tcl.printMSK(zapVr.getNpasp());
								String cliPath = File.createTempFile("msk", ".htm").getAbsolutePath();
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
				menu.add(mi4);
				
				menu.show(btnPrint, 0, btnPrint.getHeight());
			}
		});
		
		btnRecPriem = new JButton("Запись на прием");
		btnRecPriem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                	MainForm.conMan.showReceptionRecordForm(zapVr.npasp, zapVr.fam, zapVr.im, zapVr.oth, zapVr.id_pvizit);
					
					checkZapVrNext();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(Vvod.this, "Не удалось отобразить форму записи на прием", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlTalon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1260, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(btnRecPriem, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAnam, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnProsm, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBer, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRecPriem)
						.addComponent(btnAnam)
						.addComponent(btnProsm)
						.addComponent(btnBer)
						.addComponent(btnPrint))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlTalon, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
					.addGap(4))
		);
		
		shablonSearchListener = new ShablonSearchListener();
		
		tbShabSrc = new CustomTextField(true, true, false);
		tbShabSrc.getDocument().addDocumentListener(shablonSearchListener);
		tbShabSrc.setColumns(10);
		
		JButton btnShabSrc = new JButton("...");
		btnShabSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shablonform.showShablonForm(tbShabSrc.getText(), lbShabSrc.getSelectedValue());
				syncShablonList(shablonform.getSearchString(), shablonform.getShablon());
				pasteShablon(shablonform.getShablon());
			}
		});
		
		JScrollPane spShabSrc = new JScrollPane();
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					JLabel lbl = (JLabel) tabbedPane.getTabComponentAt(i);
					
					if (lbl != null)
						if (i == tabbedPane.getSelectedIndex())
							lbl.setForeground(selCol);
						else
							lbl.setForeground(defCol);
				}
			}
		});
		
		lblLastShab = new JLabel("<html>Последний выбранный шаблон: </html>");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 763, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
						.addComponent(lblLastShab, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(tbShabSrc, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnShabSrc, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tbShabSrc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnShabSrc, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblLastShab)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 466, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		lbShabSrc = new ThriftIntegerClassifierList();
		lbShabSrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) 
					if (lbShabSrc.getSelectedValue() != null) {
						try {
							pasteShablon(MainForm.tcl.getShOsm(lbShabSrc.getSelectedValue().pcod));
						} catch (KmiacServerException e1) {
							JOptionPane.showMessageDialog(Vvod.this, "Ошибка загрузка шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
					}
			}
		});
		spShabSrc.setViewportView(lbShabSrc);
		
		JPanel pnlJal = new JPanel();
		tabbedPane.addTab("<html><br>Жалобы</html>", null, pnlJal, null);
		tabbedPane.setTabComponentAt(0, new JLabel("<html><br>Жалобы</html>"));
		((JLabel) tabbedPane.getTabComponentAt(0)).setForeground(selCol);
		
		JScrollPane spJal = new JScrollPane();
		GroupLayout gl_pnlJal = new GroupLayout(pnlJal);
		gl_pnlJal.setHorizontalGroup(
			gl_pnlJal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlJal.createSequentialGroup()
					.addContainerGap()
					.addComponent(spJal, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlJal.setVerticalGroup(
			gl_pnlJal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlJal.createSequentialGroup()
					.addContainerGap()
					.addComponent(spJal, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbJal = new JTextArea();
		tbJal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbJal.setLineWrap(true);
		tbJal.setWrapStyleWord(true);
		spJal.setViewportView(tbJal);
		pnlJal.setLayout(gl_pnlJal);
		
		JPanel pnlAnam = new JPanel();
		tabbedPane.addTab("<html>История заболевания<br> (anamnesis morbi)</html>", null, pnlAnam, null);
		tabbedPane.setTabComponentAt(1, new JLabel("<html>История заболевания<br> (anamnesis morbi)</html></html>"));
		
		JScrollPane spAnam = new JScrollPane();
		GroupLayout gl_pnlAnam = new GroupLayout(pnlAnam);
		gl_pnlAnam.setHorizontalGroup(
			gl_pnlAnam.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAnam.createSequentialGroup()
					.addContainerGap()
					.addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlAnam.setVerticalGroup(
			gl_pnlAnam.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAnam.createSequentialGroup()
					.addContainerGap()
					.addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbAnam = new JTextArea();
		tbAnam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbAnam.setLineWrap(true);
		tbAnam.setWrapStyleWord(true);
		spAnam.setViewportView(tbAnam);
		pnlAnam.setLayout(gl_pnlAnam);
		
		JPanel pnlStat = new JPanel();
		tabbedPane.addTab("<html>Объективный статус <br>(status praesense)</html>", null, pnlStat, null);
		tabbedPane.setTabComponentAt(2, new JLabel("<html>Объективный статус <br>(status praesense)</html>"));
		
		tbStatTemp = new CustomTextField();
		tbStatTemp.setColumns(10);
		
		JLabel lblStatTemp = new JLabel("Темп.");
		
		JLabel lbStatlAd = new JLabel("АД");
		
		tbStatAd = new CustomTextField();
		tbStatAd.setColumns(10);
		
		JLabel lblStatRost = new JLabel("Рост");
		
		tbStatRost = new CustomTextField();
		tbStatRost.setColumns(10);
		
		JLabel lblStatVes = new JLabel("Вес");
		
		tbStatVes = new CustomTextField();
		tbStatVes.setColumns(10);
		
		JScrollPane spStat = new JScrollPane();
		
		JLabel lblStatChss = new JLabel("Чсс");
		
		tbStatChss = new CustomTextField();
		tbStatChss.setColumns(10);
		GroupLayout gl_pnlStat = new GroupLayout(pnlStat);
		gl_pnlStat.setHorizontalGroup(
			gl_pnlStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStat.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlStat.createParallelGroup(Alignment.LEADING)
						.addComponent(spStat)
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addComponent(lblStatTemp, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbStatTemp, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lbStatlAd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(tbStatAd, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblStatRost, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(tbStatRost, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblStatVes, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(tbStatVes, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblStatChss, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbStatChss, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlStat.setVerticalGroup(
			gl_pnlStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStat.createSequentialGroup()
					.addGroup(gl_pnlStat.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlStat.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlStat.createParallelGroup(Alignment.BASELINE)
									.addComponent(tbStatVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblStatChss)
									.addComponent(tbStatChss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(tbStatRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tbStatAd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlStat.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblStatTemp)
									.addComponent(tbStatTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(13)
							.addComponent(lbStatlAd))
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(13)
							.addComponent(lblStatRost))
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(13)
							.addComponent(lblStatVes)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spStat, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbStat = new JTextArea();
		tbStat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbStat.setWrapStyleWord(true);
		tbStat.setLineWrap(true);
		spStat.setViewportView(tbStat);
		pnlStat.setLayout(gl_pnlStat);
		
		JPanel pnlFiz = new JPanel();
		tabbedPane.addTab("<html><br>Физикальное обследование</html>", null, pnlFiz, null);
		tabbedPane.setTabComponentAt(3, new JLabel("<html><br>Физикальное обследование</html>"));
		
		JScrollPane spFiz = new JScrollPane();
		
		GroupLayout gl_pnlFiz = new GroupLayout(pnlFiz);
		gl_pnlFiz.setHorizontalGroup(
				gl_pnlFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlFiz.createSequentialGroup()
					.addContainerGap()
					.addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlFiz.setVerticalGroup(
				gl_pnlFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlFiz.createSequentialGroup()
					.addContainerGap()
					.addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbFiz = new JTextArea();
		tbFiz.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbFiz.setLineWrap(true);
		tbFiz.setWrapStyleWord(true);
		spFiz.setViewportView(tbFiz);
		pnlFiz.setLayout(gl_pnlFiz);
		
		JPanel pnlLoc = new JPanel();
		tabbedPane.addTab("<html>Локальный статус<br> (localis status)<br></html>", null, pnlLoc, null);
		tabbedPane.setTabComponentAt(4, new JLabel("<html>Локальный статус<br> (localis status)<br></html>"));
		
		JScrollPane spLoc = new JScrollPane();
		GroupLayout gl_pnlLoc = new GroupLayout(pnlLoc);
		gl_pnlLoc.setHorizontalGroup(
			gl_pnlLoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLoc.createSequentialGroup()
					.addContainerGap()
					.addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlLoc.setVerticalGroup(
			gl_pnlLoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLoc.createSequentialGroup()
					.addContainerGap()
					.addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbLoc = new JTextArea();
		tbLoc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbLoc.setLineWrap(true);
		tbLoc.setWrapStyleWord(true);
		spLoc.setViewportView(tbLoc);
		pnlLoc.setLayout(gl_pnlLoc);
		
		JPanel pnlDiag = new JPanel();
		tabbedPane.addTab("<html><br>Диагноз</html>", null, pnlDiag, null);
		tabbedPane.setTabComponentAt(5, new JLabel("<html><br>Диагноз</html>"));
		
		JPanel PnlDiag = new JPanel();
		
		JScrollPane spDiag = new JScrollPane();
		
		JButton btnDiagAdd = new JButton("");
		btnDiagAdd.setToolTipText("Добавление новой записи");
		btnDiagAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		addDiag(MainForm.conMan.showMkbTreeForm("Диагноз", ""));
			}
		});
		btnDiagAdd.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		
		final JButton btnDiagDel = new JButton("");
		btnDiagDel.setToolTipText("Удаление записи");
		btnDiagDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
					if (tblDiag.getSelectedItem()!= null)
					if (JOptionPane.showConfirmDialog(Vvod.this, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		  			MainForm.tcl.DeletePdiagAmb(tblDiag.getSelectedItem().getId());
					tblDiag.setData(MainForm.tcl.getPdiagAmb(Vvod.zapVr.getId_pvizit()));}
					if (tblDiag.getRowCount() > 0)
						tblDiag.setRowSelectionInterval(tblDiag.getRowCount() - 1, tblDiag.getRowCount() - 1);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		btnDiagDel.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		
		JButton btnDiagSave = new JButton("");
		btnDiagSave.setToolTipText("Сохранение изменений");
		btnDiagSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
			  		diagamb.setDiag(tblDiag.getSelectedItem().getDiag());
			  		diagamb.setNamed(getTextOrNull(tbDiagOpis.getText()));
			  		diagamb.setDatad(tblDiag.getSelectedItem().getDatad());
			  		diagamb.setDatap(pvizitAmb.getDatap());
			  		if (rbtDiagOsn.isSelected()) diagamb.setDiag_stat(1);
			  		if (rbtDiagSop.isSelected())diagamb.setDiag_stat(3);
			  		if (rbtDiagOsl.isSelected()) diagamb.setDiag_stat(2);
			  		if (cmbDiagObstReg.getSelectedPcod() != null) diagamb.setObstreg(cmbDiagObstReg.getSelectedPcod()); else diagamb.unsetObstreg();
			  		if (cmbDiagVidTr.getSelectedPcod() != null) diagamb.setVid_tr(cmbDiagVidTr.getSelectedPcod()); else diagamb.unsetVid_tr();
			  		
			  		pdiag = new PdiagZ();
			  		pdisp = new Pdisp();
			  		if (rbtDiagPredv.isSelected())
			  			diagamb.setPredv(true);
			  		if (rbtDiagZakl.isSelected()) {
			  			diagamb.setPredv(false);
			  			pdiag.setId_diag_amb(diagamb.getId());
			  			pdiag.setNpasp(diagamb.getNpasp());
			  			pdiag.setDiag(diagamb.getDiag());
			  			pdiag.setCpodr(MainForm.authInfo.getCpodr());
			  			pdiag.setNmvd(diagamb.getObstreg());
			  			pdiag.setCod_sp(diagamb.getCod_sp());
			  			pdiag.setCdol_ot(diagamb.getCdol());
			  			pdiag.setNamed(diagamb.getNamed());
			  			pdiag.setUch(zapVr.getNuch());
			  			if (rbtInvUst1.isSelected())pdiag.setPpi(1);
			  			if (rbtInvUst2.isSelected())pdiag.setPpi(2);
			  			if (rbtDiagHarOstr.isSelected()) pdiag.setXzab(1);
			  			if (rbtDiagHarHron.isSelected()) pdiag.setXzab(2);
			  			if (rbtDiagStadPoz.isSelected()) pdiag.setStady(2);
			  			if (rbtDiagStadRan.isSelected()) pdiag.setStady(1);
			  			if (chbDiagBer.isSelected()) pdiag.setPat(1);
			  			if (chbDiagBoe.isSelected()) pdiag.setPrizb(1);
			  			if (chbDiagInv.isSelected()) pdiag.setPrizi(1);
			  			if (tbDiagDispDatVz.getDate() != null) pdiag.setDisp(1);
			  			MainForm.tcl.setPdiag(pdiag);
			  		}
		  		if (diagamb.isSetNamed()) MainForm.tcl.UpdatePdiagAmb(diagamb);
		  		else JOptionPane.showMessageDialog(Vvod.this, "Введите описание диагноза");
		  		
	  			if (tbDiagDispDatVz.getDate() != null){
		  			pdisp.setId_diag(diagamb.getId());
		  			pdiag.setId_diag_amb(diagamb.getId());
		  			pdisp.setNpasp(diagamb.getNpasp());
		  			pdisp.setDiag(diagamb.getDiag());
		  			pdisp.setPcod(MainForm.authInfo.getCpodr());
					pdisp.setD_vz(tbDiagDispDatVz.getDate().getTime());
		  			if (tbDiagDispDatIsh.getDate() != null)
						pdisp.setDataish(tbDiagDispDatIsh.getDate().getTime());
		  			if (cmbDiagDispIsh.getSelectedPcod() != null) pdisp.setIshod(cmbDiagDispIsh.getSelectedPcod()); else pdisp.unsetIshod();
			  		if (cmbDiagDispGrup.getSelectedPcod() != null) pdisp.setD_grup(cmbDiagDispGrup.getSelectedPcod()); else pdisp.unsetD_grup();


		  			pdisp.setCod_sp(diagamb.getCod_sp());
		  			pdisp.setCdol_ot(diagamb.getCdol());
		  			tfNuch.setText(pdisp.getCdol_ot()+zapVr.getNuch());
		  			pdisp.setD_uch(Integer.valueOf(tfNuch.getText()));
		  			pdisp.setDiag_n(tfNewDs.getText());
		  			MainForm.tcl.setPdisp(pdisp);
		  			if (!tfNewDs.isEmpty() && !tfNewDs.getText().equals(diagamb.diag)) {
		  				diagamb.setDiag(tfNewDs.getText());
		  				diagamb.setId_obr(zapVr.getId_pvizit());
				  		diagamb.setNpasp(zapVr.getNpasp());
				  		diagamb.setDatap(System.currentTimeMillis());
				  		diagamb.setDatad(System.currentTimeMillis());
				  		diagamb.setCod_sp(MainForm.authInfo.getPcod());
				  		diagamb.setCdol(MainForm.authInfo.getCdol());
				  		diagamb.setPredv(false);
				  		diagamb.setDiag_stat(1);
						diagamb.setNamed(diag_named);
						diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
						pdiag.setId_diag_amb(diagamb.getId());
						pdiag.setDiag(tfNewDs.getText());
						MainForm.tcl.setPdiag(pdiag);
						pdisp.setId_diag(diagamb.getId());
		  				pdisp.setDiag_s(tblDiag.getSelectedItem().diag);
		  				pdisp.setDiag(tfNewDs.getText());
		  				pdisp.setDiag_n(null);
		  				pdisp.setDatad(tfDataIzmNewDs.getDate().getTime());
		  				MainForm.tcl.setPdisp(pdisp); 
		  				tblDiag.setData(MainForm.tcl.getPdiagAmb(zapVr.getId_pvizit()));
		  			}
		  			/*pdiag*/
		  				pdiag.setD_vz(pdisp.getD_vz());
		  				pdiag.setDataish(pdisp.getDataish());
			  		pdiag.setIshod(pdisp.getIshod());
			  		pdiag.setD_grup(pdisp.getD_grup());
			  		MainForm.tcl.setPdiag(pdiag);	
	  			} 
			  	} catch (KmiacServerException e1) {
		  		e1.printStackTrace();
		  	} catch (TException e1) {
		  		MainForm.conMan.reconnect(e1);
		  	}
			}
		});
		btnDiagSave.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		GroupLayout gl_PnlDiag = new GroupLayout(PnlDiag);
		gl_PnlDiag.setHorizontalGroup(
			gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PnlDiag.createSequentialGroup()
					.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, 517, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDiagDel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDiagSave, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDiagAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addGap(123))
		);
		gl_PnlDiag.setVerticalGroup(
			gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PnlDiag.createSequentialGroup()
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(spDiag, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
						.addGroup(gl_PnlDiag.createSequentialGroup()
							.addComponent(btnDiagAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDiagDel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDiagSave, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
					.addGap(0))
		);
		
		tblDiag = new CustomTable<>(true, true, PdiagAmb.class, 7, "Дата установления диагноза", 3, "Код МКБ");
		tblDiag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent  e) {
				if (e.getClickCount() == 2)
					if (tblDiag.getSelectedItem().diag != null)
						if (tblDiag.getSelectedColumn() == 1) {
							StringClassifier res = ConnectionManager.instance.showMkbTreeForm("Диагнозы", tblDiag.getSelectedItem().diag);
							if (res != null) {
								tblDiag.getSelectedItem().setDiag(res.pcod);
								tbDiagOpis.setText(res.name);
								tblDiag.updateSelectedItem();
							}
					}
			}
		});
		tblDiag.setDateField(0);
		tblDiag.setEditableFields(false, 1);
		tblDiag.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					if (tblDiag.getSelectedItem()!= null) {
						diagamb = tblDiag.getSelectedItem();
							
						try {
							pdiag = MainForm.tcl.getPdiagZ(diagamb.getId());
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (PdiagNotFoundException e1) {
							System.out.println("diagZ not found");
							pdiag = new PdiagZ();
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
						
						try {
							pdisp = MainForm.tcl.getPdisp(zapVr.getNpasp(),tblDiag.getSelectedItem().diag);
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (PdispNotFoundException e1) {
							System.out.println("disp not found");
							pdisp = new Pdisp();
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
					} else {
						diagamb = new PdiagAmb();
						pdiag = new PdiagZ();
						pdisp = new Pdisp();
					}
					
					tbDiagOpis.setText(diagamb.getNamed());
					tbDiagOpis.setCaretPosition(0);
					bgDiagStat.clearSelection();
					rbtDiagOsn.setSelected(diagamb.diag_stat == 1);
					rbtDiagSop.setSelected(diagamb.diag_stat == 3);
					rbtDiagOsl.setSelected(diagamb.diag_stat == 2);
					bgDiagPredv.clearSelection();
					if (diagamb.predv)
						rbtDiagPredv.doClick();
					else if (tblDiag.getSelectedItem() !=  null)
						rbtDiagZakl.doClick();
					else
						rbtDiagPredv.doClick();
					if (diagamb.isSetObstreg()) cmbDiagObstReg.setSelectedPcod(diagamb.getObstreg());else cmbDiagObstReg.setSelectedItem(null);
					if (diagamb.isSetVid_tr()) cmbDiagVidTr.setSelectedPcod(diagamb.getVid_tr()); else cmbDiagVidTr.setSelectedItem(null);
					
					bgDiagStadZab.clearSelection();
					rbtDiagStadRan.setSelected(pdiag.getStady() == 1);
					rbtDiagStadPoz.setSelected(pdiag.getStady() == 2);
					bgDiagHarZab.clearSelection();
					rbtDiagHarOstr.setSelected(pdiag.getXzab() == 1);
					rbtDiagHarHron.setSelected(pdiag.getXzab() == 2);
					chbDiagBer.setSelected(pdiag.getPat() == 1);
					chbDiagBoe.setSelected(pdiag.getPrizb() == 1);
					chbDiagInv.setSelected(pdiag.getPrizi() == 1);
		  			rbtInvUst1.setEnabled(chbDiagInv.isSelected());
		  			rbtInvUst2.setEnabled(chbDiagInv.isSelected());
					bgInvUst.clearSelection();
					rbtInvUst1.setSelected(pdiag.getPpi() == 1);
					rbtInvUst2.setSelected(pdiag.getPpi() == 2);
					
					if (pdisp.isSetD_vz())
						tbDiagDispDatVz.setDate(pdisp.getD_vz());
					else
						tbDiagDispDatVz.setValue(null);
					
					if (pdisp.isSetDataish())
						tbDiagDispDatIsh.setDate(pdisp.getDataish());
					else
						tbDiagDispDatIsh.setValue(null);
					
					if (pdisp.isSetIshod())
						cmbDiagDispIsh.setSelectedPcod(pdisp.getIshod());
					else
						cmbDiagDispIsh.setSelectedItem(null);
					if (pdisp.isSetD_grup())
						cmbDiagDispGrup.setSelectedPcod(pdisp.getD_grup());
					else
						cmbDiagDispGrup.setSelectedItem(null);
					tfNuch.setText(String.valueOf(pdisp.getD_uch()));
				}
			}
		});
		tblDiag.setFillsViewportHeight(true);
		spDiag.setViewportView(tblDiag);
		PnlDiag.setLayout(gl_PnlDiag);
		
		JScrollPane spDiagOpis = new JScrollPane();
		
		JLabel lblDiagOpis = new JLabel("<html>Описание диагноза</html>");
		
		JPanel pnlDiagStat = new JPanel();
		pnlDiagStat.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		bgDiagStat = new ButtonGroup();
		
		JPanel pnlDiagPredv = new JPanel();
		pnlDiagPredv.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		MouseAdapter maBtnDesel = new MouseAdapter() {
			JRadioButton btn = null;
			boolean deselect = false;
			
			@Override
			public void mousePressed(MouseEvent e) {
				btn = (JRadioButton) e.getSource();
				deselect = btn.isSelected();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (deselect)
					((DefaultButtonModel) btn.getModel()).getGroup().clearSelection();
			}
		};
		
		bgDiagPredv = new ButtonGroup();
		
		rbtDiagPredv = new JRadioButton("Предварительный");
		rbtDiagPredv.setSelected(true);
		rbtDiagPredv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setHarZabEnabled(false);
			}
		});
		bgDiagPredv.add(rbtDiagPredv);
		
		rbtDiagZakl = new JRadioButton("Заключительный");
		rbtDiagZakl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setHarZabEnabled(true);
			}
		});
		bgDiagPredv.add(rbtDiagZakl);
		
		GroupLayout gl_pnlDiagPredv = new GroupLayout(pnlDiagPredv);
		gl_pnlDiagPredv.setHorizontalGroup(
			gl_pnlDiagPredv.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlDiagPredv.createSequentialGroup()
					.addComponent(rbtDiagPredv)
					.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
					.addComponent(rbtDiagZakl)
					.addContainerGap())
		);
		gl_pnlDiagPredv.setVerticalGroup(
			gl_pnlDiagPredv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagPredv.createSequentialGroup()
					.addGroup(gl_pnlDiagPredv.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiagZakl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbtDiagPredv, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(2))
		);
		pnlDiagPredv.setLayout(gl_pnlDiagPredv);
		
		JPanel pnlDiagStadZab = new JPanel();
		pnlDiagStadZab.setBorder(new TitledBorder(null, "Стадия заболевания", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bgDiagStadZab = new ButtonGroup();
		
		JPanel pnlDiagHarZab = new JPanel();
		pnlDiagHarZab.setBorder(new TitledBorder(null, "Характер заболевания", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bgDiagHarZab = new ButtonGroup();
		
		rbtDiagHarOstr = new JRadioButton("Острое");
		rbtDiagHarOstr.addMouseListener(maBtnDesel);
		bgDiagHarZab.add(rbtDiagHarOstr);
		
		rbtDiagHarHron = new JRadioButton("Хроническое");
		rbtDiagHarHron.addMouseListener(maBtnDesel);
		bgDiagHarZab.add(rbtDiagHarHron);
		
		GroupLayout gl_pnlDiagHarZab = new GroupLayout(pnlDiagHarZab);
		gl_pnlDiagHarZab.setHorizontalGroup(
			gl_pnlDiagHarZab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagHarZab.createSequentialGroup()
					.addComponent(rbtDiagHarOstr, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(rbtDiagHarHron, GroupLayout.PREFERRED_SIZE, 88, Short.MAX_VALUE)
					.addGap(14))
		);
		gl_pnlDiagHarZab.setVerticalGroup(
			gl_pnlDiagHarZab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagHarZab.createSequentialGroup()
					.addGroup(gl_pnlDiagHarZab.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiagHarOstr, GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE)
						.addComponent(rbtDiagHarHron, GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE))
					.addGap(0))
		);
		pnlDiagHarZab.setLayout(gl_pnlDiagHarZab);
		
		cmbDiagVidTr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_ai0);
		
		JLabel lblDiagVidTr = new JLabel("Вид травмы");
		
		JLabel lblDiagObstReg = new JLabel("Обстоятельства регистрации");
		
		cmbDiagObstReg = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abv);;
		
		JPanel pnlDiagDisp = new JPanel();
		pnlDiagDisp.setBorder(new TitledBorder(null, "\u0414\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u043D\u044B\u0439 \u0443\u0447\u0435\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		chbDiagBoe = new JCheckBox("Участие в боевых действиях");
		
		chbDiagInv = new JCheckBox("Инвалидизирующий диагноз");
		chbDiagInv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bgInvUst.clearSelection();
	  			rbtInvUst1.setEnabled(chbDiagInv.isSelected());
	  			rbtInvUst2.setEnabled(chbDiagInv.isSelected());
			}
		});
		
		chbDiagBer = new JCheckBox("Противопоказания для беременности");
		
		 pnlInvUst = new JPanel();
		pnlInvUst.setEnabled(false);
		
		bgInvUst = new ButtonGroup();
		
		pnlInvUst.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0418\u043D\u0432\u0430\u043B\u0438\u0434\u043D\u043E\u0441\u0442\u044C \u0443\u0441\u0442\u0430\u043D\u043E\u0432\u043B\u0435\u043D\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		 rbtInvUst1 = new JRadioButton("Впервые");
		 rbtInvUst1.addMouseListener(maBtnDesel);
		 bgInvUst.add(rbtInvUst1);
		
		 rbtInvUst2 = new JRadioButton("Повторно");
		 rbtInvUst2.addMouseListener(maBtnDesel);
		 bgInvUst.add(rbtInvUst2);
		 
		GroupLayout gl_pnlInvUst = new GroupLayout(pnlInvUst);
		gl_pnlInvUst.setHorizontalGroup(
			gl_pnlInvUst.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlInvUst.createSequentialGroup()
					.addComponent(rbtInvUst1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(rbtInvUst2, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_pnlInvUst.setVerticalGroup(
			gl_pnlInvUst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlInvUst.createSequentialGroup()
					.addGroup(gl_pnlInvUst.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtInvUst1, GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE)
						.addComponent(rbtInvUst2, GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE))
					.addGap(0))
		);
		pnlInvUst.setLayout(gl_pnlInvUst);
		GroupLayout gl_pnlDiag = new GroupLayout(pnlDiag);
		gl_pnlDiag.setHorizontalGroup(
			gl_pnlDiag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlInvUst, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addComponent(lblDiagVidTr, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbDiagVidTr, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblDiagObstReg)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbDiagObstReg, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addComponent(chbDiagInv)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chbDiagBoe)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chbDiagBer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(PnlDiag, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addComponent(lblDiagOpis, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(spDiagOpis, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
						.addComponent(pnlDiagDisp, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDiag.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_pnlDiag.createSequentialGroup()
								.addComponent(pnlDiagStadZab, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(pnlDiagHarZab, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, gl_pnlDiag.createSequentialGroup()
								.addComponent(pnlDiagStat, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(pnlDiagPredv, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_pnlDiag.setVerticalGroup(
			gl_pnlDiag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiag.createSequentialGroup()
					.addContainerGap()
					.addComponent(PnlDiag, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(spDiagOpis, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(lblDiagOpis))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlDiagStat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlDiagPredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.BASELINE)
						.addComponent(pnlDiagStadZab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlDiagHarZab, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbDiagVidTr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagVidTr, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagObstReg)
						.addComponent(cmbDiagObstReg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(pnlDiagDisp, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.BASELINE)
						.addComponent(chbDiagInv)
						.addComponent(chbDiagBoe)
						.addComponent(chbDiagBer))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlInvUst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		
		tbDiagDispDatVz = new CustomDateEditor();
		tbDiagDispDatVz.setColumns(10);
		
		JLabel lblDiagDispDatVz = new JLabel("Дата взятия на ДУ");
		
		JLabel lblDiagDispDatIsh = new JLabel("Дата установления исхода");
		
		tbDiagDispDatIsh = new CustomDateEditor();
		tbDiagDispDatIsh.setColumns(10);
		
		cmbDiagDispGrup = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abc);
		
		JLabel lblDiagDispGrup = new JLabel("Группа ДУ");
		
		cmbDiagDispIsh = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abb);
		
		JLabel lblDiagDispIsh = new JLabel("Исход ДУ");
		
		btnDispHron = new JButton("...");
		btnDispHron.setToolTipText("Диспансерные мероприятия");
		btnDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			disphron.ShowDispHron();
			}
		});
		
		JLabel lblNuch = new JLabel("№ уч-ка");
		
		tfNuch = new CustomTextField();
		tfNuch.setColumns(10);
		
		JLabel lblNewDs = new JLabel("Новый Ds");
		
		tfNewDs = new CustomTextField();
		tfNewDs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = ConnectionManager.instance.showMkbTreeForm("Диагнозы", tfNewDs.getText());
					if (res != null) {
                        tfNewDs.setText(res.pcod);
                        diag_named=res.name;
                        tfDataIzmNewDs.setDate(System.currentTimeMillis());
                 }
				}
			}
		});
		tfNewDs.setColumns(10);
		
		JLabel lblDataIzmNewDs = new JLabel("Дата изм-я Ds");
		
		tfDataIzmNewDs = new CustomDateEditor();
		tfDataIzmNewDs.setColumns(10);
		GroupLayout gl_pnlDiagDisp = new GroupLayout(pnlDiagDisp);
		gl_pnlDiagDisp.setHorizontalGroup(
			gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagDisp.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDiagDisp.createSequentialGroup()
							.addComponent(lblDiagDispDatVz)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbDiagDispDatVz, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addGap(20)
							.addComponent(lblDiagDispGrup)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbDiagDispGrup, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDiagDisp.createSequentialGroup()
							.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addComponent(lblDiagDispIsh)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbDiagDispIsh, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addComponent(lblNuch)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tfNuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblNewDs)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblDataIzmNewDs)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDataIzmNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addGap(10)
									.addComponent(btnDispHron, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
								.addComponent(tbDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))))
					.addGap(2))
		);
		gl_pnlDiagDisp.setVerticalGroup(
			gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagDisp.createSequentialGroup()
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiagDispDatVz)
						.addComponent(tbDiagDispDatVz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagDispGrup)
						.addComponent(cmbDiagDispGrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiagDispIsh)
						.addComponent(cmbDiagDispIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagDispDatIsh)
						.addComponent(tbDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNuch)
						.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfNuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDispHron, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewDs)
							.addComponent(tfNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDataIzmNewDs)
							.addComponent(tfDataIzmNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(11))
		);
		pnlDiagDisp.setLayout(gl_pnlDiagDisp);
		
		rbtDiagStadRan = new JRadioButton("Ранняя");
		rbtDiagStadRan.addMouseListener(maBtnDesel);
		bgDiagStadZab.add(rbtDiagStadRan);
		
		rbtDiagStadPoz = new JRadioButton("Поздняя");
		rbtDiagStadPoz.addMouseListener(maBtnDesel);
		bgDiagStadZab.add(rbtDiagStadPoz);
		
		GroupLayout gl_pnlDiagStadZab = new GroupLayout(pnlDiagStadZab);
		gl_pnlDiagStadZab.setHorizontalGroup(
			gl_pnlDiagStadZab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagStadZab.createSequentialGroup()
					.addComponent(rbtDiagStadRan, GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
					.addGap(87)
					.addComponent(rbtDiagStadPoz, GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
					.addGap(59))
		);
		gl_pnlDiagStadZab.setVerticalGroup(
			gl_pnlDiagStadZab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagStadZab.createSequentialGroup()
					.addGroup(gl_pnlDiagStadZab.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiagStadRan, GroupLayout.PREFERRED_SIZE, 16, Short.MAX_VALUE)
						.addComponent(rbtDiagStadPoz, GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE))
					.addGap(0))
		);
		pnlDiagStadZab.setLayout(gl_pnlDiagStadZab);
		
		rbtDiagOsn = new JRadioButton("Основной");
		rbtDiagOsn.setSelected(true);
		bgDiagStat.add(rbtDiagOsn);
		
		rbtDiagSop = new JRadioButton("Сопутствующий");
		bgDiagStat.add(rbtDiagSop);
		
		rbtDiagOsl = new JRadioButton("Осложнение основного");
		bgDiagStat.add(rbtDiagOsl);
		
		GroupLayout gl_pnlDiagStat = new GroupLayout(pnlDiagStat);
		gl_pnlDiagStat.setHorizontalGroup(
			gl_pnlDiagStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagStat.createSequentialGroup()
					.addGap(2)
					.addComponent(rbtDiagOsn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtDiagSop)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDiagOsl)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlDiagStat.setVerticalGroup(
			gl_pnlDiagStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagStat.createSequentialGroup()
					.addGroup(gl_pnlDiagStat.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiagSop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbtDiagOsl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbtDiagOsn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(2))
		);
		pnlDiagStat.setLayout(gl_pnlDiagStat);
		
		tbDiagOpis = new JTextArea();
		tbDiagOpis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spDiagOpis.setViewportView(tbDiagOpis);
		pnlDiag.setLayout(gl_pnlDiag);
		
		JPanel pnlLech = new JPanel();
		tabbedPane.addTab("<html><br>Лечение</html>", null, pnlLech, null);
		tabbedPane.setTabComponentAt(6, new JLabel("<html><br>Лечение</html>"));
		
		JScrollPane spLech = new JScrollPane();
		GroupLayout gl_pnlLech = new GroupLayout(pnlLech);
		gl_pnlLech.setHorizontalGroup(
			gl_pnlLech.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLech.createSequentialGroup()
					.addContainerGap()
					.addComponent(spLech, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlLech.setVerticalGroup(
			gl_pnlLech.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLech.createSequentialGroup()
					.addContainerGap()
					.addComponent(spLech, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbLech = new JTextArea();
		tbLech.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbLech.setLineWrap(true);
		tbLech.setWrapStyleWord(true);
		spLech.setViewportView(tbLech);
		pnlLech.setLayout(gl_pnlLech);
		
		JPanel pnlLabIssl = new JPanel();
		tabbedPane.addTab("<html>Лабораторно-<br>диагностические <br>исследования</html>", null, pnlLabIssl, null);
		tabbedPane.setTabComponentAt(7, new JLabel("<html>Лабораторно-<br>диагностические <br>исследования</html>"));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_pnlLabIssl = new GroupLayout(pnlLabIssl);
		gl_pnlLabIssl.setHorizontalGroup(
			gl_pnlLabIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLabIssl.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_1)
					.addContainerGap())
		);
		gl_pnlLabIssl.setVerticalGroup(
			gl_pnlLabIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlLabIssl.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 445, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JPanel pnlIssl = new JPanel();
		tabbedPane_1.addTab("Направление на исследование", null, pnlIssl, null);
		
		cmbNaprMesto = new ThriftIntegerClassifierCombobox<>(true);
		cmbNaprMesto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cmbNaprMesto.getSelectedItem() != null)
						cmbOrgan.setData(MainForm.tcl.get_n_nz1(cmbNaprMesto.getSelectedItem().pcod));
				} catch (KmiacServerException e1) {
					JOptionPane.showMessageDialog(Vvod.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		JLabel lblNaprMesto = new JLabel("Лаборатория");
		
		cmbOrgan = new ThriftStringClassifierCombobox<>(true);
		cmbOrgan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (cmbOrgan.getSelectedItem()!= null)
						tblNaprPokazMet.setData(MainForm.tcl.getPokazMet(cmbOrgan.getSelectedItem().pcod, cmbNaprMesto.getSelectedItem().pcod));
				} catch (KmiacServerException e) {
					JOptionPane.showMessageDialog(Vvod.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		JLabel lblNaprVidIssl = new JLabel("Органы и системы");
		
		JLabel lblNaprPokazMet = new JLabel("Исследования");
		
		JScrollPane spNaprPokazMet = new JScrollPane();
		
		JButton btnNaprPrint = new JButton("Печать");
		btnNaprPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if ((cmbOrgan.getSelectedItem() != null) ) {
						P_isl_ld pisl = new P_isl_ld();
						Prez_d prezd = new Prez_d();
						Prez_l prezl = new Prez_l();
						pisl.setNpasp(Vvod.zapVr.getNpasp());
						pisl.setPcisl(cmbOrgan.getSelectedPcod());
						pisl.setNapravl(2);
						pisl.setNaprotd(MainForm.authInfo.getCpodr());
						pisl.setDatan(System.currentTimeMillis());
						pisl.setVrach(MainForm.authInfo.getPcod());
						pisl.setDataz(System.currentTimeMillis());
						pisl.setPvizit_id(tblPos.getSelectedItem().getId_obr());
						pisl.setPrichina(pvizit.getCobr());
						pisl.setKodotd(cmbNaprMesto.getSelectedPcod());
						pisl.setNisl(MainForm.tcl.AddPisl(pisl));
						List<String> selItems = new ArrayList<>();
						for (IntegerClassifier el : listVidIssl)
							if (el.pcod == cmbNaprMesto.getSelectedPcod()) {
								for (PokazMet pokazMet : tblNaprPokazMet.getData()) {
									if (pokazMet.vybor) {
										if (el.name.equals("Л")) {
											prezl.setNpasp(pisl.getNpasp());
											prezl.setNisl(pisl.getNisl());
											prezl.setCpok(pokazMet.pcod);
											prezl.setId(MainForm.tcl.AddPrezl(prezl));	
										}
										else{
											prezd.setNpasp(pisl.getNpasp());
											prezd.setNisl(pisl.getNisl());
											prezd.setKodisl(pokazMet.pcod);
											prezd.setId(MainForm.tcl.AddPrezd(prezd));
										}
										selItems.add(pokazMet.getPcod());
									}
								}
						}
						if (selItems.size() != 0) {
							IsslMet isslmet = new IsslMet();
							isslmet.setPvizitId(tblPos.getSelectedItem().getId_obr());
							isslmet.setUserId(MainForm.authInfo.getUser_id());
							isslmet.setNpasp(Vvod.zapVr.getNpasp());
							isslmet.setPokaz(selItems);
							if (cmbNaprMesto.getSelectedItem() != null) isslmet.setMesto(cmbNaprMesto.getSelectedItem().getName());
							isslmet.setKab(getTextOrNull(tbNaprKab.getText()));
							isslmet.setClpu(cmbLpu.getSelectedPcod());
							isslmet.setClpu_name(MainForm.authInfo.getClpu_name());
							isslmet.setCpodr_name(MainForm.authInfo.getCpodr_name());
							String servPath = MainForm.tcl.printIsslMetod(isslmet);
							String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
       						MainForm.conMan.openFileInEditor(cliPath, false);
						}
					}
				}
				
					catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		tbNaprKab = new CustomTextField();
		tbNaprKab.setColumns(10);
		
		JLabel lblNaprKab = new JLabel("Кабинет");
		
		JLabel lblLpu = new JLabel("ЛПУ");
		
		 cmbLpu =  new ThriftIntegerClassifierCombobox<>(true);
		 cmbLpu.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				try {
					if (cmbLpu.getSelectedItem() != null){
						cmbNaprMesto.setData(MainForm.tcl.get_n_lds(cmbLpu.getSelectedPcod()));
							cmbOrgan.setData(MainForm.tcl.get_n_nz1(0));
							tblNaprPokazMet.setData(MainForm.tcl.getPokazMet("", 0));
					}
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
		 	}
		 });
		GroupLayout gl_pnlIssl = new GroupLayout(pnlIssl);
		gl_pnlIssl.setHorizontalGroup(
			gl_pnlIssl.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addComponent(lblLpu)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbLpu, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addComponent(lblNaprMesto, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbNaprMesto, 0, 458, Short.MAX_VALUE))
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addComponent(lblNaprVidIssl, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbOrgan, 0, 419, Short.MAX_VALUE))
						.addComponent(spNaprPokazMet, GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
						.addComponent(btnNaprPrint, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addComponent(lblNaprKab, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbNaprKab, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNaprPokazMet, GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlIssl.setVerticalGroup(
			gl_pnlIssl.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLpu)
						.addComponent(cmbLpu, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaprMesto)
						.addComponent(cmbNaprMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaprVidIssl)
						.addComponent(cmbOrgan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNaprPokazMet)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spNaprPokazMet, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaprKab)
						.addComponent(tbNaprKab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNaprPrint)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		
		tblNaprPokazMet = new CustomTable<>(false, true, PokazMet.class, 0, "Код", 1, "Наименование", 2, "Выбор");
		tblNaprPokazMet.setEditableFields(true, 2);
		tblNaprPokazMet.setFillsViewportHeight(true);
		spNaprPokazMet.setViewportView(tblNaprPokazMet);
		pnlIssl.setLayout(gl_pnlIssl);
		
		JPanel pnlRezIssl = new JPanel();
		tabbedPane_1.addTab("Результаты исследований", null, pnlRezIssl, null);
		
		JSplitPane splRezIssl = new JSplitPane();
		 Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
		GroupLayout gl_pnlRezIssl = new GroupLayout(pnlRezIssl);
		gl_pnlRezIssl.setHorizontalGroup(
			gl_pnlRezIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRezIssl.createSequentialGroup()
					.addComponent(splRezIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlRezIssl.setVerticalGroup(
			gl_pnlRezIssl.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlRezIssl.createSequentialGroup()
					.addContainerGap()
					.addComponent(splRezIssl, GroupLayout.PREFERRED_SIZE, 400, Short.MAX_VALUE))
		);
		
		JPanel pnlRezIsslL = new JPanel();
		splRezIssl.setLeftComponent(pnlRezIsslL);
		
		JScrollPane spRezIssl = new JScrollPane();
		GroupLayout gl_pnlRezIsslL = new GroupLayout(pnlRezIsslL);
		gl_pnlRezIsslL.setHorizontalGroup(
			gl_pnlRezIsslL.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlRezIsslL.createSequentialGroup()
					.addComponent(spRezIssl, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlRezIsslL.setVerticalGroup(
			gl_pnlRezIsslL.createParallelGroup(Alignment.LEADING)
				.addComponent(spRezIssl, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
		);
		
		 treeRezIssl = new JTree();
		 treeRezIssl.addTreeSelectionListener(new TreeSelectionListener() {
		 	public void valueChanged(TreeSelectionEvent e) {
		 		if (e.getNewLeadSelectionPath() == null) {
		 			epTxtRezIssl.setText("");
		 			return;
		 		}
		 		sb = new StringBuilder();	
		 		Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();

		 		if (lastPath instanceof IsslPokazNode) {
		 			IsslPokazNode isslPokazNode = (IsslPokazNode) lastPath;
	 				IsslInfo iinfo = isslPokazNode.isslpokaz;
						addLineToDetailInfo("id: ", iinfo.isSetId(), iinfo.getId());
						addLineToDetailInfo("Наименование",iinfo.isSetPokaz_name(), iinfo.getPokaz_name());
						addLineToDetailInfo("Результат",iinfo.isSetRez(), iinfo.getRez());
						epTxtRezIssl.setText(sb.toString());
					
		 		}
		 	}
		 });
		 treeRezIssl.addTreeExpansionListener(new TreeExpansionListener() {
		 	public void treeCollapsed(TreeExpansionEvent event) {
		 	}
		 	public void treeExpanded(TreeExpansionEvent event) {
		 		Object lastPath = event.getPath().getLastPathComponent();
		 		if (lastPath instanceof IsslInfoTreeNode) {
		 			try {
						IsslInfoTreeNode isslnode = (IsslInfoTreeNode) lastPath;
						isslnode.removeAllChildren();
						for (IsslInfo isslChild : MainForm.tcl.getIsslInfoPokaz(isslnode.issl.getNisl())) {
							isslnode.add(new IsslPokazNode(isslChild));
						}
						((DefaultTreeModel) treeRezIssl.getModel()).reload(isslnode);
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
		 		}

		 		}
		 });
		spRezIssl.setViewportView(treeRezIssl);
		treeRezIssl.setShowsRootHandles(true);
		treeRezIssl.setRootVisible(false);
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) treeRezIssl.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		pnlRezIsslL.setLayout(gl_pnlRezIsslL);
		
		JPanel pnlRezIsslR = new JPanel();
		splRezIssl.setRightComponent(pnlRezIsslR);
		
		JScrollPane spTxtRezIssl = new JScrollPane();
		GroupLayout gl_pnlRezIsslR = new GroupLayout(pnlRezIsslR);
		gl_pnlRezIsslR.setHorizontalGroup(
			gl_pnlRezIsslR.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRezIsslR.createSequentialGroup()
					.addGap(4)
					.addComponent(spTxtRezIssl, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlRezIsslR.setVerticalGroup(
			gl_pnlRezIsslR.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRezIsslR.createSequentialGroup()
					.addGap(4)
					.addComponent(spTxtRezIssl, GroupLayout.PREFERRED_SIZE, 401, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		 epTxtRezIssl = new JEditorPane();
		spTxtRezIssl.setViewportView(epTxtRezIssl);
		pnlRezIsslR.setLayout(gl_pnlRezIsslR);
		pnlRezIssl.setLayout(gl_pnlRezIssl);
		pnlLabIssl.setLayout(gl_pnlLabIssl);
		
		JPanel pnlNapr = new JPanel();
		tabbedPane.addTab("<html><br>Направления</html>", null, pnlNapr, null);
		tabbedPane.setTabComponentAt(8, new JLabel("<html><br>Направления</html>"));

		
		JLabel lblKonsVidNapr = new JLabel("на");
		
		  cmbConsVidNapr = new JComboBox<String>();
		cmbConsVidNapr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbConsVidNapr.getSelectedIndex() != 0){cmbVidStacionar.setVisible(false);lblVidStacionar.setVisible(false);lblKonsMesto.setVisible(true); cmbKonsMesto.setVisible(true);}
				else {try {
					cmbVidStacionar.setData(MainForm.tcl.get_n_tip());
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}cmbVidStacionar.setVisible(true);lblVidStacionar.setVisible(true);
				}
			}
		});
		cmbConsVidNapr.setModel(new DefaultComboBoxModel(new String[] {"госпитализацию", "консультацию"}));
		
		 lblVidStacionar = new JLabel("Вид стационара");
		
		cmbVidStacionar = new ThriftIntegerClassifierCombobox<>(true);
		cmbVidStacionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbVidStacionar.getSelectedItem() != null){
					if ((cmbVidStacionar.getSelectedPcod()==1)||(cmbVidStacionar.getSelectedPcod()==2)){lblKonsMesto.setVisible(true); cmbKonsMesto.setVisible(true); 
					try {
					cmbKonsMesto.setData(MainForm.tcl.get_m00());
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
				}
				if ((cmbVidStacionar.getSelectedPcod()==3)||(cmbVidStacionar.getSelectedPcod()==4)){lblKonsMesto.setVisible(false); cmbKonsMesto.setVisible(false); }
				}
			}
		});
		
		lblKonsMesto = new JLabel("Куда");
		
		cmbKonsMesto = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_n00);
		
		JLabel lblKonsObosnov = new JLabel("Обоснование для направления");
		
		tbKonsObosnov = new JTextArea();
		tbKonsObosnov.setBorder(UIManager.getBorder("ScrollPane.border"));
		tbKonsObosnov.setWrapStyleWord(true);
		tbKonsObosnov.setLineWrap(true);
		tbKonsObosnov.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnKonsPrint = new JButton("Печать");
		btnKonsPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					PNapr pnapr = new PNapr();
					if (cmbConsVidNapr.getSelectedIndex() != 0){
						pnapr.setIdpvizit(tblPos.getSelectedItem().getId_obr());
						pnapr.setVid_doc(3);
						pnapr.setText(tbKonsObosnov.getText());					
						pnapr.setId(MainForm.tcl.AddPnapr(pnapr));
						NaprKons naprkons = new NaprKons();
						naprkons.setUserId(MainForm.authInfo.getUser_id());
						naprkons.setNpasp(Vvod.zapVr.getNpasp());
						naprkons.setObosnov(tbKonsObosnov.getText());
						if (cmbKonsMesto.getSelectedItem()!= null) naprkons.setCpol(cmbKonsMesto.getSelectedItem().getName());
						naprkons.setNazv(cmbKonsMesto.getSelectedItem().toString());
						naprkons.setCdol(MainForm.authInfo.getCdol());
						naprkons.setPvizitId(tblPos.getSelectedItem().getId_obr());
						naprkons.setCpodr_name(MainForm.authInfo.getCpodr_name());
						naprkons.setClpu_name(MainForm.authInfo.getClpu_name());
						String servPath = MainForm.tcl.printNaprKons(naprkons);
						String cliPath = File.createTempFile("napk", ".htm").getAbsolutePath();
						MainForm.conMan.transferFileFromServer(servPath, cliPath);
   						MainForm.conMan.openFileInEditor(cliPath, false);
					}
					else {
						Napr napr = new Napr();
						pnapr.setIdpvizit(tblPos.getSelectedItem().getId_obr());
						pnapr.setVid_doc(3);
						pnapr.setText(tbKonsObosnov.getText());
						if ((cmbVidStacionar.getSelectedPcod()==3)||(cmbVidStacionar.getSelectedPcod()==4)){
						List<Integer> sp;
						Cgosp gosp = new Cgosp();
						gosp.setNpasp(zapVr.getNpasp());
						gosp.setNaprav("К");
						gosp.setVid_st(cmbVidStacionar.getSelectedPcod());
						for (PdiagAmb pd : tblDiag.getData()) {
							if (pd.diag_stat==1) {
								gosp.setDiag_n(pd.getDiag());
								gosp.setNamed_n(pd.getNamed());
								gosp.setDiag_p(pd.getDiag());
								gosp.setNamed_p(pd.getNamed());}
						}
						gosp.setDataz(System.currentTimeMillis());
						gosp.setNist(444);
						gosp.setPl_extr(2);
						gosp.setDatap(System.currentTimeMillis());
						gosp.setVremp(System.currentTimeMillis());
						gosp.setCotd(MainForm.authInfo.getCpodr());
						gosp.setCotd_p(MainForm.authInfo.getCpodr());
						gosp.setDataosm(System.currentTimeMillis());
						gosp.setVremosm(System.currentTimeMillis());
						sp = MainForm.tcl.AddCGosp(gosp);
						gosp.setId(sp.get(0));
						gosp.setNgosp(sp.get(1));
						Cotd cotd = new Cotd();
						cotd.setId_gosp(gosp.getId());
						cotd.setCotd(gosp.getCotd());
						cotd.setDataz(System.currentTimeMillis());
						cotd.setNist(gosp.getNist());
						cotd.setId(MainForm.tcl.AddCotd(cotd));
						}
						if ((cmbVidStacionar.getSelectedPcod()==1)||(cmbVidStacionar.getSelectedPcod()==2)){
						napr.setUserId(MainForm.authInfo.getUser_id());
						napr.setNpasp(Vvod.zapVr.getNpasp());
						napr.setObosnov(tbKonsObosnov.getText());
						if (cmbKonsMesto.getSelectedItem()!= null) napr.setClpu(cmbKonsMesto.getSelectedItem().getName());
						napr.setCpodr_name(MainForm.authInfo.getCpodr_name());
						napr.setClpu_name(MainForm.authInfo.getClpu_name());
						napr.setPvizitId(tblPos.getSelectedItem().getId_obr());
						String servPath = MainForm.tcl.printNapr(napr);
						String cliPath = File.createTempFile("napr", ".htm").getAbsolutePath();
						MainForm.conMan.transferFileFromServer(servPath, cliPath);	
   						MainForm.conMan.openFileInEditor(cliPath, false);}
					}
				}
				catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_pnlNapr = new GroupLayout(pnlNapr);
		gl_pnlNapr.setHorizontalGroup(
			gl_pnlNapr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNapr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addComponent(lblVidStacionar, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(cmbVidStacionar, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addComponent(lblKonsMesto, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(cmbKonsMesto, GroupLayout.PREFERRED_SIZE, 496, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblKonsObosnov, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addGap(1)
							.addComponent(tbKonsObosnov, GroupLayout.PREFERRED_SIZE, 556, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnKonsPrint, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addComponent(lblKonsVidNapr, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(cmbConsVidNapr, GroupLayout.PREFERRED_SIZE, 522, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_pnlNapr.setVerticalGroup(
			gl_pnlNapr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNapr.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblKonsVidNapr))
						.addComponent(cmbConsVidNapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblVidStacionar))
						.addComponent(cmbVidStacionar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblKonsMesto))
						.addComponent(cmbKonsMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(lblKonsObosnov)
					.addGap(7)
					.addComponent(tbKonsObosnov, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(btnKonsPrint)
					.addContainerGap(71, Short.MAX_VALUE))
		);
		pnlNapr.setLayout(gl_pnlNapr);
		
		JPanel pnlOcen = new JPanel();
		tabbedPane.addTab("<html><br>Оценка данных анамнеза</html>", null, pnlOcen, null);
		tabbedPane.setTabComponentAt(9, new JLabel("<html><br>Оценка данных анамнеза</html></html>"));
		
		JScrollPane spOcen = new JScrollPane();
		GroupLayout gl_pnlOcen = new GroupLayout(pnlOcen);
		gl_pnlOcen.setHorizontalGroup(
			gl_pnlOcen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOcen.createSequentialGroup()
					.addContainerGap()
					.addComponent(spOcen, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlOcen.setVerticalGroup(
			gl_pnlOcen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOcen.createSequentialGroup()
					.addContainerGap()
					.addComponent(spOcen, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbOcen = new JTextArea();
		tbOcen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbOcen.setLineWrap(true);
		tbOcen.setWrapStyleWord(true);
		spOcen.setViewportView(tbOcen);
		pnlOcen.setLayout(gl_pnlOcen);
		
		JPanel pnlRecom = new JPanel();
		tabbedPane.addTab("<html><br>Рекомендации</html>", null, pnlRecom, null);
		tabbedPane.setTabComponentAt(10, new JLabel("<html><br>Рекомендации</html>"));
		
		JScrollPane spRecom = new JScrollPane();
		GroupLayout gl_pnlRecom = new GroupLayout(pnlRecom);
		gl_pnlRecom.setHorizontalGroup(
			gl_pnlRecom.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRecom.createSequentialGroup()
					.addContainerGap()
					.addComponent(spRecom, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlRecom.setVerticalGroup(
			gl_pnlRecom.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRecom.createSequentialGroup()
					.addContainerGap()
					.addComponent(spRecom, GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		tbRecom = new JTextArea();
		spRecom.setViewportView(tbRecom);
		tbRecom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbRecom.setLineWrap(true);
		tbRecom.setWrapStyleWord(true);
		pnlRecom.setLayout(gl_pnlRecom);
		
		JPanel pnlZakl = new JPanel();
		tabbedPane.addTab("<html><br>Заключение</html>", null, pnlZakl, null);
		tabbedPane.setTabComponentAt(11, new JLabel("<html><br>Заключение</html>"));
		
		JLabel lblZakl = new JLabel("Заключение специалиста");
		
		JScrollPane spZakl = new JScrollPane();
		
		cmbZaklIsh = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_ap0);
		
		JLabel lblZaklRek = new JLabel("Медицинские рекомендации");
		
		JScrollPane spZaklRek = new JScrollPane();
		
		JLabel lblZaklIsh = new JLabel("Исход");
		GroupLayout gl_pnlZakl = new GroupLayout(pnlZakl);
		gl_pnlZakl.setHorizontalGroup(
			gl_pnlZakl.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlZakl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlZakl.createParallelGroup(Alignment.TRAILING)
						.addComponent(spZaklRek, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
						.addComponent(spZakl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
						.addComponent(lblZakl, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
						.addComponent(lblZaklRek, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_pnlZakl.createSequentialGroup()
							.addComponent(lblZaklIsh, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbZaklIsh, 0, 259, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pnlZakl.setVerticalGroup(
			gl_pnlZakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlZakl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlZakl.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbZaklIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblZaklIsh))
					.addGap(18)
					.addComponent(lblZakl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spZakl, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblZaklRek, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spZaklRek, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbZaklRek = new JTextArea();
		tbZaklRek.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbZaklRek.setLineWrap(true);
		tbZaklRek.setWrapStyleWord(true);
		spZaklRek.setViewportView(tbZaklRek);
		
		tbZakl = new JTextArea();
		tbZakl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbZakl.setWrapStyleWord(true);
		tbZakl.setLineWrap(true);
		spZakl.setViewportView(tbZakl);
		pnlZakl.setLayout(gl_pnlZakl);
		/*tabbedPane.addTab("<html><br>Оценка данных анамнеза</html>", null, pnlOcen, null);
		tabbedPane.setTabComponentAt(8, new JLabel("<html><br>Оценка данных анамнеза<br><br></html>"));*/
		
		panel_2.setLayout(gl_panel_2);
		
		cmbVidOpl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_opl);
		
		JLabel lblVidOpl = new JLabel("Вид оплаты");
		
		cmbCelObr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p0c);
		
		JLabel lblCelObr = new JLabel("Цель посещения");
		
		JLabel lblRez = new JLabel("Результат");
		
		cmbRez = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_aq0);
		
		JLabel lblMobs = new JLabel("Место обслуж.");
		
		cmbMobs = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abs);
		GroupLayout gl_pnlTalon = new GroupLayout(pnlTalon);
		gl_pnlTalon.setHorizontalGroup(
			gl_pnlTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTalon.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblCelObr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblVidOpl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.LEADING)
						.addComponent(cmbCelObr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cmbVidOpl, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblRez, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblMobs, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.TRAILING)
						.addComponent(cmbRez, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cmbMobs, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlTalon.setVerticalGroup(
			gl_pnlTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTalon.createSequentialGroup()
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVidOpl)
						.addComponent(cmbVidOpl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbRez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRez))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCelObr)
						.addComponent(cmbMobs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbCelObr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMobs))
					.addGap(24))
		);
		pnlTalon.setLayout(gl_pnlTalon);
		
		JScrollPane scrollPane = new JScrollPane();
		
		 btnPosAdd = new JButton("");
		 btnPosAdd.setToolTipText("Добавление новой записи");
		btnPosAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (PvizitAmb pviz : tblPos.getData())
					if (pviz.getDatap() == getDateMills(System.currentTimeMillis())) {
						JOptionPane.showMessageDialog(Vvod.this, "Невозможно записать два посещения за одну дату");
						return;
					}
				
				pvizit = new Pvizit();
				if (zapVr.getId_pvizit()!=0){
				pvizit.setId(zapVr.getId_pvizit());
				pvizit.setNpasp(zapVr.getNpasp());
				pvizit.setCpol(MainForm.authInfo.getCpodr());
				pvizit.setDatao(System.currentTimeMillis());
				pvizit.setCod_sp(MainForm.authInfo.getPcod());
				pvizit.setCdol(MainForm.authInfo.getCdol());
				pvizit.setCuser(MainForm.authInfo.getUser_id());
				pvizit.setDataz(System.currentTimeMillis());
				
				pvizitAmb = new PvizitAmb();
				pvizitAmb.setId_obr(pvizit.getId());
				pvizitAmb.setNpasp(zapVr.getNpasp());
				pvizitAmb.setDatap(System.currentTimeMillis());
				pvizitAmb.setDataz(System.currentTimeMillis());
				pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
				pvizitAmb.setCdol(MainForm.authInfo.getCdol());
				pvizitAmb.setCpol(MainForm.authInfo.getCpodr());
				pvizitAmb.setKod_ter(MainForm.authInfo.getKdate());
				
				try {
					Vvod.pvizit = MainForm.tcl.getPvizit(pvizit.getId());
					pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
					tblPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
				} catch (KmiacServerException e2) {
					e2.printStackTrace();
				} catch (PvizitNotFoundException e2) {
					try {
						MainForm.tcl.AddPvizit(pvizit);
						zapVr.setId_pvizit(pvizit.id);
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						tblPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
						e1.printStackTrace();
					}
				} catch (TException e2) {
					MainForm.conMan.reconnect(e2);
					e2.printStackTrace();
				}
				}
				else {
					try {
						pvizit.setNpasp(zapVr.getNpasp());
						pvizit.setCpol(MainForm.authInfo.getCpodr());
						pvizit.setDatao(System.currentTimeMillis());
						pvizit.setCod_sp(MainForm.authInfo.getPcod());
						pvizit.setCdol(MainForm.authInfo.getCdol());
						pvizit.setCuser(MainForm.authInfo.getUser_id());
						pvizit.setDataz(System.currentTimeMillis());
						pvizit.setId(MainForm.tcl.AddPvizitId(pvizit));
						zapVr.setId_pvizit(pvizit.id);
						pvizitAmb = new PvizitAmb();
						pvizitAmb.setId_obr(pvizit.getId());
						pvizitAmb.setNpasp(zapVr.getNpasp());
						pvizitAmb.setDatap(System.currentTimeMillis());
						pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
						pvizitAmb.setCdol(MainForm.authInfo.getCdol());
						pvizitAmb.setCpol(MainForm.authInfo.getCpodr());
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						pvizitAmb.setKod_ter(MainForm.authInfo.getKdate());
						tblPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}	
				}
				pvizitAmbCopy = new PvizitAmb(pvizitAmb);
				priemCopy = new Priem(priem);
				anamZabCopy = new AnamZab(anamZab);
				pvizitCopy = new Pvizit(pvizit);
			}
		});
		btnPosAdd.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		
		final JButton btnPosDel = new JButton("");
		btnPosDel.setToolTipText("Удаление записи");
		btnPosDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (tblPos.getSelectedItem() !=  null) {
						if (JOptionPane.showConfirmDialog(Vvod.this, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							MainForm.tcl.DeleteRdDin(tblPos.getSelectedItem().getId());
							MainForm.tcl.DeletePriem(tblPos.getSelectedItem().getId());
							MainForm.tcl.DeletePvizitAmb(tblPos.getSelectedItem().getId());
							if (tblPos.getSelectedRow() == 0)
								MainForm.tcl.DeleteEtalon(zapVr.getId_pvizit());
							
							int prevIdx = tblPos.getSelectedRow();
							tblPos.setData(MainForm.tcl.getPvizitAmb(Vvod.zapVr.getId_pvizit()));
							if (tblPos.getRowCount() > 0)
								if (prevIdx < tblPos.getRowCount() - 1)
									tblPos.setRowSelectionInterval(prevIdx, prevIdx);
								else
									tblPos.setRowSelectionInterval(tblPos.getRowCount() - 1, tblPos.getRowCount() - 1);
							if (tblPos.getSelectedItem() == null) {
								MainForm.tcl.DeleteAnamZab(zapVr.getId_pvizit());
								MainForm.tcl.DeletePvizit(zapVr.getId_pvizit());
								MainForm.tcl.DeletePdiagAmbVizit(zapVr.getId_pvizit());
								Vvod.this.dispatchEvent(new WindowEvent(Vvod.this, WindowEvent.WINDOW_CLOSING));
							}
						}
					}
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		btnPosDel.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		
		btnPosSave = new JButton("");
		btnPosSave.setToolTipText("Сохранение изменений");
		btnPosSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblPos.getSelectedItem() != null)
					try {
						if (!prepareSavePos())
							return;
						
						MainForm.tcl.setPriem(priem);
						MainForm.tcl.setAnamZab(anamZab);
						MainForm.tcl.UpdatePvizit(pvizit);
						MainForm.tcl.UpdatePvizitAmb(pvizitAmb);
						btnRecPriem.setEnabled(!pvizit.isSetIshod());
						
						pvizitAmbCopy = new PvizitAmb(pvizitAmb);
						priemCopy = new Priem(priem);
						anamZabCopy = new AnamZab(anamZab);
						pvizitCopy = new Pvizit(pvizit);
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					}
			}
		});
		btnPosSave.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPosAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPosDel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPosSave, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnPosAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPosDel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPosSave, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
					.addGap(0))
		);
		
		tblPos = new CustomTable<>(true, false, PvizitAmb.class, 3, "Дата", 19, "ФИО врача", 5, "Должность");
		tblPos.setDateField(0);
		tblPos.setEditableFields(false, 1, 2);
		tblPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (!arg0.getValueIsAdjusting()){
				pvizitAmb = new PvizitAmb();
				priem = new Priem();
				anamZab = new AnamZab();
				pvizit = new Pvizit();
				if (tblPos.getSelectedItem()!= null) {
					pvizitAmb = tblPos.getSelectedItem();
					try {
						
						priem = MainForm.tcl.getPriem(tblPos.getSelectedItem().npasp, tblPos.getSelectedItem().id);
						pvizit = MainForm.tcl.getPvizit(zapVr.getId_pvizit());
						anamZab = MainForm.tcl.getAnamZab(zapVr.getId_pvizit(), zapVr.getNpasp());
						btnRecPriem.setEnabled(!pvizit.isSetIshod());
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (PriemNotFoundException e) {
						e.printStackTrace();
					} catch (PvizitNotFoundException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
				}
				
				if (pvizitAmb.isSetCpos())
					cmbCelObr.setSelectedPcod(pvizitAmb.getCpos());
				else
					cmbCelObr.setSelectedItem(null);
				if (pvizitAmb.isSetRezult())
					cmbRez.setSelectedPcod(pvizitAmb.getRezult());
				else
					cmbRez.setSelectedItem(null);
				if (pvizit.isSetIshod())
					cmbZaklIsh.setSelectedPcod(pvizit.getIshod());
				else
					cmbZaklIsh.setSelectedItem(null);
				if (pvizitAmb.isSetMobs())
					cmbMobs.setSelectedPcod(pvizitAmb.getMobs());
				else
					cmbMobs.setSelectedItem(null);
				if (pvizitAmb.isSetOpl())
					cmbVidOpl.setSelectedPcod(pvizitAmb.getOpl());
				else
					cmbVidOpl.setSelectedItem(null);
				pvizitAmb.setPl_extr(1);
				tbJal.setText(priem.getT_jalob());	
				tbStatAd.setText(priem.getT_ad());	
				tbStatTemp.setText(priem.getT_temp());	
				tbStatChss.setText(priem.getT_chss());	
				tbStatRost.setText(priem.getT_rost());	
				tbStatVes.setText(priem.getT_ves());	
				tbLoc.setText(priem.getT_st_localis());	
				tbFiz.setText(priem.getT_fiz_obsl());
				tbOcen.setText(priem.getT_ocenka());
				tbStat.setText(priem.getT_status_praesense());
				tbAnam.setText(anamZab.getT_ist_zab());
				tbZakl.setText(pvizit.getZakl());
				tbZaklRek.setText(pvizit.getRecomend());
				tbLech.setText(pvizit.getLech());
				
				pvizitAmbCopy = new PvizitAmb(pvizitAmb);
				priemCopy = new Priem(priem);
				anamZabCopy = new AnamZab(anamZab);
				pvizitCopy = new Pvizit(pvizit);
				
			}
		}
	});
		tblPos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblPos);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

	}
	
	private DefaultMutableTreeNode createNodes(){
		root = new DefaultMutableTreeNode("Корень");
		issinfo = new DefaultMutableTreeNode("Даты назначенных исследований");
		root.add(issinfo);
		
		try {
			{	for (P_isl_ld issl : MainForm.tcl.getIsslInfoDate(Vvod.zapVr.id_pvizit))
					issinfo.add(new IsslInfoTreeNode(issl));
			}
			


		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		} 

		return root;
	}
	
	class IsslInfoTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 3986622548094236905L;
		private P_isl_ld issl;
		
		public IsslInfoTreeNode(P_isl_ld issl) {
			this.issl = issl;
			this.add(new IsslPokazNode(new IsslInfo()));
		}
		
		@Override
		public String toString() {
			return DateFormat.getDateInstance().format(new Date(issl.getDatan()));
		}
	}
	
	class IsslPokazNode extends DefaultMutableTreeNode{
		private IsslInfo isslpokaz;
		
		public IsslPokazNode(IsslInfo isslpokaz) {
			this.isslpokaz = isslpokaz;
		}
		
		@Override
		public String toString() {
			return isslpokaz.getPokaz();
		}
	}

	private void addLineToDetailInfo(String name, boolean isSet, Object value) {
		if (isSet)
			if ((name != null) && (value != null))
				if ((name.length() > 0) && (value.toString().length() > 0))
					sb.append(String.format("%s: %s%s", name, value, lineSep));
	}
	
	private void addLineToDetailInfo(String name, Object value) {
		addLineToDetailInfo(name, true, value);
	}
	
	private class ShablonSearchListener implements DocumentListener {
		Timer timer = new Timer(500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateNow();
			}
		});
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			timer.restart();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			timer.restart();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			timer.restart();
		}
		
		public void updateNow() {
			timer.stop();
			loadShablonList();
		}
		
		public void updateNow(String searchString) {
			tbShabSrc.setText(searchString);
			updateNow();
		}
	}
	
	private void syncShablonList(String searchString, Shablon shablon) {
		if (shablon != null) {
			shablonSearchListener.updateNow(searchString);
			for (int i = 0; i < lbShabSrc.getData().size(); i++)
				if (lbShabSrc.getData().get(i).pcod == shablon.id)
				{
					lbShabSrc.setSelectedIndex(i);
					break;
				}
		} else {
			lbShabSrc.setSelectedIndex(-1);
		}
	}
	
	private void loadShablonList() {
		try {
			lbShabSrc.setData(MainForm.tcl.getShOsmPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu, (tbShabSrc.getText().length() < 3) ? null : '%' + tbShabSrc.getText() + '%'));
		} catch (KmiacServerException e1) {
			JOptionPane.showMessageDialog(Vvod.this, "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
	}
	
	public void onConnect() {
		try {
			tblPos.setStringClassifierSelector(2, ConnectionManager.instance.getStringClassifier(StringClassifiers.n_s00));
			cmbVidStacionar.setData(MainForm.tcl.get_n_tip());
			cmbLpu.setData(MainForm.tcl.get_m00());
			listVidIssl = MainForm.tcl.get_vid_issl();
			lbShabSrc.setData(MainForm.tcl.getShOsmPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu,  null));
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(Vvod.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	public void showVvod(ZapVr zapVr) {
		Vvod.zapVr = zapVr;
				
		try {
			setTitle(String.format("Врачебный осмотр - пациент: %s %s %s, номер и серия полиса: %s %s", zapVr.getFam(), zapVr.getIm(), zapVr.getOth(), zapVr.getNompolis(), zapVr.getSerpolis()));
			int age = (int) ((System.currentTimeMillis() - zapVr.datar) / 31556952000L);
			btnBer.setEnabled((zapVr.pol != 1) && ((age > 13) && (age < 50)));
			chbDiagBer.setEnabled(btnBer.isEnabled());
			lblLastShab.setText("<html>Последний выбранный шаблон: </html>");
			
			tblPos.setData(MainForm.tcl.getPvizitAmb(zapVr.getId_pvizit()));
			if (tblPos.getRowCount() > 0) {
				if (pvizitAmb.datap < getDateMills(System.currentTimeMillis()))
					btnPosAdd.doClick();
			} else {
				btnPosAdd.doClick();
			}
			tblDiag.setData(MainForm.tcl.getPdiagAmb(zapVr.getId_pvizit()));
			if (tblDiag.getRowCount() > 0)
				tblDiag.setRowSelectionInterval(tblDiag.getRowCount() - 1, tblDiag.getRowCount() - 1);
			treeRezIssl.setModel(new DefaultTreeModel(createNodes()));
			
			checkZapVrNext();
			
			setVisible(true);
			MainForm.instance.setVisible(false);
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
	}
	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
	
	private void pasteShablon(Shablon sh) {
		if (sh == null)
			return;
		
		tbJal.setText("");
		tbAnam.setText("");
		tbStat.setText("");
		tbLoc.setText("");
		tbLech.setText("");
		tbOcen.setText("");
		tbZakl.setText("");
		tbRecom.setText("");

		for (ShablonText st : sh.textList) {
			switch (st.grupId) {
			case 1:
				tbJal.setText(st.text);
				break;
			case 2:
				tbAnam.setText(st.text);
				break;
			case 6:
				tbStat.setText(st.text);
				break;
			case 8:
				tbLoc.setText(st.text);
				break;
			case 10:
				tbLech.setText(st.text);
				break;
			case 14:
				tbOcen.setText(st.text);
				break;
			case 13:
				tbZakl.setText(st.text);
				break;
			case 12:
				tbRecom.setText(st.text);
				break;
			default:
				break;
			}
		}
		
		lblLastShab.setText(String.format("<html>Последний выбранный шаблон: %s %s</html>", sh.diag.trim(), sh.name));
	}

	private void setHarZabEnabled(boolean value) {
		rbtDiagHarHron.setEnabled(value);
		rbtDiagHarOstr.setEnabled(value);
		rbtDiagStadRan.setEnabled(value);
		rbtDiagStadPoz.setEnabled(value);
		tbDiagDispDatVz.setEnabled(value);
		tbDiagDispDatIsh.setEnabled(value);
		cmbDiagDispGrup.setEnabled(value);
		cmbDiagDispIsh.setEnabled(value);
		tfNuch.setEnabled(value);
		tfNewDs.setEnabled(value);
		tfDataIzmNewDs.setEnabled(value);
		btnDispHron.setEnabled(value);
	}
	
	private boolean checkInput() throws TException {
//		try {
//			if (tblPos.getData().size() > 0)
//				if (tblPos.getData().get(0).datap == getDateMills(System.currentTimeMillis()))
//					if (MainForm.tcl.isZapVrNext(zapVr.id_pvizit)) {
//						return true;
//					} else {
//						if (!MainForm.tcl.getPvizit(zapVr.id_pvizit).isSetIshod())
//							return false;
//						else if (tblDiag.getData().size() == 0) 
//							return false;
//					}
//		} catch (KmiacServerException | PvizitNotFoundException e) {
//			return false;
//		}
		return true;
	}
	
	private long getDateMills(long mills) {
		try {
			SimpleDateFormat frm = new SimpleDateFormat("dd.MM.yyyy");
			
			return frm.parse(frm.format(new Date(mills))).getTime();
		} catch (ParseException e) {
			System.err.println("Error getDateMills");
			
			return 0;
		}
	}
	
	private void addDiag(StringClassifier mkb) {
		try {
  			if (mkb != null) {
		  		diagamb = new PdiagAmb();
		  		diagamb.setId_obr(zapVr.getId_pvizit());
		  		diagamb.setNpasp(zapVr.getNpasp());
		  		diagamb.setDatap(System.currentTimeMillis());
		  		diagamb.setDatad(System.currentTimeMillis());
		  		diagamb.setCod_sp(MainForm.authInfo.getPcod());
		  		diagamb.setCdol(MainForm.authInfo.getCdol());
		  		diagamb.setPredv(true);
		  		diagamb.setDiag_stat(1);
				diagamb.setDiag(mkb.pcod);
				diagamb.setNamed(mkb.name);
				diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
	 			tblDiag.addItem(diagamb);
  			}
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
	}
		
//	private boolean checkCmb(ThriftIntegerClassifierCombobox<IntegerClassifier> cmb) throws TException {
//			if (tblPos.getData().size() > 0){
//				if (cmb.getSelectedPcod()==null)
//					return false;
//				else return true;
//					}
//		return true;
//	}

	private void checkZapVrNext() throws KmiacServerException, TException {
		if (MainForm.tcl.isZapVrNext(zapVr.id_pvizit)) {
			cmbZaklIsh.setSelectedIndex(-1);
			cmbZaklIsh.setEnabled(false);
			btnPosSave.doClick();
		} else {
			cmbZaklIsh.setEnabled(true);
		}
	}

	/**
	 * @throws TException
	 */
	public boolean checkTalInput() {
//		if (!checkCmb(cmbVidOpl)) {
//			JOptionPane.showMessageDialog(Vvod.this, "Поле 'Вид оплаты' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
//			cmbVidOpl.requestFocusInWindow();
//			return false;
//		}
//		if (!checkCmb(cmbCelObr)) {
//			JOptionPane.showMessageDialog(Vvod.this, "Поле 'Цель посещения' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
//			cmbCelObr.requestFocusInWindow();
//			return false;
//		}
//		if (!checkCmb(cmbRez)) {
//			JOptionPane.showMessageDialog(Vvod.this, "Поле 'Результат' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
//			cmbRez.requestFocusInWindow();
//			return false;
//		}
//		if (!checkCmb(cmbMobs)) {
//			JOptionPane.showMessageDialog(Vvod.this, "Поле 'Место обслуживания' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
//			cmbMobs.requestFocusInWindow();
//			return false;
//		}
		return true;
	}

	private boolean prepareSavePos() {
		for (PvizitAmb pv: tblPos.getData())
			if (pv != pvizitAmb)
				if (pv.getDatap() == pvizitAmb.getDatap()) {
					JOptionPane.showMessageDialog(Vvod.this, "Посещение с такой датой уже занесено");
					tblPos.cancelEdit();
					pvizitAmb = tblPos.getSelectedItem();
					return false;
				}
		if (!checkTalInput())
			return false;
		
		priem = new Priem();
		anamZab = new AnamZab();
		if (pvizit.id > 0) {
			priem.setId_pvizit(pvizit.getId());
			priem.setNpasp(pvizit.getNpasp());
			priem.setIdpos(pvizitAmb.getId());
			priem.setT_temp(getTextOrNull(tbStatTemp.getText()));
			priem.setT_ad(getTextOrNull(tbStatAd.getText()));
			priem.setT_chss(getTextOrNull(tbStatChss.getText()));
			priem.setT_rost(getTextOrNull(tbStatRost.getText()));
			priem.setT_ves(getTextOrNull(tbStatVes.getText()));
			priem.setT_st_localis(getTextOrNull(tbLoc.getText()));
			priem.setT_ocenka(getTextOrNull(tbOcen.getText()));
			priem.setT_jalob(getTextOrNull(tbJal.getText()));
			priem.setT_status_praesense(getTextOrNull(tbStat.getText()));
			priem.setT_fiz_obsl(getTextOrNull(tbFiz.getText()));
			
			anamZab.setId_pvizit(pvizit.getId());
			anamZab.setNpasp(pvizit.getNpasp());
			anamZab.setT_ist_zab(getTextOrNull(tbAnam.getText()));
		}
		
		pvizit.setZakl(getTextOrNull(tbZakl.getText()));
		pvizit.setRecomend(getTextOrNull(tbZaklRek.getText()));
		pvizit.setLech(getTextOrNull(tbLech.getText()));
		if (cmbCelObr.getSelectedPcod() != null) {
			pvizitAmb.setCpos(cmbCelObr.getSelectedPcod());
			pvizit.setCobr(pvizitAmb.getCpos());
		} else {
			pvizitAmb.unsetCpos();pvizit.unsetCobr();
		}
		if (cmbRez.getSelectedPcod() != null) {
			pvizitAmb.setRezult(cmbRez.getSelectedPcod());
			pvizit.setRezult(pvizitAmb.getRezult());
		} else {
			pvizitAmb.unsetRezult();pvizit.unsetRezult();
			pvizit.unsetRezult();
		}
		if (cmbZaklIsh.getSelectedPcod() != null)
			pvizit.setIshod(cmbZaklIsh.getSelectedPcod());
		else
			pvizit.unsetIshod();
		if (cmbMobs.getSelectedPcod() != null)
			pvizitAmb.setMobs(cmbMobs.getSelectedPcod());
		else
			pvizitAmb.unsetMobs();
		if (cmbVidOpl.getSelectedPcod() != null) {
			pvizitAmb.setOpl(cmbVidOpl.getSelectedPcod());
			if (cmbVidOpl.getSelectedPcod() == 2) {
				try {
					pvizitAmb.setStoim(MainForm.tcl.getStoim(MainForm.authInfo.getKateg(), MainForm.authInfo.getC_nom(), MainForm.authInfo.getCdol()));
					pvizitAmbCopy.setStoim(pvizitAmb.stoim);
				} catch (TException e3) {
					e3.printStackTrace();
					MainForm.conMan.reconnect(e3);
				}
			} else {
				pvizitAmb.unsetStoim();
				pvizitAmbCopy.unsetStoim();
			}
		}
		else
			pvizitAmb.unsetOpl();
		for (PdiagAmb pd : tblDiag.getData())
			if (pd.diag_stat == 1) {
				pvizitAmb.setDiag(pd.getDiag());
			}
		
		return true;
	}
	
	private boolean checkDataChanged() {
		if (!prepareSavePos())
			return false;
		
		if (!pvizitAmbCopy.equals(pvizitAmb))
			return true;
		if (!priemCopy.equals(priem))
			return true;
		if (!anamZabCopy.equals(anamZab))
			return true;
		if (!pvizitCopy.equals(pvizit))
			return true;
		
		return false;
	}
}
