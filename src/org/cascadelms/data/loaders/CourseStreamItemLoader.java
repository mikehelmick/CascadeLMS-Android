package org.cascadelms.data.loaders;

import java.util.List;

import org.cascadelms.data.models.StreamItem;
import org.cascadelms.fragments.SocialStreamFragment.StreamDataSource;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * An {@link AsyncTaskLoader} capable of loading data from a StreamDataSource.
 */
public class CourseStreamItemLoader extends AsyncTaskLoader<List<StreamItem>>
{
	private static final int ALL_ITEMS = -1;
	private StreamDataSource dataSource;
	private int courseId;

	/**
	 * This constructor creates a Loader to load only StreamItems that related
	 * to the given course id.
	 * 
	 * @param context
	 * @param dataSource
	 * @param courseId
	 */
	public CourseStreamItemLoader( Context context,
			StreamDataSource dataSource, int courseId )
	{
		super( context );
		this.dataSource = dataSource;
		this.courseId = courseId;
	}

	/**
	 * This constructor creates a Loader to load all StreamItems regardless of
	 * their course association.
	 * 
	 * @param context
	 * @param dataSource
	 */
	public CourseStreamItemLoader( Context context, StreamDataSource dataSource )
	{
		super( context );
		this.dataSource = dataSource;
		this.courseId = ALL_ITEMS;
	}

	@Override
	public List<StreamItem> loadInBackground()
	{
		if( this.courseId == ALL_ITEMS )
		{
			return this.dataSource.getAllStreamItems();
		} else
		{
			return this.dataSource.getStreamItemsForCourse( courseId );
		}
	}
}
