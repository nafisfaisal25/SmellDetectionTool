package CalculateMetrics;

import java.util.Set;

import com.github.javaparser.ast.body.MethodDeclaration;

public class LAACalculator {
	private MethodDeclaration method;
	private double numberOfLAA;
	private String className;
	private Set<String>allClassName;
	
	public LAACalculator(MethodDeclaration method,String className,Set<String>allClassName) {
		this.method=method;
		this.className=className;
		this.allClassName=allClassName;
	}
	
	public void doOperation() {
		calculateLAA();
	}
	
	public void calculateLAA() {
		
		NOAVCalculator noav=new NOAVCalculator(method, className, allClassName);
		noav.doOperation();
		int numberOfNoav=noav.getNOAV();
		
		ATFDforMethodCalculator atfd=new ATFDforMethodCalculator(method, className, allClassName);
		atfd.doOperation();
		int numberOfAtfd=atfd.getATFD();
		if(numberOfNoav==0)numberOfLAA= 0;
		else numberOfLAA=(double)(numberOfNoav-numberOfAtfd)/numberOfNoav;
		
	}
	
	public double getLAA() {
		return numberOfLAA;
	}

}
