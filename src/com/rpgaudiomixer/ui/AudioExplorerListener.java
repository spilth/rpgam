package com.rpgaudiomixer.ui;

import java.io.File;
import java.util.EventListener;

import org.eclipse.jface.viewers.SelectionChangedEvent;

public interface AudioExplorerListener extends EventListener {
	public void fileDoubleClicked(File f);
	public void directorySelectionChanged(SelectionChangedEvent event);
	public void fileSelectionChanged(SelectionChangedEvent event);
}
