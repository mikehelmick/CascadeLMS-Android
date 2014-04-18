package org.cascadelms.fragments;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.R;
import org.cascadelms.SimpleOAuth;
import org.cascadelms.data.adapters.DocumentAdapter;
import org.cascadelms.data.loaders.DocumentsLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.sources.AuthTokenInfo;
import org.cascadelms.data.sources.CascadeDataSource;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A <code>Fragment</code> for displaying the files associated with a course.
 */
public class DocumentsFragment extends ListFragment implements
		LoaderCallbacks<List<Document>>, OnItemClickListener
{
	private DocumentAdapter adapter;
	private DocumentsDataSource dataSource;
	private TextView emptyView;
	private int courseId;
	private LinkedList<Document> directoryStack;

	/* Constants */
	private static final String ARGS_COURSE_ID = "org.cascadelms.args_course_id";
	private static final String ARGS_COURSE_TITLE = "org.cascadelms.args_course_title";

	/**
	 * Creates a new instance of <code>DocumentsFragment</code> using the
	 * specified course id.
	 * 
	 * @param course
	 *            the database id of the course to pull data from.
	 * @return a <code>DocumentsFragment</code> instance
	 */
	public static DocumentsFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putInt( ARGS_COURSE_ID, course.getId() );
		args.putString( ARGS_COURSE_TITLE, course.getTitle() );
		DocumentsFragment instance = new DocumentsFragment();
		instance.setArguments( args );
		return instance;
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		/* Gets the course id from the arguments */
		this.courseId = this.getArguments().getInt( ARGS_COURSE_ID );
		LOGGER.info( "DocumentsFragment created for course #" + this.courseId );
		this.adapter = new DocumentAdapter( this.getActivity() );

		/* Creates a data source and begins loading */
		this.dataSource = CascadeDataSource.getInstance();

		this.directoryStack = new LinkedList<Document>();
		this.directoryStack.add( Document.rootDocument() );
		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );
		View view = inflater.inflate( R.layout.fragment_documents, null );
		emptyView = (TextView) view
				.findViewById( R.id.fragment_documents_empty );
		this.setListAdapter( adapter );
		return view;
	}

	@Override
	public void onViewCreated( View view, Bundle savedInstanceState )
	{
		this.getActivity().getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_DOCUMENTS, null, this )
				.forceLoad();
		this.getListView().setOnItemClickListener( this );
		this.getListView().setEmptyView( emptyView );
		super.onViewCreated( view, savedInstanceState );
	}

	@Override
	public void onItemClick( AdapterView<?> parent, View view, int position,
			long id )
	{
		Document doc = (Document) adapter.getItem( position );
		if( doc.isFolder() )
		{
			LOGGER.info( "Got a click on a folder." );
			Bundle args = new Bundle();
			args.putParcelable( DocumentsLoader.ARGS_DIRECTORY, doc );
			this.getActivity()
					.getSupportLoaderManager()
					.initLoader( LoaderCodes.LOADER_CODE_DOCUMENT_DIRECTORY,
							args, this ).forceLoad();
			this.changeDirectory( doc );
		} else
		{
			LOGGER.info( "Got a click on a file." );
			this.downloadFile( doc );
		}
	}

	@Override
	public void setEmptyText( CharSequence text )
	{
		/*
		 * Subclasses using custom layouts must override this method to operate
		 * on the correct TextView.
		 */
		emptyView.setText( text );
	}

	@Override
	public Loader<List<Document>> onCreateLoader( int id, Bundle args )
	{
		switch( id )
		{
			case LoaderCodes.LOADER_CODE_DOCUMENTS:
			{
				LOGGER.info( "DocumentsFragment began loading Documents." );
				this.setEmptyText( this
						.getString( R.string.fragment_documents_list_loading_message ) );
				return new DocumentsLoader( this.getActivity(),
						this.dataSource, this.courseId, null );
			}
			case LoaderCodes.LOADER_CODE_DOCUMENT_DIRECTORY:
			{
				LOGGER.info( "DocumentsFragment began loading directory." );
				this.setEmptyText( this
						.getString( R.string.fragment_documents_list_loading_message ) );
				return new DocumentsLoader( this.getActivity(),
						this.dataSource, this.courseId,
						(Document) args
								.getParcelable( DocumentsLoader.ARGS_DIRECTORY ) );
			}
			default:
			{
				return null;
			}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<Document>> loader,
			List<Document> data )
	{
		LOGGER.info( "A documents loader finished." );
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_DOCUMENTS:
			{
				LOGGER.info( "DocumentsFragment finished loading root directory." );
                if (data != null)
                    this.adapter.setData( data );
                else
                    Toast.makeText(getActivity(), R.string.fragment_documents_list_failed,
                            Toast.LENGTH_SHORT).show();
				this.setEmptyText( this
						.getString( R.string.fragment_documents_list_empty_message ) );
				return;
			}
			case LoaderCodes.LOADER_CODE_DOCUMENT_DIRECTORY:
			{
				LOGGER.info( "DocumentsFragment finished loading directory." );
				this.adapter.setData( data );
				this.setEmptyText( this
						.getString( R.string.fragment_documents_list_empty_message ) );
				return;
			}
		}
	}

	@Override
	public void onLoaderReset( Loader<List<Document>> loader )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_DOCUMENTS:
			{
				this.adapter.clear();
				return;
			}
			case LoaderCodes.LOADER_CODE_DOCUMENT_DIRECTORY:
			{
				this.adapter.clear();
				return;
			}
		}
	}

	private void upDirectory()
	{
		directoryStack.removeFirst();
		LOGGER.info( "Changed directory to "
				+ directoryStack.getFirst().getTitle() );
		/* Update the breadcrumb. */
		// TODO
	}

	private void changeDirectory( Document dir )
	{
		/* Add dir to the breadcrumb. */
		LOGGER.info( "Changed directory to " + dir.getTitle() );
		directoryStack.addFirst( dir );
	}

	/**
	 * Queries whether the DownloadManager is available at the current API
	 * level.
	 * <p>
	 * Thanks to Stack Overflow for this code.<br>
	 * http://stackoverflow.com/questions
	 * /3028306/download-a-file-with-android-and
	 * -showing-the-progress-in-a-progressdialog
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isDownloadManagerAvailable( Context context )
	{
		try
		{
			if( Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD )
			{
				return false;
			}
			Intent intent = new Intent( Intent.ACTION_MAIN );
			intent.addCategory( Intent.CATEGORY_LAUNCHER );
			intent.setClassName( "com.android.providers.downloads.ui",
					"com.android.providers.downloads.ui.DownloadList" );
			List<ResolveInfo> list = context.getPackageManager()
					.queryIntentActivities( intent,
							PackageManager.MATCH_DEFAULT_ONLY );
			return list.size() > 0;
		} catch( Exception e )
		{
			return false;
		}
	}

	@SuppressLint( "NewApi" )
	// TODO Will need another way to download files on lower API levels.
	public void downloadFile( Document document )
	{
        if ( !document.isExternal() )
        {
            if (isDownloadManagerAvailable(this.getActivity()))
            {
                AuthTokenInfo tokenInfo = new AuthTokenInfo(getActivity());

                DownloadManager.Request request = new DownloadManager.Request(
                        Uri.parse(document.getDocumentURL().toString()));
                request.setTitle(document.getTitle());
                request.addRequestHeader(SimpleOAuth.OAUTH_TOKEN, tokenInfo.getAuthToken());

                // in order for this if to run, you must use the android 3.2 to
                // compile
                // your app
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                }

			/* Removes the first '/' from the URL's file string. */
                request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS, document.getFileName());

                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) this.getActivity()
                        .getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
            } else
            {
                Toast.makeText(this.getActivity(),
                        "DownloadManager is not available.", Toast.LENGTH_LONG)
                        .show();
            }
        }
        else
        {
            // Open external URLs in the user's default browser.
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(document.getDocumentURL()));
            startActivity(intent);
        }
	}

	/**
	 * An interface for providing Documents data.
	 */
	public interface DocumentsDataSource
	{
		public List<Document> getDocumentsForCourse( int courseId );

		public List<Document> getDocumentsInFolder( int courseId, Document folder );
	}

	private static Logger LOGGER = Logger.getLogger( DocumentsFragment.class
			.getName() );
}
