package ru.nkz.ivcgzo.classifierImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.nkz.ivcgzo.dbConnection.DBConnectionTypes;
import ru.nkz.ivcgzo.dbConnection.DBParams;

public class Configuration {
	public DBParams srcBaseParams;
	public DBParams dstBaseParams;
	public List<TableParams> tableParams;
	
	public Configuration(String configFilePath) throws SAXException, IOException, ParserConfigurationException, SAXException {
		parseConfigFile(configFilePath);
		
		if (dstBaseParams.type != DBConnectionTypes.postgre)
			throw new SAXException("Destination database type must be postgre.");
	}
	
	private void parseConfigFile(String configFilePath) throws SAXException, IOException, ParserConfigurationException, SAXException {
		Document confDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(configFilePath);
		
		Element rootElem = confDoc.getDocumentElement();
		
		Element srcBaseElem = getBaseElementAndCheckParamsNodeCount(rootElem, "srcBase");
		srcBaseParams = new DBParams(srcBaseElem);
		Element dstBaseElem = getBaseElementAndCheckParamsNodeCount(rootElem, "dstBase");
		dstBaseParams = new DBParams(dstBaseElem);
		
		tableParams = new ArrayList<>();
		NodeList tableList = rootElem.getElementsByTagName("tableList");
		for (int i = 0; i < tableList.getLength(); i++) {
			Element tableListElement = (Element) tableList.item(i);
			NodeList tableParamList = tableListElement.getElementsByTagName("table");
			for (int j = 0; j < tableParamList.getLength(); j++) {
				tableParams.add(new TableParams((Element) tableParamList.item(j)));
			}
			
		}
	}
	
	private Element getBaseElementAndCheckParamsNodeCount(Element rootElement, String nodeName) throws SAXException {
		NodeList lst = rootElement.getElementsByTagName(nodeName);
		if (lst.getLength() == 0)
			throw new SAXException(String.format("Node '%s' not specified.", nodeName));
		else if (lst.getLength() > 1)
			throw new SAXException(String.format("Node '%s' specified more than once.", nodeName));
		else
			return (Element) lst.item(0);
	}
	
}
