package ru.nkz.ivcgzo.clientViewSelect;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ClassifierManager;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.PatientSearchForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewIntegerClassifierForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewMkbTreeForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewStringClassifierForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;

public class MainForm extends Client<ThriftViewSelect.Client> {
	public static ThriftViewSelect.Client tcl;
	public JFrame frame;
	public static ClassifierManager ccm;
	public PatientSearchForm srcFrm;
	public ViewMkbTreeForm mkbFrm;
	public ViewIntegerClassifierForm intFrm;
	public ViewStringClassifierForm strFrm;

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftViewSelect.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initModalForms();
		
//		setFrame(new ViewTablePcodStringForm());
		//setFrame(new ViewTablePcodIntForm());
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	public class metaClassifier {
		
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftViewSelect.Client) {
			tcl = thrClient;
			try { 
//				if (tcl.isClassifierPcodInteger("n_l01")) System.out.print("ololo");
				//ViewTablePcodIntForm.tableFill();
				//else ViewTablePcodStringForm.tableFill();
//				ViewTablePcodStringForm.tableFill();
				//NSForm.
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
	}

	private void initModalForms() {
		if (ccm == null)
			ccm = new ClassifierManager();
		
		srcFrm = new PatientSearchForm();
		mkbFrm = new ViewMkbTreeForm();
		intFrm = new ViewIntegerClassifierForm();
		strFrm = new ViewStringClassifierForm();
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
						mkbFrm.removeModalityListener();
						disposeModal();
					}
					
				case 3: //ld int
				case 4: //ld int wo prms
				case 5: //ld str
				case 6: //ld str wo prms
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
						strFrm.removeModalityListener();
						disposeModal();
					}
					
				}
			}
		
		return null;
	}
}
