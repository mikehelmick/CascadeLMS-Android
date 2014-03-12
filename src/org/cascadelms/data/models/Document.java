package org.cascadelms.data.models;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * An immutable data model representing a document from the Cascade LMS database.  
 * <p>
 * Note that this class does not use a builder, because all of its attributes are required.
 */
public class Document extends Item implements Parcelable
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

	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel( Parcel dest, int flags ) 
	{
		super.writeToParcel( dest, flags );
		dest.writeString( extension );
		dest.writeLong( fileSize );
		dest.writeString( documentURL.toString() );
	}
	
	public static Parcelable.Creator<Document> CREATOR = new Parcelable.Creator<Document>() 
	{
		@Override
		public Document createFromParcel(Parcel source) 
		{
			try 
			{
				return new Document( source.readInt(), source.readString(), source.readString(), 
						source.readLong(), new URL( source.readString() ) );
			} catch (MalformedURLException e) 
			{
				throw new RuntimeException( "Unable to create Document from Parcel.  The documentURL was malformed." );
			}
		}

		@Override
		public Document[] newArray( int size ) 
		{
			return new Document[size];
		}
	};
}
