package com.rpgaudiomixer.audioengine;

import java.io.File;
import javax.swing.event.EventListenerList;


public abstract class AudioEngine {	
	protected EventListenerList listenerList = new EventListenerList();

	// Listeners
	public void addAudioEngineListener(AudioEngineListener ael) {
		listenerList.add(AudioEngineListener.class, ael);
	}

	public void removeAudioEngineListener(AudioEngineListener ael) {
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
	public static String formatAliasLength(int length) {
		int minutes = length / 60;
		int seconds = length % 60;
		String s;
		if (seconds < 10) {
			s = "0" + seconds;
		} else {
			s = Integer.toString(seconds);
		}
		return Integer.toString(minutes) + ":" + s;
	}
}
