/**
 * 
 */
package com.rpgaudiomixer.audioengine;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

/**
 * @author delegreg
 *
 */
public class JavaZoomAudioChannel extends AudioChannel implements BasicPlayerListener {

	private long microsecondsPlayed;
	private long lastEventSecond;
	private long bytesPlayed;
	private String currentAlias;
	private File currentFile;
	private BasicPlayer player;

	private String findEventKey(BasicPlayerEvent whoami){
		int code=whoami.getCode();
		
		if (code==BasicPlayerEvent.EOM){return "EOM";};
		if (code==BasicPlayerEvent.GAIN){return "GAIN";};
		if (code==BasicPlayerEvent.OPENED){return "OPENED";};
		if (code==BasicPlayerEvent.OPENING){return "OPENING";};
		if (code==BasicPlayerEvent.PAN){return "PAN";};
		if (code==BasicPlayerEvent.PAUSED){return "PAUSED";};
		if (code==BasicPlayerEvent.PLAYING){return "PLAYING";};
		if (code==BasicPlayerEvent.RESUMED){return "RESUMED";};
		if (code==BasicPlayerEvent.SEEKED){return "SEEKED";};
		if (code==BasicPlayerEvent.SEEKING){return "SEEKING";};
		if (code==BasicPlayerEvent.STOPPED){return "STOPPED";};
		if (code==BasicPlayerEvent.UNKNOWN){return "UNKNOWN";};

		return "";
	}
	
	
	
	/**
	 * Creates a BasicPlayer and register its events
	 * @author delegreg 
	 */
	public JavaZoomAudioChannel() {
		// TODO Auto-generated constructor stub
		player=new BasicPlayer();
		player.addBasicPlayerListener(this);
		init();
	}

	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#getDuration(java.io.File)
	 */
	@Override
	public int getDuration(File file) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#getProgress()
	 */
	@Override
	public int getProgress() {
		// TODO Auto-generated method stub
		return (int) (microsecondsPlayed/1000000);
	}

	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#getVolume()
	 */
	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return (int) player.getGainValue();
	}

	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		stop();
		resetProgress();
		currentAlias="";
		currentFile=null;
	}

	private void resetProgress() {
		microsecondsPlayed=0;
		bytesPlayed=0;
		lastEventSecond=0;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#play(java.io.File)
	 */
	@Override
	public boolean play(File file) {
		try {
			player.stop();
			player.open(file);
			player.play();
			
			currentFile=file;
			
			return true;
			
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#setVolume(int)
	 */
	@Override
	public void setVolume(int volumeLevel) {
		try {
			player.setGain(volumeLevel);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.rpgaudiomixer.audioengine.AudioChannel#stop()
	 */
	@Override
	public void stop() {
		try {
			player.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resetProgress();
	}

	public void opened(Object arg0, Map arg1) {
		// TODO Auto-generated method stub
		
	}

	public void progress(int bytesRead, long microsecondsElapsed, byte[] pcmSamples, Map properties) 
	{
		
		microsecondsPlayed=microsecondsElapsed;
		bytesPlayed=bytesRead;
		//watchdog added to generate progress events every second or so...
		if (microsecondsPlayed>(lastEventSecond+1)*1000000) {
			
			Object[] listeners = listenerList.getListenerList();
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
		         if (listeners[i] == AudioChannelListener.class) {
		             // Lazily create the event:
		             ((AudioChannelListener) listeners[i + 1]).progress((int) (microsecondsPlayed/1000),this );
		         }
		     }
			
			lastEventSecond=microsecondsPlayed/1000000;
			System.out.println( currentFile.toString() + "progress " + bytesRead/1024 + "Ko " + microsecondsElapsed/1000000 + "s " );
		}

		

	}


	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub
		
	}

	public void stateUpdated(BasicPlayerEvent bpe) {
		System.out.println( "stateUpdated " + findEventKey(bpe) + " " + bpe.toString());
		if (bpe.getCode() == BasicPlayerEvent.EOM) {
			Object[] listeners = listenerList.getListenerList();
			for (int i = listeners.length - 2; i >= 0; i -= 2) {
		         if (listeners[i] == AudioChannelListener.class) {
		             // Lazily create the event:
		             ((AudioChannelListener) listeners[i + 1]).songFinished(this);
		         }
		     }

		}
		
	}



	@Override
	public String getCurrentAlias() {
		// TODO Auto-generated method stub
		return currentAlias;
	}



	@Override
	public File getCurrentFile() {
		// TODO Auto-generated method stub
		return currentFile;
	}

}
