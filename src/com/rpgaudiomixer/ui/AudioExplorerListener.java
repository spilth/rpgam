package com.rpgaudiomixer.ui;

import java.io.File;
import java.util.EventListener;

public interface AudioExplorerListener extends EventListener {
	public void fileDoubleClicked(File f);
}
