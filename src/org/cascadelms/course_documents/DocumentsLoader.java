package org.cascadelms.course_documents;

import java.util.List;

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
		/* This method is performed on a separate thread. */
		return this.source.getDocumentsForCourse( courseId );
	}

}
