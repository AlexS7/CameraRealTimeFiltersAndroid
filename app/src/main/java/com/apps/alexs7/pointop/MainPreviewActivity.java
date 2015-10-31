package com.apps.alexs7.pointop;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.apps.alexs7.pointop.fragments.CleanPreviewFragment;

import org.opencv.android.OpenCVLoader;


public class MainPreviewActivity extends Activity
        implements CleanPreviewFragment.OnBitmapUpdatedListener {

    private BProcessor bProcessor;
    private FragmentHelper fragmentHelper;
    private UIHelper uiHelper;
    private boolean processing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_preview);

        if(!OpenCVLoader.initDebug()){
            Log.d("ERROR", "Unable to load OpenCV");
        }
        else{
            Log.d("SUCCESS", "OpenCV loaded");
        }

        bProcessor = new BProcessor(this);
        fragmentHelper = new FragmentHelper(this);
        uiHelper = new UIHelper(this,bProcessor);

        fragmentHelper.setupFragments();
        uiHelper.buildListWithChoices();

        Toast.makeText(getApplicationContext(),"Tap preview to switch cameras", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCleanPreviewBitmapUpdated(Bitmap origBmp) {
        if(fragmentHelper.getProcessedPreviewFragment() != null) {
            if(!processing){
                processing = true;
                new ProcessBitmap().execute(origBmp);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main_preview);

        fragmentHelper.setupFragments();
        uiHelper.buildListWithChoices();
    }

    private class ProcessBitmap extends AsyncTask<Bitmap, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap resultBitmap;
            resultBitmap = bProcessor.processBitmap(bitmaps[0]);
            return resultBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            processing = false;
            fragmentHelper.getProcessedPreviewFragment().setImageViewBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }
}
