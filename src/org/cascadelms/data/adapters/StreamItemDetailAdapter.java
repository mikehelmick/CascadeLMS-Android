package org.cascadelms.data.adapters;

import java.text.SimpleDateFormat;

import org.cascadelms.R;
import org.cascadelms.data.models.Comment;
import org.cascadelms.data.models.StreamItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StreamItemDetailAdapter extends BaseAdapter
{
	Context context;
	StreamItem item;

	public StreamItemDetailAdapter( Context context )
	{
		this.context = context;
	}

	public void setItem( StreamItem item )
	{
		this.item = item;
	}

	@Override
	public int getCount()
	{
		if( item == null )
			return 0;
		else if( item.getComments() == null )
			return 1;
		return 1 + item.getComments().length;
	}

	@Override
	public Object getItem( int position )
	{
		if( position == 0 )
			return item;
		else if( item != null && item.getComments() != null )
			return item.getComments()[position - 1];
		return null;
	}

	@Override
	public long getItemId( int position )
	{
		return position;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		if( item == null )
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat( "MMM d, K:m a" );

		if( position == 0 )
		{
			/* Inflates a new view if the adapter doesn't provide one to reuse. */
			// if (convertView == null)
			{
				convertView = LayoutInflater.from( context ).inflate(
						R.layout.list_item_socialstream_post, parent, false );
			}

			if( convertView != null )
			{
				TextView dateLabel = (TextView) convertView
						.findViewById( R.id.socialstream_date_course );
				dateLabel.setText( dateFormat.format( item.getPostDate() ) );
				TextView authorLabel = (TextView) convertView
						.findViewById( R.id.socialstream_author );
				authorLabel.setText( item.getAuthor().getName() );
				TextView summaryLabel = (TextView) convertView
						.findViewById( R.id.socialstream_description );
				summaryLabel.setText( item.getBody() );
				TextView scoreLabel = (TextView) convertView
						.findViewById( R.id.socialstream_aplus_count );
				scoreLabel.setText( Integer.toString( item.getAPlusCount() ) );
				TextView commentLabel = (TextView) convertView
						.findViewById( R.id.socialstream_comment_count );

				int commentCount = item.getCommentCount();

				commentLabel.setText( convertView.getResources()
						.getQuantityString( R.plurals.comments, commentCount,
								commentCount ) );

				String authorAvatarURL = item.getAuthor().getGravatarURL();
				Drawable authorAvatar = null;
				/*
				 * TODO: the StreamItem can only provide the URL to the author's
				 * avatar image. The drawable needs to be loaded here.
				 */

				if( authorAvatar != null )
				{
					ImageView avatarImage = (ImageView) convertView
							.findViewById( R.id.socialstream_avatar );
					avatarImage.setImageDrawable( authorAvatar );
				}
			}
		} else
		{
			// TODO: Reenable convertView
			// if (convertView == null)
			{
				convertView = LayoutInflater.from( context ).inflate(
						R.layout.list_item_socialstream_comment, parent, false );
			}

			if( convertView != null )
			{
				Comment comment = item.getComments()[position - 1];

				TextView dateLabel = (TextView) convertView
						.findViewById( R.id.socialstream_date );
				dateLabel.setText( dateFormat.format( comment.getCreatedAt() ) );
				TextView authorLabel = (TextView) convertView
						.findViewById( R.id.socialstream_author );
				authorLabel.setText( comment.getAuthor() );
				TextView summaryLabel = (TextView) convertView
						.findViewById( R.id.socialstream_description );
				summaryLabel.setText( comment.getBody() );
			}
		}

		return convertView;
	}
}
