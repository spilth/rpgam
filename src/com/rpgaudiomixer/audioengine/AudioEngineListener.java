package com.rpgaudiomixer.audioengine;

import java.util.EventListener;

public interface AudioEngineListener extends EventListener {

	abstract void songFinished();
	
}
