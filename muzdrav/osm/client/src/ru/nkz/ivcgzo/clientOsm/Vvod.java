package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractCellEditor;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.thrift.TException;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.TableComboBoxIntegerEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.TableComboBoxStringEditor;
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
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;
import ru.nkz.ivcgzo.thriftOsm.Shablon;
import ru.nkz.ivcgzo.thriftOsm.ShablonText;
import ru.nkz.ivcgzo.thriftOsm.SpravNetrud;
import ru.nkz.ivcgzo.thriftOsm.VrachInfo;
import ru.nkz.ivcgzo.thriftOsm.Vypis;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

public class Vvod extends JFrame {
	private static final long serialVersionUID = 4761424994673488103L;
	private JTabbedPane tabbedPane;
	private CustomTable<PvizitAmb, PvizitAmb._Fields> tblPos;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbVidOpl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbCelObr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbRez;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbMobs;
	private CustomTextField tbShabSrc;
	private JButton btnShabSrc;
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
	private CustomDateEditor tfPlanDatIssl;
	private CustomTextField tbNaprKab;
	private JLabel lblZaklRek;
	private JScrollPane spZaklRek;
	private JTextArea tbZaklRek;
	private JLabel lblZakl;
	private JScrollPane spZakl; 
	private JTextArea tbZakl;
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
	private JButton btnSearch;
	private JButton btnAnam;
	private JButton btnProsm;
	private JButton btnDispHron;
	private JCheckBox chbDiagBoe;
	private JCheckBox chbDiagInv;
	private JCheckBox chbDiagBer;
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
	private JTextArea tbJal;
	private CustomTextField tbStatAd;
	private CustomTextField tbAdDist;
	private CustomTextField tbStatTemp;
	private CustomTextField tbStatChss;
	private CustomTextField tbStatRost;
	private CustomTextField tbStatVes;
	private JTextArea tbLoc;
	private JTextArea tbFiz;
	private JTextArea tbOcen;
	private JTextArea tbStat;
	private JTextArea tbRecom;
	private JTextArea tbAnam;
	private JTextArea tbLech;
	
