package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seng_project.Project;

public class GetFileNameTest {

	Project p;
	String pathName;
	String javaType;
	String[] pathNameArray = {pathName};
	File[] list;
	
	
	@Before
	public void setUp() throws Exception {
		p = new Project();
		pathName = AllTests.BASEDIR + "SENG-300-Assignment-1\\src\\test_classes";
		list = p.getDirectory(pathName);
	}

	@Test
	public void testThatTheThreeClassesInTest_ClassesAllAreInFileName() {
		String[] testFileName = p.getFileName(list);
		String[] result = {AllTests.BASEDIR + "SENG-300-Assignment-1\\src\\test_classes\\" + "ClassWithCommentsBeforeAndAfter.java", 
				AllTests.BASEDIR + "SENG-300-Assignment-1\\src\\test_classes\\" + "ClassWithDifferentDeclarationsAndReferences.java",
				AllTests.BASEDIR + "SENG-300-Assignment-1\\src\\test_classes\\" + "ClassWithoutComments.java"};
		assertArrayEquals(result, testFileName);
	}
	
	@Test
	public void testEmpty_Package() {
		pathName = AllTests.BASEDIR + "\\SENG-300-Assignment-1\\src\\empty_package";
		list = p.getDirectory(pathName);
		String[] testFileName = p.getFileName(list);
		String[] result = {};
		assertArrayEquals(result, testFileName);
	}

}
