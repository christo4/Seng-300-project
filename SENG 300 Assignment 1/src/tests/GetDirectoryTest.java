package tests;

import seng_project.Project;
import java.io.File;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetDirectoryTest {

	Project p;
	String pathName;
	String javaType;
	String[] pathNameArray = {pathName};
	
	@Before
	public void setUp() throws Exception {
		p = new Project();
		pathName = AllTests.BASEDIR + "\\SENG 300 Assignment 1\\src\\test_classes";
		javaType = "";
		
	}


	@Test
	public void testGetDirectoryWithValidDirectory() {
		File[] list = p.getDirectory(pathName);
		assertNotNull(list);
	}
	
	@Test public void testGetDirectoryWithInvalidDirectory() {
		pathName = "Invalid Path";
		File[] list = p.getDirectory(pathName);
		assertNull(list);
	}

}
