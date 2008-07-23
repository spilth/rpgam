package com.rpgaudiomixer.model;

import junit.framework.TestCase;

public class PlaylistTest extends TestCase {

	Playlist automaticNone, automaticList, automaticSong, manualNone, manualList, manualSong, randomNone, randomList, randomSong;
	Alias song1, song2, song3;
	
	protected void setUp() {
		song1 = new Alias("1.mp3");
		song2 = new Alias("2.mp3");
		song3 = new Alias("3.mp3");

		automaticNone = new Playlist("automatic-none", Playlist.MODE_AUTOMATIC, Playlist.LOOP_NONE);
		automaticList = new Playlist("automatic-list", Playlist.MODE_AUTOMATIC, Playlist.LOOP_LIST);
		automaticSong = new Playlist("automatic-song", Playlist.MODE_AUTOMATIC, Playlist.LOOP_SONG);

		manualNone = new Playlist("manual-none", Playlist.MODE_MANUAL, Playlist.LOOP_NONE);
		manualList = new Playlist("manual-list", Playlist.MODE_MANUAL, Playlist.LOOP_LIST);
		manualSong = new Playlist("manual-song", Playlist.MODE_MANUAL, Playlist.LOOP_SONG);
		
		randomNone = new Playlist("random-none", Playlist.MODE_RANDOM, Playlist.LOOP_NONE);
		randomList = new Playlist("random-list", Playlist.MODE_RANDOM, Playlist.LOOP_LIST);
		randomSong = new Playlist("random-song", Playlist.MODE_RANDOM, Playlist.LOOP_SONG);		

		automaticNone.add(song1);
		automaticNone.add(song2);
		automaticNone.add(song3);
		automaticList.add(song1);
		automaticList.add(song2);
		automaticList.add(song3);
		automaticSong.add(song1);
		automaticSong.add(song2);
		automaticSong.add(song3);

		manualNone.add(song1);
		manualNone.add(song2);
		manualNone.add(song3);
		manualList.add(song1);
		manualList.add(song2);
		manualList.add(song3);
		manualSong.add(song1);
		manualSong.add(song2);
		manualSong.add(song3);

		randomNone.add(song1);
		randomNone.add(song2);
		randomNone.add(song3);
		randomList.add(song1);
		randomList.add(song2);
		randomList.add(song3);
		randomSong.add(song1);
		randomSong.add(song2);
		randomSong.add(song3);
		
	}
	
	public void testDefaultModes() {
		Playlist p = new Playlist("Foo");
		assertEquals(Playlist.MODE_AUTOMATIC, p.getListMode());
		assertEquals(Playlist.LOOP_NONE, p.getLoopMode());
	}
	
	public void testGetNext() {
		assertSame(song2, automaticNone.getNext(song1));
		assertSame(song1, automaticList.getNext(song3));
		assertSame(song1, automaticSong.getNext(song1));

		assertSame(null, manualNone.getNext(song1));
		assertSame(null, manualList.getNext(song1));
		assertSame(song1, manualSong.getNext(song1));

		assertNotSame(null, randomNone.getNext(song1));
		assertNotSame(null, randomList.getNext(song1));
		assertSame(song1, randomSong.getNext(song1));
	}
	
}
