package org.cascadelms.fragments;

import java.util.ArrayList;

import org.cascadelms.R;
import org.cascadelms.socialstream.SocialStreamAdapter;
import org.cascadelms.socialstream.SocialStreamPost;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class SocialStreamFragment extends HttpCommunicatorFragment
{
    private String[] mSubpageNames;
    private SubpageNavListener mListener;
    
    public interface SubpageNavListener
    {
        public void handleSubpageNavItem(int subpageId);
    }
    
    private class SubpageGridItemClickListener implements
        AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id)
        {
            if (mListener != null)
                mListener.handleSubpageNavItem(position + 1);
        }
    }
    
    private class SocialStreamItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // TODO: Supply post object
            goToPostDetail(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_socialstream, null);

        // We're in a course, construct the subnavigation.
        if (getCourseId() != -1)
        {
            GridView subpageGrid = (GridView) view
                    .findViewById(R.id.subpage_category_grid);
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
        
        ListView socialstreamList = (ListView) view.findViewById(R.id.socialstream_list);
        
        if (socialstreamList != null)
        {
            ArrayList<SocialStreamPost> postList = new ArrayList<SocialStreamPost>();
            
            for (int i = 0; i < 10; ++i)
                postList.add(new SocialStreamPost());
            
            socialstreamList.setAdapter(new SocialStreamAdapter(view.getContext(), postList));
            socialstreamList.setOnItemClickListener(new SocialStreamItemClickListener());
        }

        return view;
    }
    
    @Override
    public void onAttach(Activity activity)
    {
        mListener = (SubpageNavListener)activity;
        super.onAttach(activity);
    }
    
    private void goToPostDetail(SocialStreamPost parentPost)
    {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        
        SocialStreamDetailFragment fragment = new SocialStreamDetailFragment();

        transaction.replace(R.id.content_frame, fragment);
        transaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        // Add to back stack but only if this is not the home fragment.
        transaction.addToBackStack(null);

        transaction.commit();
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

    @Override
    public String getFragmentTitle()
    {
        return null;
    }

}
