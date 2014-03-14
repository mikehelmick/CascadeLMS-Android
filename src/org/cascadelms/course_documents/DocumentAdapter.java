package org.cascadelms.course_documents;

import java.util.logging.Logger;

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
		textView.setText( document.getTitle() );
		textView.setCompoundDrawablesWithIntrinsicBounds(
				this.getIconIdForExtension( document.getFileExtension() ), 0,
				0, 0 );
		return textView;
	}

	private int getIconIdForExtension( String extension )
	{
		/* TODO Attempt to match the string to a list of extensions. */

		// Drawable draw = resources.getDrawable( R.drawable.fileicon_bg );
		// LOGGER.warning( "Drawable was " + draw.isVisible() );

		return R.drawable.fileicon_bg;
	}

	Logger LOGGER = Logger.getLogger( DocumentAdapter.class.getName() );
}
