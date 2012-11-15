package ru.nkz.ivcgzo.dbMaintenance;

import ru.nkz.ivcgzo.classifierImporter.ClassifierImporter;
import ru.nkz.ivcgzo.libsMd5Updater.LibsMd5Updater;
import ru.nkz.ivcgzo.shablonImporter.ShablonImporter;
import ru.nkz.ivcgzo.zabolDataImporter.ZabolDataImporter;

public class DBMaintenance {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("This program does some suportive shit.");
		
		if (args.length < 1)
			usage();
		
		try {
			switch (args[0]) {
			case "--impClass":
				importClassifiers(args);
				break;
			case "--updLibsMd5":
				updateLibsMd5(args);
				break;
			case "--impShab":
				importShablon(args);
				break;
			case "--impZabol":
				importZabol(args);
				break;
			default:
				usage();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void usage() {
		System.out.println("Usage: <command> [command params]");
		
		System.out.println("Commands:");
		
		System.out.println("\t--impClass <path_to_xml>");
		System.out.println("\tUpdates classifiers specified in xml.");
		
		System.out.println("\t--updLibsMd5 <path_to_xml>");
		System.out.println("\tUpdates libraries md5 and sizes in database.");
		
		System.out.println("Guess the format of an xml for yourself.");
		System.exit(2);
	}
	
	private static void importClassifiers(String[] args) throws Exception {
		ClassifierImporter cimp = new ClassifierImporter(args[1]);
		cimp.importClassifiers();
	}
	
	private static void updateLibsMd5(String[] args) throws Exception {
		LibsMd5Updater lmu = new LibsMd5Updater(args[1]);
		lmu.updateLibsMd5();
	}
	
	private static void importShablon(String[] args) throws Exception {
		ShablonImporter shImp = new ShablonImporter(args[1]);
		shImp.importShablonOsm();
	}
	
	private static void importZabol(String[] args) throws Exception {
		ZabolDataImporter zdi = new ZabolDataImporter();
		zdi.importData(args);
	}
}
