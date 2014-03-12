package org.cascadelms.course_documents;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.R;
import org.cascadelms.data_models.Document;

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
	
	/**
	 * Creates a new instance of <code>DocumentsFragment</code> using the specified course id.
	 * @param courseId the database id of the course to pull data from.
	 * @return a <code>DocumentsFragment</code> instance
	 */
	public static DocumentsFragment newInstance( int courseId )
	{
		Bundle args = new Bundle();
		args.putInt( ARGS_COURSE_ID, courseId );
		DocumentsFragment instance =  new DocumentsFragment();
		instance.setArguments( args );
		return instance;
	}
	
	@Override
	public void onCreate( Bundle savedInstanceState ) 
	{
		/* Gets the course id from the arguments */
		this.courseId = this.getArguments().getInt( ARGS_COURSE_ID );
		
		/* Creates a data source */
		// TODO
		super.onCreate( savedInstanceState );
	}
	
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState )
    {
        super.onCreateView( inflater, container, savedInstanceState );
        View view = inflater.inflate( R.layout.fragment_documents, null );
        return view;
    }

	@Override
	public Loader<List<Document>> onCreateLoader( int id, Bundle args ) 
	{
		return new DocumentsLoader( this.getActivity(), this.dataSource, this.courseId );
	}

	@Override
	public void onLoadFinished( Loader<List<Document>> loader, List<Document> data ) 
	{	
		LOGGER.info( "DocumentsFragment finished loading Documents." );
		this.adapter.clear();
		this.adapter.addAll( data );
	}

	@Override
	public void onLoaderReset( Loader<List<Document>> loader )
	{
		this.adapter.clear();
	}
	
	private static Logger LOGGER = Logger.getLogger( DocumentsFragment.class.getName() );
}
