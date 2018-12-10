package ExploreFiles;

import CalculateMetrics.*;
import Printer.CSVFileGenerator;
import SmellDetector.DataClassDetector;
import SmellDetector.FeatureEnvyDetector;
import SmellDetector.GodClassDetector;
import SmellDetector.LongMethodDetector;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.google.common.base.Strings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Handler;

import javax.swing.SingleSelectionModel;

public class ClassExplorer {
	
	public Set<String> allClassName=new HashSet<>();
	public JavaParser parser=new JavaParser();
	private String className;
	private String packageName;
	
	GodClassDetector g=new GodClassDetector();
	DataClassDetector d=new DataClassDetector();
	FeatureEnvyDetector f=new FeatureEnvyDetector();
	LongMethodDetector l=new LongMethodDetector();
	
	
	public void doOperation(File projectDir) {
		getAllClassName(projectDir);
		//System.out.println(allClassName.size());
		prepareJavaParser(new FileExplorer(projectDir).getDotjavaContainer());
		//listClasses(projectDir);
	}

    public void detectFeatureEnvy(File projectDir) {
    	
    	Map <String, ArrayList<Double>> map=new TreeMap<>();
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
        	
        	ArrayList<Double>list;
        	ArrayList<String>methodNameList;
        	
            //System.out.println(path);
            //System.out.println(Strings.repeat("=", path.length()));
            metricsCalculatorHandler handler=new metricsCalculatorHandler(file);
            
            list= handler.ATFDforMethodCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("ATFDForMethod", list);
            list=handler.FDPCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("FDP", list);
            list=handler.LAACalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("LAA", list);
            methodNameList=handler.getMethodNameList(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            
            
            f.compareMetricWithThresholad(map,methodNameList,path);
            
            
            
        }).explore(projectDir);
    }
    
    public void CreateCSVForFeatureEnvy() {
		String csvString=f.getCsvString();
        CSVFileGenerator csv=new CSVFileGenerator("C:\\Users\\DELL\\Desktop\\MetricsTool\\SPL3\\godClassOutput");
        csv.print(csvString,"/featurEnvy.csv");
	}
    
    public void detectLongMethod(File projectDir) {
    	
    	Map <String, ArrayList<Double>> map=new TreeMap<>();
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
        	
        	ArrayList<Double>list;
        	ArrayList<String>methodNameList;
        	
            //System.out.println(path);
            //System.out.println(Strings.repeat("=", path.length()));
            metricsCalculatorHandler handler=new metricsCalculatorHandler(file);
            
            list= handler.LOCforMethodcalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("LOCForMethod", list);
            try {
				list=handler.cyclomaticComplexityCaclHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            map.put("CYCLO", list);
            list=handler.NOAVCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("NOAV", list);
            methodNameList=handler.getMethodNameList(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            
            l.compareMetricWithThresholad(map,methodNameList,path);
            
            
        }).explore(projectDir);
    }
    
    public void CreateCSVForLongMethod() {
		String csvString=l.getCsvString();
        CSVFileGenerator csv=new CSVFileGenerator("C:\\Users\\DELL\\Desktop\\MetricsTool\\SPL3\\godClassOutput");
        csv.print(csvString,"/LongMethod.csv");
	}
    
    public void detectDataClass(File projectDir) {
    	
    	Map <String, ArrayList<Double>> map=new TreeMap<>();
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
        	
        	ArrayList<Double>list;
        	
            metricsCalculatorHandler handler=new metricsCalculatorHandler(file);
            
            list= handler.WOCCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("WOC", list);
            list= handler.NOPACalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("NOPA", list);
            list=handler.NOAMCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("NOAM", list);
            try {
				list=handler.weightedMethodCountCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            map.put("WMC", list);
            
            d.compareMetricWithThresholad(map,path,getClassName(file));
            
            
        }).explore(projectDir);
    }
    
    public void CreateCSVForDataClass() {
		String csvString=d.getCsvString();
        CSVFileGenerator csv=new CSVFileGenerator("C:\\Users\\DELL\\Desktop\\MetricsTool\\SPL3\\godClassOutput");
        csv.print(csvString,"/dataClass.csv");
	}
    
    public void detectGodClass(File projectDir) {
    	
    	Map <String, ArrayList<Double>> map=new TreeMap<>();
    	
        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
        	
        	ArrayList<Double>list;
        	
            metricsCalculatorHandler handler=new metricsCalculatorHandler(file);
            
            list= handler.ATFDCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            map.put("ATFD", list);
            
            try {
				list=handler.weightedMethodCountCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            map.put("WMC", list);
            
            //list= handler.TCCCalcHandler(allClassName, parser, getPackageName(file)+"."+getClassName(file));
            //map.put("TCC", list);
            
            
            
            g.compareMetricWithThresholad(map,path,getClassName(file));
            
            
            
        }).explore(projectDir);
    }

	public void CreateCSVForGodClass() {
		String csvString=g.getCsvString();
        CSVFileGenerator csv=new CSVFileGenerator("C:\\Users\\DELL\\Desktop\\MetricsTool\\SPL3\\godClassOutput");
        csv.print(csvString,"/godClass.csv");
	}
    
    
    public void prepareJavaParser(Set <String> dotJavaContainer) {
		TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);
       
        //combinedSolver.add(new JavaParserTypeSolver(new File("C:\\Users\\DELL\\workspace\\SymbolSolverExperiment\\src\\first")));
        //combinedSolver.add(new JavaParserTypeSolver(new File("C:\\Users\\DELL\\Desktop\\MetricsTool\\JSettlers-Test-Data\\jsettlers-1.1.18-src\\src\\java\\soc\\client")));

        
        for (String filePath : dotJavaContainer) {
        	//System.out.println(filePath);
			combinedSolver.add(new JavaParserTypeSolver(new File(filePath)));
		}
        
		JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
		JavaParser.getStaticConfiguration().setSymbolResolver(symbolSolver);
	}
    
    public void getAllClassName(File projectDir) {
    	new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
        
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        allClassName.add(getPackageName(file)+"."+getClassName(file));
                        
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);
    }
    
    public String getClassName(File file) {
    	
        
            try {
                new VoidVisitorAdapter<Object>() {
                   
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        className=n.getNameAsString();
                        
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
            return className;
       
    }
    
    public String getPackageName(File file) {
    	
        
        try {
            new VoidVisitorAdapter<Object>() {
            	@Override
                public void visit(CompilationUnit n, Object arg) {
                    super.visit(n, arg);
                    try {
                        packageName=n.getPackageDeclaration().get().getNameAsString();

					} catch (Exception e) {
						packageName="";
					}
                    
                }
            }.visit(JavaParser.parse(file), null);
        } catch (IOException e) {
            new RuntimeException(e);
        }
        return packageName;
   
}
    
    
    
    

    public static void main(String[] args) {
        //File projectDir = new File("../SymbolSolverExperiment");
        //listClasses(projectDir);
    }
}

