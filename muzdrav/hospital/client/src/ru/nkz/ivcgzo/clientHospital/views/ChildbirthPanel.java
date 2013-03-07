package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.net.URL;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.MainFrame;
import ru.nkz.ivcgzo.clientHospital.controllers.ChildbirthController;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.model.IPatientObserver;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.PrdIshodNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.RdDinStruct;
import ru.nkz.ivcgzo.thriftHospital.RdSlStruct;
import ru.nkz.ivcgzo.thriftHospital.TRdIshod;

/**
 * Вкладка делалась не мной - просто изменил для совместимости.
 * Оставленно авторское форматирование.
 * TODO рефакторить!
 * TODO переделать по аналогии с остальными панелями.
 */
public class ChildbirthPanel extends JPanel implements IPatientObserver {
    private static final long serialVersionUID = -2271316126670108144L;
    private static final String TOOLTIP_TEXT =
            "<html><b>Роды</b> - позволяет лечащему врачу сохранять информацию "
            + "о родах</html>";
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/childbirth.png");
    private static final String TITLE = "Роды";

    private ChildbirthController controller;
    private IHospitalModel model;
    private JPanel panel_2;
    private JCheckBox ChBpsi;
    private CustomDateEditor TShvat;
    private CustomDateEditor TVod;
    private JTextField TKash;
    private CustomDateEditor TPoln;
    private CustomDateEditor TNash;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBEff;
    private JTextField TMed;
    private JTextField TPer1;
    private JTextField TPer2;
    private JTextField TPer3;
    private CustomTimeEditor TTSh;
    private CustomTimeEditor TTVo;
    private CustomTimeEditor TTP;
    private CustomTimeEditor TTN;
    private JTextField TPrm1;
    private JTextField TPrm2;
    private JTextField TPrm3;
    private JTextField TGde;
    private JTextField TVes;
    private JTextField Schcc;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPred;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSerd1;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSerd;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBVid;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPoz;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPolpl;
    private JTextField Shdm;
    private JTextField Soj;
    private JTextField SVes;
    private JTextField TRod;
    private JTextField Sdtr;
    private JTextField Stvera;
    private JTextField Sdcr;
    private JTextField Scdiag;
    private JTextField Sdsp;
    private JTextField Scext;
    private CustomDateEditor Tdataosl;
    private CustomDateEditor Tdatam;
    private JTextField Sber;
    private JTextField Srod;
    private JTextField TObol;
    private JTextField TDet;
    private CustomDateEditor TDatarod;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBAkush;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBVrash;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPrinial;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPosled;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBishod;
    private JTextField TVremp;
    private JTextField TVremm;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> CBOsmotr;
    private JTextField SDlina;
    private JTextField Obvit;
    private JTextField TOsob;
    private JTextField SKrov;
    private JTextField SSRok;
    private TRdIshod trdIshod;
    private RdSlStruct rdsl;
    private RdDinStruct rddin;

    public ChildbirthPanel(final ChildbirthController inController,
            final IHospitalModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerPatientObserver((IPatientObserver) this);

        setChildbirthPanel();
        List<IntegerClassifier> doctorsList;
        try {
            doctorsList = ClientHospital.tcl.get_s_vrach(ClientHospital.authInfo.getClpu());

            CBPrinial.setData(doctorsList);
            CBAkush.setData(doctorsList);
            CBVrash.setData(doctorsList);
            CBOsmotr.setData(doctorsList);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
        }
    }

    //TODO привести в порядок - 1500 строк - очень сложно читать
    //TODO названия вида panel_11 - непонятны, привести к человеко-читаемому виду
    //TODO убрать все вызовы сервера в модель
    private void setChildbirthPanel() {
        panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JLabel lblNewLabel_11 = new JLabel("Схватки начались");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_12 = new JLabel("Воды отошли");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_13 = new JLabel("Качество и количество вод");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_14 = new JLabel("Полное открытие");
        lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_15 = new JLabel("Начало потуг");
        lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        ChBpsi = new JCheckBox("Психопрофилактическая подготовка");
        ChBpsi.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_24 = new JLabel("Медикаментозное обезболивание");
        lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_25 = new JLabel("Эффект");
        lblNewLabel_25.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TShvat = new CustomDateEditor();
        TShvat.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
            }
        });
        TShvat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//              if (TPoln.getDate() == null)
