package SmellDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.base.Strings;

import ExploreFiles.ClassExplorer;

public class BrainMethodDetector {
	private String csvString = "Full Path,Detected Brain Method,"+"\n";

	public BrainMethodDetector() {
		
	}
	
	public BrainMethodDetector(File projectDir) {
		calculateMetricValues(projectDir);
	}
	
	private void calculateMetricValues(File projectDir) {
		ClassExplorer classexplorer=new ClassExplorer();
		classexplorer.doOperation(projectDir);
		classexplorer.detectLongMethod(projectDir);
		classexplorer.CreateCSVForLongMethod();
	}
	
	public void compareMetricWithThresholad(Map<String,ArrayList<Double>> map, ArrayList<String> methodNameList,String path) {
		int HIGH_class=60,HIGH_cyclo=3,MANY=7,SEVERAL=3;
		
		ArrayList<Double> LOCForMethod = null,CYCLO = null,NOAV = null,MAXNESTING=null;
		if(map.get("LOCForMethod") != null)LOCForMethod=map.get("LOCForMethod");
		if(map.get("CYCLO") != null)CYCLO=map.get("CYCLO");
		if(map.get("NOAV") != null)NOAV=map.get("NOAV");
		if(map.get("MAXNESTING") != null)MAXNESTING=map.get("MAXNESTING");
		
		for(int i=0;i<LOCForMethod.size();i++) {
			if(LOCForMethod.get(i)>HIGH_class && CYCLO.get(i) >=HIGH_cyclo && NOAV.get(i) > MANY && MAXNESTING.get(i) >=SEVERAL) {
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
