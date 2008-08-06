package com.rpgaudiomixer.audioengine;

import java.io.File;
import javax.swing.event.EventListenerList;

/**
 * AudioEngine is an abstract class representing the functionality
 * expected of an audio library for RPG Audio Mixer.
 * This allows us the flexibility to use other audio implementations
 * without having to alter any code within the main application.
 * Only a wrapper class, wrapping the audio library and extending
 * AudioEngine, needs to be created.
 * The base class provides some common functionality for adding
 * and removing listeners for audio engine events.
 * 
 * Each AudioEngine should provide a number of Players.
 * Song Player for playing songs from a Playlist.
 * Preview Player for playing songs from the Audio Explorer.
 * Effect Player for playing effects from a Palette.
 * 
 * @author Brian Kelly
 *
 */

public abstract class AudioEngine {
	public final static int SECONDS_PER_MINUTE = 60;
	
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * @param audioEngineListener The AudioEngineListener
	 * that wants to be added.
	 */
	public final void addAudioEngineListener(
			final AudioEngineListener audioEngineListener) {
		listenerList.add(AudioEngineListener.class, audioEngineListener);
	}

	/**
	 * @param audioEngineListener The AudioEngineListener
	 * that wants to be removed.
	 */
	public final void removeAudioEngineListener(
			final AudioEngineListener audioEngineListener) {
		listenerList.remove(AudioEngineListener.class, audioEngineListener);
	}
	
	/**
	 * This method should return the length of the given audio file
	 * in seconds.
	 * @param file The audio file to be measured.
	 * @return The length of the file in seconds.
	 */
	public abstract int getFileDuration(File file);
	
	/**
	 * Play the specified song in the Song Player.
	 * 
	 * @param file The file you'd like to play.
	 * 
	 * @return Returns true if the song was able to load and play.
	 * False otherwise
	 */
	public abstract boolean playSong(File file);

	/**
	 * Play the specified song in the Preview Player.
	 * 
	 * @param file The file you'd like to play.
	 * 
	 * @return Returns true if the song was able to load and play.
	 * False otherwise.
	 */
	public abstract boolean previewSong(File file);

	/**
	 * Play the specified file in the Effect Player.
	 * 
	 * @param file THe file to play.
	 * 
	 * @return Returns true if the effect was able to load and play.
	 * False otherwise.
	 */
	public abstract boolean playEffect(File file);
	
	/**
	 * Stop the Song Player.
	 */
	public abstract void stopSong();

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
	public abstract int getSongProgress();

	/**
	 * Get the progress of the currenlty playing preview song.
	 * 
	 * @return The number of seconds that have played so far.
	 */
	public abstract int getPreviewProgress();

	/**
	 * @return The current volume of the song player. 0 to 100.
	 */
	public abstract int getSongVolume();

	/**
	 * Set the desired volume for the song player.
	 * 
	 * @param volumeLevel Should be between 0 and 100 with 100 being max volume.
	 */
	public abstract void setSongVolume(int volumeLevel);

	/**
	 * Support function to format the length of an Alias as a string.
	 * 
	 * @param length The length of the alias in seconds.
	 * 
	 * @return The length of the alias as a pretty string.
	 */
	public final static String formatAliasLength(final int length) {
		int minutes = length / SECONDS_PER_MINUTE;
		int seconds = length % SECONDS_PER_MINUTE;
		String s;
		if (seconds < 10) {
			s = "0" + seconds;
		} else {
			s = Integer.toString(seconds);
		}
		return Integer.toString(minutes) + ":" + s;
	}
}
