package org.cascadelms.fragments;

import java.util.List;

import org.cascadelms.R;
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

public class AssignmentsFragment extends ListFragment implements
		LoaderCallbacks<List<Assignment>>
{
	private AssignmentsDataSource assigmentsDataSource;

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
		this.getActivity().getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_ASSIGNMENTS, null, this )
				.forceLoad();

		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );
		View view = inflater.inflate( R.layout.fragment_assignments, container,
				false );
		return view;
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

	@Override
	public Loader<List<Assignment>> onCreateLoader( int id, Bundle args )
	{
		switch( id )
		{
			case LoaderCodes.LOADER_CODE_ASSIGNMENTS:
			{
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
				/* TODO Use this data in the Fragment. */
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
				/*
				 * TODO Anything that uses data from the Loader needs to clear
				 * its data here.
				 */
				break;
			}
		}
	}

	public interface AssignmentsDataSource
	{
		public List<Assignment> getAssignmentsForCourse( int courseId );
	}
}
