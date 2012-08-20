package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.thrift.TException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.clientOsm.patientInfo.PInfo;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.AnamZab;
import ru.nkz.ivcgzo.thriftOsm.IsslMet;
import ru.nkz.ivcgzo.thriftOsm.IsslPokaz;
import ru.nkz.ivcgzo.thriftOsm.Metod;
import ru.nkz.ivcgzo.thriftOsm.Napr;
import ru.nkz.ivcgzo.thriftOsm.NaprKons;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Pdisp;
import ru.nkz.ivcgzo.thriftOsm.PdispNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pokaz;
import ru.nkz.ivcgzo.thriftOsm.PokazMet;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Protokol;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Vypis;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Vvod extends JFrame {
	private static final long serialVersionUID = 4579259944135540676L;
	public static ZapVr zapVr;
	private static Pvizit pvizit;
	private static PvizitAmb pvizitAmb;
	private static PdiagAmb diagamb;
	private static PdiagZ pdiag;
	private static Priem priem;
	private static AnamZab anamZab;
	private static Pdisp pdisp;
	private FormSign sign;
	private FormPostBer postber;
	private JPanel pzakl;
	private JPanel Jalob;
	private JEditorPane tpJalob;
	private JTextField tftemp;
	private JTextField tfad;
	private JTextField tfrost;
	private JTextField tfves;
	private JTextField tfchss;
//	private JEditorPane tpFizObsl;
//	private ShablonTextField tpLocalis;
//	private ShablonTextField tpOcenka;
	private JEditorPane tpIstZab;
//	private JEditorPane tpObosnov;
//	private JEditorPane tpodiag;

	private JEditorPane tpFizObsl;
	private ShablonTextField tpLocalis;
	private ShablonTextField tpOcenka;
	private JEditorPane tpObosnov;
	private JEditorPane tpodiag;
	private ThriftStringClassifierCombobox<StringClassifier> c_obr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbrez;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbish;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> vid_opl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> vid_travm;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbMobsp;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbDgrup;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbObstreg;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbDish;
	private CustomTable<PvizitAmb, PvizitAmb._Fields> TabPos;
	private JEditorPane tpStPraes;
	private ShablonTextField tprecom;
	private CustomTable<PdiagAmb,PdiagAmb._Fields> TabDiag;
	private ShablonTextField tpzakl;
	private PInfo pinfo;
	private CustomDateEditor tfDvz;
	public static List<IntegerClassifier> pokNames;
	private JRadioButton rbPoz;
	private JRadioButton rbRan;
	private JRadioButton rbOstr;
	private JRadioButton rbHron;
	private JCheckBox cbPatol;
	private JCheckBox cbPriznb;
	private JCheckBox cbPrizni;
	private CustomDateEditor tfFDatDIsh;
	private JButton butAnamn;
	private JButton butProsm;
	private JButton butBer;
	private JButton PosSave;
	private JButton PosDelete;
	private JButton BSearch;
	private JTextField tfKab;
	private JRadioButton rbMetodIssl;
	private JRadioButton rbPokaz;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cbVidIssl;
	public ThriftStringClassifierCombobox<StringClassifier> cbOrgan;
	public CustomTable<Metod, Metod._Fields> tabMetod;
	private CustomTable<PokazMet, PokazMet._Fields> tabPokazMet;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cbMesto;
	private JScrollPane spPokaz;
	private CustomTable <Pokaz, Pokaz._Fields> tabPokaz;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbN00;
	private JComboBox cbVidNapr;
	private JLabel lblObosnov;
	private JButton butPrintNapr;
	private JPopupMenu pmvizit;
	private JLabel tfPatient;
	private int idpv;
	
	/**
	 * Create the frame.
	 */
	public Vvod() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
		});
		try {
			pokNames = MainForm.tcl.getPokNames();
		} catch (Exception e3) {
			e3.printStackTrace();
			pokNames = new ArrayList<>();
		}
		
		 sign = new FormSign();
		 MainForm.instance.addChildFrame(sign);
		 
		// postber = new FormPostBer();
		 //MainForm.instance.addChildFrame(postber);
		 
		setBounds(100, 100, 1029, 962);
		
		final ThriftIntegerClassifierList listShablon = new ThriftIntegerClassifierList();
		listShablon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ShablonTextField.instance.setText(listShablon.getSelectedValue().getName());
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(getContentPane());
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
		);
		
		final JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setRowHeaderView(panel);
		
	
		JPanel panel_Talon = new JPanel();
				panel_Talon.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0422\u0430\u043B\u043E\u043D \u043F\u0430\u0446\u0438\u0435\u043D\u0442\u0430", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addChangeListener(new ChangeListener() {
			List<IntegerClassifier> lstIdRazd;
			
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					JPanel selPan = (JPanel) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
					if (selPan instanceof ShablonTextPanel) {
						lstIdRazd = MainForm.tcl.getShablonCdol(((ShablonTextPanel) selPan).getRazdId(), MainForm.authInfo.getCdol());
						disableTextFields(selPan);
					}
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
				System.out.println(tabbedPane.getSelectedIndex());
			}
			
			private void disableTextFields(Container c) {
				if (c.getComponentCount() > 0)
					lbl: for (int i = 0; i < c.getComponentCount(); i++) {
						if (c.getComponent(i) instanceof ShablonTextField) {
							ShablonTextField txt = (ShablonTextField)c.getComponent(i);
							for (IntegerClassifier razd : lstIdRazd) {
								if (razd.getPcod() == txt.getIdPok()) {
									txt.setVisible(true);
									continue lbl;
								}
								txt.setVisible(false);
							}
							((ShablonTextField)c.getComponent(i)).setVisible(false);
						} else if (c.getComponent(i) instanceof Container)
							disableTextFields((Container) c.getComponent(i));
					}
			}
		});
		
		 Jalob = new JPanel();
		
		 Jalob.setAutoscrolls(true);
		tabbedPane.addTab("Жалобы", null, Jalob, null);
		
		JScrollPane spJalob = new JScrollPane();
		spJalob.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_Jalob = new GroupLayout(Jalob);
		gl_Jalob.setHorizontalGroup(
			gl_Jalob.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_Jalob.createSequentialGroup()
					.addContainerGap()
					.addComponent(spJalob, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_Jalob.setVerticalGroup(
			gl_Jalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Jalob.createSequentialGroup()
					.addContainerGap()
					.addComponent(spJalob, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(314, Short.MAX_VALUE))
		);
		
		 tpJalob = new JEditorPane();
		spJalob.setViewportView(tpJalob);
		Jalob.setLayout(gl_Jalob);
		
		ShablonTextPanel pAnanmnesis = new ShablonTextPanel(2);
		tabbedPane.addTab("Anamnesis Morbi", null, pAnanmnesis, null);
		
		JScrollPane spAnamnesis = new JScrollPane();
		GroupLayout gl_pAnanmnesis = new GroupLayout(pAnanmnesis);
		gl_pAnanmnesis.setHorizontalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addContainerGap()
					.addComponent(spAnamnesis, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pAnanmnesis.setVerticalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addContainerGap()
					.addComponent(spAnamnesis, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(318, Short.MAX_VALUE))
		);
		
		 tpIstZab = new JEditorPane();
		spAnamnesis.setViewportView(tpIstZab);
		pAnanmnesis.setLayout(gl_pAnanmnesis);
		
		ShablonTextPanel pStatuspr = new ShablonTextPanel(6);
		tabbedPane.addTab("Status Praesense", null, pStatuspr, null);
		
		JPanel panel_1 = new JPanel();
		
		final JLabel lbltemp = new JLabel("Температура");
		
		tftemp = new JTextField();
		tftemp.setColumns(10);
		
		final JLabel lblad = new JLabel("АД");
		
		tfad = new JTextField();
		tfad.setColumns(10);
		
		final JLabel lblrost = new JLabel("Рост");
		
		tfrost = new JTextField();
		tfrost.setColumns(10);
		
		final JLabel lblves = new JLabel("Вес");
		
		tfves = new JTextField();
		tfves.setColumns(10);
		
		final JLabel lblchss = new JLabel("ЧСС");
		
		tfchss = new JTextField();
		tfchss.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lbltemp)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblad)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfad, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblrost)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblves, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfves, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(138)
					.addComponent(lblchss)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblchss)
						.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltemp)
						.addComponent(lblad)
						.addComponent(tfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblrost)
						.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblves)
						.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JScrollPane sppraesense = new JScrollPane();
		sppraesense.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_pStatuspr = new GroupLayout(pStatuspr);
		gl_pStatuspr.setHorizontalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pStatuspr.createParallelGroup(Alignment.LEADING)
						.addComponent(sppraesense, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pStatuspr.setVerticalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(sppraesense, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panPraesense = new JPanel();
		sppraesense.setViewportView(panPraesense);
		
		final JLabel lblStPraes = new JLabel("Status praesense");
		
		 tpStPraes = new JEditorPane();
		tpStPraes.setBorder(UIManager.getBorder("TextField.border"));
		
		GroupLayout gl_panPraesense = new GroupLayout(panPraesense);
		gl_panPraesense.setHorizontalGroup(
			gl_panPraesense.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panPraesense.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panPraesense.createParallelGroup(Alignment.LEADING)
						.addComponent(tpStPraes, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStPraes))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panPraesense.setVerticalGroup(
			gl_panPraesense.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panPraesense.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblStPraes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpStPraes, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(962, Short.MAX_VALUE))
		);
		panPraesense.setLayout(gl_panPraesense);
		pStatuspr.setLayout(gl_pStatuspr);
		
		ShablonTextPanel pfizikob = new ShablonTextPanel(7);
		tabbedPane.addTab("Физикальное обследование", null, pfizikob, null);
		
		JScrollPane spFizik = new JScrollPane();
		spFizik.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_pfizikob = new GroupLayout(pfizikob);
		gl_pfizikob.setHorizontalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addComponent(spFizik, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
		);
		gl_pfizikob.setVerticalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfizikob.createSequentialGroup()
					.addComponent(spFizik, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panFiz = new JPanel();
		spFizik.setViewportView(panFiz);
		
		final JLabel lblfizik = new JLabel("Физикальное обследование");
		
		tpFizObsl = new JEditorPane();
		tpFizObsl.setBorder(UIManager.getBorder("TextField.border"));
		
		GroupLayout gl_panFiz = new GroupLayout(panFiz);
		gl_panFiz.setHorizontalGroup(
			gl_panFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panFiz.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panFiz.createParallelGroup(Alignment.LEADING)
						.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblfizik, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_panFiz.setVerticalGroup(
			gl_panFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panFiz.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblfizik)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(3263, Short.MAX_VALUE))
		);
		panFiz.setLayout(gl_panFiz);
		pfizikob.setLayout(gl_pfizikob);
		
		JLabel lblvid_opl = new JLabel("Вид оплаты");
		
		 vid_opl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_opl);
		
		JLabel lblcobr = new JLabel("Цель обращения");
		
		c_obr = new ThriftStringClassifierCombobox<>(StringClassifiers.n_p0c);
		JLabel lblrez = new JLabel("Результат");
		
		cbrez = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_aq0);
		
		cbMobsp = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abs);
		
		JLabel lblmobs = new JLabel("Место обслуживания");
		 
		GroupLayout gl_panel_Talon = new GroupLayout(panel_Talon);
		gl_panel_Talon.setHorizontalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblcobr)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblvid_opl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(vid_opl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblrez, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(cbrez, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblmobs)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbMobsp, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(274, Short.MAX_VALUE))
		);
		gl_panel_Talon.setVerticalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addGap(3)
							.addComponent(lblrez))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblvid_opl)
									.addComponent(vid_opl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(cbrez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblcobr)
								.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblmobs)
								.addComponent(cbMobsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_Talon.setLayout(gl_panel_Talon);
		
		JScrollPane sPos = new JScrollPane();
		
		
		JButton AddVizit = new JButton("");
		AddVizit.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		AddVizit.setToolTipText("Добавить новую запись");
		AddVizit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pvizit = new Pvizit();
				idpv = zapVr.getId_pvizit();
				if (idpv!=0){
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
				pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
				pvizitAmb.setCdol(MainForm.authInfo.getCdol());
				
				try {
					SimpleDateFormat frm = new SimpleDateFormat("dd.MM.yyyy");
					String strDat = frm.format(new Date(System.currentTimeMillis()));
					Date dat = frm.parse(strDat);
					long curDateMills = dat.getTime();
					for (PvizitAmb pviz : TabPos.getData()) {
						if (pviz.getDatap() == curDateMills) {
							JOptionPane.showMessageDialog(Vvod.this, "333333");
							return;
						}
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				try {
					Vvod.pvizit = MainForm.tcl.getPvizit(pvizit.getId());
					pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
					TabPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
					TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
				} catch (KmiacServerException e2) {
					e2.printStackTrace();
				} catch (PvizitNotFoundException e2) {
					try {
						MainForm.tcl.AddPvizit(pvizit);
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						TabPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
						TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
						e.printStackTrace();
					}
				} catch (TException e2) {
					MainForm.conMan.reconnect(e2);
					e2.printStackTrace();
				}
				}
				else {
					
					try {
						pvizit.setId(zapVr.getId_pvizit());
					pvizit.setNpasp(zapVr.getNpasp());
					pvizit.setCpol(MainForm.authInfo.getCpodr());
					pvizit.setDatao(System.currentTimeMillis());
					pvizit.setCod_sp(MainForm.authInfo.getPcod());
					pvizit.setCdol(MainForm.authInfo.getCdol());
					pvizit.setCuser(MainForm.authInfo.getUser_id());
					pvizit.setDataz(System.currentTimeMillis());
					pvizit.setId(MainForm.tcl.AddPViz(pvizit));
					pvizitAmb = new PvizitAmb();
					pvizitAmb.setId_obr(pvizit.getId());
					pvizitAmb.setNpasp(zapVr.getNpasp());
					pvizitAmb.setDatap(System.currentTimeMillis());
					pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
					pvizitAmb.setCdol(MainForm.authInfo.getCdol());
					try {
						SimpleDateFormat frm = new SimpleDateFormat("dd.MM.yyyy");
						String strDat = frm.format(new Date(System.currentTimeMillis()));
						Date dat = frm.parse(strDat);
						long curDateMills = dat.getTime();
						for (PvizitAmb pviz : TabPos.getData()) {
							if (pviz.getDatap() == curDateMills) {
								JOptionPane.showMessageDialog(Vvod.this, "333333");
								return;
							}
						}
						
						try {
							Vvod.pvizit = MainForm.tcl.getPvizit(pvizit.getId());
							pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						TabPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
						TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
						} catch (PvizitNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
						
					} catch (KmiacServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						MainForm.conMan.reconnect(e);
					}	
				}
			}
		});
		

		
		JScrollPane spShab = new JScrollPane();
		
		butAnamn = new JButton("Анамнез жизни");
		butAnamn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sign.showPsign();
			}
		});
		
		butProsm = new JButton("Просмотр");
		butProsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.tcl.testConnection();
					MainForm.pInf.update(zapVr.getNpasp());
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}}
		});
		BSearch = new JButton("Поиск"); 
		BSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PatientCommonInfo inf;
				int[] res = MainForm.conMan.showPatientSearchForm("Поиск пациента", true, true);
				if (res != null) {
					int npasp = res[0];
					try {
						inf = MainForm.tcl.getPatientCommonInfo(npasp);
						tfPatient.setText("Пациент: "+inf.getFam()+" "+inf.getIm()+" "+inf.getOt()+" Номер и серия полиса: "+inf.getPoms_ser()+"  "+inf.getPoms_nom());

					} catch (KmiacServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		
		
		butBer = new JButton("Наблюдение за беременными");
		butBer.setEnabled(false);
		butBer.setActionCommand("Наблюдение за беременными");
		butBer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//postber.setVisible(true);
				}
		});
		GroupLayout gl_panel;
			
			PosSave = new JButton("");
			PosSave.setToolTipText("Сохранить");
			PosSave.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
			PosSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						priem = new Priem();
						anamZab = new AnamZab();
						priem.setId_pvizit(pvizit.getId());
						priem.setNpasp(pvizit.getNpasp());
						priem.setIdpos(pvizitAmb.getId());
						priem.setT_ad(getTextOrNull(tfad.getText()));
						priem.setT_chss(getTextOrNull(tfchss.getText()));
						priem.setT_rost(getTextOrNull(tfrost.getText()));
						priem.setT_ves(getTextOrNull(tfves.getText()));
						priem.setT_st_localis(getTextOrNull(tpLocalis.getText()));
						priem.setT_ocenka(getTextOrNull(tpOcenka.getText()));
						priem.setT_jalob(getTextOrNull(tpJalob.getText()));
						priem.setT_status_praesense(getTextOrNull(tpStPraes.getText()));
						priem.setT_fiz_obsl(getTextOrNull(tpFizObsl.getText()));
						
						anamZab.setId_pvizit(pvizit.getId());
						anamZab.setNpasp(pvizit.getNpasp());
						anamZab.setT_ist_zab(getTextOrNull(tpIstZab.getText()));
						
						pvizit.setZakl(getTextOrNull(tpzakl.getText()));
						pvizit.setRecomend(getTextOrNull(tprecom.getText()));
						if (c_obr.getSelectedPcod() != null)
							pvizit.setCobr(c_obr.getSelectedPcod());
							else pvizit.unsetCobr();
						if (cbrez.getSelectedPcod() != null)
							pvizitAmb.setRezult(cbrez.getSelectedPcod());
							else pvizitAmb.unsetRezult();
						if (cbrez.getSelectedPcod() != null)
							pvizit.setRezult(cbrez.getSelectedPcod());
							else pvizit.unsetRezult();

						if (cbish.getSelectedPcod() != null)
							pvizit.setIshod(cbish.getSelectedPcod());
							else pvizit.unsetIshod();
					
						if (cbMobsp.getSelectedPcod() != null)
							pvizitAmb.setMobs(cbMobsp.getSelectedPcod());
						else
							pvizitAmb.unsetMobs();
						if (vid_opl.getSelectedPcod() != null)
							pvizitAmb.setOpl(vid_opl.getSelectedPcod());
						else pvizitAmb.unsetOpl();

						
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
			
			PosDelete = new JButton("");
			PosDelete.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
			PosDelete.setToolTipText("Удалить запись");
			PosDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						MainForm.tcl.DeleteAnamZab(TabPos.getSelectedItem().getId_obr());
						MainForm.tcl.DeletePriem(TabPos.getSelectedItem().getId());
						MainForm.tcl.DeletePvizitAmb(TabPos.getSelectedItem().getId());
						TabPos.setData(MainForm.tcl.getPvizitAmb(Vvod.zapVr.getId_pvizit()));
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
				}
			});
			
			 tfPatient = new JLabel("");
			 tfPatient.setFont(new Font("Tahoma", Font.BOLD, 11));
			 
			
			final JButton BPrint = new JButton("Печатные формы");
			final JPanel jPanel = new JPanel();
		        jPanel.add(BPrint);
		        getContentPane().add(jPanel);
			BPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int x = BPrint.getX();
	                int y = BPrint.getY() + BPrint.getHeight();
	                JPopupMenu jPopupMenu = new JPopupMenu();
	               		JMenuItem mi1 = new JMenuItem("Случай заболевания");
	               		jPopupMenu.add(mi1);
				JMenuItem mi2 = new JMenuItem("Выписка из карты");
				jPopupMenu.add(mi2);
				JMenuItem mi3 = new JMenuItem("Протокол заключения КЭК");
				jPopupMenu.add(mi3);
                jPopupMenu.show(jPanel, x, y);

