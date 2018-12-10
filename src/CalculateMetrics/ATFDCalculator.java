package CalculateMetrics;

import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparser.Navigator;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;

import ExploreFiles.ClassExplorer;
import ExploreFiles.FileExplorer;

public class ATFDCalculator extends VoidVisitorAdapter<Void> {
	private CompilationUnit cu;
	private String className;
	private int numberOfATFD=0;
	private Set<String>allClassname;
	
	public ATFDCalculator(CompilationUnit cu,String className,Set<String>allClassname) {
		//System.out.println(" * " + declaredClass.getName());
		this.cu=cu;
		this.className=className;
		this.allClassname=allClassname;
	}
	public void doOperation() {
	
		variableCallFinder();
		MethodCallFinder();
		
	}
	
	
	public void variableCallFinder() {
		List<FieldAccessExpr> fieldCallList=cu.findAll(FieldAccessExpr.class);
		for (FieldAccessExpr fieldAccessExpr : fieldCallList) {
			//System.out.println(fieldAccessExpr.getScope().calculateResolvedType().asReferenceType().getQualifiedName());
			String type=null;
			try {
				type=fieldAccessExpr.getScope().calculateResolvedType().asReferenceType().getQualifiedName();

			} catch (Exception e) {
				//System.out.println("dhora khaise");
			}
			
			if(type!=null && !type.equals(className) && allClassname.contains(type)) numberOfATFD++;
			
		}
	}
	
	public void MethodCallFinder() {
		List<MethodCallExpr> methodCall=cu.findAll(MethodCallExpr.class);
		for (MethodCallExpr methodCallExpr : methodCall) {
			ResolvedMethodDeclaration x=null;
			try {
				x= methodCallExpr.resolve();
			} catch (Exception e) {
				//System.out.println("dhora khaise");
			}
			
			if(x!=null) {
				String QuilifiedName=x.getPackageName()+"."+x.getClassName();
				if(allClassname.contains(QuilifiedName) && !QuilifiedName.equals(className) ) {
					if(!x.getReturnType().isVoid())numberOfATFD++;
					//if(!x.getReturnType().isVoid())System.out.println(x.getName());
				}
			}
			
		}
	}
	
	
	public int getATFD() {
		return numberOfATFD;
	}
	
	
	
	
/*
	public void getTypeofReferences() {
		
		declaredClass.findAll(AssignExpr.class).forEach(ae -> {
			ResolvedType resolvedType = ae.calculateResolvedType();
			System.out.println(ae.toString() + " is a: " + resolvedType);
			
		});
		System.out.println(declaredClass);
		List<FieldDeclaration> fieldDeclaration = cu.findAll(FieldDeclaration.class);
		//System.out.println("Field type: " + fieldDeclaration.getVariables().get(0).getType().resolve().asReferenceType().getQualifiedName());
		if(fieldDeclaration.size()!=0) {
			System.out.println("Field type: " + fieldDeclaration.get(0).getVariables().get(0).getType().resolve().asReferenceType().getQualifiedName());
			System.out.println(fieldDeclaration.get(0).getVariables().get(0).getType().resolve().asReferenceType());
		}	 
	}
*/
	
	
	
}
