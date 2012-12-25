package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientLab.ClientLab;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientMedication.ClientMedication;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ClassifierManager;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.MedPolErrorsForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.PaspErrorsForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.PatientInfoForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.PatientSearchForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewIntegerClassifierForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewMkbTreeForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewMrabTreeForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewPolpTreeForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewStringClassifierForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;

public class MainForm extends Client<ThriftViewSelect.Client> {
	public static ThriftViewSelect.Client tcl;
	public static ClassifierManager ccm;
	private JFrame frame;
	private JTextField tfSearch;
	private static CustomTable<StringClassifier, StringClassifier._Fields> table;
	public static Client<ThriftViewSelect.Client> instance;
	public PatientSearchForm srcFrm;
	public ViewMkbTreeForm mkbFrm;
	public ViewIntegerClassifierForm intFrm;
	public ViewStringClassifierForm strFrm;
	public ViewPolpTreeForm polpFrm;
	public ViewMrabTreeForm mrabFrm;
	public PatientInfoForm infFrm;
	public ClientLab labFrm;
	public ru.nkz.ivcgzo.clientReception.MainForm recFrm;
	public ClientMedication medFrm;
	public PaspErrorsForm paspFrm;
	public MedPolErrorsForm medPolFrm;

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftViewSelect.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initModalForms();
		
