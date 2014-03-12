package org.cascadelms.course_documents;

import org.cascadelms.data_models.Document;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class DocumentAdapter extends ArrayAdapter<Document> 
{
	public DocumentAdapter( Context context, int resource )
	{
		super( context, resource );
	}
	
}
