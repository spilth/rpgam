package com.rpgaudiomixer.util;

import java.io.File;

import junit.framework.TestCase;

public class VisibleDirectoryFilterTest extends TestCase {
	public void testDirectory() {
		VisibleDirectoryFilter filter = new VisibleDirectoryFilter();
		assertTrue(filter.accept(new File("images")));
		assertFalse(filter.accept(new File("build.xml")));
	}
}
