package org.cascadelms.data_models;

/**
 * An abstract base class for an immutable entity from the Cascade LMS database.  
 *
 */
public abstract class Item 
{
	private final long id;
	private final String title;
	
	Item( long id, String title )
	{
		this.id = id;
		this.title = title;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public String getTitle()
	{
		return this.title;
	}
}
