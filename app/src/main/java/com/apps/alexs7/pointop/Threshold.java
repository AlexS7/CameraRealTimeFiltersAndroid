package com.apps.alexs7.pointop;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;

/**
 * Created by alex on 09/08/15.
 */
public class Threshold {

    private Allocation inAllocation;
    private Allocation outAllocation;
    private RenderScript mRS = null;
    private ScriptC_threshold mScript = null;
    private ScriptC_adaptivethreshold mScriptAdaptiveThresholding = null;

    public Threshold(Context ctx) {
        mRS = RenderScript.create(ctx);
        mScript = new ScriptC_threshold(mRS, ctx.getResources(), R.raw.threshold);
        mScriptAdaptiveThresholding = new ScriptC_adaptivethreshold(mRS, ctx.getResources(), R.raw.adaptivethreshold);
    }

    public Bitmap apply(Bitmap origBmp) {
        int width = origBmp.getWidth();
        int height = origBmp.getHeight();

        inAllocation = Allocation.createFromBitmap(mRS, origBmp);
        outAllocation = Allocation.createFromBitmap(mRS, Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888));

        mScriptAdaptiveThresholding.set_width(width-1);
        mScriptAdaptiveThresholding.set_height(height-1);
        mScriptAdaptiveThresholding.set_inPixels(inAllocation);
        mScriptAdaptiveThresholding.forEach_root(inAllocation,outAllocation);

        outAllocation.copyTo(origBmp);

        return origBmp;
    }

    public Bitmap applyAdaptiveThresholding(Bitmap origBmp) {
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
