package com.rpgaudiomixer.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Iterator;

public final class FilenameExtensionFilter implements FilenameFilter {

	private HashSet<String> acceptableExtensions = new HashSet<String>();
	
	public FilenameExtensionFilter(final String ext) {
		this.addExtension(ext);
	}

	public FilenameExtensionFilter(final String[] extensionArray) {
		for (int i = 0; i < extensionArray.length; i++) {
			this.addExtension(extensionArray[i]);
		}
	}

	public void addExtension(final String extension) {
		acceptableExtensions.add(extension.toLowerCase());
	}
	
	public boolean accept(final File dir, final String filename) {
		for (Iterator<String> i = acceptableExtensions.iterator(); i.hasNext();) {			
			String suffix = "." + (String) i.next();
			if (filename.toLowerCase().endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

}
