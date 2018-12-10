package CalculateMetrics;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class NOPACalculator {
	private ClassOrInterfaceDeclaration clazz;
	private int numberOfNOPA;
	
	
	public NOPACalculator(ClassOrInterfaceDeclaration clazz) {
		this.clazz=clazz;
	}
	
	public void doOperation() {
		calculateNOPA();
	}
	
	
	private void calculateNOPA() {
		List<FieldDeclaration> fields=clazz.getFields();
		for (FieldDeclaration fieldDeclaration : fields) {
			if(fieldDeclaration.isPublic() && !fieldDeclaration.isStatic())numberOfNOPA++;
		}
	}
	
	public int getNOPA() {
		return numberOfNOPA;
	}

}
