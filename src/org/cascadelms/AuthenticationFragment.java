package org.cascadelms;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AuthenticationFragment extends DialogFragment implements
        OnClickListener
{
    public static final String TAG = "authentication";
    private OnLoginListener loginListener;

    @Override
    public void onAttach(Activity activity)
    {
        this.loginListener = (OnLoginListener) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_authentication, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        ((Button) view.findViewById(R.id.login_button))
                .setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
        case R.id.login_button:
        {
            this.loginListener.onLogin();
            this.dismiss();
            break;
        }
        default:
        {
            Log.w(AuthenticationFragment.class.getName(),
                    "Got an onClick from an unknown view.");
        }
        }
    }

    /* This interface must be used to get a callback when login occurs. */
    public interface OnLoginListener
    {
        public void onLogin();
    }
}