	private List<IntegerClassifier> listVidIssl;
	public static ZapVr zapVr;
	public static PvizitAmb pvizitAmb;
	private Priem priem;
	private AnamZab anamZab;
	public static Pvizit pvizit;
	private PdiagAmb diagamb;
	public static PdiagZ pdiag;
	public static Pdisp pdisp;
	private int idPvizitMainForm;
	private boolean isStat;
	
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
	private static final String lineSep = System.lineSeparator();
	private JComboBox<String> cmbConsVidNapr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbVidStacionar;
	private JTextArea tbKonsObosnov;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbKonsMesto;
	private JLabel lblVidStacionar;
	private JLabel lblKonsMesto;
	public CustomTable<Pvizit, Pvizit._Fields> tblObr;
	private JButton btnObrDel;
	private JButton btnObrSave;
	private JButton btnObrAdd;
	private JButton btnNaprPrint;
	private JButton btnKonsPrint;
	private CustomTable<PdiagZ, PdiagZ._Fields> tblZaklDiag;
	private JButton btnControl;
	private JButton btnBolList;
	private JPanel pnlDinBer;
	private JPanel pnlPlod;
	private JTextField tbChssPlod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbPolojPlod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbPredlejPlod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbCerdcebPlod1;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cmbSerdcebPlod2;
	private JPanel pnlOtek;
	private JLabel lblOtek;
	private int age;
	


	
	/**
	 * Create the dialog.
	 */
	public Vvod(final boolean isStat) {
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
					
					if (!checkSameDatePos()) {
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
					
					if (!isStat) {
						MainForm.instance.updateZapList();
						MainForm.instance.setVisible(true);
					} else {
						MainForm.instance.close();
					}
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
		
		btnAnam = new JButton("Анамнез жизни");
		btnAnam.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAnam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainForm.conMan.showPatientAnamnezForm(zapVr.npasp);
			}
		});
		
		JPanel panel = new JPanel();
		
		JPanel pnlTalon = new JPanel();
		pnlTalon.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlTalon.setBorder(new TitledBorder(null, "Талон пациента", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		
		btnProsm = new JButton("Просмотр");
		btnProsm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.conMan.showPatientInfoForm(String.format("Просмотр информации на пациента %s %s %s", zapVr.fam, zapVr.im, zapVr.oth), zapVr.npasp);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(Vvod.this, String.format("Не удалось отобразить форму просмотра%s%s", System.lineSeparator(), e1.toString()), "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnBer = new JButton("Наблюдение за беременными");
		btnBer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		       		postber.setVisible(true);	
			}
	});
		btnPrint = new JButton("Печатные формы");
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            JPopupMenu menu = new JPopupMenu();
	            
	       		JMenuItem mi1 = new JMenuItem("Вкладыш в карту");
	       		mi1.addActionListener(new ActionListener() {
	       			@Override
	       			public void actionPerformed(ActionEvent arg0) {
	       				try{
       						String servPath = MainForm.tcl.printProtokol(Vvod.zapVr.getNpasp(),MainForm.authInfo.getUser_id(),tblPos.getSelectedItem().id_obr,tblPos.getSelectedItem().id,MainForm.authInfo.getCpodr(),MainForm.authInfo.getClpu(),tblPos.getSortedRowIndex());
       						String cliPath = MainForm.conMan.createReport("вкладыш в амбулаторную карту");
       						MainForm.conMan.transferFileFromServer(servPath, cliPath);
       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
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
	       		
	       		JMenuItem mi5 = new JMenuItem("Анамнез заболевания");
	       		mi5.addActionListener(new ActionListener() {
	       			@Override
	       			public void actionPerformed(ActionEvent arg0) {
	       				try{
							String servPath = MainForm.tcl.printAnamZab(tblPos.getSelectedItem().id_obr);
							String cliPath = MainForm.conMan.createReport("Anamnesis morbi");
							MainForm.conMan.transferFileFromServer(servPath, cliPath);
       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
					}
	       					
					catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

	       			}
	       		});
	       		menu.add(mi5);
	       		
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
							String cliPath = MainForm.conMan.createReport("Выписка из амбулаторной карты");
							MainForm.conMan.transferFileFromServer(servPath, cliPath);
       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
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
								String cliPath = MainForm.conMan.createReport("Заключение КЭК");
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
	       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
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
								String cliPath = MainForm.conMan.createReport("направление на МСЭК");
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
	       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
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
				
				JMenuItem mi6 = new JMenuItem("Справка о временной нетрудоспособности");
				mi6.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
							SpravNetrud spr = new SpravNetrud();
							spr.setFam(Vvod.zapVr.getFam());
							spr.setIm(Vvod.zapVr.getIm());
							spr.setOth(Vvod.zapVr.getOth());
							spr.setDatar(Vvod.zapVr.getDatar());
							spr.setNpasp(Vvod.zapVr.getNpasp());
							for (PdiagAmb pd : tblDiag.getData())
					  			if (pd.diag_stat == 1) {
					  				pvizitAmb.setDiag(pd.getDiag());
					  				spr.setDiag(pd.diag);}
							spr.setUserId(MainForm.authInfo.user_id);
							spr.setCpodr_name(MainForm.authInfo.getCpodr_name());
							spr.setClpu_name(MainForm.authInfo.getClpu_name());
       						String servPath = MainForm.tcl.printSpravNetrud(spr);
       						String cliPath = MainForm.conMan.createReport("справка о временной нетрудоспособности");
       						MainForm.conMan.transferFileFromServer(servPath, cliPath);
       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
						}
						catch (TException e1) {
							e1.printStackTrace();
							MainForm.conMan.reconnect(e1);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				menu.add(mi6);
				
				JMenuItem mi7 = new JMenuItem("Справка в бассейн");
				mi7.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try{
								String servPath = MainForm.tcl.printSprBass(zapVr.getNpasp(), zapVr.getPol());
								String cliPath = MainForm.conMan.createReport("справка в бассейн");
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
	       						MainForm.conMan.openFileInTextProcessor(cliPath, false);
						}
						catch (TException e1) {
							e1.printStackTrace();
							MainForm.conMan.reconnect(e1);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				menu.add(mi7);
				
				menu.show(btnPrint, 0, btnPrint.getHeight());
			}
		});
		
		btnRecPriem = new JButton("Запись на прием");
		btnRecPriem.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
		
		btnSearch = new JButton("Поиск");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int[] res = MainForm.conMan.showPatientSearchForm("Поиск пациентов", false, true);
					
					if (res != null) {
						showVvod(MainForm.tcl.getZapVrSrc(res[0]));
					}
				} catch (KmiacServerException e1) {
					JOptionPane.showMessageDialog(Vvod.this, "Не удалось загрузить информацию на пациента.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(Vvod.this, "Не удалось отобразить форму поиска.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnControl = new JButton("Контроль");
		btnControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] res = MainForm.conMan.showMedPolErrorsForm();
				
				if (res != null) {
					try {
						showVvod(MainForm.tcl.getZapVrSrc(res[0]));
						
						for (int i = 0; i < tblObr.getRowCount(); i++) {
							if (tblObr.getData().get(i).id == res[1]) {
								tblObr.setRowSelectionInterval(i, i);
								for (int j = 0; j < tblPos.getRowCount(); j++) {
									if (tblPos.getData().get(j).id == res[2]) {
										tblPos.setRowSelectionInterval(j, j);
										return;
									}
								}
							}
						}
						JOptionPane.showMessageDialog(Vvod.this, "Посещения с такими данными не найдено.");
					} catch (KmiacServerException e1) {
						JOptionPane.showMessageDialog(Vvod.this, "Ошибка загрузки списка посещений.");
					} catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					}
				}
			}
		});
		btnControl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		btnBolList = new JButton("Бол.лист");
		btnBolList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblObr.getRowCount() !=0)
				 MainForm.conMan.showBolListForm(zapVr.npasp, zapVr.id_pvizit, 0);
				else JOptionPane.showMessageDialog(Vvod.this, "Таблица случаев обращений не заполнена");
			}
		});
		btnBolList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
						.addComponent(pnlTalon, GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnRecPriem, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAnam)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnProsm)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBer)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnPrint)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnControl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnBolList)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch)
						.addComponent(btnRecPriem)
						.addComponent(btnAnam)
						.addComponent(btnProsm)
						.addComponent(btnBer)
						.addComponent(btnPrint)
						.addComponent(btnControl)
						.addComponent(btnBolList))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlTalon, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
					.addGap(4))
		);
		
		shablonSearchListener = new ShablonSearchListener();
		
		tbShabSrc = new CustomTextField(true, true, false);
		tbShabSrc.getDocument().addDocumentListener(shablonSearchListener);
		tbShabSrc.setColumns(10);
		
		btnShabSrc = new JButton("...");
		btnShabSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shablonform.showShablonForm(tbShabSrc.getText(), lbShabSrc.getSelectedValue());
				syncShablonList(shablonform.getSearchString(), shablonform.getShablon());
				pasteShablon(shablonform.getShablon());
			}
		});
		
		JScrollPane spShabSrc = new JScrollPane();
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
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
		
		JPanel pnlOsm = new JPanel();
		tabbedPane.addTab("<html>Осмотр<br><br></html>", null, pnlOsm, null);
		tabbedPane.setTabComponentAt(0, new JLabel("<html>Осмотр<br><br></html>"));
		
		JScrollPane spOsm = new JScrollPane();
		spOsm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout gl_pnlOsm = new GroupLayout(pnlOsm);
		gl_pnlOsm.setHorizontalGroup(
			gl_pnlOsm.createParallelGroup(Alignment.LEADING)
				.addComponent(spOsm, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
		);
		gl_pnlOsm.setVerticalGroup(
			gl_pnlOsm.createParallelGroup(Alignment.LEADING)
				.addComponent(spOsm, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE)
		);
		
		JPanel pnlOsmOsm = new JPanel();
		spOsm.setViewportView(pnlOsmOsm);
		
		JPanel pnlRecom = new JPanel();
		pnlRecom.setBorder(new TitledBorder(null, "Рекомендации", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spRecom = new JScrollPane();
		GroupLayout gl_pnlRecom = new GroupLayout(pnlRecom);
		gl_pnlRecom.setHorizontalGroup(
			gl_pnlRecom.createParallelGroup(Alignment.LEADING)
				.addGap(0, 578, Short.MAX_VALUE)
				.addGap(0, 562, Short.MAX_VALUE)
				.addComponent(spRecom, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
		);
		gl_pnlRecom.setVerticalGroup(
			gl_pnlRecom.createParallelGroup(Alignment.LEADING)
				.addGap(0, 246, Short.MAX_VALUE)
				.addGap(0, 222, Short.MAX_VALUE)
				.addComponent(spRecom, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
		);
		
		tbRecom = new JTextArea();
		tbRecom.setWrapStyleWord(true);
		tbRecom.setLineWrap(true);
		tbRecom.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spRecom.setViewportView(tbRecom);
		pnlRecom.setLayout(gl_pnlRecom);
		
		JPanel pnlJal = new JPanel();
		pnlJal.setBorder(new TitledBorder(null, "Жалобы", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spJal = new JScrollPane();
		GroupLayout gl_pnlJal = new GroupLayout(pnlJal);
		gl_pnlJal.setHorizontalGroup(
			gl_pnlJal.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
				.addComponent(spJal, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);
		gl_pnlJal.setVerticalGroup(
			gl_pnlJal.createParallelGroup(Alignment.LEADING)
				.addGap(0, 45, Short.MAX_VALUE)
				.addComponent(spJal, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
		);
		pnlJal.setLayout(gl_pnlJal);
		
		JPanel pnlAnam = new JPanel();
		pnlAnam.setBorder(new TitledBorder(null, "История заболевания (anamnesis morbi)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spAnam = new JScrollPane();
		GroupLayout gl_pnlAnam = new GroupLayout(pnlAnam);
		gl_pnlAnam.setHorizontalGroup(
			gl_pnlAnam.createParallelGroup(Alignment.LEADING)
				.addGap(0, 578, Short.MAX_VALUE)
				.addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
		);
		gl_pnlAnam.setVerticalGroup(
			gl_pnlAnam.createParallelGroup(Alignment.LEADING)
				.addGap(0, 51, Short.MAX_VALUE)
				.addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
		);
		
		tbAnam = new JTextArea();
		tbAnam.setWrapStyleWord(true);
		tbAnam.setLineWrap(true);
		tbAnam.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spAnam.setViewportView(tbAnam);
		pnlAnam.setLayout(gl_pnlAnam);
		
		JPanel pnlStat = new JPanel();
		pnlStat.setBorder(new TitledBorder(null, "Объективный статус (status praesense)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spStat = new JScrollPane();
		
		JLabel lblStatTemp = new JLabel("Темп.");
		
		tbStatTemp = new CustomTextField();
		tbStatTemp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatTemp.setColumns(10);
		
		JLabel lblStatAd = new JLabel("АД сист.");
		
		tbStatAd = new CustomTextField();
		tbStatAd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatAd.setColumns(10);
		
		JLabel lblStatRost = new JLabel("Рост");
		
		tbStatRost = new CustomTextField();
		tbStatRost.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatRost.setColumns(10);
		
		JLabel lblStatVes = new JLabel("Вес");
		
		tbStatVes = new CustomTextField();
		tbStatVes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatVes.setColumns(10);
		
		JLabel lblStatChss = new JLabel("Чсс");
		
		tbStatChss = new CustomTextField();
		tbStatChss.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatChss.setColumns(10);
		
		JLabel lblAdDist = new JLabel("АД диаст.");
		
		tbAdDist = new CustomTextField();
		tbAdDist.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbAdDist.setColumns(10);
		GroupLayout gl_pnlStat = new GroupLayout(pnlStat);
		gl_pnlStat.setHorizontalGroup(
			gl_pnlStat.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlStat.createSequentialGroup()
					.addComponent(lblStatTemp)
					.addGap(4)
					.addComponent(tbStatTemp, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblStatAd, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbStatAd, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblAdDist)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbAdDist, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatRost)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(tbStatRost, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatVes, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbStatVes, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblStatChss)
					.addGap(10)
					.addComponent(tbStatChss, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
				.addComponent(spStat)
		);
		gl_pnlStat.setVerticalGroup(
			gl_pnlStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStat.createSequentialGroup()
					.addGroup(gl_pnlStat.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(3)
							.addComponent(lblStatTemp))
						.addComponent(tbStatTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbStatAd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlStat.createParallelGroup(Alignment.BASELINE)
							.addComponent(tbStatVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblStatVes))
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(3)
							.addComponent(lblStatChss))
						.addComponent(tbStatChss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(2)
							.addComponent(lblStatAd))
						.addGroup(gl_pnlStat.createParallelGroup(Alignment.BASELINE)
							.addComponent(tbStatRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblStatRost))
						.addComponent(tbAdDist, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlStat.createSequentialGroup()
							.addGap(2)
							.addComponent(lblAdDist)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spStat, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
		);
		
		tbStat = new JTextArea();
		tbStat.setWrapStyleWord(true);
		tbStat.setLineWrap(true);
		tbStat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spStat.setViewportView(tbStat);
		pnlStat.setLayout(gl_pnlStat);
		
		JPanel pnlFiz = new JPanel();
		pnlFiz.setBorder(new TitledBorder(null, "Физикальное обследование", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spFiz = new JScrollPane();
		GroupLayout gl_pnlFiz = new GroupLayout(pnlFiz);
		gl_pnlFiz.setHorizontalGroup(
			gl_pnlFiz.createParallelGroup(Alignment.LEADING)
				.addGap(0, 578, Short.MAX_VALUE)
				.addGap(0, 562, Short.MAX_VALUE)
				.addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
		);
		gl_pnlFiz.setVerticalGroup(
			gl_pnlFiz.createParallelGroup(Alignment.LEADING)
				.addGap(0, 51, Short.MAX_VALUE)
				.addGap(0, 27, Short.MAX_VALUE)
				.addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
		);
		
		tbFiz = new JTextArea();
		tbFiz.setWrapStyleWord(true);
		tbFiz.setLineWrap(true);
		tbFiz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spFiz.setViewportView(tbFiz);
		pnlFiz.setLayout(gl_pnlFiz);
		
		JPanel pnlLoc = new JPanel();
		pnlLoc.setBorder(new TitledBorder(null, "Локальный статус (localis status)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spLoc = new JScrollPane();
		GroupLayout gl_pnlLoc = new GroupLayout(pnlLoc);
		gl_pnlLoc.setHorizontalGroup(
			gl_pnlLoc.createParallelGroup(Alignment.LEADING)
				.addGap(0, 578, Short.MAX_VALUE)
				.addGap(0, 562, Short.MAX_VALUE)
				.addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
		);
		gl_pnlLoc.setVerticalGroup(
			gl_pnlLoc.createParallelGroup(Alignment.LEADING)
				.addGap(0, 51, Short.MAX_VALUE)
				.addGap(0, 27, Short.MAX_VALUE)
				.addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
		);
		
		tbLoc = new JTextArea();
		tbLoc.setWrapStyleWord(true);
		tbLoc.setLineWrap(true);
		tbLoc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spLoc.setViewportView(tbLoc);
		pnlLoc.setLayout(gl_pnlLoc);
		
		JPanel pnlLech = new JPanel();
		pnlLech.setBorder(new TitledBorder(null, "Лечение", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spLech = new JScrollPane();
		GroupLayout gl_pnlLech = new GroupLayout(pnlLech);
		gl_pnlLech.setHorizontalGroup(
			gl_pnlLech.createParallelGroup(Alignment.LEADING)
				.addGap(0, 578, Short.MAX_VALUE)
				.addGap(0, 562, Short.MAX_VALUE)
				.addComponent(spLech, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
		);
		gl_pnlLech.setVerticalGroup(
			gl_pnlLech.createParallelGroup(Alignment.LEADING)
				.addGap(0, 51, Short.MAX_VALUE)
				.addGap(0, 27, Short.MAX_VALUE)
				.addComponent(spLech, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
		);
		
		tbLech = new JTextArea();
		tbLech.setWrapStyleWord(true);
		tbLech.setLineWrap(true);
		tbLech.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spLech.setViewportView(tbLech);
		pnlLech.setLayout(gl_pnlLech);
		
		JPanel pnlOcen = new JPanel();
		pnlOcen.setBorder(new TitledBorder(null, "Оценка данных анамнеза", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JScrollPane spOcen = new JScrollPane();
		GroupLayout gl_pnlOcen = new GroupLayout(pnlOcen);
		gl_pnlOcen.setHorizontalGroup(
			gl_pnlOcen.createParallelGroup(Alignment.LEADING)
				.addGap(0, 578, Short.MAX_VALUE)
				.addGap(0, 562, Short.MAX_VALUE)
				.addComponent(spOcen, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
		);
		gl_pnlOcen.setVerticalGroup(
			gl_pnlOcen.createParallelGroup(Alignment.LEADING)
				.addGap(0, 51, Short.MAX_VALUE)
				.addGap(0, 27, Short.MAX_VALUE)
				.addComponent(spOcen, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
		);
		
		tbOcen = new JTextArea();
		tbOcen.setWrapStyleWord(true);
		tbOcen.setLineWrap(true);
		tbOcen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spOcen.setViewportView(tbOcen);
		pnlOcen.setLayout(gl_pnlOcen);
		GroupLayout gl_pnlOsmOsm = new GroupLayout(pnlOsmOsm);
		gl_pnlOsmOsm.setHorizontalGroup(
			gl_pnlOsmOsm.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlOsmOsm.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlOsmOsm.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlJal, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addComponent(pnlRecom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addComponent(pnlAnam, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addComponent(pnlStat, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlFiz, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addComponent(pnlLoc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addComponent(pnlLech, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
						.addComponent(pnlOcen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlOsmOsm.setVerticalGroup(
			gl_pnlOsmOsm.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOsmOsm.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlJal, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlAnam, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlStat, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlFiz, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlLoc, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlLech, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlOcen, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlRecom, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlOsmOsm.setLayout(gl_pnlOsmOsm);
		
		tbJal = new JTextArea();
		tbJal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbJal.setLineWrap(true);
		tbJal.setWrapStyleWord(true);
		spJal.setViewportView(tbJal);
		pnlOsm.setLayout(gl_pnlOsm);
		
		JPanel pnlDiag = new JPanel();
		tabbedPane.addTab("<html>Диагноз<br><br></html>", null, pnlDiag, null);
		tabbedPane.setTabComponentAt(1, new JLabel("<html>Диагноз<br><br></html>"));
		
		JPanel PnlDiag = new JPanel();
		
		JScrollPane spDiag = new JScrollPane();
		
		JButton btnDiagAdd = new JButton("");
		btnDiagAdd.setToolTipText("Добавление новой записи");
		btnDiagAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	  			if ((tblPos.getSelectedItem() != null) && (tblObr.getSelectedItem() != null))
	  				addDiag(MainForm.conMan.showMkbTreeForm("Диагноз", ""));
			}
		});
		btnDiagAdd.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		
		final JButton btnDiagDel = new JButton("");
		btnDiagDel.setToolTipText("Удаление записи");
		btnDiagDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
					if (tblDiag.getSelectedItem() != null)
						if (JOptionPane.showConfirmDialog(Vvod.this, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
				  			MainForm.tcl.DeletePdiagAmb(tblDiag.getSelectedItem().getId());
				  			MainForm.tcl.deleteDiag(tblDiag.getSelectedItem().getNpasp(), tblDiag.getSelectedItem().getDiag(), MainForm.authInfo.getCpodr(), tblDiag.getSelectedItem().id);
				  			tblDiag.setData(MainForm.tcl.getPdiagAmb(tblPos.getSelectedItem().id));
							tblZaklDiag.setData(MainForm.tcl.getPdiagZInfo(zapVr.npasp));
						}
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
					if (tblDiag.getSelectedItem() != null) {
				  		if (tbDiagOpis.getText().length() == 0) {
				  			JOptionPane.showMessageDialog(Vvod.this, "Введите описание диагноза");
				  			return;
				  		}
				  		for (PdiagAmb da : tblDiag.getData()) {
							if (da != diagamb) {
								if (da.diag.equals(diagamb.diag)) {
						  			JOptionPane.showMessageDialog(Vvod.this, String.format("В списке есть одинаковые диангозы: %s", da.diag));
						  			return;
								}
							}
						}
				  		diagamb.setDiag(tblDiag.getSelectedItem().getDiag());
				  		diagamb.setNamed(getTextOrNull(tbDiagOpis.getText()));
				  		diagamb.setId_pos(pvizitAmb.getId());
				  		if (rbtDiagOsn.isSelected()) diagamb.setDiag_stat(1);
				  		if (rbtDiagSop.isSelected())diagamb.setDiag_stat(3);
				  		if (rbtDiagOsl.isSelected()) diagamb.setDiag_stat(2);
				  		if (cmbDiagObstReg.getSelectedPcod() != null) diagamb.setObstreg(cmbDiagObstReg.getSelectedPcod()); else diagamb.unsetObstreg();
				  		if (cmbDiagVidTr.getSelectedPcod() != null) diagamb.setVid_tr(cmbDiagVidTr.getSelectedPcod()); else diagamb.unsetVid_tr();
				  		
				  		pdiag = new PdiagZ();
				  		pdisp = new Pdisp();
			  			//pdiag.setId_diag_amb(diagamb.id);
				  		if (rbtDiagPredv.isSelected())
				  			diagamb.setPredv(true);
				  		if (rbtDiagZakl.isSelected()) {
				  			diagamb.setPredv(false);
				  			pdiag.setNpasp(diagamb.getNpasp());
				  			pdiag.setDiag(diagamb.getDiag());
				  			pdiag.setNmvd(diagamb.getObstreg());
				  			pdiag.setNamed(diagamb.getNamed());
				  			pdiag.setDatad(diagamb.getDatad());
				  			if (rbtInvUst1.isSelected())
				  				pdiag.setPpi(1);
				  			if (rbtInvUst2.isSelected())
				  				pdiag.setPpi(2);
				  			if (rbtDiagHarOstr.isSelected())
				  				pdiag.setXzab(1);
				  			if (rbtDiagHarHron.isSelected())
				  				pdiag.setXzab(2);
				  			if (rbtDiagStadPoz.isSelected())
				  				pdiag.setStady(2);
				  			if (rbtDiagStadRan.isSelected())
				  				pdiag.setStady(1);
				  			if (chbDiagBer.isSelected())
				  				pdiag.setPat(1);
				  			if (chbDiagBoe.isSelected())
				  				pdiag.setPrizb(1);
				  			if (chbDiagInv.isSelected())
				  				pdiag.setPrizi(1);
				  			if (tbDiagDispDatVz.getDate() != null)
				  				pdiag.setDisp(1);
					  		setPdiagz(pdiag);
				  		}
				  		
			  			MainForm.tcl.UpdatePdiagAmb(diagamb);
				  		
				  		for (PdiagAmb pd : tblDiag.getData())
				  			if (pd.diag_stat == 1) {
				  				pvizitAmb.setDiag(pd.getDiag());
				  				MainForm.tcl.UpdatePvizitAmb(pvizitAmb);}
				  		
			  			if (tbDiagDispDatVz.getDate() != null) {
				  			pdisp.setNpasp(diagamb.getNpasp());
				  			pdisp.setDiag(diagamb.getDiag());
				  			pdisp.setPcod(MainForm.authInfo.getCpodr());
							pdisp.setD_vz(tbDiagDispDatVz.getDate().getTime());
				  			if (tbDiagDispDatIsh.getDate() != null)
								pdisp.setDataish(tbDiagDispDatIsh.getDate().getTime());
				  			if (cmbDiagDispIsh.getSelectedPcod() != null)
				  				pdisp.setIshod(cmbDiagDispIsh.getSelectedPcod()); else pdisp.unsetIshod();
					  		if (cmbDiagDispGrup.getSelectedPcod() != null)
					  			pdisp.setD_grup(cmbDiagDispGrup.getSelectedPcod()); else pdisp.unsetD_grup();
		
		
				  			pdisp.setCod_sp(diagamb.getCod_sp());
				  			pdisp.setCdol_ot(diagamb.getCdol());
				  			pdisp.setD_uch(Integer.valueOf(tfNuch.getText()));
				  			pdisp.setDiag_n(tfNewDs.getText());
				  			MainForm.tcl.setPdisp(pdisp);
				  			if (!tfNewDs.isEmpty() && !tfNewDs.getText().equals(diagamb.diag)) {
				  				diagamb.setDiag(tfNewDs.getText());
				  				diagamb.setId_obr(zapVr.getId_pvizit());
						  		diagamb.setNpasp(zapVr.getNpasp());
						  		for (PdiagZ pd : tblZaklDiag.getData()){
						  			if (pd.getDiag().equals(tfNewDs.getText())) 
						  				diagamb.setDatad(pdiag.getDatad());
					  				else
					  					diagamb.setDatad(System.currentTimeMillis());
						  		}
						  		if (!isStat) {
							  		diagamb.setCod_sp(MainForm.authInfo.pcod);
							  		diagamb.setCdol(MainForm.authInfo.cdol);
						  		} else {
							  		diagamb.setCod_sp(pvizitAmb.cod_sp);
							  		diagamb.setCdol(pvizitAmb.cdol);
						  		}
						  		diagamb.setPredv(false);
						  		diagamb.setDiag_stat(1);
								diagamb.setNamed(diag_named);
								diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
								pdiag.setDiag(tfNewDs.getText());
						  		setPdiagz(pdiag);
				  				pdisp.setDiag_s(pdisp.diag);
				  				pdisp.setDiag(tfNewDs.getText());
				  				pdisp.setDiag_n(null);
				  				pdisp.setDatad(tfDataIzmNewDs.getDate().getTime());
				  				MainForm.tcl.setPdisp(pdisp); 
				  				tblDiag.setData(MainForm.tcl.getPdiagAmb(tblPos.getSelectedItem().id));
				  			}
				  			/*pdiag*/
				  			if (tbDiagDispDatVz.getDate() != null)
				  				pdiag.setD_vz(pdisp.getD_vz());
				  			if (tbDiagDispDatIsh.getDate() != null)
				  				pdiag.setDataish(pdisp.getDataish());
					  		pdiag.setIshod(pdisp.getIshod());
					  		pdiag.setD_grup(pdisp.getD_grup());
					  		setPdiagz(pdiag);
		  				}
						tblZaklDiag.setData(MainForm.tcl.getPdiagZInfo(zapVr.npasp));
					}
			  	} catch (KmiacServerException e1) {
			  		e1.printStackTrace();
			  	} catch (TException e1) {
			  		MainForm.conMan.reconnect(e1);
			  	}
			}
		});
		btnDiagSave.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		
		JLabel label = new JLabel("Диагнозы посещения");
		
		JLabel label_1 = new JLabel("Заключительные диагнозы");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_PnlDiag = new GroupLayout(PnlDiag);
		gl_PnlDiag.setHorizontalGroup(
			gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PnlDiag.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addComponent(spDiag, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDiagDel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDiagAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDiagSave, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_PnlDiag.createSequentialGroup()
							.addGap(22)
							.addComponent(label_1)
							.addContainerGap(103, Short.MAX_VALUE))
						.addGroup(gl_PnlDiag.createSequentialGroup()
							.addGap(18)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_PnlDiag.setVerticalGroup(
			gl_PnlDiag.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_PnlDiag.createSequentialGroup()
					.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_PnlDiag.createSequentialGroup()
							.addGap(5)
							.addComponent(label_1)
							.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_PnlDiag.createSequentialGroup()
									.addGap(48)
									.addComponent(btnDiagDel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDiagSave)
									.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE))
								.addGroup(gl_PnlDiag.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane_1, 0, 0, Short.MAX_VALUE))))
						.addGroup(gl_PnlDiag.createSequentialGroup()
							.addGap(3)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_PnlDiag.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDiagAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addComponent(spDiag, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))))
					.addContainerGap())
		);
		
		tblZaklDiag = new CustomTable<>(false, false, PdiagZ.class, 2, "Диагноз");
		tblZaklDiag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (e.getClickCount() == 2)
						if ((tblZaklDiag.getSelectedItem().diag != null) && (tblPos.getSelectedItem() != null) && (tblObr.getSelectedItem() != null)) {
							for (PdiagAmb pa : tblDiag.getData()) {
								if (tblZaklDiag.getSelectedItem().diag.equals(pa.diag))
									return;
							}
							diagamb = new PdiagAmb();
		 			  		diagamb.setId_obr(zapVr.getId_pvizit());
		 			  		diagamb.setId_pos(tblPos.getSelectedItem().id);
		 			  		diagamb.setNpasp(zapVr.getNpasp());
		 			  		if (tblZaklDiag.getSelectedItem().isSetDatad())
		 			  			diagamb.setDatad(tblZaklDiag.getSelectedItem().getDatad());
		 			  		else
		 			  			diagamb.setDatad(System.currentTimeMillis());
					  		if (!isStat) {
						  		diagamb.setCod_sp(MainForm.authInfo.pcod);
						  		diagamb.setCdol(MainForm.authInfo.cdol);
					  		} else {
						  		diagamb.setCod_sp(pvizitAmb.cod_sp);
						  		diagamb.setCdol(pvizitAmb.cdol);
					  		}
		 			  		diagamb.setPredv(false);
		 			  		diagamb.setDiag_stat(1);
		 					diagamb.setDiag(tblZaklDiag.getSelectedItem().getDiag());
		 					diagamb.setNamed(tblZaklDiag.getSelectedItem().getNamed());
							diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
							tblDiag.addItem(diagamb);
						}
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});

		tblZaklDiag.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(tblZaklDiag);
		
		tblDiag = new CustomTable<>(true, false, PdiagAmb.class, 7, "Дата установления диагноза", 3, "Код МКБ");
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
				if (!e.getValueIsAdjusting()) {
					diagamb = new PdiagAmb();
					pdiag = new PdiagZ();
					pdisp = new Pdisp();
					if (tblDiag.getSelectedItem() != null) {
						diagamb = tblDiag.getSelectedItem();
							
						try {
							pdiag = MainForm.tcl.getPdiagZ(zapVr.npasp, diagamb.diag);
							pdisp = MainForm.tcl.getPdisp(zapVr.getNpasp(),tblDiag.getSelectedItem().diag,MainForm.authInfo.getCpodr());
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (PdiagNotFoundException e1) {
							System.out.println("diagZ not found");
						} catch (PdispNotFoundException e1) {
							System.out.println("disp not found");
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
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
		spDiagOpis.setMinimumSize(new Dimension(48, 48));
		
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
			gl_pnlDiagPredv.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlDiagPredv.createSequentialGroup()
					.addComponent(rbtDiagPredv, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtDiagZakl, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
					.addGap(0))
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
						.addComponent(PnlDiag, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_pnlDiag.createSequentialGroup()
							.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDiag.createSequentialGroup()
									.addComponent(lblDiagVidTr, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbDiagVidTr, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblDiagObstReg)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbDiagObstReg, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE))
								.addGroup(gl_pnlDiag.createSequentialGroup()
									.addComponent(pnlDiagStadZab, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pnlDiagHarZab, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
								.addGroup(gl_pnlDiag.createSequentialGroup()
									.addComponent(pnlDiagStat, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pnlDiagPredv, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
									.addGap(3))
								.addGroup(gl_pnlDiag.createSequentialGroup()
									.addComponent(lblDiagOpis, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(spDiagOpis, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)))
							.addGap(10))
						.addGroup(Alignment.TRAILING, gl_pnlDiag.createSequentialGroup()
							.addComponent(pnlDiagDisp, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, gl_pnlDiag.createSequentialGroup()
					.addGap(14)
					.addComponent(chbDiagInv, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chbDiagBoe, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chbDiagBer, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_pnlDiag.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlInvUst, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(405, Short.MAX_VALUE))
		);
		gl_pnlDiag.setVerticalGroup(
			gl_pnlDiag.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiag.createSequentialGroup()
					.addGap(1)
					.addComponent(PnlDiag, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDiagOpis)
						.addComponent(spDiagOpis, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlDiagDisp, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiag.createParallelGroup(Alignment.BASELINE)
						.addComponent(chbDiagInv)
						.addComponent(chbDiagBoe)
						.addComponent(chbDiagBer))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlInvUst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(24))
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
		
		btnDispHron = new JButton("<html>Дисп.<br>набл.</html>");
		btnDispHron.setHorizontalTextPosition(SwingConstants.LEADING);
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
							.addComponent(tbDiagDispDatVz, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDiagDispGrup)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbDiagDispGrup, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 41, Short.MAX_VALUE))
						.addGroup(gl_pnlDiagDisp.createSequentialGroup()
							.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addComponent(lblDiagDispIsh)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbDiagDispIsh, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
									.addComponent(lblDiagDispDatIsh, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addComponent(lblNuch)
									.addGap(10)
									.addComponent(tfNuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblNewDs)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblDataIzmNewDs)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDataIzmNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addGap(1)
									.addComponent(btnDispHron, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
								.addGroup(gl_pnlDiagDisp.createSequentialGroup()
									.addGap(18)
									.addComponent(tbDiagDispDatIsh, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))))
					.addGap(0))
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
					.addGroup(gl_pnlDiagDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfNuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDispHron, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewDs)
						.addComponent(tfNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataIzmNewDs)
						.addComponent(tfDataIzmNewDs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNuch))
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
					.addComponent(rbtDiagOsn, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtDiagSop, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDiagOsl, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
					.addGap(0))
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
		tbDiagOpis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spDiagOpis.setViewportView(tbDiagOpis);
		pnlDiag.setLayout(gl_pnlDiag);
		
		JPanel pnlLabIssl = new JPanel();
		tabbedPane.addTab("<html>Лабораторно-диаг-<br>ностические иссл.</html>", null, pnlLabIssl, null);
		tabbedPane.setTabComponentAt(2, new JLabel("<html>Лабораторно-диаг-<br>ностические иссл.</html>"));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_pnlLabIssl = new GroupLayout(pnlLabIssl);
		gl_pnlLabIssl.setHorizontalGroup(
			gl_pnlLabIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLabIssl.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_1, GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE))
		);
		gl_pnlLabIssl.setVerticalGroup(
			gl_pnlLabIssl.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlLabIssl.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 479, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		
		JPanel pnlIssl = new JPanel();
		pnlIssl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabbedPane_1.addTab("Направление на исследование", null, pnlIssl, null);
		
		cmbNaprMesto = new ThriftIntegerClassifierCombobox<>(true);
		cmbNaprMesto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cmbNaprMesto.getSelectedItem() != null)
						cmbOrgan.setData(MainForm.tcl.get_n_nz1(cmbNaprMesto.getSelectedItem().pcod));
						tblNaprPokazMet.setData(MainForm.tcl.getPokazMet("", 0));

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
					if (cmbOrgan.getSelectedItem()!= null){
						tblNaprPokazMet.setData(MainForm.tcl.getPokazMet(cmbOrgan.getSelectedItem().pcod, cmbNaprMesto.getSelectedItem().pcod));
						if ((cmbOrgan.getSelectedPcod().equals("B03.016.02")) || (cmbOrgan.getSelectedPcod().equals("B03.016.03"))  || (cmbOrgan.getSelectedPcod().equals("B03.016.04")) || (cmbOrgan.getSelectedPcod().equals("B03.016.05")) || (cmbOrgan.getSelectedPcod().equals("B03.016.06")) || (cmbOrgan.getSelectedPcod().equals("B03.016.10")))
						 	for (PokazMet pokazMet : tblNaprPokazMet.getData()) 
									pokazMet.setVybor(true);
						
					}
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
		
		btnNaprPrint = new JButton("Печать");
		btnNaprPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

							try {
								IsslMet isslmet = new IsslMet();
							isslmet.setPvizitId(tblPos.getSelectedItem().getId_obr());
							isslmet.setPvizitambId(tblPos.getSelectedItem().getId());
							isslmet.setUserId(MainForm.authInfo.getUser_id());
							isslmet.setNpasp(Vvod.zapVr.getNpasp());
							if (cmbNaprMesto.getSelectedItem() != null) {
								isslmet.setMesto(cmbNaprMesto.getSelectedItem().getName());
								isslmet.setKod_lab(cmbNaprMesto.getSelectedItem().getPcod());
							}
							isslmet.setKab(getTextOrNull(tbNaprKab.getText()));
							isslmet.setClpu(cmbLpu.getSelectedPcod());
							isslmet.setClpu_name(cmbLpu.getSelectedItem().getName());
							isslmet.setCpodr_name(MainForm.authInfo.getCpodr_name());
							isslmet.setCpodr(MainForm.authInfo.getCpodr());
							
							String servPath;
							
								servPath = MainForm.tcl.printIsslMetod(isslmet);
								String cliPath;
								cliPath = MainForm.conMan.createReport("направление на исследование");
							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
       						MainForm.conMan.openFileInTextProcessor(cliPath, false);

							} catch (KmiacServerException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (TException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							 catch (IOException e1) {
								// TODO Auto-generated catch block
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
		
		JLabel lblPlanDatIssl = new JLabel("Планируемая дата выполнения");
		
		tfPlanDatIssl = new CustomDateEditor();
		tfPlanDatIssl.setColumns(10);
		
		JButton btnNaprSave = new JButton("Сохранить");
		btnNaprSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					if ((cmbOrgan.getSelectedItem() != null) ) {
						P_isl_ld pisl = new P_isl_ld();
						Prez_d prezd = new Prez_d();
						Prez_l prezl = new Prez_l();
						
						for (PdiagAmb pd : tblDiag.getData()) {
							if ((pd.diag_stat == 1) || (pd.diag_stat == 3)) {
								pisl.setDiag(pd.diag);
								break;
							}
						}
						if (!pisl.isSetDiag()) {
				  			JOptionPane.showMessageDialog(Vvod.this, "Запись на исследование не возможна, так как в посещении не выставлено ни одного основного диагноза.");
				  			return;
						}
						
						for (PokazMet pokazMet : tblNaprPokazMet.getData()) {
							if (pokazMet.vybor) {
								prezd.setKodisl(pokazMet.pcod);
								break;
							}
						}
						if (!prezd.isSetKodisl()) {
				  			JOptionPane.showMessageDialog(Vvod.this, "Выберите хотя бы одно исследование.");
				  			return;
						}
						if (tfPlanDatIssl.getDate() == null){
							JOptionPane.showMessageDialog(Vvod.this, "Планируемая дата выполнения исследования не заполнена.");
							return;
						}
						
						pisl.setNpasp(Vvod.zapVr.getNpasp());
						pisl.setPcisl(cmbOrgan.getSelectedPcod());
						pisl.setNapravl(2);
						pisl.setNaprotd(MainForm.authInfo.getCpodr());
						pisl.setDatan(System.currentTimeMillis());
						pisl.setVrach(MainForm.authInfo.getPcod());
						pisl.setDataz(System.currentTimeMillis());
						pisl.setPvizit_id(tblPos.getSelectedItem().getId_obr());
						pisl.setId_pos(tblPos.getSelectedItem().getId());
						pisl.setPrichina(pvizit.getCobr());
						pisl.setKodotd(cmbNaprMesto.getSelectedPcod());
						pisl.setVopl(cmbVidOpl.getSelectedPcod());
						if (tfPlanDatIssl.getDate() != null)
							pisl.setDatap(tfPlanDatIssl.getDate().getTime());
						
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
						} 
					}
				catch (KmiacServerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (TException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				treeRezIssl.setModel(new DefaultTreeModel(createNodes()));			
			}
		});
		GroupLayout gl_pnlIssl = new GroupLayout(pnlIssl);
		gl_pnlIssl.setHorizontalGroup(
			gl_pnlIssl.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNaprPokazMet, GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addGroup(gl_pnlIssl.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNaprVidIssl, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
								.addComponent(lblNaprMesto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLpu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlIssl.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cmbOrgan, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cmbNaprMesto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_pnlIssl.createSequentialGroup()
									.addGap(1)
									.addComponent(cmbLpu, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE))
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addComponent(lblPlanDatIssl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfPlanDatIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(lblNaprKab, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbNaprKab, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addComponent(spNaprPokazMet, GroupLayout.PREFERRED_SIZE, 613, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlIssl.createSequentialGroup()
							.addComponent(btnNaprSave)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNaprPrint, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlIssl.setVerticalGroup(
			gl_pnlIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlIssl.createSequentialGroup()
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
					.addComponent(spNaprPokazMet, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPlanDatIssl)
						.addComponent(tfPlanDatIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNaprKab)
						.addComponent(tbNaprKab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pnlIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNaprSave)
						.addComponent(btnNaprPrint))
					.addGap(19))
		);
		
		tblNaprPokazMet = new CustomTable<>(false, true, PokazMet.class, 1, "Наименование", 2, "Выбор");
		tblNaprPokazMet.setEditableFields(true, 1);
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
			gl_pnlRezIsslL.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRezIsslL.createSequentialGroup()
					.addContainerGap()
					.addComponent(spRezIssl, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlRezIsslL.setVerticalGroup(
			gl_pnlRezIsslL.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRezIsslL.createSequentialGroup()
					.addContainerGap()
					.addComponent(spRezIssl, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
					.addContainerGap())
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

		 		if (lastPath instanceof IsslInfoTreeNode) {
		 			IsslInfoTreeNode isslInfoTreeNode = (IsslInfoTreeNode) lastPath;
	 				P_isl_ld issl = isslInfoTreeNode.issl_lab;
					try {
						addLineToDetailInfo("Лаборатория", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_lds), issl.isSetKodotd(), issl.getKodotd()));
						addLineToDetailInfo("Дата направления", issl.isSetDatan(),DateFormat.getDateInstance().format(new Date(issl.getDatan())));
						addLineToDetailInfo("Дата поступления на исследование", issl.isSetDatap(),DateFormat.getDateInstance().format(new Date(issl.getDatap())));
						addLineToDetailInfo("Дата выполнения", issl.isSetDatav(),DateFormat.getDateInstance().format(new Date(issl.getDatav())));
						addLineToDetailInfo("Диагноз",issl.isSetDiag(), issl.getDiag());
						for (IsslInfo iinfo : MainForm.tcl.getIsslInfoPokaz(issl.getNisl())) {
							addLineToDetailInfo("Наименование показателя",iinfo.isSetPokaz_name(), iinfo.getPokaz_name());
							addLineToDetailInfo("Результат",iinfo.isSetRez(), iinfo.getRez());
							if (iinfo.getGruppa()==2)
							{
								addLineToDetailInfo("Описание исследования",iinfo.isSetOp_name(),iinfo.getOp_name());
								addLineToDetailInfo("Заключение",iinfo.isSetRez_name(),iinfo.getRez_name());

							}
							epTxtRezIssl.setText(sb.toString());
	}
					} catch (KmiacServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 		}	
	
//		 		if (lastPath instanceof IsslInfoTreeNode) {
//		 			IsslInfoTreeNode isslInfoNode = (IsslInfoTreeNode) lastPath;
//	 				IsslInfo iinfo = isslInfoNode.isslpokaz;
//						addLineToDetailInfo("id: ", iinfo.isSetId(), iinfo.getId());
//						addLineToDetailInfo("Наименование",iinfo.isSetPokaz_name(), iinfo.getPokaz_name());
//						addLineToDetailInfo("Результат",iinfo.isSetRez(), iinfo.getRez());
//						if (iinfo.getGruppa()==2)
//	 					{
//	 						addLineToDetailInfo("Описание исследования",iinfo.isSetOp_name(),iinfo.getOp_name());
//		 					addLineToDetailInfo("Заключение",iinfo.isSetRez_name(),iinfo.getRez_name());
//
//	 					}
//						epTxtRezIssl.setText(sb.toString());
//					
						

		 	}
		 });
		 treeRezIssl.addTreeExpansionListener(new TreeExpansionListener() {
		 	public void treeCollapsed(TreeExpansionEvent event) {
		 	}
		 	public void treeExpanded(TreeExpansionEvent event) {
//		 		Object lastPath = event.getPath().getLastPathComponent();
//		 		if (lastPath instanceof IsslInfoTreeNode) {
//		 			try {
//		 				IsslInfoTreeNode issl = (IsslInfoTreeNode) lastPath;
//		 				issl.removeAllChildren();
//						for (P_isl_ld pcisl : MainForm.tcl.getIsslInfoPokaz(issl.issl_lab.getNisl())) {
//							issl.add(new IsslInfoTreeNode(pcisl));
//						}
//						((DefaultTreeModel) treeRezIssl.getModel()).reload(issl);
//					} catch (KmiacServerException e) {
//						e.printStackTrace();
//					} catch (TException e) {
//						MainForm.conMan.reconnect(e);
//					}
//		 		}

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
					.addContainerGap()
					.addComponent(spTxtRezIssl, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnlRezIsslR.setVerticalGroup(
			gl_pnlRezIsslR.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRezIsslR.createSequentialGroup()
					.addContainerGap()
					.addComponent(spTxtRezIssl, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		 epTxtRezIssl = new JEditorPane();
		spTxtRezIssl.setViewportView(epTxtRezIssl);
		pnlRezIsslR.setLayout(gl_pnlRezIsslR);
		pnlRezIssl.setLayout(gl_pnlRezIssl);
		pnlLabIssl.setLayout(gl_pnlLabIssl);
		
		JPanel pnlNapr = new JPanel();
		tabbedPane.addTab("<html>Направления<br><br></html>", null, pnlNapr, null);
		tabbedPane.setTabComponentAt(3, new JLabel("<html>Направления<br><br></html>"));

		
		JLabel lblKonsVidNapr = new JLabel("на");
		
		  cmbConsVidNapr = new JComboBox<String>();
		cmbConsVidNapr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbKonsObosnov.setText("");
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
		cmbConsVidNapr.setModel(new DefaultComboBoxModel<String>(new String[] {"госпитализацию", "консультацию"}));
		
		 lblVidStacionar = new JLabel("Вид стационара");
		
		cmbVidStacionar = new ThriftIntegerClassifierCombobox<>(true);
		cmbVidStacionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbKonsObosnov.setText("");
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
		tbKonsObosnov.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		btnKonsPrint = new JButton("Печать");
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
						for (PdiagAmb pd : tblDiag.getData()) {
							if ((pd.diag_stat==1) || (pd.diag_stat==3)) {
								naprkons.setDiag(pd.diag);
							}
						}
						if (cmbKonsMesto.getSelectedItem()!= null) naprkons.setCpol(cmbKonsMesto.getSelectedItem().getName());
						naprkons.setNazv(cmbKonsMesto.getSelectedItem().toString());
						naprkons.setCdol(MainForm.authInfo.getCdol());
						naprkons.setPvizitId(tblPos.getSelectedItem().getId_obr());
						naprkons.setCpodr_name(MainForm.authInfo.getCpodr_name());
						naprkons.setClpu_name(MainForm.authInfo.getClpu_name());
						String servPath = MainForm.tcl.printNaprKons(naprkons);
						String cliPath = MainForm.conMan.createReport("направление на консультацию");
						MainForm.conMan.transferFileFromServer(servPath, cliPath);
   						MainForm.conMan.openFileInTextProcessor(cliPath, false);
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
						gosp.setPl_extr(2);
						gosp.setDatap(System.currentTimeMillis());
						gosp.setVremp(System.currentTimeMillis());
						gosp.setN_org(MainForm.authInfo.getCpodr());
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
						cotd.setStat_type(cmbVidStacionar.getSelectedPcod());
						cotd.setId(MainForm.tcl.AddCotd(cotd));
						}
						if ((cmbVidStacionar.getSelectedPcod()==1)||(cmbVidStacionar.getSelectedPcod()==2)){
						napr.setUserId(MainForm.authInfo.getUser_id());
						napr.setNpasp(Vvod.zapVr.getNpasp());
						napr.setObosnov(tbKonsObosnov.getText());
						if (cmbKonsMesto.getSelectedItem()!= null) napr.setClpu(cmbKonsMesto.getSelectedItem().getName());
						napr.setCpodr_name(MainForm.authInfo.getCpodr_name());
						napr.setClpu_name(MainForm.authInfo.getClpu_name());
						for (PdiagAmb pd : tblDiag.getData()) {
							if ((pd.diag_stat==1) || (pd.diag_stat==3)) {
								napr.setDiag(pd.diag);
							}
						}
						napr.setPvizitId(tblPos.getSelectedItem().getId_obr());
						String servPath = MainForm.tcl.printNapr(napr);
						String cliPath = MainForm.conMan.createReport("направление на госпитализацию");
						MainForm.conMan.transferFileFromServer(servPath, cliPath);	
   						MainForm.conMan.openFileInTextProcessor(cliPath, false);}
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
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
								.addComponent(btnKonsPrint, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlNapr.createSequentialGroup()
									.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
										.addComponent(lblVidStacionar, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
										.addComponent(lblKonsMesto, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
										.addComponent(lblKonsVidNapr, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlNapr.createParallelGroup(Alignment.LEADING)
										.addComponent(cmbConsVidNapr, 0, 445, Short.MAX_VALUE)
										.addComponent(cmbKonsMesto, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
										.addComponent(cmbVidStacionar, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)))
								.addComponent(lblKonsObosnov, GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)))
						.addGroup(gl_pnlNapr.createSequentialGroup()
							.addGap(11)
							.addComponent(tbKonsObosnov, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
							.addGap(1)))
					.addGap(32))
		);
		gl_pnlNapr.setVerticalGroup(
			gl_pnlNapr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlNapr.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbConsVidNapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKonsVidNapr))
					.addGap(6)
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbVidStacionar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblVidStacionar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlNapr.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbKonsMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKonsMesto))
					.addGap(14)
					.addComponent(lblKonsObosnov)
					.addGap(7)
					.addComponent(tbKonsObosnov, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnKonsPrint)
					.addGap(35))
		);
		pnlNapr.setLayout(gl_pnlNapr);
		
		JPanel pnlZakl = new JPanel();
		tabbedPane.addTab("<html>Заключение<br><br></html>", null, pnlZakl, null);
		tabbedPane.setTabComponentAt(4, new JLabel("<html>Заключение<br><br></html>"));
	
		lblZakl = new JLabel("Заключение специалиста");
		
		spZakl = new JScrollPane();
		
		cmbZaklIsh = new ThriftIntegerClassifierCombobox<>(true);
		
		lblZaklRek = new JLabel("Медицинские рекомендации");
		
		spZaklRek = new JScrollPane();
		
		JLabel lblZaklIsh = new JLabel("Исход");
		GroupLayout gl_pnlZakl = new GroupLayout(pnlZakl);
		gl_pnlZakl.setHorizontalGroup(
			gl_pnlZakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlZakl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlZakl.createParallelGroup(Alignment.LEADING)
						.addComponent(lblZakl, GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
						.addComponent(lblZaklRek, GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
						.addGroup(gl_pnlZakl.createSequentialGroup()
							.addComponent(lblZaklIsh, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbZaklIsh, 0, 551, Short.MAX_VALUE))
						.addComponent(spZaklRek, GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
						.addComponent(spZakl, GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE))
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
					.addComponent(spZakl, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblZaklRek, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spZaklRek, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(192, Short.MAX_VALUE))
		);
		
		tbZaklRek = new JTextArea();
		tbZaklRek.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbZaklRek.setLineWrap(true);
		tbZaklRek.setWrapStyleWord(true);
		spZaklRek.setViewportView(tbZaklRek);
		
		tbZakl = new JTextArea();
		tbZakl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbZakl.setWrapStyleWord(true);
		tbZakl.setLineWrap(true);
		spZakl.setViewportView(tbZakl);
		pnlZakl.setLayout(gl_pnlZakl);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 763, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(tbShabSrc, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
							.addGap(6)
							.addComponent(btnShabSrc, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblLastShab, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
						.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(tabbedPane)
					.addGap(11))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(tbShabSrc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnShabSrc, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(lblLastShab)
					.addGap(6)
					.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
					.addGap(11))
		);
		
		pnlDinBer = new JPanel();
		tabbedPane.addTab("<html>Динамическое <br>наблюдение<br> за беременными</html>", null, pnlDinBer, null);
		tabbedPane.setTabComponentAt(5, new JLabel("<html>Динамическое <br>наблюдение<br> за беременными</html>"));
		
		pnlPlod = new JPanel();
		pnlPlod.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		pnlOtek = new JPanel();
		pnlOtek.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel pnlIzmer = new JPanel();
		pnlIzmer.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblSrokBer = new JLabel("Срок беременности");
		
		JLabel lblOkrJiv = new JLabel("Окружность живота");
		
		JLabel lblVdm = new JLabel("ВДМ");
		
		JLabel lblTolPl = new JLabel("Толщина плаценты");
		
		CustomTextField tbSrokBer = new CustomTextField();
		tbSrokBer.setColumns(10);
		
		CustomTextField tbOkrJiv = new CustomTextField();
		tbOkrJiv.setColumns(10);
		
		CustomTextField tbVdm = new CustomTextField();
		tbVdm.setColumns(10);
		
		CustomTextField tbTolPl = new CustomTextField();
		tbTolPl.setColumns(10);
		GroupLayout gl_pnlIzmer = new GroupLayout(pnlIzmer);
		gl_pnlIzmer.setHorizontalGroup(
			gl_pnlIzmer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlIzmer.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIzmer.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSrokBer)
						.addComponent(lblOkrJiv)
						.addComponent(lblVdm)
						.addComponent(lblTolPl))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlIzmer.createParallelGroup(Alignment.LEADING, false)
						.addComponent(tbSrokBer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tbVdm, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
						.addComponent(tbOkrJiv, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
						.addComponent(tbTolPl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(157, Short.MAX_VALUE))
		);
		gl_pnlIzmer.setVerticalGroup(
			gl_pnlIzmer.createParallelGroup(Alignment.LEADING)
				.addGap(0, 149, Short.MAX_VALUE)
				.addGroup(gl_pnlIzmer.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlIzmer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSrokBer)
						.addComponent(tbSrokBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIzmer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOkrJiv)
						.addComponent(tbOkrJiv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIzmer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVdm)
						.addComponent(tbVdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlIzmer.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTolPl)
						.addComponent(tbTolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		pnlIzmer.setLayout(gl_pnlIzmer);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		button.setToolTipText("Добавление обращения");
		
		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		button_1.setToolTipText("Сохранение изменений");
		GroupLayout gl_pnlDinBer = new GroupLayout(pnlDinBer);
		gl_pnlDinBer.setHorizontalGroup(
			gl_pnlDinBer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDinBer.createSequentialGroup()
					.addGroup(gl_pnlDinBer.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDinBer.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlDinBer.createParallelGroup(Alignment.LEADING)
								.addComponent(pnlIzmer, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlDinBer.createSequentialGroup()
									.addComponent(pnlPlod, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(pnlOtek, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlDinBer.createSequentialGroup()
							.addGap(212)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlDinBer.setVerticalGroup(
			gl_pnlDinBer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDinBer.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDinBer.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlOtek, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDinBer.createParallelGroup(Alignment.TRAILING)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_pnlDinBer.createSequentialGroup()
								.addComponent(pnlPlod, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(pnlIzmer, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(266, Short.MAX_VALUE))
		);
		
		lblOtek = new JLabel("Отеки");
		lblOtek.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		JCheckBox cbNijkOtek = new JCheckBox("Нижние конечности");
		
		JCheckBox cbVerhkOtek = new JCheckBox("Верхние конечности");
		
		JCheckBox cbVerhbrOtek = new JCheckBox("Верхняя брюшная стенка");
		
		JCheckBox cbGenOtek = new JCheckBox("Генерализованные");
		GroupLayout gl_pnlOtek = new GroupLayout(pnlOtek);
		gl_pnlOtek.setHorizontalGroup(
			gl_pnlOtek.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOtek.createSequentialGroup()
					.addGroup(gl_pnlOtek.createParallelGroup(Alignment.LEADING)
						.addComponent(cbNijkOtek)
						.addComponent(cbVerhkOtek)
						.addComponent(cbVerhbrOtek)
						.addGroup(gl_pnlOtek.createSequentialGroup()
							.addGap(66)
							.addComponent(lblOtek))
						.addComponent(cbGenOtek))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlOtek.setVerticalGroup(
			gl_pnlOtek.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOtek.createSequentialGroup()
					.addComponent(lblOtek)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbNijkOtek)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbVerhkOtek)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbVerhbrOtek)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbGenOtek)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		pnlOtek.setLayout(gl_pnlOtek);
		
		JLabel lblChssPlod = new JLabel("Чсс плода");
		
		JLabel lblPolojPlod = new JLabel("Положение плода");
		
		JLabel lblPredlejPlod = new JLabel("Предлежание плода");
		
		JLabel lblSerdcePlod = new JLabel("Сердцебиение плода");
		
		tbChssPlod = new CustomTextField();
		tbChssPlod.setColumns(10);
		
		 cmbPolojPlod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db1);
		
		 cmbPredlejPlod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db2);
		
		 cmbCerdcebPlod1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db3);
		
		 cmbSerdcebPlod2 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db4);
		 
		GroupLayout gl_pnlPlod = new GroupLayout(pnlPlod);
		gl_pnlPlod.setHorizontalGroup(
			gl_pnlPlod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPlod.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlPlod.createParallelGroup(Alignment.LEADING)
						.addComponent(lblChssPlod)
						.addComponent(lblPolojPlod)
						.addComponent(lblPredlejPlod)
						.addComponent(lblSerdcePlod))
					.addGap(13)
					.addGroup(gl_pnlPlod.createParallelGroup(Alignment.LEADING)
						.addComponent(cmbPredlejPlod, Alignment.TRAILING, 0, 226, Short.MAX_VALUE)
						.addComponent(cmbPolojPlod, 0, 226, Short.MAX_VALUE)
						.addComponent(tbChssPlod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbCerdcebPlod1, Alignment.TRAILING, 0, 226, Short.MAX_VALUE)
						.addComponent(cmbSerdcebPlod2, 0, 226, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlPlod.setVerticalGroup(
			gl_pnlPlod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPlod.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlPlod.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblChssPlod)
						.addComponent(tbChssPlod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlPlod.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPolojPlod)
						.addComponent(cmbPolojPlod, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlPlod.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPredlejPlod)
						.addComponent(cmbPredlejPlod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlPlod.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSerdcePlod)
						.addComponent(cmbCerdcebPlod1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbSerdcebPlod2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPlod.setLayout(gl_pnlPlod);
		pnlDinBer.setLayout(gl_pnlDinBer);
		panel_2.setLayout(gl_panel_2);
		
		cmbVidOpl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_opl);
		cmbVidOpl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblVidOpl = new JLabel("Вид опл.");
		lblVidOpl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		cmbCelObr = new ThriftIntegerClassifierCombobox<>(true);
		cmbCelObr.setMaximumSize(new Dimension(123, 32767));
		cmbCelObr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblCelObr = new JLabel("Цель пос.");
		lblCelObr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblRez = new JLabel("Результат");
		lblRez.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		cmbRez = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_aq0);
		
		JLabel lblMobs = new JLabel("Место обс.");
		lblMobs.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		cmbMobs = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abs);
		GroupLayout gl_pnlTalon = new GroupLayout(pnlTalon);
		gl_pnlTalon.setHorizontalGroup(
			gl_pnlTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTalon.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblVidOpl, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbVidOpl, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblCelObr, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbCelObr, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblRez, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbRez, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblMobs, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmbMobs, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
		);
		gl_pnlTalon.setVerticalGroup(
			gl_pnlTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTalon.createSequentialGroup()
					.addGroup(gl_pnlTalon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVidOpl)
						.addComponent(cmbVidOpl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmbMobs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMobs)
						.addComponent(cmbRez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRez)
						.addComponent(lblCelObr)
						.addComponent(cmbCelObr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(50))
		);
		pnlTalon.setLayout(gl_pnlTalon);
		
		JScrollPane spPos = new JScrollPane();
		spPos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		 btnPosAdd = new JButton("");
		 btnPosAdd.setToolTipText("Добавление посещения");
		btnPosAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblObr.getSelectedItem() == null)
					return;
				
				if (tblObr.getSelectedItem().closed) {
					JOptionPane.showMessageDialog(Vvod.this, "Добавить посещение не возможно, так как случай обращения закрыт.");
					return;
				}
				
				pvizitAmb = new PvizitAmb();
				pvizitAmb.setId_obr(zapVr.id_pvizit);
				pvizitAmb.setNpasp(zapVr.getNpasp());
				pvizitAmb.setDatap(System.currentTimeMillis());
				pvizitAmb.setDataz(System.currentTimeMillis());
	  			pvizitAmb.setCod_sp(MainForm.authInfo.pcod);
	  			pvizitAmb.setCdol(MainForm.authInfo.cdol);
				pvizitAmb.setCpol(MainForm.authInfo.getCpodr());
				pvizitAmb.setKod_ter(MainForm.authInfo.getKdate());
				
				try {
					pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
					tblPos.setData(MainForm.tcl.getPvizitAmb(zapVr.id_pvizit));
					tblPos.requestFocusInWindow();
					if (tblPos.editCellAt(tblPos.getSelectedRow(), 0))
						tblPos.getEditorComponent().requestFocusInWindow();
				} catch (KmiacServerException e2) {
					e2.printStackTrace();
				} catch (TException e2) {
					MainForm.conMan.reconnect(e2);
					e2.printStackTrace();
				}
		
				btnNaprPrint.setEnabled((tblObr.getRowCount() > 0) && (tblPos.getRowCount() > 0));
				btnKonsPrint.setEnabled(btnNaprPrint.isEnabled());
				
				pvizitAmbCopy = new PvizitAmb(pvizitAmb);
				priemCopy = new Priem(priem);
				anamZabCopy = new AnamZab(anamZab);
			}
		});
		btnPosAdd.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		
		final JButton btnPosDel = new JButton("");
		btnPosDel.setToolTipText("Удаление посещения");
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
							if (tblPos.getSelectedItem() == null)
								btnObrDel.doClick();
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
						if (!checkZaklDiag())
							return;
						if (!prepareSavePos())
							return;
						
						MainForm.tcl.setPriem(priem);
						MainForm.tcl.setAnamZab(anamZab);
						pvizit.setDatao(tblObr.getSelectedItem().datao);
						MainForm.tcl.UpdatePvizit(pvizit);
						MainForm.tcl.UpdatePvizitAmb(pvizitAmb);
						btnRecPriem.setEnabled(!isStat && !pvizit.isSetIshod());
						if (pvizit.isSetIshod()) {
							tblObr.getSelectedItem().setIshod(pvizit.getIshod());
							tblObr.getSelectedItem().setClosed(true);
						} else {
							tblObr.getSelectedItem().unsetIshod();
							tblObr.getSelectedItem().setClosed(false);
						}
						tblObr.updateChangedSelectedItem();
						
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
		
		JScrollPane spObr = new JScrollPane();
		spObr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		btnObrAdd = new JButton("");
		btnObrAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Pvizit pviz : tblObr.getData())
					if (pviz.getDatao() == getDateMills(System.currentTimeMillis())) {
						JOptionPane.showMessageDialog(Vvod.this, "Невозможно записать два обращения за одну дату");
						return;
					}
				
				if (zapVr.npasp > 0)
					addPvizit();
			}
		});
		btnObrAdd.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		btnObrAdd.setToolTipText("Добавление обращения");
		
		btnObrDel = new JButton("");
		btnObrDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tblObr.getSelectedItem() !=  null) {
					if ((tblPos.getRowCount() == 0) || (JOptionPane.showConfirmDialog(Vvod.this, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
						try {
							MainForm.tcl.DeletePvizitAmbObr(zapVr.getId_pvizit());
							MainForm.tcl.DeleteAnamZab(zapVr.getId_pvizit());
							MainForm.tcl.DeletePvizit(zapVr.getId_pvizit());
							MainForm.tcl.DeletePdiagAmbVizit(zapVr.getId_pvizit());
							
							int prevIdx = tblObr.getSelectedRow();
							tblObr.setData(MainForm.tcl.getPvizitList(zapVr.npasp));
							if (tblObr.getRowCount() > 0)
								if (prevIdx < tblObr.getRowCount() - 1)
									tblObr.setRowSelectionInterval(prevIdx, prevIdx);
								else
									tblObr.setRowSelectionInterval(tblObr.getRowCount() - 1, tblObr.getRowCount() - 1);
							if (tblObr.getSelectedItem() == null) {
								Vvod.this.dispatchEvent(new WindowEvent(Vvod.this, WindowEvent.WINDOW_CLOSING));
							}
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (TException e1) {
							e1.printStackTrace();
							MainForm.conMan.reconnect(e1);
						}
					}
				}
			}
		});
		btnObrDel.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		btnObrDel.setToolTipText("Удаление обращения");
		
		btnObrSave = new JButton("");
		btnObrSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPosSave.doClick();
			}
		});
		btnObrSave.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		btnObrSave.setToolTipText("Сохранение изменений");
		
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tblObr.setData(MainForm.tcl.getPvizitList(zapVr.npasp));
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRefresh.setToolTipText("Обновить");
		btnRefresh.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/refresh.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(spObr, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnObrAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnObrDel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnObrSave, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRefresh, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spPos, GroupLayout.PREFERRED_SIZE, 541, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPosAdd, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPosDel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPosSave, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spPos, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
							.addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnObrAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnObrDel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnObrSave, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
						.addComponent(spObr, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnPosAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPosDel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPosSave, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
					.addGap(0))
		);
		
		tblObr = new CustomTable<>(true, false, Pvizit.class, 3, "Дата обращения", 17, "Закрыт");
		tblObr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tblObr.setDateField(0);
		tblObr.setEditableFields(false, 1);
		tblObr.setFillsViewportHeight(true);
		tblObr.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (tblObr.getSelectedItem() != null) {
						zapVr.setId_pvizit(tblObr.getSelectedItem().id);
						tblPos.setData(MainForm.tcl.getPvizitAmb(zapVr.getId_pvizit()));
						//treeRezIssl.setModel(new DefaultTreeModel(createNodes()));
						checkZapVrNext();
					} else {
						tblPos.setData(new ArrayList<PvizitAmb>());
						//treeRezIssl.setModel(null);
					}
				} catch (KmiacServerException e1) {
					JOptionPane.showMessageDialog(Vvod.this, "Не удалось загрузить список посещений.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		spObr.setViewportView(tblObr);
		
		tblPos = new CustomTable<>(true, false, PvizitAmb.class, 3, "Дата посещения", 19, "ФИО врача", 24, "Должность");
		tblPos.setDateField(0);
		tblPos.setEditableFields(false, 1, 2);
		tblPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (!arg0.getValueIsAdjusting() && (tblPos.getSelectedItem() != null)){
				pvizitAmb = new PvizitAmb();
				priem = new Priem();
				anamZab = new AnamZab();
				pvizit = tblObr.getSelectedItem();
				if (pvizit == null)
					pvizit = new Pvizit();
				if (tblPos.getSelectedItem()!= null) {
					pvizitAmb = tblPos.getSelectedItem();
					try {
						tblDiag.setData(MainForm.tcl.getPdiagAmb(tblPos.getSelectedItem().getId()));
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}
						try {
						
						priem = MainForm.tcl.getPriem(zapVr.npasp, tblPos.getSelectedItem().id);
						pvizit = MainForm.tcl.getPvizit(zapVr.getId_pvizit());
						tblObr.replaceSelectedItem(pvizit);
						anamZab = MainForm.tcl.getAnamZab(zapVr.getId_pvizit(), zapVr.getNpasp());
						btnRecPriem.setEnabled(!isStat &&!pvizit.isSetIshod());
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (PriemNotFoundException e) {
						e.printStackTrace();
					} catch (PvizitNotFoundException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
				} else {
					tblDiag.setData(new ArrayList<PdiagAmb>());
				}
				
				if (pvizitAmb.isSetCpos()) {
					cmbCelObr.setSelectedPcod(pvizitAmb.getCpos());
					pvizit.setCobr(pvizitAmb.cpos);
				} else {
					cmbCelObr.setSelectedItem(null);
				}
				if (pvizitAmb.isSetRezult()) {
					cmbRez.setSelectedPcod(pvizitAmb.getRezult());
					pvizit.setRezult(pvizitAmb.rezult);
				} else {
					cmbRez.setSelectedItem(null);
					pvizit.unsetRezult();
				}
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
				tbStatAd.setText(priem.getT_ad_sist());	
				tbAdDist.setText(priem.getT_ad_dist());	
				tbStatTemp.setText(priem.getT_temp());	
				tbStatChss.setText(priem.getT_chss());	
				tbStatRost.setText(priem.getT_rost());	
				tbStatVes.setText(priem.getT_ves());	
				tbLoc.setText(priem.getT_st_localis());	
				tbFiz.setText(priem.getT_fiz_obsl());
				tbOcen.setText(priem.getT_ocenka());
				tbStat.setText(priem.getT_status_praesense());
				tbRecom.setText(priem.getT_recom());
				tbAnam.setText(anamZab.getT_ist_zab());
				tbZakl.setText(pvizit.getZakl());
				tbZaklRek.setText(pvizit.getRecomend());
				tbLech.setText(pvizit.getLech());
				treeRezIssl.setModel(new DefaultTreeModel(createNodes()));
				
				pvizitAmbCopy = new PvizitAmb(pvizitAmb);
				priemCopy = new Priem(priem);
				anamZabCopy = new AnamZab(anamZab);
				pvizitCopy = new Pvizit(pvizit);
				
			}
		}
	});
		tblPos.setFillsViewportHeight(true);
		spPos.setViewportView(tblPos);
		panel.setLayout(gl_panel);
		setStatMode(isStat);
		getContentPane().setLayout(groupLayout);
	}

	private DefaultMutableTreeNode createNodes(){
		root = new DefaultMutableTreeNode("Корень");
		issinfo = new DefaultMutableTreeNode("Системы назначенных исследований");
		root.add(issinfo);
		
		try {
			{	for (P_isl_ld issl : MainForm.tcl.getIsslInfoDate(tblPos.getSelectedItem().id))
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
		private P_isl_ld issl_lab;
		
		public IsslInfoTreeNode(P_isl_ld issl_lab) {
			this.issl_lab = issl_lab;
//			this.add(new IsslSystNode(new P_isl_ld()));
		}
		
		@Override
		public String toString() {
			//return DateFormat.getDateInstance().format(new Date(issl.getDatan()));
			return issl_lab.getName_pcisl();
		}
	}
	
	class IsslSystNode extends DefaultMutableTreeNode{
		private static final long serialVersionUID = 5707201011289452058L;
		private P_isl_ld ld;
		
		public IsslSystNode(P_isl_ld ld) {
			this.ld = ld;
		}
		
		@Override
		public String toString() {
			return ld.getPcisl();
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
	
	private String getValueFromClassifier(List<IntegerClassifier> list, boolean isSet, int pcod) {
		if (isSet)
			if (pcod != 0)
				for (IntegerClassifier item : list) {
					if (item.getPcod() == pcod)
						return item.getName();
				}
		
		return null;
	}
	
	private String getValueFromClassifier(List<StringClassifier> list, boolean isSet, String pcod) {
		if (isSet)
			if (pcod != null)
				if (!pcod.equals(""))
					for (StringClassifier item : list) {
						if (item.getPcod().equals(pcod))
							return item.getName();
					}
		
		return null;
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
			if (isStat)
				setTblPosToStatMode();
			cmbVidStacionar.setData(MainForm.tcl.get_n_tip());
			cmbLpu.setData(MainForm.tcl.get_m00());
			cmbCelObr.setData(MainForm.tcl.get_n_p0c());
			cmbZaklIsh.setData(MainForm.tcl.get_n_ap0());
			cmbLpu.setSelectedPcod(MainForm.authInfo.getClpu());
			listVidIssl = MainForm.tcl.get_vid_issl();
			if (!isStat)
				lbShabSrc.setData(MainForm.tcl.getShOsmPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu,  null));

		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(Vvod.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	public void showVvod(ZapVr zapVr) {
		Vvod.zapVr = zapVr;
		idPvizitMainForm = zapVr.id_pvizit;
		
		try {
			if (!isStat)
				setTitle("Врачебный осмотр");
			else
				setTitle("Врачебный осмотр, режим статистика");
			if (zapVr.npasp > 0) {
				if (zapVr.serpolis == null) setTitle(String.format("%s - %d, %s %s %s, номер и серия полиса: %s, %7$td.%7$tm.%7$tY ", getTitle(), zapVr.getNpasp(), zapVr.getFam(), zapVr.getIm(), zapVr.getOth(), zapVr.getNompolis(), zapVr.getDatar()));
				if (zapVr.nompolis == null) setTitle(String.format("%s - %d, %s %s %s, серия и номер полиса: %s, %7$td.%7$tm.%7$tY ", getTitle(), zapVr.getNpasp(), zapVr.getFam(), zapVr.getIm(), zapVr.getOth(), zapVr.getSerpolis(), zapVr.getDatar()));;
				if ((zapVr.serpolis != null) && (zapVr.nompolis != null))setTitle(String.format("%s - %d, %s %s %s, номер и серия полиса: %s %s, %8$td.%8$tm.%8$tY ", getTitle(), zapVr.getNpasp(), zapVr.getFam(), zapVr.getIm(), zapVr.getOth(), zapVr.getNompolis(), zapVr.getSerpolis(), zapVr.getDatar()));
				if ((zapVr.serpolis == null) && (zapVr.nompolis == null))setTitle(String.format("%s - %d, %s %s %s, номер и серия полиса: -, %6$td.%6$tm.%6$tY ", getTitle(), zapVr.getNpasp(), zapVr.getFam(), zapVr.getIm(), zapVr.getOth(), zapVr.getDatar()));
				age = (int) ((System.currentTimeMillis() - zapVr.datar) / 31556952000L);
				btnBer.setEnabled(!isStat && (zapVr.pol != 1) && ((age > 13) && (age < 50)));
				if (!isStat && (zapVr.pol != 1) && ((age > 13) && (age < 50)))  tabbedPane.setEnabledAt(5, true); else tabbedPane.setEnabledAt(5, false);
				btnRecPriem.setEnabled(!isStat);
				chbDiagBer.setEnabled(btnBer.isEnabled());
				tblZaklDiag.setData(MainForm.tcl.getPdiagZInfo(zapVr.npasp));
				
			} else {
				btnRecPriem.setEnabled(false);
				btnAnam.setEnabled(false);
				btnProsm.setEnabled(false);
				btnBer.setEnabled(false);
				btnPrint.setEnabled(false);
				tblZaklDiag.setData(new ArrayList<PdiagZ>());
			}
			lblLastShab.setText("<html>Последний выбранный шаблон: </html>");
			cmbVidStacionar.setSelectedItem(null);
			cmbKonsMesto.setSelectedItem(null);
			tbKonsObosnov.setText("");
			tblObr.setData(MainForm.tcl.getPvizitList(zapVr.npasp));
			if ((idPvizitMainForm > 0) && (tblObr.getRowCount() > 0)) {
				for (int i = 0; i < tblObr.getRowCount(); i++) {
					if (tblObr.getData().get(i).id == idPvizitMainForm) {
						if (i > 0)
							tblObr.setRowSelectionInterval(i, i);
						break;
					}
				}
			}
			btnNaprPrint.setEnabled((tblObr.getRowCount() > 0) && (tblPos.getRowCount() > 0));
			btnKonsPrint.setEnabled(btnNaprPrint.isEnabled());
			
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
	if ((tbJal.getText().length() != 0) || (tbAnam.getText().length() != 0) || (tbStat.getText().length() != 0) 
		|| (tbLoc.getText().length() != 0) || (tbLech.getText().length() != 0) || (tbOcen.getText().length() != 0)
		|| (tbZakl.getText().length() != 0) || (tbRecom.getText().length() != 0))
	{
		if (JOptionPane.showConfirmDialog(Vvod.this, "Очистить поля?", "Очищение полей осмотра", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
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
		addDiagShablon(sh.diag.trim());
			}
		else {
			for (ShablonText st : sh.textList) {
				switch (st.grupId) {
				case 1:
					tbJal.setText(tbJal.getText() + st.text);
					break;
				case 2:
					tbAnam.setText(tbAnam.getText() + st.text);
					break;
				case 6:
					tbStat.setText(tbStat.getText() + st.text);
					break;
				case 8:
					tbLoc.setText(tbLoc.getText() + st.text);
					break;
				case 10:
					tbLech.setText(tbLech.getText() + st.text);
					break;
				case 14:
					tbOcen.setText(tbOcen.getText() + st.text);
					break;
				case 13:
					tbZakl.setText(tbZakl.getText() + st.text);
					break;
				case 12:
					tbRecom.setText(tbRecom.getText() + st.text);
					break;
				default:
					break;
				}
			}
		addDiagShablon(sh.diag.trim());
		}
	}
	
	if ((tbJal.getText().length() == 0) && (tbAnam.getText().length() == 0) && (tbStat.getText().length() == 0) 
			&& (tbLoc.getText().length() == 0) && (tbLech.getText().length() == 0) && (tbOcen.getText().length() == 0)
			&& (tbZakl.getText().length() == 0) && (tbRecom.getText().length() == 0))
		{

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
			addDiagShablon(sh.diag.trim());
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
		  		for (PdiagAmb da : tblDiag.getData()) {
					if (da.diag.equals(mkb.pcod)) {
			  			JOptionPane.showMessageDialog(Vvod.this, String.format("Диагноз %s уже есть в списке.", mkb.pcod));
			  			return;
					}
				}
		  		diagamb = new PdiagAmb();
		  		diagamb.setId_obr(zapVr.getId_pvizit());
		  		diagamb.setId_pos(tblPos.getSelectedItem().id);
		  		diagamb.setNpasp(zapVr.getNpasp());
		  		for (PdiagZ pd : tblZaklDiag.getData()){
		  			if (pd.getDiag().equals(mkb.pcod)) 
		  				{diagamb.setDatad(pd.getDatad());
		  				diagamb.setPredv(false);}
	  				}

		  		if (!diagamb.isSetDatad()){
		  			diagamb.setDatad(System.currentTimeMillis());
		  			diagamb.setPredv(true);
		  		}
		  		if (!isStat) {
			  		diagamb.setCod_sp(MainForm.authInfo.pcod);
			  		diagamb.setCdol(MainForm.authInfo.cdol);
		  		} else {
			  		diagamb.setCod_sp(pvizitAmb.cod_sp);
			  		diagamb.setCdol(pvizitAmb.cdol);
		  		}
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
	
	private void addDiagShablon(String diag_pcod) {
		try {
  			if (diag_pcod != null) {
		  		for (PdiagAmb da : tblDiag.getData()) {
					if (da.diag.equals(diag_pcod)) {
			  			JOptionPane.showMessageDialog(Vvod.this, String.format("Диагноз %s уже есть в списке.", diag_pcod));
			  			return;
					}
				}
		  		diagamb = new PdiagAmb();
		  		diagamb.setId_obr(zapVr.getId_pvizit());
		  		diagamb.setId_pos(tblPos.getSelectedItem().id);
		  		diagamb.setNpasp(zapVr.getNpasp());
		  		for (PdiagZ pd : tblZaklDiag.getData()){
		  			if (pd.getDiag().equals(diag_pcod)) 
		  				{diagamb.setDatad(pd.getDatad());
		  				diagamb.setPredv(false);}
	  				}

		  		if (!diagamb.isSetDatad()){
		  			diagamb.setDatad(System.currentTimeMillis());
		  			diagamb.setPredv(true);
		  		}
		  		if (!isStat) {
			  		diagamb.setCod_sp(MainForm.authInfo.pcod);
			  		diagamb.setCdol(MainForm.authInfo.cdol);
		  		} else {
			  		diagamb.setCod_sp(pvizitAmb.cod_sp);
			  		diagamb.setCdol(pvizitAmb.cdol);
		  		}
		  		diagamb.setDiag_stat(1);
				diagamb.setDiag(diag_pcod);
				diagamb.setNamed(MainForm.conMan.getNameFromPcodString(StringClassifiers.n_c00, diag_pcod));
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
//		if (MainForm.tcl.isZapVrNext(zapVr.id_pvizit)) {
//			cmbZaklIsh.setSelectedIndex(-1);
//			cmbZaklIsh.setEnabled(false);
//			btnPosSave.doClick();
//		} else {
//			cmbZaklIsh.setEnabled(true);
//		}
	}

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
	
	private boolean checkZaklDiag() {
		boolean haveZaklDiag = false;
		
		for (PdiagAmb pa : tblDiag.getData())
			haveZaklDiag |= !pa.predv;
		
//		if (haveZaklDiag && (cmbZaklIsh.getSelectedItem() == null)) {
//			JOptionPane.showMessageDialog(Vvod.this, "Пациенту выставлен заключительный диагноз, но не выставлен исход.", "Ошибка ввода данных", JOptionPane.ERROR_MESSAGE);
//			return false;
//		} else 
		if ((cmbZaklIsh.getSelectedItem() != null) && !haveZaklDiag) {
			JOptionPane.showMessageDialog(Vvod.this, "Пациенту выставлен исход, но не выставлен заключительный диагноз.", "Ошибка ввода данных", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

	private boolean prepareSavePos() {
		if (!checkSameDatePos())
			return false;
		if (!checkTalInput())
			return false;
		if ((pvizit == null) || (pvizitAmb == null))
			return false;
		if (!pvizitAmb.isSetCod_sp() || !pvizitAmb.isSetCdol()) {
			JOptionPane.showMessageDialog(Vvod.this, "Не указан специалист или его должность.");
			return false;
		}
		
		priem = new Priem();
		anamZab = new AnamZab();
		if (pvizit.id > 0) {
			priem.setId_pvizit(pvizit.getId());
			priem.setNpasp(pvizit.getNpasp());
			priem.setIdpos(pvizitAmb.getId());
			priem.setT_temp(getTextOrNull(tbStatTemp.getText()));
			priem.setT_ad_sist(getTextOrNull(tbStatAd.getText()));
			priem.setT_ad_dist(getTextOrNull(tbAdDist.getText()));
			priem.setT_chss(getTextOrNull(tbStatChss.getText()));
			priem.setT_rost(getTextOrNull(tbStatRost.getText()));
			priem.setT_ves(getTextOrNull(tbStatVes.getText()));
			priem.setT_st_localis(getTextOrNull(tbLoc.getText()));
			priem.setT_ocenka(getTextOrNull(tbOcen.getText()));
			priem.setT_jalob(getTextOrNull(tbJal.getText()));
			priem.setT_status_praesense(getTextOrNull(tbStat.getText()));
			priem.setT_recom(getTextOrNull(tbRecom.getText()));
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
			pvizitAmb.unsetCpos();
			pvizit.unsetCobr();
		}
		if (cmbRez.getSelectedPcod() != null) {
			pvizitAmb.setRezult(cmbRez.getSelectedPcod());
			pvizit.setRezult(pvizitAmb.getRezult());
		} else {
			pvizitAmb.unsetRezult();
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
			  		if (!isStat) {
						pvizitAmb.setStoim(MainForm.tcl.getStoim(MainForm.authInfo.getKateg(), MainForm.authInfo.getC_nom(), MainForm.authInfo.cdol));
			  		} else {
						pvizitAmb.setStoim(MainForm.tcl.getStoim(MainForm.authInfo.getKateg(), MainForm.authInfo.getC_nom(), pvizitAmb.cdol));
			  		}
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
				pvizitAmbCopy.setDiag(pvizitAmb.diag);
			}
		
		return true;
	}

	private boolean checkSameDatePos() {
		pvizitAmb = tblPos.getSelectedItem();
		for (PvizitAmb pv: tblPos.getData())
			if (pv != pvizitAmb)
				if (pv.getDatap() == pvizitAmb.getDatap()) {
					JOptionPane.showMessageDialog(Vvod.this, "Посещение с такой датой уже занесено.");
					tblPos.cancelEdit();
					return false;
				}
		
		for (PvizitAmb pv: tblPos.getData()) {
			for (PvizitAmb pv1: tblPos.getData()) {
				if (pv != pv1)
					if (pv.getDatap() == pv1.getDatap()) {
						JOptionPane.showMessageDialog(Vvod.this, String.format("Есть посещения с одинаковой датой: %s.", SimpleDateFormat.getDateInstance().format(pv.getDatap())));
						return false;
					}
			}
		}
		
		for (PvizitAmb pv: tblPos.getData()) {
			if (pv.getDatap() < pvizit.getDatao()) {
				JOptionPane.showMessageDialog(Vvod.this, String.format("Дата посещения %s меньше даты обращения.", SimpleDateFormat.getDateInstance().format(pv.getDatap())));
				return false;
			}
		}
		
		return true;
	}
	
	private boolean checkDataChanged() {
		if ((tblObr.getSelectedItem() == null) && (tblPos.getSelectedItem() == null))
			return false;
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

	private void addPvizit() {
		int idPvizit = idPvizitMainForm;
		
		for (Pvizit pv : tblObr.getData())
			if (pv.id == idPvizit) {
				idPvizit = 0;
				break;
			}
		
		pvizit = new Pvizit();
		try {
			pvizit.setNpasp(zapVr.getNpasp());
			pvizit.setCpol(MainForm.authInfo.getCpodr());
			pvizit.setDatao(getDateMills(System.currentTimeMillis()));
			pvizit.setCuser(MainForm.authInfo.getUser_id());
			pvizit.setCdol(MainForm.authInfo.getCdol());
			pvizit.setCod_sp(MainForm.authInfo.getPcod());
			pvizit.setDataz(System.currentTimeMillis());
			pvizit.setId(idPvizit);
			if (idPvizit == 0)
				pvizit.setId(MainForm.tcl.AddPvizitId(pvizit));
			else
				MainForm.tcl.AddPvizit(pvizit);
			tblObr.addItem(0, pvizit);
			tblObr.updateSelectedItem();
			btnPosAdd.doClick();
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}	
		pvizitCopy = new Pvizit(pvizit);
	}
	
	private void setStatMode(boolean val) {
		isStat = val;
		
		btnSearch.setVisible(isStat);
		btnRecPriem.setVisible(!isStat);
		btnAnam.setVisible(!isStat);
		btnProsm.setVisible(!isStat);
		btnBer.setVisible(!isStat);
		btnPrint.setVisible(!isStat);
		btnControl.setVisible(!isStat);
		btnBolList.setVisible(!isStat);
		
		tbShabSrc.setEnabled(!isStat);
		btnShabSrc.setEnabled(!isStat);
		lbShabSrc.setEnabled(!isStat);
		lblLastShab.setEnabled(!isStat);
		
		if (isStat) {
			tabbedPane.removeTabAt(0);
			tabbedPane.removeTabAt(1);
			tabbedPane.removeTabAt(1);
		}
		
		lblZakl.setVisible(!isStat);
		spZakl.setVisible(!isStat);
		lblZaklRek.setVisible(!isStat);
		spZaklRek.setVisible(!isStat);
	}
	
	private void setPdiagz(PdiagZ pdz) throws KmiacServerException, TException {
		for (PdiagZ d : tblZaklDiag.getData())
			if (d.isSetDiag() && (d.diag.equals(pdz.diag)))
				return;
		
		MainForm.tcl.setPdiag(pdz);
	}
	
	private void setTblPosToStatMode() {
		new TablePosStatModeCellEditor(tblPos, 1, 2);
	}
	
	private class TablePosStatModeCellEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1445132039936596977L;
		private TableComboBoxIntegerEditor cmbVr;
		private TableComboBoxStringEditor cmbDol;
		private HashMap<Integer, List<StringClassifier>> mapDol;
		private CustomTable<PvizitAmb, PvizitAmb._Fields> tbl;
		private int idxVr;
		private int idxDol;
		private int curIdx;
		private String prevVrName;
		private String prevDolName;
		
		public TablePosStatModeCellEditor(CustomTable<PvizitAmb, PvizitAmb._Fields> tbl, int idxVr, int idxDol) {
			try {
				this.tbl = tbl;
				this.idxVr = idxVr;
				this.idxDol = idxDol;
				tbl.setEditableFields(true, idxVr, idxDol);
				createMapDolAndFillComboboxVr(MainForm.tcl.getVrachList(MainForm.authInfo.clpu, MainForm.authInfo.cpodr));
				cmbDol = new TableComboBoxStringEditor(null, true, null);
				setComboboxesProperties();
				tblPos.getColumnModel().getColumn(idxVr).setCellEditor(this);
				tblPos.getColumnModel().getColumn(idxDol).setCellEditor(this);
			} catch (KmiacServerException e) {
				JOptionPane.showMessageDialog(Vvod.this, "Не удалось загрузить список врачей.");
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			}
		}
		
		private void createMapDolAndFillComboboxVr(List<VrachInfo> vrl) {
			List<IntegerClassifier> lst = new ArrayList<>();
			mapDol = new HashMap<>();
			
			for (VrachInfo vi : vrl) {
				if (!mapDol.containsKey(vi.pcod)) {
					mapDol.put(vi.pcod, new ArrayList<StringClassifier>());
					lst.add(new IntegerClassifier(vi.pcod, vi.short_fio));
				}
				mapDol.get(vi.pcod).add(new StringClassifier(vi.cdol, vi.cdol_name));
			}
			
			cmbVr = new TableComboBoxIntegerEditor(null, true, lst);
		}
		
		private void setComboboxesProperties() {
			CellEditorListener cel = new CellEditorListener() {
				
				@Override
				public void editingStopped(ChangeEvent e) {
					stopCellEditing();
				}
				
				@Override
				public void editingCanceled(ChangeEvent e) {
					cancelCellEditing();
				}
			};
			cmbVr.setParentTable(tbl);
			cmbDol.setParentTable(tbl);
			cmbVr.addCellEditorListener(cel);
			cmbDol.addCellEditorListener(cel);
		}
		
		@Override
		public Object getCellEditorValue() {
			if (curIdx == idxVr) {
				if (cmbVr.getSelectedItem() != null) {
					tbl.getSelectedItem().setCod_sp(cmbVr.getSelectedItem().pcod);
					return cmbVr.getSelectedItem().name;
					} else {
						return prevVrName;
					}
			} else if (curIdx == idxDol) {
				if (cmbDol.getSelectedItem() != null) {
					tbl.getSelectedItem().setCdol(cmbDol.getSelectedItem().pcod);
					return cmbDol.getSelectedItem().name;
					} else {
						return prevDolName;
					}
			}
			
			return null;
		}
		
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			curIdx = column;
			if (curIdx == idxVr) {
				prevVrName = (String) value;
				
				tbl.getData().get(row).unsetCdol();
				tbl.getData().get(row).unsetCdol_name();
				tbl.updateChangedSelectedItem();
				
				try {
					cmbVr.setSelectedPcod(tbl.getData().get(row).cod_sp);
				} catch (Exception e) {
					cmbVr.setSelectedIndex(-1);
				}
				
				return cmbVr;
			} else if (curIdx == idxDol) {
				prevDolName = (String) value;
				cmbDol.setData(mapDol.get(tbl.getData().get(row).cod_sp));
				if (cmbDol.getItemCount() == 1)
					cmbDol.setSelectedIndex(0);
				return cmbDol;
			}
			
			return null;
		}
		
		@Override
		public boolean isCellEditable(EventObject e) {
            if (e instanceof MouseEvent) {
                return ((MouseEvent)e).getClickCount() >= 2;
            }
            
			return super.isCellEditable(e);
		}
	}
}
