package org.cascadelms;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity
{
    private static final String PREFS_AUTH = "AuthenticationData";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // Values for email and password at the time of the login attempt.
    private String mUsername;
    private String mPassword;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView
                .setOnEditorActionListener(new TextView.OnEditorActionListener()
                {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                            KeyEvent keyEvent)
                    {
                        if (id == R.id.login || id == EditorInfo.IME_NULL)
                        {
                            attemptLogin();
                            return true;
                        }
                        return false;
                    }
                });

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        findViewById(R.id.sign_in_button).setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        attemptLogin();
                    }
                });
    }

    /**
     * Attempts to log in with the given username and password, launching a
     * background task to do so.
     */
    public void attemptLogin()
    {
        if (mAuthTask != null)
        {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mUsername = mUsernameView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        showProgress(true);
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            mLoginFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        }
        else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.

            try
            {
                // Simulate network access.
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthTask = null;
            showProgress(false);

            if (success)
            {
                // TODO: Make this better.
                SharedPreferences preferences = getSharedPreferences(
                        PREFS_AUTH, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("loggedIn", true);
                editor.commit();

                Toast.makeText(getApplicationContext(), R.string.toast_login,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);

                // Make Back leave the app.
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
            else
            {
                mPasswordView.setError(getString(R.string.error_general));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
