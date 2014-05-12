package org.cascadelms.data.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import org.cascadelms.SelectSchoolActivity.SchoolsDataSource;
import org.cascadelms.data.models.School;

import java.util.List;

public class SchoolLoader extends AsyncTaskLoader<List<School>>
{
    private SchoolsDataSource dataSource;

    public SchoolLoader(Context context, SchoolsDataSource dataSource)
    {
        super(context);
        this.dataSource = dataSource;
    }

    @Override
    public List<School> loadInBackground()
    {
        return dataSource.getSchools();
    }
}
