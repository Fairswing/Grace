package application;

public class Cancer {
	
	private String id;
	private char diagnosis;
	private float radius, texture, perimeter, area, smoothness, compactness, concavity, concavity_points;
	
	/**
	 * @param id
	 * @param diagnosis
	 * @param radius
	 * @param texture
	 * @param perimeter
	 * @param area
	 * @param smoothness
	 * @param compactness
	 * @param concavity
	 * @param concavity_points
	 */
	public Cancer(String id, char diagnosis, float radius, float texture, float perimeter, float area, float smoothness,
			float compactness, float concavity, float concavity_points) {
		super();
		this.id = id;
		this.diagnosis = diagnosis;
		this.radius = radius;
		this.texture = texture;
		this.perimeter = perimeter;
		this.area = area;
		this.smoothness = smoothness;
		this.compactness = compactness;
		this.concavity = concavity;
		this.concavity_points = concavity_points;
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
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
	/**
	 * @return the texture
	 */
	public float getTexture() {
		return texture;
	}
	/**
	 * @param texture the texture to set
	 */
	public void setTexture(float texture) {
		this.texture = texture;
	}
	/**
	 * @return the perimeter
	 */
	public float getPerimeter() {
		return perimeter;
	}
	/**
	 * @param perimeter the perimeter to set
	 */
	public void setPerimeter(float perimeter) {
		this.perimeter = perimeter;
	}
	/**
	 * @return the area
	 */
	public float getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(float area) {
		this.area = area;
	}
	/**
	 * @return the smoothness
	 */
	public float getSmoothness() {
		return smoothness;
	}
	/**
	 * @param smoothness the smoothness to set
	 */
	public void setSmoothness(float smoothness) {
		this.smoothness = smoothness;
	}
	/**
	 * @return the compactness
	 */
	public float getCompactness() {
		return compactness;
	}
	/**
	 * @param compactness the compactness to set
	 */
	public void setCompactness(float compactness) {
		this.compactness = compactness;
	}
	/**
	 * @return the concavity
	 */
	public float getConcavity() {
		return concavity;
	}
	/**
	 * @param concavity the concavity to set
	 */
	public void setConcavity(float concavity) {
		this.concavity = concavity;
	}
	/**
	 * @return the concavity_points
	 */
	public float getConcavity_points() {
		return concavity_points;
	}
	/**
	 * @param concavity_points the concavity_points to set
	 */
	public void setConcavity_points(float concavity_points) {
		this.concavity_points = concavity_points;
	}
	
	

}
