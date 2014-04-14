package org.cascadelms.data.sources;

import org.cascadelms.CascadeApp;
import org.cascadelms.SimpleOAuth;
import org.cascadelms.StreamActivity.CourseDataSource;
import org.cascadelms.data.models.Assignment;
import org.cascadelms.data.models.BlogPost;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.Document;
import org.cascadelms.data.models.Grade;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.fragments.AssignmentsFragment.AssignmentsDataSource;
import org.cascadelms.fragments.CourseBlogFragment.BlogDataSource;
import org.cascadelms.fragments.DocumentsFragment.DocumentsDataSource;
import org.cascadelms.fragments.GradesFragment.GradesDataSource;
import org.cascadelms.fragments.SocialStreamFragment.StreamDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * A class for communicating with a Cascade server and providing data.
 */
public class CascadeDataSource implements CourseDataSource,
        DocumentsDataSource, AssignmentsDataSource, BlogDataSource,
        StreamDataSource, GradesDataSource
{
    private static CascadeDataSource instance;

    private CascadeDataSource()
    {
		/* Private constructor */
    }

    public static CascadeDataSource getInstance()
    {
        if( instance == null )
        {
            instance = new CascadeDataSource();
        }
        return instance;
    }

    private InputStream getInputStream(String path)
    {
        AuthTokenInfo tokenInfo = new AuthTokenInfo(CascadeApp.getContext());
        HttpURLConnection request = null;
        InputStream stream = null;

        try
        {
            URL url = new URL(tokenInfo.getCascadeUrl() + "/" + path);

            request = (HttpURLConnection) url.openConnection();
            request.addRequestProperty("Accept", "text/xml");
            request.setRequestProperty(SimpleOAuth.OAUTH_TOKEN, tokenInfo.getAuthToken());

            request.connect();

            stream = request.getInputStream();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return stream;
    }

    @Override
    public List<Assignment> getAssignmentsForCourse(int courseId)
    {
        List<Assignment> data = null;

        InputStream stream = getInputStream("course/" + courseId + "/assignments");

        try
        {
            if (stream != null)
                data = XMLParser.parseAssignments(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<BlogPost> getBlogPostsForCourse(int courseId)
    {
        return null;
    }

    @Override
    public List<Course> getAvailableCourses()
    {
        return null;
    }

    @Override
    public List<Document> getDocumentsForCourse(int courseId)
    {
        return null;
    }

    @Override
    public List<Document> getDocumentsInFolder(Document folder)
    {
        return null;
    }

    @Override
    public List<Grade> getGradesForCourse(int courseId)
    {
        List<Grade> data = null;

        InputStream stream = getInputStream("course/" + courseId + "/grades");

        try
        {
            if (stream != null)
                data = XMLParser.parseGrades(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<StreamItem> getAllStreamItems()
    {
        List<StreamItem> data = null;

        InputStream stream = getInputStream("home");

        try
        {
            if (stream != null)
                data = XMLParser.parseFeed(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<StreamItem> getStreamItemsForCourse(int courseId)
    {
        List<StreamItem> data = null;

        InputStream stream = getInputStream("course/" + courseId);

        try
        {
            if (stream != null)
                data = XMLParser.parseCourseFeed(stream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public StreamItem getStreamItemDetail(long postId)
    {
        return null;
    }
}
