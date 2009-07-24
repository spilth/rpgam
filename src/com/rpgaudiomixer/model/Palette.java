package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Palette represents a collection of Aliases used for playing sound effects.
 * 
 * @author Brian Kelly
 */

public final class Palette extends BaseResource implements AliasCollector {

	private List<Alias> effects = new ArrayList<Alias>();
	
	public Palette() {
		this("Default Palette");
	}

	public Palette(final String paletteName) {
		this.name = paletteName;
	}

	public List<Alias> getEffects() {
		return effects;
	}

	public void add(final Alias alias) {
		effects.add(alias);
	}

	public void remove(final Alias alias) {
		effects.remove(alias);
	}

}
