package org.cascadelms.fragments;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.R;
import org.cascadelms.data.adapters.DocumentAdapter;
import org.cascadelms.data.loaders.DocumentsLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.models.Folder;
import org.cascadelms.data.sources.FakeDataSource;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

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
	private Folder rootFolder;

	/* Constants */
	private static final String ARGS_COURSE_ID = "org.cascadelms.args_course_id";
	private static final String ARGS_COURSE_TITLE = "org.cascadelms.args_course_title";

	/**
	 * Creates a new instance of <code>DocumentsFragment</code> using the
	 * specified course id.
	 * 
	 * @param courseId
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
		this.dataSource = FakeDataSource.getInstance();
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

		if( ( (Document) adapter.getItem( position ) ).isFolder() )
		{
			LOGGER.info( "Got a click on a folder." );
		} else
		{
			LOGGER.info( "Got a click on a file." );
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
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_DOCUMENTS:
			{
				LOGGER.info( "DocumentsFragment finished loading root directory." );
				this.rootFolder = Folder.createRootFolder( data );
				this.adapter.setData( rootFolder.getContents() );
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
		}

	}

	/**
	 * An interface for providing Documents data.
	 */
	public interface DocumentsDataSource
	{
		public List<Document> getDocumentsForCourse( int courseId );

		public List<Document> getDocumentsInFolder( Document folder );
	}

	private static Logger LOGGER = Logger.getLogger( DocumentsFragment.class
			.getName() );
}
