package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;


public class Folder implements IResource {
	private String name;
	private List<IResource> items = new ArrayList<IResource>();
	private IResource parent = null;
	
	public Folder() {
		this("Default Folder");
	}
	
	public Folder(String name) {
		setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addItem(IResource resource) {
		items.add(resource);
		resource.setParent(this);
	}
	
	public void removeItem(IResource resource) {
		items.remove(resource);
	}
	
	public List<IResource> getItems() {
		return items;
	}

	public IResource getParent() {
		return parent;
	}

	public void setParent(IResource parent) {
		this.parent = parent;
	}
	
}
