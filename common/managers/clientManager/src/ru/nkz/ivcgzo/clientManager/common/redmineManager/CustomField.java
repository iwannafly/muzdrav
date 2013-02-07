package ru.nkz.ivcgzo.clientManager.common.redmineManager;

public class CustomField<T> {
	private int id;
	private String name;
	private T value;
	
	private CustomField() {
		
	}
	
	protected CustomField(int id, String name) {
		this(id, name, null);
	}
	
	protected CustomField(int id, String name, T value) {
		this();
		
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
