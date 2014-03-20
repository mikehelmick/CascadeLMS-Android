package org.cascadelms.data.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.R;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.models.Document.DocumentComparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DocumentAdapter extends BaseAdapter
{
	private ArrayList<Object> items;
	private Context context;

	/* Constants */
	private static final int ITEM_TYPE_DOCUMENT = 0;
	private static final int ITEM_TYPE_DIVIDER = 1;

	public DocumentAdapter( Context context )
	{
		this.items = new ArrayList<Object>();
		this.context = context;
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem( int position )
	{
		return items.get( position );
	}

	@Override
	public long getItemId( int position )
	{
		return position;
	}

	@Override
	public boolean isEnabled( int position )
	{
		{
			Object item = items.get( position );
			if( item instanceof Document )
			{
				return true;
			} else if( item instanceof Divider )
			{
				return false;
			} else
			{
				throw new IllegalStateException(
						"An item in the list was neither a Document nor Divider." );
			}
		}
	}

	@Override
	public int getItemViewType( int position )
	{
		Object item = items.get( position );
		if( item instanceof Document )
		{
			return ITEM_TYPE_DOCUMENT;
		} else if( item instanceof Divider )
		{
			return ITEM_TYPE_DIVIDER;
		} else
		{
			throw new IllegalStateException(
					"An item in the list was neither a Document nor Divider." );
		}
	}

	@Override
	public int getViewTypeCount()
	{
		return 2;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		LOGGER.info( "DocumentAdapter.getView() at " + position );
		switch( this.getItemViewType( position ) )
		{
			case ITEM_TYPE_DOCUMENT:
			{
				/* Re-uses the convertView, if possible. */
				TextView textView;
				if( convertView == null )
				{
					textView = (TextView) LayoutInflater.from( this.context )
							.inflate( R.layout.list_item_document, parent,
									false );
				} else
				{
					textView = (TextView) convertView;
				}
				Document document = (Document) this.getItem( position );

				/* Sets the list item text according to the Document's title. */
				textView.setText( document.getTitle() );

				/* Sets the icon according to the Document's filetype. */
				textView.setCompoundDrawablesWithIntrinsicBounds( this
						.getIconIdForExtension( document.getFileExtension() ),
						0, 0, 0 );
				return textView;
			}
			case ITEM_TYPE_DIVIDER:
			{
				TextView dividerView = (TextView) LayoutInflater.from(
						this.context ).inflate( R.layout.list_item_document,
						parent, false );
				dividerView
						.setText( ( (Divider) this.getItem( position ) ).title );
				return dividerView;
			}
			default:
			{
				throw new IllegalStateException(
						"Unrecognized item type in DocumentAdapter.getView()" );
			}
		}
	}

	private int getIconIdForExtension( String extension )
	{
		/* If the extension is null, the document is a directory. */
		if( extension == null )
			return R.drawable.fileicon_folder;
		if( this.stringInList( extension, WORD_EXTENSIONS ) )
			return R.drawable.fileicon_word;
		if( this.stringInList( extension, TEXT_EXTENSIONS ) )
			return R.drawable.fileicon_text;
		if( this.stringInList( extension, POWERPOINT_EXTENSIONS ) )
			return R.drawable.fileicon_powerpoint;
		if( this.stringInList( extension, COMPRESSED_EXTENSIONS ) )
			return R.drawable.fileicon_compressed;
		if( this.stringInList( extension, CODE_EXTENSIONS ) )
			return R.drawable.fileicon_code;
		if( this.stringInList( extension, EXCEL_EXTENSIONS ) )
			return R.drawable.fileicon_excel;
		if( this.stringInList( extension, HTML_EXTENSIONS ) )
			return R.drawable.fileicon_html;
		if( this.stringInList( extension, IMAGE_EXTENSIONS ) )
			return R.drawable.fileicon_image;
		if( this.stringInList( extension, MOVIE_EXTENSIONS ) )
			return R.drawable.fileicon_movie;
		if( this.stringInList( extension, MUSIC_EXTENSIONS ) )
			return R.drawable.fileicon_music;
		if( this.stringInList( extension, PDF_EXTENSIONS ) )
			return R.drawable.fileicon_pdf;

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

	public void setData( List<? extends Document> collection )
	{
		/* Sort the data and insert dividers. */
		items.clear();
		Collections.sort( collection, new DocumentComparator() );
		items.addAll( collection );
		if( items.isEmpty() )
		{
			/* Nothing to do on an empty list. */
			return;
		}

		int startingIndex = 0;
		/* Only insert the header if there are folders. */
		if( ( (Document) items.get( 0 ) ).isFolder() )
		{
			items.add( 0, new Divider( "Folders" ) ); // TODO use string
														// resource
			startingIndex = 1;
		}
		/* Finds the correct position to insert the Files divider. */
		for ( int i = startingIndex; i < items.size(); i++ )
		{
			if( ( (Document) items.get( i ) ).isFolder() )
			{
				continue;
			} else
			{
				items.add( i, new Divider( "Files" ) );
				break;
			}
		}
		this.notifyDataSetChanged();
	}

	public void clear()
	{
		items.clear();
	}

	/**
	 * A class inserted into the items list as a divider.
	 * 
	 */
	private class Divider
	{
		private String title;

		private Divider( String title )
		{
			this.title = title;
		}
	}

	/* Lists of extensions that share an icon. */
	private static String[] WORD_EXTENSIONS = { ".doc", ".docx", ".docm",
			".wps" };
	private static String[] TEXT_EXTENSIONS = { ".asc", ".csv", ".rtf", ".txt" };
	private static String[] POWERPOINT_EXTENSIONS = { ".pps", ".ppt" };
	private static String[] COMPRESSED_EXTENSIONS = { ".7z", ".apk", ".bz2",
			".gz", ".jar", ".rar", ".tar", ".tar.gz", ".zip" };
	private static String[] CODE_EXTENSIONS = { ".c", ".h", ".cpp", ".cc",
			".cxx", ".cbp", ".cs", ".e", ".hpp", ".hxx", ".hs", ".java",
			".lisp", ".m", ".pl", ".pm", ".py", ".rb" };
	private static String[] EXCEL_EXTENSIONS = { ".xls", ".xlsx" };
	private static String[] HTML_EXTENSIONS = { ".html", ".xhtml", ".mhtml" };
	private static String[] IMAGE_EXTENSIONS = { ".bmp", ".exif", ".gif",
			".ico", ".jpg", ".jpeg", ".png", ".tif", ".tiff" };
	private static String[] MOVIE_EXTENSIONS = { ".avi", ".mkv", ".mov",
			".mpeg", ".mpg", ".mpe", "wmv" };
	private static String[] MUSIC_EXTENSIONS = { ".aiff", ".wav", ".flac",
			".m4a", ".wma", ".mp3", ".aac" };
	private static String[] PDF_EXTENSIONS = { ".pdf" };

	private static Logger LOGGER = Logger.getLogger( DocumentAdapter.class
			.getName() );
}