mi1.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				Protokol protokol = new Protokol();
				protokol.setUserId(MainForm.authInfo.getUser_id());
				protokol.setNpasp(Vvod.zapVr.getNpasp());
				protokol.setPvizit_id(TabPos.getSelectedItem().id_obr);
				protokol.setPvizit_ambId(TabPos.getSelectedItem().id);
				protokol.setCpol(MainForm.authInfo.getCpodr());
				String servPath = MainForm.tcl.printProtokol(protokol);
				String cliPath;
				cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
				MainForm.conMan.transferFileFromServer(servPath, cliPath);

		}
		catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	});

mi2.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			Vypis vp = new Vypis();
			vp.setNpasp(Vvod.zapVr.getNpasp());
			vp.setPvizit_id(TabPos.getSelectedItem().id_obr);
			vp.setUserId(MainForm.authInfo.getUser_id());
			vp.setCpodr_name(MainForm.authInfo.getCpodr_name());
			vp.setClpu_name(MainForm.authInfo.getClpu_name());
			
			String servPath = MainForm.tcl.printVypis(vp);
			String cliPath;
			cliPath = File.createTempFile("vypis", ".htm").getAbsolutePath();
			MainForm.conMan.transferFileFromServer(servPath, cliPath);

		}
		catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	});

