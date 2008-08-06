package com.rpgaudiomixer.audioengine;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * JavaZoomAudioEngine is an implementation of the AudioEngine
 * class specific to the JavaZoom audio library.
 * 
 * @author Brian Kelly
 *
 */

public final class JavaZoomAudioEngine
		extends AudioEngine
		implements BasicPlayerListener {

	private BasicPlayer songPlayer, previewPlayer, effectPlayer;
	
	public JavaZoomAudioEngine() {
		songPlayer = new BasicPlayer();
		previewPlayer = new BasicPlayer();
		effectPlayer = new BasicPlayer();
		
		songPlayer.addBasicPlayerListener(this);
	}
	
	@Override
	public int getFileDuration(File f) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPreviewProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSongProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSongVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean playSong(File f) {	
		System.out.println(f.getAbsolutePath());
		try {
			songPlayer.stop();
			previewPlayer.stop();

			songPlayer.open(f);
			songPlayer.play();
				
			return true;
			
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}

		return false;
	}

	@Override
	public boolean previewSong(File f) {
		try {
			songPlayer.stop();
			previewPlayer.stop();
			
			previewPlayer.open(f);
			previewPlayer.play();

			return true;
			
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public void setSongVolume(int volumeLevel) {
		// TODO Auto-generated method stub

	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		stopSong();
	}

	@Override
	public void stopSong() {
		// TODO Auto-generated method stub
		try {
			previewPlayer.stop();
			songPlayer.stop();

		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// Basic Player Listener Implementation
	public void opened(Object arg0, Map properties) {
		System.out.println("FILE OPENED");
		System.out.println(properties);
	}

	public void progress(int bytesRead, 
			long microsecondsElapsed,
			byte[] pcmSamples,
			Map properties) {
		//System.out.println(bytesRead);
	}

	public void setController(BasicController bc) {
		// TODO Auto-generated method stub
		
	}

	public void stateUpdated(BasicPlayerEvent bpe) {
		if (bpe.getCode() == BasicPlayerEvent.EOM) {
			Object[] listeners = listenerList.getListenerList();
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
		         if (listeners[i] == AudioEngineListener.class) {
		             // Lazily create the event:
		             ((AudioEngineListener) listeners[i + 1]).songFinished();
		         }
		     }

		}
		
	}

	@Override
	public boolean playEffect(File f) {
		try {
			effectPlayer.stop();
			effectPlayer.open(f);
			effectPlayer.play();

			return true;
			
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}

		return false;
	}

}
