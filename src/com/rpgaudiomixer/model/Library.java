package com.rpgaudiomixer.model;

/**
 * Library is the main document type in RPG Audio Mixer.
 * It contains Folders, Playlists and Palettes.
 * 
 * @author Brian Kelly
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
		
		Folder f = new Folder("Default Folder");
		Playlist p = new Playlist("Default Playlist");
		Palette x = new Palette("Default Palette");
		
		root.addItem(f);
		root.addItem(p);
		root.addItem(x);
	}
		
	public Folder getRoot() {
		return root;
	}

}
