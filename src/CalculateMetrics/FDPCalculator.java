package CalculateMetrics;

import java.util.Set;

import com.github.javaparser.ast.body.MethodDeclaration;

public class FDPCalculator {
	private MethodDeclaration method;
	private int numberOfFDP;
	private String className;
	private Set<String>allClassName;
	
	public FDPCalculator(MethodDeclaration method,String className,Set<String>allClassName) {
		this.method=method;
		this.className=className;
		this.allClassName=allClassName;
	}
	
	public void doOperation() {
		calculateFDP();
	}
	
	public void calculateFDP() {
		
		ATFDforMethodCalculator atfd=new ATFDforMethodCalculator(method, className, allClassName);
		atfd.doOperation();
		numberOfFDP=atfd.getTotalDiffererntClassAccessed();
		
		
	}
	
	public int getFDP() {
		return numberOfFDP;
	}
}
