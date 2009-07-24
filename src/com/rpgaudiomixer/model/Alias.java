package com.rpgaudiomixer.model;

/**
 * An Alias is a pointer to a file on the file system.
 * It has its own name, separate from the filename.
 * 
 * @author Brian Kelly
 *
 */

public final class Alias {
	
	private String name;
	private String filePath;

	public Alias(final String aliasPath) {
		this(aliasPath, aliasPath);
	}

	public Alias(final String aliasName, final String aliasPath) {
		setName(aliasName);
		setPath(aliasPath);
	}

	public void setName(final String newName) {
		this.name = newName;
	}

	public String getName() {
		return this.name;
	}

	public void setPath(final String newPath) {
		this.filePath = newPath;
	}

	public String getPath() {
		return this.filePath;
	}

	public String toString() {
		return this.name;
	}
}
