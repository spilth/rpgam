package com.rpgaudiomixer.model;

/**
 * Library is the main document type in RPG Audio Mixer.
 * It contains Folders, Playlists and Palettes.
 * 
 * @author brian
 *
 */

public final class Library {
	/**
	 * The root Folder that contains all children folders,
	 * playlists and palettes.
	 */
	private final Folder root;
	
	/**
	 * This is the default Constructor.
	 */
	public Library() {
		root = new Folder("Root");

		Folder f = new Folder("Library");
		Playlist p = new Playlist("Default Playlist");
		Palette x = new Palette("Default Palette");
		
		root.addItem(f);
		f.addItem(p);
		f.addItem(x);
	}
	
	/**
	 * 
	 * @return The root Folder of the Library
	 */
	public IResource getResources() {
		return root;
	}

}
