package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Folder is a resource that can contain other resources.
 * 
 * @author Brian Kelly
 *
 */

public final class Folder extends BaseResource {

	/**
	 * The folder's child items.
	 */
	private List<IResource> children = new ArrayList<IResource>();
	
	/**
	 * Default constructor.  Creates a folder with the name "Default Folder".
	 */
	public Folder() {
		this("Default Folder");
	}

	/** 
	 * Creates a folder with a specified name.
	 * 
	 * @param folderName Name to give the Folder.
	 */
	public Folder(final String folderName) {
		setName(folderName);
	}
		
	/**
	 * Adds a child item to this folder.
	 * 
	 * @param resource The resource to add. Must implement IResource.
	 */
	public void addItem(final IResource resource) {
		children.add(resource);
		resource.setParent(this);
	}

	/**
	 * Removes the specified resource from the Folder.
	 * 
	 * @param resource The resoure to remove.  Must implement IResource.
	 */
	public void removeItem(final IResource resource) {
		children.remove(resource);
		resource.setParent(null);
	}
	
	/**
	 * @return A List of this folder's IResources.
	 */
	public List<IResource> getChildren() {
		return children;
	}

}
