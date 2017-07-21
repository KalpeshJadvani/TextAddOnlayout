package com.example.kalpesh.textaddonlayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kalpesh.textaddview.MainFunctionRun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OpeanGallery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opean_gallery);

            findViewById(R.id.opengallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(weHavePermissionToWriteExternalStorage() && weHavePermissionToReadExternalStorage() || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

                          Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                          startActivityForResult(Intent.createChooser(intent, "Complete action using"), 102);


//                        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                        getIntent.setType("image/*");
//                        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickIntent.setType("image/*");
//                        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
//                        startActivityForResult(chooserIntent, 102);


                    }else {
                        if(weHavePermissionToReadExternalStorage()){
                            requestREAD_EXTERNALPermissionFirst();
                        }else {
                            requestWtrite_EXTERNALPermissionFirst();
                        }
                    }
                }
            });


            findViewById(R.id.defult).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(weHavePermissionToWriteExternalStorage() && weHavePermissionToReadExternalStorage() || android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

                        try {

                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("activity", "defult");
                            startActivity(intent);

                        } catch (Exception ioe) {
                            //handle error
                        }

                    }else {

                        if(weHavePermissionToReadExternalStorage()){
                         requestREAD_EXTERNALPermissionFirst();
                        }else {
                            requestWtrite_EXTERNALPermissionFirst();
                        }

                    }

                }
            });

    }

    String picturePath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 102) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {

                    if(selectedUri.toString().toLowerCase().contains("content:".toLowerCase())){
                        String[] fileColumns = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedUri, fileColumns, null, null, null);
                        cursor.moveToFirst();
                        int cIndex = cursor.getColumnIndex(fileColumns[0]);
                        picturePath = cursor.getString(cIndex);
                        cursor.close();

                    }else {
                        picturePath = data.getData().getPath();
                    }


                    if (picturePath != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("activity","opengallery");
                        intent.putExtra("picture", picturePath);
                        startActivity(intent);
                    }


                } else {

                Toast.makeText(OpeanGallery.this, " Getting Error......", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }






    public String uriToPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void requestREAD_EXTERNALPermissionFirst() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "We need permission so you can read .", Toast.LENGTH_LONG).
                    show();
            requestForResultExternalStoragePermission();
        } else {
            requestForResultExternalStoragePermission();
        }
    }




    private void requestForResultExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 112);

    }


    //read external storage

    private boolean weHavePermissionToReadExternalStorage()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }



    //write external storage


    private boolean weHavePermissionToWriteExternalStorage()
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestWtrite_EXTERNALPermissionFirst() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "We need permission so you can read .", Toast.LENGTH_LONG).
                    show();
            requestForResultExternalWriteStoragePermission();
        } else {
            requestForResultExternalWriteStoragePermission();
        }
    }

    private void requestForResultExternalWriteStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 112);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 112 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            onCreate(new Bundle());
        }
    }

}

