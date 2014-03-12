package org.cascadelms;

import java.util.List;
import java.util.logging.Logger;

import org.cascadelms.data.loaders.CourseLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.sources.FakeDataSource;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * The MAIN activity for the CascadeLMS app. This activity shows the
 * SocialStream fragment and hosts a navigation drawer allowing the user to
 * select a Course to view.
 */
public class StreamActivity extends ActionBarActivity implements
		LoaderCallbacks<List<Course>>, ListView.OnItemClickListener
{
	private CourseDataSource courseDataSource;
	private CourseNavAdapter mCourseNavAdapter;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	/* Constants */
	private static final String PREFS_AUTH = "AuthenticationData";
	public static final String ACTIVITY_STREAM_EXTRAS_SELECTED_COURSE = "org.cascadelms.extras.course_data";
	public static final String ACTIVITY_STREAM_EXTRAS_COURSE_LIST = "org.cascadelms.extras.course_list";

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		this.setContentView( R.layout.activity_stream );

		/* Sets up a CourseDataSource and begins course loading. */
		this.courseDataSource = FakeDataSource.getInstance();
		this.getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_COURSES, null, this )
				.forceLoad();

		/* Creates the navigation adapter and drawer */
		mCourseNavAdapter = new CourseNavAdapter( this );
		mDrawerList = (ListView) findViewById( R.id.activity_stream_drawer );
		mDrawerList.setAdapter( mCourseNavAdapter );
		mDrawerList.setOnItemClickListener( this );

		/* Sets up the drawer toggle */
		DrawerLayout drawer = (DrawerLayout) this
				.findViewById( R.id.activity_stream_drawer_layout );
		mDrawerToggle = new ActionBarDrawerToggle( this, drawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close )
		{
			public void onDrawerClosed( View view )
			{
				super.onDrawerClosed( view );
				StreamActivity.this.getSupportActionBar().setTitle(
						StreamActivity.this.getString( R.string.app_name ) );
			}

			public void onDrawerOpened( View drawerView )
			{
				super.onDrawerOpened( drawerView );
				getSupportActionBar()
						.setTitle(
								StreamActivity.this
										.getString( R.string.activity_stream_drawer_title ) );
			}
		};
		drawer.setDrawerListener( mDrawerToggle );

		/* Enables the home button as a drawer toggle. */
		mDrawerToggle.setDrawerIndicatorEnabled( true );
		this.getSupportActionBar().setDisplayHomeAsUpEnabled( true );
		this.getSupportActionBar().setHomeButtonEnabled( true );

		drawer.setDrawerShadow( R.drawable.drawer_shadow, GravityCompat.START );

		// TODO: Store the login status in a different way.
		SharedPreferences preferences = getSharedPreferences( PREFS_AUTH, 0 );
		boolean loggedIn = preferences.getBoolean( "loggedIn", false );

		if( !loggedIn )
		{
			startLoginActivity();
		}
	}

	@Override
	protected void onPostCreate( Bundle savedInstanceState )
	{
		super.onPostCreate( savedInstanceState );
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged( Configuration newConfig )
	{
		super.onConfigurationChanged( newConfig );
		mDrawerToggle.onConfigurationChanged( newConfig );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.activity_stream, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		/* Gives the DrawerToggle a change to handle the item first. */
		if( mDrawerToggle.onOptionsItemSelected( item ) )
		{
			return true;
		}
		switch( item.getItemId() )
		{
			case R.id.action_logout:
			{
				this.logOut();
				return true;
			}
			default:
			{
				return super.onOptionsItemSelected( item );
			}
		}
	}

	@Override
	public void onItemClick( AdapterView<?> parent, View view, int position,
			long id )
	{
		LOGGER.info( "Press on course " + id
				+ " in the StreamActivity nav drawer." );
		this.startCourseActivity( mCourseNavAdapter.getItem( position ) );
	}

	/**
	 * Starts the LoginActivity.
	 */
	private void startLoginActivity()
	{
		Intent intent = new Intent( this, LoginActivity.class );

		// Make Back leave the app.
		intent.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY );
		this.startActivity( intent );
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
		intent.putExtra( ACTIVITY_STREAM_EXTRAS_SELECTED_COURSE, course );
		intent.putExtra( ACTIVITY_STREAM_EXTRAS_COURSE_LIST,
				this.mCourseNavAdapter.getAllCourses() );
		this.startActivity( intent );
	}

	/**
	 * Logs the user out and starts the LoginActivity.
	 */
	private void logOut()
	{
		// TODO: Change the way login is stored.
		SharedPreferences preferences = getSharedPreferences( PREFS_AUTH, 0 );
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean( "loggedIn", false );
		editor.commit();

		Toast.makeText( getApplicationContext(), R.string.toast_logout,
				Toast.LENGTH_SHORT ).show();

		this.startLoginActivity();
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
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_COURSES:
			{
				LOGGER.info( "MainActivity finished loading courses." );
				this.mCourseNavAdapter.clear();
				this.mCourseNavAdapter.addAll( data );
				return;
			}
		}
	}

	@Override
	public void onLoaderReset( Loader<List<Course>> loader )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_COURSES:
			{
				this.mCourseNavAdapter.clear();
				return;
			}
		}
	}

	private static Logger LOGGER = Logger.getLogger( StreamActivity.class
			.getName() );
}
