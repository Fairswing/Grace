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
	
	/**
	 * 
	 * @return an ArrayList of all the cancers contained in the .csv
	 */
	public static CsvReader getCSV(String FileName) {
		
		CsvReader data = null;
		try {
			data = new CsvReader(FileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		return data;
	}
	
	/**
	 * 
	 * @return return an ArrayList<> with, as first element the min of the first column as second the max of the first column and here on
	 */
	public static ArrayList<Double> getminMax(){
		ArrayList<Double> minMax = new ArrayList<Double>();
		
		try {
			CsvReader dataset = new CsvReader("dataset.csv");
			
			dataset.readHeaders();
			
			// read the first record to insert the first max & min on the array
			dataset.readRecord();
			
			// for debugging purpose only
			//System.out.println(dataset.get("id"));
			
			minMax.add(Double.parseDouble(dataset.get("radius_mean"))); 
			minMax.add(Double.parseDouble(dataset.get("radius_mean"))); 
			minMax.add(Double.parseDouble(dataset.get("texture_mean")));
			minMax.add(Double.parseDouble(dataset.get("texture_mean")));
			minMax.add(Double.parseDouble(dataset.get("perimeter_mean")));
			minMax.add(Double.parseDouble(dataset.get("perimeter_mean")));
			minMax.add(Double.parseDouble(dataset.get("area_mean")));
			minMax.add(Double.parseDouble(dataset.get("area_mean")));
			minMax.add(Double.parseDouble(dataset.get("smoothness_mean")));
			minMax.add(Double.parseDouble(dataset.get("smoothness_mean")));
			minMax.add(Double.parseDouble(dataset.get("compactness_mean")));
			minMax.add(Double.parseDouble(dataset.get("compactness_mean")));
			minMax.add(Double.parseDouble(dataset.get("concavity_mean")));
			minMax.add(Double.parseDouble(dataset.get("concavity_mean")));
			minMax.add(Double.parseDouble(dataset.get("concave points_mean")));
			minMax.add(Double.parseDouble(dataset.get("concave points_mean")));
			minMax.add(Double.parseDouble(dataset.get("symmetry_mean")));
			minMax.add(Double.parseDouble(dataset.get("symmetry_mean")));
			minMax.add(Double.parseDouble(dataset.get("fractal_dimension_mean")));
			minMax.add(Double.parseDouble(dataset.get("fractal_dimension_mean")));
			minMax.add(Double.parseDouble(dataset.get("radius_se")));
			minMax.add(Double.parseDouble(dataset.get("radius_se")));
			minMax.add(Double.parseDouble(dataset.get("texture_se")));
			minMax.add(Double.parseDouble(dataset.get("texture_se")));
			minMax.add(Double.parseDouble(dataset.get("perimeter_se")));
			minMax.add(Double.parseDouble(dataset.get("perimeter_se")));
			minMax.add(Double.parseDouble(dataset.get("area_se")));
			minMax.add(Double.parseDouble(dataset.get("area_se")));
			minMax.add(Double.parseDouble(dataset.get("smoothness_se")));
			minMax.add(Double.parseDouble(dataset.get("smoothness_se")));
			minMax.add(Double.parseDouble(dataset.get("compactness_se")));
			minMax.add(Double.parseDouble(dataset.get("compactness_se")));
			minMax.add(Double.parseDouble(dataset.get("concavity_se")));
			minMax.add(Double.parseDouble(dataset.get("concavity_se")));
			minMax.add(Double.parseDouble(dataset.get("concave points_se")));
			minMax.add(Double.parseDouble(dataset.get("concave points_se")));
			minMax.add(Double.parseDouble(dataset.get("symmetry_se")));
			minMax.add(Double.parseDouble(dataset.get("symmetry_se")));
			minMax.add(Double.parseDouble(dataset.get("fractal_dimension_se")));
			minMax.add(Double.parseDouble(dataset.get("fractal_dimension_se")));
			minMax.add(Double.parseDouble(dataset.get("radius_worst")));
			minMax.add(Double.parseDouble(dataset.get("radius_worst")));
			minMax.add(Double.parseDouble(dataset.get("texture_worst")));
			minMax.add(Double.parseDouble(dataset.get("texture_worst")));
			minMax.add(Double.parseDouble(dataset.get("perimeter_worst")));
			minMax.add(Double.parseDouble(dataset.get("perimeter_worst")));
			minMax.add(Double.parseDouble(dataset.get("area_worst")));
			minMax.add(Double.parseDouble(dataset.get("area_worst")));
			minMax.add(Double.parseDouble(dataset.get("smoothness_worst")));
			minMax.add(Double.parseDouble(dataset.get("smoothness_worst")));
			minMax.add(Double.parseDouble(dataset.get("compactness_worst")));
			minMax.add(Double.parseDouble(dataset.get("compactness_worst")));
			minMax.add(Double.parseDouble(dataset.get("concavity_worst")));
			minMax.add(Double.parseDouble(dataset.get("concavity_worst")));
			minMax.add(Double.parseDouble(dataset.get("concave points_worst")));
			minMax.add(Double.parseDouble(dataset.get("concave points_worst")));
			minMax.add(Double.parseDouble(dataset.get("symmetry_worst")));
			minMax.add(Double.parseDouble(dataset.get("symmetry_worst")));
			minMax.add(Double.parseDouble(dataset.get("fractal_dimension_worst")));
			minMax.add(Double.parseDouble(dataset.get("fractal_dimension_worst")));
			
			// for debugging purpose only
			/*System.out.println("point 0 " + minMax.get(0));
			System.out.println(minMax.get(1));
			System.out.println(minMax.get(2));
			System.out.println(minMax.get(3));*/
			
			while(dataset.readRecord()) {
					
				for( int i = 2, j = 0; i < 32 ; i++, j = j+2) {
					if(Double.parseDouble(dataset.get(i)) < minMax.get(j)) {
						minMax.set(j, Double.parseDouble(dataset.get(i)));
							
						// for debugging purpose only
						//System.out.println("New min at position " + j + " " + minMax.get(j));
					}
					else
						if (Double.parseDouble(dataset.get(i)) > minMax.get(j + 1)) {
							minMax.set(j+1, Double.parseDouble(dataset.get(i)));
							
							// for debugging purpose only
							//System.out.println("New max at position " + j+1 + " " + minMax.get(j+1));
							
						}
					
					//System.out.println("i: " + i + " j: " + j);
				}
					
			}
				
				
			
			dataset.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return minMax;
	}

}
