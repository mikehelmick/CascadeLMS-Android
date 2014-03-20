package org.cascadelms.data.adapters;

import org.cascadelms.data.models.School;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SchoolsAdapter extends ArrayAdapter<School>
{
    public SchoolsAdapter( Context context )
    {
        super( context, android.R.layout.simple_spinner_item );
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        /* Inflates a new view if the adapter doesn't provide one to reuse. */
        if( convertView == null )
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(
                    android.R.layout.simple_spinner_item, parent, false );
        }

        if (convertView != null)
        {
            TextView label = (TextView) convertView
                    .findViewById(android.R.id.text1);
            label.setText( this.getItem( position ).getSchoolName() );
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        if( convertView == null )
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(
                    android.R.layout.simple_spinner_dropdown_item, parent, false );
        }

        if (convertView != null)
        {
            TextView label = (TextView) convertView
                    .findViewById(android.R.id.text1);
            label.setText( this.getItem( position ).getSchoolName() );
        }

        return convertView;
    }
}
