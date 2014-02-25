package org.cascadelms.fragments;

import org.cascadelms.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AssignmentsFragment extends HttpCommunicatorFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        
        View view = inflater.inflate( R.layout.fragment_assignments, null );
        TextView label = (TextView)view.findViewById(R.id.placeholder);
        
        label.append(" " + getCourseId());
        
        return view;
    }

    @Override
    protected String getSubpageLocation()
    {
        int courseId = getCourseId();
        
        if (courseId == -1)
            return null;
        else
            return "course/" + courseId + "/assignments";
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.fragment_assignments);
    }

}
