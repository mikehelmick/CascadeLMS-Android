package org.cascadelms.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/** 
 * An immutable data model class representing a single course from the CascadeLMS database.
 * <p>
 * New instances should be constructed using the {@link Course.Builder} class.
 * 
 */
public class Course implements Parcelable
{	
	/* Required Attributes */
	private final int id;
	private final String title;
	
	/* Optional Attributes */
	private final String shortDescription;
	
	public static Course HOME = new Course.Builder( 0, "Home" ).build();
	
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
	
	public boolean isHome()
	{
		return this.id == HOME.id;
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
		
		public Builder setShortDescription( String shortDescription )
		{
			this.shortDescription = shortDescription;
			return this;
		}
	}

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel( Parcel dest, int flags ) 
	{
		dest.writeInt( this.id );
		dest.writeString( this.title );
		dest.writeString( this.shortDescription );
	}
	
	public static Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() 
	{
		@Override
		public Course createFromParcel( Parcel source ) 
		{
			return new Course.Builder( source.readInt(), source.readString() ).setShortDescription( source.readString() ).build();
		}

		@Override
		public Course[] newArray( int size ) 
		{
			return new Course[size];
		}
	};
}
