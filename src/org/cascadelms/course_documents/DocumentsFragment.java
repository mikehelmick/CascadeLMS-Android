package org.cascadelms.course_documents;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.R;
import org.cascadelms.data.loaders.DocumentsLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.sources.FakeDataSource;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A <code>Fragment</code> for displaying the files associated with a course.  
 */
public class DocumentsFragment extends ListFragment implements LoaderCallbacks<List<Document>>
{
	private DocumentAdapter adapter;
	private DocumentsDataSource dataSource;
	private int courseId;
	
	/* Constants */
	private static final String ARGS_COURSE_ID = "org.cascadelms.args_course_id";
	private static final String ARGS_COURSE_TITLE = "org.cascadelms.args_course_title";
	
	/**
	 * Creates a new instance of <code>DocumentsFragment</code> using the specified course id.
	 * @param courseId the database id of the course to pull data from.
	 * @return a <code>DocumentsFragment</code> instance
	 */
	public static DocumentsFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putInt( ARGS_COURSE_ID, course.getId() );
		args.putString( ARGS_COURSE_TITLE, course.getTitle() );
		DocumentsFragment instance =  new DocumentsFragment();
		instance.setArguments( args );
		return instance;
	}
	
	@Override
	public void onCreate( Bundle savedInstanceState ) 
	{
		/* Gets the course id from the arguments */
		this.courseId = this.getArguments().getInt( ARGS_COURSE_ID );
		LOGGER.info( "DocumentsFragment created for course #" + this.courseId );
		this.adapter = new DocumentAdapter( this.getActivity(), android.R.layout.simple_list_item_1 );
		
		/* Creates a data source and begins loading */
		this.dataSource = FakeDataSource.getInstance();
		this.getActivity().getSupportLoaderManager().initLoader( 0, null, this ).forceLoad();
		super.onCreate( savedInstanceState );
	}
	
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState )
    {
        super.onCreateView( inflater, container, savedInstanceState );
        View view = inflater.inflate( R.layout.fragment_documents, null );
        this.setListAdapter( adapter );
        return view;
    }

	@Override
	public Loader<List<Document>> onCreateLoader( int id, Bundle args ) 
	{
		switch( id )
		{
		case LoaderCodes.LOADER_CODE_DOCUMENTS:
		{
			LOGGER.info( "DocumentsFragment began loading Documents." );
			return new DocumentsLoader( this.getActivity(), this.dataSource, this.courseId );
		}
		default:
		{
			return null;
		}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<Document>> loader, List<Document> data ) 
	{	
		LOGGER.info( "DocumentsFragment finished loading Documents." );
		this.adapter.clear();
		this.adapter.addAll( data );
		if( data.isEmpty() )
		{
			this.setEmptyText( this.getString( R.string.fragment_document_list_empty_message ) );
		}
	}

	@Override
	public void onLoaderReset( Loader<List<Document>> loader )
	{
		this.adapter.clear();
	}
	
	/**
	 * An interface for providing Documents data.
	 */
	public interface DocumentsDataSource 
	{
		public List<Document> getDocumentsForCourse( int courseId );
	}
	
	private static Logger LOGGER = Logger.getLogger( DocumentsFragment.class.getName() );
}