//                  TPoln.setDate(TShvat.getText());
//              if (TVod.getDate() == null)
//                  TVod.setDate(TShvat.getText());
//              if (TNash.getDate() == null)
//                  TNash.setDate(TShvat.getText());
//              if (TDatarod.getDate() == null)
//                  TDatarod.setDate(TShvat.getText());
            }
        });
        TShvat.setFont(new Font("Tahoma", Font.BOLD, 12));
        TShvat.setColumns(10);
        
        TVod = new CustomDateEditor();
        TVod.setFont(new Font("Tahoma", Font.BOLD, 12));
        TVod.setColumns(10);
        
        TKash = new JTextField();
        TKash.setFont(new Font("Tahoma", Font.BOLD, 12));
        TKash.setColumns(10);
        
        TPoln = new CustomDateEditor();
        TPoln.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPoln.setColumns(10);
        
        TNash = new CustomDateEditor();
        TNash.setFont(new Font("Tahoma", Font.BOLD, 12));
        TNash.setColumns(10);
        
        CBEff = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db13);
        
        TMed = new JTextField();
        TMed.setFont(new Font("Tahoma", Font.BOLD, 12));
        TMed.setColumns(10);
        
        JLabel lblNewLabel_23 = new JLabel("Продолжительность I период");
        lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPer1 = new JTextField();
        TPer1.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPer1.setColumns(10);
        
        JLabel lblNewLabel_26 = new JLabel("II период");
        lblNewLabel_26.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPer2 = new JTextField();
        TPer2.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPer2.setColumns(10);
        
        JLabel lblNewLabel_27 = new JLabel("III период");
        lblNewLabel_27.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPer3 = new JTextField();
        TPer3.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPer3.setColumns(10);
        
        TTSh = new CustomTimeEditor();
        TTSh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (TPoln.getDate() == null)
                    TPoln.setDate(TShvat.getText());
                if (TVod.getDate() == null)
                    TVod.setDate(TShvat.getText());
                if (TNash.getDate() == null)
                    TNash.setDate(TShvat.getText());
                if (TDatarod.getDate() == null)
                    TDatarod.setDate(TShvat.getText());
            }
        });
        TTSh.setFont(new Font("Tahoma", Font.BOLD, 12));
        TTSh.setColumns(10);
        
        TTVo = new CustomTimeEditor();
        TTVo.setFont(new Font("Tahoma", Font.BOLD, 12));
        TTVo.setColumns(10);
        
        TTP = new CustomTimeEditor();
        TTP.setFont(new Font("Tahoma", Font.BOLD, 12));
        TTP.setColumns(10);
        
        TTN = new CustomTimeEditor();
        TTN.setFont(new Font("Tahoma", Font.BOLD, 12));
        TTN.setColumns(10);
        
        JLabel lblNewLabel_46 = new JLabel("ч.");
        lblNewLabel_46.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPrm1 = new JTextField();
        TPrm1.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPrm1.setColumns(10);
        
        JLabel label = new JLabel("мин.");
        
        JLabel label_1 = new JLabel("ч.");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPrm2 = new JTextField();
        TPrm2.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPrm2.setColumns(10);
        
        JLabel label_2 = new JLabel("мин.");
        
        JLabel label_5 = new JLabel("ч.");
        label_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TPrm3 = new JTextField();
        TPrm3.setFont(new Font("Tahoma", Font.BOLD, 12));
        TPrm3.setColumns(10);
        
        JLabel label_6 = new JLabel("мин.");
        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
            gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addComponent(ChBpsi)
                        .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_panel_2.createSequentialGroup()
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblNewLabel_13)
                                    .addComponent(lblNewLabel_14)
                                    .addComponent(lblNewLabel_15)
                                    .addComponent(lblNewLabel_11)
                                    .addComponent(lblNewLabel_12))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                    .addGroup(gl_panel_2.createSequentialGroup()
                                        .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
                                            .addComponent(TShvat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(TVod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(ComponentPlacement.UNRELATED)
                                        .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
                                            .addComponent(TTVo, 0, 0, Short.MAX_VALUE)
                                            .addComponent(TTSh, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                        .addComponent(TKash, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                        .addGroup(gl_panel_2.createSequentialGroup()
                                            .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(TPoln, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(TNash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(ComponentPlacement.UNRELATED)
                                            .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(TTP, 0, 0, Short.MAX_VALUE)
                                                .addComponent(TTN, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                            .addPreferredGap(ComponentPlacement.RELATED, 2, Short.MAX_VALUE)))))
                            .addGroup(gl_panel_2.createSequentialGroup()
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                    .addComponent(lblNewLabel_24)
                                    .addComponent(lblNewLabel_25)
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblNewLabel_23)
                                        .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                            .addComponent(lblNewLabel_27)
                                            .addComponent(lblNewLabel_26))))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                    .addComponent(TMed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(gl_panel_2.createSequentialGroup()
                                        .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
                                            .addComponent(TPer3, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                            .addComponent(TPer2, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                            .addComponent(TPer1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                            .addGroup(gl_panel_2.createSequentialGroup()
                                                .addComponent(label_5, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
                                                .addGap(4)
                                                .addComponent(TPrm3, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(label_6, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(gl_panel_2.createSequentialGroup()
                                                .addComponent(lblNewLabel_46)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(TPrm1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(label))
                                            .addGroup(gl_panel_2.createSequentialGroup()
                                                .addComponent(label_1, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
                                                .addGap(4)
                                                .addComponent(TPrm2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                                .addGap(10)
                                                .addComponent(label_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(CBEff, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED))))
                    .addGap(31))
        );
        gl_panel_2.setVerticalGroup(
            gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                    .addGap(14)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addComponent(lblNewLabel_11)
                            .addGap(15)
                            .addComponent(lblNewLabel_12))
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(TShvat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(TTSh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(TVod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(TTVo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNewLabel_13)
                        .addComponent(TKash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNewLabel_14)
                        .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                            .addComponent(TPoln, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(TTP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(12)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(TNash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_15)
                        .addComponent(TTN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(ChBpsi)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNewLabel_24)
                        .addComponent(TMed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(12)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNewLabel_25)
                        .addComponent(CBEff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNewLabel_23)
                        .addComponent(TPer1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_46)
                        .addComponent(TPrm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblNewLabel_26)
                            .addComponent(TPer2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGap(3)
                            .addComponent(label_1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                        .addComponent(TPrm2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGap(4)
                            .addComponent(label_2)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGap(3)
                            .addComponent(label_5, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                        .addComponent(TPrm3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGap(4)
                            .addComponent(label_6))
                        .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblNewLabel_27)
                            .addComponent(TPer3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        );
        panel_2.setLayout(gl_panel_2);
        
        JLabel lblNewLabel = new JLabel("Окружность живота");
        lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        
        JLabel lblNewLabel_1 = new JLabel("Высота дна матки");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_2 = new JLabel("Положение плода");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_3 = new JLabel("Позиция");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_4 = new JLabel("Вид");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_5 = new JLabel("Сердцебиение плода");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_6 = new JLabel("Где находился");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_7 = new JLabel("Ударов");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_8 = new JLabel("Предлежание");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_9 = new JLabel("Родовая деятельность");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_10 = new JLabel("Предполагаемый вес");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Soj = new JTextField();
        Soj.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Shdm = new JTextField();
        Shdm.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBPolpl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db1);
        
        CBPolpl.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBPoz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db10);
        CBPoz.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBVid = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db11);
        CBVid.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBSerd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db3);
        CBSerd.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBSerd1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db4);
        CBSerd1.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBPred = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db2);
        CBPred.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Schcc = new JTextField();
        Schcc.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        TVes = new JTextField();
        TVes.setFont(new Font("Tahoma", Font.BOLD, 12));
        TVes.setColumns(10);
        
        TGde = new JTextField();
        TGde.setFont(new Font("Tahoma", Font.BOLD, 12));
        TGde.setColumns(10);
        
        JLabel lblNewLabel_33 = new JLabel("Вес женщины");
        lblNewLabel_33.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        SVes = new JTextField();
        SVes.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        TRod = new JTextField();
        TRod.setFont(new Font("Tahoma", Font.BOLD, 12));
        TRod.setColumns(10);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        
        JLabel lblNewLabel_38 = new JLabel("Таз: D Sp.");
        lblNewLabel_38.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_39 = new JLabel("D Gr.");
        lblNewLabel_39.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_40 = new JLabel("D Tr.");
        lblNewLabel_40.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_41 = new JLabel("C ext.");
        lblNewLabel_41.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_42 = new JLabel("C Diag.");
        lblNewLabel_42.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_43 = new JLabel("T vera");
        lblNewLabel_43.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Sdtr = new JTextField();
        Sdtr.setFont(new Font("Dialog", Font.BOLD, 12));
        
        Stvera = new JTextField();
        Stvera.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Sdcr = new JTextField();
        Sdcr.setFont(new Font("Dialog", Font.BOLD, 12));
        
        Scdiag = new JTextField();
        Scdiag.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Sdsp = new JTextField();
        Sdsp.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        Scext = new JTextField();
        Scext.setFont(new Font("Tahoma", Font.BOLD, 12));
        GroupLayout gl_panel_6 = new GroupLayout(panel_6);
        gl_panel_6.setHorizontalGroup(
            gl_panel_6.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_6.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNewLabel_41)
                        .addComponent(lblNewLabel_38))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(Sdsp)
                        .addComponent(Scext, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNewLabel_42)
                        .addComponent(lblNewLabel_39))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(Scdiag)
                        .addComponent(Sdcr, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                    .addGap(10)
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNewLabel_40)
                        .addComponent(lblNewLabel_43))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(Stvera)
                        .addComponent(Sdtr, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                    .addContainerGap(92, Short.MAX_VALUE))
        );
        gl_panel_6.setVerticalGroup(
            gl_panel_6.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_6.createSequentialGroup()
                    .addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_6.createSequentialGroup()
                            .addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblNewLabel_38)
                                .addComponent(Sdsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblNewLabel_41)
                                .addComponent(Scext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(gl_panel_6.createSequentialGroup()
                            .addComponent(lblNewLabel_39)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblNewLabel_42))
                        .addGroup(gl_panel_6.createSequentialGroup()
                            .addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
                                .addComponent(Sdcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNewLabel_40)
                                .addComponent(Sdtr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
                                .addComponent(Scdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblNewLabel_43)
                                .addComponent(Stvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_6.setLayout(gl_panel_6);
        
        JLabel lblNewLabel_37 = new JLabel("Первое шевеление плода");
        lblNewLabel_37.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Tdataosl = new CustomDateEditor();
        Tdataosl.setFont(new Font("Tahoma", Font.BOLD, 12));
        Tdataosl.setColumns(10);
        
        JLabel lblNewLabel_36 = new JLabel("Дата последних месячных");
        lblNewLabel_36.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Tdatam = new CustomDateEditor();
        Tdatam.setFont(new Font("Tahoma", Font.BOLD, 12));
        Tdatam.setColumns(10);
        
        JLabel lblNewLabel_34 = new JLabel("Которая беременность");
        lblNewLabel_34.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Sber = new JTextField();
        Sber.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel lblNewLabel_35 = new JLabel(" роды");
        lblNewLabel_35.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Srod = new JTextField();
        Srod.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel lblNewLabel_17 = new JLabel("Детское место");
        lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TDet = new JTextField();
        TDet.setFont(new Font("Tahoma", Font.BOLD, 12));
        TDet.setColumns(10);
        
        JLabel lblNewLabel_18 = new JLabel("Оболочки");
        lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TObol = new JTextField();
        TObol.setFont(new Font("Tahoma", Font.BOLD, 12));
        TObol.setColumns(10);
        
        JLabel lblNewLabel_19 = new JLabel("Длина пуповины");
        lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
        
        JLabel lblNewLabel_28 = new JLabel("Принял ребенка");
        lblNewLabel_28.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_30 = new JLabel("Дежурный врач");
        lblNewLabel_30.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_31 = new JLabel("Акушерка");
        lblNewLabel_31.setFont(new Font("Tahoma", Font.PLAIN, 12));
        //StringKlassifier s_vrash
        CBPrinial = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        CBPrinial.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBVrash = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        CBVrash.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBAkush = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        CBAkush.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        TDatarod = new CustomDateEditor();
        TDatarod.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        CBishod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db7);
        CBishod.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel lblNewLabel_32 = new JLabel("Дата родов");
        lblNewLabel_32.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_44 = new JLabel("Исход беременности");
        lblNewLabel_44.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        JLabel lblNewLabel_16 = new JLabel("Послед выделен");
        lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        CBPosled = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db12);
        CBPosled.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel LVrem = new JLabel("Через ");
        LVrem.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TVremp = new JTextField();
        TVremp.setFont(new Font("Tahoma", Font.BOLD, 12));
        TVremp.setColumns(10);
        
        JLabel label_3 = new JLabel("ч.");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TVremm = new JTextField();
        TVremm.setFont(new Font("Tahoma", Font.BOLD, 12));
        TVremm.setColumns(10);
        
        JLabel label_4 = new JLabel("мин.");
        GroupLayout gl_panel_4 = new GroupLayout(panel_4);
        gl_panel_4.setHorizontalGroup(
            gl_panel_4.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_4.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addGap(11)
                            .addComponent(lblNewLabel_16))
                        .addComponent(lblNewLabel_32, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_44)
                        .addComponent(lblNewLabel_31)
                        .addComponent(lblNewLabel_30)
                        .addComponent(lblNewLabel_28))
                    .addGap(14)
                    .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addComponent(CBPrinial, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBVrash, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                        .addComponent(CBAkush, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_4.createSequentialGroup()
                                    .addComponent(CBPosled, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(LVrem))
                                .addComponent(TDatarod, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addComponent(CBishod, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(TVremp, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(label_3)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(TVremm, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_4)))
                    .addContainerGap())
        );
        gl_panel_4.setVerticalGroup(
            gl_panel_4.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_4.createSequentialGroup()
                    .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblNewLabel_16)
                                .addGroup(gl_panel_4.createSequentialGroup()
                                    .addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(CBPosled, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TVremp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(LVrem))
                                        .addGroup(gl_panel_4.createSequentialGroup()
                                            .addComponent(label_3)
                                            .addGap(3)))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(CBPrinial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_28))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(CBVrash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_30))
                                    .addGap(11)
                                    .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(CBAkush, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_31))
                                    .addGap(18)
                                    .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(CBishod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_44))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(TDatarod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_32)))))
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addGap(13)
                            .addComponent(TVremm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addGap(17)
                            .addComponent(label_4)))
                    .addContainerGap(15, Short.MAX_VALUE))
        );
        panel_4.setLayout(gl_panel_4);
        
        JLabel lblNewLabel_29 = new JLabel("Послед осматривал");
        lblNewLabel_29.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        CBOsmotr = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        CBOsmotr.setFont(new Font("Tahoma", Font.BOLD, 12));
        GroupLayout gl_pChildbirth = new GroupLayout(this);
        gl_pChildbirth.setHorizontalGroup(
            gl_pChildbirth.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_pChildbirth.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(gl_pChildbirth.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_pChildbirth.createSequentialGroup()
                            .addComponent(lblNewLabel_29)
                            .addGap(18)
                            .addComponent(CBOsmotr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)
                        .addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(502, Short.MAX_VALUE))
        );
        gl_pChildbirth.setVerticalGroup(
            gl_pChildbirth.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_pChildbirth.createSequentialGroup()
                    .addGap(11)
                    .addGroup(gl_pChildbirth.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_pChildbirth.createSequentialGroup()
                            .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 343, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)))
                    .addGap(8)
                    .addGroup(gl_pChildbirth.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_pChildbirth.createSequentialGroup()
                            .addGap(4)
                            .addComponent(lblNewLabel_29))
                        .addComponent(CBOsmotr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(87, Short.MAX_VALUE))
        );
        
        SDlina = new JTextField();
        SDlina.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel lblNewLabel_20 = new JLabel("Обвитие вокруг");
        lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        Obvit = new JTextField();
        Obvit.setFont(new Font("Tahoma", Font.BOLD, 12));
        Obvit.setColumns(10);
        
        JLabel lblNewLabel_21 = new JLabel("Особенности");
        lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        TOsob = new JTextField();
        TOsob.setFont(new Font("Tahoma", Font.BOLD, 12));
        TOsob.setColumns(10);
        
        JLabel lblNewLabel_22 = new JLabel("Кровопотеря мл.");
        lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        SKrov = new JTextField();
        SKrov.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JLabel lblNewLabel_45 = new JLabel("Срок:");
        lblNewLabel_45.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        SSRok = new JTextField();
        SSRok.setFont(new Font("Tahoma", Font.BOLD, 12));
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBackground(Color.WHITE);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    System.out.println("добавление родов"); 
                    Integer id1 = 0; Integer numr = 0;Integer srok = 40;Integer numdin = 0;
                    Integer oj = 100; Integer hdm = 30; Integer polpl = 1;Integer predpl = 1;
                    Integer chcc = 110; Integer serd = 1; Integer serd1 = 1; double ves = 70.0;double vespl = 3.00;
                //  внести случай и динамику присвоить значения структуре TRdIshod  
