package ru.nkz.ivcgzo.clientStaticInputVrachInfo;

import java.lang.reflect.InvocationTargetException;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfoAdmin.ThriftServerStaticInputVrachInfoAdmin;

public class ThriftClient extends ThriftServerStaticInputVrachInfoAdmin.Client {
	private TTransport thrTrans;
	private Object callbackObj;
	
	public static ThriftClient create(Object callbackObj) {
		ThriftClient client = new ThriftClient(new TFramedTransport(new TSocket("localhost", configuration.thrPort)), callbackObj);
		
		return client;
	}

	private ThriftClient(TTransport thrTrans, Object callbackObj) {
		super(new TBinaryProtocol(thrTrans));
		
		this.thrTrans = thrTrans;
		this.callbackObj = callbackObj;
	}
	
	public void connect() throws TTransportException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		thrTrans.open();
		
		callbackObj.getClass().getMethod("onConnect").invoke(callbackObj);
	}
	
	public void disconnect() {
		thrTrans.close();
	}
}
