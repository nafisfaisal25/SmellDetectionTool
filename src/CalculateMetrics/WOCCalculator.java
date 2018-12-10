package CalculateMetrics;

import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class WOCCalculator {
	private ClassOrInterfaceDeclaration clazz;
	private double numberOfWOC;
	private double numberOfFunctionalMethod;
	private double numberOfPublicMember;
	
	public WOCCalculator(ClassOrInterfaceDeclaration clazz) {
		this.clazz =clazz;
	}
	
	public void doOperation() {
		findPublicFinctionalMethod();
		findPublicMember();
		calculateWOC();
	}
	
	public boolean isGetterSetterMethod(MethodDeclaration method) {
		if(method.getNameAsString().startsWith("get"))return true;
		else if(method.getNameAsString().startsWith("set"))return true;
		else return false;
		
	}
	
	public void findPublicFinctionalMethod() {
		for (MethodDeclaration method : clazz.getMethods()) {
			if(method.isPublic() && !isGetterSetterMethod(method))numberOfFunctionalMethod++;
		}
	}
	
	
	private void findPublicMember() {
        clazz.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(FieldDeclaration n, Void arg) {
                if(n.isPublic())numberOfPublicMember++;
                super.visit(n, arg);
            }

            @Override
            public void visit(MethodDeclaration n, Void arg) {
                if(n.isPublic())numberOfPublicMember++;
                super.visit(n, arg);
            }

        }, null);
    }
	
	private void calculateWOC() {
		if(numberOfPublicMember==0) numberOfWOC=0;
		else numberOfWOC=(double)numberOfFunctionalMethod/numberOfPublicMember;
	}
	
	
	public double getWOC() {
		return numberOfWOC;
	}
}
