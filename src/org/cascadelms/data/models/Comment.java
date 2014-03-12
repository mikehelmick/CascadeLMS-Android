package org.cascadelms.data.models;

import java.util.Date;

public class Comment 
{
	private final String body;
	private final String author;
	private final Date createdAt;
	
	public Comment( String body, String author, Date createdAt )
	{
		this.body = body;
		this.author = author;
		this.createdAt = createdAt;
	}

	public String getBody() 
	{
		return body;
	}

	public String getAuthor() 
	{
		return author;
	}

	public Date getCreatedAt() 
	{
		return createdAt;
	}
}
