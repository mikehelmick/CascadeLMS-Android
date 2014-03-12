package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.course_documents.DocumentsFragment.DocumentsDataSource;
import org.cascadelms.data.models.Document;

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
		return this.source.getDocumentsForCourse( courseId );
	}
}
