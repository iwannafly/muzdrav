package ru.nkz.ivcgzo.classifierImporter;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class TableParams {
	public final String srcName;
	public final String dstName;
	
	public TableParams(Element tableElement) throws SAXException {
		if (tableElement.hasAttribute("srcName"))
			srcName = tableElement.getAttribute("srcName");
		else
			throw new SAXException("Attribute 'srcName' not specified for table.");
		if (tableElement.hasAttribute("dstName"))
			dstName = tableElement.getAttribute("dstName");
		else
			dstName = srcName;
	}
}
