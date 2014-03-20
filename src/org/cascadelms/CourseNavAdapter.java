package org.cascadelms;

import java.util.ArrayList;

import org.cascadelms.data.models.Course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Used to map and list courses and subpages.
 */
public class CourseNavAdapter extends ArrayAdapter<Course>
{
	public CourseNavAdapter( Context context )
	{
		super( context, android.R.layout.simple_list_item_1 );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		/* Inflates a new view if the adapter doesn't provide one to reuse. */
		if( convertView == null )
		{
			convertView = LayoutInflater.from( this.getContext() ).inflate(
					android.R.layout.simple_list_item_1, parent, false );
		}

        if (convertView != null)
        {
            TextView label = (TextView) convertView
                    .findViewById(android.R.id.text1);
            label.setText( this.getItem( position ).getTitle() );
        }

		return convertView;
	}

	/**
	 * Gets all the Courses currently in the adapter.
	 * 
	 * @return an <code>ArrayList</code> of Courses
	 */
	public ArrayList<Course> getAllCourses()
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		for ( int i = 0; i < this.getCount(); i++ )
		{
			courses.add( this.getItem( i ) );
		}
		return courses;
	}
}