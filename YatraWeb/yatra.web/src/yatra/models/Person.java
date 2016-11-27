package yatra.models;

/**
 * @author Mitikaa
 *
 */
public class Person {
	private String name;
	private String location;
	
	public Person(){
		
	}
	
	/**
	 * @param name
	 * @param location
	 */
	public Person(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person [name=" + name + ", location=" + location + "]";
	}
	
}
