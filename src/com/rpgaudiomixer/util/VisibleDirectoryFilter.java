package com.rpgaudiomixer.util;

import java.io.File;
import java.io.FileFilter;

public final class VisibleDirectoryFilter implements FileFilter {

	public boolean accept(final File file) {
		return file.isDirectory() && !file.isHidden();
	}
}
