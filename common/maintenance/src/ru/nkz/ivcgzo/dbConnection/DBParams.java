package ru.nkz.ivcgzo.dbConnection;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class DBParams {
	public final DBConnectionTypes type;
	public final String host;
	public final Integer port;
	public final String schema;
	public final String name;
	public final String login;
	public final String password;
	
	public DBParams(Element databaseElement) throws SAXException {
		if (!databaseElement.hasAttribute("type"))
			type = DBConnectionTypes.postgre;
		else {
			try {
				type = DBConnectionTypes.valueOf(databaseElement.getAttribute("type"));
			} catch (IllegalArgumentException e) {
				throw new SAXException(String.format("Illegal database type. Allowed types are %s. Default: postgre.", (Object[]) DBConnectionTypes.values()));
			}
		}
		if (databaseElement.hasAttribute("host"))
			host = databaseElement.getAttribute("host");
		else
			host = null;
		if (databaseElement.hasAttribute("port"))
			port = Integer.parseInt(databaseElement.getAttribute("port"));
		else
			port = null;
		
		if (type == DBConnectionTypes.oracle)
			if (databaseElement.hasAttribute("schema"))
				schema = databaseElement.getAttribute("schema");
			else
				throw new SAXException("Attribute 'schema' must be specified for oracle database.");
		else
			schema = "public";
			
		name = databaseElement.getAttribute("name");
		login = databaseElement.getAttribute("login");
		password = databaseElement.getAttribute("password");
	}
}
