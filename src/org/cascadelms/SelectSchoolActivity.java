package org.cascadelms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import org.cascadelms.data.adapters.SchoolsAdapter;
import org.cascadelms.data.loaders.LoaderCodes;
import org.cascadelms.data.loaders.SchoolLoader;
import org.cascadelms.data.models.School;
import org.cascadelms.data.sources.AuthTokenInfo;
import org.cascadelms.data.sources.FakeDataSource;

import java.util.List;


public class SelectSchoolActivity extends FragmentActivity
        implements LoaderCallbacks<List<School>>
{
    private SchoolsDataSource dataSource;
    private SchoolsAdapter adapter;

    private EditText mSchoolUrl;

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

        final Spinner spinner = (Spinner) findViewById(R.id.school_spinner);

        if (spinner != null)
        {
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
                    School school = (School) spinner.getSelectedItem();

                    if (school != null)
                    {
                        String cascadeUrl = school.getCascadeUrl();

                        if (cascadeUrl != null)
                            mSchoolUrl.setText(cascadeUrl);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {

                }
            });
        }

        mSchoolUrl = (EditText) findViewById(R.id.cascade_url_editview);

        getSupportLoaderManager().initLoader(LoaderCodes.LOADER_CODE_SCHOOLS, null, this).forceLoad();
    }

    private void confirmSchool()
    {
        AuthTokenInfo tokenInfo = new AuthTokenInfo(this);

        tokenInfo.setCascadeUrl(mSchoolUrl.getText().toString());

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
