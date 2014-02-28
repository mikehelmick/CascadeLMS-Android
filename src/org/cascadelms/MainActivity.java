package org.cascadelms;

import java.util.ArrayList;
import java.util.Iterator;

import org.cascadelms.CourseFragment.CourseDataProvider;
import org.cascadelms.fragments.AssignmentsFragment;
import org.cascadelms.fragments.CourseBlogFragment;
import org.cascadelms.fragments.DocumentsFragment;
import org.cascadelms.fragments.GradesFragment;
import org.cascadelms.fragments.HttpCommunicatorFragment;
import org.cascadelms.fragments.SocialStreamFragment;
import org.cascadelms.fragments.SocialStreamFragment.SubpageNavListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity is the container for each fragment that will be displayed in the
 * Cascade app.
 */
public class MainActivity extends ActionBarActivity implements
        FragmentManager.OnBackStackChangedListener, SubpageNavListener, CourseDataProvider
{
    private static final String PREFS_AUTH = "AuthenticationData";
    private static final String BACKTAG_HOME = "Home";
    private static final String BACKTAG_COURSEHOME = "CourseHome";
    private static final String BACKTAG_COURSESUBPAGE = "CourseSubpage";
    private static final int FRAGMENT_OTHER = -1;
    private static final int FRAGMENT_HOME = 0;
    private static final int NO_COURSE_SELECTED = -1;
    private static final int COURSE_EXISTING = -2;
    private static final int COURSE_UNSET = -3;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    
    private int selectedCourseId = MainActivity.NO_COURSE_SELECTED;
    private String selectedCourseTitle = "";

    private class CourseEntry
    {
        public final int id;
        public final String name;

        public CourseEntry(int courseId, String courseName)
        {
            id = courseId;
            name = courseName;
        }
    }
    
    private class FragmentCourseInfo
    {
        public final int fragment;
        public final int course;
        public final String title;

        public FragmentCourseInfo(int fragmentId, int courseId, String fragmentTitle)
        {
            fragment = fragmentId;
            course = courseId;
            title = fragmentTitle;
        }
    }

    private ArrayList<CourseEntry> mCourseList;

    // Used to map and list courses and subpages.
    private class CourseNavAdapter extends BaseAdapter
    {
        private final int SECTION_HOME = 0;
        private final int SECTION_COURSE_START = 1;

        private Context mContext;
        private ArrayList<CourseEntry> mCourseListRef;

        public CourseNavAdapter(Context context,
                ArrayList<CourseEntry> courseList)
        {
            mContext = context;
            mCourseListRef = courseList;
        }

        @Override
        public int getCount()
        {
            return mCourseListRef.size() + SECTION_COURSE_START;
        }

        @Override
        public Object getItem(int position)
        {
            if (position == SECTION_HOME)
                return NO_COURSE_SELECTED;
            else
                return mCourseListRef.get(position - SECTION_COURSE_START).id;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View newView = null;

            LayoutInflater inflater = LayoutInflater.from(mContext);
            newView = inflater.inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            TextView label = (TextView) newView
                    .findViewById(android.R.id.text1);

            if (position == SECTION_HOME)
                label.setText("Home");
            else
                label.setText(mCourseListRef.get(position
                        - SECTION_COURSE_START).name);

            return newView;
        }
    }

    private class CourseNavItemClickListener implements
            ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id)
        {
            handleCourseNavItem(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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

        // Add some dummy courses
        clearCourses();
        registerCourse(2, "CS App");
        registerCourse(5, "CS5001 - Senior Design");
        registerCourse(8, "Artificial Intelligence");

        CourseNavAdapter adapter = new CourseNavAdapter(this, mCourseList);

        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new CourseNavItemClickListener());

        // Set up drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Jump to home fragment.
        if (savedInstanceState == null)
            setFragment(FRAGMENT_HOME, NO_COURSE_SELECTED);
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

    private void clearCourses()
    {
        if (mCourseList == null)
            mCourseList = new ArrayList<CourseEntry>();

        mCourseList.clear();
    }

    private void registerCourse(int id, String name)
    {
        if (mCourseList == null)
            mCourseList = new ArrayList<CourseEntry>();

        mCourseList.add(new CourseEntry(id, name));
    }

    private void handleCourseNavItem(int position)
    {
        int newCourseId = (Integer) mDrawerList.getItemAtPosition(position);

        setFragment(FRAGMENT_HOME, newCourseId);

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void handleSubpageNavItem(int subpageId)
    {
        setFragment(subpageId, COURSE_EXISTING);
    }

    private HttpCommunicatorFragment subpageClassFactory(int fragmentId)
    {
        HttpCommunicatorFragment fragment;

        switch (fragmentId)
        {
        case R.string.fragment_courseblog:
            fragment = new CourseBlogFragment();
            break;
        case R.string.fragment_documents:
            fragment = new DocumentsFragment();
            break;
        case R.string.fragment_assignments:
            fragment = new AssignmentsFragment();
            break;
        case R.string.fragment_grades:
            fragment = new GradesFragment();
            break;
        default:
            fragment = new SocialStreamFragment();
        }

        return fragment;
    }
    
    private FragmentCourseInfo getCurrentFragmentCourse()
    {
        FragmentManager manager = getSupportFragmentManager();

        // Get current top fragment
        HttpCommunicatorFragment topFragment = (HttpCommunicatorFragment) manager
                .findFragmentById(R.id.content_frame);

        int fragmentId = FRAGMENT_OTHER;
        int courseId = COURSE_UNSET;
        String fragmentTitle = null;

        if (topFragment != null)
        {
            fragmentId = topFragment.getFragmentId();
            courseId = topFragment.getCourseId();
            fragmentTitle = topFragment.getFragmentTitle();
        }
        
        return new FragmentCourseInfo(fragmentId, courseId, fragmentTitle);
    }
    
    private void goUp()
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentCourseInfo old = getCurrentFragmentCourse();
        
        if (old.fragment == FRAGMENT_OTHER)
            manager.popBackStack();
        else if (old.fragment == FRAGMENT_HOME)
            manager.popBackStack(BACKTAG_COURSEHOME,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        else
            manager.popBackStack(BACKTAG_COURSESUBPAGE,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void setFragment(int fragmentId, int courseId)
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentCourseInfo old = getCurrentFragmentCourse();
        
        int oldCourseId = old.course;
        int oldFragmentId = old.fragment;

        // Only switch if we aren't already on the subpage.
        if (oldCourseId != courseId || oldFragmentId != fragmentId)
        {
            String backstackTag;

            // Tag fragments for up functionality.
            if (courseId != NO_COURSE_SELECTED)
            {
                if (fragmentId == FRAGMENT_HOME)
                    backstackTag = BACKTAG_COURSEHOME;
                else
                    backstackTag = BACKTAG_COURSESUBPAGE;
            }
            else
            {
                backstackTag = BACKTAG_HOME;
                
                // If we're at home, clear stack.
                manager.popBackStack(BACKTAG_COURSEHOME,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            // Instantiate the appropriate fragment.
            HttpCommunicatorFragment fragment = subpageClassFactory(fragmentId);

            Bundle bundle = new Bundle();

            // Reuse existing courseId
            if (courseId == COURSE_EXISTING)
                bundle.putInt("courseId", oldCourseId);
            else
                bundle.putInt("courseId", courseId);
            // For saved configs.
            bundle.putInt("fragmentId", fragmentId);

            fragment.setArguments(bundle);

            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.content_frame, fragment);
            transaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            // Add to back stack but only if this is not the home fragment.
            if (courseId != NO_COURSE_SELECTED)
                transaction.addToBackStack(backstackTag);

            transaction.commit();
        }
    }

    private void updateActionBar()
    {
        FragmentCourseInfo old = getCurrentFragmentCourse();
        
        int courseId = old.course;
        int fragmentId = old.fragment;
        String fragmentTitle = old.title;

        if (courseId != NO_COURSE_SELECTED)
        {
            boolean foundCourse = false;

            // Find course with this ID and grab its name for the Action Bar
            // subtitle.
            for (Iterator<CourseEntry> iter = mCourseList.iterator(); iter
                    .hasNext();)
            {
                CourseEntry entry = iter.next();

                if (entry.id == courseId)
                {
                    getSupportActionBar().setTitle(entry.name);
                    foundCourse = true;
                    break;
                }
            }

            if (foundCourse)
                getSupportActionBar().setSubtitle(fragmentTitle);
            else
                getSupportActionBar().setSubtitle(null);
        }
        else
        {
            if (fragmentTitle != null)
                getSupportActionBar().setTitle(fragmentTitle);
            else
                getSupportActionBar().setTitle(getTitle());
            getSupportActionBar().setSubtitle(null);
        }
        
        // Up navigation when inside a course subpage.
        mDrawerToggle.setDrawerIndicatorEnabled(fragmentId == FRAGMENT_HOME);
    }

    /* CourseDataProvider Methods */
    
	@Override
	public int getCourseId() 
	{
		return this.selectedCourseId;
	}

	@Override
	public String getCourseTitle() 
	{
		return this.selectedCourseTitle;
	}
}
