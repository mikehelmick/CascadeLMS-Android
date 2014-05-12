package org.cascadelms.data.sources;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthTokenInfo
{
    /* Constants */
    private static final String PREFS_AUTH = "AuthenticationData";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_CASCADE_URL = "cascadeUrl";

    private SharedPreferences mPreferences = null;
    private String mAuthToken = null;
    private String mCascadeUrl = null;

    public AuthTokenInfo(Context context)
    {
        mPreferences = context.getSharedPreferences(PREFS_AUTH, 0);
        mAuthToken = mPreferences.getString(KEY_TOKEN, null);
        mCascadeUrl = mPreferences.getString(KEY_CASCADE_URL, null);
    }

    public boolean isValid()
    {
        return (mAuthToken != null && mCascadeUrl != null);
    }

    public String getAuthToken()
    {
        return mAuthToken;
    }

    public String getCascadeUrl()
    {
        return mCascadeUrl;
    }

    public void setAuthToken(String token)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public void setCascadeUrl(String url)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_CASCADE_URL, url);
        editor.commit();
    }

    public void clearData()
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_TOKEN, null);
        editor.putString(KEY_CASCADE_URL, null);
        editor.commit();
    }
}
