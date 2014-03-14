package org.cascadelms.course_documents;

import java.util.Collection;

import org.cascadelms.R;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.models.Document.DocumentComparator;

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

		/* Sets the list item text according to the Document's title. */
		textView.setText( document.getTitle() );

		/* Sets the icon according to the Document's filetype. */
		textView.setCompoundDrawablesWithIntrinsicBounds(
				this.getIconIdForExtension( document.getFileExtension() ), 0,
				0, 0 );
		return textView;
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

	@Override
	public void addAll( Collection<? extends Document> collection )
	{
		/* Sort after adding a collection of data. */
		for ( Document doc : collection )
		{
			this.add( doc );
		}
		this.sort( new DocumentComparator() );
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
}
