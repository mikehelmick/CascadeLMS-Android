package org.cascadelms;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.data.loaders.CourseLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.sources.FakeDataSource;
import org.cascadelms.fragments.CourseLandingFragment;
import org.cascadelms.fragments.SocialStreamFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity is the container for each fragment that will be displayed in the
 * Cascade app.
 */
public class MainActivity extends ActionBarActivity implements
        FragmentManager.OnBackStackChangedListener, ActionBar.TabListener, LoaderCallbacks<List<Course>>
{
    private static final String PREFS_AUTH = "AuthenticationData";
    private static final int COURSE_NONE = -1;
    private static final String BACKTAG_COURSELANDING = "CourseLanding";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private CourseNavAdapter mCourseNavAdapter;
    
    private CourseDataSource courseDataSource;

    private int[] mTabFragmentIdList =
    {
        R.string.fragment_socialstream,
        R.string.fragment_courseblog,
        R.string.fragment_documents,
        R.string.fragment_assignments,
        R.string.fragment_grades
    };

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
//        FragmentManager manager = getSupportFragmentManager();
//
//        CourseLandingFragment landingFragment = CourseLandingFragment.newInstance();
//
//        if (landingFragment != null)
//            landingFragment.switchView(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    // Used to map and list courses and subpages.
    private class CourseNavAdapter extends ArrayAdapter<Course>
    {
        public CourseNavAdapter(Context context )
        {
        	super( context, android.R.layout.simple_list_item_1 );
        	this.add( Course.HOME );
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent )
        {
        	/* Inflates a new view if the adapter doesn't provide one to reuse. */
            if( convertView == null )
            {
            	convertView = LayoutInflater.from( this.getContext() )
            			.inflate( android.R.layout.simple_list_item_1, parent, false );
            }

            TextView label = (TextView) convertView.findViewById( android.R.id.text1 );

            if (position == Course.HOME.getId() )
            {
                label.setText( this.getContext().getString( R.string.course_navigation_home ));
            }
            else
            {
                label.setText(this.getItem( position ).getTitle() );
            }

            return convertView;
        }
        
        @Override
        public void clear() 
        {
        	super.clear();
        	/* Ensures that HOME always appears in the list. */
        	this.add( Course.HOME );
        }
    }

    private class CourseNavItemClickListener implements
            ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id)
        {
            Course newCourse = (Course) mDrawerList.getItemAtPosition(position);

            setCourseId( newCourse );

            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        /* Setup CourseDataSource and begin course loading. */
        this.courseDataSource = FakeDataSource.getInstance();
        this.getSupportLoaderManager().initLoader( 0, null, this ).forceLoad();

        // TODO: Make this better.
        SharedPreferences preferences = getSharedPreferences(PREFS_AUTH, 0);
        boolean loggedIn = preferences.getBoolean("loggedIn", false);

        if (loggedIn)
            setupNavigation(savedInstanceState);
        else
            openLoginActivity();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId())
        {
        case android.R.id.home:
            goUp();
            return true;
        case R.id.action_logout:
            logOut();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackStackChanged()
    {
        // Update upon navigation
        updateActionBar();
    }

    @Override
    protected void onResumeFragments()
    {
        super.onResumeFragments();
        // (Re)update upon device orientation change
        updateActionBar();
    }

    private void setupNavigation(Bundle savedInstanceState)
    {
        // Needed to update subtitle in ActionBar when fragments change.
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        

        mCourseNavAdapter = new CourseNavAdapter( this );

        mDrawerList.setAdapter( mCourseNavAdapter );
        mDrawerList.setOnItemClickListener(new CourseNavItemClickListener());

        // Set up drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ActionBar actionbar = getSupportActionBar();

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        for (int i = 0; i < mTabFragmentIdList.length; ++i)
        {
            ActionBar.Tab tab = actionbar.newTab();

            tab.setText(getString(mTabFragmentIdList[i])).setTabListener(this);

            actionbar.addTab(tab);
        }

        // Jump to home.
        if (savedInstanceState == null)
            setCourseId( Course.HOME );
    }

    private void logOut()
    {
        // TODO: Make this better.
        SharedPreferences preferences = getSharedPreferences(PREFS_AUTH, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("loggedIn", false);
        editor.commit();

        Toast.makeText(getApplicationContext(), R.string.toast_logout,
                Toast.LENGTH_SHORT).show();

        openLoginActivity();
    }

    private void openLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);

        // Make Back leave the app.
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
    
    private void goUp()
    {
        FragmentManager manager = getSupportFragmentManager();

        manager.popBackStack();
    }

    private void setCourseId( Course course )
    {
        FragmentManager manager = getSupportFragmentManager();

        Fragment fragment = null;
        FragmentTransaction transaction = manager.beginTransaction();

        // "No course" - show main Social Stream.
        if ( course.isHome() )
        {
            fragment = new SocialStreamFragment();

            // Clear backstack.
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        // We have a course, show the course landing fragment.
        else
        {
            fragment = CourseLandingFragment.newInstance( course );

            // Back navigation only if we aren't on the "home" fragment
            transaction.addToBackStack(BACKTAG_COURSELANDING);
        }

        //TODO change fragment argument passing.
//        Bundle bundle = new Bundle();
//        bundle.putInt("courseId", courseId);
//        fragment.setArguments(bundle);

        transaction.replace(R.id.content_frame, fragment);
        transaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.commit();
    }

    private void updateActionBar()
    {
        FragmentManager manager = getSupportFragmentManager();

        Fragment fragment = manager.findFragmentById(R.id.content_frame);

        mDrawerToggle.setDrawerIndicatorEnabled(fragment instanceof SocialStreamFragment
                || fragment instanceof CourseLandingFragment);

        ActionBar actionbar = getSupportActionBar();

        // Toggle the use of tabs.
        if (fragment instanceof CourseLandingFragment)
        {
            actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
        else if (actionbar.getNavigationMode() != ActionBar.NAVIGATION_MODE_STANDARD)
            actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }
    
    public interface CourseDataSource
    {
    	public List<Course> getAvailableCourses();
    }

	@Override
	public Loader<List<Course>> onCreateLoader( int id, Bundle args ) 
	{
		switch( id )
		{
		case LoaderCodes.LOADER_CODE_COURSES:
		{
			return new CourseLoader( this, this.courseDataSource );
		}
		default:
		{
			return null;
		}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<Course>> loader, List<Course> data ) 
	{	
		LOGGER.info( "MainActivity finished loading courses." );
		this.mCourseNavAdapter.clear();
		this.mCourseNavAdapter.addAll( data );
	}

	@Override
	public void onLoaderReset( Loader<List<Course>> loader ) 
	{
		/* TODO Clear anything that relies on the loaders data. */
	}
	
	private static Logger LOGGER = Logger.getLogger( MainActivity.class.getName() );
}
