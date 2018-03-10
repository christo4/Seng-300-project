package seng_project;



import java.util.Map;
import java.util.Scanner;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
public class JavaApplication {
		public static void main(String[] args) {
			int decCount = 0;
			int refCount = 0;
			Project p = new Project();
			Scanner input = new Scanner(System.in);
			//System.out.print("Enter Java Type: ");
			//String type = input.nextLine();
			p.initParser("public class A {ArrayList<String> string = new ArrayList<String>; int seconds;} private class B{} char C;}");
		}
		
		public void initParser(String type) {

			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(type.toCharArray());						
			parser.setKind(parser.K_COMPILATION_UNIT);
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);
			CompilationUnit unit = (CompilationUnit)parser.createAST(null);
			unit.accept(new ASTVisitor() {
				
				public boolean visit(TypeDeclaration node) {
					System.out.println("Type Declaration: " + node.getName());

					return true;
				}
				public boolean visit(FieldDeclaration node) {
					System.out.println("Field Declaration: " + node.getType());
					return true;
				}

				
			});
		}

		

}
