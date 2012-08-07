package ru.ivcgzo.nkz.clientReception;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class TalonSelectFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tbTalonSelect;
    private JTable tbTalonDelete;

    public TalonSelectFrame() {

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.3);
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING).addComponent(
                    splitPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );

        JPanel pnInformation = new JPanel();
        splitPane.setLeftComponent(pnInformation);
        pnInformation.setLayout(new BoxLayout(pnInformation, BoxLayout.Y_AXIS));
        
        JPanel pnPatientInfo = new JPanel();
        pnInformation.add(pnPatientInfo);
        GroupLayout gl_pnPatientInfo = new GroupLayout(pnPatientInfo);
        gl_pnPatientInfo.setHorizontalGroup(
            gl_pnPatientInfo.createParallelGroup(Alignment.LEADING)
                .addGap(0, 295, Short.MAX_VALUE)
        );
        gl_pnPatientInfo.setVerticalGroup(
            gl_pnPatientInfo.createParallelGroup(Alignment.LEADING)
                .addGap(0, 231, Short.MAX_VALUE)
        );
        pnPatientInfo.setLayout(gl_pnPatientInfo);

        JPanel plTalonType = new JPanel();
        plTalonType.setBorder(new TitledBorder(null, "\u0412\u044B\u0431\u043E\u0440 \u0441\u043F\u0435\u0446\u0438\u0430\u043B\u0438\u0441\u0442\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        plTalonType.setBackground(new Color(153, 153, 255));
        pnInformation.add(plTalonType);
        
        JComboBox cbxPoliclinic = new JComboBox();
        
        JComboBox cbxSpeciality = new JComboBox();
        
        JComboBox cbxDoctor = new JComboBox();
        
        JComboBox comboBox_3 = new JComboBox();
        
        JButton btnUpdate = new JButton("Обновить");
        GroupLayout gl_panel_2 = new GroupLayout(plTalonType);
        gl_panel_2.setHorizontalGroup(
            gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(btnUpdate, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboBox_3, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxDoctor, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxSpeciality, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxPoliclinic, Alignment.LEADING, 0, 175, Short.MAX_VALUE))
                    .addContainerGap(101, Short.MAX_VALUE))
        );
        gl_panel_2.setVerticalGroup(
            gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                    .addGap(20)
                    .addComponent(cbxPoliclinic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(cbxSpeciality, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(cbxDoctor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnUpdate)
                    .addContainerGap(83, Short.MAX_VALUE))
        );
        plTalonType.setLayout(gl_panel_2);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        splitPane.setRightComponent(tabbedPane);

        JScrollPane pnTalonSelect = new JScrollPane();
        tabbedPane.addTab("Свободные талоны", null, pnTalonSelect, null);

        tbTalonSelect = new JTable();
        pnTalonSelect.setViewportView(tbTalonSelect);
        
        JScrollPane pnTalonDelete = new JScrollPane();
        tabbedPane.addTab("Занятые талоны", null, pnTalonDelete, null);
        
        tbTalonDelete = new JTable();
        pnTalonDelete.setViewportView(tbTalonDelete);
        getContentPane().setLayout(groupLayout);

    }
}
