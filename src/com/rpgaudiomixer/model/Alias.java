package com.rpgaudiomixer.model;

/**
 * An Alias is a pointer to a file on the file system.
 * It has its own name, separate from the filename.
 * 
 * @author Brian Kelly
 *
 */

public final class Alias {
	
	/** The name of the Alias. */
	private String name;

	/** The path the Alias points to. */
	private String path;

	/**
	 * Aliases can't be created without at least a path,
	 * so the parameterless constructor is hidden.
	 */
	@SuppressWarnings("unused")
	private Alias() { }

	/**
	 * Method to create an Alias with just a path.
	 * The path is also used as the name.
	 * 
	 * @param aliasPath Path of the file you want to point to.
	 */
	public Alias(final String aliasPath) {
		this(aliasPath, aliasPath);
	}

	/**
	 * Method to create an Alias with a specific name and path.
	 * 
	 * @param aliasName The name to use.
	 * @param aliasPath The path to point to.
	 */
	public Alias(final String aliasName, final String aliasPath) {
		setName(aliasName);
		setPath(aliasPath);
	}

	/**
	 * Set the name of the Alias.
	 * 
	 * @param newName The new name.
	 */
	public void setName(final String newName) {
		this.name = newName;
	}

	/**
	 * Get the name of the Alias.
	 * 
	 * @return The name of the Alias as a String.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the path of the Alias.
	 * 
	 * @param newPath The new path to use.
	 */
	public void setPath(final String newPath) {
		this.path = newPath;
	}

	/**
	 * Gets the path of the Alias.
	 * 
	 * @return The file path of the Alias as a String.
	 */
	public String getPath() {
		return this.path;
	}


	/**
	 * Provide a print-friendly representation of the Alias.
	 * 
	 * @return The Alias as a String.
	 */
	public String toString() {
		return this.name;
	}
}
