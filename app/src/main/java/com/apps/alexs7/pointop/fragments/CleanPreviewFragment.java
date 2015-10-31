package com.apps.alexs7.pointop.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.apps.alexs7.pointop.CameraUtilities;
import com.apps.alexs7.pointop.CenteredSquareTextureView;
import com.apps.alexs7.pointop.R;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation" ) //For camera
public class CleanPreviewFragment extends Fragment implements TextureView.SurfaceTextureListener {

    OnBitmapUpdatedListener mCallback;
    private CenteredSquareTextureView mTextureView;
    private Camera mCamera;
    private int currentCameraSide;

    public CleanPreviewFragment() {}

    public interface OnBitmapUpdatedListener {
        void onCleanPreviewBitmapUpdated(Bitmap bmp);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        currentCameraSide = Camera.CameraInfo.CAMERA_FACING_BACK;
        if(!CameraUtilities.isCameraInUse(mCamera)) {
            mCamera = Camera.open(currentCameraSide);
            if(fragmentOrientation() == Configuration.ORIENTATION_PORTRAIT){
                mCamera.setDisplayOrientation(90);
            }
        }

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnBitmapUpdatedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBitmapUpdatedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_clean_preview, container, false);

        return fragmentView;
    }

    @Override
    public void onStart() {
        mTextureView = (CenteredSquareTextureView) getView().findViewById(R.id.clean_preview_txview);
        mTextureView.setSurfaceTextureListener(this);
        setSwitchCamerasListener();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!CameraUtilities.isCameraInUse(mCamera)) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                mCamera.setDisplayOrientation(90);
            }
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> resolutionSizes = parameters.getSupportedPictureSizes();
        Camera.Size optimalSize = CameraUtilities.getOptimalPreviewSize(resolutionSizes, width, height);
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewCallback(null);
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        Bitmap origBmp = mTextureView.getBitmap();
        mCallback.onCleanPreviewBitmapUpdated(origBmp);
    }

    public void stopPreview(){
        if(CameraUtilities.isCameraInUse(mCamera)) {
           mCamera.stopPreview();
        }
    }

    private void setSwitchCamerasListener() {
        mTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CameraUtilities.isCameraInUse(mCamera)){
                    if(currentCameraSide == Camera.CameraInfo.CAMERA_FACING_FRONT){
                        currentCameraSide = Camera.CameraInfo.CAMERA_FACING_BACK;
                    }else{
                        currentCameraSide = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    }

                    mCamera = CameraUtilities.releaseCamera(mCamera);
                    mCamera = Camera.open(currentCameraSide);
                    try {
                        mCamera.setPreviewTexture(mTextureView.getSurfaceTexture());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(fragmentOrientation() == Configuration.ORIENTATION_PORTRAIT){
                        mCamera.setDisplayOrientation(90);
                    }
                    mCamera.startPreview();
                }
            }
        });
    }

    private int fragmentOrientation(){
        return getActivity().getResources().getConfiguration().orientation;
    }
}
