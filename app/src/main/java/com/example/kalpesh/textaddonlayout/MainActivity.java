package com.example.kalpesh.textaddonlayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kalpesh.textaddonlayout.CusttomImageView.CropImageView;
import com.example.kalpesh.textaddview.MainFunctionRun;
import com.example.kalpesh.textaddview.StickerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements StickerView.Returnno{
    RelativeLayout MainLayout;
    FrameLayout canvas;
    MainFunctionRun mainFunctionRun;
    public static Bitmap ThirdBitFinal;
    public static CropImageView mCropView;
    String path;
    String  ActName;
    public static int topMargin;
    public static int bottomMargin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MainLayout = (RelativeLayout) findViewById(R.id.mainlayout);

        canvas = (FrameLayout) findViewById(R.id.myframe);

        mainFunctionRun = new MainFunctionRun(MainActivity.this, MainLayout, canvas);

        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mCropView.setHandleShowMode(CropImageView.ShowMode.NOT_SHOW);
        mCropView.setGuideShowMode(CropImageView.ShowMode.NOT_SHOW);
        //mCropView.setFrameColor(getResources().getColor(R.color.white));
        mCropView.setAnimationDuration(200);

        Bundle bundle = getIntent().getExtras();

        ActName = bundle.getString("activity");
        path =  bundle.getString("picture");

        if(ActName.equals("opengallery")){
             if(path!=null)
             new DownloadImage().execute();

        }else {

            mCropView.setImageResource(R.drawable.newmy);
            setFramsize();

        }

        findViewById(R.id.addbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             mainFunctionRun.addnewtext();

            }
        });


        findViewById(R.id.donebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoneAllTask();
            }
        });

    }

    public void DoneAllTask() {
         try {
            mainFunctionRun.DoneAll();
            canvas.setDrawingCacheEnabled(true);
            ThirdBitFinal = Bitmap.createBitmap(canvas.getDrawingCache(true));
            canvas.setDrawingCacheEnabled(false);

            Uri photoUri = savePicture(getBaseContext(), overlay(mCropView.getImageBitmap(),ThirdBitFinal));

            Intent intent = new Intent(getBaseContext(), ShowImage.class);
            intent.setData(photoUri);
            startActivity(intent);
            mainFunctionRun.Removall();

         }catch (Exception e){
             e.printStackTrace();
         }
    }


    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {

        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bmp2.getWidth(), bmp2.getHeight()), new RectF(0, 0, bmp1.getWidth(), bmp1.getHeight()), Matrix.ScaleToFit.CENTER);
        Bitmap newBitmap = Bitmap.createBitmap(bmp2, 0, 0, bmp2.getWidth(), bmp2.getHeight(), m, true);

        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(newBitmap, new Matrix(), null);
        return bmOverlay;
    }


    public void setFramsize(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.i("my","Not null  Image rect "+CropImageView.mImageRect_setin_screchview);

                if(CropImageView.mImageRect_setin_screchview != null) {

                    View view = canvas; //or however you need it
                    RelativeLayout.LayoutParams lpp = (RelativeLayout.LayoutParams) view.getLayoutParams();

                    bottomMargin =lpp.bottomMargin+(int)CropImageView.mImageRect_setin_screchview.top;
                    topMargin =lpp.topMargin+(int)CropImageView.mImageRect_setin_screchview.top;

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

                    lp.setMargins((int) CropImageView.mImageRect_setin_screchview.left,topMargin,(int) CropImageView.mImageRect_setin_screchview.left,bottomMargin);

                    // canvas.setBackgroundColor(Color.parseColor("#D8000000"));

                    canvas.setLayoutParams(lp);


                }else {
                    onBackPressed();
                }
            }

        },3000);
    }



    public static Uri savePicture(Context context, Bitmap bitmap) {
         /*    Square Image Creater Function */
        // bitmap = ThumbnailUtils.extractThumbnail(bitmap, cropHeight, cropHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                context.getString(R.string.app_name)
        );

        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }


        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile = new File(
                mediaStorageDir.getPath() + File.separator + "IMG_Demo.png"
        );

        // Saving the bitmap
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            FileOutputStream stream = new FileOutputStream(mediaFile);
            stream.write(out.toByteArray());

            stream.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // Mediascanner need to scan for the image saved
        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri fileContentUri = Uri.fromFile(mediaFile);
        mediaScannerIntent.setData(fileContentUri);
        context.sendBroadcast(mediaScannerIntent);

      return fileContentUri;
    }


    @Override
    public void ObjectNo(int no) {
        mainFunctionRun.ObjectNo(no);

    }


    @Override
    public void onBackPressed() {
        if(mainFunctionRun.isBackTrue()){
            super.onBackPressed();
        }else {
            mainFunctionRun.onBack();
        }

    }


    private class DownloadImage extends AsyncTask<String, String, Void> {
        private Bitmap main;

        @Override
        protected void onPreExecute() {
            main = null;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {

                    main = Glide
                            .with(getApplicationContext())
                            .load(path)
                            .asBitmap()
                            .centerCrop()
                            .into(350,350) // Width and height
                            .get();


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mCropView.setImageBitmap(main);
            setFramsize();

        }

        @Override
        protected void onCancelled() {

        }

    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;

        try {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,filePathColumn, null, null, null);
            if (cursor == null || cursor.getCount() < 1) {
                return null; // no cursor or no record. DO YOUR ERROR HANDLING
            }
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if(columnIndex < 0) // no column index
                return null; // DO YOUR ERROR HANDLING
            return cursor.getString(columnIndex);

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
