package com.rpgaudiomixer.audioengine;

import java.util.EventListener;

public interface AudioEngineListener extends EventListener {

	public abstract void songFinished();
	
}
