package org.cascadelms.fragments;

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
 * fragmentId (int): Corresponds to the type of fragment created.
 *
 */
public abstract class HttpCommunicatorFragment extends Fragment
{
    private String mBaseUrl;
    private int mCourseId = -1;
    private int mFragmentId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        Bundle arguments = getArguments();
        
        if (arguments != null)
        {
            mBaseUrl = arguments.getString("baseUrl");
            String cookie = arguments.getString("cookie"); // TODO
            mCourseId = arguments.getInt("courseId", -1);
            // TODO: Better-coupled design than just setting the fragment ID
            //  manually.
            mFragmentId = arguments.getInt("fragmentId", -1);
        }
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    /** Returns the course ID used by this fragment. **/
    public int getCourseId()
    {
        return mCourseId;
    }
    
    /** Returns the fragment ID used by this fragment. **/
    public int getFragmentId()
    {
        return mFragmentId;
    }
    
    /** Must be implemented in subclasses to provide a display name for this
     *  Fragment that will appear, for example, inside the Action Bar.
     */
    public abstract String getFragmentTitle();
    
    /** Must be implemented in subclasses to determine the full URL of the
     *  page to visit. The return value of this function is combined with
     *  the base URL like so:
     *  
     *  URL = baseURL/subpageURL
     *  
     *  If there is no valid sub URL (for example, a valid courseID is needed),
     *  return null.
     */
    protected abstract String getSubpageLocation();
}
