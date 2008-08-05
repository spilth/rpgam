package com.rpgaudiomixer.model;

/**
 * An interface for objects that can have Aliases added to it
 * or removed from it.
 * 
 * @author Brian Kelly
 *
 */

public interface AliasCollector {

	/**
	 * Method to add an Alias to a collector.
	 * 
	 * @param alias The alias to be added
	 */
	void add(Alias alias);

	/**
	 * Method to remove an Alias from a collector.
	 * 
	 * @param alias The alias to be removed
	 */
	void remove(Alias alias);
}
