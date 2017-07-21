package com.example.kalpesh.textaddonlayout.CusttomImageView;

import android.graphics.Bitmap;

/**
 * Created by omsai on 8/6/2016.
 */
public interface CropCallback extends Callback {
    void onSuccess(Bitmap cropped);
    void onError();
}
