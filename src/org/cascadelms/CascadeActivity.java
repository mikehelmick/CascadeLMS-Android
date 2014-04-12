package org.cascadelms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CascadeActivity extends ActionBarActivity
{
	/* Constants */
	public static final String PREFS_AUTH = "AuthenticationData";

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		SharedPreferences preferences = getSharedPreferences( PREFS_AUTH, 0 );
		String token = preferences.getString( "token", null );

		if( token == null )
		{
			startLoginActivity();
		}
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		super.onCreateOptionsMenu( menu );
		this.getMenuInflater().inflate( R.menu.activity_cascade, menu );
		return true;
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch( item.getItemId() )
		{
			case R.id.action_logout:
			{
				this.logOut();
				return true;
			}
			case R.id.action_settings:
			{
				/* TODO Launch the settings Activity from here. */
				return true;
			}
			default:
			{
				return super.onOptionsItemSelected( item );
			}
		}
	}

	/**
	 * Starts the LoginActivity.
	 */
	protected void startLoginActivity()
	{
		Intent intent = new Intent( this, SelectSchoolActivity.class );

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
		this.startActivity( intent );
        finish();
	}

	/**
	 * Logs the user out and starts the LoginActivity.
	 */
	protected void logOut()
	{
		// TODO: Change the way login status is stored.
		SharedPreferences preferences = getSharedPreferences( PREFS_AUTH, 0 );
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean( "loggedIn", false );
		editor.commit();

		Toast.makeText( getApplicationContext(), R.string.toast_logout,
				Toast.LENGTH_SHORT ).show();

		this.startLoginActivity();
        finish();
	}
}
