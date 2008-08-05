package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Folder is a resource that can contain other resources.
 * 
 * @author Brian Kelly
 *
 */

public final class Folder implements IResource {

	/**
	 * The name of the folder.
	 */
	private String name;

	/**
	 * The folders child items.
	 */
	private List<IResource> items = new ArrayList<IResource>();

	/**
	 * The parents resource of this folder.
	 */
	private IResource parent = null;

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
	 * Method to change the name of a folder.
	 * 
	 * @param newName The new name to give the folder.
	 */
	public void setName(final String newName) {
		this.name = newName;
	}

	/**
	 * Method to return the name of a folder.
	 * 
	 * @return The name of the folder.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a child item to this folder.
	 * 
	 * @param resource The resource to add. Must implement IResource.
	 */
	public void addItem(final IResource resource) {
		items.add(resource);
		resource.setParent(this);
	}

	/**
	 * Removes the specified resource from the Folder.
	 * 
	 * @param resource The resoure to remove.  Must implement IResource.
	 */
	public void removeItem(final IResource resource) {
		items.remove(resource);
	}
	
	/**
	 * @return A List of this folder's IResources.
	 */
	public List<IResource> getItems() {
		return items;
	}

	/**
	 * @return The parent IResource for this Folder.
	 */
	public IResource getParent() {
		return parent;
	}

	/**
	 * @param newParent The new parent for this Folder.
	 */
	public void setParent(final IResource newParent) {
		this.parent = newParent;
	}
	
}
