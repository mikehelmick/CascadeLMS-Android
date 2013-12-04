package org.cascadelms;

import java.util.ArrayList;

import org.cascadelms.fragments.HttpCommunicatorFragment;
import org.cascadelms.fragments.SocialStreamFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
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
public class MainActivity extends ActionBarActivity
{
    private static final String PREFS_AUTH = "AuthenticationData";
    private static final int FRAGMENT_HOME = -1;
    private static final int COURSE_NONE = -1;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

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
            newView = inflater.inflate(
                    android.R.layout.simple_list_item_1, parent,
                    false);
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
        {
            setContentView(R.layout.activity_main);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);

            // Add some dummy courses
            mCourseList = new ArrayList<CourseEntry>();
            registerCourse(2, "CS App");
            registerCourse(5, "CS5001 - Senior Design");
            registerCourse(8, "Artificial Intelligence");

            CourseNavAdapter adapter = new CourseNavAdapter(this, mCourseList);

            mDrawerList.setAdapter(adapter);
            mDrawerList
                    .setOnItemClickListener(new CourseNavItemClickListener());

            // Set up drawer toggle
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, R.string.drawer_open,
                    R.string.drawer_close);

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            
            int courseId = COURSE_NONE;
            
            if (savedInstanceState != null)
                courseId = savedInstanceState.getInt("courseId", COURSE_NONE);

            // Jump to fragment.
            setFragment(FRAGMENT_HOME, courseId);
        }
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
            outState.putInt("courseId", topFragment.getCourseId());
        
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

    private void registerCourse(int id, String name)
    {
        mCourseList.add(new CourseEntry(id, name));
    }

    private void handleCourseNavItem(int position)
    {
        int newCourseId = (Integer) mDrawerList.getItemAtPosition(position);

        setFragment(FRAGMENT_HOME, newCourseId);

        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void setFragment(int fragmentId, int courseId)
    {
        // Jump to default fragment. TODO: Other fragments.
        SocialStreamFragment fragment = new SocialStreamFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("courseId", courseId);

        fragment.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        
        // Get current top fragment
        HttpCommunicatorFragment topFragment = (HttpCommunicatorFragment) manager
                .findFragmentById(R.id.content_frame);

        // Remove existing fragment from back stack if we aren't navigating away
        //  from the home page.
        if (topFragment != null && topFragment.getCourseId() != COURSE_NONE)
            manager.popBackStack();

        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.content_frame, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        // Add to back stack but only if this is not the home fragment.
        if (courseId != COURSE_NONE)
            transaction.addToBackStack(null);
        
        transaction.commit();

    }
}
