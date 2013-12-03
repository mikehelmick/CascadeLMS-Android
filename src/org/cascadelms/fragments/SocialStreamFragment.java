package org.cascadelms.fragments;

import org.cascadelms.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SocialStreamFragment extends HttpCommunicatorFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        
        return inflater.inflate( R.layout.fragment_socialstream, null );
    }
    
}
