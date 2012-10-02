package ru.nkz.ivcgzo.shablonImporter;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.nkz.ivcgzo.dbConnection.DBParams;

public class Configuration {
	public DBParams srcBaseParams;
	public DBParams dstBaseParams;
	
	public Configuration(String configFilePath) throws SAXException, IOException, ParserConfigurationException, SAXException {
		parseConfigFile(configFilePath);
	}
	
	private void parseConfigFile(String configFilePath) throws SAXException, IOException, ParserConfigurationException, SAXException {
		Document confDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(configFilePath);
		
		Element rootElem = confDoc.getDocumentElement();
		
		srcBaseParams = new DBParams(getBaseElementAndCheckParamsNodeCount(rootElem, "srcBase"));
		dstBaseParams = new DBParams(getBaseElementAndCheckParamsNodeCount(rootElem, "dstBase"));
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
