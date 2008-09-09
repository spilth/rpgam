package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Palette represents a collection of Aliases used for playing sound effects.
 * 
 * @author Brian Kelly
 */

public final class Palette extends BaseResource implements AliasCollector {

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
