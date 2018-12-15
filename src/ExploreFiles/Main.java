package ExploreFiles;

import java.io.File;

import SmellDetector.BrainMethodDetector;
import SmellDetector.DataClassDetector;
import SmellDetector.DuplicatedCodeDetector;
import SmellDetector.FeatureEnvyDetector;
import SmellDetector.GodClassDetector;

public class Main {

	public static void main() {
		
		File projectDir = new File("C:\\uploads\\project");

		
		
		new FeatureEnvyDetector(projectDir);
		new BrainMethodDetector(projectDir);
		new DataClassDetector(projectDir);
		new GodClassDetector(projectDir);
		new DuplicatedCodeDetector(projectDir);
		
		

	}

}
