package SmellDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.base.Strings;

import ExploreFiles.ClassExplorer;

public class FeatureEnvyDetector {
	
	private String csvString = "Full Path,Detected Feature Envy Method,"+"\n";

	public FeatureEnvyDetector() {
		
	}
	
	public FeatureEnvyDetector(File projectDir) {
		calculateMetricValues(projectDir);
	}
	
	private void calculateMetricValues(File projectDir) {
		ClassExplorer classexplorer=new ClassExplorer();
		classexplorer.doOperation(projectDir);
		classexplorer.detectFeatureEnvy(projectDir);
		classexplorer.CreateCSVForFeatureEnvy();
	}
	
	public void compareMetricWithThresholad(Map<String,ArrayList<Double>> map, ArrayList<String> methodNameList,String path) {
		int FEW=4;
		
		ArrayList<Double> FDP = null,LAA = null,ATFDForMethod = null;
		if(map.get("FDP") != null)FDP=map.get("FDP");
		if(map.get("LAA") != null)LAA=map.get("LAA");
		if(map.get("ATFDForMethod") != null)ATFDForMethod=map.get("ATFDForMethod");
		
		for(int i=0;i<FDP.size();i++) {
			if(FDP.get(i)<=FEW && LAA.get(i)<(1.0/3) && ATFDForMethod.get(i) > FEW ) {
				//System.out.println(path);
	            //System.out.println(Strings.repeat("=", path.length()));
				//System.out.println(methodNameList.get(i));
				generateCSVString(path, methodNameList.get(i));
			}
		}

		
	}
	private void generateCSVString(String path,String methodName) {
		csvString+=path+","+methodName+","+"\n";
	}
	
	public String getCsvString() {
		return csvString;
	}
}
