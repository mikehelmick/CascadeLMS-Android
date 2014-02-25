package org.cascadelms.fragments;

import java.util.ArrayList;

import org.cascadelms.R;
import org.cascadelms.socialstream.SocialStreamAdapter;
import org.cascadelms.socialstream.SocialStreamPost;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class SocialStreamFragment extends HttpCommunicatorFragment
{
    private SubpageNavListener mListener;
    
    private int[] mSubpageFragmentIds =
    {
        R.string.fragment_courseblog,
        R.string.fragment_documents,
        R.string.fragment_assignments,
        R.string.fragment_grades
    };
    
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
            SubpageNavAdapter adapter = (SubpageNavAdapter) parent.getAdapter();
            
            if (adapter != null && mListener != null)
            {
                Integer stringId = (Integer)adapter.getItem(position);
                
                if (stringId != null)
                mListener.handleSubpageNavItem(stringId.intValue());
            }
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
    
    private class SubpageNavAdapter extends BaseAdapter
    {
        private Context mContext;
        private int[] mStringIds;

        public SubpageNavAdapter(Context context,
                int[] stringIds)
        {
            mContext = context;
            mStringIds = stringIds;
        }

        @Override
        public int getCount()
        {
            return mStringIds.length;
        }

        @Override
        public Object getItem(int position)
        {
            if (position >= 0 && position < mStringIds.length)
                return mStringIds[position];
            else
                return -1;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View newView = null;

            LayoutInflater inflater = LayoutInflater.from(mContext);
            newView = inflater.inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            TextView label = (TextView) newView
                    .findViewById(android.R.id.text1);

            label.setText(getString(mStringIds[position]));

            return newView;
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

            if (subpageGrid != null)
            {
                subpageGrid.setAdapter(new SubpageNavAdapter(view.getContext(), mSubpageFragmentIds));
                subpageGrid.setOnItemClickListener(new SubpageGridItemClickListener());
            }
            else
                Log.e(SocialStreamFragment.class.getName(),
                        "Could not find subpage grid.");
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
