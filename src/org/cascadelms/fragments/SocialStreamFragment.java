package org.cascadelms.fragments;

import java.util.ArrayList;

import org.cascadelms.R;
import org.cascadelms.data.models.Course;
import org.cascadelms.socialstream.SocialStreamAdapter;
import org.cascadelms.socialstream.SocialStreamPost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class SocialStreamFragment extends Fragment
{
	private static final String ARGS_COURSE = "org.cascadelms.args_course";

	public static SocialStreamFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		SocialStreamFragment fragment = new SocialStreamFragment();
		fragment.setArguments( args );
		return fragment;
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
		 * TODO Should send an intent to open DetailActivity Modifying the
		 * parent Activity's layout is no longer an option.
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
