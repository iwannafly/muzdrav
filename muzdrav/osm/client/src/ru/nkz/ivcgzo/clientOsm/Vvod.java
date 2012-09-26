package ru.nkz.ivcgzo.clientOsm;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
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
import ru.nkz.ivcgzo.thriftOsm.Pmer;
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
	private ThriftStringClassifierCombobox<StringClassifier> cmbNaprVidIssl;
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
	private JCheckBox chbDiagBoe;
	private JCheckBox chbDiagInv;
	private JCheckBox chbDiagBer;
	private JComboBox<String> cmbKonsVidNapr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbKonsMesto;
	private JTextArea tbKonsObosnov;
	private JTextArea tbLech;
	private JButton btnBer;
	private JButton btnPrint;
	private ThriftIntegerClassifierList lbShabSrc;
	private ShablonSearchListener shablonSearchListener;
	
	private List<IntegerClassifier> listVidIssl;
	public static ZapVr zapVr;
	public static PvizitAmb pvizitAmb;
	private Priem priem;
	private AnamZab anamZab;
	public static Pvizit pvizit;
	private PdiagAmb diagamb;
	private PdiagZ pdiag;
	private Pdisp pdisp;
	
	private FormSign sign;
	private FormPostBer postber;
	private ShablonForm shablonform;
	private FormRdDin dinform;
	private DispHron disphron;
	public static JButton btnPosAdd;
	
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
					if (!checkCmb(cmbVidOpl)) {
						JOptionPane.showMessageDialog(Vvod.this, "Поле 'Вид оплаты' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
						cmbVidOpl.requestFocus();
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
					if (!checkCmb(cmbCelObr)) {
						JOptionPane.showMessageDialog(Vvod.this, "Поле 'Цель посещения' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
						cmbCelObr.requestFocus();
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
					if (!checkCmb(cmbRez)) {
						JOptionPane.showMessageDialog(Vvod.this, "Поле 'Результат' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
						cmbRez.requestFocus();
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
					if (!checkCmb(cmbMobs)) {
						JOptionPane.showMessageDialog(Vvod.this, "Поле 'Место обслуживания' не заполнено", "Предупреждение", JOptionPane.ERROR_MESSAGE);
						cmbMobs.requestFocus();
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						return;
					}
					
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
		setBounds(100, 100, 1010, 748);
		
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
				  JPopupMenu menuBer = new JPopupMenu();
		            
		       		JMenuItem mb1 = new JMenuItem("Постановка на учет по беременности");
		       		mb1.addActionListener(new ActionListener() {
		       			@Override
		       			public void actionPerformed(ActionEvent arg0) {
		       				postber.setVisible(true);	
		       			}
		       		});
		       		menuBer.add(mb1);
		       		
					JMenuItem mb2 = new JMenuItem("Динамическое наблюдение за беременными");
					mb2.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							dinform = new FormRdDin();
							dinform.setVisible(true);
						}
		});
					menuBer.add(mb2);
					
					menuBer.show(btnBer, 0, btnBer.getHeight());
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
       						MainForm.conMan.openFileInEditor(cliPath, true);
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
       						MainForm.conMan.openFileInEditor(cliPath, true);
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
	       						MainForm.conMan.openFileInEditor(cliPath, true);
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
	       						MainForm.conMan.openFileInEditor(cliPath, true);
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
		
		JButton btnRecPriem = new JButton("Запись на прием");
		btnRecPriem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
					IClient client = MainForm.conMan.getPluginLoader().loadPluginByAppId(10);
					client.showModal(MainForm.instance, zapVr.npasp, zapVr.im, zapVr.fam, zapVr.oth, zapVr.datar, zapVr.id_pvizit);
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlTalon, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
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
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
					.addGap(11))
		);
		
		shablonSearchListener = new ShablonSearchListener();
		
		tbShabSrc = new CustomTextField(true, true, false);
		tbShabSrc.getDocument().addDocumentListener(shablonSearchListener);
		tbShabSrc.setColumns(10);
		
		JButton btnShabSrc = new JButton("...");
		btnShabSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shablonform.showShablonForm(tbShabSrc.getText());
				syncShablonList(shablonform.getSearchString(), shablonform.getShablon());
				pasteShablon(shablonform.getShablon());
			}
		});
		
		JScrollPane spShabSrc = new JScrollPane();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 763, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(tbShabSrc, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnShabSrc, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
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
							.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 460, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		lbShabSrc = new ThriftIntegerClassifierList();
		lbShabSrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) 
					if (lbShabSrc.getSelectedValue() != null) {
						try {
							pasteShablon(MainForm.tcl.getSh(lbShabSrc.getSelectedValue().pcod));
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
		tabbedPane.addTab("Жалобы", null, pnlJal, null);
		
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
		tabbedPane.addTab("Anamnesis morbi", null, pnlAnam, null);
		
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
		tabbedPane.addTab("Status praesense", null, pnlStat, null);
		
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
		tabbedPane.addTab("Физикальное обследование", null, pnlFiz, null);
		
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
		tabbedPane.addTab("Localis status", null, pnlLoc, null);
		
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
		tabbedPane.addTab("Диагноз", null, pnlDiag, null);
		
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
					if (JOptionPane.showConfirmDialog(btnDiagDel, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		  			MainForm.tcl.DeletePdiagAmb(tblDiag.getSelectedItem().getId());
					tblDiag.setData(MainForm.tcl.getPdiagAmb(Vvod.zapVr.getId_pvizit()));}
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
		  			MainForm.tcl.setPdisp(pdisp);
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
				.addGap(0, 161, Short.MAX_VALUE)
				.addGroup(gl_PnlDiag.createSequentialGroup()
					.addComponent(spDiag, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnDiagAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDiagDel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDiagSave, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
		);
		gl_PnlDiag.setVerticalGroup(
			gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 118, Short.MAX_VALUE)
				.addGroup(gl_PnlDiag.createSequentialGroup()
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
						.addComponent(spDiag, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
						.addGroup(gl_PnlDiag.createSequentialGroup()
							.addComponent(btnDiagAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDiagDel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDiagSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(0))
		);
		
		tblDiag = new CustomTable<>(false, true, PdiagAmb.class, 7, "Дата", 3, "Код МКБ");
		tblDiag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (tblDiag.getSelectedItem() != null) {
						StringClassifier res = ConnectionManager.instance.showMkbTreeForm("Диагнозы", tblDiag.getSelectedItem().diag);
						if (res != null) {
							tblDiag.getSelectedItem().setDiag(res.pcod);
							tblDiag.updateSelectedItem();
						}
					}
			}
		});
		tblDiag.setDateField(0);
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
							pdisp = MainForm.tcl.getPdisp(diagamb.getId());
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
				.addGroup(gl_pnlDiagPredv.createSequentialGroup()
					.addComponent(rbtDiagPredv, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDiagZakl, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
					.addGap(3))
		);
		gl_pnlDiagPredv.setVerticalGroup(
			gl_pnlDiagPredv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagPredv.createSequentialGroup()
					.addGroup(gl_pnlDiagPredv.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiagPredv, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbtDiagZakl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
		bgDiagHarZab.add(rbtDiagHarOstr);
		
		rbtDiagHarHron = new JRadioButton("Хроническое");
		bgDiagHarZab.add(rbtDiagHarOstr);
		
		GroupLayout gl_pnlDiagHarZab = new GroupLayout(pnlDiagHarZab);
		gl_pnlDiagHarZab.setHorizontalGroup(
			gl_pnlDiagHarZab.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlDiagHarZab.createSequentialGroup()
					.addComponent(rbtDiagHarOstr, GroupLayout.PREFERRED_SIZE, 132, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtDiagHarHron, GroupLayout.PREFERRED_SIZE, 130, Short.MAX_VALUE)
					.addGap(4))
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
		
		chbDiagBer = new JCheckBox("Противопоказания для беременности");
		GroupLayout gl_pnlDiag = new GroupLayout(pnlDiag);
		gl_pnlDiag.setHorizontalGroup(
			gl_pnlDiag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.TRAILING)
						.addComponent(PnlDiag, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addComponent(lblDiagOpis, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spDiagOpis, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
						.addComponent(pnlDiagStat, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addComponent(pnlDiagPredv, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addComponent(pnlDiagStadZab, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pnlDiagHarZab, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addComponent(lblDiagVidTr, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbDiagVidTr, 0, 145, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDiagObstReg, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbDiagObstReg, 0, 142, Short.MAX_VALUE))
						.addComponent(pnlDiagDisp, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(gl_pnlDiag.createSequentialGroup()
							.addGap(11)
							.addComponent(chbDiagBoe, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chbDiagInv, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chbDiagBer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pnlDiag.setVerticalGroup(
			gl_pnlDiag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiag.createSequentialGroup()
					.addContainerGap()
					.addComponent(PnlDiag, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDiagOpis)
						.addComponent(spDiagOpis, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnlDiagStat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlDiagPredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING, false)
						.addComponent(pnlDiagStadZab, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlDiagHarZab, GroupLayout.PREFERRED_SIZE, 42, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiagVidTr)
						.addComponent(cmbDiagVidTr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbDiagObstReg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagObstReg))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlDiagDisp, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.BASELINE)
						.addComponent(chbDiagBer)
						.addComponent(chbDiagBoe)
						.addComponent(chbDiagInv))
					.addGap(41))
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
		
		JButton btnDispHron = new JButton("...");
		btnDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			disphron.ShowDispHron();
			}
		});
		GroupLayout gl_pnlDiagDisp = new GroupLayout(pnlDiagDisp);
		gl_pnlDiagDisp.setHorizontalGroup(
			gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagDisp.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDiagDisp.createSequentialGroup()
							.addComponent(lblDiagDispDatVz, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbDiagDispDatVz, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnDispHron, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDiagDisp.createSequentialGroup()
							.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblDiagDispIsh, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblDiagDispGrup, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.TRAILING)
								.addComponent(cmbDiagDispIsh, 0, 462, Short.MAX_VALUE)
								.addComponent(cmbDiagDispGrup, Alignment.LEADING, 0, 462, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_pnlDiagDisp.setVerticalGroup(
			gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagDisp.createSequentialGroup()
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiagDispDatVz)
						.addComponent(tbDiagDispDatVz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagDispDatIsh)
						.addComponent(tbDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDispHron, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbDiagDispGrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagDispGrup))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(cmbDiagDispIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiagDispIsh))
					.addGap(14))
		);
		pnlDiagDisp.setLayout(gl_pnlDiagDisp);
		
		rbtDiagStadRan = new JRadioButton("Ранняя");
		bgDiagStadZab.add(rbtDiagStadRan);
		
		rbtDiagStadPoz = new JRadioButton("Поздняя");
		bgDiagStadZab.add(rbtDiagStadPoz);
		
		GroupLayout gl_pnlDiagStadZab = new GroupLayout(pnlDiagStadZab);
		gl_pnlDiagStadZab.setHorizontalGroup(
			gl_pnlDiagStadZab.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlDiagStadZab.createSequentialGroup()
					.addComponent(rbtDiagStadRan, GroupLayout.PREFERRED_SIZE, 126, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtDiagStadPoz, GroupLayout.PREFERRED_SIZE, 125, Short.MAX_VALUE)
					.addGap(4))
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
					.addComponent(rbtDiagOsn, GroupLayout.PREFERRED_SIZE, 113, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDiagSop, GroupLayout.PREFERRED_SIZE, 114, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDiagOsl, GroupLayout.PREFERRED_SIZE, 116, Short.MAX_VALUE)
					.addGap(2))
		);
		gl_pnlDiagStat.setVerticalGroup(
			gl_pnlDiagStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagStat.createSequentialGroup()
					.addGroup(gl_pnlDiagStat.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiagOsn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbtDiagSop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbtDiagOsl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(2))
		);
		pnlDiagStat.setLayout(gl_pnlDiagStat);
		
		tbDiagOpis = new JTextArea();
		tbDiagOpis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spDiagOpis.setViewportView(tbDiagOpis);
		pnlDiag.setLayout(gl_pnlDiag);
		
		JPanel pnlLech = new JPanel();
		tabbedPane.addTab("Назначенное лечение", null, pnlLech, null);
		
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
		
		JPanel pnlNapr = new JPanel();
		tabbedPane.addTab("Направления", null, pnlNapr, null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_pnlNapr = new GroupLayout(pnlNapr);
		gl_pnlNapr.setHorizontalGroup(
			gl_pnlNapr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNapr.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_1, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlNapr.setVerticalGroup(
			gl_pnlNapr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNapr.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_1, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel pnlIssl = new JPanel();
		tabbedPane_1.addTab("Направление на исследование", null, pnlIssl, null);
		
		cmbNaprMesto = new ThriftIntegerClassifierCombobox<>(true);
		cmbNaprMesto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cmbNaprMesto.getSelectedItem() != null)
						cmbNaprVidIssl.setData(MainForm.tcl.get_n_nz1(cmbNaprMesto.getSelectedItem().pcod));
				} catch (KmiacServerException e1) {
					JOptionPane.showMessageDialog(Vvod.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		JLabel lblNaprMesto = new JLabel("Лаборатория");
		
		cmbNaprVidIssl = new ThriftStringClassifierCombobox<>(true);
		cmbNaprVidIssl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (cmbNaprVidIssl.getSelectedItem()!= null)
						tblNaprPokazMet.setData(MainForm.tcl.getPokazMet(cmbNaprVidIssl.getSelectedItem().pcod));
				} catch (KmiacServerException e) {
					JOptionPane.showMessageDialog(Vvod.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		JLabel lblNaprVidIssl = new JLabel("Органы и системы");
		
		JLabel lblNaprPokazMet = new JLabel("Показатели");
		
		JScrollPane spNaprPokazMet = new JScrollPane();
		
		JButton btnNaprPrint = new JButton("Печать");
		btnNaprPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if ((cmbNaprVidIssl.getSelectedItem() != null) ) {
						P_isl_ld pisl = new P_isl_ld();
						Prez_d prezd = new Prez_d();
						Prez_l prezl = new Prez_l();
						pisl.setNpasp(Vvod.zapVr.getNpasp());
						pisl.setPcisl(cmbNaprVidIssl.getSelectedPcod());
						pisl.setNapravl(2);
						pisl.setNaprotd(MainForm.authInfo.getCpodr());
						pisl.setDatan(System.currentTimeMillis());
						pisl.setVrach(MainForm.authInfo.getPcod());
						pisl.setDataz(System.currentTimeMillis());
						pisl.setPvizit_id(tblPos.getSelectedItem().getId_obr());
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
//							isslmet.setKodVidIssl(cbVidIssl.getSelectedItem().getPcod());
							isslmet.setUserId(MainForm.authInfo.getUser_id());
							isslmet.setNpasp(Vvod.zapVr.getNpasp());
//							isslmet.setKodMetod(tabMetod.getSelectedItem().getObst());
							isslmet.setPokaz(selItems);
							if (cmbNaprMesto.getSelectedItem() != null) isslmet.setMesto(cmbNaprMesto.getSelectedItem().getName());
							isslmet.setKab(getTextOrNull(tbNaprKab.getText()));
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
		GroupLayout gl_pnlIssl = new GroupLayout(pnlIssl);
		gl_pnlIssl.setHorizontalGroup(
			gl_pnlIssl.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.TRAILING)
						.addComponent(spNaprPokazMet, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
						.addComponent(lblNaprPokazMet, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_pnlIssl.createSequentialGroup()
							.addComponent(lblNaprMesto, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbNaprMesto, 0, 463, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_pnlIssl.createSequentialGroup()
							.addComponent(lblNaprVidIssl, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbNaprVidIssl, 0, 424, Short.MAX_VALUE))
						.addComponent(btnNaprPrint, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, gl_pnlIssl.createSequentialGroup()
							.addComponent(lblNaprKab, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbNaprKab, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlIssl.setVerticalGroup(
			gl_pnlIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaprMesto)
						.addComponent(cmbNaprMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaprVidIssl)
						.addComponent(cmbNaprVidIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNaprPokazMet)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spNaprPokazMet, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNaprKab)
						.addComponent(tbNaprKab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNaprPrint)
					.addContainerGap())
		);
		
		tblNaprPokazMet = new CustomTable<>(false, true, PokazMet.class, 0, "Код", 1, "Наименование", 2, "Выбор");
		tblNaprPokazMet.setEditableFields(true, 2);
		tblNaprPokazMet.setFillsViewportHeight(true);
		spNaprPokazMet.setViewportView(tblNaprPokazMet);
		pnlIssl.setLayout(gl_pnlIssl);
		
		JPanel pnlKons = new JPanel();
		tabbedPane_1.addTab("Направление", null, pnlKons, null);
		
		cmbKonsVidNapr = new JComboBox<>();
		cmbKonsVidNapr.setModel(new DefaultComboBoxModel<>(new String[] {"госпитализацию", "консультацию", "обследование"}));
		
		JLabel lblKonsVidNapr = new JLabel("на");
		
		cmbKonsMesto = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_n00);
		
		JLabel lblKonsMesto = new JLabel("Куда");
		
		JLabel lblKonsObosnov = new JLabel("Обоснование для направления");
		
		JButton btnKonsPrint = new JButton("Печать");
		btnKonsPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					PNapr pnapr = new PNapr();
					if (cmbKonsVidNapr.getSelectedIndex() != 0){
						pnapr.setIdpvizit(tblPos.getSelectedItem().getId_obr());
						pnapr.setVid_doc(3);
						pnapr.setText(tbKonsObosnov.getText());					
						pnapr.setId(MainForm.tcl.AddPnapr(pnapr));
						NaprKons naprkons = new NaprKons();
						naprkons.setUserId(MainForm.authInfo.getUser_id());
						naprkons.setNpasp(Vvod.zapVr.getNpasp());
						naprkons.setObosnov(tbKonsObosnov.getText());
						if (cmbKonsMesto.getSelectedItem()!= null) naprkons.setCpol(cmbKonsMesto.getSelectedItem().getName());
						naprkons.setNazv(cmbKonsVidNapr.getSelectedItem().toString());
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
						pnapr.setId(MainForm.tcl.AddPnapr(pnapr));
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
   						MainForm.conMan.openFileInEditor(cliPath, false);
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
		
		JScrollPane spKonsObosnov = new JScrollPane();
		GroupLayout gl_pnlKons = new GroupLayout(pnlKons);
		gl_pnlKons.setHorizontalGroup(
			gl_pnlKons.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlKons.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlKons.createParallelGroup(Alignment.TRAILING)
						.addComponent(spKonsObosnov, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
						.addComponent(lblKonsObosnov, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_pnlKons.createSequentialGroup()
							.addComponent(lblKonsVidNapr, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbKonsVidNapr, 0, 527, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_pnlKons.createSequentialGroup()
							.addComponent(lblKonsMesto, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbKonsMesto, 0, 501, Short.MAX_VALUE))
						.addComponent(btnKonsPrint, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_pnlKons.setVerticalGroup(
			gl_pnlKons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlKons.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlKons.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKonsVidNapr)
						.addComponent(cmbKonsVidNapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlKons.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKonsMesto)
						.addComponent(cmbKonsMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblKonsObosnov)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spKonsObosnov, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnKonsPrint)
					.addContainerGap())
		);
		
		tbKonsObosnov = new JTextArea();
		tbKonsObosnov.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tbKonsObosnov.setLineWrap(true);
		tbKonsObosnov.setWrapStyleWord(true);
		spKonsObosnov.setViewportView(tbKonsObosnov);
		pnlKons.setLayout(gl_pnlKons);
		pnlNapr.setLayout(gl_pnlNapr);
		
		JPanel pnlOcen = new JPanel();
		tabbedPane.addTab("Оценка данных анамнеза", null, pnlOcen, null);
		
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
		
		JPanel pnlZakl = new JPanel();
		tabbedPane.addTab("Заключение", null, pnlZakl, null);
		
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
		pnlZakl.setLayout(gl_pnlZakl);;
		
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
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.LEADING, false)
						.addComponent(cmbCelObr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cmbVidOpl, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblMobs, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblRez, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.TRAILING)
						.addComponent(cmbMobs, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
						.addComponent(cmbRez, 0, 397, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlTalon.setVerticalGroup(
			gl_pnlTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTalon.createSequentialGroup()
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVidOpl)
						.addComponent(cmbVidOpl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRez)
						.addComponent(cmbRez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
				
				for (PvizitAmb pviz : tblPos.getData())
					if (pviz.getDatap() == getDateMills(System.currentTimeMillis())) {
						JOptionPane.showMessageDialog(Vvod.this, "Невозможно записать два посещения за одну дату");
						return;
					}
				
				try {
					Vvod.pvizit = MainForm.tcl.getPvizit(pvizit.getId());
					pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
					tblPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
					tblPos.setRowSelectionInterval(tblPos.getRowCount() - 1, 0);
				} catch (KmiacServerException e2) {
					e2.printStackTrace();
				} catch (PvizitNotFoundException e2) {
					try {
						MainForm.tcl.AddPvizit(pvizit);
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						tblPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
						tblPos.setRowSelectionInterval(tblPos.getRowCount() - 1, 0);
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
					for (PvizitAmb pviz : tblPos.getData())
						if (pviz.getDatap() == getDateMills(System.currentTimeMillis())) {
							JOptionPane.showMessageDialog(Vvod.this, "Невозможно записать два посещения за одну дату");
							return;
						}
					
					try {
						pvizit.setNpasp(zapVr.getNpasp());
						pvizit.setCpol(MainForm.authInfo.getCpodr());
						pvizit.setDatao(System.currentTimeMillis());
						pvizit.setCod_sp(MainForm.authInfo.getPcod());
						pvizit.setCdol(MainForm.authInfo.getCdol());
						pvizit.setCuser(MainForm.authInfo.getUser_id());
						pvizit.setDataz(System.currentTimeMillis());
						pvizit.setId(MainForm.tcl.AddPvizitId(pvizit));
						pvizitAmb = new PvizitAmb();
						pvizitAmb.setId_obr(pvizit.getId());
						pvizitAmb.setNpasp(zapVr.getNpasp());
						pvizitAmb.setDatap(System.currentTimeMillis());
						pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
						pvizitAmb.setCdol(MainForm.authInfo.getCdol());
						pvizitAmb.setCpol(MainForm.authInfo.getCpodr());
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						tblPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
						tblPos.setRowSelectionInterval(tblPos.getRowCount() - 1, 0);
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}	
				}
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
							MainForm.tcl.DeletePriem(tblPos.getSelectedItem().getId());
							MainForm.tcl.DeletePvizitAmb(tblPos.getSelectedItem().getId());
							if (tblPos.getSelectedRow() == tblPos.getRowCount() - 1)
								MainForm.tcl.DeleteEtalon(zapVr.getId_pvizit());
							tblPos.setData(MainForm.tcl.getPvizitAmb(Vvod.zapVr.getId_pvizit()));
							if (tblPos.getSelectedItem() == null) {
								MainForm.tcl.DeleteAnamZab(zapVr.getId_pvizit());
								MainForm.tcl.DeletePvizit(zapVr.getId_pvizit());	
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
		
		JButton btnPosSave = new JButton("");
		btnPosSave.setToolTipText("Сохранение изменений");
		btnPosSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblPos.getSelectedItem() != null)
					try {
						priem = new Priem();
						anamZab = new AnamZab();
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
						
						pvizit.setZakl(getTextOrNull(tbZakl.getText()));
						pvizit.setRecomend(getTextOrNull(tbZaklRek.getText()));
						pvizit.setLech(getTextOrNull(tbLech.getText()));
						if (cmbCelObr.getSelectedPcod() != null)
							{pvizitAmb.setCpos(cmbCelObr.getSelectedPcod());
							pvizit.setCobr(pvizitAmb.getCpos());}
							else 
							{pvizitAmb.unsetCpos();pvizit.unsetCobr();}
						if (cmbRez.getSelectedPcod() != null)
							{pvizitAmb.setRezult(cmbRez.getSelectedPcod());
							pvizit.setRezult(pvizitAmb.getRezult());}
							else {pvizitAmb.unsetRezult();pvizit.unsetRezult();
								pvizit.unsetRezult();}
						if (cmbZaklIsh.getSelectedPcod() != null)
							pvizit.setIshod(cmbZaklIsh.getSelectedPcod());
							else pvizit.unsetIshod();
						if (cmbMobs.getSelectedPcod() != null)
							pvizitAmb.setMobs(cmbMobs.getSelectedPcod());
						else
							{pvizitAmb.unsetMobs();}
						if (cmbVidOpl.getSelectedPcod() != null)
							pvizitAmb.setOpl(cmbVidOpl.getSelectedPcod());
						else 
						{pvizitAmb.unsetOpl();}
						pvizitAmb.setPl_extr(1);
						for (PdiagAmb pd : tblDiag.getData()) {
							if (pd.diag_stat==1) {
								pvizitAmb.setDiag(pd.getDiag());}
						}
						
						
						MainForm.tcl.setPriem(priem);
						MainForm.tcl.setAnamZab(anamZab);
						MainForm.tcl.UpdatePvizit(pvizit);
						MainForm.tcl.UpdatePvizitAmb(pvizitAmb);
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
		
		tblPos = new CustomTable<>(true, true, PvizitAmb.class, 3, "Дата", 19, "ФИО врача", 5, "Должность");
		tblPos.setDateField(0);
		tblPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (!arg0.getValueIsAdjusting()){
				if (tblPos.getSelectedItem()!= null) {
					pvizitAmb = tblPos.getSelectedItem();
					try {
						
						priem = MainForm.tcl.getPriem(tblPos.getSelectedItem().npasp, tblPos.getSelectedItem().id);
						anamZab = MainForm.tcl.getAnamZab(tblPos.getSelectedItem().id_obr, tblPos.getSelectedItem().npasp);
						pvizit	= MainForm.tcl.getPvizit(tblPos.getSelectedItem().getId_obr());
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (PvizitNotFoundException e) {
						e.printStackTrace();
					} catch (PriemNotFoundException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
				} else {
					pvizitAmb = new PvizitAmb();
					priem = new Priem();
					anamZab = new AnamZab();
					pvizit = new Pvizit();
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
			}
		}
	});
		tblPos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblPos);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

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
		shablonSearchListener.updateNow(searchString);
		
		for (int i = 0; i < lbShabSrc.getData().size(); i++)
			if (lbShabSrc.getData().get(i).pcod == shablon.id)
			{
				lbShabSrc.setSelectedIndex(i);
				break;
			}
	}
	
	private void loadShablonList() {
		try {
			lbShabSrc.setData(MainForm.tcl.getShPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu, (tbShabSrc.getText().length() < 3) ? null : '%' + tbShabSrc.getText() + '%'));
		} catch (KmiacServerException e1) {
			JOptionPane.showMessageDialog(Vvod.this, "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
	}
	
	public void onConnect() {
		try {
			tblPos.setStringClassifierSelector(2, ConnectionManager.instance.getStringClassifier(StringClassifiers.n_s00));
			cmbNaprMesto.setData(MainForm.tcl.get_n_lds(MainForm.authInfo.clpu));
			cmbKonsMesto.setData(MainForm.tcl.get_n_m00(MainForm.authInfo.clpu));
			listVidIssl = MainForm.tcl.get_vid_issl();
			lbShabSrc.setData(MainForm.tcl.getShPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu,  null));
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
			
			tblPos.setData(MainForm.tcl.getPvizitAmb(zapVr.getId_pvizit()));
			if (tblPos.getRowCount() > 0)
				tblPos.setRowSelectionInterval(tblPos.getRowCount() - 1, 0);
			tblDiag.setData(MainForm.tcl.getPdiagAmb(zapVr.getId_pvizit()));
			if (tblDiag.getRowCount() > 0)
				tblDiag.setRowSelectionInterval(tblDiag.getRowCount() - 1, 0);
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
		tbZaklRek.setText("");

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
				tbZaklRek.setText(st.text);
				break;
			default:
				break;
			}
		}
		
		addDiag(new StringClassifier(sh.diag, MainForm.conMan.getNameFromPcodString(StringClassifiers.n_c00, sh.diag.trim())));
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
	}
	
	private boolean checkInput() throws TException {
		try {
			if (tblPos.getData().size() > 0)
				if (tblPos.getData().get(tblPos.getData().size() - 1).datap == getDateMills(System.currentTimeMillis()))
					if (MainForm.tcl.isZapVrNext(zapVr.id_pvizit))
						return true;
					else if (!MainForm.tcl.getPvizit(zapVr.id_pvizit).isSetIshod())
						return false;
				if ((tblPos.getData().size() > 0)&&(tblDiag.getData().size()==0)) 
						return false;
		} catch (KmiacServerException | PvizitNotFoundException e) {
			return false;
		}
		
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
				diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
				diagamb.setDiag(mkb.pcod);
				diagamb.setNamed(mkb.name);
	 			tblDiag.addItem(diagamb);
  			}
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
	}
		
	private boolean checkCmb(ThriftIntegerClassifierCombobox cmb) throws TException {
			if (tblPos.getData().size() > 0){
				if (cmb.getSelectedPcod()==null)
					return false;
				else return true;
					}
		return true;
	}
}
