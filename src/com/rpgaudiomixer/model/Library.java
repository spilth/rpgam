package com.rpgaudiomixer.model;

import com.rpgaudiomixer.resources.LocalisedRessourcesManager;
import com.rpgaudiomixer.resources.LocalisedRessourcesManager.RessourceKeys;

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
		
		Folder f = new Folder(LocalisedRessourcesManager.getInstance().getString(RessourceKeys.DefaultFolderName));
		Playlist p = new Playlist(LocalisedRessourcesManager.getInstance().getString(RessourceKeys.DefaultPlaylistName));
		Palette x = new Palette(LocalisedRessourcesManager.getInstance().getString(RessourceKeys.DefaultPaletteName));
		
		root.addItem(f);
		root.addItem(p);
		root.addItem(x);
	}
		
	public Folder getRoot() {
		return root;
	}

}
