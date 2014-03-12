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
	private final Date summaryDate;
	private final String summary;
	private final URL linkFor;
	
	public StreamItem( long id, ItemType type, Date summaryDate, String summary, URL linkFor ) 
	{
		this.id = id;
		this.type = type;
		this.summaryDate = summaryDate;
		this.summary = summary;
		this.linkFor = linkFor;
	}

	public long getId() 
	{
		return id;
	}

	public ItemType getType() 
	{
		return type;
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

	public enum ItemType
	{
		ASSIGNMENT, BLOG_POST, DOCUMENT
	}
}