package ExploreFiles;

import java.io.File;

import SmellDetector.BrainMethodDetector;
import SmellDetector.DataClassDetector;
import SmellDetector.FeatureEnvyDetector;
import SmellDetector.GodClassDetector;

public class Main {

	public static void main() {
		// TODO Auto-generated method stub
		//File projectDir = new File("C:\\\\Users\\\\DELL\\\\Desktop\\\\MetricsTool\\\\JSettlers-Test-Data\\\\jsettlers-1.1.18-src");
		//File projectDir = new File("C:\\Users\\DELL\\Desktop\\MetricsTool\\Proguard\\proguard5.3.3");
		File projectDir = new File("C:\\uploads\\project");

		//File projectDir = new File("C:\\Users\\DELL\\workspace\\SymbolSolverExperiment");
		//File projectDir = new File("C:\\Users\\DELL\\workspace\\SPL3\\src");
		
		
		//ClassExplorer classexplorer=new ClassExplorer();
		//classexplorer.doOperation(projectDir);
		//classexplorer.listClasses(projectDir);
		
		new FeatureEnvyDetector(projectDir);
		new BrainMethodDetector(projectDir);
		new DataClassDetector(projectDir);
		new GodClassDetector(projectDir);
		
		

	}

}
