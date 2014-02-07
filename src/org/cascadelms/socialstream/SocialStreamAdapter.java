package org.cascadelms.socialstream;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SocialStreamAdapter extends BaseAdapter
{
    private Context mContext;
    private ArrayList<SocialStreamPost> mPostListRef;

    public SocialStreamAdapter(Context context,
            ArrayList<SocialStreamPost> courseList)
    {
        mContext = context;
        mPostListRef = courseList;
    }

    @Override
    public int getCount()
    {
        return mPostListRef.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mPostListRef.get(position);
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
        newView = inflater.inflate(org.cascadelms.R.layout.view_socialstream_post,
                parent, false);

        return newView;
    }
}
