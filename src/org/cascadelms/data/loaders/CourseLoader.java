package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.MainActivity.CourseDataSource;
import org.cascadelms.data.models.Course;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class CourseLoader extends AsyncTaskLoader<List<Course>>
{
	private CourseDataSource dataSource;
	
	public CourseLoader( Context context, CourseDataSource dataSource ) 
	{
		super(context);
		this.dataSource = dataSource;
	}

	@Override
	public List<Course> loadInBackground() 
	{
		return dataSource.getAvailableCourses();
	}
}