//                          patient.getPatientId(), patient.gospitalCod   
//                          Date datarod = Date(System.currentTimeMillis());
        trdIshod.setNpasp(model.getPatient().getPatientId());
        trdIshod.setNgosp(model.getPatient().gospitalCod);
        trdIshod.setId_berem(rdsl.getId_pvizit());
        if (TDatarod.getDate() != null)
        trdIshod.setDaterod(TDatarod.getDate().getTime());
        else 
            trdIshod.setDaterod(System.currentTimeMillis());
        if (TShvat.getDate() != null)
            trdIshod.setShvatd(TShvat.getDate().getTime());
            else 
                trdIshod.setShvatd(System.currentTimeMillis());
        if (TNash.getDate() != null)
            trdIshod.setPotugid(TNash.getDate().getTime());
            else 
                trdIshod.setPotugid(System.currentTimeMillis());
        if (TPoln.getDate() != null)
            trdIshod.setPolnd(TPoln.getDate().getTime());
            else 
                trdIshod.setPolnd(System.currentTimeMillis());
        if (TVod.getDate() != null)
            trdIshod.setVodyd(TVod.getDate().getTime());
            else 
                trdIshod.setVodyd(System.currentTimeMillis());
//          trdIshod.setDeyat(TRod.getText());
//      if (CBEff.getSelectedPcod() != null)
//          trdIshod.setEff(CBEff.getSelectedPcod());
//          else trdIshod.unsetEff();
//          trdIshod.setKashetv(TKash.getText());
//          trdIshod.setKrov((int) SKrov.getModel().getValue());
//          trdIshod.setMesto(TDet.getText());
//          trdIshod.setObezb(TMed.getText());
//          trdIshod.setObol(TObol.getText());
//          trdIshod.setObvit(Obvit.getText());
//          trdIshod.setOsobp(TOsob.getText());
//          trdIshod.setPoln(TPoln.getText());
//      if (CBPosled.getSelectedPcod() != null)
//          trdIshod.setPosled(CBPosled.getSelectedPcod());
//          else trdIshod.unsetPosled();
//      if (CBAkush.getSelectedPcod() != null)
//          trdIshod.setAkush(CBAkush.getSelectedPcod());
//          else trdIshod.unsetAkush();
//      if (CBPrinial.getSelectedPcod() != null)
//          trdIshod.setPrinyl(CBPrinial.getSelectedPcod());
//          else trdIshod.unsetPrinyl();
//      if (CBVrash.getSelectedPcod() != null)
//          trdIshod.setVrash(CBVrash.getSelectedPcod());
//          else trdIshod.unsetVrash();
//      if (CBOsmotr.getSelectedPcod() != null)
//          trdIshod.setOsmposl(CBOsmotr.getSelectedPcod());
//      else trdIshod.unsetOsmposl();
//      trdIshod.setDetmesto(TDet.getText());
        System.out.println("перед добавлением");    
        System.out.println(trdIshod);   
        trdIshod.setId(ClientHospital.tcl.addRdIshod(trdIshod));
                } catch (KmiacServerException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (TException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        btnNewButton.setToolTipText("Добавить");
        btnNewButton.setIcon(new ImageIcon(MainFrame.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));
        
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setBackground(Color.WHITE);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    trdIshod.setNpasp(model.getPatient().getPatientId());
                    if (TDatarod.getDate() != null){
                    trdIshod.setDaterod(TDatarod.getDate().getTime());
                    rdsl.setDatasn(TDatarod.getDate().getTime());}
                    trdIshod.setDeyat(TRod.getText());
                    if (CBEff.getSelectedPcod() != null)
                        trdIshod.setEff(CBEff.getSelectedPcod());
                        else trdIshod.unsetEff();
                    trdIshod.setKashetv(TKash.getText());
                    trdIshod.setKrov(Integer.valueOf(SKrov.getText()));
                    trdIshod.setMesto(TDet.getText());
                    trdIshod.setObezb(TMed.getText());
                    trdIshod.setObol(TObol.getText());
                    trdIshod.setObvit(Obvit.getText());
                    trdIshod.setOsobp(TOsob.getText());
                    if (TPoln.getDate() != null)
                        trdIshod.setPolnd(TPoln.getDate().getTime());
                    trdIshod.setLpupov (Integer.valueOf(SDlina.getText()));
                    if (TShvat.getDate() != null)
                        trdIshod.setShvatd(TShvat.getDate().getTime());
                    if (TVod.getDate() != null)
                        trdIshod.setVodyd(TVod.getDate().getTime());
                    if (TNash.getDate() != null)
                        trdIshod.setPotugid(TNash.getDate().getTime());
                    trdIshod.setShvatt(TTSh.getTime().getTime());
                    trdIshod.setVodyt(TTVo.getTime().getTime());
                    trdIshod.setPolnt(TTP.getTime().getTime());
                    trdIshod.setPotugit(TTN.getTime().getTime());
                    trdIshod.setPsih(ChBpsi.isSelected()); 
        //              trdIshod.setVremp(TVremp.getText());
        //              trdIshod.setPrr1(TPer1.getText());
        //              trdIshod.setPrr2(TPer2.getText());
        //              trdIshod.setPrr3(TPer3.getText());
                    trdIshod.setVremp(Integer.valueOf(TVremp.getText())*60+Integer.valueOf(TVremm.getText()));
                    trdIshod.setPrr1(Integer.valueOf(TPer1.getText())*60+Integer.valueOf(TPrm1.getText()));
                    trdIshod.setPrr2(Integer.valueOf(TPer2.getText())*60+Integer.valueOf(TPrm2.getText()));
                    trdIshod.setPrr3(Integer.valueOf(TPer3.getText())*60+Integer.valueOf(TPrm3.getText()));
                    trdIshod.setVespl(Double.valueOf(TVes.getText()));
                    if (CBPosled.getSelectedPcod() != null)
                        trdIshod.setPosled(CBPosled.getSelectedPcod());
                        else trdIshod.unsetPosled();
                    if (CBAkush.getSelectedPcod() != null)
                        trdIshod.setAkush(CBAkush.getSelectedPcod());
                        else trdIshod.unsetAkush();
                    if (CBPrinial.getSelectedPcod() != null)
                        trdIshod.setPrinyl(CBPrinial.getSelectedPcod());
                        else trdIshod.unsetPrinyl();
                    if (CBVrash.getSelectedPcod() != null)
                        trdIshod.setVrash(CBVrash.getSelectedPcod());
                        else trdIshod.unsetVrash();
                    if (CBOsmotr.getSelectedPcod() != null)
                        trdIshod.setOsmposl(CBOsmotr.getSelectedPcod());
                    else trdIshod.unsetOsmposl();
                    trdIshod.setDetmesto(TDet.getText());
                    System.out.println(trdIshod);   
                    rddin.setChcc(Integer.valueOf(Schcc.getText()));
                    rddin.setSrok(Integer.valueOf(SSRok.getText()));
                    rddin.setVes(Double.valueOf(SVes.getText()));
                    rddin.setOj(Integer.valueOf(Soj.getText()));
                    rddin.setHdm(Integer.valueOf(Shdm.getText()));
                    if (CBVid.getSelectedPcod() != null)
                        rddin.setVidpl(CBVid.getSelectedPcod());
                        else rddin.unsetVidpl();
                    if (CBSerd.getSelectedPcod() != null)
                        rddin.setSerd(CBSerd.getSelectedPcod());
                        else rddin.unsetSerd();
                    if (CBSerd1.getSelectedPcod() != null)
                        rddin.setSerd1(CBSerd1.getSelectedPcod());
                        else rddin.unsetSerd1();
                    if (CBPred.getSelectedPcod() != null)
                        rddin.setPredpl(CBPred.getSelectedPcod());
                        else rddin.unsetPredpl();
                    if (CBPolpl.getSelectedPcod() != null)
                        rddin.setPolpl(CBPolpl.getSelectedPcod());
                        else rddin.unsetPolpl();
                    if (CBPoz.getSelectedPcod() != null)
                        rddin.setPozpl(CBPoz.getSelectedPcod());
                        else rddin.unsetPozpl();
                    System.out.println(rddin);  
                    rdsl.setShet(Integer.valueOf(Sber.getText()));
                    rdsl.setKolrod(Integer.valueOf(Srod.getText()));
                    rdsl.setDTroch(Integer.valueOf(Sdtr.getText()));
                    rdsl.setCvera(Integer.valueOf(Stvera.getText()));
                    rdsl.setDsr(Integer.valueOf(Sdcr.getText()));
                    rdsl.setCdiagt(Integer.valueOf(Scdiag.getText()));
                    rdsl.setDsp(Integer.valueOf(Sdsp.getText()));
                    rdsl.setCext(Integer.valueOf(Scext.getText()));
                    rddin.setNpasp(model.getPatient().getPatientId());
                    if (rdsl.getYavka1() == 0)
                    rdsl.setYavka1(Integer.valueOf(SSRok.getText()));
                    rddin.setNgosp(model.getPatient().gospitalCod);
                    if (CBishod.getSelectedPcod() != null)
                        rdsl.setIshod(CBishod.getSelectedPcod());
                        else rdsl.unsetIshod();
                        if (Tdatam.getDate() != null)
                            rdsl.setDataM(Tdatam.getDate().getTime());
                        if (Tdataosl.getDate() != null)
                            rdsl.setDataosl(Tdataosl.getDate().getTime());
                    //внести роды в исход беременности
                    System.out.println(rdsl);   
                        ClientHospital.tcl.updateRdIshod(trdIshod);
                        ClientHospital.tcl.UpdateRdSl(rdsl);
                        ClientHospital.tcl.UpdateRdDin(rddin);
                    } catch (KmiacServerException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (TException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
            }
        });
        btnNewButton_1.setToolTipText("Сохранить");
        btnNewButton_1.setIcon(new ImageIcon(MainFrame.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
        
        JButton btnNewButton_2 = new JButton("");
        btnNewButton_2.setBackground(Color.WHITE);
        btnNewButton_2.setIcon(new ImageIcon(MainFrame.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
        btnNewButton_2.setToolTipText("Удалить");
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
            gl_panel_3.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_3.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_2)
                                    .addGap(25)
                                    .addComponent(CBPolpl, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_3)
                                    .addGap(82)
                                    .addComponent(CBPoz, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_4)
                                    .addGap(109)
                                    .addComponent(CBVid, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_1)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(Shdm, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(lblNewLabel_10)))
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(TVes, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                            .addGap(182))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel_5)
                                        .addComponent(lblNewLabel_8))
                                    .addGap(10)
                                    .addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(CBPred, GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                        .addComponent(CBSerd, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, 122, Short.MAX_VALUE)
                                    .addGap(18)
                                    .addComponent(lblNewLabel_7)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(Schcc, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblNewLabel_6))
                            .addContainerGap())))
                .addGroup(gl_panel_3.createSequentialGroup()
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(10)
                            .addComponent(lblNewLabel_34)
                            .addGap(18)
                            .addComponent(Sber, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                            .addGap(4)
                            .addComponent(lblNewLabel_35)
                            .addGap(10)
                            .addComponent(Srod, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(lblNewLabel_45)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(SSRok, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(10)
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_33)
                                    .addGap(24)
                                    .addComponent(SVes, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(lblNewLabel)
                                    .addGap(18)
                                    .addComponent(Soj, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED))
                                .addComponent(panel_6, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(10)
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_36)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(Tdatam, 0, 0, Short.MAX_VALUE))
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(lblNewLabel_37)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(Tdataosl, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblNewLabel_19))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblNewLabel_22))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblNewLabel_21)))
                    .addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
                        .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
                    .addGap(26))
                .addGroup(gl_panel_3.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNewLabel_18)
                        .addComponent(lblNewLabel_9)
                        .addComponent(lblNewLabel_17)
                        .addComponent(lblNewLabel_20))
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(8)
                            .addComponent(SKrov, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                            .addGap(148))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(4)
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                                .addComponent(TDet, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                .addComponent(TRod)
                                .addComponent(TObol)
                                .addComponent(SDlina, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                                .addComponent(Obvit, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
                                .addComponent(TGde, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                .addComponent(TOsob, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))))
                    .addContainerGap(191, Short.MAX_VALUE))
        );
        gl_panel_3.setVerticalGroup(
            gl_panel_3.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel_3.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(2)
                            .addComponent(lblNewLabel_34))
                        .addComponent(Sber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(2)
                            .addComponent(lblNewLabel_35))
                        .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                            .addComponent(Srod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNewLabel_45)
                            .addComponent(SSRok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(14)
                            .addComponent(lblNewLabel_36))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(11)
                            .addComponent(Tdatam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(15)
                            .addComponent(lblNewLabel_37))
                        .addComponent(Tdataosl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(11)
                    .addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                    .addGap(6)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(2)
                            .addComponent(lblNewLabel_33))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(1)
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblNewLabel)
                                .addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(Soj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(2)
                            .addComponent(lblNewLabel_1))
                        .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                            .addComponent(Shdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNewLabel_10)
                            .addComponent(TVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(14)
                            .addComponent(lblNewLabel_2))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(6)
                            .addComponent(CBPolpl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(14)
                            .addComponent(lblNewLabel_3))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(6)
                            .addComponent(CBPoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(14)
                            .addComponent(lblNewLabel_4))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(CBVid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addComponent(CBSerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(4)
                            .addComponent(lblNewLabel_5))
                        .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblNewLabel_7)
                            .addComponent(Schcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNewLabel_8)
                        .addComponent(CBPred, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNewLabel_6)
                        .addComponent(TGde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                        .addComponent(TRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_9))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
                        .addComponent(TDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNewLabel_17))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblNewLabel_18)
                        .addComponent(TObol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addGap(13)
                            .addComponent(lblNewLabel_19)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblNewLabel_20)
                            .addGap(18)
                            .addComponent(lblNewLabel_21))
                        .addGroup(gl_panel_3.createSequentialGroup()
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(btnNewButton_1)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(btnNewButton_2))
                                .addGroup(gl_panel_3.createSequentialGroup()
                                    .addComponent(SDlina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(Obvit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(TOsob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
                    .addGap(149)
                    .addComponent(lblNewLabel_22)
                    .addGap(78)
                    .addComponent(SKrov, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        panel_3.setLayout(gl_panel_3);
        setLayout(gl_pChildbirth);
    }

    public final Component getComponent() {
        return this;
    }

    public final String getPanelToolTipText() {
        return TOOLTIP_TEXT;
    }

    public final String getTitle() {
        return TITLE;
    }

    public final URL getIconURL() {
        return ICON;
    }

    @Override
    public void patientChanged() {
        try {
            trdIshod = ClientHospital.tcl.getRdIshodInfo(
                    model.getPatient().getPatientId(),
                    model.getPatient().gospitalCod);
            System.out.println("начальные значения");       
            System.out.println(trdIshod);       
            setDefaultValues();
        } catch(PrdIshodNotFoundException e) {
//          e.printStackTrace();
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientHospital.conMan.reconnect(e);
        }
//      try {
//          trdIshod = ClientHospital.tcl.getRdIshodInfo(
//              patient.getPatientId(), patient.gospitalCod);
//          setDefaultValues();
//      } catch (KmiacServerException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      } catch (TException e) {
//          e.printStackTrace();
//          ClientHospital.conMan.reconnect(e);
//      }
//      private void setChildbirthPanel() {
        rddin = new RdDinStruct();
        try {
            rddin = ClientHospital.tcl.getRdDinInfo(model.getPatient().getPatientId(),
                    model.getPatient().gospitalCod);

            System.out.println(rddin);      
        SVes.setText(String.valueOf(rddin.getVes()));
            Soj.setText(String.valueOf(rddin.getOj()));
            Shdm.setText(String.valueOf(rddin.getHdm()));
            Schcc.setText(String.valueOf(rddin.getChcc()));
            SSRok.setText(String.valueOf(rddin.getSrok()));
//                     if ((rddin.isSetPozpl())||(rddin.getPozpl() != 0))
            if (rddin.getPozpl() != 0)
            CBPoz.setSelectedPcod(rddin.getPozpl());
            else CBPoz.setSelectedItem(null);
            if (rddin.getPolpl() != 0)
            CBPolpl.setSelectedPcod(rddin.getPolpl());
            else CBPolpl.setSelectedItem(null);
            if (rddin.getVidpl() != 0)
            CBVid.setSelectedPcod(rddin.getVidpl());
            else CBVid.setSelectedItem(null);
            if (rddin.getSerd() != 0)
            CBSerd.setSelectedPcod(rddin.getSerd());
            else CBSerd.setSelectedItem(null);
            if (rddin.getSerd1() != 0)
            CBSerd1.setSelectedPcod(rddin.getSerd1());
            else CBSerd1.setSelectedItem(null);
            if (rddin.getPredpl() != 0)
            CBPred.setSelectedPcod(rddin.getPredpl());
            else CBPred.setSelectedItem(null);
            TVes.setText(String.valueOf(rddin.getOj()*rddin.getHdm()));

        } catch (KmiacServerException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (TException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        trdIshod = new TRdIshod();
        try {
            trdIshod = ClientHospital.tcl.getRdIshodInfo(model.getPatient().getPatientId(),
                    model.getPatient().gospitalCod);
            System.out.println("начальные значения 11");
            setDefaultValues();
        } catch (KmiacServerException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (TException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        rdsl = new RdSlStruct();
        try {
            rdsl = ClientHospital.tcl.getRdSlInfo(model.getPatient().getPatientId());
            Tdataosl.setDate(rdsl.getDataosl());
            if (rdsl.getDataosl() == 0)
            Tdataosl.setText(null);
            Tdatam.setDate(rdsl.getDataM());
            if (rdsl.getDataM() == 0)
            Tdatam.setText(null);
            Srod.setText(String.valueOf(rdsl.getKolrod()));
            Sber.setText(String.valueOf(rdsl.getShet()));
            Sdtr.setText(String.valueOf(rdsl.getDTroch()));
            Stvera.setText(String.valueOf(rdsl.getCvera()));
            Sdcr.setText(String.valueOf(rdsl.getDsp()));
            Scdiag.setText(String.valueOf(rdsl.getCdiagt()));
            Sdsp.setText(String.valueOf(rdsl.getDsr()));
            Scext.setText(String.valueOf(rdsl.getCext()));
        } catch (KmiacServerException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        } catch (TException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        };
    }

    private void setDefaultValues() {
        try {
            System.out.println("начальные значения");       
            SDlina.setText(String.valueOf(trdIshod.getLpupov()));
            SKrov.setText(String.valueOf(trdIshod.getKrov()));
            if (trdIshod.getObvit() == null)
            Obvit.setText(""); else
            Obvit.setText(trdIshod.getObvit());
            TDatarod.setDate(trdIshod.getDaterod());
            if (trdIshod.getDaterod() == 0)
            TDatarod.setText(null);
            TPoln.setDate(trdIshod.getPolnd());
            TTP.setTime(trdIshod.getPolnt());
            if (trdIshod.getDetmesto() == null)
            TDet.setText(""); else  
            TDet.setText(trdIshod.getDetmesto());
            TNash.setDate(trdIshod.getPotugid());
            TTN.setTime(trdIshod.getPotugit());
            if (trdIshod.getObol() == null)
            TObol.setText(""); else 
            TObol.setText(trdIshod.getObol()); 
            if (trdIshod.getOsobp() == null)
            TOsob.setText("");  else
            TOsob.setText(trdIshod.getOsobp());
            Integer ch = 0;Integer ch1 = 0;Integer ch2 = 0;Integer ch3 = 0;
            Integer v = trdIshod.getVremp();
            ch = v/60; v=v-ch*60;
            TVremp.setText(String.valueOf(ch));
            TVremm.setText(String.valueOf(v));
            Integer v1 = trdIshod.getPrr1();
            ch1 = v1/60; v1=v1-ch1*60;
            TPer1.setText(String.valueOf(ch1));
            TPrm1.setText(String.valueOf(v1));
            Integer v2 = trdIshod.getPrr2();
            ch2 = v2/60; v2=v2-ch2*60;
            TPer2.setText(String.valueOf(ch2));
            TPrm2.setText(String.valueOf(v2));
            Integer v3 = trdIshod.getPrr3();
            ch3 = v3/60; v3=v3-ch3*60;
            TPer3.setText(String.valueOf(ch3));
            TPrm3.setText(String.valueOf(v3));
            if (trdIshod.getMesto() == null)
            TGde.setText(""); else
            TGde.setText(trdIshod.getMesto());
            TShvat.setDate(trdIshod.getShvatd());
            TTSh.setTime(trdIshod.getShvatt());
            TVod.setDate(trdIshod.getVodyd());
            TTVo.setTime(trdIshod.getVodyt());
            if (trdIshod.getKashetv() == null)
            TKash.setText(""); else
            TKash.setText(trdIshod.getKashetv());
            if (trdIshod.getDeyat() == null)
            TRod.setText(""); else
            TRod.setText(trdIshod.getDeyat());
            if (trdIshod.getObezb() == null)
            TMed.setText(""); else
            TMed.setText(trdIshod.getObezb());
            
            ChBpsi.setSelected(trdIshod.isPsih());
            if (trdIshod.isSetEff())
                CBEff.setSelectedPcod(trdIshod.getEff());
            else
                CBEff.setSelectedItem(null);
            if (trdIshod.isSetPosled())
                CBPosled.setSelectedPcod(trdIshod.getPosled());
            else
                CBPosled.setSelectedItem(null);
            if (trdIshod.isSetAkush())
                CBAkush.setSelectedPcod(trdIshod.getAkush());
            else
                CBAkush.setSelectedItem(null);
            if (trdIshod.isSetVrash())
                CBVrash.setSelectedPcod(trdIshod.getVrash());
            else
                CBVrash.setSelectedItem(null);
            if (trdIshod.isSetPrinyl())
                CBPrinial.setSelectedPcod(trdIshod.getPrinyl());
            else
                CBPrinial.setSelectedItem(null);
            if (trdIshod.isSetOsmposl())
                CBOsmotr.setSelectedPcod(trdIshod.getOsmposl());
            else
                CBOsmotr.setSelectedItem(null);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Ошибка создания записи", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
