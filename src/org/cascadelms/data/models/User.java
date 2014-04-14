package org.cascadelms.data.models;

public class User
{
	private int id;
	private String name;
	private String gravatarURL;

	public User( int id, String name, String gravatarURL )
	{
		this.id = id;
		this.name = name;
		this.gravatarURL = gravatarURL;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getGravatarURL()
	{
		return gravatarURL;
	}

	@Override
	public String toString()
	{
		return "User #" + id + ": " + name + ", Avatar: " + gravatarURL;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ( ( gravatarURL == null ) ? 0 : gravatarURL.hashCode() );
		result = prime * result + id;
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		User other = (User) obj;
		if( gravatarURL == null )
		{
			if( other.gravatarURL != null )
				return false;
		} else if( !gravatarURL.equals( other.gravatarURL ) )
			return false;
		if( id != other.id )
			return false;
		if( name == null )
		{
			if( other.name != null )
				return false;
		} else if( !name.equals( other.name ) )
			return false;
		return true;
	}
}
