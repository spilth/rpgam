package com.rpgaudiomixer.model;

import java.util.List;

/** 
 * IResource is an interface that represents a
 * type of object that can be added to a Library.
 * A resource can optionally have other resources as children.
 * 
 * @author Brian Kelly
 *
 */

public interface IResource {
	
	/** 
	 * Retrieves the name of the resource.
	 * 
	 * @return A String representing the name of the resource
	 */
	String getName();
	
	/**
	 * Sets the name of the resource.
	 * 
	 * @param name The new name of the resource as a String
	 */
	void setName(String name);

	/**
	 * Get the children items of this resource, if any.

	 * @return A List of IResources or null
	 */
	List<IResource> getChildren();

	/**
	 * Sets the parents resource of this resource.
	 * 
	 * @param parent The IResource that should be this resource's parent
	 */
	void setParent(IResource parent);

	/**
	 * Gets the parents resource of this resource.
	 * Returns null if there is no parent.
	 * 
	 * @return An IResource representing the parent of this resoruce
	 */
	IResource getParent();

}
