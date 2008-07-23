package com.rpgaudiomixer.model;

public class Alias {
	private String name;
	private String path;

	// Hide parameterless constructor
	@SuppressWarnings("unused")
	private Alias() {}

	public Alias(String path) {
		this(path, path);
	}

	public Alias(String name, String path) {
		setName(name);
		setPath(path);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public String toString() {
		return this.name;
	}
}
