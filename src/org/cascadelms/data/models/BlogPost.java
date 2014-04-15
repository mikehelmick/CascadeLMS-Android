package org.cascadelms.data.models;

import java.util.Date;

/**
 * An immutable data model class representing a blog post from the Cascade LMS
 * database.
 * <p>
 * New instances should be created using the {@link BlogPost.Builder} class.
 */
public class BlogPost extends Item
{
	/* Required Attributes */
	private final boolean featured;
	private final String author;
	private final Date postedDate;
	private final int commentCount;
	private final String body;
	private final int aPlusCount;
	private final User[] aPlusUsers;

	/* Optional Attributes */
	private final Comment[] comments;

	private BlogPost( BlogPost.Builder builder )
	{
		super( builder.id, builder.title );
		this.featured = builder.featured;
		this.author = builder.author;
		this.postedDate = builder.postedDate;
		this.body = builder.body;
		this.aPlusCount = builder.aPlusCount;
		this.aPlusUsers = builder.aPlusUsers;
		this.commentCount = builder.commentCount;
		this.comments = builder.comments;

		if( this.commentCount != this.comments.length )
		{
			throw new IllegalStateException(
					"Comment count does not match the number of comments." );
		}
	}

	/**
	 * Returns whether this blog post is featured or not.
	 * 
	 * @return <code>true</code> if this post is featured.
	 */
	public boolean isFeatured()
	{
		return featured;
	}

	/**
	 * Returns the name of the user who authored this post.
	 * 
	 * @return a <code>String</code> of the author's name.
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * Gets the text body of the blog post.
	 * 
	 * @return the post body as a <code>String</code>
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * Gets the <code>Comment</code> objects associated with this blog post.
	 * 
	 * @return an array of <code>Comment</code> objects.
	 */
	public Comment[] getComments()
	{
		return comments;
	}

	/**
	 * Gets the number of comments or <code>Comment</code> objects associated
	 * with this blog post.
	 * 
	 * @return the count as an <code>int</code>
	 */
	public int getCommentCount()
	{
		return commentCount;
	}

	/**
	 * Gets the {@link Date} that this blog post was published.
	 * 
	 * @return the publish <code>Date</code>
	 */
	public Date getPostedDate()
	{
		return postedDate;
	}

	/**
	 * Gets the A+ count (score) of this post.
	 * 
	 * @return the count as an <code>int</code>
	 */
	public int getaPlusCount()
	{
		return aPlusCount;
	}

	public User[] getaPlusUsers()
	{
		return aPlusUsers;
	}

	public static class Builder
	{
		/* Required Attributes */
		private int id;
		private String title;
		private boolean featured;
		private String author;
		private Date postedDate;
		private String body;
		private int aPlusCount;
		private User[] aPlusUsers;
		private int commentCount = 0;

		/* Optional Attributes */
		private Comment[] comments;

		public Builder( int id, String title, boolean featured, String author,
				Date postedDate, String body, int aPlusScore,
				User[] aPlusUsers, int commentCount )
		{
			this.id = id;
			this.title = title;
			this.featured = featured;
			this.author = author;
			this.postedDate = postedDate;
			this.body = body;
			this.aPlusCount = aPlusScore;
			this.aPlusUsers = aPlusUsers;
			this.commentCount = commentCount;
		}

		public Builder setComments( Comment[] comments )
		{
			this.comments = comments;
			return this;
		}

		public BlogPost build()
		{
			return new BlogPost( this );
		}
	}
}
