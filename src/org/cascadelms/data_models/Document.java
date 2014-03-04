package org.cascadelms.data_models;

import java.net.URL;

/**
 * An immutable data model representing a document from the Cascade LMS database.  
 * 
 */
public class Document extends Item
{
	private final String extension;
	private final long fileSize;
	private final URL documentURL;
	
	public Document( long id, String title, String extension, long fileSize, URL documentURL )
	{
		super( id, title );
		this.extension = extension;
		this.fileSize = fileSize;
		this.documentURL = documentURL;
	}

	public String getExtension() 
	{
		return extension;
	}

	public long getFileSize() 
	{
		return fileSize;
	}

	public URL getDocumentURL() 
	{
		return documentURL;
	}
}
