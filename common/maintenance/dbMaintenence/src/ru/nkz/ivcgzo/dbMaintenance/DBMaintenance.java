package ru.nkz.ivcgzo.dbMaintenance;

import ru.nkz.ivcgzo.classifierImporter.ClassifierImporter;

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
		System.out.println("\tUpdates classifiers specified in xml. Guess the format of an xml for yourself.");
		
		System.exit(2);
	}
	
	private static void importClassifiers(String[] args) throws Exception {
		ClassifierImporter cimp = new ClassifierImporter(args[1]);
		cimp.importClassifiers();
	}

}
