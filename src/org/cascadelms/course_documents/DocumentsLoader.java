package org.cascadelms.course_documents;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.course_documents.DocumentsFragment.DocumentsDataSource;
import org.cascadelms.data_models.Document;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class DocumentsLoader extends AsyncTaskLoader<List<Document>>
{
	DocumentsDataSource source;
	int courseId;
	
	public DocumentsLoader(Context context, DocumentsDataSource source, int courseId ) 
	{
		super(context);
		this.source = source;
		this.courseId = courseId;
	}

	@Override
	public List<Document> loadInBackground() 
	{
		LOGGER.info( "Loading documents in the background." );
		/* This method is performed on a separate thread. */
		return this.source.getDocumentsForCourse( courseId );
	}
	private static Logger LOGGER = Logger.getLogger( DocumentsLoader.class.getName() );
}
