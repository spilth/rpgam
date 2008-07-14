package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements IResource, AliasCollector {
	public static final int MODE_AUTOMATIC = 0;
	public static final int MODE_MANUAL = 1;
	public static final int MODE_RANDOM = 2;

	public static final int LOOP_NONE = 0;
	public static final int LOOP_LIST = 1;
	public static final int LOOP_SONG = 2;

	private String name;
	private List<Alias> songs = new ArrayList<Alias>();
	private IResource parent = null;

	private int listMode = MODE_AUTOMATIC;	
	private int loopMode = LOOP_NONE;
	
	public Playlist() {
		this("Default Playlist");
	}
	
	public Playlist(String name) {
		this(name, MODE_AUTOMATIC, LOOP_NONE);
	}
	
	public Playlist(String name, int listMode, int loopMode) {
		this.name = name;
		this.listMode = listMode;
		this.loopMode = loopMode;
	}
	
	public List<IResource> getItems() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IResource getParent() {
		return parent;
	}

	public void setParent(IResource parent) {
		this.parent = parent;
	}

	public List<Alias> getSongs() {
		return songs;
	}

	public void add(Alias a) {
		songs.add(a);
	}

	public void setLoopMode(int i) {
		this.loopMode = i;		
	}

	public int getLoopMode() {
		return loopMode;
	}
	
	public void setListMode(int i) {
		this.listMode = i;
	}

	public int getListMode() {
		return listMode;
	}
	
	public int indexOf(Alias a) {
		return this.songs.indexOf(a);
	}
	
	public int size() {
		return this.songs.size();
	}
	
	public Alias getNext(Alias currentAlias) {	
		if (this.getLoopMode() == LOOP_SONG) {
			return currentAlias;
		}
		
		if (this.getListMode() == MODE_RANDOM) {
			return getRandomEntry();
		}

		if (this.getListMode() == MODE_AUTOMATIC) {
			// Return next song
			// If at last song

			int nextIndex = indexOf(currentAlias) + 1;
			
			if (nextIndex >= size()) {
				if (this.getLoopMode() == LOOP_LIST) {
					return getFirst();
	
				} else {
					return null;
	
				}
			} else {
				// return next song
				return songs.get(nextIndex);
			}
		}
		
		if (this.getListMode() == MODE_MANUAL) {
			return null;
		}

		return null;
	}

	private Alias getRandomEntry() {
		int randomIndex = (int) (Math.random() * size());
		return songs.get(randomIndex);
	}
	
	public Alias getFirst() {
		if (this.getListMode() == MODE_RANDOM) {
			return getRandomEntry();
		}
		
		if (getListMode() == MODE_AUTOMATIC) {
			if (songs.size() > 0) {
				return (Alias) songs.get(0);
			}
		}
		
		return null;
	}

	public void moveUp(Alias c) {
		int i = songs.indexOf(c);
		if (i > 0) {
			songs.remove(i);
			songs.add(i - 1, c);
		}
	}
	
	public void moveDown(Alias a) {
		int i = songs.indexOf(a);
		if (i + 1 < songs.size()) {	
			songs.remove(i);
			songs.add(i + 1, a);			
		}
	}

	public void remove(Alias a) {
		songs.remove(a);
	}
}
