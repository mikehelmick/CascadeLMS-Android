package org.cascadelms.course_documents;

import java.util.List;

import org.cascadelms.data_models.Document;

public interface DocumentsDataSource 
{
	public List<Document> getDocumentsForCourse( int courseId );
}
