package CalculateMetrics;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class NOAMCalculator {
	private ClassOrInterfaceDeclaration clazz;
	private int numberOfNOAM;
	
	public NOAMCalculator(ClassOrInterfaceDeclaration clazz) {
		this.clazz=clazz;
	}
	
	public void doOperation() {
		claculateNOAM();
	}
	
	private void claculateNOAM() {
		for (MethodDeclaration method : clazz.getMethods()) {
			//System.out.println(method.getNameAsString());
			if( method.isPublic() ) {
				
				if(method.getNameAsString().startsWith("get"))numberOfNOAM++;
				else if(method.getNameAsString().startsWith("set"))numberOfNOAM++;
			}
		}
	}
	
	public int getNOAM() {
		return numberOfNOAM;
	}

}
