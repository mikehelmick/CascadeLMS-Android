package org.cascadelms;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.data.loaders.CourseLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.sources.CascadeDataSource;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * The main activity for the CascadeLMS app. This activity shows the
 * SocialStream fragment and hosts a navigation drawer allowing the user to
 * select a Course to view.
 */
public class StreamActivity extends CascadeActivity implements
        LoaderCallbacks<List<Course>>, ListView.OnItemClickListener
{
    private CourseDataSource courseDataSource;
    private CourseNavAdapter mCourseNavAdapter;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawer;

    /* Constants */
    public static final String ACTIVITY_STREAM_EXTRAS_SELECTED_COURSE = "org.cascadelms.extras.course_data";
    public static final String ACTIVITY_STREAM_EXTRAS_COURSE_LIST = "org.cascadelms.extras.course_list";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_stream);

		/* Sets up a CourseDataSource and begins course loading. */
        this.courseDataSource = CascadeDataSource.getInstance();
        this.getSupportLoaderManager()
                .initLoader(LoaderCodes.LOADER_CODE_COURSES, null, this)
                .forceLoad();

		/* Creates the navigation adapter and drawer */
        mCourseNavAdapter = new CourseNavAdapter(this);
        mDrawerList = (ListView) findViewById(R.id.activity_stream_drawer);
        mDrawerList.setAdapter(mCourseNavAdapter);
        mDrawerList.setOnItemClickListener(this);

		/* Sets up the drawer toggle */
        mDrawer = (DrawerLayout) this
                .findViewById(R.id.activity_stream_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close)
        {
            public void onDrawerClosed(View view)
            {
                super.onDrawerClosed(view);
                StreamActivity.this.getSupportActionBar().setTitle(
                        StreamActivity.this.getString(R.string.app_name));
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                getSupportActionBar()
                        .setTitle(
                                StreamActivity.this
                                        .getString(R.string.activity_stream_drawer_title)
                        );
            }
        };
        mDrawer.setDrawerListener(mDrawerToggle);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		/* Enables the home button as a drawer toggle. */
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.activity_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        /* Gives the DrawerToggle a change to handle the item first. */
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch (item.getItemId())
        {
		/* No cases to respond to yet. */
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {
        LOGGER.info("Press on course " + id
                + " in the StreamActivity nav drawer.");
        this.startCourseActivity(mCourseNavAdapter.getItem(position));
        mDrawer.closeDrawers();
    }

    /**
     * Packages the chosen Course in an intent and starts CourseActivity with
     * it.
     *
     * @param course the selected Course
     */
    private void startCourseActivity(Course course)
    {
        Intent intent = new Intent(this, CourseActivity.class);
        intent.putExtra(ACTIVITY_STREAM_EXTRAS_SELECTED_COURSE, course);
        intent.putExtra(ACTIVITY_STREAM_EXTRAS_COURSE_LIST,
                this.mCourseNavAdapter.getAllCourses());
        this.startActivity(intent);
    }

    public interface CourseDataSource
    {
        public List<Course> getAvailableCourses();
    }

    @Override
    public Loader<List<Course>> onCreateLoader(int id, Bundle args)
    {
        switch (id)
        {
            case LoaderCodes.LOADER_CODE_COURSES:
            {
                return new CourseLoader(this, this.courseDataSource);
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Course>> loader, List<Course> data)
    {
        switch (loader.getId())
        {
            case LoaderCodes.LOADER_CODE_COURSES:
            {
                LOGGER.info("MainActivity finished loading courses.");
                this.mCourseNavAdapter.clear();
                if (data != null)
                    this.mCourseNavAdapter.addAll(data);
                else
                    Toast.makeText(this, R.string.course_list_failed,
                            Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Course>> loader)
    {
        switch (loader.getId())
        {
            case LoaderCodes.LOADER_CODE_COURSES:
            {
                this.mCourseNavAdapter.clear();
                return;
            }
        }
    }

    private static Logger LOGGER = Logger.getLogger(StreamActivity.class
            .getName());
}
