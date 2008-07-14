package com.rpgaudiomixer.model;

import java.util.List;

public interface IResource {
	public String getName();
	public void setName(String name);

	public List<IResource> getItems();

	public void setParent(IResource parent);
	public IResource getParent();
}
