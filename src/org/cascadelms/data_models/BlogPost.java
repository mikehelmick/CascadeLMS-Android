package org.cascadelms.data_models;

import java.util.Date;

/**
 * An immutable data model class representing a blog post from the Cascade LMS database.
 * <p>
 * New instances should be created using the {@link BlogPost.Builder} class.
 */
public class BlogPost extends Item
{
	/* Required Attributes */
	private final boolean featured;
	private final String author;
	private final Date postedAt;
	private final String body;
	
	/* Optional Attributes */
	private final Comment[] comments;
	
	public BlogPost( BlogPost.Builder builder )
	{
		super( builder.id, builder.title );
		this.featured = builder.featured;
		this.author = builder.author;
		this.postedAt = builder.postedAt;
		this.body = builder.body;
		this.comments = builder.comments;
	}

	/**
	 * Returns whether this blog post is featured or not.  
	 * @return <code>true</code> if this post is featured.
	 */
	public boolean isFeatured() 
	{
		return featured;
	}

	/**
	 * Returns the name of the user who authored this post.
	 * @return a <code>String</code> of the author's name.
	 */
	public String getAuthor() 
	{
		return author;
	}

	/**
	 * Gets the {@link Date} that this blog post was published.  
	 * @return the publish <code>Date</code>
	 */
	public Date getPostedAt() 
	{
		return postedAt;
	}

	/**
	 * Gets the text body of the blog post.
	 * @return the post body as a <code>String</code>
	 */
	public String getBody() 
	{
		return body;
	}

	/**
	 * Gets the <code>Comment</code> objects associated with this blog post.
	 * @return an array of <code>Comment</code> objects.
	 */
	public Comment[] getComments()
	{
		return comments;
	}
	
	public static class Builder
	{
		/* Required Attributes */
		private int id;
		private String title;
		private boolean featured;
		private String author;
		private Date postedAt;
		private String body;
		
		/* Optional Attributes */
		private Comment[] comments;
		
		public Builder( int id, String title, boolean featured, String author, Date postedAt, String body )
		{
			this.id = id;
			this.title = title;
			this.featured = featured;
			this.author = author;
			this.postedAt = postedAt;
			this.body = body;
		}
		
		public void setComments( Comment[] comments )
		{
			this.comments = comments;
		}
	}
}
