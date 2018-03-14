package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import seng_project.Project;

public class RefCountTest {

	Project p;
	String pathName;
	String javaType;
	String[] pathNameArray = {pathName};
	File[] list;
	String[] sourceCode;
	String[] fileName;
	
	
	@Before
	public void setUp() throws Exception {
		p = new Project();
		pathName = AllTests.BASEDIR + "SENG-300-Assignment-1\\src\\test_classes";
		javaType = "";
		list = p.getDirectory(pathName);
		sourceCode = p.getSourceCode(list);
		fileName = p.getFileName(list);
	}
	
	@Test
	public void testForIntegerReferences() {
		javaType = "int";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.refCount);
	}

	@Test
	public void testForCharReferences() {
		javaType = "char";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.refCount);
	}
	
	@Test
	public void testForBooleanReferences() {
		javaType = "boolean";
		for(int i = 0; i < fileName.length; i++){
			p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
		}
		int result = 1;
		assertEquals(result, p.refCount);
	}
}
