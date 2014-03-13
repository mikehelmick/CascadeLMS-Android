package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.data.models.BlogPost;
import org.cascadelms.fragments.CourseBlogFragment;
import org.cascadelms.fragments.CourseBlogFragment.BlogDataSource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class BlogPostLoader extends AsyncTaskLoader<List<BlogPost>>
{
	private BlogDataSource dataSource;
	private int courseId;

	public BlogPostLoader( Context context, BlogDataSource dataSource,
			int courseId )
	{
		super( context );
		this.dataSource = dataSource;
		this.courseId = courseId;
	}

	@Override
	public List<BlogPost> loadInBackground()
	{
		return this.dataSource.getBlogPostsForCourse( courseId );
	}
}
