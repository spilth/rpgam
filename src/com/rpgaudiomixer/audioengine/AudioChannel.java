package com.rpgaudiomixer.audioengine;

import java.io.File;
import javax.swing.event.EventListenerList;

/**
 * AudioChannel is an abstract class representing the functionality
 * expected of an audio library for RPG Audio Mixer.
 * This allows us the flexibility to use other audio implementations
 * without having to alter any code within the main application.
 * Only a wrapper class, wrapping the audio library and extending
 * AudioChannel, needs to be created.
 * The base class provides some common functionality for adding
 * and removing listeners for audio channel events.
 * 
 * Each AudioChannel should provide a unique Player.
 * 
 * @author delegreg
 *
 */

public abstract class AudioChannel {
	
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * @param audioEngineListener The AudioEngineListener
	 * that wants to be added.
	 */
	public final void addAudioChannelListener(
			final AudioChannelListener listener) {
		listenerList.add(AudioChannelListener.class, listener);
	}

	/**
	 * @param audioEngineListener The AudioEngineListener
	 * that wants to be removed.
	 */
	public final void removeAudioChannelListener(
			final AudioChannelListener listener) {
		listenerList.remove(AudioChannelListener.class, listener);
	}
	
	/**
	 * This method should return the length of the given audio file
	 * in seconds.
	 * @param file The audio file to be measured.
	 * @return The length of the file in seconds.
	 */
	public abstract int getDuration(File file);
	
	/**
	 * This method should return the current playing alias
	 * @return The alias string.
	 */
	public abstract String getCurrentAlias();

	/**
	 * This method should return the current playing File
	 * @return The File object.
	 */
	public abstract File getCurrentFile();

	/**
	 * Play the specified song in the Song Player.
	 * 
	 * @param file The file you'd like to play.
	 * 
	 * @return Returns true if the song was able to load and play.
	 * False otherwise
	 */
	public abstract boolean play(File file);

	/**
	 * Method to initialize the audio engine.
	 * Each implementation will probably require it's
	 * own specific initialization.
	 */
	public abstract void init();

	/**
	 * Stop the audio engine.
	 * Each implementation will probably require it's
	 * own specific stop method.
	 */
	public abstract void stop();

	/**
	 * Get the progress of the currently playing song.
	 * 
	 * @return The number of seconds that have played so far.
	 */
	public abstract int getProgress();


	/**
	 * @return The current volume of the song player. 0 to 100.
	 */
	public abstract int getVolume();

	/**
	 * Set the desired volume for the song player.
	 * 
	 * @param volumeLevel Should be between 0 and 100 with 100 being max volume.
	 */
	public abstract void setVolume(int volumeLevel);


}
