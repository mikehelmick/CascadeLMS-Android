package org.cascadelms.data.models;

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
	private final Date summaryDate;
	private final String summary;
	private final URL linkFor;
    private final int score;
    private final int commentCount;
	
	public StreamItem( long id, ItemType type, String author, Date summaryDate, String summary,
                       URL linkFor, int score, int commentCount )
	{
		this.id = id;
		this.type = type;
        this.author = author;
		this.summaryDate = summaryDate;
		this.summary = summary;
		this.linkFor = linkFor;
        this.score = score;
        this.commentCount = commentCount;
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

    public enum ItemType
	{
		ASSIGNMENT, BLOG_POST, DOCUMENT
	}
}