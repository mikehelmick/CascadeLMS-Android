package org.cascadelms;

import org.cascadelms.data.adapters.StreamItemDetailAdapter;
import org.cascadelms.data.loaders.StreamItemDetailLoader;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.models.StreamItem;
import org.cascadelms.data.sources.CascadeDataSource;
import org.cascadelms.fragments.SocialStreamFragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.widget.ListView;

public class StreamDetailActivity extends CascadeActivity implements
        LoaderCallbacks<StreamItem>
{
    private int postId;
    private SocialStreamFragment.StreamDataSource streamDataSource;
    private StreamItemDetailAdapter adapter;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_stream_detail);

        /* Gets the Course data provided by the Intent that started this. */
        Bundle extras = this.getIntent().getExtras();
        this.postId = extras.getInt(SocialStreamFragment.ARGS_POSTID);

		/* Initializes a data source and begins loading. */
        this.streamDataSource = CascadeDataSource.getInstance();
        this.adapter = new StreamItemDetailAdapter(this);

        this.getSupportLoaderManager()
                .initLoader(LoaderCodes.LOADER_CODE_STREAM_DETAIL, null, this).forceLoad();

        ListView streamList = (ListView) findViewById( android.R.id.list );

        if (streamList != null)
        {
            streamList.setAdapter(adapter);
        }

        // No parent in the XML, display as up programmatically.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<StreamItem> onCreateLoader( int id, Bundle args )
    {
        switch( id )
        {
            case LoaderCodes.LOADER_CODE_STREAM_DETAIL:
            {
                return new StreamItemDetailLoader( this,
                        streamDataSource, postId );
            }
            default:
            {
                return null;
            }
        }
    }

    @Override
    public void onLoadFinished( Loader<StreamItem> loader,
                                StreamItem data )
    {
        switch( loader.getId() )
        {
            case LoaderCodes.LOADER_CODE_STREAM_DETAIL:
            {
                adapter.setItem(data);
                break;
            }
        }

    }

    @Override
    public void onLoaderReset( Loader<StreamItem> loader )
    {
        switch( loader.getId() )
        {
            case LoaderCodes.LOADER_CODE_STREAM_DETAIL:
            {
                adapter.setItem(null);
                break;
            }
        }
    }
}
