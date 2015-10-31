package com.apps.alexs7.pointop;

import android.hardware.Camera;
import android.util.Size;

import java.util.List;

/**
 * Created by alex on 25/07/15.
 */
@SuppressWarnings("deprecation" ) //For camera
public class CameraUtilities {

    public static Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.05;
        double targetRatio = (double) w/h;

        if (sizes==null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        int targetHeight = h;

        // Find size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public static boolean isCameraInUse(Camera c) {
        c = null;
        try {
            c = Camera.open();
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (c != null) c.release();
        }
        return false;
    }

    public static Camera releaseCamera(Camera mCamera) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        return mCamera;
    }
}
