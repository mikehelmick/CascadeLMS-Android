package org.cascadelms.socialstream;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SocialStreamCommentAdapter extends BaseAdapter
{
    private Context mContext;
    private SocialStreamPost mParentPostRef;
    private ArrayList<SocialStreamComment> mCommentListRef;

    public SocialStreamCommentAdapter(Context context,
            SocialStreamPost parentPost,
            ArrayList<SocialStreamComment> commentList)
    {
        mContext = context;
        mParentPostRef = parentPost;
        mCommentListRef = commentList;
    }

    @Override
    public int getCount()
    {
        return mCommentListRef.size() + 1;
    }

    @Override
    public Object getItem(int position)
    {
        if (position == 0)
            return mParentPostRef;
        else
            return mCommentListRef.get(position);
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
        if (position == 0)
            newView = inflater.inflate(org.cascadelms.R.layout.list_item_socialstream_post,
                    parent, false);
        else
            newView = inflater.inflate(org.cascadelms.R.layout.list_item_socialstream_comment,
                    parent, false);

        return newView;
    }
}
