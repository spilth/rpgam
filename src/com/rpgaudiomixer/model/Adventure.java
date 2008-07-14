package com.rpgaudiomixer.model;

public class Adventure {
	private Folder root;
	
	public Adventure() {
		root = new Folder("Root");

		Folder f = new Folder("Adventure");
		Playlist p = new Playlist("Main Playlist");
		Palette x = new Palette("Main Palette");
		
		root.addItem(f);
		f.addItem(p);
		f.addItem(x);
	}
	
	public IResource getResources() {
		return root;
	}

}
