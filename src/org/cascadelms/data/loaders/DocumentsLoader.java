package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.data.models.Document;
import org.cascadelms.fragments.DocumentsFragment.DocumentsDataSource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DocumentsLoader extends AsyncTaskLoader<List<Document>>
{
	DocumentsDataSource source;
	int courseId;
	Document folder;

	/**
	 * Constructs a Loader capable of fetching Documents in the background.
	 * 
	 * @param context
	 *            the Context where this loader will be used.
	 * @param source
	 *            a DocumentsDataSource to provide the data
	 * @param courseId
	 *            the database id of a Course
	 * @param folder
	 *            an optional
	 */
	public DocumentsLoader( Context context, DocumentsDataSource source,
			int courseId, Document folder )
	{
		super( context );
		this.source = source;
		this.courseId = courseId;
		this.folder = folder;

		/* Validates that the Document is indeed a folder. */
		if( folder != null && !folder.isFolder() )
		{
			throw new IllegalArgumentException(
					"The document folder supplied to DocumentsLoader was not actually a folder." );
		}
	}

	@Override
	public List<Document> loadInBackground()
	{
		if( this.folder == null )
		{
			return this.source.getDocumentsForCourse( courseId );
		} else
		{
			return this.source.getDocumentsInFolder( folder );
		}
	}
}
