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
}
