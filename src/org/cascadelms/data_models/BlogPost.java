package org.cascadelms.data_models;

import java.util.Date;

/**
 * An immutable data model class representing a blog post from the Cascade LMS database.
 *
 */
public class BlogPost extends Item
{
	private final boolean featured;
	private final String author;
	private final Date postedAt;
	private final String body;
	private final Comment[] comments;
	
	public BlogPost( long id, String title, boolean featured, String author, Date postedAt, String body, Comment[] comments )
	{
		super( id, title );
		this.featured = featured;
		this.author = author;
		this.postedAt = postedAt;
		this.body = body;
		this.comments = comments;
	}
	
	public BlogPost( long id, String title, boolean featured, String author, Date postedAt, String body )
	{
		this( id, title, featured, author, postedAt, body, null );
	}

	public boolean isFeatured() 
	{
		return featured;
	}

	public String getAuthor() 
	{
		return author;
	}

	public Date getPostedAt() 
	{
		return postedAt;
	}

	public String getBody() 
	{
		return body;
	}

	public Comment[] getComments()
	{
		return comments;
	}
}
