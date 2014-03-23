package org.cascadelms.fragments;

import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.adapters.StreamItemAdapter;
import org.cascadelms.data.loaders.CourseStreamItemLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.data.sources.FakeDataSource;
import org.cascadelms.socialstream.SocialStreamPost;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SocialStreamFragment extends ListFragment implements
		LoaderCallbacks<List<StreamItem>>
{
	private StreamDataSource streamDataSource;
    private StreamItemAdapter adapter;
    private TextView emptyView;

	private static final String ARGS_COURSE = "org.cascadelms.args_course";

	public static SocialStreamFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		SocialStreamFragment fragment = new SocialStreamFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		/* Initializes a data source and begins loading. */
		this.streamDataSource = FakeDataSource.getInstance();
        this.adapter = new StreamItemAdapter(getActivity());

		super.onCreate( savedInstanceState );
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        /* There are two loaders available to this Fragment. */

        if (this.getCourse() == null)
            /* This call initializes a Loader to load all StreamItems. */
            this.getActivity().getSupportLoaderManager()
                .initLoader( LoaderCodes.LOADER_CODE_TOTAL_STREAM, null, this ).forceLoad();
        else
		/*
		 * This call initializes a Loader to load only stream items for this
		 * Fragment's Course
		 */
            this.getActivity().getSupportLoaderManager()
                .initLoader(LoaderCodes.LOADER_CODE_COURSE_STREAM, null, this).forceLoad();

        this.getListView().setAdapter( adapter );
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {
        super.onCreateView( inflater, container, savedInstanceState );

        View view = inflater.inflate( R.layout.fragment_socialstream, null );
        this.emptyView = (TextView) view
                .findViewById( R.id.fragment_socialstream_empty );
        ( (ListView) view.findViewById( android.R.id.list ) )
                .setEmptyView( emptyView );

        return view;
    }

    private class SocialStreamItemClickListener implements
			AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick( AdapterView<?> parent, View view,
				int position, long id )
		{
			// TODO: Supply post object
			goToPostDetail( null );
		}
	}

	private void goToPostDetail( SocialStreamPost parentPost )
	{
		/*
		 * TODO Modifying the parent Activity's layout is no longer an option.
		 */
		// ActionBarActivity activity = (ActionBarActivity) getActivity();
		// FragmentManager manager = activity.getSupportFragmentManager();
		// FragmentTransaction transaction = manager.beginTransaction();
		//
		// SocialStreamDetailFragment fragment = new
		// SocialStreamDetailFragment();
		//
		// transaction.replace( R.id.content_frame, fragment );
		// transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN
		// );
		//
		// // Add to back stack but only if this is not the home fragment.
		// transaction.addToBackStack( null );
		//
		// transaction.commit();
	}

	@Override
	public Loader<List<StreamItem>> onCreateLoader( int id, Bundle args )
	{
		switch( id )
		{
			case LoaderCodes.LOADER_CODE_COURSE_STREAM:
			{
				return new CourseStreamItemLoader( this.getActivity(),
						streamDataSource, this.getCourse().getId() );
			}
			case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
			{
				return new CourseStreamItemLoader( this.getActivity(),
						streamDataSource );
			}
			default:
			{
				return null;
			}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<StreamItem>> loader,
			List<StreamItem> data )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_COURSE_STREAM:
			case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
			{
                adapter.clear();
                adapter.addAll(data);
                this.emptyView
                        .setText( R.string.fragment_socialstream_list_empty_message );
				break;
			}
		}

	}

	@Override
	public void onLoaderReset( Loader<List<StreamItem>> loader )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_COURSE_STREAM:
			case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
			{
                adapter.clear();
				break;
			}
		}
	}

	public interface StreamDataSource
	{
		public List<StreamItem> getAllStreamItems();

		public List<StreamItem> getStreamItemsForCourse( int courseId );
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
        if (this.getArguments() == null)
            return null;
        else
            return this.getArguments().getParcelable( ARGS_COURSE );
    }
}
