package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Playlist represents a collection of Aliases to be treated as
 * a list of Songs to play in a specified order.
 * 
 * @author Brian Kelly
 */

public final class Playlist implements IResource, AliasCollector {
	public static final int MODE_AUTOMATIC = 0;
	public static final int MODE_MANUAL = 1;
	public static final int MODE_RANDOM = 2;

	public static final int LOOP_NONE = 0;
	public static final int LOOP_LIST = 1;
	public static final int LOOP_SONG = 2;

	/**
	 * The name of the Playlist.
	 */
	private String name;
	
	/**
	 * The songs that make up the Playlist.
	 */
	private List<Alias> songs = new ArrayList<Alias>();

	/**
	 * The parent IResource this Playlist belongs to.
	 */
	private IResource parent = null;

	/**
	 * The default List Mode.
	 */
	private int listMode = MODE_AUTOMATIC;	

	/**
	 * The default Looping Mode.
	 */
	private int loopMode = LOOP_NONE;

	/**
	 * Default constructor creates a Playlist with the name "Default Playlist".
	 */
	public Playlist() {
		this("Default Playlist");
	}

	/**
	 * Constructor to create a Playlist with a specified name.
	 * Default to MODE_AUTOMATIC and LOOP_NONE.
	 * 
	 * @param playlistName The desired name for the Playlist.
	 */
	public Playlist(final String playlistName) {
		this(playlistName, MODE_AUTOMATIC, LOOP_NONE);
	}
	
	/**
	 * Constructor to create a Playlist with a specific name,
	 * List Mode and Loop Mode.
	 * 
	 * @param playlistName The desired name for the Playlist.
	 * 
	 * @param list The desired List Mode for the Playlist.
	 * MODE_AUTOMATIC, MODE_RANDOM or MODE_NONE.
	 * 
	 * @param loop The desired Loop Mode for the Playlist,
	 * LOOP_NONE, LOOP_PLAYLIST or LOOP_SONG.
	 */
	
	public Playlist(final String playlistName, final int list, final int loop) {
		this.name = playlistName;
		this.listMode = list;
		this.loopMode = loop;
	}
	
	public List<IResource> getItems() {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(final String newName) {
		this.name = newName;
	}

	public IResource getParent() {
		return parent;
	}

	public void setParent(final IResource newParent) {
		this.parent = newParent;
	}

	public List<Alias> getSongs() {
		return songs;
	}

	public void add(final Alias alias) {
		songs.add(alias);
	}
	
	public void remove(final Alias alias) {
		songs.remove(alias);
	}

	public void setLoopMode(final int i) {
		this.loopMode = i;		
	}

	public int getLoopMode() {
		return loopMode;
	}
	
	public void setListMode(final int i) {
		this.listMode = i;
	}

	public int getListMode() {
		return listMode;
	}
	
	public int indexOf(final Alias alias) {
		return this.songs.indexOf(alias);
	}
	
	public int size() {
		return this.songs.size();
	}
	
	/**
	 * Returns the next song after the provided currentAlias,
	 * based on the Playlist's List Mode and Loop Mode.
	 * 
	 * @param currentAlias The Alias that was playing.
	 * @return The new Alias to play or null.
	 */
	public Alias getNext(final Alias currentAlias) {	
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

	public void moveUp(final Alias alias) {
		int i = songs.indexOf(alias);
		if (i > 0) {
			songs.remove(i);
			songs.add(i - 1, alias);
		}
	}
	
	public void moveDown(final Alias alias) {
		int i = songs.indexOf(alias);
		if (i + 1 < songs.size()) {	
			songs.remove(i);
			songs.add(i + 1, alias);			
		}
	}

}
