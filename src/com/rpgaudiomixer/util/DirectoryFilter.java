package com.rpgaudiomixer.util;

import java.io.File;
import java.io.FileFilter;

/**
 * A simple implementation of FileFilter
 * that only accepts Directories.
 * 
 * @author Brian Kelly
 *
 */

public final class DirectoryFilter implements FileFilter {
	/**
	 * Accept the file if it's a directory.
	 * 
	 * @param file The file to inspect.
	 * 
	 * @return True if the file is a directory.  False if not.
	 */
	public boolean accept(final File file) {
		return file.isDirectory();
	}
}
