package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seng_project.Project;

public class DecCountTest {

	Project p;
	String pathName;
	String javaType;
	String[] pathNameArray;
	File[] list;
	String[] sourceCode;
	String[] fileName;
	
	
	@Before
	public void setUp() throws Exception {
		p = new Project();
		pathName = AllTests.BASEDIR + "Seng-project-Group1\\src\\test_classes";
		javaType = "";
		list = p.getDirectory(pathName);
		sourceCode = p.getSourceCode(list);
		fileName = p.getFileName(list);
	}


	@Test
	public void testClassWithoutComments() {
		javaType = "test_classes.ClassWithoutComments";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.decCount);
	}

	// This test case should be able to consider the cases where there and comments before and after the class declcaration
	@Test
	public void testClassWithCommentsBeforeAndAfter() {
		javaType = "test_classes.ClassWithCommentsBeforeAndAfter";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.decCount);
	}
	
	@Test
	public void testForExactlyOneIntegerDeclaration() {
		javaType = "int";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.decCount);
	}
	@Test
	public void testForExactlyOneStringArrayDeclaration() {
		javaType = "String[]";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.decCount);
	}
	@Test
	public void testForNonexistentDeclaration() {
		javaType = "foo";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 0;
		assertEquals(result, p.decCount);
	}
	@Test
	public void testForBooleanDeclaration() {
		javaType = "boolean";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 0;
		assertEquals(result, p.decCount);
	}
	
}
