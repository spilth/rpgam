package com.rpgaudiomixer.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A class that filers filenames based on their extension.
 * 
 * @author Brian Kelly
 *
 */

public final class FilenameExtensionFilter implements FilenameFilter {
	/** The list of acceptable extensions. */
	private HashSet<String> extensions = new HashSet<String>();

	/** Default constructor. */
	public FilenameExtensionFilter() { }
	
	/**
	 * Create a filter with a single extension.
	 * 
	 * @param ext The extension to use.  Should NOT include the leading dot.
	 * "mp3", not "*.mp3" or ".mp3".
	 *  
	 */

	public FilenameExtensionFilter(final String ext) {
		this();
		this.addExtension(ext);
	}

	/**
	 * Creates a filter from an array of extensions.
	 * 
	 * @param extensionArray An array of extensions.
	 */
	public FilenameExtensionFilter(final String[] extensionArray) {
		// Loops through array and add extensions
		for (int i = 0; i < extensionArray.length; i++) {
			this.addExtension(extensionArray[i]);
		}
	}

	/** 
	 * @param extension The extension to add.
	 */
	public void addExtension(final String extension) {
		extensions.add(extension);
	}
	
	/**
	 * @param extension The extension to remove.
	 */
	public void removeExtension(final String extension) {
		extensions.remove(extension);
	}
	
	/**
	 * FilenameFilter implementation.
	 * Accepts a file if it ends with any of the extensions
	 * that this filter contains.
	 * 
	 * @param file The file to test.
	 * @param string I'm not sure... ???
	 * 
	 * @return True if the file ends with one of the supplied extensions.
	 */
	public boolean accept(final File file, final String string) {
		if (extensions.isEmpty()) {
			return false;
		}

		for (Iterator<String> i = extensions.iterator(); i.hasNext();) {
			if (string.toLowerCase().endsWith("." + (String) i.next())) {
				return !(new File(string)).isHidden();
			}
		}

		return false;
	}

}
