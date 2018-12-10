package CalculateMetrics;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class WeightedMethodCountCalculator {
	private int WeightedMethodCount;
	private ClassOrInterfaceDeclaration declaredClass;
	
	public WeightedMethodCountCalculator(ClassOrInterfaceDeclaration declaredClass) {
		//System.out.println(" * " + declaredClass.getName());
		this.declaredClass=declaredClass;
	}
	
	public int calculateWeightedMethodCount() {
		for (MethodDeclaration method : declaredClass.getMethods()) {
			CyclomaticComplexityCalculator c=new CyclomaticComplexityCalculator(method);
			int complexity=c.calculateComplexity();
			WeightedMethodCount+=complexity;
		}
		return WeightedMethodCount;
	}
	

}
