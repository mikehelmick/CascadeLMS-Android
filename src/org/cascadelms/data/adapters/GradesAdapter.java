package org.cascadelms.data.adapters;

import org.cascadelms.data.models.Grade;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class GradesAdapter extends ArrayAdapter<Grade>
{
	public GradesAdapter( Context context )
	{
		super( context, android.R.layout.simple_list_item_1 );// R.layout.list_item_grade
																// );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		// TODO Auto-generated method stub
		return super.getView( position, convertView, parent );
	}
}
