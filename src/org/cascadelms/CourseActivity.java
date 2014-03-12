package org.cascadelms;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.course_documents.DocumentsFragment;
import org.cascadelms.data.models.Course;
import org.cascadelms.fragments.AssignmentsFragment;
import org.cascadelms.fragments.CourseBlogFragment;
import org.cascadelms.fragments.GradesFragment;
import org.cascadelms.fragments.SocialStreamFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * An Activity for showing the sub-page Fragments related to a Course.
 */
public class CourseActivity extends ActionBarActivity implements TabListener,
		ListView.OnItemClickListener
{
	private List<Course> courses;
	private Course selectedCourse;
	private ViewPager mViewPager;
	private int[] mTabTitleIdArray = { R.string.fragment_socialstream,
			R.string.fragment_courseblog, R.string.fragment_documents,
			R.string.fragment_assignments, R.string.fragment_grades };
	private CourseNavAdapter mCourseNavAdapter;
	private ListView mDrawerList;
	private DrawerLayout mDrawer;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.activity_course );

		/* Gets the Course data provided by the Intent that started this. */
		Bundle extras = this.getIntent().getExtras();
		this.selectedCourse = extras
				.getParcelable( StreamActivity.ACTIVITY_STREAM_EXTRAS_SELECTED_COURSE );
		this.courses = extras
				.getParcelableArrayList( StreamActivity.ACTIVITY_STREAM_EXTRAS_COURSE_LIST );
		LOGGER.info( "Created CourseActivity for course #"
				+ selectedCourse.getId() );

		/* Creates the ViewPager */
		mViewPager = (ViewPager) this.findViewById( R.id.activity_course_pager );
		mViewPager.setAdapter( new CourseFragmentAdapter( this
				.getSupportFragmentManager() ) );
		mViewPager
				.setOnPageChangeListener( new ViewPager.SimpleOnPageChangeListener()
				{
					@Override
					public void onPageSelected( int position )
					{
						CourseActivity.this.getSupportActionBar()
								.setSelectedNavigationItem( position );
					}
				} );

		/* Creates the ActionBar tabs */
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
		for ( int id : mTabTitleIdArray )
		{
			actionBar.addTab( actionBar.newTab().setText( getString( id ) )
					.setTabListener( this ) );
		}

		/* Sets the home button to navigate back to the StreamActivity. */
		this.getSupportActionBar().setDisplayHomeAsUpEnabled( true );

		/* Creates the navigation drawer. */
		mCourseNavAdapter = new CourseNavAdapter( this );
		mCourseNavAdapter.addAll( courses );
		mDrawerList = (ListView) findViewById( R.id.activity_course_drawer );
		mDrawerList.setAdapter( mCourseNavAdapter );
		mDrawerList.setOnItemClickListener( this );
		mDrawer = (DrawerLayout) this
				.findViewById( R.id.activity_course_drawer_layout );

		/* Sets the title of the ActionBar to the selected Course's title. */
		actionBar.setTitle( this.selectedCourse.getTitle() );
	}

	@Override
	public void onTabSelected( ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction )
	{
		mViewPager.setCurrentItem( tab.getPosition() );
	}

	@Override
	public void onTabUnselected( ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction )
	{
	}

	@Override
	public void onTabReselected( ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction )
	{
	}

	@Override
	public void onItemClick( AdapterView<?> parent, View view, int position,
			long id )
	{
		LOGGER.info( "Press on course " + id
				+ " in the CourseActivity nav drawer." );
		this.startCourseActivity( mCourseNavAdapter.getItem( position ) );
		mDrawer.closeDrawers();
	}

	/**
	 * Packages the chosen Course in an intent and starts CourseActivity with
	 * it.
	 * 
	 * @param course
	 *            the selected Course
	 */
	private void startCourseActivity( Course course )
	{
		Intent intent = new Intent( this, CourseActivity.class );
		intent.putExtra( StreamActivity.ACTIVITY_STREAM_EXTRAS_SELECTED_COURSE,
				course );
		intent.putExtra( StreamActivity.ACTIVITY_STREAM_EXTRAS_COURSE_LIST,
				this.mCourseNavAdapter.getAllCourses() );
		this.startActivity( intent );
	}

	private class CourseFragmentAdapter extends FragmentPagerAdapter
	{
		private ArrayList<Fragment> fragments;

		public CourseFragmentAdapter( FragmentManager manager )
		{
			super( manager );
			this.fragments = new ArrayList<Fragment>();
			// TODO change these to create the Fragments using their static
			// methods.
			fragments.add( new SocialStreamFragment() );
			fragments.add( new CourseBlogFragment() );
			fragments.add( DocumentsFragment
					.newInstance( CourseActivity.this.selectedCourse ) );
			fragments.add( AssignmentsFragment
					.newInstance( CourseActivity.this.selectedCourse ) );
			fragments.add( new GradesFragment() );
		}

		@Override
		public Fragment getItem( int position )
		{
			return this.fragments.get( position );
		}

		@Override
		public int getCount()
		{
			return this.fragments.size();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu( Menu menu )
	// {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// // getMenuInflater().inflate( R.menu.main, menu );
	// return true;
	// }

	// @Override
	// protected void onResumeFragments()
	// {
	// super.onResumeFragments();
	// // (Re)update upon device orientation change
	// // updateActionBar();
	// }

	// private void goUp()
	// {
	// FragmentManager manager = getSupportFragmentManager();
	//
	// manager.popBackStack();
	// }

	// private void setCourseId( Course course )
	// {
	// FragmentManager manager = getSupportFragmentManager();
	//
	// Fragment fragment = null;
	// FragmentTransaction transaction = manager.beginTransaction();
	//
	// // "No course" - show main Social Stream.
	// // if( course.isHome() )
	// // {
	// // fragment = new SocialStreamFragment();
	// //
	// // // Clear backstack.
	// // manager.popBackStack( null,
	// // FragmentManager.POP_BACK_STACK_INCLUSIVE );
	// // }
	// // We have a course, show the course landing fragment.
	// // else
	// // {
	// // fragment = CourseLandingFragment.newInstance( course );
	// //
	// // // Back navigation only if we aren't on the "home" fragment
	// // transaction.addToBackStack( BACKTAG_COURSELANDING );
	// // }
	//
	// // TODO change fragment argument passing.
	// // Bundle bundle = new Bundle();
	// // bundle.putInt("courseId", courseId);
	// // fragment.setArguments(bundle);
	//
	// transaction.replace( R.id.content_frame, fragment );
	// transaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
	//
	// transaction.commit();
	// }

	// private void updateActionBar()
	// {
	// //
	// // ActionBar actionbar = getSupportActionBar();
	// //
	// }

	private static Logger LOGGER = Logger.getLogger( CourseActivity.class
			.getName() );
}
