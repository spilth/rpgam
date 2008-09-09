package com.rpgaudiomixer.model;

import java.util.List;

public class BaseResource implements IResource {
	/**
	 * The name of the resource.
	 */
	protected String name;
	
	/**
	 * The parent resource of this resource.
	 */
	protected IResource parent = null;

	/**
	 * The library this resource is a part of.
	 */
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