mi3.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				String servPath = MainForm.tcl.printKek(Vvod.zapVr.getNpasp(), TabPos.getSelectedItem().id_obr);
				String cliPath;
				cliPath = File.createTempFile("kek", ".htm").getAbsolutePath();
				MainForm.conMan.transferFileFromServer(servPath, cliPath);

		}
		catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	});
				}
			});
			gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 701, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(spShab, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(sPos, GroupLayout.PREFERRED_SIZE, 609, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(AddVizit, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(PosSave, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(PosDelete, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
									.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 741, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(3732, Short.MAX_VALUE))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(tfPatient, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
								.addGap(4112))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(butAnamn)
								.addGap(18)
								.addComponent(butProsm)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(butBer)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(BPrint)
								.addContainerGap(4240, Short.MAX_VALUE))))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(butAnamn)
							.addComponent(butProsm)
							.addComponent(butBer)
							.addComponent(BPrint))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(PosSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(PosDelete)
									.addComponent(AddVizit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(29))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(tfPatient, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(sPos, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(spShab, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 499, Short.MAX_VALUE))
						.addContainerGap())
			);
		
		spShab.setViewportView(listShablon);
		
		TabPos = new CustomTable<>(false,false,PvizitAmb.class,3,"Дата",19,"ФИО врача",5,"Должность");
		TabPos.setDateField(0);
		sPos.setViewportView(TabPos);
		TabPos.setFillsViewportHeight(true);
		
		pmvizit = new JPopupMenu();
		addPopup(TabPos, pmvizit);
		
		priem = new Priem();
		anamZab = new AnamZab();
				JMenuItem mi1 = new JMenuItem("Случай заболевания");
				pmvizit.add(mi1);
				JMenuItem mi2 = new JMenuItem("Выписка из карты");
				pmvizit.add(mi2);
				JMenuItem mi3 = new JMenuItem("Протокол заключения КЭК");
				pmvizit.add(mi3);
mi1.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				Protokol protokol = new Protokol();
				protokol.setUserId(MainForm.authInfo.getUser_id());
				protokol.setNpasp(Vvod.zapVr.getNpasp());
				protokol.setPvizit_id(TabPos.getSelectedItem().id_obr);
				protokol.setPvizit_ambId(TabPos.getSelectedItem().id);
				protokol.setCpol(MainForm.authInfo.getCpodr());
				String servPath = MainForm.tcl.printProtokol(protokol);
				String cliPath;
				cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
				MainForm.conMan.transferFileFromServer(servPath, cliPath);

		}
		catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	});

