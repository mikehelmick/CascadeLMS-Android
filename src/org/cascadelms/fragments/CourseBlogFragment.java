package org.cascadelms.fragments;

import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.adapters.BlogPostAdapter;
import org.cascadelms.data.loaders.BlogPostLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.BlogPost;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.sources.FakeDataSource;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CourseBlogFragment extends ListFragment implements
		LoaderCallbacks<List<BlogPost>>
{
	private BlogDataSource blogDataSource;
    private BlogPostAdapter adapter;

	/* Constants */
	private static final String ARGS_COURSE = "org.cascadelms.args_course";

	public static CourseBlogFragment newInstance( Course course )
	{
		Bundle args = new Bundle();
		args.putParcelable( ARGS_COURSE, course );
		CourseBlogFragment fragment = new CourseBlogFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		/* Initializes a data source and begins loading. */
		this.blogDataSource = FakeDataSource.getInstance();
		this.getActivity().getSupportLoaderManager()
				.initLoader( LoaderCodes.LOADER_CODE_BLOG, null, this )
				.forceLoad();
        this.adapter = new BlogPostAdapter(getActivity());

		super.onCreate( savedInstanceState );
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState )
	{
		super.onCreateView( inflater, container, savedInstanceState );

		return inflater.inflate( R.layout.fragment_courseblog, null );
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        this.getListView().setAdapter( adapter );
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
	public Loader<List<BlogPost>> onCreateLoader( int id, Bundle args )
	{
		switch( id )
		{
			case LoaderCodes.LOADER_CODE_BLOG:
			{
				return new BlogPostLoader( this.getActivity(), blogDataSource,
						this.getCourse().getId() );
			}
			default:
			{
				return null;
			}
		}
	}

	@Override
	public void onLoadFinished( Loader<List<BlogPost>> loader,
			List<BlogPost> data )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_BLOG:
			{
                adapter.clear();
                adapter.addAll(data);
				break;
			}
		}

	}

	@Override
	public void onLoaderReset( Loader<List<BlogPost>> loader )
	{
		switch( loader.getId() )
		{
			case LoaderCodes.LOADER_CODE_BLOG:
			{
                adapter.clear();
				break;
			}
		}
	}

	public interface BlogDataSource
	{
		public List<BlogPost> getBlogPostsForCourse( int courseId );
	}

	/**
	 * Retrieves the {@link Course} provided as an argument to this Fragment.
	 * The value cannot be stored, because the Fragment may be recreated by the
	 * FragmentManager at any time.
	 * 
	 * @return the <code>Course</code>
	 */
	private Course getCourse()
	{
		return this.getArguments().getParcelable( ARGS_COURSE );
	}
}
