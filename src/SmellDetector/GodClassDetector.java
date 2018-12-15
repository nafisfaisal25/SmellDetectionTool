package SmellDetector;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.google.common.base.Strings;

import ExploreFiles.ClassExplorer;
import Printer.CSVFileGenerator;

public class GodClassDetector {
	private String csvString="Full Path,Detected God Class,"+"\n";
	
	
	public GodClassDetector() {
		
	}
	
	public GodClassDetector(File projectDir) {
		calculateMetricValues(projectDir);
	}
	
	private void calculateMetricValues(File projectDir) {
		ClassExplorer classexplorer=new ClassExplorer();
		classexplorer.doOperation(projectDir);
		classexplorer.detectGodClass(projectDir);
		classexplorer.CreateCSVForGodClass();
	}
	
	public void compareMetricWithThresholad(Map<String,ArrayList<Double>> map,String path,String className) {
		int FEW=3,VERY_HIGH_wmc=47;
		
		ArrayList<Double> ATFD = null,WMC = null,TCC = null;
		if(map.get("ATFD") != null)ATFD=map.get("ATFD");
		if(map.get("WMC") != null)WMC=map.get("WMC");
		
		//if(map.get("TCC") != null)TCC=map.get("TCC");
		
		
		
		for(int i=0;i<ATFD.size();i++) {
			if( ATFD.get(i) > FEW && WMC.get(i) >= VERY_HIGH_wmc) {
					//System.out.println(path);
		            //System.out.println(Strings.repeat("=", path.length()));
		            //System.out.println(className);
		          	generateCSVString(path, className);
				
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
