package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

public class Palette implements IResource, AliasCollector {
	public String name;
	public IResource parent = null;
	private List<Alias> effects = new ArrayList<Alias>();
	
	public Palette() {
		this("Default Palette");
	}
	
	public Palette(String name) {
		this.name = name;
	}
	
	public List<IResource> getItems() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IResource getParent() {
		return parent;
	}

	public void setParent(IResource parent) {
		this.parent = parent;
	}

	public List<Alias> getEffects() {
		return effects;
	}

	public void add(Alias a) {
		effects.add(a);
	}
	
	public void remove(Alias a) {
		effects.remove(a);
	}

}
