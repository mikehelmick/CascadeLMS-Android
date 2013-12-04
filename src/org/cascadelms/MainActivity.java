package org.cascadelms;

import java.util.ArrayList;
import java.util.Iterator;

import org.cascadelms.fragments.AssignmentsFragment;
import org.cascadelms.fragments.CourseBlogFragment;
import org.cascadelms.fragments.DocumentsFragment;
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
import android.util.Log;
import android.util.TypedValue;
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
        FragmentManager.OnBackStackChangedListener, SubpageNavListener
{
    private static final String PREFS_AUTH = "AuthenticationData";
    private static final String BACKTAG_COURSEHOME = "CourseHome";
    private static final String BACKTAG_COURSESUBPAGE = "CourseSubpage";
    private static final int FRAGMENT_HOME = 0;
    private static final int COURSE_NONE = -1;
    private static final int COURSE_EXISTING = -2;
    private static final int COURSE_UNSET = -3;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    private String[] mSubpageNames;

    private enum SubpageClass
    {
        SOCIAL_STREAM, COURSE_BLOG, DOCUMENTS, ASSIGNMENTS
    }

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

    private ArrayList<CourseEntry> mCourseList;

    // Used to map and list courses and subpages.
    private class CourseNavAdapter extends BaseAdapter
    {
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
            return mCourseListRef.size() + 1;
        }

        @Override
        public Object getItem(int position)
        {
            if (position == 0)
                return -1;
            else
                return mCourseListRef.get(position - 1).id;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public boolean isEnabled(int position)
        {
            // Don't make headers selectable
            if (position == 0)
                return false;
            else
                return true;
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

            if (position == 0)
            {
                label.setText("COURSES");
                // Extremely bad method of restyling for a header but
                // "good enough"
                label.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        (float) (label.getTextSize() * 0.7));
            }
            else
                label.setText(mCourseListRef.get(position - 1).name);

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
    protected void onSaveInstanceState(Bundle outState)
    {
        FragmentManager manager = getSupportFragmentManager();
        // Get current top fragment
        HttpCommunicatorFragment topFragment = (HttpCommunicatorFragment) manager
                .findFragmentById(R.id.content_frame);

        // Save course ID in case the orientation changes or whatever.
        if (topFragment != null)
        {
            outState.putInt("courseId", topFragment.getCourseId());
            outState.putInt("fragmentId", topFragment.getFragmentId());
        }

        super.onSaveInstanceState(outState);
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
        updateActionBar();
    }

    private void setupNavigation(Bundle savedInstanceState)
    {
        mSubpageNames = getResources().getStringArray(R.array.subpage_names);

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

        int courseId = COURSE_NONE;
        int fragmentId = FRAGMENT_HOME;

        if (savedInstanceState != null)
        {
            courseId = savedInstanceState.getInt("courseId", COURSE_NONE);
            fragmentId = savedInstanceState.getInt("fragmentId", FRAGMENT_HOME);
        }

        // Jump to fragment.
        setFragment(fragmentId, courseId);
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

        // Check length
        if (fragmentId < 0 || fragmentId >= SubpageClass.values().length)
        {
            Log.e(MainActivity.class.getName(),
                    "Uh oh, the chosen fragment ID is out of range! Defaulting to Social Stream.");
            fragmentId = FRAGMENT_HOME;
        }
        // Convert int to enum
        SubpageClass classId = SubpageClass.values()[fragmentId];

        switch (classId)
        {
        case COURSE_BLOG:
            fragment = new CourseBlogFragment();
            break;
        case DOCUMENTS:
            fragment = new DocumentsFragment();
            break;
        case ASSIGNMENTS:
            fragment = new AssignmentsFragment();
            break;
        default:
            fragment = new SocialStreamFragment();
        }

        return fragment;
    }

    private void setFragment(int fragmentId, int courseId)
    {
        FragmentManager manager = getSupportFragmentManager();

        // Get current top fragment
        HttpCommunicatorFragment topFragment = (HttpCommunicatorFragment) manager
                .findFragmentById(R.id.content_frame);

        int oldCourseId;

        if (topFragment == null)
            oldCourseId = COURSE_UNSET;
        else
            oldCourseId = topFragment.getCourseId();

        // Only switch if we aren't already on the subpage.
        if (oldCourseId != courseId || courseId == COURSE_EXISTING)
        {
            String backstackTag = "";

            // Remove existing fragments up to the proper landing page, and
            // tag the new fragment as appropriate.
            if (courseId != COURSE_NONE)
            {
                if (fragmentId == FRAGMENT_HOME)
                {
                    backstackTag = BACKTAG_COURSEHOME;
                    manager.popBackStack(BACKTAG_COURSEHOME,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                else
                {
                    backstackTag = BACKTAG_COURSESUBPAGE;
                    manager.popBackStack(BACKTAG_COURSESUBPAGE,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
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
            if (courseId != COURSE_NONE)
                transaction.addToBackStack(backstackTag);

            transaction.commit();
        }
    }

    private void updateActionBar()
    {
        FragmentManager manager = getSupportFragmentManager();

        // Get current top fragment
        HttpCommunicatorFragment topFragment = (HttpCommunicatorFragment) manager
                .findFragmentById(R.id.content_frame);

        if (topFragment != null && topFragment.getCourseId() != COURSE_NONE)
        {
            // Find course with this ID and grab its name for the Action Bar
            // subtitle.
            for (Iterator<CourseEntry> iter = mCourseList.iterator(); iter
                    .hasNext();)
            {
                CourseEntry entry = iter.next();

                if (entry.id == topFragment.getCourseId())
                {
                    getSupportActionBar().setSubtitle(entry.name);
                    break;
                }
            }
        }
        else
            getSupportActionBar().setSubtitle(null);
    }
}
