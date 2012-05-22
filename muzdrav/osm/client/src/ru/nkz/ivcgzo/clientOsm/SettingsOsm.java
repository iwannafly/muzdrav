package ru.nkz.ivcgzo.clientOsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;

public class SettingsOsm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JCheckBox jal_d;
	private JCheckBox jal_kr;
	private JCheckBox jal_p;
	private JCheckBox jal_m;
	private JCheckBox jal_en;
	private JCheckBox jal_ner;
	private JCheckBox jal_op;
	private JCheckBox jal_l;
	private JCheckBox jal_ob;
	private JCheckBox jal_pr;
	private JCheckBox istz;
	private JCheckBox nach;
	private JCheckBox symp;
	private JCheckBox otn;
	private JCheckBox ps;
	private JCheckBox allerg;
	private JCheckBox ist;
	private JCheckBox razv;
	private JCheckBox usl;
	private JCheckBox perz;
	private JCheckBox per_op;
	private JCheckBox gem;
	private JCheckBox nasl;
	private JCheckBox gyn;
	private JCheckBox farm;
	private JCheckBox lek;
	private JCheckBox gorm;
	private JCheckBox st;
	private JCheckBox ob;
	private JCheckBox koj;
	private JCheckBox sl;
	private JCheckBox kl;
	private JCheckBox lim;
	private JCheckBox kost;
	private JCheckBox nerv;
	private JCheckBox chss;
	private JCheckBox temp;
	private JCheckBox art;
	private JCheckBox rost;
	private JCheckBox ves;
	private JCheckBox telo;
	private JCheckBox fiz;
	private JCheckBox sust;
	private JCheckBox dyh;
	private JCheckBox gr;
	private JCheckBox perl;
	private JCheckBox ausl;
	private JCheckBox bronh;
	private JCheckBox arter;
	private JCheckBox obls;
	private JCheckBox pers;
	private JCheckBox auss;
	private JCheckBox polr;
	private JCheckBox jiv;
	private JCheckBox palj;
	private JCheckBox jkt;
	private JCheckBox palpj;
	private JCheckBox palpod;
	private JCheckBox pech;
	private JCheckBox jelch;
	private JCheckBox selez;
	private JCheckBox oblz;
	private JCheckBox oblp;
	private JCheckBox pochk;
	private JCheckBox moch;
	private JCheckBox jelm;
	private JCheckBox jelgr;
	private JCheckBox matka;
	private JCheckBox polorg;
	private JCheckBox chit;
	private JCheckBox stloc;
	private JCheckBox ocen; 
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public SettingsOsm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			loadConfig();}
		});
		setBounds(100, 100, 962, 629);
		
		JPanel osn = new JPanel();
		JPanel pjalob = new JPanel();
		pjalob.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel pmorbi = new JPanel();
		pmorbi.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		istz = new JCheckBox("История заболевания");
		
		nach = new JCheckBox("Начало заболевания");
		
		symp = new JCheckBox("Симптомы");
		
		otn = new JCheckBox("Отношение больного к болезни");
		
		ps = new JCheckBox("Психологическая ситуация в связи с болезнью");
		GroupLayout gl_pmorbi = new GroupLayout(pmorbi);
		gl_pmorbi.setHorizontalGroup(
			gl_pmorbi.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pmorbi.createSequentialGroup()
					.addGroup(gl_pmorbi.createParallelGroup(Alignment.LEADING)
						.addComponent(symp)
						.addComponent(nach)
						.addComponent(istz))
					.addContainerGap(178, Short.MAX_VALUE))
				.addGroup(gl_pmorbi.createSequentialGroup()
					.addComponent(otn)
					.addGap(127))
				.addGroup(gl_pmorbi.createSequentialGroup()
					.addComponent(ps)
					.addGap(167))
		);
		gl_pmorbi.setVerticalGroup(
			gl_pmorbi.createParallelGroup(Alignment.LEADING)
				.addGap(0, 169, Short.MAX_VALUE)
				.addGroup(gl_pmorbi.createSequentialGroup()
					.addContainerGap()
					.addComponent(istz)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(nach)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(symp)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(otn)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ps)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		pmorbi.setLayout(gl_pmorbi);
		
		JPanel pistz = new JPanel();
		pistz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		razv = new JCheckBox("Развитие");
		
		ist = new JCheckBox("История жизни");
		
		allerg = new JCheckBox("Аллергоанамнез");
		
		nasl = new JCheckBox("Наследственность");
		
		gyn = new JCheckBox("Гинекологический анамнез");
		
		farm = new JCheckBox("Фармакологический анамнез");
		
		usl = new JCheckBox("Условия проживания");
		
		lek = new JCheckBox("Прием лекарственных средств");
		
		perz = new JCheckBox("Перенесенные заболевания");
		
		gorm = new JCheckBox("Применение гормональных препаратов");
		
		per_op = new JCheckBox("Перенесенные операции");
		
		gem = new JCheckBox("Гемотрансфузия");
		GroupLayout gl_pistz = new GroupLayout(pistz);
		gl_pistz.setHorizontalGroup(
			gl_pistz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pistz.createSequentialGroup()
					.addGroup(gl_pistz.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pistz.createSequentialGroup()
							.addGroup(gl_pistz.createParallelGroup(Alignment.LEADING)
								.addComponent(razv)
								.addComponent(ist)
								.addComponent(allerg))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pistz.createParallelGroup(Alignment.LEADING)
								.addComponent(farm)
								.addComponent(gyn)
								.addComponent(gorm)))
						.addGroup(gl_pistz.createSequentialGroup()
							.addComponent(usl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lek))
						.addGroup(gl_pistz.createSequentialGroup()
							.addComponent(perz)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(nasl))
						.addGroup(gl_pistz.createSequentialGroup()
							.addComponent(per_op)
							.addGap(18)
							.addComponent(gem)))
					.addGap(118))
		);
		gl_pistz.setVerticalGroup(
			gl_pistz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pistz.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pistz.createParallelGroup(Alignment.BASELINE)
						.addComponent(allerg)
						.addComponent(gorm))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pistz.createParallelGroup(Alignment.BASELINE)
						.addComponent(ist)
						.addComponent(gyn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pistz.createParallelGroup(Alignment.BASELINE)
						.addComponent(razv)
						.addComponent(farm))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pistz.createParallelGroup(Alignment.BASELINE)
						.addComponent(usl)
						.addComponent(lek))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pistz.createParallelGroup(Alignment.BASELINE)
						.addComponent(perz)
						.addComponent(nasl))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pistz.createParallelGroup(Alignment.BASELINE)
						.addComponent(per_op)
						.addComponent(gem))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pistz.setLayout(gl_pistz);
		
		JPanel pstpr = new JPanel();
		pstpr.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		koj = new JCheckBox("Кожные покровы");
		
		ob = new JCheckBox("Общее состояние");
		
		st = new JCheckBox("Status praesense");
		
		art = new JCheckBox("Артериальное давление");
		
		nerv = new JCheckBox("Нервно-психический статус");
		
		chss = new JCheckBox("ЧСС");
		
		sl = new JCheckBox("Видимые слизистые");
		
		temp = new JCheckBox("Температура");
		
		kl = new JCheckBox("Подкожная клетчатка");
		
		kost = new JCheckBox("Костно-мышечная система");
		
		lim = new JCheckBox("Лимфатические узлы");
		
		ves = new JCheckBox("Вес");
		
		rost = new JCheckBox("Рост");
		
		telo = new JCheckBox("Телосложение");
		GroupLayout gl_pstpr = new GroupLayout(pstpr);
		gl_pstpr.setHorizontalGroup(
			gl_pstpr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pstpr.createSequentialGroup()
					.addGroup(gl_pstpr.createParallelGroup(Alignment.LEADING)
						.addComponent(koj)
						.addComponent(ob)
						.addComponent(st))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pstpr.createParallelGroup(Alignment.LEADING)
						.addComponent(chss)
						.addComponent(nerv)
						.addComponent(kost)))
				.addGroup(gl_pstpr.createSequentialGroup()
					.addComponent(sl)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(temp))
				.addGroup(gl_pstpr.createSequentialGroup()
					.addComponent(kl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(art))
				.addComponent(lim)
				.addGroup(gl_pstpr.createSequentialGroup()
					.addComponent(rost, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addComponent(telo, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
				.addComponent(ves, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
		);
		gl_pstpr.setVerticalGroup(
			gl_pstpr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pstpr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pstpr.createParallelGroup(Alignment.BASELINE)
						.addComponent(st)
						.addComponent(kost))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pstpr.createParallelGroup(Alignment.BASELINE)
						.addComponent(ob)
						.addComponent(nerv))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pstpr.createParallelGroup(Alignment.BASELINE)
						.addComponent(koj)
						.addComponent(chss))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pstpr.createParallelGroup(Alignment.BASELINE)
						.addComponent(sl)
						.addComponent(temp))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pstpr.createParallelGroup(Alignment.BASELINE)
						.addComponent(kl)
						.addComponent(art))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lim)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pstpr.createParallelGroup(Alignment.LEADING)
						.addComponent(rost)
						.addComponent(telo))
					.addGap(3)
					.addComponent(ves)
					.addContainerGap(160, Short.MAX_VALUE))
		);
		pstpr.setLayout(gl_pstpr);
		
		JPanel pfiz = new JPanel();
		pfiz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		dyh = new JCheckBox("Дыхание");
		
		sust = new JCheckBox("Суставы");
		
		fiz = new JCheckBox("Физикальное обследование");
		
		auss = new JCheckBox("Аускультация сердца");
		
		pers = new JCheckBox("Перкуссия сердца");
		
		obls = new JCheckBox("Область сердца");
		
		gr = new JCheckBox("Грудная клетка");
		
		polr = new JCheckBox("Полость рта");
		
		perl = new JCheckBox("Перкуссия легких");
		
		jiv = new JCheckBox("Живот");
		
		ausl = new JCheckBox("Аускультация легких");
		
		bronh = new JCheckBox("Бронхофония");
		
		palj = new JCheckBox("Пальпация живота");
		
		arter = new JCheckBox("Артерии и шейные вены");
		
		jkt = new JCheckBox("Пальпация, перкуссия и аускультация ЖКТ");
		
		pochk = new JCheckBox("Почки");
		
		moch = new JCheckBox("Мочевой пузырь");
		
		jelm = new JCheckBox("Молочные железы");
		
		jelgr = new JCheckBox("Грудные железы мужчин");
		
		pech = new JCheckBox("Печень");
		
		palpod = new JCheckBox("Пальпация поджелудочной железы");
		
		palpj = new JCheckBox("Пальпация желудка");
		
		jelch = new JCheckBox("Желчный пузырь");
		
		matka = new JCheckBox("Матка и ее придатки");
		
		selez = new JCheckBox("Селезенка");
		
		polorg = new JCheckBox("Наружные половые органы у мужчин");
		
		oblp = new JCheckBox("Поясничная область");
		
		oblz = new JCheckBox("Область заднего прохода");
		
		chit = new JCheckBox("Щитовидная железа");
		GroupLayout gl_pfiz = new GroupLayout(pfiz);
		gl_pfiz.setHorizontalGroup(
			gl_pfiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfiz.createSequentialGroup()
					.addGroup(gl_pfiz.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pfiz.createSequentialGroup()
							.addGroup(gl_pfiz.createParallelGroup(Alignment.LEADING)
								.addComponent(dyh)
								.addComponent(sust)
								.addComponent(fiz))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pfiz.createParallelGroup(Alignment.LEADING)
								.addComponent(auss)
								.addComponent(pers)
								.addComponent(obls)))
						.addGroup(gl_pfiz.createSequentialGroup()
							.addComponent(gr)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(polr))
						.addGroup(gl_pfiz.createSequentialGroup()
							.addComponent(perl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jiv))
						.addGroup(gl_pfiz.createSequentialGroup()
							.addComponent(arter, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(moch, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pfiz.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(jkt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, gl_pfiz.createSequentialGroup()
								.addGroup(gl_pfiz.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pfiz.createSequentialGroup()
										.addComponent(ausl)
										.addGap(10))
									.addGroup(Alignment.TRAILING, gl_pfiz.createSequentialGroup()
										.addComponent(bronh, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_pfiz.createParallelGroup(Alignment.LEADING)
									.addComponent(pochk, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
									.addComponent(palj, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))))
						.addComponent(palpod)
						.addGroup(gl_pfiz.createSequentialGroup()
							.addComponent(palpj, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jelm, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
						.addComponent(oblp, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pfiz.createSequentialGroup()
							.addGroup(gl_pfiz.createParallelGroup(Alignment.LEADING)
								.addComponent(selez, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addComponent(oblz, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chit))
						.addComponent(jelch, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(pech, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pfiz.createSequentialGroup()
							.addComponent(jelgr)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(matka, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
						.addComponent(polorg, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_pfiz.setVerticalGroup(
			gl_pfiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfiz.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(fiz)
						.addComponent(obls))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(sust)
						.addComponent(pers))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(dyh)
						.addComponent(auss))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(gr)
						.addComponent(polr))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(perl)
						.addComponent(jiv))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(ausl)
						.addComponent(palj))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(bronh)
						.addComponent(pochk))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(arter)
						.addComponent(moch))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jkt)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(palpj)
						.addComponent(jelm))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(palpod)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(jelgr)
						.addComponent(matka))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pech)
					.addGap(3)
					.addComponent(jelch)
					.addGap(3)
					.addComponent(selez)
					.addGap(3)
					.addGroup(gl_pfiz.createParallelGroup(Alignment.BASELINE)
						.addComponent(oblz)
						.addComponent(chit))
					.addGap(3)
					.addComponent(oblp)
					.addGap(2)
					.addComponent(polorg)
					.addContainerGap())
		);
		pfiz.setLayout(gl_pfiz);
		
		JPanel pstloc = new JPanel();
		pstloc.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		stloc = new JCheckBox("Status localis");
		GroupLayout gl_pstloc = new GroupLayout(pstloc);
		gl_pstloc.setHorizontalGroup(
			gl_pstloc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pstloc.createSequentialGroup()
					.addComponent(stloc)
					.addContainerGap(297, Short.MAX_VALUE))
		);
		gl_pstloc.setVerticalGroup(
			gl_pstloc.createParallelGroup(Alignment.LEADING)
				.addGap(0, 140, Short.MAX_VALUE)
				.addGroup(gl_pstloc.createSequentialGroup()
					.addContainerGap()
					.addComponent(stloc)
					.addContainerGap(106, Short.MAX_VALUE))
		);
		pstloc.setLayout(gl_pstloc);
		
		JPanel pocen = new JPanel();
		pocen.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		 ocen = new JCheckBox("Оценка данных анамнеза и объективного исследования");
		GroupLayout gl_pocen = new GroupLayout(pocen);
		gl_pocen.setHorizontalGroup(
			gl_pocen.createParallelGroup(Alignment.LEADING)
				.addGap(0, 268, Short.MAX_VALUE)
				.addGroup(gl_pocen.createSequentialGroup()
					.addComponent(ocen)
					.addContainerGap(297, Short.MAX_VALUE))
		);
		gl_pocen.setVerticalGroup(
			gl_pocen.createParallelGroup(Alignment.LEADING)
				.addGap(0, 37, Short.MAX_VALUE)
				.addGap(0, 140, Short.MAX_VALUE)
				.addGroup(gl_pocen.createSequentialGroup()
					.addContainerGap()
					.addComponent(ocen)
					.addContainerGap(106, Short.MAX_VALUE))
		);
		pocen.setLayout(gl_pocen);
		
		JButton button = new JButton("Сохранить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DocumentBuilder builder;
				try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				} catch (ParserConfigurationException e) {
				e.printStackTrace();
				return;
				}
				Document doc = builder.newDocument();

				Element Mainelem = doc.createElement("main");
				Mainelem.setAttribute("name", "Врач амбулаторного приема");
				Mainelem.appendChild(doc.createTextNode("osm"));
				doc.appendChild(Mainelem);
				if (jal_d.isSelected()){
					Element JalDEl = doc.createElement("jalob_dyh");
					JalDEl.setAttributeNode(doc.createAttribute("name"));
					JalDEl.setAttribute("name", "jal_d");
					JalDEl.setAttributeNode(doc.createAttribute("id"));
					JalDEl.setAttribute("id", "2");
					JalDEl.appendChild(doc.createTextNode("Дыхательная система"));
					Mainelem.appendChild(JalDEl);}
				if (jal_kr.isSelected()){
					Element JalKrEl = doc.createElement("jalob_kr");
					JalKrEl.setAttributeNode(doc.createAttribute("name"));
					JalKrEl.setAttribute("name", "jal_kr");
					JalKrEl.setAttributeNode(doc.createAttribute("id"));
					JalKrEl.setAttribute("id", "3");
					JalKrEl.appendChild(doc.createTextNode("Система кровообращения"));
					Mainelem.appendChild(JalKrEl);}
				if (jal_p.isSelected()){
					Element JalPEl = doc.createElement("jalob_p");
					JalPEl.setAttributeNode(doc.createAttribute("name"));
					JalPEl.setAttribute("name", "jal_p");
					JalPEl.setAttributeNode(doc.createAttribute("id"));
					JalPEl.setAttribute("id", "4");
					JalPEl.appendChild(doc.createTextNode("Система пищеварения"));
					Mainelem.appendChild(JalPEl);}
				if (jal_m.isSelected()){
					Element JalMEl = doc.createElement("jalob_m");
					JalMEl.setAttributeNode(doc.createAttribute("name"));
					JalMEl.setAttribute("name", "jal_m");
					JalMEl.setAttributeNode(doc.createAttribute("id"));
					JalMEl.setAttribute("id", "5");
					JalMEl.appendChild(doc.createTextNode("Мочеполовая система"));
					Mainelem.appendChild(JalMEl);}
				if (jal_en.isSelected()){
					Element JalEnEl = doc.createElement("jalob_en");
					JalEnEl.setAttributeNode(doc.createAttribute("name"));
					JalEnEl.setAttribute("name", "jal_en");
					JalEnEl.setAttributeNode(doc.createAttribute("id"));
					JalEnEl.setAttribute("id", "6");
					JalEnEl.appendChild(doc.createTextNode("Эндокринная система"));
					Mainelem.appendChild(JalEnEl);}
				if (jal_ner.isSelected()){
					Element JalNerEl = doc.createElement("jalob_nerv");
					JalNerEl.setAttributeNode(doc.createAttribute("name"));
					JalNerEl.setAttribute("name", "jal_ner");
					JalNerEl.setAttributeNode(doc.createAttribute("id"));
					JalNerEl.setAttribute("id", "7");
					JalNerEl.appendChild(doc.createTextNode("Нервная система и органы чувств"));
					Mainelem.appendChild(JalNerEl);}
				if (jal_op.isSelected()){
					Element JalOpEl = doc.createElement("jalob_opor");
					JalOpEl.setAttributeNode(doc.createAttribute("name"));
					JalOpEl.setAttribute("name", "jal_opor");
					JalOpEl.setAttributeNode(doc.createAttribute("id"));
					JalOpEl.setAttribute("id", "8");
					JalOpEl.appendChild(doc.createTextNode("Опорно-двигательная система"));
					Mainelem.appendChild(JalOpEl);}
				if (jal_l.isSelected()){
					Element JalLEl = doc.createElement("jalob_lih");
					JalLEl.setAttributeNode(doc.createAttribute("name"));
					JalLEl.setAttribute("name", "jal_lih");
					JalLEl.setAttributeNode(doc.createAttribute("id"));
					JalLEl.setAttribute("id", "9");
					JalLEl.appendChild(doc.createTextNode("Лихорадка"));
					Mainelem.appendChild(JalLEl);}
				if (jal_ob.isSelected()){
					Element JalObEl = doc.createElement("jalob_ob");
					JalObEl.setAttributeNode(doc.createAttribute("name"));
					JalObEl.setAttribute("name", "jal_ob");
					JalObEl.setAttributeNode(doc.createAttribute("id"));
					JalObEl.setAttribute("id", "10");
					JalObEl.appendChild(doc.createTextNode("Жалобы общего характера"));
					Mainelem.appendChild(JalObEl);}
				if (jal_pr.isSelected()){
					Element JalPrEl = doc.createElement("jalob_pr");
					JalPrEl.setAttributeNode(doc.createAttribute("name"));
					JalPrEl.setAttribute("name", "jal_pr");
					JalPrEl.setAttributeNode(doc.createAttribute("id"));
					JalPrEl.setAttribute("id", "11");
					JalPrEl.appendChild(doc.createTextNode("Прочие жалобы"));
					Mainelem.appendChild(JalPrEl);}
				if (istz.isSelected()){
					Element IstzEl = doc.createElement("ist_zab");
					IstzEl.setAttributeNode(doc.createAttribute("name"));
					IstzEl.setAttribute("name", "istzab");
					IstzEl.setAttributeNode(doc.createAttribute("id"));
					IstzEl.setAttribute("id", "12");
					IstzEl.appendChild(doc.createTextNode("История заболевания"));
					Mainelem.appendChild(IstzEl);}
				if (nach.isSelected()){
					Element NachzEl = doc.createElement("nach_zab");
					NachzEl.setAttributeNode(doc.createAttribute("name"));
					NachzEl.setAttribute("name", "nachzab");
					NachzEl.setAttributeNode(doc.createAttribute("id"));
					NachzEl.setAttribute("id", "13");
					NachzEl.appendChild(doc.createTextNode("Начало заболевания"));
					Mainelem.appendChild(NachzEl);}
				if (symp.isSelected()){
					Element SymEl = doc.createElement("sympt");
					SymEl.setAttributeNode(doc.createAttribute("name"));
					SymEl.setAttribute("name", "sympt");
					SymEl.setAttributeNode(doc.createAttribute("id"));
					SymEl.setAttribute("id", "14");
					SymEl.appendChild(doc.createTextNode("Симптомы"));
					Mainelem.appendChild(SymEl);}
				if (otn.isSelected()){
					Element OtnEl = doc.createElement("otn_bol");
					OtnEl.setAttributeNode(doc.createAttribute("name"));
					OtnEl.setAttribute("name", "otnbol");
					OtnEl.setAttributeNode(doc.createAttribute("id"));
					OtnEl.setAttribute("id", "15");
					OtnEl.appendChild(doc.createTextNode("Отношение больного к болезни"));
					Mainelem.appendChild(OtnEl);}
				if (ps.isSelected()){
					Element PsEl = doc.createElement("ps_sit");
					PsEl.setAttributeNode(doc.createAttribute("name"));
					PsEl.setAttribute("name", "pssit");
					PsEl.setAttributeNode(doc.createAttribute("id"));
					PsEl.setAttribute("id", "16");
					PsEl.appendChild(doc.createTextNode("Психол.ситуаци в связи с болезнью"));
					Mainelem.appendChild(PsEl);}
				if (allerg.isSelected()){
					Element AllerEl = doc.createElement("allerg");
					AllerEl.setAttributeNode(doc.createAttribute("name"));
					AllerEl.setAttribute("name", "allerg");
					AllerEl.setAttributeNode(doc.createAttribute("id"));
					AllerEl.setAttribute("id", "17");
					AllerEl.appendChild(doc.createTextNode("Аллергоанамнез"));
					Mainelem.appendChild(AllerEl);}
				if (ist.isSelected()){
					Element IstEl = doc.createElement("vitae");
					IstEl.setAttributeNode(doc.createAttribute("name"));
					IstEl.setAttribute("name", "vitae");
					IstEl.setAttributeNode(doc.createAttribute("id"));
					IstEl.setAttribute("id", "18");
					IstEl.appendChild(doc.createTextNode("История жизни"));
					Mainelem.appendChild(IstEl);}
				if (razv.isSelected()){
					Element RazvEl = doc.createElement("razv");
					RazvEl.setAttributeNode(doc.createAttribute("name"));
					RazvEl.setAttribute("name", "razv");
					RazvEl.setAttributeNode(doc.createAttribute("id"));
					RazvEl.setAttribute("id", "19");
					RazvEl.appendChild(doc.createTextNode("Развитие"));
					Mainelem.appendChild(RazvEl);}
				if (usl.isSelected()){
					Element UslEl = doc.createElement("uslov");
					UslEl.setAttributeNode(doc.createAttribute("name"));
					UslEl.setAttribute("name", "usl");
					UslEl.setAttributeNode(doc.createAttribute("id"));
					UslEl.setAttribute("id", "20");
					UslEl.appendChild(doc.createTextNode("Условия проживания"));
					Mainelem.appendChild(UslEl);}
				if (perz.isSelected()){
					Element PerzEl = doc.createElement("per_zab");
					PerzEl.setAttributeNode(doc.createAttribute("name"));
					PerzEl.setAttribute("name", "perz");
					PerzEl.setAttributeNode(doc.createAttribute("id"));
					PerzEl.setAttribute("id", "21");
					PerzEl.appendChild(doc.createTextNode("Перенесенные заболевания"));
					Mainelem.appendChild(PerzEl);}
				if (per_op.isSelected()){
					Element PeropEl = doc.createElement("per_op");
					PeropEl.setAttributeNode(doc.createAttribute("name"));
					PeropEl.setAttribute("name", "perop");
					PeropEl.setAttributeNode(doc.createAttribute("id"));
					PeropEl.setAttribute("id", "22");
					PeropEl.appendChild(doc.createTextNode("Перенесенные операции"));
					Mainelem.appendChild(PeropEl);}
				if (gem.isSelected()){
					Element GemEl = doc.createElement("gem_trans");
					GemEl.setAttributeNode(doc.createAttribute("name"));
					GemEl.setAttribute("name", "gem");
					GemEl.setAttributeNode(doc.createAttribute("id"));
					GemEl.setAttribute("id", "23");
					GemEl.appendChild(doc.createTextNode("Гемотрансфузия"));
					Mainelem.appendChild(GemEl);}
				if (nasl.isSelected()){
					Element NAsEl = doc.createElement("nasl");
					NAsEl.setAttributeNode(doc.createAttribute("name"));
					NAsEl.setAttribute("name", "nasl");
					NAsEl.setAttributeNode(doc.createAttribute("id"));
					NAsEl.setAttribute("id", "24");
					NAsEl.appendChild(doc.createTextNode("Наследственность"));
					Mainelem.appendChild(NAsEl);}
				if (gyn.isSelected()){
					Element GynEl = doc.createElement("gin_anamn");
					GynEl.setAttributeNode(doc.createAttribute("name"));
					GynEl.setAttribute("name", "ginanam");
					GynEl.setAttributeNode(doc.createAttribute("id"));
					GynEl.setAttribute("id", "25");
					GynEl.appendChild(doc.createTextNode("Гинекологич.анамнез"));
					Mainelem.appendChild(GynEl);}
				if (farm.isSelected()){
					Element FarmEl = doc.createElement("farm_anamn");
					FarmEl.setAttributeNode(doc.createAttribute("name"));
					FarmEl.setAttribute("name", "farm");
					FarmEl.setAttributeNode(doc.createAttribute("id"));
					FarmEl.setAttribute("id", "26");
					FarmEl.appendChild(doc.createTextNode("Фармакологический анамнез"));
					Mainelem.appendChild(FarmEl);}
				if (lek.isSelected()){
					Element LekEl = doc.createElement("lek_sr");
					LekEl.setAttributeNode(doc.createAttribute("name"));
					LekEl.setAttribute("name", "lek");
					LekEl.setAttributeNode(doc.createAttribute("id"));
					LekEl.setAttribute("id", "27");
					LekEl.appendChild(doc.createTextNode("Прием лек.средств"));
					Mainelem.appendChild(LekEl);}
				if (gorm.isSelected()){
					Element GormEl = doc.createElement("gormon_prep");
					GormEl.setAttributeNode(doc.createAttribute("name"));
					GormEl.setAttribute("name", "gorm");
					GormEl.setAttributeNode(doc.createAttribute("id"));
					GormEl.setAttribute("id", "28");
					GormEl.appendChild(doc.createTextNode("Применение гормон.препаратов"));
					Mainelem.appendChild(GormEl);}
				if (st.isSelected()){
					Element StPEl = doc.createElement("st_praesense");
					StPEl.setAttributeNode(doc.createAttribute("name"));
					StPEl.setAttribute("name", "status praesense");
					StPEl.setAttributeNode(doc.createAttribute("id"));
					StPEl.setAttribute("id", "29");
					StPEl.appendChild(doc.createTextNode("Status Praesense"));
					Mainelem.appendChild(StPEl);}
				if (ob.isSelected()){
					Element ObEl = doc.createElement("ob_sost");
					ObEl.setAttributeNode(doc.createAttribute("name"));
					ObEl.setAttribute("name", "obs");
					ObEl.setAttributeNode(doc.createAttribute("id"));
					ObEl.setAttribute("id", "30");
					ObEl.appendChild(doc.createTextNode("Общее состояние"));
					Mainelem.appendChild(ObEl);}
				if (koj.isSelected()){
					Element KojEl = doc.createElement("koj_pokr");
					KojEl.setAttributeNode(doc.createAttribute("name"));
					KojEl.setAttribute("name", "kojp");
					KojEl.setAttributeNode(doc.createAttribute("id"));
					KojEl.setAttribute("id", "31");
					KojEl.appendChild(doc.createTextNode("Кожные покровы"));
					Mainelem.appendChild(KojEl);}
				if (sl.isSelected()){
					Element SlEl = doc.createElement("sliz");
					SlEl.setAttributeNode(doc.createAttribute("name"));
					SlEl.setAttribute("name", "sliz");
					SlEl.setAttributeNode(doc.createAttribute("id"));
					SlEl.setAttribute("id", "32");
					SlEl.appendChild(doc.createTextNode("Видимые слизистые"));
					Mainelem.appendChild(SlEl);}
				if (kl.isSelected()){
					Element KlEl = doc.createElement("podk_kl");
					KlEl.setAttributeNode(doc.createAttribute("name"));
					KlEl.setAttribute("name", "podkl");
					KlEl.setAttributeNode(doc.createAttribute("id"));
					KlEl.setAttribute("id", "33");
					KlEl.appendChild(doc.createTextNode("Подкожная клетчатка"));
					Mainelem.appendChild(KlEl);}
				if (lim.isSelected()){
					Element LimEl = doc.createElement("limf");
					LimEl.setAttributeNode(doc.createAttribute("name"));
					LimEl.setAttribute("name", "limf");
					LimEl.setAttributeNode(doc.createAttribute("id"));
					LimEl.setAttribute("id", "34");
					LimEl.appendChild(doc.createTextNode("Лимф.узлы"));
					Mainelem.appendChild(LimEl);}
				if (kost.isSelected()){
					Element KosEl = doc.createElement("kost");
					KosEl.setAttributeNode(doc.createAttribute("name"));
					KosEl.setAttribute("name", "kost");
					KosEl.setAttributeNode(doc.createAttribute("id"));
					KosEl.setAttribute("id", "35");
					KosEl.appendChild(doc.createTextNode("Костно-мышечная система"));
					Mainelem.appendChild(KosEl);}
				if (nerv.isSelected()){
					Element NervEl = doc.createElement("nerv_ps");
					NervEl.setAttributeNode(doc.createAttribute("name"));
					NervEl.setAttribute("name", "nerv");
					NervEl.setAttributeNode(doc.createAttribute("id"));
					NervEl.setAttribute("id", "36");
					NervEl.appendChild(doc.createTextNode("Нервно-психический статус"));
					Mainelem.appendChild(NervEl);}
				if (chss.isSelected()){
					Element CHSSEl = doc.createElement("chss");
					CHSSEl.setAttributeNode(doc.createAttribute("name"));
					CHSSEl.setAttribute("name", "chss");
					CHSSEl.setAttributeNode(doc.createAttribute("id"));
					CHSSEl.setAttribute("id", "37");
					CHSSEl.appendChild(doc.createTextNode("ЧСС"));
					Mainelem.appendChild(CHSSEl);}
				if (temp.isSelected()){
					Element TempEl = doc.createElement("temp");
					TempEl.setAttributeNode(doc.createAttribute("name"));
					TempEl.setAttribute("name", "temp");
					TempEl.setAttributeNode(doc.createAttribute("id"));
					TempEl.setAttribute("id", "38");
					TempEl.appendChild(doc.createTextNode("Температура"));
					Mainelem.appendChild(TempEl);}
				if (art.isSelected()){
					Element AdEl = doc.createElement("ad");
					AdEl.setAttributeNode(doc.createAttribute("name"));
					AdEl.setAttribute("name", "ad");
					AdEl.setAttributeNode(doc.createAttribute("id"));
					AdEl.setAttribute("id", "39");
					AdEl.appendChild(doc.createTextNode("АД"));
					Mainelem.appendChild(AdEl);}
				if (rost.isSelected()){
					Element RostEl = doc.createElement("rost");
					RostEl.setAttributeNode(doc.createAttribute("name"));
					RostEl.setAttribute("name", "rost");
					RostEl.setAttributeNode(doc.createAttribute("id"));
					RostEl.setAttribute("id", "40");
					RostEl.appendChild(doc.createTextNode("Рост"));
					Mainelem.appendChild(RostEl);}	
				if (ves.isSelected()){
					Element VesEl = doc.createElement("ves");
					VesEl.setAttributeNode(doc.createAttribute("name"));
					VesEl.setAttribute("name", "ves");
					VesEl.setAttributeNode(doc.createAttribute("id"));
					VesEl.setAttribute("id", "41");
					VesEl.appendChild(doc.createTextNode("Вес"));
					Mainelem.appendChild(VesEl);}
				if (telo.isSelected()){
					Element TeloEl = doc.createElement("telosl");
					TeloEl.setAttributeNode(doc.createAttribute("name"));
					TeloEl.setAttribute("name", "telo");
					TeloEl.setAttributeNode(doc.createAttribute("id"));
					TeloEl.setAttribute("id", "42");
					TeloEl.appendChild(doc.createTextNode("Телосложение"));
					Mainelem.appendChild(TeloEl);}
				if (fiz.isSelected()){
					Element FizEl = doc.createElement("fiz_obsl");
					FizEl.setAttributeNode(doc.createAttribute("name"));
					FizEl.setAttribute("name", "fiz");
					FizEl.setAttributeNode(doc.createAttribute("id"));
					FizEl.setAttribute("id", "43");
					FizEl.appendChild(doc.createTextNode("Физикальное обследование"));
					Mainelem.appendChild(FizEl);}
				if (sust.isSelected()){
					Element SustEl = doc.createElement("sust");
					SustEl.setAttributeNode(doc.createAttribute("name"));
					SustEl.setAttribute("name", "sust");
					SustEl.setAttributeNode(doc.createAttribute("id"));
					SustEl.setAttribute("id", "44");
					SustEl.appendChild(doc.createTextNode("Суставы"));
					Mainelem.appendChild(SustEl);}
				if (dyh.isSelected()){
					Element DyhEl = doc.createElement("dyh");
					DyhEl.setAttributeNode(doc.createAttribute("name"));
					DyhEl.setAttribute("name", "dyh");
					DyhEl.setAttributeNode(doc.createAttribute("id"));
					DyhEl.setAttribute("id", "45");
					DyhEl.appendChild(doc.createTextNode("Дыхание"));
					Mainelem.appendChild(DyhEl);}
				if (gr.isSelected()){
					Element GrEl = doc.createElement("gr_kl");
					GrEl.setAttributeNode(doc.createAttribute("name"));
					GrEl.setAttribute("name", "grk");
					GrEl.setAttributeNode(doc.createAttribute("id"));
					GrEl.setAttribute("id", "46");
					GrEl.appendChild(doc.createTextNode("Грудная клетка"));
					Mainelem.appendChild(GrEl);}
				if (perl.isSelected()){
					Element PerlEl = doc.createElement("perk_l");
					PerlEl.setAttributeNode(doc.createAttribute("name"));
					PerlEl.setAttribute("name", "perl");
					PerlEl.setAttributeNode(doc.createAttribute("id"));
					PerlEl.setAttribute("id", "47");
					PerlEl.appendChild(doc.createTextNode("Перкуссия легких"));
					Mainelem.appendChild(PerlEl);}
				if (ausl.isSelected()){
					Element AuslEl = doc.createElement("aus_l");
					AuslEl.setAttributeNode(doc.createAttribute("name"));
					AuslEl.setAttribute("name", "ausl");
					AuslEl.setAttributeNode(doc.createAttribute("id"));
					AuslEl.setAttribute("id", "48");
					AuslEl.appendChild(doc.createTextNode("Аускультация легких"));
					Mainelem.appendChild(AuslEl);}
				if (bronh.isSelected()){
					Element BronhEl = doc.createElement("bronho");
					BronhEl.setAttributeNode(doc.createAttribute("name"));
					BronhEl.setAttribute("name", "bronh");
					BronhEl.setAttributeNode(doc.createAttribute("id"));
					BronhEl.setAttribute("id", "49");
					BronhEl.appendChild(doc.createTextNode("Бронхофония"));
					Mainelem.appendChild(BronhEl);}
				if (arter.isSelected()){
					Element ArterEl = doc.createElement("arter");
					ArterEl.setAttributeNode(doc.createAttribute("name"));
					ArterEl.setAttribute("name", "arter");
					ArterEl.setAttributeNode(doc.createAttribute("id"));
					ArterEl.setAttribute("id", "50");
					ArterEl.appendChild(doc.createTextNode("Артерии и шейные вены"));
					Mainelem.appendChild(ArterEl);}
				if (obls.isSelected()){
					Element OblSEl = doc.createElement("obl_s");
					OblSEl.setAttributeNode(doc.createAttribute("name"));
					OblSEl.setAttribute("name", "obls");
					OblSEl.setAttributeNode(doc.createAttribute("id"));
					OblSEl.setAttribute("id", "51");
					OblSEl.appendChild(doc.createTextNode("Область сердца"));
					Mainelem.appendChild(OblSEl);}
				if (pers.isSelected()){
					Element PerSEl = doc.createElement("perk_s");
					PerSEl.setAttributeNode(doc.createAttribute("name"));
					PerSEl.setAttribute("name", "pers");
					PerSEl.setAttributeNode(doc.createAttribute("id"));
					PerSEl.setAttribute("id", "52");
					PerSEl.appendChild(doc.createTextNode("Перкуссия сердца"));
					Mainelem.appendChild(PerSEl);}
				if (auss.isSelected()){
					Element AusSEl = doc.createElement("ausk_s");
					AusSEl.setAttributeNode(doc.createAttribute("name"));
					AusSEl.setAttribute("name", "auss");
					AusSEl.setAttributeNode(doc.createAttribute("id"));
					AusSEl.setAttribute("id", "53");
					AusSEl.appendChild(doc.createTextNode("Аускультация сердца"));
					Mainelem.appendChild(AusSEl);}
				if (polr.isSelected()){
					Element PolrEl = doc.createElement("pol_rta");
					PolrEl.setAttributeNode(doc.createAttribute("name"));
					PolrEl.setAttribute("name", "polr");
					PolrEl.setAttributeNode(doc.createAttribute("id"));
					PolrEl.setAttribute("id", "54");
					PolrEl.appendChild(doc.createTextNode("Полость рта"));
					Mainelem.appendChild(PolrEl);}
				if (jiv.isSelected()){
					Element JivEl = doc.createElement("jivot");
					JivEl.setAttributeNode(doc.createAttribute("name"));
					JivEl.setAttribute("name", "jiv");
					JivEl.setAttributeNode(doc.createAttribute("id"));
					JivEl.setAttribute("id", "55");
					JivEl.appendChild(doc.createTextNode("Живот"));
					Mainelem.appendChild(JivEl);}
				if (palj.isSelected()){
					Element PaljEl = doc.createElement("palp_j");
					PaljEl.setAttributeNode(doc.createAttribute("name"));
					PaljEl.setAttribute("name", "palj");
					PaljEl.setAttributeNode(doc.createAttribute("id"));
					PaljEl.setAttribute("id", "56");
					PaljEl.appendChild(doc.createTextNode("Пальпация живота"));
					Mainelem.appendChild(PaljEl);}
				if (jkt.isSelected()){
					Element JktEl = doc.createElement("jkt");
					JktEl.setAttributeNode(doc.createAttribute("name"));
					JktEl.setAttribute("name", "jkt");
					JktEl.setAttributeNode(doc.createAttribute("id"));
					JktEl.setAttribute("id", "57");
					JktEl.appendChild(doc.createTextNode("ЖКТ"));
					Mainelem.appendChild(JktEl);}
				if (palpj.isSelected()){
					Element PalpjEl = doc.createElement("palp_jel");
					PalpjEl.setAttributeNode(doc.createAttribute("name"));
					PalpjEl.setAttribute("name", "palp_jel");
					PalpjEl.setAttributeNode(doc.createAttribute("id"));
					PalpjEl.setAttribute("id", "58");
					PalpjEl.appendChild(doc.createTextNode("Пальпация желудка"));
					Mainelem.appendChild(PalpjEl);}
				if (palpod.isSelected()){
					Element PalpodEl = doc.createElement("palp_podjel");
					PalpodEl.setAttributeNode(doc.createAttribute("name"));
					PalpodEl.setAttribute("name", "palpod");
					PalpodEl.setAttributeNode(doc.createAttribute("id"));
					PalpodEl.setAttribute("id", "59");
					PalpodEl.appendChild(doc.createTextNode("Пальпация поджел.железы"));
					Mainelem.appendChild(PalpodEl);}
				if (pech.isSelected()){
					Element PechEl = doc.createElement("pechen");
					PechEl.setAttributeNode(doc.createAttribute("name"));
					PechEl.setAttribute("name", "pech");
					PechEl.setAttributeNode(doc.createAttribute("id"));
					PechEl.setAttribute("id", "60");
					PechEl.appendChild(doc.createTextNode("Печень"));
					Mainelem.appendChild(PechEl);}
				if (jelch.isSelected()){
					Element JechEl = doc.createElement("jelch_p");
					JechEl.setAttributeNode(doc.createAttribute("name"));
					JechEl.setAttribute("name", "jelch");
					JechEl.setAttributeNode(doc.createAttribute("id"));
					JechEl.setAttribute("id", "61");
					JechEl.appendChild(doc.createTextNode("Желчный пузырь"));
					Mainelem.appendChild(JechEl);}
				if (selez.isSelected()){
					Element SelezEl = doc.createElement("selez");
					SelezEl.setAttributeNode(doc.createAttribute("name"));
					SelezEl.setAttribute("name", "selez");
					SelezEl.setAttributeNode(doc.createAttribute("id"));
					SelezEl.setAttribute("id", "62");
					SelezEl.appendChild(doc.createTextNode("Селезенка"));
					Mainelem.appendChild(SelezEl);}
				if (oblz.isSelected()){
					Element OblzEl = doc.createElement("obl_zad");
					OblzEl.setAttributeNode(doc.createAttribute("name"));
					OblzEl.setAttribute("name", "oblz");
					OblzEl.setAttributeNode(doc.createAttribute("id"));
					OblzEl.setAttribute("id", "63");
					OblzEl.appendChild(doc.createTextNode("Обл.заднего прохода"));
					Mainelem.appendChild(OblzEl);}
				if (oblp.isSelected()){
					Element OblPEl = doc.createElement("poyasn");
					OblPEl.setAttributeNode(doc.createAttribute("name"));
					OblPEl.setAttribute("name", "oblp");
					OblPEl.setAttributeNode(doc.createAttribute("id"));
					OblPEl.setAttribute("id", "64");
					OblPEl.appendChild(doc.createTextNode("Поясничная область"));
					Mainelem.appendChild(OblPEl);}
				if (pochk.isSelected()){
					Element PochkEl = doc.createElement("pochki");
					PochkEl.setAttributeNode(doc.createAttribute("name"));
					PochkEl.setAttribute("name", "pochk");
					PochkEl.setAttributeNode(doc.createAttribute("id"));
					PochkEl.setAttribute("id", "65");
					PochkEl.appendChild(doc.createTextNode("Почки"));
					Mainelem.appendChild(PochkEl);}
				if (moch.isSelected()){
					Element MochEl = doc.createElement("moch_p");
					MochEl.setAttributeNode(doc.createAttribute("name"));
					MochEl.setAttribute("name", "moch");
					MochEl.setAttributeNode(doc.createAttribute("id"));
					MochEl.setAttribute("id", "66");
					MochEl.appendChild(doc.createTextNode("Мочевой пузырь"));
					Mainelem.appendChild(MochEl);}
				if (jelm.isSelected()){
					Element JelmEl = doc.createElement("mol_jel");
					JelmEl.setAttributeNode(doc.createAttribute("name"));
					JelmEl.setAttribute("name", "jelm");
					JelmEl.setAttributeNode(doc.createAttribute("id"));
					JelmEl.setAttribute("id", "67");
					JelmEl.appendChild(doc.createTextNode("Молочные железы"));
					Mainelem.appendChild(JelmEl);}
				if (jelgr.isSelected()){
					Element JelgrEl = doc.createElement("gr_jel");
					JelgrEl.setAttributeNode(doc.createAttribute("name"));
					JelgrEl.setAttribute("name", "jelgr");
					JelgrEl.setAttributeNode(doc.createAttribute("id"));
					JelgrEl.setAttribute("id", "68");
					JelgrEl.appendChild(doc.createTextNode("Грудные железы мужчин"));
					Mainelem.appendChild(JelgrEl);}
				if (matka.isSelected()){
					Element MatEl = doc.createElement("matka");
					MatEl.setAttributeNode(doc.createAttribute("name"));
					MatEl.setAttribute("name", "matka");
					MatEl.setAttributeNode(doc.createAttribute("id"));
					MatEl.setAttribute("id", "69");
					MatEl.appendChild(doc.createTextNode("Матка и ее придатки"));
					Mainelem.appendChild(MatEl);}
				if (polorg.isSelected()){
					Element PolEl = doc.createElement("polov_org");
					PolEl.setAttributeNode(doc.createAttribute("name"));
					PolEl.setAttribute("name", "polorg");
					PolEl.setAttributeNode(doc.createAttribute("id"));
					PolEl.setAttribute("id", "70");
					PolEl.appendChild(doc.createTextNode("Полов.органы у мужчин"));
					Mainelem.appendChild(PolEl);}
				if (chit.isSelected()){
					Element ChitEl = doc.createElement("chitov");
					ChitEl.setAttributeNode(doc.createAttribute("name"));
					ChitEl.setAttribute("name", "chit");
					ChitEl.setAttributeNode(doc.createAttribute("id"));
					ChitEl.setAttribute("id", "71");
					ChitEl.appendChild(doc.createTextNode("Щитов.железа"));
					Mainelem.appendChild(ChitEl);}
				if (stloc.isSelected()){
					Element StLocEl = doc.createElement("stloc");
					StLocEl.setAttributeNode(doc.createAttribute("name"));
					StLocEl.setAttribute("name", "status localis");
					StLocEl.setAttributeNode(doc.createAttribute("id"));
					StLocEl.setAttribute("id", "72");
					StLocEl.appendChild(doc.createTextNode("Status Localis"));
					Mainelem.appendChild(StLocEl);}
				if (ocen.isSelected()){
					Element OcenEl = doc.createElement("ocenka");
					OcenEl.setAttributeNode(doc.createAttribute("name"));
					OcenEl.setAttribute("name", "ocen");
					OcenEl.setAttributeNode(doc.createAttribute("id"));
					OcenEl.setAttribute("id", "73");
					OcenEl.appendChild(doc.createTextNode("Оценка данных анамнеза и объективного иссл."));
					Mainelem.appendChild(OcenEl);}
		 
