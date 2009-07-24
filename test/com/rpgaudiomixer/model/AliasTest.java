package com.rpgaudiomixer.model;

import junit.framework.TestCase;

public class AliasTest extends TestCase {

	public void testSingleParameterConstructor() {
		Alias a = new Alias("build.xml");
		assertEquals(a.getName(), a.getPath());
	}
}
