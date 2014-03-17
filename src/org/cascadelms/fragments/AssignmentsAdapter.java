package org.cascadelms.fragments;

import org.cascadelms.R;
import org.cascadelms.data.models.Assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AssignmentsAdapter extends ArrayAdapter<Assignment>
{
	public AssignmentsAdapter( Context context )
	{
		super( context, R.layout.list_item_assignment );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		/* Re-uses the convertView, if possible. */
		View view;
		if( convertView == null )
		{
			view = LayoutInflater.from( this.getContext() ).inflate(
					R.layout.list_item_assignment, parent, false );
		} else
		{
			view = convertView;
		}
		Assignment assignment = this.getItem( position );

		/* Sets the list item text according to the Document's title. */
		( (TextView) view.findViewById( R.id.list_item_assignment_title ) )
				.setText( assignment.toString() );

		return view;
	}
}
