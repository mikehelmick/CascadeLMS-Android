package org.cascadelms.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.cascadelms.data.models.StreamItem;
import org.cascadelms.fragments.SocialStreamFragment.StreamDataSource;

import java.util.List;

/**
 * An {@link android.support.v4.content.AsyncTaskLoader} capable of loading data on a detailed post from a StreamDataSource.
 */
public class StreamItemDetailLoader extends AsyncTaskLoader<StreamItem>
{
	private StreamDataSource dataSource;
	private int postId;

	/**
	 * This constructor creates a Loader to load a more detailed StreamItem for the
	 * given post id.
	 *
	 * @param context
	 * @param dataSource
	 * @param postId
	 */
	public StreamItemDetailLoader(Context context,
                                  StreamDataSource dataSource, int postId)
	{
		super( context );
		this.dataSource = dataSource;
		this.postId = postId;
	}

	@Override
	public StreamItem loadInBackground()
	{
		{
			return this.dataSource.getStreamItemDetail( postId );
		}
	}
}