		initialize();
		setFrame(frame);
		instance = this;
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle("Нормативно-справочная информация");
		frame.setBounds(100, 100, 750, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonGroup group = new ButtonGroup();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Показать");
		menuBar.add(mnNewMenu);
		
		final JMenuItem rbMenuItemAll = new JRadioButtonMenuItem("Все");
		mnNewMenu.add(rbMenuItemAll);
		group.add(rbMenuItemAll);
		rbMenuItemAll.setSelected(true);
		
		rbMenuItemAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbMenuItemAll.isSelected()) {
					table.setRowSorter(null);
				}
			}
		});
		
		final JMenuItem rbMenuItemStat = new JRadioButtonMenuItem("Статические");

		mnNewMenu.add(rbMenuItemStat);
		group.add(rbMenuItemStat);

		rbMenuItemStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbMenuItemStat.isSelected()) {
					table.setRowSorter(null);
					setClassifierFilter("n_");
				}
			}
		});


		
		final JMenuItem rbMenuItemDyn = new JRadioButtonMenuItem("Редактируемые");
		mnNewMenu.add(rbMenuItemDyn);
		group.add(rbMenuItemDyn);

		rbMenuItemDyn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbMenuItemDyn.isSelected()) {
					table.setRowSorter(null);					
					setClassifierFilter("s_");
				}
			}
			
		});
		
		tfSearch = new JTextField();
		frame.getContentPane().add(tfSearch, BorderLayout.NORTH);
		tfSearch.setColumns(10);
		
		tfSearch.getDocument().addDocumentListener(new DocumentListener ()
	       {
			  public void changedUpdate(DocumentEvent documentEvent) {
			      search();
			  }
			  public void insertUpdate(DocumentEvent documentEvent) {
			      search();
			  }
			  public void removeUpdate(DocumentEvent documentEvent) {
			      search();
			  }
	       });
		
		JScrollPane spClassifier = new JScrollPane();
		frame.getContentPane().add(spClassifier, BorderLayout.CENTER);
		table = new CustomTable<>(false, true, StringClassifier.class, 0, "Код", 1, "Наименование");
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		spClassifier.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String className = table.getSelectedItem().pcod;
					try {
						if (className.equals("n_c00")) {
							conMan.showMkbTreeForm("Диагноз", "");
						} else if (className.equals("n_nsipol")) {
							conMan.showPolpTreeForm("Поликлиники Кемеровской Области", -1, -1, -1);
						} else if (className.equals("n_z43")) {
							conMan.showMrabTreeForm("Место работы", -1);
						} else if (tcl.isClassifierPcodInteger(className)) {
							ViewTablePcodIntForm VSPIForm = new ViewTablePcodIntForm();
							MainForm.instance.addChildFrame(VSPIForm);
							ViewTablePcodIntForm.tableFill(className);
							VSPIForm.setVisible(true);
						} else {
							ViewTablePcodStringForm VSPSForm = new ViewTablePcodStringForm();
							MainForm.instance.addChildFrame(VSPSForm);
							ViewTablePcodStringForm.tableFill(className);
							VSPSForm.setVisible(true);
						}
					} catch (TException e1) {
						e1.printStackTrace();
						conMan.reconnect(e1);
					}
				}
			}
		});
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftViewSelect.Client) {
			tcl = thrClient;
			try {
				table.setData(tcl.getVSStringClassifierView("n_spr"));
			} catch (TException e) {
				e.printStackTrace();
				conMan.reconnect(e);
			}
		}
	}

	// Быстрый поиск по таблице
    private void search()
    {
    	String target = tfSearch.getText();
    	for(int row = 0; row < table.getRowCount(); row++)
    		for(int col = 0; col < table.getColumnCount(); col++)
    		{
    			String next = table.getValueAt(row, col).toString();
    			Pattern pt = Pattern.compile(target.toLowerCase());
    			Matcher mt = pt.matcher(next.toLowerCase());
    			if (mt.find())
    			{
    				table.setRowSelectionInterval(row, row);
    				table.setLocation(0, -((table.getRowHeight() * row) - 100));
    				return;
    			}
    		}
    }
    
    //Фильтрация классификаторов
	public void setClassifierFilter (String beginsWith) {
		TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<>(table.getModel());
		RowFilter<Object, Object> rf = RowFilter.regexFilter("^"+beginsWith, 0);
		table.setRowSorter(sorter);
		sorter.setRowFilter(rf);
	}
	
	private void initModalForms() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		if (ccm == null)
			ccm = new ClassifierManager();
		
		srcFrm = new PatientSearchForm();
		mkbFrm = new ViewMkbTreeForm();
		intFrm = new ViewIntegerClassifierForm();
		strFrm = new ViewStringClassifierForm();
		polpFrm = new ViewPolpTreeForm();
		mrabFrm = new ViewMrabTreeForm();
		infFrm = new PatientInfoForm();
		labFrm = new ClientLab(conMan, authInfo, 0);
		medFrm = new ClientMedication(conMan, authInfo, 0);
		recFrm = new ru.nkz.ivcgzo.clientReception.MainForm(conMan, authInfo, 0);
		paspFrm = new PaspErrorsForm();
		medPolFrm = new MedPolErrorsForm();
	}
	
	@Override
	public Object showModal(IClient parent, Object... params) {
		if (conMan != null && parent != null)
			if (params.length > 0) {
				JDialog dialog = null;
				switch ((int) params[0]) {
				case 1:
					setFrame(srcFrm);
					srcFrm.setTitle((String) params[1]);
					dialog = prepareModal(parent);
					if ((boolean) params[2])
						srcFrm.clearFields();
					srcFrm.setOptionalParamsEnabledState(!(boolean) params[3]);
					srcFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						List<PatientBriefInfo> srcResList = srcFrm.getSearchResults();
						if (srcResList != null) {
							int[] res = new int[srcResList.size()];
							for (int i = 0; i < srcResList.size(); i++)
								res[i] = srcResList.get(i).npasp;
							
							return res;
						}
					} finally {
						setFrame(frame);
						srcFrm.removeModalityListener();
						disposeModal();
					}
					break;
					
				case 2:
					setFrame(mkbFrm);
					mkbFrm.setTitle((String) params[1]);
					dialog = prepareModal(parent);
					mkbFrm.prepare((String) params[2]);
					mkbFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						return mkbFrm.getResults();
					} finally {
						setFrame(frame);
						mkbFrm.removeModalityListener();
						disposeModal();
					}
					
				case 3:
				case 4:
				case 5:
				case 6:
					try {
						conMan.setClient(this);
						conMan.connect(getPort());
						
						switch ((int) params[0]) {
						case 3:
							return ccm.getIntegerClassifier((IntegerClassifiers) params[1], (ClassifierSortOrder) params[2], (ClassifierSortFields) params[3]);
						case 4:
							return ccm.getIntegerClassifier((IntegerClassifiers) params[1]);
						case 5:
							return ccm.getStringClassifier((StringClassifiers) params[1], (ClassifierSortOrder) params[2], (ClassifierSortFields) params[3]);
						case 6:
							return ccm.getStringClassifier((StringClassifiers) params[1]);
						}
					} catch (Exception e) {
						conMan.disconnect(getPort());
						conMan.setClient(parent);
					}
					break;
					
				case 7:
				case 8:
					setFrame(intFrm);
					intFrm.rightSnapWindow();
					dialog = prepareModal(parent);
					intFrm.setModalityListener();
					if ((int) params[0] == 7)
						intFrm.prepare((IntegerClassifiers) params[1], (ClassifierSortOrder) params[2], (ClassifierSortFields) params[3]);
					else
						intFrm.prepare((IntegerClassifiers) params[1], ClassifierSortOrder.none, null);
					dialog.setVisible(true);
					try {
						return intFrm.getResults();
					} finally {
						setFrame(frame);
						intFrm.removeModalityListener();
						disposeModal();
					}
					
				case 9:
				case 10:
					setFrame(strFrm);
					strFrm.rightSnapWindow();
					dialog = prepareModal(parent);
					strFrm.setModalityListener();
					if ((int) params[0] == 9)
						strFrm.prepare((StringClassifiers) params[1], (ClassifierSortOrder) params[2], (ClassifierSortFields) params[3]);
					else
						strFrm.prepare((StringClassifiers) params[1], ClassifierSortOrder.none, null);
					dialog.setVisible(true);
					try {
						return strFrm.getResults();
					} finally {
						setFrame(frame);
						strFrm.removeModalityListener();
						disposeModal();
					}
					
				case 11:
					setFrame(polpFrm);
					polpFrm.setTitle((String) params[1]);
					dialog = prepareModal(parent);
					polpFrm.prepare((int) params[2], (int) params[3], (int) params[4]);
					polpFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						return polpFrm.getResults();
					} finally {
						setFrame(frame);
						polpFrm.removeModalityListener();
						disposeModal();
					}
					
				case 12: 
					setFrame(mrabFrm);
					mrabFrm.setTitle((String) params[1]);
					dialog = prepareModal(parent);
					mrabFrm.prepare((int) params[2]);
					mrabFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						return mrabFrm.getResults();
					} finally {
						setFrame(frame);
						mrabFrm.removeModalityListener();
						disposeModal();
					}
					
				case 13:
				case 14:
				case 15:
				case 16:
					try {
						conMan.setClient(this);
						conMan.connect(getPort());
						
						switch ((int) params[0]) {
						case 13:
							return ccm.getNameFromPcodInteger((IntegerClassifiers) params[1], (int) params[2]);
						case 14:
							return ccm.getNameFromPcodString((StringClassifiers) params[1], (String) params[2]);
						case 15:
							return ccm.getPcodFromNameInteger((IntegerClassifiers) params[1], (String) params[2]);
						case 16:
							return ccm.getPcodFromNameString((StringClassifiers) params[1], (String) params[2]);
						}
					} catch (Exception e) {
						conMan.disconnect(getPort());
						conMan.setClient(parent);
					}
					break;
					
				case 17:
					setFrame(infFrm);
					infFrm.setTitle((String) params[1]);
					dialog = prepareModal(parent);
					infFrm.update((int) params[2]);
					infFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						return infFrm.getResults();
					} finally {
						setFrame(frame);
						infFrm.removeModalityListener();
						disposeModal();
					}
					
				case 18:
					labFrm.showModal(parent, params[1], params[2], params[3], params[4], params[5]);
					break;
					
				case 19:
					recFrm.showModal(parent, params[1], params[2], params[3], params[4], params[5]);
					break;

				case 20:
				    medFrm.showModal(parent, params[1], params[2], params[3], params[4], params[5]);
				    break;
				    
				case 21:
					setFrame(paspFrm);
					dialog = prepareModal(parent);
					paspFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						return paspFrm.getResults();
					} finally {
						setFrame(frame);
						paspFrm.removeModalityListener();
						disposeModal();
					}
					
				case 22:
					setFrame(medPolFrm);
					dialog = prepareModal(parent);
					medPolFrm.setModalityListener();
					dialog.setVisible(true);
					try {
						return medPolFrm.getResults();
					} finally {
						setFrame(frame);
						medPolFrm.removeModalityListener();
						disposeModal();
					}
				}
			}
				
		return null;
	}
}
