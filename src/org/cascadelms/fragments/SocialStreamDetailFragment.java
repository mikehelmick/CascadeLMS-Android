package org.cascadelms.fragments;

import java.util.ArrayList;

import org.cascadelms.R;
import org.cascadelms.socialstream.SocialStreamComment;
import org.cascadelms.socialstream.SocialStreamCommentAdapter;
import org.cascadelms.socialstream.SocialStreamPost;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SocialStreamDetailFragment extends HttpCommunicatorFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_socialstream_detail,
                null);

        ListView socialstreamList = (ListView) view
                .findViewById(R.id.socialstream_comment_list);

        if (socialstreamList != null)
        {
            SocialStreamPost parentPost = new SocialStreamPost();
            ArrayList<SocialStreamComment> commentList = new ArrayList<SocialStreamComment>();

            for (int i = 0; i < 10; ++i)
                commentList.add(new SocialStreamComment());

            socialstreamList.setAdapter(new SocialStreamCommentAdapter(view
                    .getContext(), parentPost, commentList));
        }

        return view;
    }

    @Override
    protected String getSubpageLocation()
    {
        // TODO: Post ID
        return "post/view/";
    }
    
    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.fragment_viewpost);
    }
}
