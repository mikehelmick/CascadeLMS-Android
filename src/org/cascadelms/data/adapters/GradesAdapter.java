package org.cascadelms.data.adapters;

import org.cascadelms.R;
import org.cascadelms.data.models.Grade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GradesAdapter extends ArrayAdapter<Grade>
{
    public GradesAdapter(Context context)
    {
        super(context, R.layout.list_item_grade);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view;
        if (convertView != null)
        {
            view = convertView;
        } else
        {
            view = LayoutInflater.from(this.getContext()).inflate(
                    R.layout.list_item_grade, parent, false);
        }
        TextView title = (TextView) view.findViewById(R.id.gradeTitle);
        title.setText(this.getItem(position).toString());

        return view;
    }
}
