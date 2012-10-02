package ru.nkz.ivcgzo.clientViewSelect;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;

public class ViewTreeForm extends ViewSelectForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2337307028219064562L;
;

	/**
	 * 
	 */
	public ViewTreeForm() {
		
		JTree tree = new JTree();
		spClassifier.setViewportView(tree);
		//getContentPane().add(tree, BorderLayout.CENTER);
		createGUI();
		initialize();
	}

	private void initialize() {
			
	}
}
