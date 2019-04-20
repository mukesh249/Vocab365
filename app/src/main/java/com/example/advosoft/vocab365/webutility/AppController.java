package com.example.advosoft.vocab365.webutility;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mAppController;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppController =  this;
    }

    //Getting App Controller Instance
    public static synchronized AppController getInstance()
    {
        Log.d("X","AppController Instance Passed");
        return mAppController;
    }

    //Getting Request Queue
    public RequestQueue getRequestQueue()
    {
        Log.d("X","Request que passed");
        if(mRequestQueue==null)
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    //Getting image Loader
    public ImageLoader getImageLoader()
    {
        Log.d("X","ImageLoader passed");
        getRequestQueue();
        if(mImageLoader==null)
        {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    //Adding to requestque with tag
    public <T> void addToRequestQueue(Request<T> req,String tag)
    {
        Log.i("X","Added to Request Que");
        req.setTag(TextUtils.isEmpty(tag)? TAG : tag);
        getRequestQueue().add(req);
    }

    //Adding to Request que without custom tag
    public <T> void addToRequestQueue(Request<T> req)
    {
        Log.i("X","Added to Request Que");
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(String tag)
    {
        if(mRequestQueue!=null)
        {
            mRequestQueue.cancelAll(tag);
        }
    }


}