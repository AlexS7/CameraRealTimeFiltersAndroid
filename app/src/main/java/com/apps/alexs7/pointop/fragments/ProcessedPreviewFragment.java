package com.apps.alexs7.pointop.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apps.alexs7.pointop.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessedPreviewFragment extends Fragment {

    private ImageView mImageView;

    public ProcessedPreviewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_processed_preview, container, false);
    }

    @Override
    public void onStart() {
        mImageView = (ImageView) getView().findViewById(R.id.processed_preview_img_view);
        super.onStart();
    }

    public void setImageViewBitmap(Bitmap bmp){
        if(mImageView != null) {
            mImageView.setImageBitmap(bmp);
        }
    }
}
