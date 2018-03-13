package seng_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;


/*Main gets command line arguments and initializes them to pathname and javatype for later use. Main also calls the methods
 * getDirectory, getSourceCode and getFileName in order to do file io and get the source code and each class name. Finally
 * main initializes a parser passing in the source code(String array), file name(string array), javatype(string) and path name(String array).
 */
public class Project {
		public static int refCount = 0;
		public static int decCount = 0;
		public static void main(String[] args) {

			Project p = new Project();
			String pathName = "";
			String javaType = "";
			String[] pathNameArray = {pathName};
			
			if(args.length == 2) {
				pathName = args[0];
				javaType = args[1];
			}
			else {
				System.out.println("Unallowed amount of arguments. Ending program.");
				System.exit(1);
			}
			File[] list = p.getDirectory(pathName);
			String[] sourceCode = p.getSourceCode(list);
			String[] fileName = p.getFileName(list);
			
			//Initializes the parser for each class that is in the folder
			for(int i = 0; i < fileName.length; i++){
				p.initParser(sourceCode[i], fileName[i], javaType, pathNameArray);
			}
		}
		
		//Get Directory goes through the folder given by the pathname and adds every file that ends with .java to the File[] list and returns
		//File[]
		public File[] getDirectory(String pathName) {
			File file = new File(pathName);
			File[] list = file.listFiles(new FilenameFilter() {
				public boolean accept(File file, String name)
				{
					return name.endsWith(".java");
				}
			});
			return list;
		}
		
		//getFileName takes the File[] list from get directory and gets every file name for use in the set Unit name method of initialize parser
		//returns a string array
		public String[] getFileName(File[] list){
			String fileName[] = new String[list.length];
			for(int i = 0; i<list.length; i++) {
				fileName[i] = list[i].toString();
			}
			return fileName;
		}
		
		//getSourceCode goes inside each file in the File list and converts the source code into string which is then stored in a string array and
		//returned. This method also uses fileToString in order to convert the files into strings
		public String[] getSourceCode(File[] list){
			String sourceCode[] = new String[list.length];
			for(int i = 0; i<list.length; i++) {
				File temp = list[i];
				sourceCode[i] = fileToString(temp);
			}
			return sourceCode;
		}
		
		//File to string goes into the file given and converts it into a string. To be used in the getSourceCode function
		public String fileToString(File fileToBeRead) {
			String line;
			String code = "";
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(fileToBeRead));
				while ((line = br.readLine()) != null) {
					code = code + line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return code;
		}
		
		//Initialize Parser is the main method which initializes a parser for each class in the folder given. SetEnviornment is set to the pathName
		//given as a command line argument and also sets the unit name to the current class that it is parsing. Set Kind is set current source code
		//That it is parsing inside the class and now that SetEnviornment and SetUnitName are both initialized we can tell the parser to resolve bindings and 
		//to do bindings recovery so we can recieve much more information and for it to handle syntax errors within the classes being parsed
		public void initParser(String sourceCode, String fileName, String javaType, String[] pathNameArray) {
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(sourceCode.toCharArray());						
			parser.setKind(parser.K_COMPILATION_UNIT);
			parser.setEnvironment(pathNameArray, null, null, true);
			parser.setUnitName(fileName);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			CompilationUnit unit = (CompilationUnit)parser.createAST(null);
			System.out.println(unit.getAST().hasBindingsRecovery());
			
			
			
			
			unit.accept(new ASTVisitor() {
				
				
				public boolean visit(TypeDeclaration node) {					//if the node name is the same is the input given, should increase count.. but it's not working
					//if(node.getName().toString() == javaType){
					//	decCount++;
					//}
					decCount++;
					return true;
				}
				public void endVisit(TypeDeclaration node) {
					//System.out.println("declarations found :" + decCount);
				}
				
				public boolean visit(FieldDeclaration node) {
					refCount++;
					return true;
				}

				public void endVisit(FieldDeclaration node) {
					//System.out.println("references found :" + refCount);
				}
				
				
				public boolean visit(EnumDeclaration node) {
					if(node.getName().toString() == javaType) {
						decCount++;
					}
					return true;
				}
				public void endVisit(EnumDeclaration node) {
				}
		
				
	/*			public boolean visit(AnnotationTypeDeclaration node) {
					decCount++;
					return true;
				}
				public void endVisit(AnnotationTypeDeclaration node) {
				}
		*/		
			});	
			System.out.println(javaType + " Declarations found: " + decCount + "; references found: " + refCount);
		}
}