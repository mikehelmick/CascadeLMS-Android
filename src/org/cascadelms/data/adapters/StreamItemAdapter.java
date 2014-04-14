package org.cascadelms.data.adapters;

import java.text.SimpleDateFormat;

import org.cascadelms.R;
import org.cascadelms.data.models.StreamItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StreamItemAdapter extends ArrayAdapter<StreamItem>
{
	public StreamItemAdapter( Context context )
	{
		super( context, R.layout.list_item_socialstream_post );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		/* Inflates a new view if the adapter doesn't provide one to reuse. */
		if( convertView == null )
		{
			convertView = LayoutInflater.from( this.getContext() ).inflate(
					R.layout.list_item_socialstream_post, parent, false );
		}

		if( convertView != null )
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat( "MMM d, K:m a" );

			TextView dateLabel = (TextView) convertView
					.findViewById( R.id.socialstream_date_course );
			dateLabel.setText( dateFormat.format( this.getItem( position )
					.getPostDate() ) );
			TextView authorLabel = (TextView) convertView
					.findViewById( R.id.socialstream_author );
			authorLabel
					.setText( this.getItem( position ).getAuthor().getName() );
			TextView summaryLabel = (TextView) convertView
					.findViewById( R.id.socialstream_description );
			summaryLabel.setText( this.getItem( position ).getBody() );
			TextView scoreLabel = (TextView) convertView
					.findViewById( R.id.socialstream_aplus_count );
			scoreLabel.setText( Integer.toString( this.getItem( position )
					.getAPlusCount() ) );
			TextView commentLabel = (TextView) convertView
					.findViewById( R.id.socialstream_comment_count );

			int commentCount = this.getItem( position ).getCommentCount();

			commentLabel.setText( convertView.getResources().getQuantityString(
					R.plurals.comments, commentCount, commentCount ) );

			String authorAvatarURL = this.getItem( position ).getAuthor()
					.getGravatarURL();
			Drawable authorAvatar = null;
			/*
			 * TODO: The Stream Item class can only provide the URL to fetch the
			 * author's avatar. The drawable needs to be loaded here.
			 */

			if( authorAvatar != null )
			{
				ImageView avatarImage = (ImageView) convertView
						.findViewById( R.id.socialstream_avatar );
				avatarImage.setImageDrawable( authorAvatar );
			}
		}

		return convertView;
	}
}
