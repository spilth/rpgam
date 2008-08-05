package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Palette represents a collection of Aliases used for playing sound effects.
 * 
 * @author Brian Kelly
 */

public final class Palette implements IResource, AliasCollector {

	/**
	 * The name of the Palette.
	 */
	private String name;
	
	/**
	 * The parent IResource of the Palette.
	 */
	private IResource parent = null;

	/**
	 * The list of Aliases that the Palette contains.
	 */
	private List<Alias> effects = new ArrayList<Alias>();
	
	/**
	 * Default constructor for a Palette.  Gives it the name "Default Palette".
	 */
	public Palette() {
		this("Default Palette");
	}

	/**
	 * Constructor for creating a Palette with a specific name.
	 * 
	 * @param paletteName Name to give to Palette.
	 */
	public Palette(final String paletteName) {
		this.name = paletteName;
	}

	/**
	 * Returns the Aliases assigned to the Palette.
	 * 
	 * @return List of IResources that make up this Palette.
	 */
	public List<IResource> getItems() {
		return null;
	}

	/**
	 * @return The name of the Palette.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to give a Palette a new name.
	 * 
	 * @param newName The new name.
	 */
	public void setName(final String newName) {
		this.name = newName;
	}

	/**
	 * @return The parent IResource of this Palette.
	 */
	public IResource getParent() {
		return parent;
	}

	/**
	 * Method to set a new parent for the Palette.
	 * 
	 * @param newParent The new parent.
	 */
	public void setParent(final IResource newParent) {
		this.parent = newParent;
	}

	/**
	 * @return A List of Aliases assigned to the Palette.
	 */
	public List<Alias> getEffects() {
		return effects;
	}

	/**
	 * Method to add an Alias to the Palette.
	 * 
	 * @param alias Alias to add.
	 */
	public void add(final Alias alias) {
		effects.add(alias);
	}

	/**
	 * Method to remove an Alias form the Palette.
	 * 
	 * @param alias Alias to remove.
	 */
	public void remove(final Alias alias) {
		effects.remove(alias);
	}

}
