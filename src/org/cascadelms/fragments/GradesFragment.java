package org.cascadelms.fragments;

import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.adapters.GradesAdapter;
import org.cascadelms.data.loaders.GradesLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Grade;
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

public class GradesFragment extends ListFragment implements
		LoaderCallbacks<List<Grade>>
{
	private GradesDataSource dataSource;
	private GradesAdapter adapter;
	private TextView emptyView;

	/* Constants */
	private static final String ARGS_COURSE = "org.cascadelms.args_course";

	public static GradesFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		GradesFragment fragment = new GradesFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		this.dataSource = FakeDataSource.getInstance();
		this.adapter = new GradesAdapter( this.getActivity() );
		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );

		View view = inflater.inflate( R.layout.fragment_grades, null );
		this.emptyView = (TextView) view
				.findViewById( R.id.fragment_grades_empty );
		( (ListView) view.findViewById( android.R.id.list ) )
				.setEmptyView( emptyView );
		return view;
	}

	@Override
	public void onViewCreated( View view, Bundle savedInstanceState )
	{
		this.getActivity().getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_GRADES, null, this )
				.forceLoad();
		this.getListView().setAdapter( adapter );
		super.onViewCreated( view, savedInstanceState );
	}

	@Override
	public Loader<List<Grade>> onCreateLoader( int id, Bundle args )
	{
		switch( id )
		{
			case LoaderCodes.LOADER_CODE_GRADES:
			{
				emptyView
						.setText( R.string.fragment_grades_list_loading_message );
				return new GradesLoader( this.getActivity(), this.dataSource,
						this.getCourse().getId() );
			}
			default:
			{
				return null;
			}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<Grade>> loader, List<Grade> data )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_GRADES:
			{
				this.adapter.clear();
				this.adapter.addAll( data );
				this.emptyView
						.setText( R.string.fragment_grades_list_empty_message );
				return;
			}
		}
	}

	@Override
	public void onLoaderReset( Loader<List<Grade>> loader )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_GRADES:
			{
				this.adapter.clear();
				return;
			}
		}
	}

	public interface GradesDataSource
	{
		public List<Grade> getGradesForCourse( int courseId );
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
