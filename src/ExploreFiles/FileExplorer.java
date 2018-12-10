package ExploreFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.google.common.base.Strings;


public class FileExplorer {
	
	Set<String>dotJavaContainer ;
	
	public FileExplorer(File projectDir) {
		dotJavaContainer=new HashSet<>();
		browseClasses(projectDir);
	}
	
	
	
	public void browseClasses(File rootFile) {
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            
        	dotJavaContainer.add(file.getParent());
            dotJavaContainer.add(file.getParentFile().getParent());
            //dotJavaContainer.add(file.getParentFile().getParentFile().getParent());
            //dotJavaContainer.add(file.getParentFile().getParentFile().getParentFile().getParent());
            
            
        }).explore(rootFile);
    }
	
	public Set<String> getDotjavaContainer(){
		return dotJavaContainer;
	}
}
