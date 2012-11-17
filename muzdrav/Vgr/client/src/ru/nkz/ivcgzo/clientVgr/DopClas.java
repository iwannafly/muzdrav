package ru.nkz.ivcgzo.clientVgr;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



public class DopClas extends JFrame {
	JFileChooser fc;
	String pathfile = "";
	
	public DopClas() {
		setTitle("Загрузка информации");
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
	}
	
	public void OpenWindowFileChooser(String title) {
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("rar","rar");
		fc.setFileFilter(filter);
		fc.setDialogTitle(title);
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
			pathfile = fc.getSelectedFile().getAbsolutePath();
            System.out.println(pathfile);
        }
	}

}
