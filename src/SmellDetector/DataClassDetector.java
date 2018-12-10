package SmellDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.base.Strings;

import ExploreFiles.ClassExplorer;

public class DataClassDetector {
	private String csvString="Full Path,Detected Data Class,"+"\n";

	public DataClassDetector() {
		
	}
	
	public DataClassDetector(File projectDir) {
		calculateMetricValues(projectDir);
	}
	
	private void calculateMetricValues(File projectDir) {
		ClassExplorer classexplorer=new ClassExplorer();
		classexplorer.doOperation(projectDir);
		classexplorer.detectDataClass(projectDir);
		classexplorer.CreateCSVForDataClass();
	}
	
	public void compareMetricWithThresholad(Map<String,ArrayList<Double>> map,String path,String className) {
		//int HIGH_class=65,HIGH_cyclo=4,MANY=7;
		int FEW=5,HIGH_wmc=31,VERY_HIGH_wmc=47,MANY=8;
		
		ArrayList<Double> WOC = null,WMC = null,NOAM = null,NOPA = null;
		if(map.get("WOC") != null)WOC=map.get("WOC");
		if(map.get("WMC") != null)WMC=map.get("WMC");
		if(map.get("NOAM") != null)NOAM=map.get("NOAM");
		if(map.get("NOPA") != null)NOPA=map.get("NOPA");
		
		
		for(int i=0;i<WOC.size();i++) {
			//System.out.println(WOC.get(i));
			if( ((NOAM.get(i)+NOPA.get(i))>FEW && WMC.get(i) <HIGH_wmc) || ((NOAM.get(i)+NOPA.get(i))>MANY && WMC.get(i) <VERY_HIGH_wmc) ) {
				if(WOC.get(i) < (1.0/3)) {
					//System.out.println(path);
		            //System.out.println(Strings.repeat("=", path.length()));
		            //System.out.println(className);
					generateCSVString(path, className);
				}
			}
		}

		
	}
	
	private void generateCSVString(String path,String className) {
		csvString+=path+","+className+","+"\n";
	}
	
	public String getCsvString() {
		return csvString;
	}
}
