package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_0;
import ru.nkz.ivcgzo.thriftViewSelect.mrab_0;
import ru.nkz.ivcgzo.thriftViewSelect.polp_0;

public class ClassifierManager {
	private Map<Integer, List<IntegerClassifier>> intClassList;
	private Map<Integer, List<StringClassifier>> strClassList;
	private List<mkb_0> mkbTreeClass;
	private List<polp_0> polpTreeClass;
	private List<mrab_0> mrabTreeClass;
	
	public ClassifierManager() {
		intClassList = new HashMap<>();
		strClassList = new HashMap<>();
	}
	
	private Integer getKey(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		if (ord.getValue() == 0)
			return cls.getValue();
		else
			return (fld.getValue() << 0x1c) | (ord.getValue() << 0x18) | cls.getValue();
	}
	
	private Integer getKey(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		if (ord.getValue() == 0)
			return cls.getValue();
		else
			return (fld.getValue() << 0x1c) | (ord.getValue() << 0x18) | cls.getValue();
	}
	
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException, TException {
		int key = getKey(cls, ord, fld);
		
		if (!intClassList.containsKey(key))
			intClassList.put(key, MainForm.tcl.getIntegerClassifierSorted(cls, ord, fld));
		
		return intClassList.get(key);
	}
	
	public List<IntegerClassifier> getIntegerClassifier(IntegerClassifiers cls) throws KmiacServerException, TException {
		return getIntegerClassifier(cls, ClassifierSortOrder.none, null);
	}
	
	public List<StringClassifier> getStringClassifier(StringClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) throws KmiacServerException, TException {
		int key = getKey(cls, ord, fld);
		
		if (!strClassList.containsKey(key))
			strClassList.put(key, MainForm.tcl.getStringClassifierSorted(cls, ord, fld));
		
		return strClassList.get(key);
	}
	
	public List<StringClassifier> getStringClassifier(StringClassifiers cls) throws KmiacServerException, TException {
		return getStringClassifier(cls, ClassifierSortOrder.none, null);
	}
	
	public List<mkb_0> getMkbTreeClassifier() throws KmiacServerException, TException {
		if (mkbTreeClass == null)
			mkbTreeClass = MainForm.tcl.getMkb_0();
		
		return mkbTreeClass;
	}
	
	public List<polp_0> getPolpTreeClassifier() throws KmiacServerException, TException {
		if (polpTreeClass == null)
			polpTreeClass = MainForm.tcl.getPolp_0();
		
		return polpTreeClass;
	}
	
	public List<mrab_0> getMrabTreeClassifier() throws KmiacServerException, TException {
		if (mrabTreeClass == null)
			mrabTreeClass = MainForm.tcl.getMrab_0();
		
		return mrabTreeClass;
	}
}
