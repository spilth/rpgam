package com.rpgaudiomixer.model;

/**
 * An interface for objects that can have Aliases added to it
 * or removed from it.
 * 
 * @author Brian Kelly
 *
 */

public interface AliasCollector {
	void add(Alias alias);
	void remove(Alias alias);
}