//				javax.xml.transform.Result result = new StreamResult("SettingsOsm.xml");
//				Source source = new DOMSource(doc);
//				Transformer xformer;
//				try {
//				xformer = TransformerFactory.newInstance().newTransformer();
//				} catch (TransformerConfigurationException e) {
//				e.printStackTrace();
//				return;
//				} catch (TransformerFactoryConfigurationError e) {
//				e.printStackTrace();
//				return;
//				}
//				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
//				try {
//				xformer.transform(source, result);
//				} catch (TransformerException e) {
//				e.printStackTrace();
//				return;
//				}
				try {
					StringWriter sw = new StringWriter();
					StreamResult sr = new StreamResult(sw);
					TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), sr);
					MainForm.tcl.saveUserConfig(MainForm.authInfo.user_id, sw.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GroupLayout gl_osn = new GroupLayout(osn);
		gl_osn.setHorizontalGroup(
			gl_osn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_osn.createSequentialGroup()
					.addGroup(gl_osn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_osn.createSequentialGroup()
							.addGroup(gl_osn.createParallelGroup(Alignment.LEADING)
								.addComponent(pjalob, GroupLayout.PREFERRED_SIZE, 321, Short.MAX_VALUE)
								.addComponent(pistz, GroupLayout.PREFERRED_SIZE, 321, Short.MAX_VALUE))
							.addGroup(gl_osn.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_osn.createSequentialGroup()
									.addGap(18)
									.addComponent(pmorbi, GroupLayout.PREFERRED_SIZE, 268, Short.MAX_VALUE))
								.addGroup(gl_osn.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_osn.createParallelGroup(Alignment.LEADING)
										.addComponent(pocen, GroupLayout.PREFERRED_SIZE, 268, Short.MAX_VALUE)
										.addComponent(pstloc, GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
										.addComponent(button))
									.addGap(8))))
						.addComponent(pstpr, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(pfiz, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_osn.setVerticalGroup(
			gl_osn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_osn.createSequentialGroup()
					.addGroup(gl_osn.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_osn.createSequentialGroup()
							.addComponent(pjalob, GroupLayout.PREFERRED_SIZE, 169, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pistz, GroupLayout.PREFERRED_SIZE, 169, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pstpr, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_osn.createSequentialGroup()
							.addComponent(pfiz, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
							.addGap(105))
						.addGroup(gl_osn.createSequentialGroup()
							.addComponent(pmorbi, GroupLayout.PREFERRED_SIZE, 138, Short.MAX_VALUE)
							.addGap(15)
							.addComponent(pstloc, GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pocen, GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(button)
							.addGap(311)))
					.addContainerGap())
		);
		
		jal_kr = new JCheckBox("Система кровообращения");
		
		jal_d = new JCheckBox("Дыхательная система");
		
		jal_p = new JCheckBox("Система пищеварения");
		
		jal_m = new JCheckBox("Мочеполовая система");
		
		jal_en = new JCheckBox("Эндокринная система");
		
		jal_ner = new JCheckBox("Нервная система и органы чувств");
		
		jal_op = new JCheckBox("Опорно-двигательная система");
		
		jal_l = new JCheckBox("Лихорадка");
		
		jal_ob = new JCheckBox("Жалобы общего характера");
		
		jal_pr = new JCheckBox("Прочие жалобы");
		
		JLabel label = new JLabel("Жалобы:");
		GroupLayout gl_pjalob = new GroupLayout(pjalob);
		gl_pjalob.setHorizontalGroup(
			gl_pjalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pjalob.createSequentialGroup()
					.addGroup(gl_pjalob.createParallelGroup(Alignment.LEADING)
						.addComponent(jal_m)
						.addComponent(jal_d)
						.addGroup(gl_pjalob.createSequentialGroup()
							.addContainerGap()
							.addComponent(label)))
					.addPreferredGap(ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
					.addGroup(gl_pjalob.createParallelGroup(Alignment.LEADING)
						.addComponent(jal_kr)
						.addComponent(jal_p)
						.addComponent(jal_en))
					.addContainerGap())
				.addGroup(gl_pjalob.createSequentialGroup()
					.addComponent(jal_ner)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jal_l)
					.addGap(44))
				.addGroup(gl_pjalob.createSequentialGroup()
					.addComponent(jal_op)
					.addGap(18)
					.addComponent(jal_pr)
					.addGap(44))
				.addGroup(gl_pjalob.createSequentialGroup()
					.addComponent(jal_ob)
					.addContainerGap())
		);
		gl_pjalob.setVerticalGroup(
			gl_pjalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pjalob.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pjalob.createParallelGroup(Alignment.BASELINE)
						.addComponent(jal_kr)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pjalob.createParallelGroup(Alignment.BASELINE)
						.addComponent(jal_d)
						.addComponent(jal_p))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pjalob.createParallelGroup(Alignment.BASELINE)
						.addComponent(jal_m)
						.addComponent(jal_en))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pjalob.createParallelGroup(Alignment.BASELINE)
						.addComponent(jal_ner)
						.addComponent(jal_l))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pjalob.createParallelGroup(Alignment.BASELINE)
						.addComponent(jal_op)
						.addComponent(jal_pr))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(jal_ob)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pjalob.setLayout(gl_pjalob);
		osn.setLayout(gl_osn);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(osn, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(osn, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
	}
	public void loadConfig() {
		try {
			StringReader sr = new StringReader(MainForm.authInfo.config);
			StreamSource src = new StreamSource(sr);
			DOMResult res = new DOMResult();
			TransformerFactory.newInstance().newTransformer().transform(src, res);
			Document document = (Document) res.getNode();
			jal_d.setSelected(getElement(document, "jalob_dyh") != null);
			jal_kr.setSelected(getElement(document, "jalob_kr") != null);
			jal_p.setSelected(getElement(document, "jalob_op") != null);
			jal_m.setSelected(getElement(document, "jalob_m") != null);
			jal_en.setSelected(getElement(document, "jalob_en") != null);
			jal_ner.setSelected(getElement(document, "jalob_nerv") != null);
			jal_op.setSelected(getElement(document, "jalob_opor") != null);
			jal_l.setSelected(getElement(document, "jalob_lih") != null);
			jal_ob.setSelected(getElement(document, "jalob_ob") != null);
			jal_pr.setSelected(getElement(document, "jalob_pr") != null);
			istz.setSelected(getElement(document, "ist_zab") != null);
			nach.setSelected(getElement(document, "nach_zab") != null);
			symp.setSelected(getElement(document, "sympt") != null);
			otn.setSelected(getElement(document, "otn_bol") != null);
			ps.setSelected(getElement(document, "ps_sit") != null);
			allerg.setSelected(getElement(document, "allerg") != null);
			ist.setSelected(getElement(document, "vitae") != null);
			razv.setSelected(getElement(document, "razv") != null);
			usl.setSelected(getElement(document, "uslov") != null);
			perz.setSelected(getElement(document, "per_zab") != null);
			per_op.setSelected(getElement(document, "per_op") != null);
			gem.setSelected(getElement(document, "gem_trans") != null);
			nasl.setSelected(getElement(document, "nasl") != null);
			gyn.setSelected(getElement(document, "gin_anamn") != null);
			farm.setSelected(getElement(document, "farm_anamn") != null);
			lek.setSelected(getElement(document, "lek_sr") != null);
			gorm.setSelected(getElement(document, "gormon_prep") != null);
			st.setSelected(getElement(document, "st_praesense") != null);
			ob.setSelected(getElement(document, "ob_sost") != null);
			koj.setSelected(getElement(document, "koj_pokr") != null);
			sl.setSelected(getElement(document, "sliz") != null);
			kl.setSelected(getElement(document, "podk_kl") != null);
			lim.setSelected(getElement(document, "limf") != null);
			kost.setSelected(getElement(document, "kost") != null);
			nerv.setSelected(getElement(document, "nerv_ps") != null);
			chss.setSelected(getElement(document, "chss") != null);
			temp.setSelected(getElement(document, "temp") != null);
			art.setSelected(getElement(document, "ad") != null);
			rost.setSelected(getElement(document, "rost") != null);
			ves.setSelected(getElement(document, "ves") != null);
			telo.setSelected(getElement(document, "telosl") != null);
			fiz.setSelected(getElement(document, "fiz_obsl") != null);
			sust.setSelected(getElement(document, "sust") != null);
			dyh.setSelected(getElement(document, "dyh") != null);
			gr.setSelected(getElement(document, "gr_kl") != null);
			perl.setSelected(getElement(document, "perk_l") != null);
			ausl.setSelected(getElement(document, "aus_l") != null);
			bronh.setSelected(getElement(document, "bronho") != null);
			arter.setSelected(getElement(document, "arter") != null);
			obls.setSelected(getElement(document, "obl_s") != null);
			pers.setSelected(getElement(document, "perk_s") != null);
			auss.setSelected(getElement(document, "ausk_s") != null);
			polr.setSelected(getElement(document, "pol_rta") != null);
			jiv.setSelected(getElement(document, "jivot") != null);
			palj.setSelected(getElement(document, "palp_j") != null);
			jkt.setSelected(getElement(document, "jkt") != null);
			palpj.setSelected(getElement(document, "palp_jel") != null);
			palpod.setSelected(getElement(document, "palp_podjel") != null);
			pech.setSelected(getElement(document, "pechen") != null);
			jelch.setSelected(getElement(document, "jelch_p") != null);
			selez.setSelected(getElement(document, "selez") != null);
			oblz.setSelected(getElement(document, "obl_zad") != null);
			oblp.setSelected(getElement(document, "poyasn") != null);
			pochk.setSelected(getElement(document, "pochki") != null);
			moch.setSelected(getElement(document, "moch_p") != null);
			jelm.setSelected(getElement(document, "mol_jel") != null);
			jelgr.setSelected(getElement(document, "gr_jel") != null);
			matka.setSelected(getElement(document, "matka") != null);
			polorg.setSelected(getElement(document, "polov_org") != null);
			chit.setSelected(getElement(document, "chitov") != null);
			stloc.setSelected(getElement(document, "stloc") != null);
			ocen.setSelected(getElement(document, "ocenka") != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Element getElement(Document doc, String name){
		NodeList nls;
		nls = doc.getElementsByTagName(name);
		if (nls.getLength()>0) {
			return (Element) nls.item(0);
		}
		return null;
	}
}
