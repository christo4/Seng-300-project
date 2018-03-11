

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
			
			//System.out.print("Enter Java Type: ");
			//String type = input.nextLine();
			p.initParser("public class A {String name; char c; Time D;} public class B{}");

		}
		
		public void initParser(String javaFiles) {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter Type: ");
			String javaType = input.nextLine();
			
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(javaFiles.toCharArray());						
			parser.setKind(parser.K_COMPILATION_UNIT);
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			CompilationUnit unit = (CompilationUnit)parser.createAST(null);
			unit.accept(new ASTVisitor() {
				
				
				public boolean visit(TypeDeclaration node) {					//if the node name is the same is the input given, should increase count.. but it's not working
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

		

