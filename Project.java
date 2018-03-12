package seng_project;

import java.io.File;
import java.io.FilenameFilter;
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
			String pathName;
			String javaType;
			
			if(args.length == 2) {
				pathName = args[0];
				javaType = args[1];
			}
			else {
				System.out.println("Unallowed amount of arguments. Ending program.");
				System.exit(1);
			}
			p.initParser(p.getDirectory(pathName));
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
		
		public void initParser(File[] pathName) {
			//Scanner input = new Scanner(System.in);
			//System.out.print("Enter Type: ");
			//String javaType = input.nextLine();
			
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(pathName);						
			parser.setKind(parser.K_COMPILATION_UNIT);
			parser.setResolveBindings(true);
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			CompilationUnit unit = (CompilationUnit)parser.createAST(null);
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
