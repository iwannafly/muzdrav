package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SettingsOsm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JCheckBox jal;
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
				if (jal.isSelected()){
				Element JalEl = doc.createElement("Jalob");
				JalEl.setAttributeNode(doc.createAttribute("name"));
				JalEl.setAttribute("name", "Жалобы");
				JalEl.appendChild(doc.createTextNode("Jalob"));
				Mainelem.appendChild(JalEl);}
				if (jal_d.isSelected()){
					Element JalDEl = doc.createElement("Jalob_dyh");
					JalDEl.setAttributeNode(doc.createAttribute("name"));
					JalDEl.setAttribute("name", "Жалобы.Дых.система");
					JalDEl.appendChild(doc.createTextNode("Jalob_dyh"));
					Mainelem.appendChild(JalDEl);}
				if (jal_kr.isSelected()){
					Element JalKrEl = doc.createElement("Jalob_kr");
					JalKrEl.setAttributeNode(doc.createAttribute("name"));
					JalKrEl.setAttribute("name", "Жалобы.Кровен.система");
					JalKrEl.appendChild(doc.createTextNode("Jalob_kr"));
					Mainelem.appendChild(JalKrEl);}
				if (jal_p.isSelected()){
					Element JalPEl = doc.createElement("Jalob_p");
					JalPEl.setAttributeNode(doc.createAttribute("name"));
					JalPEl.setAttribute("name", "Жалобы.Система пищев.");
					JalPEl.appendChild(doc.createTextNode("Jalob_p"));
					Mainelem.appendChild(JalPEl);}
				if (jal_m.isSelected()){
					Element JalMEl = doc.createElement("Jalob_m");
					JalMEl.setAttributeNode(doc.createAttribute("name"));
					JalMEl.setAttribute("name", "Жалобы.Мочеполов.сист.");
					JalMEl.appendChild(doc.createTextNode("Jalob_m"));
					Mainelem.appendChild(JalMEl);}
				if (jal_en.isSelected()){
					Element JalEnEl = doc.createElement("Jalob_en");
					JalEnEl.setAttributeNode(doc.createAttribute("name"));
					JalEnEl.setAttribute("name", "Жалобы.Эндокрин.сист.");
					JalEnEl.appendChild(doc.createTextNode("Jalob_en"));
					Mainelem.appendChild(JalEnEl);}
				if (jal_ner.isSelected()){
					Element JalNerEl = doc.createElement("Jalob_nerv");
					JalNerEl.setAttributeNode(doc.createAttribute("name"));
					JalNerEl.setAttribute("name", "Жалобы.Нервн.сист.");
					JalNerEl.appendChild(doc.createTextNode("Jalob_nerv"));
					Mainelem.appendChild(JalNerEl);}
				if (jal_op.isSelected()){
					Element JalOpEl = doc.createElement("Jalob_opor");
					JalOpEl.setAttributeNode(doc.createAttribute("name"));
					JalOpEl.setAttribute("name", "Жалобы.Опорно-дв.сист.");
					JalOpEl.appendChild(doc.createTextNode("Jalob_opor"));
					Mainelem.appendChild(JalOpEl);}
				if (jal_l.isSelected()){
					Element JalLEl = doc.createElement("Jalob_lih");
					JalLEl.setAttributeNode(doc.createAttribute("name"));
					JalLEl.setAttribute("name", "Жалобы.Лихорадка");
					JalLEl.appendChild(doc.createTextNode("Jalob_lih"));
					Mainelem.appendChild(JalLEl);}
				if (jal_ob.isSelected()){
					Element JalObEl = doc.createElement("Jalob_ob");
					JalObEl.setAttributeNode(doc.createAttribute("name"));
					JalObEl.setAttribute("name", "Жалобы общего характера");
					JalObEl.appendChild(doc.createTextNode("Jalob_ob"));
					Mainelem.appendChild(JalObEl);}
				if (jal_pr.isSelected()){
					Element JalPrEl = doc.createElement("Jalob_pr");
					JalPrEl.setAttributeNode(doc.createAttribute("name"));
					JalPrEl.setAttribute("name", "Прочие жалобы");
					JalPrEl.appendChild(doc.createTextNode("Jalob_pr"));
					Mainelem.appendChild(JalPrEl);}
				if (istz.isSelected()){
					Element IstzEl = doc.createElement("Ist_zab");
					IstzEl.setAttributeNode(doc.createAttribute("name"));
					IstzEl.setAttribute("name", "История заболевания");
					IstzEl.appendChild(doc.createTextNode("Ist_zab"));
					Mainelem.appendChild(IstzEl);}
				if (nach.isSelected()){
					Element NachzEl = doc.createElement("Nach_zab");
					NachzEl.setAttributeNode(doc.createAttribute("name"));
					NachzEl.setAttribute("name", "Начало заболевания");
					NachzEl.appendChild(doc.createTextNode("Nach_zab"));
					Mainelem.appendChild(NachzEl);}
				if (symp.isSelected()){
					Element SymEl = doc.createElement("Sympt");
					SymEl.setAttributeNode(doc.createAttribute("name"));
					SymEl.setAttribute("name", "Симптомы");
					SymEl.appendChild(doc.createTextNode("Sympt"));
					Mainelem.appendChild(SymEl);}
				if (otn.isSelected()){
					Element OtnEl = doc.createElement("Otn_bol");
					OtnEl.setAttributeNode(doc.createAttribute("name"));
					OtnEl.setAttribute("name", "Отношение больного к болезни");
					OtnEl.appendChild(doc.createTextNode("Otn_bol"));
					Mainelem.appendChild(OtnEl);}
				if (ps.isSelected()){
					Element PsEl = doc.createElement("Ps_sit");
					PsEl.setAttributeNode(doc.createAttribute("name"));
					PsEl.setAttribute("name", "Психол.ситуация в связи с бол.");
					PsEl.appendChild(doc.createTextNode("Ps_sit"));
					Mainelem.appendChild(PsEl);}
				if (allerg.isSelected()){
					Element AllerEl = doc.createElement("Allerg");
					AllerEl.setAttributeNode(doc.createAttribute("name"));
					AllerEl.setAttribute("name", "Аллергоанамнез");
					AllerEl.appendChild(doc.createTextNode("Allerg"));
					Mainelem.appendChild(AllerEl);}
				if (ist.isSelected()){
					Element IstEl = doc.createElement("Vitae");
					IstEl.setAttributeNode(doc.createAttribute("name"));
					IstEl.setAttribute("name", "История жизни");
					IstEl.appendChild(doc.createTextNode("Vitae"));
					Mainelem.appendChild(IstEl);}
				if (razv.isSelected()){
					Element RazvEl = doc.createElement("Razv");
					RazvEl.setAttributeNode(doc.createAttribute("name"));
					RazvEl.setAttribute("name", "Развитие");
					RazvEl.appendChild(doc.createTextNode("Razv"));
					Mainelem.appendChild(RazvEl);}
				if (usl.isSelected()){
					Element UslEl = doc.createElement("Uslov");
					UslEl.setAttributeNode(doc.createAttribute("name"));
					UslEl.setAttribute("name", "Усл.проживания");
					UslEl.appendChild(doc.createTextNode("Uslov"));
					Mainelem.appendChild(UslEl);}
				if (perz.isSelected()){
					Element PerzEl = doc.createElement("PerZab");
					PerzEl.setAttributeNode(doc.createAttribute("name"));
					PerzEl.setAttribute("name", "Пер.заболевания");
					PerzEl.appendChild(doc.createTextNode("PerZab"));
					Mainelem.appendChild(PerzEl);}
				if (per_op.isSelected()){
					Element PeropEl = doc.createElement("PerOp");
					PeropEl.setAttributeNode(doc.createAttribute("name"));
					PeropEl.setAttribute("name", "Пер.операции");
					PeropEl.appendChild(doc.createTextNode("PerOp"));
					Mainelem.appendChild(PeropEl);}
				if (gem.isSelected()){
					Element GemEl = doc.createElement("GemTrans");
					GemEl.setAttributeNode(doc.createAttribute("name"));
					GemEl.setAttribute("name", "Гемотрансфузия");
					GemEl.appendChild(doc.createTextNode("GemTrans"));
					Mainelem.appendChild(GemEl);}
				if (nasl.isSelected()){
					Element NAsEl = doc.createElement("Nasl");
					NAsEl.setAttributeNode(doc.createAttribute("name"));
					NAsEl.setAttribute("name", "Наследственность");
					NAsEl.appendChild(doc.createTextNode("Nasl"));
					Mainelem.appendChild(NAsEl);}
				if (gyn.isSelected()){
					Element NAsEl = doc.createElement("GinAnamn");
					NAsEl.setAttributeNode(doc.createAttribute("name"));
					NAsEl.setAttribute("name", "Гинек.анамнез");
					NAsEl.appendChild(doc.createTextNode("GinAnamn"));
					Mainelem.appendChild(NAsEl);}
				if (farm.isSelected()){
					Element FarmEl = doc.createElement("FarmAnamn");
					FarmEl.setAttributeNode(doc.createAttribute("name"));
					FarmEl.setAttribute("name", "Фарм.анамнез");
					FarmEl.appendChild(doc.createTextNode("FarmAnamn"));
					Mainelem.appendChild(FarmEl);}
				if (lek.isSelected()){
					Element LekEl = doc.createElement("LekSr");
					LekEl.setAttributeNode(doc.createAttribute("name"));
					LekEl.setAttribute("name", "Прием лек.средств");
					LekEl.appendChild(doc.createTextNode("LekSr"));
					Mainelem.appendChild(LekEl);}
				if (gorm.isSelected()){
					Element GormEl = doc.createElement("GormonPrep");
					GormEl.setAttributeNode(doc.createAttribute("name"));
					GormEl.setAttribute("name", "Прием гормон.препаратов");
					GormEl.appendChild(doc.createTextNode("GormonPrep"));
					Mainelem.appendChild(GormEl);}
				if (st.isSelected()){
					Element StPEl = doc.createElement("StPraesense");
					StPEl.setAttributeNode(doc.createAttribute("name"));
					StPEl.setAttribute("name", "Status Praesense");
					StPEl.appendChild(doc.createTextNode("StPraesense"));
					Mainelem.appendChild(StPEl);}
				if (ob.isSelected()){
					Element ObEl = doc.createElement("ObSost");
					ObEl.setAttributeNode(doc.createAttribute("name"));
					ObEl.setAttribute("name", "Общ.состояние");
					ObEl.appendChild(doc.createTextNode("ObSost"));
					Mainelem.appendChild(ObEl);}
				if (koj.isSelected()){
					Element KojEl = doc.createElement("KojPokr");
					KojEl.setAttributeNode(doc.createAttribute("name"));
					KojEl.setAttribute("name", "Кож.покровы");
					KojEl.appendChild(doc.createTextNode("KojPokr"));
					Mainelem.appendChild(KojEl);}
				if (sl.isSelected()){
					Element SlEl = doc.createElement("Sliz");
					SlEl.setAttributeNode(doc.createAttribute("name"));
					SlEl.setAttribute("name", "Видим.слизистые");
					SlEl.appendChild(doc.createTextNode("Sliz"));
					Mainelem.appendChild(SlEl);}
				if (kl.isSelected()){
					Element KlEl = doc.createElement("PodkKl");
					KlEl.setAttributeNode(doc.createAttribute("name"));
					KlEl.setAttribute("name", "Подк.клетчатка");
					KlEl.appendChild(doc.createTextNode("PodkKl"));
					Mainelem.appendChild(KlEl);}
				if (lim.isSelected()){
					Element LimEl = doc.createElement("Limf");
					LimEl.setAttributeNode(doc.createAttribute("name"));
					LimEl.setAttribute("name", "Лимф.узлы");
					LimEl.appendChild(doc.createTextNode("Limf"));
					Mainelem.appendChild(LimEl);}
				if (kost.isSelected()){
					Element KosEl = doc.createElement("Kost");
					KosEl.setAttributeNode(doc.createAttribute("name"));
					KosEl.setAttribute("name", "Костно-мышечная система");
					KosEl.appendChild(doc.createTextNode("Kost"));
					Mainelem.appendChild(KosEl);}
				if (nerv.isSelected()){
					Element NervEl = doc.createElement("NervPs");
					NervEl.setAttributeNode(doc.createAttribute("name"));
					NervEl.setAttribute("name", "Нервно-психич. статус");
					NervEl.appendChild(doc.createTextNode("NervPs"));
					Mainelem.appendChild(NervEl);}
				if (chss.isSelected()){
					Element CHSSEl = doc.createElement("Chss");
					CHSSEl.setAttributeNode(doc.createAttribute("name"));
					CHSSEl.setAttribute("name", "ЧСС");
					CHSSEl.appendChild(doc.createTextNode("Chss"));
					Mainelem.appendChild(CHSSEl);}
				if (art.isSelected()){
					Element AdEl = doc.createElement("Ad");
					AdEl.setAttributeNode(doc.createAttribute("name"));
					AdEl.setAttribute("name", "АД");
					AdEl.appendChild(doc.createTextNode("Ad"));
					Mainelem.appendChild(AdEl);}
				if (rost.isSelected()){
					Element RostEl = doc.createElement("Rost");
					RostEl.setAttributeNode(doc.createAttribute("name"));
					RostEl.setAttribute("name", "Рост");
					RostEl.appendChild(doc.createTextNode("Rost"));
					Mainelem.appendChild(RostEl);}	
				if (ves.isSelected()){
					Element VesEl = doc.createElement("Ves");
					VesEl.setAttributeNode(doc.createAttribute("name"));
					VesEl.setAttribute("name", "Вес");
					VesEl.appendChild(doc.createTextNode("Ves"));
					Mainelem.appendChild(VesEl);}
				if (telo.isSelected()){
					Element TeloEl = doc.createElement("Telosl");
					TeloEl.setAttributeNode(doc.createAttribute("name"));
					TeloEl.setAttribute("name", "Телосложение");
					TeloEl.appendChild(doc.createTextNode("Telosl"));
					Mainelem.appendChild(TeloEl);}
				if (fiz.isSelected()){
					Element FizEl = doc.createElement("FizObsl");
					FizEl.setAttributeNode(doc.createAttribute("name"));
					FizEl.setAttribute("name", "Физик.обследование");
					FizEl.appendChild(doc.createTextNode("FizObsl"));
					Mainelem.appendChild(FizEl);}
				if (sust.isSelected()){
					Element SustEl = doc.createElement("Sust");
					SustEl.setAttributeNode(doc.createAttribute("name"));
					SustEl.setAttribute("name", "Суставы");
					SustEl.appendChild(doc.createTextNode("Sust"));
					Mainelem.appendChild(SustEl);}
				if (dyh.isSelected()){
					Element DyhEl = doc.createElement("Dyh");
					DyhEl.setAttributeNode(doc.createAttribute("name"));
					DyhEl.setAttribute("name", "Дыхание");
					DyhEl.appendChild(doc.createTextNode("Dyh"));
					Mainelem.appendChild(DyhEl);}
				if (gr.isSelected()){
					Element GrEl = doc.createElement("GrKl");
					GrEl.setAttributeNode(doc.createAttribute("name"));
					GrEl.setAttribute("name", "Гр.клетка");
					GrEl.appendChild(doc.createTextNode("GrKl"));
					Mainelem.appendChild(GrEl);}
				if (perl.isSelected()){
					Element PerlEl = doc.createElement("PerkL");
					PerlEl.setAttributeNode(doc.createAttribute("name"));
					PerlEl.setAttribute("name", "Перкуссия легких");
					PerlEl.appendChild(doc.createTextNode("PerkL"));
					Mainelem.appendChild(PerlEl);}
				if (ausl.isSelected()){
					Element AuslEl = doc.createElement("AusL");
					AuslEl.setAttributeNode(doc.createAttribute("name"));
					AuslEl.setAttribute("name", "Аускультация легких");
					AuslEl.appendChild(doc.createTextNode("AusL"));
					Mainelem.appendChild(AuslEl);}
				if (bronh.isSelected()){
					Element BronhEl = doc.createElement("Bronho");
					BronhEl.setAttributeNode(doc.createAttribute("name"));
					BronhEl.setAttribute("name", "Бронхофония");
					BronhEl.appendChild(doc.createTextNode("Bronho"));
					Mainelem.appendChild(BronhEl);}
				if (arter.isSelected()){
					Element BronhEl = doc.createElement("Arter");
					BronhEl.setAttributeNode(doc.createAttribute("name"));
					BronhEl.setAttribute("name", "Артерии и шейные вены");
					BronhEl.appendChild(doc.createTextNode("Arter"));
					Mainelem.appendChild(BronhEl);}
				if (obls.isSelected()){
					Element OblSEl = doc.createElement("OblS");
					OblSEl.setAttributeNode(doc.createAttribute("name"));
					OblSEl.setAttribute("name", "Область сердца");
					OblSEl.appendChild(doc.createTextNode("OblS"));
					Mainelem.appendChild(OblSEl);}
				if (pers.isSelected()){
					Element PerSEl = doc.createElement("PerkS");
					PerSEl.setAttributeNode(doc.createAttribute("name"));
					PerSEl.setAttribute("name", "Перкуссия сердца");
					PerSEl.appendChild(doc.createTextNode("PerkS"));
					Mainelem.appendChild(PerSEl);}
				if (auss.isSelected()){
					Element AusSEl = doc.createElement("AuskS");
					AusSEl.setAttributeNode(doc.createAttribute("name"));
					AusSEl.setAttribute("name", "Аускультация сердца");
					AusSEl.appendChild(doc.createTextNode("AuskS"));
					Mainelem.appendChild(AusSEl);}
				if (polr.isSelected()){
					Element PolrEl = doc.createElement("PolRta");
					PolrEl.setAttributeNode(doc.createAttribute("name"));
					PolrEl.setAttribute("name", "Полость рта");
					PolrEl.appendChild(doc.createTextNode("PolRta"));
					Mainelem.appendChild(PolrEl);}
				if (jiv.isSelected()){
					Element JivEl = doc.createElement("Jivot");
					JivEl.setAttributeNode(doc.createAttribute("name"));
					JivEl.setAttribute("name", "Живот");
					JivEl.appendChild(doc.createTextNode("Jivot"));
					Mainelem.appendChild(JivEl);}
				if (palj.isSelected()){
					Element PaljEl = doc.createElement("PalpJ");
					PaljEl.setAttributeNode(doc.createAttribute("name"));
					PaljEl.setAttribute("name", "Пальпация живота");
					PaljEl.appendChild(doc.createTextNode("PalpJ"));
					Mainelem.appendChild(PaljEl);}
				if (jkt.isSelected()){
					Element JktEl = doc.createElement("JKT");
					JktEl.setAttributeNode(doc.createAttribute("name"));
					JktEl.setAttribute("name", "Пальп.,перк. И ауск. жел.-кишечного тракта");
					JktEl.appendChild(doc.createTextNode("JKT"));
					Mainelem.appendChild(JktEl);}
				if (palpj.isSelected()){
					Element PalpjEl = doc.createElement("PalpJel");
					PalpjEl.setAttributeNode(doc.createAttribute("name"));
					PalpjEl.setAttribute("name", "Пальп.желудка");
					PalpjEl.appendChild(doc.createTextNode("PalpJel"));
					Mainelem.appendChild(PalpjEl);}
				if (palpod.isSelected()){
					Element PalpodEl = doc.createElement("PalpPodjel");
					PalpodEl.setAttributeNode(doc.createAttribute("name"));
					PalpodEl.setAttribute("name", "Пальп.поджел.жлезы");
					PalpodEl.appendChild(doc.createTextNode("PalpPodjel"));
					Mainelem.appendChild(PalpodEl);}
				if (pech.isSelected()){
					Element PechEl = doc.createElement("Pechen");
					PechEl.setAttributeNode(doc.createAttribute("name"));
					PechEl.setAttribute("name", "Печень");
					PechEl.appendChild(doc.createTextNode("Pechen"));
					Mainelem.appendChild(PechEl);}
				if (jelch.isSelected()){
					Element JechEl = doc.createElement("JelchP");
					JechEl.setAttributeNode(doc.createAttribute("name"));
					JechEl.setAttribute("name", "Желч.пузырь");
					JechEl.appendChild(doc.createTextNode("JelchP"));
					Mainelem.appendChild(JechEl);}
				if (selez.isSelected()){
					Element SelezEl = doc.createElement("Selez");
					SelezEl.setAttributeNode(doc.createAttribute("name"));
					SelezEl.setAttribute("name", "Селезенка");
					SelezEl.appendChild(doc.createTextNode("Selez"));
					Mainelem.appendChild(SelezEl);}
				if (oblz.isSelected()){
					Element OblzEl = doc.createElement("OblZad");
					OblzEl.setAttributeNode(doc.createAttribute("name"));
					OblzEl.setAttribute("name", "Оюл.заднего прохода");
					OblzEl.appendChild(doc.createTextNode("OblZad"));
					Mainelem.appendChild(OblzEl);}
				if (oblp.isSelected()){
					Element OblPEl = doc.createElement("Poyasn");
					OblPEl.setAttributeNode(doc.createAttribute("name"));
					OblPEl.setAttribute("name", "Поясн.область");
					OblPEl.appendChild(doc.createTextNode("Poyasn"));
					Mainelem.appendChild(OblPEl);}
				if (pochk.isSelected()){
					Element PochkEl = doc.createElement("Pochki");
					PochkEl.setAttributeNode(doc.createAttribute("name"));
					PochkEl.setAttribute("name", "Почки");
					PochkEl.appendChild(doc.createTextNode("Pochki"));
					Mainelem.appendChild(PochkEl);}
				if (moch.isSelected()){
					Element MochEl = doc.createElement("MochP");
					MochEl.setAttributeNode(doc.createAttribute("name"));
					MochEl.setAttribute("name", "Мочев.пузырь");
					MochEl.appendChild(doc.createTextNode("MochP"));
					Mainelem.appendChild(MochEl);}
				if (jelm.isSelected()){
					Element JelmEl = doc.createElement("MolJel");
					JelmEl.setAttributeNode(doc.createAttribute("name"));
					JelmEl.setAttribute("name", "Молочные железы");
					JelmEl.appendChild(doc.createTextNode("MolJel"));
					Mainelem.appendChild(JelmEl);}
				if (jelgr.isSelected()){
					Element JelgrEl = doc.createElement("GrJel");
					JelgrEl.setAttributeNode(doc.createAttribute("name"));
					JelgrEl.setAttribute("name", "Грудные железы мужчин");
					JelgrEl.appendChild(doc.createTextNode("GrJel"));
					Mainelem.appendChild(JelgrEl);}
				if (matka.isSelected()){
					Element MatEl = doc.createElement("Matka");
					MatEl.setAttributeNode(doc.createAttribute("name"));
					MatEl.setAttribute("name", "Матка и ее придатки");
					MatEl.appendChild(doc.createTextNode("Matka"));
					Mainelem.appendChild(MatEl);}
				if (polorg.isSelected()){
					Element PolEl = doc.createElement("PolovOrg");
					PolEl.setAttributeNode(doc.createAttribute("name"));
					PolEl.setAttribute("name", "Наружные полов.органы у мужчин");
					PolEl.appendChild(doc.createTextNode("PolOrg"));
					Mainelem.appendChild(PolEl);}
				if (chit.isSelected()){
					Element ChitEl = doc.createElement("Chitov");
					ChitEl.setAttributeNode(doc.createAttribute("name"));
					ChitEl.setAttribute("name", "Щитов.железа");
					ChitEl.appendChild(doc.createTextNode("Chitov"));
					Mainelem.appendChild(ChitEl);}
				if (stloc.isSelected()){
					Element StLocEl = doc.createElement("Stloc");
					StLocEl.setAttributeNode(doc.createAttribute("name"));
					StLocEl.setAttribute("name", "Status localis");
					StLocEl.appendChild(doc.createTextNode("StLoc"));
					Mainelem.appendChild(StLocEl);}
				if (ocen.isSelected()){
					Element OcenEl = doc.createElement("Ocenka");
					OcenEl.setAttributeNode(doc.createAttribute("name"));
					OcenEl.setAttribute("name", "Оценка данных анамнеза");
					OcenEl.appendChild(doc.createTextNode("Ocenka"));
					Mainelem.appendChild(OcenEl);}
		 
				javax.xml.transform.Result result = new StreamResult("SettingsOsm.xml");
				Source source = new DOMSource(doc);
				Transformer xformer;
				try {
				xformer = TransformerFactory.newInstance().newTransformer();
				} catch (TransformerConfigurationException e) {
				e.printStackTrace();
				return;
				} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();
				return;
				}
				xformer.setOutputProperty(OutputKeys.INDENT, "yes");
				try {
				xformer.transform(source, result);
				} catch (TransformerException e) {
				e.printStackTrace();
				return;
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
		
		jal = new JCheckBox("Жалобы");
		
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
		GroupLayout gl_pjalob = new GroupLayout(pjalob);
		gl_pjalob.setHorizontalGroup(
			gl_pjalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pjalob.createSequentialGroup()
					.addGroup(gl_pjalob.createParallelGroup(Alignment.LEADING)
						.addComponent(jal_m)
						.addComponent(jal_d)
						.addComponent(jal))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_pjalob.createParallelGroup(Alignment.LEADING)
						.addComponent(jal_kr)
						.addComponent(jal_p)
						.addComponent(jal_en))
					.addContainerGap(34, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(jal)
						.addComponent(jal_kr))
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
					.addContainerGap(30, Short.MAX_VALUE))
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
}
