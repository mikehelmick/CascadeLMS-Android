package org.cascadelms.course_documents;

import org.cascadelms.data.models.Document;

import android.content.Context;
import android.widget.ArrayAdapter;

public class DocumentAdapter extends ArrayAdapter<Document> 
{
	public DocumentAdapter( Context context, int resource )
	{
		super( context, resource );
	}
	
}
