package ru.nkz.ivcgzo.clientViewSelect;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ClassifierManager;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.PatientSearchForm;
import ru.nkz.ivcgzo.clientViewSelect.modalForms.ViewMkbTreeForm;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;

public class MainForm extends Client<ThriftViewSelect.Client> {
	public static ThriftViewSelect.Client tcl;
	public JFrame frame;
	public static ClassifierManager ccm;
	public PatientSearchForm srcFrm;
	public ViewMkbTreeForm mkbFrm;

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
	}
	
	@Override
	public Object showModal(IClient parent, Object... params) {
		if (conMan != null && parent != null)
			if (params.length > 0) {
				switch ((int) params[0]) {
				case 1:
					setFrame(srcFrm);
					srcFrm.setTitle((String) params[1]);
					if ((boolean) params[2])
						srcFrm.clearFields();
					srcFrm.setOptionalParamsEnabledState(!(boolean) params[3]);
					srcFrm.setModalityListener();
					prepareModal(parent).setVisible(true);
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
					mkbFrm.prepare((String) params[2]);
					mkbFrm.setModalityListener();
					prepareModal(parent).setVisible(true);
					try {
						return mkbFrm.getResults();
					} finally {
						mkbFrm.removeModalityListener();
						disposeModal();
					}
				}
			}
		
		return null;
	}
}
