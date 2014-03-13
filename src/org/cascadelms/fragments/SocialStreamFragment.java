package org.cascadelms.fragments;

import java.util.ArrayList;
import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.loaders.CourseStreamItemLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.data.sources.FakeDataSource;
import org.cascadelms.socialstream.SocialStreamAdapter;
import org.cascadelms.socialstream.SocialStreamPost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class SocialStreamFragment extends Fragment implements
		LoaderCallbacks<List<StreamItem>>
{
	private StreamDataSource streamDataSource;

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

		/* There are two loaders available to this Fragment. */
		/* This call initializes a Loader to load all StreamItems. */
		this.getActivity().getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_TOTAL_STREAM, null, this );
		/*
		 * This call initializes a Loader to load only stream items for this
		 * Fragment's Course
		 */
		// this.getActivity().getSupportLoaderManager()
		// .initLoader( LoaderCodes.LOADER_CODE_COURSE_STREAM, null, this );

		super.onCreate( savedInstanceState );
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

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );

		View view = inflater.inflate( R.layout.fragment_socialstream, null );

		ListView socialstreamList = (ListView) view
				.findViewById( R.id.socialstream_list );

		if( socialstreamList != null )
		{
			ArrayList<SocialStreamPost> postList = new ArrayList<SocialStreamPost>();

			for ( int i = 0; i < 10; ++i )
				postList.add( new SocialStreamPost() );

			socialstreamList.setAdapter( new SocialStreamAdapter( view
					.getContext(), postList ) );
			socialstreamList
					.setOnItemClickListener( new SocialStreamItemClickListener() );
		}

		return view;
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
			{
				/* TODO Use this data in the Fragment. */
				break;
			}
			case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
			{
				/* TODO Use this data in the Fragment. */
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
			{
				/*
				 * TODO Anything that uses data from this Loader needs to clear
				 * its data here.
				 */
				break;
			}
			case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
			{
				/*
				 * TODO Anything that uses data from this Loader needs to clear
				 * its data here.
				 */
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
		return this.getArguments().getParcelable( ARGS_COURSE );
	}
}
