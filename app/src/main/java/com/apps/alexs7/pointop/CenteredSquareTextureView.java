package com.apps.alexs7.pointop;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

/**
 * Created by alex on 25/07/15.
 */
public class CenteredSquareTextureView extends TextureView {
    public CenteredSquareTextureView(Context context) {
        super(context);
    }

    public CenteredSquareTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenteredSquareTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentHeight, parentHeight); //height will always be < width
        }
    }
}
