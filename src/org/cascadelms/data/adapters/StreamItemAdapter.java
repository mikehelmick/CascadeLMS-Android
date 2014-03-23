package org.cascadelms.data.adapters;

import org.cascadelms.R;
import org.cascadelms.data.models.StreamItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StreamItemAdapter extends ArrayAdapter<StreamItem>
{
    public StreamItemAdapter( Context context )
    {
        super( context, R.layout.list_item_socialstream_post);
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        /* Inflates a new view if the adapter doesn't provide one to reuse. */
        if( convertView == null )
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(
                    R.layout.list_item_socialstream_post, parent, false );
        }

        if (convertView != null)
        {
            TextView dateLabel = (TextView) convertView
                    .findViewById(R.id.socialstream_date_course);
            dateLabel.setText( this.getItem( position ).getSummaryDate().toString() );
            TextView summaryLabel = (TextView) convertView
                    .findViewById(R.id.socialstream_description);
            summaryLabel.setText( this.getItem(position).getSummary() );
        }

        return convertView;
    }
}
