package org.cascadelms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.cascadelms.data.models.School;

import java.util.List;


public class SelectSchoolActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    private void confirmSchool()
    {
        Intent intent = new Intent( SelectSchoolActivity.this,
                LoginActivity.class );

        startActivity( intent );
    }

    public interface SchoolsDataSource
    {
        public List<School> getSchools();
    }
}
