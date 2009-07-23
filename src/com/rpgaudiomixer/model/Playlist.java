package com.rpgaudiomixer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Playlist represents a collection of Aliases to be treated as
 * a list of Songs to play in a specified order.
 * 
 * @author Brian Kelly
 */

public final class Playlist extends BaseResource implements AliasCollector {
	
	SongSelector selector = new LoopModeSongSelector();
	
	/**
	 * A Playlist in Automatic List Mode always returns
	 * the next song in the list.
	 */
	public static final int MODE_AUTOMATIC = 0;

	/**
	 * A Playlist in Manual List Mode NEVER returns
	 * the next song in a list.
	 */
	public static final int MODE_MANUAL = 1;

	/**
	 * A Playlist in Random List Mode returns
	 * a random song instead of the next song.
	 */
	public static final int MODE_RANDOM = 2;

	/**
	 * A Playlist in None Loop Mode never loops.
	 */
	public static final int LOOP_NONE = 0;

	/**
	 * A Playlist in List Loop Mode will
	 * return to the first song in the list
	 * after the last song has played.
	 */
	public static final int LOOP_LIST = 1;

	/** 
	 * A Playlist in Song Loop Mode will
	 * return the same song after a song
	 * has finished playing.
	 */
	public static final int LOOP_SONG = 2;

	/**
	 * The songs that make up the Playlist.
	 */
	private List<Alias> songs = new ArrayList<Alias>();

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

	/**
	 * Get all the Aliases associated with this Playlist.
	 * 
	 * @return A List of Aliases.
	 */
	public List<Alias> getSongs() {
		return songs;
	}

	/**
	 * Add an Alias to the Playlist.
	 * 
	 * @param alias The Alias to add.
	 */
	public void add(final Alias alias) {
		songs.add(alias);
	}

	/**
	 * Remove an Alias from the Playlist.
	 * 
	 * @param alias The Alias to remove.
	 */
	public void remove(final Alias alias) {
		songs.remove(alias);
	}

	/**
	 * Set the Loop Mode for this Playlist.
	 * Should be LOOP_NONE, LOOP_PLAYLIST or LOOP_SONG.
	 * 
	 * @param newLoopMode The new loop mode to use.
	 */
	public void setLoopMode(final int newLoopMode) {
		this.loopMode = newLoopMode;		
	}

	/**
	 * @return The playlist's current loop mode.
	 */
	public int getLoopMode() {
		return loopMode;
	}

	/**
	 * Set the List Mode for this Playlist
	 * Should be MODE_AUTOMATIC, MODE_RANDOM or MODE_NONE.
	 * 
	 * @param newListMode The new list mode to use.
	 */
	public void setListMode(final int newListMode) {
		this.listMode = newListMode;
	}
	
	/**
	 * @return The current List Mode
	 */
	public int getListMode() {
		return listMode;
	}
	
	/**
	 * Determine the index of the specified Alias in this Playlist.
	 * 
	 * @param alias The Alias you are searching for.
	 * 
	 * @return The index of the Alias or ??? if not found.
	 */
	public int indexOf(final Alias alias) {
		return this.songs.indexOf(alias);
	}

	/**
	 * @return The number of songs in the Playlist
	 */
	public int size() {
		return this.songs.size();
	}
	
	/**
	 * Returns the next song after the provided currentAlias,
	 * based on the Playlist's List Mode and Loop Mode.
	 * 
	 * @param currentAlias The Alias that was playing.
	 * 
	 * @return The new Alias to play or null.
	 */
	public Alias getNext(final Alias currentAlias) {
		
		if (this.getLoopMode() == LOOP_SONG) {
			return selector.getNext(currentAlias);
		}
		
		if (this.getListMode() == MODE_RANDOM) {
			return getRandomEntry();
		}

		if (this.getListMode() == MODE_AUTOMATIC) {
			int nextIndex = indexOf(currentAlias) + 1;
			
			if (nextIndex >= size()) {
				// Return next song if at last song
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

	/**
	 * Used to get a random Alias from the Playlist.
	 * 
	 * @return A randomly chosen Alias.
	 */
	private Alias getRandomEntry() {
		int randomIndex = (int) (Math.random() * size());
		return songs.get(randomIndex);
	}

	/**
	 * Returns the "first" Alias in the Playlist, based on the List Mode.
	 * MODE_RANDOM returns a random Alias.
	 * MODE_AUTOMATIC returns the first Alias.
	 * MODE_NONE returns null.
	 * 
	 * @return An Alias.
	 */
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

	/**
	 * Method to move an Alias up one position in the Playlist.
	 * 
	 * @param alias The Alias you want to move up.
	 */
	public void moveUp(final Alias alias) {
		int i = songs.indexOf(alias);
		if (i > 0) {
			songs.remove(i);
			songs.add(i - 1, alias);
		}
	}

	/**
	 * Method to move an Alias down one position in the Playlist.
	 * 
	 * @param alias The Alias you want to move down.
	 */
	public void moveDown(final Alias alias) {
		int i = songs.indexOf(alias);
		if (i + 1 < songs.size()) {	
			songs.remove(i);
			songs.add(i + 1, alias);			
		}
	}

}
