package com.example.kalpesh.textaddonlayout.CusttomImageView;

import android.net.Uri;

/**
 * Created by omsai on 8/6/2016.
 */
public interface SaveCallback extends Callback{
    void onSuccess(Uri outputUri);
    void onError();
}
