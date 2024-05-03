/**
 * 
 */
package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.csvreader.CsvReader;

/**
 * 
 */
public class DataReader {
	
	private String fileName;
	
	/**
	 * @param fileName
	 */
	public DataReader(String fileName) {
		super();
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public static ArrayList<Cancer> getCSV() {
		
		ArrayList<Cancer> data = new ArrayList<Cancer>();
		 try {
				
				CsvReader dataset = new CsvReader("dataset.csv");
				
				dataset.readHeaders();
				// for debugging purpose only
				//System.out.println(dataset.toString());
				
				while (dataset.readRecord()) {
					data.add(new Cancer(dataset.get("id"), dataset.get("diagnosis"), Float.parseFloat(dataset.get("radius_mean")), Float.parseFloat(dataset.get("texture_mean")), 
							Float.parseFloat(dataset.get("perimeter_mean")), Float.parseFloat(dataset.get("area_mean")), Float.parseFloat(dataset.get("smoothness_mean")),
							Float.parseFloat(dataset.get("compactness_mean")), Float.parseFloat(dataset.get("concavity_mean")), Float.parseFloat(dataset.get("concave points_mean")),
							Float.parseFloat(dataset.get("symmetry_mean")), Float.parseFloat(dataset.get("fractal_dimension_mean")), Float.parseFloat(dataset.get("radius_se")),
							Float.parseFloat(dataset.get("texture_se")), Float.parseFloat(dataset.get("perimeter_se")), Float.parseFloat(dataset.get("area_se")), Float.parseFloat(dataset.get("smoothness_se")),
							Float.parseFloat(dataset.get("compactness_se")), Float.parseFloat(dataset.get("concavity_se")), Float.parseFloat(dataset.get("concave points_se")),
							Float.parseFloat(dataset.get("symmetry_se")), Float.parseFloat(dataset.get("fractal_dimension_se")), Float.parseFloat(dataset.get("radius_worst")),
							Float.parseFloat(dataset.get("texture_worst")), Float.parseFloat(dataset.get("perimeter_worst")), Float.parseFloat(dataset.get("area_worst")),
							Float.parseFloat(dataset.get("smoothness_worst")), Float.parseFloat(dataset.get("compactness_worst")), Float.parseFloat(dataset.get("concavity_worst")),
							Float.parseFloat(dataset.get("concave points_worst")), Float.parseFloat(dataset.get("symmetry_worst")), Float.parseFloat(dataset.get("fractal_dimension_worst"))));
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
		 return data;
	}

}
