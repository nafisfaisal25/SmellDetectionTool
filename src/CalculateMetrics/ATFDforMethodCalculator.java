package CalculateMetrics;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
	private Map<String,Integer>numberOfVarAccessed=new TreeMap<>();
	
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
				if(!numberOfVarAccessed.containsKey(type))
					numberOfVarAccessed.put(type,1);
				else {
					numberOfVarAccessed.put(type,numberOfVarAccessed.get(type)+1);
				}
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
						if(!numberOfVarAccessed.containsKey(QuilifiedName))
							numberOfVarAccessed.put(QuilifiedName,1);
						else {
							numberOfVarAccessed.put(QuilifiedName,numberOfVarAccessed.get(QuilifiedName)+1);
						}
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
	
	public String getHighestFDP() {
		int maxValue=-1;
		for (Object o : numberOfVarAccessed.keySet()) {
			if(numberOfVarAccessed.get(o)>maxValue)maxValue=numberOfVarAccessed.get(o);
		}
		String key=(String) getKeyFromValue(numberOfVarAccessed,maxValue);
		return key;
	}
	public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
          if (hm.get(o).equals(value)) {
            return o;
          }
        }
        return null;
     }
	
}
