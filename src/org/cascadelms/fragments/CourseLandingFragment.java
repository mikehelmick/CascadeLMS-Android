package org.cascadelms.fragments;

import java.util.logging.Logger;

import org.cascadelms.data.models.Course;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CourseLandingFragment extends Fragment
{

	/* Constants */
	private static final String ARGS_COURSE = "org.cascadelms.args_course";

	public static CourseLandingFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		CourseLandingFragment fragment = new CourseLandingFragment();
		fragment.setArguments( args );
		return fragment;
	}

	// @Override
	// public void onCreate( Bundle savedInstanceState )
	// {
	// ActionBar actionBar = ( (ActionBarActivity) this.getActivity() )
	// .getSupportActionBar();

	// @Override
	// public View onCreateView( LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState )
	// {
	// super.onCreateView( inflater, container, savedInstanceState );
	//
	// View view = inflater.inflate( R.layout.fragment_courselanding, null );
	//
	// if( view != null )
	// {
	//
	// } else
	// {
	// Log.e( getClass().getName(), "Could not retrieve view." );
	// }
	//
	// return view;
	// }

	/**
	 * Retrieves the {@link Course} provided as an argument to this Fragment.
	 * The value cannot be stored, because the Fragment may be recreated by the
	 * Fragment manager at any time.
	 * 
	 * @return the <code>Course</code>
	 */
	private Course getCourse()
	{
		return this.getArguments().getParcelable( ARGS_COURSE );
	}

	private static Logger LOGGER = Logger
			.getLogger( CourseLandingFragment.class.getName() );
}
