package com.rpgaudiomixer.audioengine;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

/**
 * JavaZoomAudioEngine is an implementation of the AudioEngine
 * class specific to the JavaZoom audio library.
 * 
 * @author Brian Kelly
 *
 */

public final class JavaZoomAudioEngine
		extends AudioEngine
		implements AudioChannelListener {

	private Hashtable<String,JavaZoomAudioChannel> channels;

	private String findPlayerKey(JavaZoomAudioChannel whoami){
		for (String test : channels.keySet()) {
			if (channels.get(test)==whoami){
				return test;
			}
		}; 
		return "";
	}

	private JavaZoomAudioChannel songChannel, previewChannel, effectChannel;
	
	public JavaZoomAudioEngine() {
		channels = new Hashtable<String, JavaZoomAudioChannel>();

		songChannel    = new JavaZoomAudioChannel();
		previewChannel = new JavaZoomAudioChannel();
		effectChannel  = new JavaZoomAudioChannel();

		channels.put("song", songChannel);
		channels.put("effect", effectChannel);
		channels.put("effect", effectChannel);
		
		songChannel.addAudioChannelListener(this);
		effectChannel.addAudioChannelListener(this);
		previewChannel.addAudioChannelListener(this);
	}
	
	@Override
	public int getFileDuration(File f) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPreviewProgress() {
		// TODO Auto-generated method stub
		return previewChannel.getProgress();
	}

	@Override
	public int getSongProgress() {
		// TODO Auto-generated method stub
		return songChannel.getProgress();
	}

	@Override
	public int getSongVolume() {
		// TODO Auto-generated method stub
		return songChannel.getVolume();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		for (String test : channels.keySet()) {
			channels.get(test).init();
		}; 
	}

	@Override
	public boolean playSong(File f) {	
		System.out.println(f.getAbsolutePath());

		songChannel.stop();
		previewChannel.stop();

		songChannel.play(f);
			
		return true;
			
	}

	@Override
	public boolean previewSong(File f) {
		System.out.println(f.getAbsolutePath());

		songChannel.stop();
		previewChannel.stop();

		previewChannel.play(f);
			
		return true;
	}

	@Override
	public void setSongVolume(int volumeLevel) {
		// TODO Auto-generated method stub
		songChannel.setVolume(volumeLevel);
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		for (String test : channels.keySet()) {
			channels.get(test).stop();
		}; 
	}

	@Override
	public void stopSong() {
		// TODO Auto-generated method stub
		previewChannel.stop();
		songChannel.stop();
	}

	// Basic Player Listener Implementation
	//TODO obsolete, see if we need this
	public void opened(Object arg0, Map properties) {
		System.out.println("FILE OPENED");
		System.out.println(properties);
	}

	@Override
	public boolean playEffect(File f, int channelNumber) {
		effectChannel.stop();
		effectChannel.play(f);
		return true;
	}

	public void progress(int secondsPlayed, AudioChannel source) {
		// TODO what to do with this method ?
		// answer : calculate ratio based on song length and propagate event
		
	}

	public void songFinished(AudioChannel source) {
		// propagate song finished event to top level
		
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
	         if (listeners[i] == AudioEngineListener.class) {
	             // Lazily create the event:
	             ((AudioEngineListener) listeners[i + 1]).songFinished( findPlayerKey((JavaZoomAudioChannel) source) );
	         }
	     }
	
	}

}
