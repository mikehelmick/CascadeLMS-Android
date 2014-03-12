package org.cascadelms.data_models;

/**
 * An abstract base class for an immutable entity from the Cascade LMS database.  
 *
 */
public abstract class Item 
{
	protected final int id;
	protected final String title;
	
	Item( int id, String title )
	{
		if( title == null )
		{
			throw new IllegalArgumentException( "Item cannot have a null title." );
		}
		this.id = id;
		this.title = title;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	@Override
	public String toString() 
	{
		return this.title;
	}
}
