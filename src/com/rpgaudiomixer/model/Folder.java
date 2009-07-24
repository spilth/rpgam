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

	private List<IResource> childResources = new ArrayList<IResource>();
	
	public Folder() {
		this("Default Folder");
	}

	public Folder(final String folderName) {
		setName(folderName);
	}
		
	/**
	 * Adds a child item to this folder.
	 * 
	 * @param resource The resource to add. Must implement IResource.
	 */
	public void addItem(final IResource resource) {
		childResources.add(resource);
		resource.setParent(this);
	}

	/**
	 * Removes the specified resource from the Folder.
	 * 
	 * @param resource The resoure to remove.  Must implement IResource.
	 */
	public void removeItem(final IResource resource) {
		childResources.remove(resource);
		resource.setParent(null);
	}
	
	/**
	 * @return A List of this folder's IResources.
	 */
	public List<IResource> getChildren() {
		return childResources;
	}

}
