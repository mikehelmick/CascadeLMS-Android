package org.cascadelms.data_models;

/** 
 * An immutable data model class representing a single course from the CascadeLMS database.
 * <p>
 * New instances should be constructed using the {@link Course.Builder} class.
 * 
 */
public class Course 
{	
	/* Required Attributes */
	private final int id;
	private final String title;
	
	/* Optional Attributes */
	private final String shortDescription;
	
	private Course( Course.Builder builder )
	{
		/* Checks argument */
		if( builder == null ) 
			throw new IllegalArgumentException( "Builder must not be null." );
		
		/* Sets the fields according to the builder. */
		this.id = builder.id;
		this.title = builder.title;
		this.shortDescription = builder.shortDescription;
		
		/* Validates data */
		if( this.title == null )
		{
		throw new IllegalStateException( "Course title cannot be null." );
		}
	}

	/**
	 * @return the id of this <code>Course</code>
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * @return the title of this <code>Course</code>
	 */
	public String getTitle() 
	{
		return title;
	}

	/**
	 * @return a short description of this <code>Course</code>; possibly <code>null</code>
	 */
	public String getShortDescription() 
	{
		return shortDescription;
	}
	
	/**
	 * A builder class used to construct new {@link Course} instances.  The <code>
	 * Builder</code> is constructed with the required attributes.  Optional attributes 
	 * can be set with the <code>Builder</code>'s set methods.  Finally, create the 
	 * <code>Course</code> by calling <code>Builder.build()</code>.
	 */
	public static class Builder
	{
		private int id;
		private String title;
		private String shortDescription;
		
		public Builder( int id, String title )
		{
			this.id = id;
			this.title = title;
		}
		
		public Course build()
		{
			return new Course( this );
		}
		
		public void setShortDescription( String shortDescription )
		{
			this.shortDescription = shortDescription;
		}
	}
}
