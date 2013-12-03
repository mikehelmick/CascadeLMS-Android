package org.cascadelms;

import org.cascadelms.fragments.SocialStreamFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

/**
 * MainActivity is the container for each fragment that will be displayed in the
 * Cascade app.
 */
public class MainActivity extends ActionBarActivity
{
    private static final String PREFS_AUTH = "AuthenticationData";
    
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

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
            
            mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            mDrawerList = (ListView)findViewById(R.id.left_drawer);
            
            // Set up drawer toggle
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, R.string.drawer_open,
                    R.string.drawer_close);
            
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            
            // Jump to default fragment
            SocialStreamFragment fragment = new SocialStreamFragment();
            
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_frame, fragment)
                                      .commit();
        }
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
    
    private void registerCourse(int id, String courseName)
    {
        
    }
}
