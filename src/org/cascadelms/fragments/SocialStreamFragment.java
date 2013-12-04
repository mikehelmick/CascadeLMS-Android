package org.cascadelms.fragments;

import org.cascadelms.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SocialStreamFragment extends HttpCommunicatorFragment
{
    private String[] mSubpageNames;
    private SubpageNavListener mListener;
    
    public interface SubpageNavListener
    {
        public void handleSubpageNavItem(int subpageId);
    }
    
    private class SubpageGridItemClickListener implements
        GridView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id)
        {
            if (mListener != null)
                mListener.handleSubpageNavItem(position + 1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_socialstream, null);
        TextView label = (TextView) view.findViewById(R.id.textView1);

        label.append(" " + getCourseId());

        // We're in a course, construct the subnavigation.
        if (getCourseId() != -1)
        {
            GridView subpageGrid = (GridView) view
                    .findViewById(R.id.subpageCategoryGrid);
            mSubpageNames = getResources()
                    .getStringArray(R.array.subpage_names);

            if (subpageGrid != null && mSubpageNames != null)
            {
                subpageGrid.setAdapter(new ArrayAdapter<String>(view
                        .getContext(), android.R.layout.simple_list_item_1,
                        mSubpageNames));
                subpageGrid.setOnItemClickListener(new SubpageGridItemClickListener());
            }
            else
                Log.e(SocialStreamFragment.class.getName(),
                        "Could not get subpage data.");
        }

        return view;
    }
    
    @Override
    public void onAttach(Activity activity)
    {
        mListener = (SubpageNavListener)activity;
        super.onAttach(activity);
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
