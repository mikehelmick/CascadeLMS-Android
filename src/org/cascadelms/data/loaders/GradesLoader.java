package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.data.models.Grade;
import org.cascadelms.fragments.GradesFragment.GradesDataSource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class GradesLoader extends AsyncTaskLoader<List<Grade>>
{
    private GradesDataSource dataSource;
    private int courseId;

    public GradesLoader(Context context, GradesDataSource dataSource,
                        int courseId)
    {
        super(context);
        this.dataSource = dataSource;
        this.courseId = courseId;
    }

    @Override
    public List<Grade> loadInBackground()
    {
        return this.dataSource.getGradesForCourse(courseId);
    }
}