mi2.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			Vypis vp = new Vypis();
			vp.setNpasp(Vvod.zapVr.getNpasp());
			vp.setPvizit_id(TabPos.getSelectedItem().id_obr);
			vp.setUserId(MainForm.authInfo.getUser_id());
			vp.setCpodr_name(MainForm.authInfo.getCpodr_name());
			vp.setClpu_name(MainForm.authInfo.getClpu_name());
			
			String servPath = MainForm.tcl.printVypis(vp);
			String cliPath;
			cliPath = File.createTempFile("vypis", ".htm").getAbsolutePath();
			MainForm.conMan.transferFileFromServer(servPath, cliPath);

		}
		catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	});

mi3.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				String servPath = MainForm.tcl.printKek(Vvod.zapVr.getNpasp(), TabPos.getSelectedItem().id_obr);
				String cliPath;
				cliPath = File.createTempFile("kek", ".htm").getAbsolutePath();
				MainForm.conMan.transferFileFromServer(servPath, cliPath);

		}
		catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		}
	});
			
		
			TabPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabPos.getSelectedItem()!= null) {
						pvizitAmb = TabPos.getSelectedItem();
						try {
							
							priem = MainForm.tcl.getPriem(TabPos.getSelectedItem().npasp,TabPos.getSelectedItem().id);
							anamZab = MainForm.tcl.getAnamZab(TabPos.getSelectedItem().npasp,TabPos.getSelectedItem().id_obr);
						try {
							pvizit	= MainForm.tcl.getPvizit(TabPos.getSelectedItem().getId_obr());
						} catch (PvizitNotFoundException e) {
							e.printStackTrace();
						}
						} catch (KmiacServerException e) {
								e.printStackTrace();
							} catch (PriemNotFoundException e) {
								e.printStackTrace();
							} catch (TException e) {
								MainForm.conMan.reconnect(e);
							}
						if (pvizit.isSetCobr())
							c_obr.setSelectedPcod(pvizit.getCobr());
						else
							c_obr.setSelectedItem(null);
						if (pvizitAmb.isSetRezult())
							cbrez.setSelectedPcod(pvizitAmb.getRezult());
						else
							cbrez.setSelectedItem(null);
						if (pvizit.isSetIshod())
							cbish.setSelectedPcod(pvizit.getIshod());
						else
							cbish.setSelectedItem(null);
						if (pvizitAmb.isSetMobs())
							cbMobsp.setSelectedPcod(pvizitAmb.getMobs());
						else
							cbMobsp.setSelectedItem(null);
						if (pvizitAmb.isSetOpl())
							vid_opl.setSelectedPcod(pvizitAmb.getOpl());
						else
							vid_opl.setSelectedItem(null);
						tpJalob.setText(priem.getT_jalob());	
						tfad.setText(priem.getT_ad());	
						tftemp.setText(priem.getT_temp());	
						tfchss.setText(priem.getT_chss());	
						tfrost.setText(priem.getT_rost());	
						tfves.setText(priem.getT_ves());	
						tpLocalis.setText(priem.getT_st_localis());	
						tpOcenka.setText(priem.getT_ocenka());
						tpIstZab.setText(anamZab.getT_ist_zab());

					}
				}
			}
		});
			
		JPanel plocst = new ShablonTextPanel(8);
		tabbedPane.addTab("Localis status", null, plocst, null);
		
		tpLocalis = new ShablonTextField(8, 68, listShablon);
		tpLocalis.setBorder(UIManager.getBorder("TextField.border"));
		GroupLayout gl_plocst = new GroupLayout(plocst);
		gl_plocst.setHorizontalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plocst.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpLocalis, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_plocst.setVerticalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plocst.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpLocalis, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(427, Short.MAX_VALUE))
		);
		plocst.setLayout(gl_plocst);
		final ButtonGroup GroupBox1 = new ButtonGroup();
		final ButtonGroup GroupBox2 = new ButtonGroup();;
		final ButtonGroup GBoxStady = new ButtonGroup();
		final ButtonGroup GBoxHar = new ButtonGroup();
		
		JPanel pds = new JPanel();
		tabbedPane.addTab("Диагноз", null, pds, null);
		
		JPanel pvidd = new JPanel();
		pvidd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		final JRadioButton jbosn = new JRadioButton("Основной",true);
		final JRadioButton jbsoput = new JRadioButton("Сопутствующий",true);
		final JRadioButton jbosl = new JRadioButton("Осложнение основного",true);
		pvidd.add(jbosn);
		pvidd.add(jbsoput);
		pvidd.add(jbosl);
		GroupBox1.add(jbosn);
		GroupBox1.add(jbsoput);
		GroupBox1.add(jbosl);
		
		JPanel ppredv = new JPanel();
		ppredv.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		final JRadioButton jbpredv = new JRadioButton("Предварительный",true);
		jbpredv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rbHron.setEnabled(false);
	  			rbOstr.setEnabled(false);
	  			rbRan.setEnabled(false);
	  			rbPoz.setEnabled(false);
	  			tfDvz.setEnabled(false);
	  			tfFDatDIsh.setEnabled(false);
	  			cbDgrup.setEnabled(false);
	  			cbDish.setEnabled(false);
			}
		});
		final JRadioButton jbzakl = new JRadioButton("Заключительный",true);
		jbzakl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rbHron.setEnabled(true);
	  			rbOstr.setEnabled(true);
	  			rbRan.setEnabled(true);
	  			rbPoz.setEnabled(true);
	  			tfDvz.setEnabled(true);
	  			tfFDatDIsh.setEnabled(true);
	  			cbDgrup.setEnabled(true);
	  			cbDish.setEnabled(true);
			}
		});
		ppredv.add(jbpredv);
		ppredv.add(jbzakl);
		GroupBox2.add(jbpredv);
		GroupBox2.add(jbzakl);
		
		JScrollPane spDiag = new JScrollPane();
		TabDiag = new CustomTable<>(false,true,PdiagAmb.class,7,"Дата",3,"Код МКБ");
		TabDiag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (TabDiag.getSelectedItem() != null) {
						TabDiag.getSelectedItem().setDiag(ConnectionManager.instance.showMkbTreeForm("Диагнозы", TabDiag.getSelectedItem().diag).pcod);
						TabDiag.updateSelectedItem();
					}
			}
		});
		TabDiag.setDateField(0);
		spDiag.setViewportView(TabDiag);
		TabDiag.setFillsViewportHeight(true);
		TabDiag.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			PdiagZ pdiag;
			Pdisp pdisp;
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabDiag.getSelectedItem()!= null) {
						diagamb = TabDiag.getSelectedItem();
						tpodiag.setText(diagamb.getNamed());
						GroupBox1.clearSelection();
						GroupBox2.clearSelection();
							jbosn.setSelected(diagamb.diag_stat == 1);
							jbsoput.setSelected(diagamb.diag_stat == 3);
							jbosl.setSelected(diagamb.diag_stat == 2);
							if (diagamb.predv)
								jbpredv.setSelected(true);
							else
								jbzakl.setSelected(true);
							if (diagamb.isSetObstreg()) cbObstreg.setSelectedPcod(diagamb.getObstreg());else cbObstreg.setSelectedItem(null);
							if (diagamb.isSetVid_tr()) vid_travm.setSelectedPcod(diagamb.getVid_tr()); else vid_travm.setSelectedItem(null);

							
						pdiag = new PdiagZ();
						GBoxStady.clearSelection();
						GBoxHar.clearSelection();
						try {
							pdiag = MainForm.tcl.getPdiagZ(TabDiag.getSelectedItem().getId());
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (PdiagNotFoundException e) {
							System.out.println("diagZ not found");
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
						rbPoz.setSelected(pdiag.getStady() == 1);
						rbRan.setSelected(pdiag.getStady() == 2);
						rbOstr.setSelected(pdiag.getXzab() == 1);
						rbHron.setSelected(pdiag.getXzab() == 2);
						cbPatol.setSelected(pdiag.getPat() == 1);
						cbPriznb.setSelected(pdiag.getPrizb() == 1);
						cbPrizni.setSelected(pdiag.getPrizi() == 1);
						
						pdisp = new Pdisp();
						try {
							pdisp = MainForm.tcl.getPdisp(TabDiag.getSelectedItem().getId());
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (PdispNotFoundException e) {
							System.out.println("disp not found");
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
						
						if (pdisp.isSetD_vz())
							tfDvz.setDate(pdisp.getD_vz());
						else
							tfDvz.setValue(null);
						
						if (pdisp.isSetDataish())
							tfFDatDIsh.setDate(pdisp.getDataish());
						else
							tfFDatDIsh.setValue(null);
						
						if (pdisp.isSetIshod())
							cbDish.setSelectedPcod(pdisp.getIshod());
						else
							cbDish.setSelectedItem(null);
						if (pdisp.isSetD_grup())
							cbDgrup.setSelectedPcod(pdisp.getD_grup());
						else
							cbDgrup.setSelectedItem(null);
						}	
					}
				}
		});			
		
				
				 vid_travm = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_ai0);
				 
				 JLabel lblvidtravm = new JLabel("Вид травмы");
				 
				 JButton bAddDiag = new JButton("");
				 bAddDiag.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
				 bAddDiag.setToolTipText("Добавить новый диагноз");
				 bAddDiag.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		TabDiag.requestFocus();
				  		try {
				  			StringClassifier mkb = MainForm.conMan.showMkbTreeForm("Диагноз", "");
				  			
				  			if (mkb != null) {
						  		diagamb = new PdiagAmb();
						  		diagamb.setId_obr(zapVr.getId_pvizit());
						  		diagamb.setNpasp(zapVr.getNpasp());
						  		diagamb.setDatap(System.currentTimeMillis());
						  		diagamb.setDatad(System.currentTimeMillis());
						  		diagamb.setCod_sp(MainForm.authInfo.getPcod());
						  		diagamb.setCdol(MainForm.authInfo.getCdol());
						  		diagamb.setPredv(true);
								diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
								diagamb.setDiag(mkb.pcod);
					 			TabDiag.addItem(diagamb);
				  			}
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}


				 	}	

				 });
				 
				 JLabel lblObstreg = new JLabel("Обстоятельства регистрации");
				 
				  cbObstreg = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abv);
				  
				  JPanel pStady = new JPanel();
				  pStady.setBorder(new TitledBorder(null, "\u0421\u0442\u0430\u0434\u0438\u044F \u0437\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
				  rbRan = new JRadioButton("Ранняя", false);
				  rbRan.setEnabled(false);
				  
				  rbPoz = new JRadioButton("Поздняя", false);
				  rbPoz.setEnabled(false);
				  pStady.add(rbRan);
				  pStady.add(rbPoz);
				  GBoxStady.add(rbRan);
				  GBoxStady.add(rbPoz);
				  
				  JPanel pXzab = new JPanel();
				  pXzab.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0425\u0430\u0440\u0430\u043A\u0442\u0435\u0440 \u0437\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				  rbOstr = new JRadioButton("Острое", false);
				  rbOstr.setEnabled(false);
				  pXzab.add(rbOstr);
				  
				  rbHron = new JRadioButton("Хроническое", false);
				  rbHron.setEnabled(false);
				  pXzab.add(rbHron);
				  GBoxHar.add(rbOstr);
				  GBoxHar.add(rbHron);
				  
				  JPanel pDisp = new JPanel();
				  pDisp.setBorder(new TitledBorder(null, "\u0414\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u043D\u044B\u0439 \u0443\u0447\u0435\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
				  
				  cbPatol = new JCheckBox("Патология, имеющая абсолютные противопоказания к вынашиванию беременности");
				  
				  cbPriznb = new JCheckBox("Связь с участием в боевых действиях");
				  
				  cbPrizni = new JCheckBox("Инвалидность");
				  
				  JButton bSaveDiag = new JButton("");
				  bSaveDiag.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
				  bSaveDiag.setToolTipText("Сохранить изменения");
				  bSaveDiag.addActionListener(new ActionListener() {
				  	public void actionPerformed(ActionEvent e) {
				  		try {
					  		diagamb.setDiag(TabDiag.getSelectedItem().getDiag());
					  		diagamb.setNamed(getTextOrNull(tpodiag.getText()));
					  		diagamb.setDatad(TabDiag.getSelectedItem().getDatad());
					  		if (jbosn.isSelected()) diagamb.setDiag_stat(1);
					  		if (jbsoput.isSelected())diagamb.setDiag_stat(3);
					  		if (jbosl.isSelected()) diagamb.setDiag_stat(2);
					  		if (cbObstreg.getSelectedPcod() != null) diagamb.setObstreg(cbObstreg.getSelectedPcod());
					  		else diagamb.unsetObstreg();
					  		if (vid_travm.getSelectedPcod() != null) diagamb.setVid_tr(vid_travm.getSelectedPcod());else diagamb.unsetVid_tr();
					  		
					  		pdiag = new PdiagZ();
					  		pdisp = new Pdisp();
					  		if (jbpredv.isSelected()) {
					  			diagamb.setPredv(true);
					  		}
					  		if (jbzakl.isSelected()) {
					  			
					  			diagamb.setPredv(false);
					  			pdiag.setId_diag_amb(diagamb.getId());
					  			pdiag.setNpasp(diagamb.getNpasp());
					  			pdiag.setDiag(diagamb.getDiag());
					  			pdiag.setCpodr(MainForm.authInfo.getCpodr());
					  			pdiag.setNmvd(diagamb.getObstreg());
					  			pdiag.setCod_sp(diagamb.getCod_sp());
					  			pdiag.setCdol_ot(diagamb.getCdol());
					  			pdiag.setNamed(diagamb.getNamed());
					  			if (rbOstr.isSelected()) pdiag.setXzab(1);
					  			if (rbHron.isSelected()) pdiag.setXzab(2);
					  			if (rbPoz.isSelected()) pdiag.setStady(2);
					  			if (rbRan.isSelected()) pdiag.setStady(1);
					  			if (cbPatol.isSelected()) pdiag.setPat(1);
					  			if (cbPriznb.isSelected()) pdiag.setPrizb(1);
					  			if (cbPrizni.isSelected()) pdiag.setPrizi(1);
					  			if (tfDvz.getDate() != null) pdiag.setDisp(1);
					  			MainForm.tcl.setPdiag(pdiag);
					  		}
				  		if (diagamb.isSetDiag() && diagamb.isSetNamed()) MainForm.tcl.UpdatePdiagAmb(diagamb);
				  		else JOptionPane.showMessageDialog(panel, "Введите недостающее значение");
				  		
			  			if (tfDvz.getDate() != null){
				  			pdisp.setId_diag(diagamb.getId());
				  			pdiag.setId_diag_amb(diagamb.getId());
				  			pdisp.setNpasp(diagamb.getNpasp());
				  			pdisp.setDiag(diagamb.getDiag());
				  			pdisp.setPcod(MainForm.authInfo.getCpodr());
				  			if (tfDvz.getDate() != null)
								pdisp.setD_vz(tfDvz.getDate().getTime());
				  			if (tfFDatDIsh.getDate() != null)
								pdisp.setDataish(tfFDatDIsh.getDate().getTime());
				  			if (cbDish.getSelectedPcod() != null) pdisp.setIshod(cbDish.getSelectedPcod());else pdisp.unsetIshod();
					  		if (cbDgrup.getSelectedPcod() != null) pdisp.setD_grup(cbDgrup.getSelectedPcod()); else pdisp.unsetD_grup();

	
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
				  
				  JButton DeleteDiag = new JButton("");
				  DeleteDiag.setIcon(new ImageIcon(Vvod.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
				  DeleteDiag.setToolTipText("Удалить диагноз");
				  DeleteDiag.addActionListener(new ActionListener() {
				  	public void actionPerformed(ActionEvent arg0) {
				  		try {
							MainForm.tcl.DeletePdiagAmb(TabDiag.getSelectedItem().getId());
							TabDiag.setData(MainForm.tcl.getPdiagAmb(Vvod.zapVr.getId_pvizit()));
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
					}
				  });
				  
				  JLabel lblODiag = new JLabel("Описание диагноза");
				  
				   tpodiag = new JEditorPane();
				  tpodiag.setBorder(UIManager.getBorder("TextField.border"));
				  GroupLayout gl_pds = new GroupLayout(pds);
				  gl_pds.setHorizontalGroup(
				  	gl_pds.createParallelGroup(Alignment.LEADING)
				  		.addGroup(gl_pds.createSequentialGroup()
				  			.addContainerGap()
				  			.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING, false)
				  						.addGroup(gl_pds.createSequentialGroup()
				  							.addComponent(lblODiag)
				  							.addPreferredGap(ComponentPlacement.RELATED)
				  							.addComponent(tpodiag))
				  						.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				  					.addPreferredGap(ComponentPlacement.UNRELATED)
				  					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING, false)
				  						.addComponent(DeleteDiag, 0, 0, Short.MAX_VALUE)
				  						.addComponent(bSaveDiag, 0, 0, Short.MAX_VALUE)
				  						.addComponent(bAddDiag, GroupLayout.PREFERRED_SIZE, 48, Short.MAX_VALUE)))
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(lblvidtravm)
				  					.addPreferredGap(ComponentPlacement.RELATED)
				  					.addComponent(vid_travm, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
				  					.addPreferredGap(ComponentPlacement.UNRELATED)
				  					.addComponent(lblObstreg)
				  					.addPreferredGap(ComponentPlacement.UNRELATED)
				  					.addComponent(cbObstreg, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
				  				.addComponent(pDisp, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(cbPatol, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(cbPriznb)
				  					.addPreferredGap(ComponentPlacement.RELATED)
				  					.addComponent(cbPrizni))
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(pStady, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  					.addGap(18)
				  					.addComponent(pXzab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				  				.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				  			.addContainerGap())
				  );
				  gl_pds.setVerticalGroup(
				  	gl_pds.createParallelGroup(Alignment.LEADING)
				  		.addGroup(gl_pds.createSequentialGroup()
				  			.addContainerGap(11, Short.MAX_VALUE)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.TRAILING)
				  				.addGroup(Alignment.LEADING, gl_pds.createSequentialGroup()
				  					.addComponent(bAddDiag)
				  					.addPreferredGap(ComponentPlacement.RELATED)
				  					.addComponent(bSaveDiag)
				  					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				  					.addComponent(DeleteDiag))
				  				.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
				  			.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addGap(26)
				  					.addComponent(lblODiag))
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addGap(9)
				  					.addComponent(tpodiag, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
				  			.addGap(10)
				  			.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.TRAILING)
				  				.addComponent(pStady, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(pXzab, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
				  			.addPreferredGap(ComponentPlacement.UNRELATED)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
				  				.addComponent(lblvidtravm)
				  				.addComponent(vid_travm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(lblObstreg)
				  				.addComponent(cbObstreg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addComponent(pDisp, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
				  				.addComponent(cbPriznb)
				  				.addComponent(cbPrizni))
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addComponent(cbPatol, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
				  			.addGap(42))
				  );
				  
				  JLabel lblDish = new JLabel("Исход д/у");
				  
				   cbDish = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abb);
				   cbDish.setEnabled(false);
				   
				   JLabel lblDvz = new JLabel("Дата взятия на д/у");
				   
				   tfDvz = new CustomDateEditor();
				   tfDvz.setEnabled(false);
				   tfDvz.setColumns(10);
				   
				   JLabel lblDGrup = new JLabel("Группа д/у");
				   
				    cbDgrup = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abc);
				    cbDgrup.setEnabled(false);
				    
				    JLabel lblDatDIsh = new JLabel("Дата установления исхода");
				    
				    tfFDatDIsh = new CustomDateEditor();
				    tfFDatDIsh.setEnabled(false);
				    tfFDatDIsh.setColumns(10);
				    GroupLayout gl_pDisp = new GroupLayout(pDisp);
				    gl_pDisp.setHorizontalGroup(
				    	gl_pDisp.createParallelGroup(Alignment.LEADING)
				    		.addGroup(gl_pDisp.createSequentialGroup()
				    			.addContainerGap()
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addComponent(lblDvz)
				    					.addGap(4)
				    					.addComponent(tfDvz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				    					.addPreferredGap(ComponentPlacement.RELATED)
				    					.addComponent(lblDatDIsh, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				    					.addPreferredGap(ComponentPlacement.RELATED)
				    					.addComponent(tfFDatDIsh, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addComponent(lblDGrup)
				    					.addPreferredGap(ComponentPlacement.RELATED)
				    					.addComponent(cbDgrup, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addComponent(lblDish)
				    					.addPreferredGap(ComponentPlacement.RELATED)
				    					.addComponent(cbDish, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)))
				    			.addContainerGap())
				    );
				    gl_pDisp.setVerticalGroup(
				    	gl_pDisp.createParallelGroup(Alignment.LEADING)
				    		.addGroup(gl_pDisp.createSequentialGroup()
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addGap(3)
				    					.addComponent(lblDvz))
				    				.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
				    					.addComponent(tfDvz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				    					.addComponent(lblDatDIsh)
				    					.addComponent(tfFDatDIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				    			.addPreferredGap(ComponentPlacement.RELATED)
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
				    				.addComponent(lblDGrup)
				    				.addComponent(cbDgrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    			.addPreferredGap(ComponentPlacement.RELATED)
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
				    				.addComponent(lblDish)
				    				.addComponent(cbDish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				    );
				    pDisp.setLayout(gl_pDisp);
				    pds.setLayout(gl_pds);
		
		JPanel pNazn = new JPanel();
		tabbedPane.addTab("Назначенное лечение", null, pNazn, null);
		GroupLayout gl_pNazn = new GroupLayout(pNazn);
		gl_pNazn.setHorizontalGroup(
			gl_pNazn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 538, Short.MAX_VALUE)
		);
		gl_pNazn.setVerticalGroup(
			gl_pNazn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 494, Short.MAX_VALUE)
		);
		pNazn.setLayout(gl_pNazn);

		
		JPanel pprint = new JPanel();
		tabbedPane.addTab("Направления", null, pprint, null);
		
		JScrollPane spPrint = new JScrollPane();
		GroupLayout gl_pprint = new GroupLayout(pprint);
		gl_pprint.setHorizontalGroup(
			gl_pprint.createParallelGroup(Alignment.LEADING)
				.addComponent(spPrint, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
		);
		gl_pprint.setVerticalGroup(
			gl_pprint.createParallelGroup(Alignment.LEADING)
				.addComponent(spPrint, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
		);
		
		JTabbedPane tabPrint = new JTabbedPane(JTabbedPane.TOP);
		spPrint.setViewportView(tabPrint);
		
		JPanel pNaprIssl = new JPanel();
		tabPrint.addTab("Направление на исследование", null, pNaprIssl, null);
		
		final JLabel lblmet = new JLabel("Методы");
		
		final JScrollPane spMetod = new JScrollPane();
		
		final JLabel lblPokazMet = new JLabel("Показатели");
		
		final JScrollPane spPokazMet = new JScrollPane();
		
		final JLabel lblMesto = new JLabel("Место");
		
		cbMesto = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		
		final JLabel lblKab = new JLabel("Каб.");
		
		tfKab = new JTextField();
		tfKab.setColumns(10);
		
		final JScrollPane spPokaz = new JScrollPane();
		spPokaz.setVisible(false);
		
		final JLabel lblOrgan = new JLabel("Органы и системы");
		lblOrgan.setVisible(false);
		
rbPokaz = new JRadioButton("Показатели");
rbPokaz.setVisible(false);
rbPokaz.addActionListener(new ActionListener() {
 	public void actionPerformed(ActionEvent e) {
 		rbMetodIssl.setSelected(false);
	 	lblmet.setVisible(false);
	 	spMetod.setVisible(false);
	 	spPokazMet.setVisible(false);
	 	tabMetod.setVisible(false);
	 	lblPokazMet.setVisible(false);
	 	spPokazMet.setVisible(false);
	 	tabPokazMet.setVisible(false);
	 	lblOrgan.setVisible(true);
	 	cbOrgan.setVisible(true);
	 	spPokaz.setVisible(true);
	 	tabPokaz.setVisible(true);

 	}
 });
		
		
		
		cbOrgan = new ThriftStringClassifierCombobox<StringClassifier>(true);
		cbOrgan.setVisible(false);
		cbOrgan.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				try {
					if (cbOrgan.getSelectedItem() != null)
					tabPokaz.setData(MainForm.tcl.getPokaz(cbVidIssl.getSelectedItem().pcod,cbOrgan.getSelectedItem().pcod));
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}	
}
		 });
		
		
		
		 rbMetodIssl = new JRadioButton("Методы исследований");
		 rbMetodIssl.setVisible(false);
		 rbMetodIssl.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 	rbPokaz.setSelected(false);	
			 	spPokaz.setVisible(false);
			 	tabPokaz.setVisible(false);
			 	lblOrgan.setVisible(false);
			 	cbOrgan.setVisible(false);
			 	cbVidIssl.setVisible(true);
			 	lblmet.setVisible(true);
			 	spMetod.setVisible(true);
			 	tabMetod.setVisible(true);
			 	lblPokazMet.setVisible(true);
			 	spPokazMet.setVisible(true);
			 	tabPokazMet.setVisible(true);
			 	}
			 });
		
		JLabel lblVidIssl = new JLabel("Вид исследования");
		
		cbVidIssl = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_p0e1);
		cbVidIssl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbVidIssl.getSelectedItem()!= null){
					try {
//						if (rbMetodIssl.isSelected()){//
								tabMetod.setData(MainForm.tcl.getMetod(cbVidIssl.getSelectedItem().pcod));
//						}
						if (rbPokaz.isSelected()){
							cbOrgan.setSelectedItem(null);
							cbOrgan.setData(MainForm.tcl.get_n_nz1(cbVidIssl.getSelectedItem().pcod));	
								}
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
							
						}
					}
										
				}
		});
		
		JButton butPrint = new JButton("Печать");
		butPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
	//				if (rbMetodIssl.isSelected()){
					if ((cbVidIssl.getSelectedItem() != null) && (tabMetod.getSelectedItem() != null)) {
						List<String> selItems = new ArrayList<>();
						for (PokazMet pokazMet : tabPokazMet.getData()) {
							if (pokazMet.vybor)
								selItems.add(pokazMet.getPcod());
						}
						if (selItems.size() != 0) {
							IsslMet isslmet = new IsslMet();
							isslmet.setPvizitId(TabPos.getSelectedItem().getId_obr());
							isslmet.setKodVidIssl(cbVidIssl.getSelectedItem().getPcod());
							isslmet.setUserId(MainForm.authInfo.getUser_id());
							isslmet.setNpasp(Vvod.zapVr.getNpasp());
							isslmet.setKodMetod(tabMetod.getSelectedItem().getObst());
							isslmet.setPokaz(selItems);
							if (cbMesto.getSelectedItem()!=null)isslmet.setMesto(cbMesto.getSelectedItem().getName());
							isslmet.setKab(getTextOrNull(tfKab.getText()));
							isslmet.setClpu_name(MainForm.authInfo.getClpu_name());
							isslmet.setCpodr_name(MainForm.authInfo.getCpodr_name());
							String servPath = MainForm.tcl.printIsslMetod(isslmet);
							String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
						}
					}
//				}
				if (rbPokaz.isSelected()){
					if ((cbVidIssl.getSelectedItem() != null) && (cbOrgan.getSelectedItem() != null)) {
							List<String> selItems = new ArrayList<>();
							for (Pokaz pokaz : tabPokaz.getData()) {
								if (pokaz.vybor)
									selItems.add(pokaz.getPcod());
							}
							if (selItems.size() != 0) {
								IsslPokaz pokaz = new IsslPokaz();
								pokaz.setPvizitId(TabPos.getSelectedItem().getId_obr());
								pokaz.setKodVidIssl(cbVidIssl.getSelectedItem().getPcod());
								pokaz.setUserId(MainForm.authInfo.getUser_id());
								pokaz.setClpu_name(MainForm.authInfo.getClpu_name());
								pokaz.setCpodr_name(MainForm.authInfo.getCpodr_name());
								pokaz.setNpasp(Vvod.zapVr.getNpasp());
								pokaz.setPokaz(selItems);
								
								if (cbMesto.getSelectedItem()!=null)pokaz.setMesto(cbMesto.getSelectedItem().getName());
								pokaz.setKab(getTextOrNull(tfKab.getText()));
								String servPath = MainForm.tcl.printIsslPokaz(pokaz);
								String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
							
							}
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
		
		
		
		GroupLayout gl_pNaprIssl = new GroupLayout(pNaprIssl);
		gl_pNaprIssl.setHorizontalGroup(
			gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pNaprIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(rbMetodIssl, GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
							.addGap(683))
						.addGroup(Alignment.TRAILING, gl_pNaprIssl.createSequentialGroup()
							.addComponent(spMetod, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(spPokaz, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(lblPokazMet, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblmet, Alignment.LEADING)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addComponent(lblVidIssl)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbVidIssl, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
									.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
										.addComponent(rbPokaz, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
										.addComponent(lblOrgan, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbOrgan, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(butPrint)
							.addContainerGap(887, Short.MAX_VALUE))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(lblMesto)
							.addGap(18)
							.addComponent(cbMesto, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(lblKab)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfKab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(843, Short.MAX_VALUE))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spPokazMet, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		gl_pNaprIssl.setVerticalGroup(
			gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pNaprIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbMetodIssl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbPokaz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbVidIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblVidIssl))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblmet))
						.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
							.addComponent(cbOrgan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblOrgan)))
					.addGap(8)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spMetod, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPokazMet))
						.addComponent(spPokaz, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
					.addGap(3)
					.addComponent(spPokazMet, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMesto)
						.addComponent(cbMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKab)
						.addComponent(tfKab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(115)
					.addComponent(butPrint)
					.addGap(22))
		);
		
		tabPokaz = new CustomTable<>(false, true, Pokaz.class, 0,"Код показателя",1,"Наименование",2,"Стоимость",5,"Выбор");
		tabPokaz.setEditableFields(true, 3);
		spPokaz.setViewportView(tabPokaz);
		tabPokaz.setFillsViewportHeight(true);
		
		tabPokazMet = new CustomTable<>(false,true,PokazMet.class,0,"Код",1,"Наименование",2,"Стоимость",4,"Выбор");
		tabPokazMet.setEditableFields(true, 3);
		spPokazMet.setViewportView(tabPokazMet);
		tabPokazMet.setFillsViewportHeight(true);
		
		tabMetod = new CustomTable<>(false,true,Metod.class,0,"Код",1,"Наименование");;
		spMetod.setViewportView(tabMetod);
		tabMetod.setFillsViewportHeight(true);
		tabMetod.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (tabMetod.getSelectedItem()!=null){
						try {
							tabPokazMet.setData(MainForm.tcl.getPokazMet(tabMetod.getSelectedItem().getObst()));
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
					}
				}	
			}
		});
		

		pNaprIssl.setLayout(gl_pNaprIssl);
		
		final JPanel pKons = new JPanel();
		tabPrint.addTab("Направление", null, pKons, null);
		
		JLabel lblN00 = new JLabel("Куда");
		
		 cbN00 = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		
		cbVidNapr = new JComboBox();
		cbVidNapr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if (cbVidNapr.getSelectedIndex() != 0)
						cbN00.setData(MainForm.tcl.get_n_n00());
					}
					 catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}
				}
		});
		cbVidNapr.setModel(new DefaultComboBoxModel(new String[] {"госпитализацию", "консультацию", "обследование"}));
		
		lblObosnov = new JLabel("<html>Обоснование для <br>\r\nнаправления");
		
		 tpObosnov = new JEditorPane();
		tpObosnov.setBorder(UIManager.getBorder("TextField.border"));
		
		butPrintNapr = new JButton("Печать");
		butPrintNapr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			try{
				if (cbVidNapr.getSelectedIndex() != 0){
					NaprKons naprkons = new NaprKons();
					naprkons.setUserId(MainForm.authInfo.getUser_id());
					naprkons.setNpasp(Vvod.zapVr.getNpasp());
					naprkons.setObosnov(tpObosnov.getText());
					if (cbN00.getSelectedItem()!=null) naprkons.setCpol(cbN00.getSelectedItem().getName());
					naprkons.setNazv(cbVidNapr.getSelectedItem().toString());
					naprkons.setCdol(MainForm.authInfo.getCdol());
					naprkons.setPvizitId(TabPos.getSelectedItem().getId_obr());
					naprkons.setCpodr_name(MainForm.authInfo.getCpodr_name());
					naprkons.setClpu_name(MainForm.authInfo.getClpu_name());
					String servPath = MainForm.tcl.printNaprKons(naprkons);
					String cliPath;
					cliPath = File.createTempFile("napr", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
				}
				else {
					Napr napr = new Napr();
					napr.setUserId(MainForm.authInfo.getUser_id());
					napr.setNpasp(Vvod.zapVr.getNpasp());
					napr.setObosnov(tpObosnov.getText());
					if (cbN00.getSelectedItem()!=null) napr.setClpu(cbN00.getSelectedItem().getName());
					napr.setCpodr_name(MainForm.authInfo.getCpodr_name());
					napr.setClpu_name(MainForm.authInfo.getClpu_name());
					napr.setPvizitId(TabPos.getSelectedItem().getId_obr());
					String servPath = MainForm.tcl.printNapr(napr);
					String cliPath;
					cliPath = File.createTempFile("napr", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);	
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
		
		JLabel label = new JLabel("на");
		
		GroupLayout gl_pKons = new GroupLayout(pKons);
		gl_pKons.setHorizontalGroup(
			gl_pKons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pKons.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pKons.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pKons.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbVidNapr, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pKons.createSequentialGroup()
							.addComponent(lblN00)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbN00, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pKons.createSequentialGroup()
							.addComponent(lblObosnov, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpObosnov, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE))
						.addComponent(butPrintNapr))
					.addContainerGap(294, Short.MAX_VALUE))
		);
		gl_pKons.setVerticalGroup(
			gl_pKons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pKons.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_pKons.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(cbVidNapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pKons.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblN00)
						.addComponent(cbN00, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pKons.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pKons.createSequentialGroup()
							.addGap(18)
							.addComponent(tpObosnov, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pKons.createSequentialGroup()
							.addGap(37)
							.addComponent(lblObosnov)))
					.addGap(17)
					.addComponent(butPrintNapr)
					.addContainerGap(577, Short.MAX_VALUE))
		);
		pKons.setLayout(gl_pKons);
		pprint.setLayout(gl_pprint);
		
		JPanel pOcenka = new ShablonTextPanel(9);
		tabbedPane.addTab("Оценка данных анамнеза", null, pOcenka, null);
		
		 tpOcenka = new ShablonTextField(9, 69, listShablon);
		 tpOcenka.setBorder(UIManager.getBorder("TextField.border"));
		GroupLayout gl_pOcenka = new GroupLayout(pOcenka);
		gl_pOcenka.setHorizontalGroup(
			gl_pOcenka.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pOcenka.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpOcenka, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(239, Short.MAX_VALUE))
		);
		gl_pOcenka.setVerticalGroup(
			gl_pOcenka.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pOcenka.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpOcenka, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(389, Short.MAX_VALUE))
		);
		pOcenka.setLayout(gl_pOcenka);
		
		pzakl = new  ShablonTextPanel(10);
		tabbedPane.addTab("Заключение", null, pzakl, null);
		
		final JLabel lblzakl = new JLabel("Заключение специалиста");
		
		tpzakl = new ShablonTextField(10, 65, listShablon);
		tpzakl.setBorder(UIManager.getBorder("TextField.border"));
		
		final JLabel lblrecom = new JLabel("Медицинские рекомендации");
		
		 tprecom = new ShablonTextField(10, 66, listShablon);
		tprecom.setBorder(UIManager.getBorder("TextField.border"));
		
		JLabel label_1 = new JLabel("Исход");
		
		cbish = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_ap0);
		GroupLayout gl_pzakl = new GroupLayout(pzakl);
		gl_pzakl.setHorizontalGroup(
			gl_pzakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pzakl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pzakl.createParallelGroup(Alignment.LEADING)
						.addComponent(lblzakl)
						.addComponent(lblrecom)
						.addComponent(tpzakl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tprecom, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(cbish, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pzakl.setVerticalGroup(
			gl_pzakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pzakl.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblzakl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpzakl, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblrecom)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tprecom, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_pzakl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pzakl.createSequentialGroup()
							.addGap(3)
							.addComponent(label_1))
						.addComponent(cbish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(221))
		);
		pzakl.setLayout(gl_pzakl);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(gl_contentPane);
	}
	
	public void showVvod(ZapVr zapVr) {
		Vvod.zapVr = zapVr;
				
		try {
			PatientCommonInfo inf;
				try {
					inf = MainForm.tcl.getPatientCommonInfo(Vvod.zapVr.npasp);
					tfPatient.setText("Пациент: "+inf.getFam()+" "+inf.getIm()+" "+inf.getOt()+" Номер и серия полиса: "+inf.getPoms_ser()+"  "+inf.getPoms_nom());
				} catch (PatientNotFoundException e) {
					e.printStackTrace();
				}
				if (zapVr.pol != 1 ) butBer.setEnabled(true); else butBer.setEnabled(false);
			TabPos.setData(MainForm.tcl.getPvizitAmb(zapVr.getId_pvizit()));
			if (TabPos.getRowCount() > 0)
				TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
			TabDiag.setData(MainForm.tcl.getPdiagAmb(zapVr.getId_pvizit()));
			
			//c_obr.setSelectedPcod(TabPos.getSelectedItem().getId_obr());
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
		
		setVisible(true);
	}
	
	public void onConnect() {
		try {
			TabPos.setStringClassifierSelector(2, ConnectionManager.instance.getStringClassifier(StringClassifiers.n_s00));
			cbMesto.setData(MainForm.tcl.get_n_lds(MainForm.authInfo.clpu));
			cbN00.setData(MainForm.tcl.get_n_m00(MainForm.authInfo.clpu));
			
			
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
	}
	
	public void loadConfig() {
		try {
			StringReader sr = new StringReader(MainForm.authInfo.config);
			StreamSource src = new StreamSource(sr);
			DOMResult res = new DOMResult();
			TransformerFactory.newInstance().newTransformer().transform(src, res);
			Document document = (Document) res.getNode();
					if (getElement(document, "jalob_dyh")!=null){
						JEditorPane tpJalobd = new JEditorPane();
						Jalob.add(tpJalobd);
											
			}
					else {
						
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Element getElement(Document doc, String name){
		NodeList nls;
		nls = doc.getElementsByTagName(name);
		if (nls.getLength()>0) {
			return (Element) nls.item(0);
		}
		return null;
	}
	
	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
