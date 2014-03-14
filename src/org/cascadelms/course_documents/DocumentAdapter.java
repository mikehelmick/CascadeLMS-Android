package org.cascadelms.course_documents;

import org.cascadelms.R;
import org.cascadelms.data.models.Document;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DocumentAdapter extends ArrayAdapter<Document>
{
	public DocumentAdapter( Context context, int resource )
	{
		super( context, resource );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		/* Re-uses the convertView, if possible. */
		TextView textView;
		if( convertView == null )
		{
			textView = (TextView) LayoutInflater.from( this.getContext() )
					.inflate( R.layout.list_item_document, parent, false );
		} else
		{
			textView = (TextView) convertView;
		}
		Document document = this.getItem( position );
		if( document.isFolder() )
		{
			textView.setText( document.getTitle() );
		} else
		{
			textView.setText( document.getTitle() + document.getFileExtension() );
		}
		textView.setCompoundDrawablesWithIntrinsicBounds(
				this.getIconIdForExtension( document.getFileExtension() ), 0,
				0, 0 );
		return textView;
	}

	private int getIconIdForExtension( String extension )
	{
		/* If the extension is null, the document is a directory. */
		if( extension == null )
		{
			return R.drawable.fileicon_folder;
		}
		if( this.stringInList( extension, WORD_EXTENSIONS ) )
		{
			return R.drawable.fileicon_word;
		}
		if( this.stringInList( extension, TEXT_EXTENSIONS ) )
		{
			return R.drawable.fileicon_text;
		}
		if( this.stringInList( extension, POWERPOINT_EXTENSIONS ) )
		{
			return R.drawable.fileicon_powerpoint;
		}

		return R.drawable.fileicon_generic;
	}

	private boolean stringInList( String string, String[] stringsList )
	{
		for ( String listString : stringsList )
		{
			if( string.equalsIgnoreCase( listString ) )
			{
				return true;
			}
		}
		return false;
	}

	/* Lists of extensions that share an icon. */
	private static String[] WORD_EXTENSIONS = { ".doc", ".docx", ".docm",
			".wps" };
	private static String[] TEXT_EXTENSIONS = { ".asc", ".csv", ".rtf", ".txt" };
	private static String[] POWERPOINT_EXTENSIONS = { ".pps", ".ppt" };
}
