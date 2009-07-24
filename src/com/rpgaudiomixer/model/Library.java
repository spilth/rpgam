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
	private final Folder root = new Folder("Root");
	
	public Library() {
		initialize();
	}

	private void initialize() {
		LocalisedRessourcesManager.getInstance();
		root.addItem(new Folder(LocalisedRessourcesManager.getString(RessourceKeys.DefaultFolderName)));
		root.addItem(new Playlist(LocalisedRessourcesManager.getString(RessourceKeys.DefaultPlaylistName)));
		root.addItem(new Palette(LocalisedRessourcesManager.getString(RessourceKeys.DefaultPaletteName)));
	}
		
	public Folder getRoot() {
		return root;
	}

}
