package org.cascadelms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;

import org.cascadelms.data.adapters.SchoolsAdapter;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.loaders.SchoolLoader;
import org.cascadelms.data.models.School;
import org.cascadelms.data.sources.FakeDataSource;

import java.util.List;


public class SelectSchoolActivity extends FragmentActivity
        implements LoaderCallbacks<List<School>>
{
    private SchoolsDataSource dataSource;
    private SchoolsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataSource = FakeDataSource.getInstance();
        adapter = new SchoolsAdapter(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_school);

        findViewById(R.id.next_button).setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick( View view )
                    {
                        confirmSchool();
                    }
                } );

        Spinner spinner = (Spinner) findViewById(R.id.school_spinner);

        if (spinner != null)
        {
            spinner.setAdapter(adapter);
        }

        getSupportLoaderManager().initLoader(LoaderCodes.LOADER_CODE_SCHOOLS, null, this).forceLoad();
    }

    private void confirmSchool()
    {
        Intent intent = new Intent( SelectSchoolActivity.this,
                LoginActivity.class );

        startActivity( intent );
    }

    @Override
    public Loader<List<School>> onCreateLoader(int id, Bundle args)
    {
        switch( id )
        {
            case LoaderCodes.LOADER_CODE_SCHOOLS:
            {
                return new SchoolLoader(this, dataSource);
            }
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<School>> loader, List<School> data)
    {
        switch( loader.getId() )
        {
            case LoaderCodes.LOADER_CODE_SCHOOLS:
            {
                adapter.clear();
                adapter.addAll(data);
                return;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<School>> loader)
    {
        switch( loader.getId() )
        {
            case LoaderCodes.LOADER_CODE_SCHOOLS:
            {
                this.adapter.clear();
                return;
            }
        }
    }

    public interface SchoolsDataSource
    {
        public List<School> getSchools();
    }
}
