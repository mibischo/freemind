package tests.freemind;

import java.io.File;

import freemind.tools.OsHelper;

public class OsHelperTests extends FreeMindTestBase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testIsMacOSX() {
		System.setProperty("os.name", "Mac OS");
		assertTrue(OsHelper.isMacOsX());
	}
	
	public void testIsLinux() {
		System.setProperty("os.name", "Linux");
		assertTrue(OsHelper.isLinux());
	}
	
	public void testIsWindows() {
		System.setProperty("os.name", "Windows");
		assertTrue(OsHelper.isWindows());
	}
	
	public void testIsAbsolutePathWindows() {
		System.setProperty("file.separator", "\\");
		System.setProperty("os.name", "Windows");
		assertTrue(OsHelper.isAbsolutePath("C:\\asdf"));
	}
	
	public void testIsAbsolutePathLinux() {
		System.setProperty("file.separator", "/");
		System.setProperty("os.name", "Linux");
		assertTrue(OsHelper.isAbsolutePath("/tmp"));
	}
	
	public void testIsAbsolutePathMac() {
		System.setProperty("file.separator", "/");
		System.setProperty("os.name", "Mac OS");
		assertTrue(OsHelper.isAbsolutePath("/tmp"));
	}
}
