package org.cascadelms.data.adapters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cascadelms.R;
import org.cascadelms.data.models.Assignment;
import org.cascadelms.data.models.Assignment.AssignmentComparator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AssignmentsAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<Object> items;

	/* Constants */
	private static final int ITEM_TYPE_ASSIGNMENT = 0;
	private static final int ITEM_TYPE_DIVIDER = 1;

	public AssignmentsAdapter( Context context )
	{
		this.context = context;
		this.items = new ArrayList<Object>();
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}

	@Override
	public boolean isEnabled( int position )
	{
		{
			Object item = items.get( position );
			if( item instanceof Assignment )
			{
				return true;
			} else if( item instanceof Divider )
			{
				return false;
			} else
			{
				throw new IllegalStateException(
						"An item in the list was neither an Assignment nor Divider." );
			}
		}
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	@Override
	public Object getItem( int position )
	{
		return items.get( position );
	}

	@Override
	public long getItemId( int position )
	{
		return position;
	}

	@Override
	public int getViewTypeCount()
	{
		return 2;
	}

	@Override
	public int getItemViewType( int position )
	{
		Object item = items.get( position );
		if( item instanceof Assignment )
		{
			return ITEM_TYPE_ASSIGNMENT;
		} else if( item instanceof Divider )
		{
			return ITEM_TYPE_DIVIDER;
		} else
		{
			throw new IllegalStateException(
					"An item in the list was neither an Assignment nor Divider." );
		}
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		switch( this.getItemViewType( position ) )
		{
			case ITEM_TYPE_ASSIGNMENT:
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
				( (TextView) view
						.findViewById( R.id.list_item_assignment_title ) )
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
				( (TextView) view
						.findViewById( R.id.list_item_assignment_points ) )
						.setText( pointsString );

				/*
				 * Sets the date field to the next upcoming date of the
				 * assignment.
				 */
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
			case ITEM_TYPE_DIVIDER:
			{
				/* TODO create an actual XML file to inflate for dividers. */
				TextView dividerView = (TextView) LayoutInflater.from(
						this.context ).inflate( R.layout.list_item_divider,
						parent, false );
				dividerView
						.setText( ( (Divider) this.getItem( position ) ).title );
				return dividerView;
			}
			default:
			{
				throw new IllegalStateException(
						"Unrecognized item type in AssignmentsAdapter.getView()" );
			}
		}

	}

	public void setData( List<? extends Assignment> data )
	{
		/* Sorts the Documents and replaces the list with them. */
		Collections.sort( data, new AssignmentComparator() );
		this.items.clear();
		this.items.addAll( data );
		if( data.isEmpty() )
		{
			/* Nothing left to do. */
			return;
		}

		/* Adds in list separators at the appropriate indexes */
		int index = 0;
		if( ( (Assignment) items.get( 0 ) ).isUpcoming() )
		{
			items.add( 0, new Divider( "Upcoming" ) ); // TODO Use string
														// resource.
			index = 1;
		}
		for ( int i = index; i < items.size(); i++ )
		{
			if( ( (Assignment) items.get( i ) ).isCurrent() )
			{
				items.add( i, new Divider( "Current" ) ); // TODO Use string
															// resource.
				index = i + 1;
				break;
			}
		}
		for ( int i = index; i < items.size(); i++ )
		{
			if( ( (Assignment) items.get( i ) ).isPastDue() )
			{
				items.add( i, new Divider( "Past Due" ) ); // TODO Use string
															// resource.
				index = i + 1;
				break;
			}
		}
		for ( int i = index; i < items.size(); i++ )
		{
			if( ( (Assignment) items.get( i ) ).isClosed() )
			{
				items.add( i, new Divider( "Closed" ) ); // TODO Use string
															// resource.
				break;
			}
		}

		this.notifyDataSetChanged();
	}

	public void clear()
	{
		this.items.clear();
		this.notifyDataSetInvalidated();
	}

	private class Divider
	{
		private String title;

		private Divider( String title )
		{
			this.title = title;
		}
	}
}
