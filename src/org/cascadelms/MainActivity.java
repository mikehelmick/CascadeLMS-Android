package org.cascadelms;

import org.cascadelms.AuthenticationFragment.OnLoginListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * MainActivity is the container for each fragment that will be displayed in the
 * Cascade app.
 */
public class MainActivity extends ActionBarActivity implements OnLoginListener
{
    /*
     * This flag should eventually be replaced with a better method for tracking
     * login status.
     */
    private boolean userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLoggedIn = false;
    }

    @Override
    protected void onResume()
    {
        /* Checks login status every time the Activity resumes. */
        if (!userLoggedIn)
        {
            /* Shows the AuthenticationDialog. */
            new AuthenticationFragment().show(this.getSupportFragmentManager(),
                    AuthenticationFragment.TAG);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.action_logout:
            onLogout();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /* Called to perform the login */
    public void onLogin()
    {
        this.userLoggedIn = true;

        Log.i(MainActivity.class.getName(), "User is now logged in.");
        doToast(R.string.toast_login);
    }

    /* Called to perform the logout */
    private void onLogout()
    {
        this.userLoggedIn = false;

        Log.i(MainActivity.class.getName(), "User is now logged out.");
        doToast(R.string.toast_logout);
    }

    /* Helper function to display a Toast */
    private void doToast(int stringId)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, stringId, duration);
        toast.show();
    }
}
