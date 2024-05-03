package application;

public class Cancer {
	
	private String id;
	private char diagnosis;
	private float radius_mean, texture_mean, perimeter_mean, area_mean, smoothness_mean, compactness_mean, concavity_mean, concave_points_mean, 
			symmetry_mean, fractal_dimension_mean, radius_se, texture_se, perimeter_se, area_se, smoothness_se, 
			compactness_se, concavity_se, concave_points_se, symmetry_se, fractal_dimension_se, radius_worst, 
			texture_worst, perimeter_worst, area_worst, smoothness_worst, compactness_worst, concavity_worst, 
			concave_points_worst, symmetry_worst, fractal_dimension_worst;
	
	/**
	 * @param id
	 * @param diagnosis
	 * @param radius_mean
	 * @param texture_mean
	 * @param perimeter_mean
	 * @param area_mean
	 * @param smoothness_mean
	 * @param compactness_mean
	 * @param concavity_mean
	 * @param concave_points_mean
	 * @param symmetry_mean
	 * @param fractal_dimension_mean
	 * @param radius_se
	 * @param texture_se
	 * @param perimeter_se
	 * @param area_se
	 * @param smoothness_se
	 * @param compactness_se
	 * @param concavity_se
	 * @param concave_points_se
	 * @param symmetry_se
	 * @param fractal_dimension_se
	 * @param radius_worst
	 * @param texture_worst
	 * @param perimeter_worst
	 * @param area_worst
	 * @param smoothness_worst
	 * @param compactness_worst
	 * @param concavity_worst
	 * @param concave_points_worst
	 * @param symmetry_worst
	 * @param fractal_dimension_worst
	 */
	public Cancer(String id, char diagnosis, float radius_mean, float texture_mean, float perimeter_mean,
			float area_mean, float smoothness_mean, float compactness_mean, float concavity_mean,
			float concave_points_mean, float symmetry_mean, float fractal_dimension_mean, float radius_se,
			float texture_se, float perimeter_se, float area_se, float smoothness_se, float compactness_se,
			float concavity_se, float concave_points_se, float symmetry_se, float fractal_dimension_se,
			float radius_worst, float texture_worst, float perimeter_worst, float area_worst, float smoothness_worst,
			float compactness_worst, float concavity_worst, float concave_points_worst, float symmetry_worst,
			float fractal_dimension_worst) {
		super();
		this.id = id;
		this.diagnosis = diagnosis;
		this.radius_mean = radius_mean;
		this.texture_mean = texture_mean;
		this.perimeter_mean = perimeter_mean;
		this.area_mean = area_mean;
		this.smoothness_mean = smoothness_mean;
		this.compactness_mean = compactness_mean;
		this.concavity_mean = concavity_mean;
		this.concave_points_mean = concave_points_mean;
		this.symmetry_mean = symmetry_mean;
		this.fractal_dimension_mean = fractal_dimension_mean;
		this.radius_se = radius_se;
		this.texture_se = texture_se;
		this.perimeter_se = perimeter_se;
		this.area_se = area_se;
		this.smoothness_se = smoothness_se;
		this.compactness_se = compactness_se;
		this.concavity_se = concavity_se;
		this.concave_points_se = concave_points_se;
		this.symmetry_se = symmetry_se;
		this.fractal_dimension_se = fractal_dimension_se;
		this.radius_worst = radius_worst;
		this.texture_worst = texture_worst;
		this.perimeter_worst = perimeter_worst;
		this.area_worst = area_worst;
		this.smoothness_worst = smoothness_worst;
		this.compactness_worst = compactness_worst;
		this.concavity_worst = concavity_worst;
		this.concave_points_worst = concave_points_worst;
		this.symmetry_worst = symmetry_worst;
		this.fractal_dimension_worst = fractal_dimension_worst;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the diagnosis
	 */
	public char getDiagnosis() {
		return diagnosis;
	}

	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(char diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * @return the radius_mean
	 */
	public float getRadius_mean() {
		return radius_mean;
	}

	/**
	 * @param radius_mean the radius_mean to set
	 */
	public void setRadius_mean(float radius_mean) {
		this.radius_mean = radius_mean;
	}

	/**
	 * @return the texture_mean
	 */
	public float getTexture_mean() {
		return texture_mean;
	}

	/**
	 * @param texture_mean the texture_mean to set
	 */
	public void setTexture_mean(float texture_mean) {
		this.texture_mean = texture_mean;
	}

	/**
	 * @return the perimeter_mean
	 */
	public float getPerimeter_mean() {
		return perimeter_mean;
	}

	/**
	 * @param perimeter_mean the perimeter_mean to set
	 */
	public void setPerimeter_mean(float perimeter_mean) {
		this.perimeter_mean = perimeter_mean;
	}

	/**
	 * @return the area_mean
	 */
	public float getArea_mean() {
		return area_mean;
	}

	/**
	 * @param area_mean the area_mean to set
	 */
	public void setArea_mean(float area_mean) {
		this.area_mean = area_mean;
	}

	/**
	 * @return the smoothness_mean
	 */
	public float getSmoothness_mean() {
		return smoothness_mean;
	}

	/**
	 * @param smoothness_mean the smoothness_mean to set
	 */
	public void setSmoothness_mean(float smoothness_mean) {
		this.smoothness_mean = smoothness_mean;
	}

	/**
	 * @return the compactness_mean
	 */
	public float getCompactness_mean() {
		return compactness_mean;
	}

	/**
	 * @param compactness_mean the compactness_mean to set
	 */
	public void setCompactness_mean(float compactness_mean) {
		this.compactness_mean = compactness_mean;
	}

	/**
	 * @return the concavity_mean
	 */
	public float getConcavity_mean() {
		return concavity_mean;
	}

	/**
	 * @param concavity_mean the concavity_mean to set
	 */
	public void setConcavity_mean(float concavity_mean) {
		this.concavity_mean = concavity_mean;
	}

	/**
	 * @return the concave_points_mean
	 */
	public float getConcave_points_mean() {
		return concave_points_mean;
	}

	/**
	 * @param concave_points_mean the concave_points_mean to set
	 */
	public void setConcave_points_mean(float concave_points_mean) {
		this.concave_points_mean = concave_points_mean;
	}

	/**
	 * @return the symmetry_mean
	 */
	public float getSymmetry_mean() {
		return symmetry_mean;
	}

	/**
	 * @param symmetry_mean the symmetry_mean to set
	 */
	public void setSymmetry_mean(float symmetry_mean) {
		this.symmetry_mean = symmetry_mean;
	}

	/**
	 * @return the fractal_dimension_mean
	 */
	public float getFractal_dimension_mean() {
		return fractal_dimension_mean;
	}

	/**
	 * @param fractal_dimension_mean the fractal_dimension_mean to set
	 */
	public void setFractal_dimension_mean(float fractal_dimension_mean) {
		this.fractal_dimension_mean = fractal_dimension_mean;
	}

	/**
	 * @return the radius_se
	 */
	public float getRadius_se() {
		return radius_se;
	}

	/**
	 * @param radius_se the radius_se to set
	 */
	public void setRadius_se(float radius_se) {
		this.radius_se = radius_se;
	}

	/**
	 * @return the texture_se
	 */
	public float getTexture_se() {
		return texture_se;
	}

	/**
	 * @param texture_se the texture_se to set
	 */
	public void setTexture_se(float texture_se) {
		this.texture_se = texture_se;
	}

	/**
	 * @return the perimeter_se
	 */
	public float getPerimeter_se() {
		return perimeter_se;
	}

	/**
	 * @param perimeter_se the perimeter_se to set
	 */
	public void setPerimeter_se(float perimeter_se) {
		this.perimeter_se = perimeter_se;
	}

	/**
	 * @return the area_se
	 */
	public float getArea_se() {
		return area_se;
	}

	/**
	 * @param area_se the area_se to set
	 */
	public void setArea_se(float area_se) {
		this.area_se = area_se;
	}

	/**
	 * @return the smoothness_se
	 */
	public float getSmoothness_se() {
		return smoothness_se;
	}

	/**
	 * @param smoothness_se the smoothness_se to set
	 */
	public void setSmoothness_se(float smoothness_se) {
		this.smoothness_se = smoothness_se;
	}

	/**
	 * @return the compactness_se
	 */
	public float getCompactness_se() {
		return compactness_se;
	}

	/**
	 * @param compactness_se the compactness_se to set
	 */
	public void setCompactness_se(float compactness_se) {
		this.compactness_se = compactness_se;
	}

	/**
	 * @return the concavity_se
	 */
	public float getConcavity_se() {
		return concavity_se;
	}

	/**
	 * @param concavity_se the concavity_se to set
	 */
	public void setConcavity_se(float concavity_se) {
		this.concavity_se = concavity_se;
	}

	/**
	 * @return the concave_points_se
	 */
	public float getConcave_points_se() {
		return concave_points_se;
	}

	/**
	 * @param concave_points_se the concave_points_se to set
	 */
	public void setConcave_points_se(float concave_points_se) {
		this.concave_points_se = concave_points_se;
	}

	/**
	 * @return the symmetry_se
	 */
	public float getSymmetry_se() {
		return symmetry_se;
	}

	/**
	 * @param symmetry_se the symmetry_se to set
	 */
	public void setSymmetry_se(float symmetry_se) {
		this.symmetry_se = symmetry_se;
	}

	/**
	 * @return the fractal_dimension_se
	 */
	public float getFractal_dimension_se() {
		return fractal_dimension_se;
	}

	/**
	 * @param fractal_dimension_se the fractal_dimension_se to set
	 */
	public void setFractal_dimension_se(float fractal_dimension_se) {
		this.fractal_dimension_se = fractal_dimension_se;
	}

	/**
	 * @return the radius_worst
	 */
	public float getRadius_worst() {
		return radius_worst;
	}

	/**
	 * @param radius_worst the radius_worst to set
	 */
	public void setRadius_worst(float radius_worst) {
		this.radius_worst = radius_worst;
	}

	/**
	 * @return the texture_worst
	 */
	public float getTexture_worst() {
		return texture_worst;
	}

	/**
	 * @param texture_worst the texture_worst to set
	 */
	public void setTexture_worst(float texture_worst) {
		this.texture_worst = texture_worst;
	}

	/**
	 * @return the perimeter_worst
	 */
	public float getPerimeter_worst() {
		return perimeter_worst;
	}

	/**
	 * @param perimeter_worst the perimeter_worst to set
	 */
	public void setPerimeter_worst(float perimeter_worst) {
		this.perimeter_worst = perimeter_worst;
	}

	/**
	 * @return the area_worst
	 */
	public float getArea_worst() {
		return area_worst;
	}

	/**
	 * @param area_worst the area_worst to set
	 */
	public void setArea_worst(float area_worst) {
		this.area_worst = area_worst;
	}

	/**
	 * @return the smoothness_worst
	 */
	public float getSmoothness_worst() {
		return smoothness_worst;
	}

	/**
	 * @param smoothness_worst the smoothness_worst to set
	 */
	public void setSmoothness_worst(float smoothness_worst) {
		this.smoothness_worst = smoothness_worst;
	}

	/**
	 * @return the compactness_worst
	 */
	public float getCompactness_worst() {
		return compactness_worst;
	}

	/**
	 * @param compactness_worst the compactness_worst to set
	 */
	public void setCompactness_worst(float compactness_worst) {
		this.compactness_worst = compactness_worst;
	}

	/**
	 * @return the concavity_worst
	 */
	public float getConcavity_worst() {
		return concavity_worst;
	}

	/**
	 * @param concavity_worst the concavity_worst to set
	 */
	public void setConcavity_worst(float concavity_worst) {
		this.concavity_worst = concavity_worst;
	}

	/**
	 * @return the concave_points_worst
	 */
	public float getConcave_points_worst() {
		return concave_points_worst;
	}

	/**
	 * @param concave_points_worst the concave_points_worst to set
	 */
	public void setConcave_points_worst(float concave_points_worst) {
		this.concave_points_worst = concave_points_worst;
	}

	/**
	 * @return the symmetry_worst
	 */
	public float getSymmetry_worst() {
		return symmetry_worst;
	}

	/**
	 * @param symmetry_worst the symmetry_worst to set
	 */
	public void setSymmetry_worst(float symmetry_worst) {
		this.symmetry_worst = symmetry_worst;
	}

	/**
	 * @return the fractal_dimension_worst
	 */
	public float getFractal_dimension_worst() {
		return fractal_dimension_worst;
	}

	/**
	 * @param fractal_dimension_worst the fractal_dimension_worst to set
	 */
	public void setFractal_dimension_worst(float fractal_dimension_worst) {
		this.fractal_dimension_worst = fractal_dimension_worst;
	}

}
