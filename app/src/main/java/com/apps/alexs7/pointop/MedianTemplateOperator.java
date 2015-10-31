package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

/**
 * Created by alex on 02/10/15.
 */
public class MedianTemplateOperator {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript mRS = null;
    private ScriptC_threshold mScript = null;

    public MedianTemplateOperator(Context ctx) {
        mRS = RenderScript.create(ctx);
        mScript = new ScriptC_threshold(mRS, ctx.getResources(), R.raw.mediantemplate);
    }

    public Bitmap apply(Bitmap origBmp) {
        int width = origBmp.getWidth();
        int height = origBmp.getHeight();

        inAllocation = Allocation.createFromBitmap(mRS, origBmp);
        outAllocation = Allocation.createFromBitmap(mRS, Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888));

        mScript.set_width(width-1);
        mScript.set_height(height-1);
        mScript.set_inPixels(inAllocation);
        mScript.forEach_root(inAllocation,outAllocation);

        outAllocation.copyTo(origBmp);

        return origBmp;
    }
}
