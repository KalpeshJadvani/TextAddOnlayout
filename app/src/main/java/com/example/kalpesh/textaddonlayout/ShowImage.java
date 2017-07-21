package com.example.kalpesh.textaddonlayout;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImage extends AppCompatActivity {
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);


        ImageView imageView=(ImageView)findViewById(R.id.imageid);

        imageUri = getIntent().getData();

        imageView.setImageURI(imageUri);

    }
}
