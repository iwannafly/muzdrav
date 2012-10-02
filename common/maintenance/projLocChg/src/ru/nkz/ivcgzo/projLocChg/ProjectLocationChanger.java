package ru.nkz.ivcgzo.projLocChg;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProjectLocationChanger {
	String metaPath;
	List<ProjectDescriptor> locFiles;
	
	private JFrame frmEclipseProjectsRoot;
	private JButton btnMetaChoose;
	private ProjectsRootFolderJList<ProjectsRootFolder> lstRootDirs;
	private JTextField tbMetaDir;
	private JTextField tbRootChg;
	private JButton btnRootChgAll;
	private JButton btnRootChoose;
	private ProjectDescriptorJList<ProjectDescriptor> lstProjects;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					ProjectLocationChanger window = new ProjectLocationChanger();
					window.frmEclipseProjectsRoot.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProjectLocationChanger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEclipseProjectsRoot = new JFrame();
		frmEclipseProjectsRoot.setTitle("Eclipse projects root changer");
		frmEclipseProjectsRoot.setBounds(100, 100, 640, 640);
		frmEclipseProjectsRoot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlMetaDir = new JPanel();
		pnlMetaDir.setBorder(new TitledBorder(null, ".metadata folder", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlRootDirs = new JPanel();
		pnlRootDirs.setBorder(new TitledBorder(null, "Estimated project root folders", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frmEclipseProjectsRoot.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlRootDirs, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
						.addComponent(pnlMetaDir, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlMetaDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlRootDirs, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JScrollPane scpRootDirs = new JScrollPane();
		
		JPanel pnlRootChg = new JPanel();
		pnlRootChg.setBorder(new TitledBorder(null, "Change root folder", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_pnlRootDirs = new GroupLayout(pnlRootDirs);
		gl_pnlRootDirs.setHorizontalGroup(
			gl_pnlRootDirs.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlRootDirs.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlRootDirs.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlRootChg, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addComponent(scpRootDirs, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlRootDirs.setVerticalGroup(
			gl_pnlRootDirs.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRootDirs.createSequentialGroup()
					.addContainerGap()
					.addComponent(scpRootDirs, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlRootChg, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		btnRootChgAll = new JButton("Change");
		btnRootChgAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectsRootFolder prf = lstRootDirs.getSelectedValue();
				if (prf != null) {
					changeRootFolder(tbRootChg.getText(), prf.getProjectDescriptors());
					analyzeProjectLocations();
				}
			}
		});

		tbRootChg = new JTextField();
		DocumentListener rootChgDl = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setChangeRootEnabled();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setChangeRootEnabled();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setChangeRootEnabled();
			}
			
			private void setChangeRootEnabled() {
				ProjectsRootFolder prf = (lstRootDirs.getModel().getSize() == 0) ? null : lstRootDirs.getSelectedValue();
				boolean rootIsGood = false;
				if (prf != null)
					rootIsGood = checkRootFolder(tbRootChg.getText(), prf.getProjectDescriptors());
				btnRootChgAll.setEnabled(rootIsGood);
			}
		};
		tbRootChg.getDocument().addDocumentListener(rootChgDl);
		
		btnRootChoose = new JButton("Choose");
		btnRootChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = (checkPathExistance(tbRootChg.getText())) ? new JFileChooser(tbRootChg.getText()) : new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fc.showDialog(frmEclipseProjectsRoot, "Choose") == JFileChooser.APPROVE_OPTION)
					tbRootChg.setText(fc.getSelectedFile().getAbsolutePath());
			}
		});
		
		JScrollPane scpProjects = new JScrollPane();
		
		GroupLayout gl_pnlRootChg = new GroupLayout(pnlRootChg);
		gl_pnlRootChg.setHorizontalGroup(
			gl_pnlRootChg.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlRootChg.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlRootChg.createParallelGroup(Alignment.LEADING)
						.addComponent(scpProjects, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_pnlRootChg.createSequentialGroup()
							.addComponent(tbRootChg, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRootChoose, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnRootChgAll, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_pnlRootChg.setVerticalGroup(
			gl_pnlRootChg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlRootChg.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlRootChg.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbRootChg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRootChoose))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scpProjects, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRootChgAll)
					.addContainerGap())
		);
		
		lstProjects = new ProjectDescriptorJList<>();
		scpProjects.setViewportView(lstProjects);
		pnlRootChg.setLayout(gl_pnlRootChg);
		
		lstRootDirs = new ProjectsRootFolderJList<>();
		lstRootDirs.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					tbRootChg.setText(lstRootDirs.getSelectedValue().getPath());
					lstProjects.setData(lstRootDirs.getSelectedValue().getProjectDescriptors());
				}
			}
		});
		scpRootDirs.setViewportView(lstRootDirs);
		pnlRootDirs.setLayout(gl_pnlRootDirs);
		
		tbMetaDir = new JTextField();
		DocumentListener metaDirDl = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setMetaAnalyzeEnabled();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setMetaAnalyzeEnabled();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setMetaAnalyzeEnabled();
			}
			
			private void setMetaAnalyzeEnabled() {
				lstRootDirs.setData(null);
				lstProjects.setData(null);
				tbRootChg.setText("");
				boolean metaIsGood = checkMetaDir();
				btnRootChoose.setEnabled(metaIsGood);
				if (metaIsGood)
					analyzeProjectLocations();
			}
		};
		tbMetaDir.getDocument().addDocumentListener(metaDirDl);
		metaDirDl.changedUpdate(null);
		rootChgDl.changedUpdate(null);
		
		btnMetaChoose = new JButton("Choose");
		btnMetaChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = (checkPathExistance(tbMetaDir.getText())) ? new JFileChooser(tbMetaDir.getText()) : new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fc.showDialog(frmEclipseProjectsRoot, "Choose") == JFileChooser.APPROVE_OPTION)
					tbMetaDir.setText(fc.getSelectedFile().getAbsolutePath());
			}
		});
		
		GroupLayout gl_pnlMetaDir = new GroupLayout(pnlMetaDir);
		gl_pnlMetaDir.setHorizontalGroup(
			gl_pnlMetaDir.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlMetaDir.createSequentialGroup()
					.addContainerGap()
					.addComponent(tbMetaDir, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnMetaChoose, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlMetaDir.setVerticalGroup(
			gl_pnlMetaDir.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMetaDir.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlMetaDir.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnMetaChoose)
						.addComponent(tbMetaDir, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		pnlMetaDir.setLayout(gl_pnlMetaDir);
		frmEclipseProjectsRoot.getContentPane().setLayout(groupLayout);
	}
	
	private boolean checkPathExistance(String first, String... more) {
		return Paths.get(first, more).toFile().exists();
	}
	
	private boolean checkMetaDir() {
		metaPath = tbMetaDir.getText();
		
		if (!new File(metaPath).getName().equals(".metadata"))
			return false;
		
		if (!checkPathExistance(metaPath))
			return false;
		
		if (!checkPathExistance(metaPath, "version.ini"))
			return false;
		
		if (!checkPathExistance(metaPath, ".plugins", "org.eclipse.core.resources", ".projects"))
			return false;
		
		return true;
	}
	
	private void analyzeProjectLocations() {
		File projectMetaPath = Paths.get(metaPath, ".plugins", "org.eclipse.core.resources", ".projects").toFile();
		
		try {
			locFiles = new ArrayList<>();
			for (File locPath : projectMetaPath.listFiles())
				if (locPath.isDirectory())
					if (new File(locPath, "org.eclipse.jdt.core").exists())
					{
						ProjectDescriptor lf = new ProjectDescriptor(locPath.getAbsolutePath());
						if (lf.isExternal())
							locFiles.add(lf);
					}
			List<ProjectsRootFolder> rfl = ProjectsRootFolder.getFolders(locFiles);
			lstRootDirs.setData(rfl);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frmEclipseProjectsRoot, e, "Loading projects failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void changeRootFolder(String rootPath, List<ProjectDescriptor> projDescList) {
		try {
			for (ProjectDescriptor projDesc : projDescList)
				projDesc.saveLocFile(rootPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean checkRootFolder(String rootPath, List<ProjectDescriptor> projDescList) {
		for (ProjectDescriptor projDesc : projDescList)
			if (!projDesc.checkRootPath(rootPath))
				return false;
		
		return true;
	}
}
