package com.rpgaudiomixer.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Iterator;

public class FilenameExtensionFilter implements FilenameFilter {
	HashSet<String> extensions = new HashSet<String>();
	
	public FilenameExtensionFilter() {}

	public FilenameExtensionFilter(String ext) {
		this();
		this.addExtension(ext);
	}
	
	public FilenameExtensionFilter(String[] exts) {
		// Loops through array and add extensions
		for (int i = 0; i < exts.length; i++) {
			this.addExtension(exts[i]);
		}
	}
	
	public void addExtension(String ext) {
		extensions.add(ext);
	}
	
	public void removeExtension(String ext) {
		extensions.remove(ext);
	}
	
	// FilenameFilter Implementation
	public boolean accept(File f, String s) {
		if (extensions.isEmpty()) return false;

		for (Iterator<String> i = extensions.iterator(); i.hasNext();) {
			if (s.toLowerCase().endsWith( "." + (String)i.next() )) {
				return true;
			}
		}

		return false;
	}

}
