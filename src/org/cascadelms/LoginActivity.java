package org.cascadelms;

import org.cascadelms.auth.*;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends FragmentActivity
{
    private static final String PREFS_AUTH = "AuthenticationData";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private OAuthPhaseOneTask mAuthOneTask = null;
    private OAuthPhaseTwoTask mAuthTwoTask = null;

    // OAuth object
    private SimpleOAuth mOauth = null;

    // UI references.
    private WebView mLoginWebView;
    private View mLoginStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        String schoolUrl;

        /* Gets the Course data provided by the Intent that started this. */
        Bundle extras = this.getIntent().getExtras();

        if (extras != null)
        {
            schoolUrl = extras.getString(SelectSchoolActivity.ARGS_SCHOOL_URL);

            mOauth = new SimpleOAuth(
                    ConsumerSecretsProvider.getConsumerKey(),
                    ConsumerSecretsProvider.getConsumerSecret(),
                    schoolUrl + "/oauth/request_token",
                    schoolUrl + "/oauth/access_token",
                    schoolUrl + "/oauth/authorize");

            mLoginWebView = (WebView) findViewById(R.id.login_view);
            mLoginStatusView = findViewById(R.id.login_status);

            mAuthOneTask = new OAuthPhaseOneTask();
            mAuthOneTask.execute((Void) null);
        } else
            showAuthError("No school URL provided.");
    }

    @Override
    protected void onStop()
    {
        if (mAuthOneTask != null)
            mAuthOneTask.cancel(true);
        if (mAuthTwoTask != null)
            mAuthTwoTask.cancel(true);

        super.onStop();
    }

    /**
     * Opens the WebView so the user can authorize the app.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showLogin()
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-out
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(0)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            mLoginStatusView.setVisibility(View.GONE);
                        }
                    });

            mLoginWebView.setVisibility(View.VISIBLE);
            mLoginWebView.animate().setDuration(shortAnimTime)
                    .alpha(1)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            mLoginWebView.setVisibility(View.VISIBLE);
                        }
                    });
        } else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(View.GONE);
            mLoginWebView.setVisibility(View.VISIBLE);
        }

        mLoginWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                // Check for the callback URL.
                if (url.startsWith("cascade://androidapp"))
                {
                    mAuthTwoTask = new OAuthPhaseTwoTask();
                    mAuthTwoTask.execute((Void) null);

                    return true;
                }

                return false;
            }
        });

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        WebSettings settings = mLoginWebView.getSettings();
        settings.setSaveFormData(false);
        settings.setSavePassword(false);

        mLoginWebView.loadUrl(mOauth.getAuthorizeUrl());
    }

    /* Displays the specified error and returns to the Select School screen when dismissed. */
    private void showAuthError(final String message)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                dialogBuilder.setTitle("Authentication failed").setMessage(message)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Toast.makeText(getApplicationContext(), R.string.toast_login_fail,
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).setCancelable(false).show();
            }
        });
    }

    /* Phase one: Retrieves the request token. If successful, opens the WebView. */
    public class OAuthPhaseOneTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            Boolean success = true;
            Log.i(getLocalClassName(), "Starting OAuth.");

            try
            {
                mOauth.getRequestToken();
            } catch (IOException e)
            {
                success = false;
                showAuthError(e.getLocalizedMessage());
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthOneTask = null;

            if (success)
            {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                dialogBuilder.setMessage(R.string.desc_login_instructions)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                showLogin();
                            }
                        }).setCancelable(false).show();
            }
        }

        @Override
        protected void onCancelled()
        {
            mAuthOneTask = null;
            Toast.makeText(getApplicationContext(), R.string.toast_login_cancel,
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /* Phase two: Does the token exchange. If successful, completes login. */
    public class OAuthPhaseTwoTask extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... params)
        {
            Boolean success = true;

            try
            {
                mOauth.exchangeToken();
            } catch (IOException e)
            {
                success = false;
                showAuthError(e.getLocalizedMessage());
                e.printStackTrace();
            }

            return success;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            mAuthTwoTask = null;

            if (success)
            {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();

                // TODO: Make this better.
                SharedPreferences preferences = getSharedPreferences(
                        PREFS_AUTH, 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("token", mOauth.getOAuthToken());
                editor.commit();

                Toast.makeText(getApplicationContext(), R.string.toast_login,
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this,
                        StreamActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected void onCancelled()
        {
            mAuthTwoTask = null;
            Toast.makeText(getApplicationContext(), R.string.toast_login_cancel,
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
