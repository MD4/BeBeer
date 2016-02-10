package android.epsi.com.bebeer.services.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by fhibon on 28/01/2016.
 * Interface to access beers image: from cache or from the internet
 */
public class ApiImageAccessor {

    private static final String TAG = "ApiImageAccessor";

    private static ApiImageAccessor mInstance = new ApiImageAccessor();
    private final LruCache<String, Bitmap> mCache;

    private ApiImageAccessor() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        int size = maxMemory / 8;
        mCache = new LruCache<String, Bitmap>(size) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static ApiImageAccessor getInstance() {
        return mInstance;
    }

    /**
     * Load image from given URL, and display it into the view
     *
     * @param imageView
     * @param imageUrl
     */
    public void displayImageToView(ImageView imageView, String imageUrl) {
        Bitmap bitmap = this.getBitmapFromCache(imageUrl);

        if (bitmap == null) {
            new AsyncCacheLoader(imageView)
                    .execute(imageUrl);

        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Add given bitmap to the cache
     *
     * @param id
     * @param bitmap
     */
    private void addBitmapToCache(String id, Bitmap bitmap) {
        mCache.put(id, bitmap);
    }

    /**
     * Retrieve image from cache
     *
     * @param id Id of image
     * @return Bitmap or null
     */
    private Bitmap getBitmapFromCache(String id) {
        return mCache.get(id);
    }

    /**
     * Async task: download an image and add it to the cache
     */
    private class AsyncCacheLoader extends AsyncTask<String, Void, Bitmap> {

        private final ImageView mView;
        private String mUrl;

        public AsyncCacheLoader(ImageView imageView) {
            mView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            mUrl = params[0];
            Bitmap bitMap = null;
            try {
                InputStream in = new java.net.URL(mUrl).openStream();
                bitMap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

                Log.e(TAG, "Error while fetching " + mUrl, e);
            }
            return bitMap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mView.setImageBitmap(bitmap);
                addBitmapToCache(mUrl, bitmap);
            }
        }
    }
}
