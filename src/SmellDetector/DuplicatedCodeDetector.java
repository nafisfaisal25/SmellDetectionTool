package SmellDetector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import ExploreFiles.ClassExplorer;
import ExploreFiles.DirExplorer;
import Printer.CSVFileGenerator;

public class DuplicatedCodeDetector {
	File projectDir;
	Map<String,String> methodmap=new TreeMap<>();
	private String csvString = "Full Path,Method Signature,Full Path of Duplicated Code,Method Signature of Duplicated Code,"+"\n";
	

	
	public DuplicatedCodeDetector(File projectDir) {
		this.projectDir=projectDir;
		getAllClassName();
		detectClone();
		CreateCSVForDupplicatedCode();
	}
	
	private void getAllClassName() {
    	new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
    		
            try {
                new VoidVisitorAdapter<Object>() {
                	String Path=path;
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        
	                        for (MethodDeclaration method : n.getMethods()) {
	                        	
									Path=path + "-"+ method.getSignature().toString();
		//							System.out.println(method.getBody().get().toString());
		//							System.out.println(method.getAllContainedComments());
		//							System.out.println(Path);
									String methodBody=null;
									try {
										methodBody=method.toString();
									} catch (Exception e) {
										methodBody= "";
									}
//									System.out.println(methodBody);
									if(methodBody!="") {
										List<Comment> commentList=method.getAllContainedComments();
										for (Comment comment : commentList) {
											methodBody=methodBody.replace(comment.toString(), "");
										}
										methodBody=methodBody.replace(" ", "");
										methodBody = methodBody.replaceAll("\\r|\\n", "");
										Path=Path.replace(",", " ");
										methodmap.put(Path,methodBody);
									}
	                        	
	                        }

							
						
                        
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);
    }
	
	public void detectClone() {
		
		List<String> keySet = new ArrayList<String>();
		keySet.addAll(methodmap.keySet());
		
		for(int i=0;i<keySet.size();i++) {
			for(int j=i+1;j<keySet.size();j++) {
				if(methodmap.get(keySet.get(i)).equals(methodmap.get(keySet.get(j)))) {
					String path1[]=keySet.get(i).split("-");
					String path2[]=keySet.get(j).split("-");
					generateCSVString(path1[0],path1[1],path2[0],path2[1]);
//					System.out.println(path1[0]+ " " +path1[1]+ " " +path2[0]+ " " +path2[1]);
//					System.out.println(path1[1]);
//					System.out.println(methodmap.get(keySet.get(i))+" "+(methodmap.get(keySet.get(j))));
//					System.out.println((keySet.get(i)) +" " + (keySet.get(i)));
				}
			}
		}
		
	}
	
	private void generateCSVString(String path1,String methodName1,String path2,String methodName2) {
		csvString+=path1+","+methodName1+"," + path2+"," + methodName2 + "," + "\n";
	}
	
	
	public void CreateCSVForDupplicatedCode() {
        CSVFileGenerator csv=new CSVFileGenerator("C:\\uploads");
        csv.print(csvString,"/DuplicatedCode.csv");
	}
    
}
