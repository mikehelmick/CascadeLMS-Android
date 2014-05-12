package org.cascadelms.data.adapters;

import java.text.SimpleDateFormat;

import org.cascadelms.R;
import org.cascadelms.data.models.BlogPost;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BlogPostAdapter extends ArrayAdapter<BlogPost>
{
    public BlogPostAdapter(Context context)
    {
        super(context, R.layout.list_item_courseblog);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        /* Inflates a new view if the adapter doesn't provide one to reuse. */
        if (convertView == null)
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(
                    R.layout.list_item_courseblog, parent, false);
        }

        if (convertView != null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, h:mm a");

            TextView titleLabel = (TextView) convertView
                    .findViewById(R.id.courseblog_title);
            titleLabel.setText(this.getItem(position).getTitle());
            TextView authorLabel = (TextView) convertView
                    .findViewById(R.id.courseblog_author);
            authorLabel.setText(this.getItem(position).getAuthor());
            TextView dateLabel = (TextView) convertView
                    .findViewById(R.id.courseblog_date_course);
            dateLabel.setText(dateFormat.format(this.getItem(position)
                    .getPostedDate()));
            TextView summaryLabel = (TextView) convertView
                    .findViewById(R.id.courseblog_description);
            summaryLabel.setText(Html.fromHtml(this.getItem(position).getBody()));
            TextView scoreLabel = (TextView) convertView
                    .findViewById(R.id.courseblog_aplus_count);
            scoreLabel.setText(Integer.toString(this.getItem(position)
                    .getaPlusCount()));
            TextView commentLabel = (TextView) convertView
                    .findViewById(R.id.courseblog_comment_count);

            int commentCount = this.getItem(position).getCommentCount();

            commentLabel.setText(convertView.getResources().getQuantityString(
                    R.plurals.comments, commentCount, commentCount));
        }

        return convertView;
    }
}
