package com.rpgaudiomixer.model;

import java.util.List;

public class BaseResource implements IResource {
	protected String name;
	protected IResource parent = null;
	protected Library library = null;
		
	public List<IResource> getChildren() {
		return null;
	}

	public String getName() {
		return this.name;
	}

	public IResource getParent() {
		return this.parent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(IResource parent) {
		this.parent = parent;
	}

}
