package ru.nkz.ivcgzo.libsMd5Updater;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class LibsMd5Params {
	public final String libPath;
	public final String pluginPath;
	
	public LibsMd5Params(Element tableElement) throws SAXException {
		if (tableElement.hasAttribute("libs"))
			libPath = tableElement.getAttribute("libs");
		else
			throw new SAXException("Attribute 'libs' not specified for path.");
		if (tableElement.hasAttribute("plugins"))
			pluginPath = tableElement.getAttribute("plugins");
		else
			throw new SAXException("Attribute 'plugins' not specified for path.");
	}
}
