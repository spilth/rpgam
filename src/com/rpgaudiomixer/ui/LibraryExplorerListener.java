package com.rpgaudiomixer.ui;

import java.util.EventListener;

import com.rpgaudiomixer.model.IResource;

public interface LibraryExplorerListener extends EventListener {
	public void resourceSelected(IResource selectedResource);
	public void playlistOpened(IResource openedPlaylist);
	public void paletteOpened(IResource openedPalette);
	public void moveResource(IResource selectedResource,
			IResource targetResource);
	public void dropFiles(String[] paths,
			IResource targetResource);
}
