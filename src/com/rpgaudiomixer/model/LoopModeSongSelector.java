package com.rpgaudiomixer.model;

public class LoopModeSongSelector implements SongSelector {

	public Alias getNext(Alias currentAlias) {
		return currentAlias;
	}

}
