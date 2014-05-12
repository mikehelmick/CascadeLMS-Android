package org.cascadelms.fragments;

import java.util.List;

import org.cascadelms.R;
import org.cascadelms.StreamDetailActivity;
import org.cascadelms.data.adapters.StreamItemAdapter;
import org.cascadelms.data.loaders.CourseStreamItemLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.Course;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.data.sources.CascadeDataSource;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SocialStreamFragment extends ListFragment implements
        LoaderCallbacks<List<StreamItem>>
{
    private StreamDataSource streamDataSource;
    private StreamItemAdapter adapter;
    private TextView emptyView;

    private static final String ARGS_COURSE = "org.cascadelms.args_course";
    public static final String ARGS_POSTID = "org.cascadelms.extras.postid";

    public static SocialStreamFragment newInstance(Course course)
    {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_COURSE, course);
        SocialStreamFragment fragment = new SocialStreamFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        /* Initializes a data source and begins loading. */
        this.streamDataSource = CascadeDataSource.getInstance();
        this.adapter = new StreamItemAdapter(getActivity());

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        /* There are two loaders available to this Fragment. */

        if (this.getCourse() == null)
            /* This call initializes a Loader to load all StreamItems. */
            this.getActivity().getSupportLoaderManager()
                    .initLoader(LoaderCodes.LOADER_CODE_TOTAL_STREAM, null, this).forceLoad();
        else
		/*
		 * This call initializes a Loader to load only stream items for this
		 * Fragment's Course
		 */
            this.getActivity().getSupportLoaderManager()
                    .initLoader(LoaderCodes.LOADER_CODE_COURSE_STREAM, null, this).forceLoad();

        this.getListView().setAdapter(adapter);
        this.getListView().setOnItemClickListener(new SocialStreamItemClickListener());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_socialstream, null);
        this.emptyView = (TextView) view
                .findViewById(R.id.fragment_socialstream_empty);
        ((ListView) view.findViewById(android.R.id.list))
                .setEmptyView(emptyView);

        return view;
    }

    private class SocialStreamItemClickListener implements
            AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id)
        {
            StreamItem post = adapter.getItem(position);
            goToPostDetail(post);
        }
    }

    private void goToPostDetail(StreamItem parentPost)
    {
        Intent intent = new Intent(this.getActivity(), StreamDetailActivity.class);
        intent.putExtra(ARGS_POSTID, parentPost.getId());
        this.startActivity(intent);
    }

    @Override
    public Loader<List<StreamItem>> onCreateLoader(int id, Bundle args)
    {
        switch (id)
        {
            case LoaderCodes.LOADER_CODE_COURSE_STREAM:
            {
                return new CourseStreamItemLoader(this.getActivity(),
                        streamDataSource, this.getCourse().getId());
            }
            case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
            {
                return new CourseStreamItemLoader(this.getActivity(),
                        streamDataSource);
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public void onLoadFinished(Loader<List<StreamItem>> loader,
                               List<StreamItem> data)
    {
        switch (loader.getId())
        {
            case LoaderCodes.LOADER_CODE_COURSE_STREAM:
            case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
            {
                adapter.clear();
                if (data != null)
                    adapter.addAll(data);
                else
                    Toast.makeText(getActivity(), R.string.fragment_socialstream_list_failed,
                            Toast.LENGTH_SHORT).show();
                this.emptyView
                        .setText(R.string.fragment_socialstream_list_empty_message);
                break;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<List<StreamItem>> loader)
    {
        switch (loader.getId())
        {
            case LoaderCodes.LOADER_CODE_COURSE_STREAM:
            case LoaderCodes.LOADER_CODE_TOTAL_STREAM:
            {
                adapter.clear();
                break;
            }
        }
    }

    public interface StreamDataSource
    {
        public List<StreamItem> getAllStreamItems();

        public List<StreamItem> getStreamItemsForCourse(int courseId);

        public StreamItem getStreamItemDetail(long postId);
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
        if (this.getArguments() == null)
            return null;
        else
            return this.getArguments().getParcelable(ARGS_COURSE);
    }
}
