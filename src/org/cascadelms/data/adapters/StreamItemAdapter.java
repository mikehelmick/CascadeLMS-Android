package org.cascadelms.data.adapters;

import java.text.SimpleDateFormat;

import org.cascadelms.R;
import org.cascadelms.data.loaders.ImageViewDownloadTask;
import org.cascadelms.data.models.StreamItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StreamItemAdapter extends ArrayAdapter<StreamItem>
{
    private static final int CACHE_SIZE = 20 * 1024 * 1024;
    private static LruCache<String, Bitmap> mImageMemCache;

	public StreamItemAdapter( Context context )
	{
		super( context, R.layout.list_item_socialstream_post );

        mImageMemCache = new LruCache<String, Bitmap>(CACHE_SIZE);
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
			summaryLabel.setText(Html.fromHtml(this.getItem( position ).getBody() ) );
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
            ImageView authorAvatar = (ImageView) convertView
                    .findViewById( R.id.socialstream_avatar );
            authorAvatar.setImageDrawable(null);

            Bitmap cachedBitmap = mImageMemCache.get(authorAvatarURL);

            if (cachedBitmap != null)
            {
                authorAvatar.setImageBitmap(cachedBitmap);
            }
            else
            {
                ImageViewDownloadTask downloadTask = new ImageViewDownloadTask(authorAvatar,
                        mImageMemCache);

                downloadTask.execute(authorAvatarURL);
            }
		}

		return convertView;
	}
}
