package Printer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class CSVFileGenerator {
	private String path;
	
	public CSVFileGenerator(String path) {
		this.path=path;
	}
	
	
	public void print(String res,String fileName) {
    	
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path+fileName));
            writer.write(res);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	
    }
}
