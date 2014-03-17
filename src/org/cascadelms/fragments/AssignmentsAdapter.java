package org.cascadelms.fragments;

import java.util.logging.Logger;

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

		/* Sets the points earned/possible text. */
		String pointsString;
		if( Double.compare( assignment.getPointsEarned(), Double.NaN ) == 0 )
		{
			pointsString = this.getContext().getString(
					R.string.list_item_assignment_no_points,
					assignment.getPointsPossible() );
		} else
		{
			Logger.getLogger( AssignmentsAdapter.class.getName() ).info(
					"Earned: " + assignment.getPointsEarned() );
			pointsString = this.getContext().getString(
					R.string.list_item_assignment_points,
					assignment.getPointsEarned(),
					assignment.getPointsPossible() );
		}
		( (TextView) view.findViewById( R.id.list_item_assignment_points ) )
				.setText( pointsString );

		/* Sets the date field to the next upcoming date of the assignment. */
		String dateString = "";
		if( assignment.isUpcoming() )
		{
			dateString = this.getContext().getString(
					R.string.list_item_assignment_upcoming_date,
					assignment.getOpenDate() );
		} else if( assignment.isCurrent() )
		{
			dateString = this.getContext().getString(
					R.string.list_item_assignment_due_date,
					assignment.getDueDate() );
		} else if( assignment.isPastDue() )
		{
			dateString = this.getContext().getString(
					R.string.list_item_assignment_close_date,
					assignment.getCloseDate() );
		} else if( assignment.isClosed() )
		{
			dateString = this.getContext().getString(
					R.string.list_item_assignment_closed );
		} else
		{
			throw new IllegalStateException(
					"The assignment must be one of upcoming/current/past/closed" );
		}
		( (TextView) view.findViewById( R.id.list_item_assignment_date ) )
				.setText( dateString );

		return view;
	}
}
