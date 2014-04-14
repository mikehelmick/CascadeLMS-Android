package org.cascadelms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.cascadelms.data.sources.AuthTokenInfo;

public class CascadeActivity extends ActionBarActivity
{
    AuthTokenInfo mTokenInfo = null;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

        mTokenInfo = new AuthTokenInfo(this);

		if( !mTokenInfo.isValid() )
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
        mTokenInfo.clearData();

		Toast.makeText( getApplicationContext(), R.string.toast_logout,
				Toast.LENGTH_SHORT ).show();

		this.startLoginActivity();
        finish();
	}
}
