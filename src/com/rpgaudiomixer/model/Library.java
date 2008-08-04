package com.rpgaudiomixer.model;

public class Library {
	private Folder root;
	
	public Library() {
		root = new Folder("Root");

		Folder f = new Folder("Library");
		Playlist p = new Playlist("Default Playlist");
		Palette x = new Palette("Default Palette");
		
		root.addItem(f);
		f.addItem(p);
		f.addItem(x);
	}
	
	public IResource getResources() {
		return root;
	}

}
