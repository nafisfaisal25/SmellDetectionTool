package CalculateMetrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

public class ATFDforMethodCalculator {
	private MethodDeclaration method;
	private String className;
	private int numberOfATFD=0;
	private Set<String>allClassname;
	private Set<String>TotalDifferentClassAccessed=new HashSet<>();
	
	public ATFDforMethodCalculator(MethodDeclaration method,String className,Set<String>allClassname) {
		//System.out.println(" * " + declaredClass.getName());
		this.method=method;
		this.className=className;
		this.allClassname=allClassname;
	}
	public void doOperation() {
	
		variableCallFinder();
		MethodCallFinder();
		
	}
	
	
	public void variableCallFinder() {
		List<FieldAccessExpr> fieldCallList=method.findAll(FieldAccessExpr.class);
		for (FieldAccessExpr fieldAccessExpr : fieldCallList) {
			//System.out.println(fieldAccessExpr.getScope().calculateResolvedType().asReferenceType().getQualifiedName());
			String type=null;
			try {
				type=fieldAccessExpr.getScope().calculateResolvedType().asReferenceType().getQualifiedName();

			} catch (Exception e) {
				//System.out.println("dhora khaise");
			}
			
			if(type!=null && !type.equals(className) && allClassname.contains(type)) {
				numberOfATFD++;
				TotalDifferentClassAccessed.add(type);
			}
			
		}
	}
	
	public void MethodCallFinder() {
		List<MethodCallExpr> methodCall=method.findAll(MethodCallExpr.class);
		for (MethodCallExpr methodCallExpr : methodCall) {
			ResolvedMethodDeclaration x=null;
			//System.out.println(methodCallExpr);
			try {
				x= methodCallExpr.resolve();
			} catch (Exception e) {
				
				//System.out.println("dhora khaise");
			}
			
			if(x!=null) {
				String QuilifiedName=x.getPackageName()+"."+x.getClassName();

				if(allClassname.contains(QuilifiedName) && !QuilifiedName.equals(className) ) {
					if(!x.getReturnType().isVoid()) {
						numberOfATFD++;
						TotalDifferentClassAccessed.add(QuilifiedName);
					}
					
					//if(!x.getReturnType().isVoid())System.out.println(x.getName());
				}
			}
			
		}
	}
	
	
	public int getATFD() {
		return numberOfATFD;
	}
	
	public int getTotalDiffererntClassAccessed() {
		return TotalDifferentClassAccessed.size();
	}
	
}
