package org.cascadelms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * HttpCommunicatorFragment is the base class for all fragments that are used to
 * display data from the server, each one representing a different "page".
 * 
 * Arguments:
 * baseUrl (string): The base URL, generally the domain + path to the location
 *                   where the instance of Cascade is being hosted.
 * cookie  (string): Needed for transactions with the server.
 * courseId   (int): Optional, used to indicate which course this view
 *                   corresponds to.
 *
 */
public class HttpCommunicatorFragment extends Fragment
{
    private String mBaseUrl;
    private int mCourseId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        Bundle arguments = getArguments();
        
        mBaseUrl = arguments.getString("baseUrl");
        String cookie = arguments.getString("cookie"); // TODO
        mCourseId = arguments.getInt("courseId", -1);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
