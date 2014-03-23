package org.cascadelms.data.adapters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.models.Assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AssignmentsAdapter extends BaseAdapter
{
	Context context;
	ArrayList<Assignment> assignments;

	public AssignmentsAdapter( Context context )
	{
		this.context = context;
		this.assignments = new ArrayList<Assignment>();
	}

	@Override
	public int getCount()
	{
		return assignments.size();
	}

	@Override
	public Object getItem( int position )
	{
		return assignments.get( position );
	}

	@Override
	public long getItemId( int position )
	{
		return position;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		/* Re-uses the convertView, if possible. */
		View view;
		if( convertView == null )
		{
			view = LayoutInflater.from( this.context ).inflate(
					R.layout.list_item_assignment, parent, false );
		} else
		{
			view = convertView;
		}
		Assignment assignment = (Assignment) this.getItem( position );

		/* Sets the list item text according to the Document's title. */
		( (TextView) view.findViewById( R.id.list_item_assignment_title ) )
				.setText( assignment.toString() );

		/* Sets the points earned/possible text. */
		String pointsString;
		NumberFormat format = DecimalFormat.getNumberInstance();
		format.setMaximumFractionDigits( 2 );
		if( Double.compare( assignment.getPointsEarned(), Double.NaN ) == 0 )
		{
			pointsString = this.context.getString(
					R.string.list_item_assignment_no_points,
					format.format( assignment.getPointsPossible() ) );
		} else
		{
			pointsString = this.context.getString(
					R.string.list_item_assignment_points,
					format.format( assignment.getPointsEarned() ),
					format.format( assignment.getPointsPossible() ) );
		}
		( (TextView) view.findViewById( R.id.list_item_assignment_points ) )
				.setText( pointsString );

		/* Sets the date field to the next upcoming date of the assignment. */
		String dateString = "";
		if( assignment.isUpcoming() )
		{
			dateString = this.context.getString(
					R.string.list_item_assignment_upcoming_date,
					assignment.getOpenDate() );
		} else if( assignment.isCurrent() )
		{
			dateString = this.context.getString(
					R.string.list_item_assignment_due_date,
					assignment.getDueDate() );
		} else if( assignment.isPastDue() )
		{
			dateString = this.context.getString(
					R.string.list_item_assignment_close_date,
					assignment.getCloseDate() );
		} else if( assignment.isClosed() )
		{
			dateString = this.context
					.getString( R.string.list_item_assignment_closed );
		} else
		{
			throw new IllegalStateException(
					"The assignment must be one of upcoming/current/past/closed" );
		}
		( (TextView) view.findViewById( R.id.list_item_assignment_date ) )
				.setText( dateString );

		return view;
	}

	public void setData( List<Assignment> data )
	{
		this.assignments.clear();
		this.assignments.addAll( data );
		this.notifyDataSetChanged();
	}

	public void clear()
	{
		this.assignments.clear();
		this.notifyDataSetInvalidated();
	}
}
