package CalculateMetrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

public class NOAVCalculator {
	private MethodDeclaration method;
	private Set<String>allClassName;
	private Set<String> variableList=new HashSet<>();
	private String className;
	
	
	public NOAVCalculator(MethodDeclaration method,String className,Set<String>allClassname) {
		this.method=method;
		this.className=className;
		this.allClassName=allClassname;
		
	}
	
	public void doOperation() {
		countLocalVariable();
		countParameter();
		checkAssignExpr();  //global variable can be used as target of a assignment operation
		checkArgument();	//global variable can be passed as an argument of a method of a different class
		countVariableCall(); // access variable of foreign class
	}
	
	
	

	private void countLocalVariable() {
		List<VariableDeclarator> variableDeclarator=method.findAll(VariableDeclarator.class);
    	for (VariableDeclarator variable : variableDeclarator) {
    		variableList.add(variable.getNameAsString());
    		//System.out.println(variable.getNameAsString());
    	}
	}
	
	private void countParameter() {
		
		for(Parameter parameter: method.getParameters()) {
			variableList.add(parameter.getNameAsString());
			//System.out.println(parameter.getNameAsString());
		}
	}
	
	private void checkAssignExpr() {
		for(AssignExpr assignExpr: method.findAll(AssignExpr.class)) {
			variableList.add(assignExpr.getTarget().toString());
			//System.out.println(assignExpr.getTarget().toString());
		}
	}
	private void checkArgument() {
		for(MethodCallExpr methodCallExpr: method.findAll(MethodCallExpr.class)) {
			for(Expression argument: methodCallExpr.getArguments()) {
				variableList.add(argument.toString());
				//System.out.println(argument.toString());
			}
		}
		
	}
	
	private void countVariableCall() {
		
		
		List<FieldAccessExpr> fieldCallList=method.findAll(FieldAccessExpr.class);
		for (FieldAccessExpr fieldAccessExpr : fieldCallList) {
			String type=null;
			try {
				type=fieldAccessExpr.getScope().calculateResolvedType().asReferenceType().getQualifiedName();

			} catch (Exception e) {
				//System.out.println("dhora khaise");
			}
			
			if(type!=null && !type.equals(className) && allClassName.contains(type)) {
				variableList.add((fieldAccessExpr.getNameAsString()));
				//System.out.println(fieldAccessExpr.getNameAsString());
			}
			
		}
	}
	
	public int getNOAV(){
		return variableList.size();
	}
}
