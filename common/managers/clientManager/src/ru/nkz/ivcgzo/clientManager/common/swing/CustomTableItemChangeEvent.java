package ru.nkz.ivcgzo.clientManager.common.swing;

import java.util.EventObject;

import org.apache.thrift.TBase;

public class CustomTableItemChangeEvent<T extends TBase<?, ?>> extends EventObject {
	private static final long serialVersionUID = 4119364497654100564L;
	private T item;

	public CustomTableItemChangeEvent(Object source, T item) {
		super(source);
		
		this.item = item;
	}
	
	public T getItem() {
		return item;
	}
}

