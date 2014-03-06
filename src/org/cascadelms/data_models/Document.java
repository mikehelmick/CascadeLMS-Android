package org.cascadelms.data_models;

import java.net.URL;

/**
 * An immutable data model representing a document from the Cascade LMS database.  
 * <p>
 * Note that this class does not use a builder, because all of its attributes are required.
 */
public class Document extends Item
{
	private final String extension;
	private final long fileSize;
	private final URL documentURL;
	
	public Document( int id, String title, String extension, long fileSize, URL documentURL )
	{
		super( id, title );
		
		/* Checks arguments */
		if( extension == null )
		{
			throw new IllegalArgumentException( "Document cannot have null file extension." );
		}
		if( fileSize < 0 )
		{
			throw new IllegalArgumentException( "Document cannot have negative file size." );
		}
		if( documentURL == null )
		{
			throw new IllegalArgumentException( "Document cannot have null document URL." );
		}
		
		this.extension = extension;
		this.fileSize = fileSize;
		this.documentURL = documentURL;
	}

	/**
	 * Returns this extension of the file associated with this document.
	 * @return the extension as a <code>String</code>
	 */
	public String getFileExtension() 
	{
		return extension;
	}

	/**
	 * Returns this size of the file associated with this document.
	 * @return the file size in bytes as a <code>long</code>
	 */
	public long getFileSize() 
	{
		return fileSize;
	}

	/**
	 * Returns a URL to this document's associated file.
	 * @return a {@link URL}
	 */
	public URL getDocumentURL() 
	{
		return documentURL;
	}
}
