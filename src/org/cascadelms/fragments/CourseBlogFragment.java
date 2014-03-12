package org.cascadelms.fragments;

import org.cascadelms.R;
import org.cascadelms.data.models.Course;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CourseBlogFragment extends Fragment
{
	private static final String ARGS_COURSE = "org.cascadelms.args_course";

	public static CourseBlogFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		CourseBlogFragment fragment = new CourseBlogFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );

		View view = inflater.inflate( R.layout.fragment_courseblog, null );
		TextView label = (TextView) view.findViewById( R.id.placeholder );

		label.append( " " + this.getCourse().getId() );

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
}
