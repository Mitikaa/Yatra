package yatra.model;
/**
 * @author Mitikaa
 *
 */
public class Station {
	private String name;
	private String latitude;
	private String longitude;
	private String filters;
	private String iconUrl;
	
	/**
	 * default constructor
	 */
	public Station(){
		
	}
	
	/**
	 * @param name
	 * @param latitude
	 * @param longitude
	 * @param filters
	 * @param iconUrl
	 */
	public Station(String name, String latitude, String longitude, String filters, String iconUrl) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.filters = filters;
		this.iconUrl = iconUrl;
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
	 * @return the filters
	 */
	public String getFilters() {
		return filters;
	}

	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
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
	 * @param filters the filters to set
	 */
	public void setFilters(String filters) {
		this.filters = filters;
	}

	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Station [name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", filters=" + filters
				+ ", iconUrl=" + iconUrl + "]";
	}
	
	
}
