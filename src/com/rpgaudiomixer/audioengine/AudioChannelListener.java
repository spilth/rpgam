package com.rpgaudiomixer.audioengine;

import java.util.EventListener;

/**
 * AudioEngineListener is an interface that a class must implement
 * if it would like to listen to the events sent by a class
 * extending the AudioEngine class.
 * 
 * @author Brian Kelly
 *
 */

public interface AudioChannelListener extends EventListener {

	/**
	 * Fired when the current song has finished playing.
	 */
	void songFinished(AudioChannel source);
	
	
	/**
	 * Fired on progress
	 */
	void progress(int secondsPlayed,AudioChannel source);
	
}
