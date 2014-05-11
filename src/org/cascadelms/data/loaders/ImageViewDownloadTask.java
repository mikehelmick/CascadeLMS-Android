package org.cascadelms.data.loaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import org.cascadelms.CascadeApp;
import org.cascadelms.SimpleOAuth;
import org.cascadelms.data.sources.AuthTokenInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Task that allows for asynchronous network loading of images for ImageViews.
 */
public class ImageViewDownloadTask extends AsyncTask<String, Void, Bitmap>
{
    private static final int CACHE_SIZE = 20 * 1024 * 1024;
    private final WeakReference<ImageView> mImageViewRef;
    private LruCache<String, Bitmap> mCache;

    public ImageViewDownloadTask(ImageView imageView, LruCache cache)
    {
        mImageViewRef = new WeakReference<ImageView>(imageView);
        mCache = cache;
    }

    @Override
    protected Bitmap doInBackground(String... strings)
    {
        AuthTokenInfo tokenInfo = new AuthTokenInfo(CascadeApp.getContext());
        Bitmap bitmap = null;
        HttpURLConnection request = null;

        try
        {
            // TODO: Cache that works with older versions of Android.
            HttpResponseCache.install(new File(CascadeApp.getContext().getCacheDir(), "http"),
                    CACHE_SIZE);

            URL url = new URL(strings[0]);

            request = (HttpURLConnection) url.openConnection();
            request.setUseCaches(true);
            request.addRequestProperty("Accept", "text/xml");
            request.setRequestProperty(SimpleOAuth.OAUTH_TOKEN, tokenInfo.getAuthToken());

            request.connect();

            InputStream stream = request.getInputStream();

            bitmap = BitmapFactory.decodeStream(stream);

            mCache.put(strings[0], bitmap);

            stream.close();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        if (bitmap != null)
        {
            ImageView imageView = mImageViewRef.get();

            if (imageView != null)
            {
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }

        super.onPostExecute(bitmap);
    }
}
