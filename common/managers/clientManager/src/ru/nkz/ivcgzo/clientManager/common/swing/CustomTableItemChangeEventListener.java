package ru.nkz.ivcgzo.clientManager.common.swing;

import java.util.EventListener;

import org.apache.thrift.TBase;

public interface CustomTableItemChangeEventListener<T extends TBase<?, ?>> extends EventListener {
	boolean doAction(CustomTableItemChangeEvent<T> event);
}
