package ru.nkz.ivcgzo.clientOsm.patientInfo;

import java.util.List;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;

public class Classifiers {
	public static List<IntegerClassifier> n_z30;
	public static List<IntegerClassifier> n_am0;
	public static List<IntegerClassifier> n_az9;
	public static List<IntegerClassifier> n_z43;
	public static List<IntegerClassifier> n_kas;
	public static List<IntegerClassifier> n_n00;
	public static List<IntegerClassifier> n_l01;
	public static List<IntegerClassifier> n_az0;
	public static List<IntegerClassifier> n_l02;
	
	public static boolean load(ThriftOsm.Client tcl) {
		try {
			if (n_z30 == null)
				n_z30 = tcl.get_n_z30();
			if (n_am0 == null)
				n_am0 = tcl.get_n_am0();
			if (n_az9 == null)
				n_az9 = tcl.get_n_az9();
			if (n_z43 == null)
				n_z43 = tcl.get_n_z43();
			if (n_kas == null)
				n_kas = tcl.get_n_kas();
			if (n_n00 == null)
				n_n00 = tcl.get_n_n00();
			if (n_l01 == null)
				n_l01 = tcl.get_n_l01();
			if (n_az0 == null)
				n_az0 = tcl.get_n_az0();
			if (n_l02 == null)
				n_l02 = tcl.get_n_l02();
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}
