package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.data.models.Assignment;
import org.cascadelms.fragments.AssignmentsFragment.AssignmentsDataSource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class AssignmentsLoader extends AsyncTaskLoader<List<Assignment>>
{
    private AssignmentsDataSource source;
    private int courseId;

    public AssignmentsLoader(Context context, AssignmentsDataSource source,
                             int courseId)
    {
        super(context);
        this.source = source;
        this.courseId = courseId;
    }

    @Override
    public List<Assignment> loadInBackground()
    {
        return this.source.getAssignmentsForCourse(courseId);
    }
}