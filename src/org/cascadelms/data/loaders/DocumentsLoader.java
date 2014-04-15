package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.data.models.Document;
import org.cascadelms.fragments.DocumentsFragment.DocumentsDataSource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DocumentsLoader extends AsyncTaskLoader<List<Document>>
{
	public static final String ARGS_DIRECTORY = "org.cascadelms.documents.args_directory";
	private final DocumentsDataSource source;
	private final int courseId;
	private final Document folder;

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
		if( this.folder != null && !this.folder.isFolder() )
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
			return this.source.getDocumentsInFolder( courseId, folder );
		}
	}
}
