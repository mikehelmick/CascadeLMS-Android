package org.cascadelms.data.models;

import android.graphics.drawable.Drawable;

import java.net.URL;
import java.util.Date;

/**
 * An immutable data model class representing a single stream item.
 * <p>
 * Note this class does not use the Builder pattern because all of its fields are mandatory.
 * 
 */
public class StreamItem 
{
	/* Required Attributes */
	private final long id;
	private final ItemType type;
    private final String author;
    private final Drawable authorAvatar;
	private final Date summaryDate;
	private final String summary;
	private final URL linkFor;
    private final int score;
    private final int commentCount;

    /* Optional Attributes */
    private final Comment[] comments;
	
	public StreamItem( StreamItem.Builder builder )
	{
		this.id = builder.id;
		this.type = builder.type;
        this.author = builder.author;
        this.authorAvatar = builder.authorAvatar;
		this.summaryDate = builder.summaryDate;
		this.summary = builder.summary;
		this.linkFor = builder.linkFor;
        this.score = builder.score;
        this.commentCount = builder.commentCount;
        this.comments = builder.comments;
	}

	public long getId() 
	{
		return id;
	}

	public ItemType getType() 
	{
		return type;
	}

    public String getAuthor()
    {
        return author;
    }

    public Drawable getAuthorAvatar()
    {
        return authorAvatar;
    }

	public Date getSummaryDate() 
	{
		return summaryDate;
	}

	public String getSummary() 
	{
		return summary;
	}

	public URL getLinkFor() 
	{
		return linkFor;
	}

    public int getScore()
    {
        return score;
    }

    public int getCommentCount()
    {
        return commentCount;
    }

    public Comment[] getComments()
    {
        return comments;
    }

    public enum ItemType
	{
		ASSIGNMENT, BLOG_POST, DOCUMENT, STATUS_UPDATE
	}

    public static class Builder
    {
        /* Required Attributes */
        private long id;
        private ItemType type;
        private String author;
        private Drawable authorAvatar = null;
        private Date summaryDate;
        private String summary;
        private URL linkFor;
        private int score;
        private int commentCount = 0;

        /* Optional Attributes */
        private Comment[] comments;

        public Builder( int id, ItemType type, String author, Date summaryDate,
                        String summary, URL linkFor, int score )
        {
            this.id = id;
            this.type = type;
            this.author = author;
            this.summaryDate = summaryDate;
            this.summary = summary;
            this.linkFor = linkFor;
            this.score = score;
        }

        public Builder setAuthorAvatar( Drawable authorAvatar )
        {
            this.authorAvatar = authorAvatar;
            return this;
        }

        public Builder setCommentCount( int commentCount )
        {
            this.commentCount = commentCount;
            return this;
        }

        public Builder setComments( Comment[] comments )
        {
            this.comments = comments;
            this.commentCount = comments.length;
            return this;
        }
    }
}