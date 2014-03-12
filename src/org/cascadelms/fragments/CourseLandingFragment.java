package org.cascadelms.fragments;

import java.util.logging.Logger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cascadelms.R;
import org.cascadelms.course_documents.DocumentsFragment;
import org.cascadelms.data.models.Course;

public class CourseLandingFragment extends Fragment
{
    ViewPager mViewPager = null;
    
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

    public void switchView(int position)
    {
        if (mViewPager != null)
            mViewPager.setCurrentItem(position);
    }

    private class CourseFragmentAdapter extends FragmentPagerAdapter
    {
    	/* TODO Use an ArrayList<Fragment> to manage the Fragments. */
    	
        public CourseFragmentAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;

            switch (position)
            {
                case 0: fragment = new SocialStreamFragment(); break;
                case 1: fragment = new CourseBlogFragment(); break;
                case 2: return DocumentsFragment.newInstance( CourseLandingFragment.this.getCourse() );
                case 3: return AssignmentsFragment.newInstance( CourseLandingFragment.this.getCourse() );
                case 4: fragment = new GradesFragment(); break;
            }

            Bundle bundle = new Bundle();
            bundle.putInt( "courseId", CourseLandingFragment.this.getCourse().getId() );
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount()
        {
            return 5;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_courselanding,
                null);

        if (view != null)
        {
            mViewPager = (ViewPager) view.findViewById(R.id.pager);

            mViewPager.setAdapter(new CourseFragmentAdapter(getChildFragmentManager()));
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
            {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position)
                {
                    try
                    {
                        ActionBarActivity activity = (ActionBarActivity) getActivity();
                        ActionBar actionbar = activity.getSupportActionBar();

                        if (actionbar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS)
                            actionbar.setSelectedNavigationItem(position);
                    }
                    catch (NullPointerException e)
                    {
                        Log.e(getClass().getName(), "Could not retrieve ActionBar.");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }
        else
        {
            Log.e(getClass().getName(), "Could not retrieve view.");
        }

        return view;
    }
    
    /**
     * Retrieves the {@link Course} provided as an argument to this Fragment.  The value cannot be stored, 
     * because the Fragment may be recreated by the Fragment manager at any time.  
     * @return the <code>Course</code>
     */
    private Course getCourse()
    {
    	return this.getArguments().getParcelable( ARGS_COURSE );
    }
    
    private static Logger LOGGER = Logger.getLogger( CourseLandingFragment.class.getName() );
}
