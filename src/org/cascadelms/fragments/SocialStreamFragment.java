package org.cascadelms.fragments;

import org.cascadelms.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SocialStreamFragment extends HttpCommunicatorFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        
        View view = inflater.inflate( R.layout.fragment_socialstream, null );
        TextView label = (TextView)view.findViewById(R.id.textView1);
        
        label.append(" " + getCourseId());
        
        return view;
    }

    @Override
    protected String getSubpageLocation()
    {
        int courseId = getCourseId();
        
        if (courseId == -1)
            return "home";
        else
            return "course/" + courseId;
    }
    
}
