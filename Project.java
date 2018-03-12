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

public class Project {
		public static int refCount = 0;
		public static int decCount = 0;
		public static void main(String[] args) {

			Project p = new Project();
			String pathName = "";
			String javaType = "";
			
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
			p.initParser(sourceCode, fileName, javaType, pathName);
		}
		
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
		
		public String[] getFileName(File[] list){
			String fileName[] = null;
			for(int i = 0; i<list.length; i++) {
				fileName[i] = list[i].toString();
			}
			return fileName;
		}
		public String[] getSourceCode(File[] list){
			String sourceCode[] = null;
			for(int i = 0; i<list.length; i++) {
				File temp = list[i];
				sourceCode[i] = fileToString(temp);
			}
			return sourceCode;
		}
		
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
		
		public void initParser(String[] sourceCode, String[] fileName, String javaType, String pathName) {
			//Scanner input = new Scanner(System.in);
			//System.out.print("Enter Type: ");
			//String javaType = input.nextLine();
			for(int i = 0; i < sourceCode.length; i++){
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(sourceCode[i].toCharArray());						
			parser.setKind(parser.K_COMPILATION_UNIT);
			parser.setEnviornment(pathName, null, null, true);
			parser.setUnitName(fileName[i]);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			CompilationUnit unit = (CompilationUnit)parser.createAST(null);
			System.out.println(unit.getAST().hasBindingRecovery());s
			
			
			
			
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
			
			}
			System.out.println(javaType + " Declarations found: " + decCount + "; references found: " + refCount);
		}
		
}

		