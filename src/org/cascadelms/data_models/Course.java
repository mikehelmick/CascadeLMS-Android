package org.cascadelms.data_models;

/** 
 * An immutable data model class representing a single course from the CascadeLMS database.
 * 
 */
public class Course 
{
	/* <p>TODO: Course should use the "Builder" pattern to allow for optional attributes to be set
	 * while enforcing required attributes to exist. */
	
	/* Required Attributes */
	private final long id;
	private final String title;
	
	/* Optional Attributes */
	private final String shortDescription;
	
	public Course( long id, String title, String shortDescription )
	{
		this.id = id;
		this.title = title;
		this.shortDescription = shortDescription;
	}

	public long getId() 
	{
		return id;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getShortDescription() 
	{
		return shortDescription;
	}
}
