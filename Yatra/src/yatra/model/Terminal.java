package yatra.model;

public class Terminal {
	private String name;
	private String latitude;
	private String longitude;
	private String placeId;
	
	/**
	 * default constructor
	 */
	public Terminal(){
		
	}
	
	/**
	 * @param name
	 * @param latitude
	 * @param longitude
	 * @param placeId
	 */
	public Terminal(String name, String latitude, String longitude, String placeId) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.placeId = placeId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @return the placeId
	 */
	public String getPlaceId() {
		return placeId;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Terminal [name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", placeId=" + placeId
				+ "]";
	}
	
	
}
