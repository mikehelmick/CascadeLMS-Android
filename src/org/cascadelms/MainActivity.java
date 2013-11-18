package org.cascadelms;

import org.cascadelms.AuthenticationFragment.OnLoginListener;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

/**
 * MainActivity is the container for each fragment that will be displayed in the Cascade app.
 */
public class MainActivity extends ActionBarActivity implements OnLoginListener
{	
	/* This flag should eventually be replaced with a better method for tracking login status. */
	private boolean userLoggedIn;
	
    @Override
    protected void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        userLoggedIn = false;
    }
    
    @Override
    protected void onResume() 
    {
    	/* Checks login status every time the Activity resumes. */
    	if( !userLoggedIn )
    	{    		
    		/* Shows the AuthenticationDialog. */
    		new AuthenticationFragment().show( this.getSupportFragmentManager(), AuthenticationFragment.TAG );
    	}
    	super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

	@Override
	public void onLogin() 
	{
		this.userLoggedIn = true;
		Log.i( MainActivity.class.getName(), "User is now logged in." );
	}
    
}
