package android.epsi.com.bebeer.activities.profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by fx on 21/01/2016.
 * Download an image and display it on a view
 */
class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = "ImageDownloader";

    private final ImageView mImageView;

    /**
     * View to put the image into
     *
     * @param imageView
     */
    public ImageDownloader(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitMap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitMap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e(TAG, "Error while fetching " + url, e);
        }
        return bitMap;
    }

    protected void onPostExecute(Bitmap result) {
        mImageView.setImageBitmap(result);
    }
}
