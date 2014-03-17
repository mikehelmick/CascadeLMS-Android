package org.cascadelms.fragments;

import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.adapters.AssignmentsAdapter;
import org.cascadelms.data.loaders.AssignmentsLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Assignment;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.sources.FakeDataSource;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AssignmentsFragment extends ListFragment implements
		LoaderCallbacks<List<Assignment>>
{
	private AssignmentsDataSource assigmentsDataSource;
	private AssignmentsAdapter adapter;
	private TextView emptyView;

	/* Constants */
	private static final String ARGS_COURSE = "org.cascade.args_course";

	public static AssignmentsFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		AssignmentsFragment fragment = new AssignmentsFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		/* Creates the data source and begins loading. */
		this.assigmentsDataSource = FakeDataSource.getInstance();
		this.adapter = new AssignmentsAdapter( this.getActivity() );
		this.setListAdapter( adapter );

		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );
		View view = inflater.inflate( R.layout.fragment_assignments, container,
				false );

		emptyView = (TextView) view
				.findViewById( R.id.fragment_assignments_empty );
		( (ListView) view.findViewById( android.R.id.list ) )
				.setEmptyView( emptyView );
		return view;
	}

	@Override
	public void onViewCreated( View view, Bundle savedInstanceState )
	{
		this.getActivity().getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_ASSIGNMENTS, null, this )
				.forceLoad();
		super.onViewCreated( view, savedInstanceState );
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
	public Loader<List<Assignment>> onCreateLoader( int id, Bundle args )
	{
		switch( id )
		{
			case LoaderCodes.LOADER_CODE_ASSIGNMENTS:
			{
				this.setEmptyText( this
						.getString( R.string.fragment_assignments_list_loading_message ) );
				return new AssignmentsLoader( this.getActivity(),
						assigmentsDataSource, this.getCourse().getId() );
			}
			default:
			{
				return null;
			}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<Assignment>> loader,
			List<Assignment> data )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_ASSIGNMENTS:
			{
				this.adapter.clear();
				this.adapter.addAll( data );
				this.setEmptyText( this
						.getString( R.string.fragment_assignments_list_empty_message ) );
				break;
			}
		}

	}

	@Override
	public void onLoaderReset( Loader<List<Assignment>> loader )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_ASSIGNMENTS:
			{
				this.adapter.clear();
				break;
			}
		}
	}

	public interface AssignmentsDataSource
	{
		public List<Assignment> getAssignmentsForCourse( int courseId );
	}

	/**
	 * Retrieves the {@link Course} provided as an argument to this Fragment.
	 * The value cannot be stored, because the Fragment may be recreated by the
	 * FragmentManager at any time.
	 * 
	 * @return the <code>Course</code>
	 */
	private Course getCourse()
	{
		return this.getArguments().getParcelable( ARGS_COURSE );
	}

}
