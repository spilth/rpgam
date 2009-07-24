package com.rpgaudiomixer.util;
import java.io.File;

import com.rpgaudiomixer.util.FilenameExtensionFilter;

import junit.framework.TestCase;

public class FilenameExtensionFilterTest extends TestCase {

	private static final File DIR = new File(".");
	
	public void testSingleExtension() {
		assertTrue(new FilenameExtensionFilter("bmp").accept(DIR, "foo.bmp"));
	}
	
	public void testMultipleExtensions() {
		FilenameExtensionFilter filter = new FilenameExtensionFilter(new String[] {"bmp", "jpg"});
		assertTrue(filter.accept(DIR, "foo.bmp"));
		assertTrue(filter.accept(DIR, "foo.jpg"));
	}

	public void testCaseSensitiveExtension() {
		assertTrue(new FilenameExtensionFilter("BMP").accept(DIR, "foo.bmp"));
		assertTrue(new FilenameExtensionFilter("bmp").accept(DIR, "foo.BMP"));
	}

}
