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
 * @author Brian Kelly
 *
 */

public abstract class AudioEngine {	
	public final static int SECONDS_PER_MINUTE = 60;
	
	protected EventListenerList listenerList = new EventListenerList();

	// Listeners
	public final void addAudioEngineListener(final AudioEngineListener ael) {
		listenerList.add(AudioEngineListener.class, ael);
	}

	public final void removeAudioEngineListener(final AudioEngineListener ael) {
		listenerList.remove(AudioEngineListener.class, ael);
	}
	
	public abstract int getFileDuration(File f);
	
	public abstract boolean playSong(File f);
	public abstract boolean previewSong(File f);
	public abstract boolean playEffect(File f);
	public abstract void stopSong();
	public abstract void init();
	public abstract void stop();

	public abstract int getSongProgress();
	public abstract int getPreviewProgress();
	
	public abstract int getSongVolume();
	public abstract void setSongVolume(int volumeLevel);

	// SUPPORT
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
