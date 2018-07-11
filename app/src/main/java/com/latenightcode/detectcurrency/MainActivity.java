package com.latenightcode.detectcurrency;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA=1, SELECT_FILE=0;
    private ImageView mPreviewImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mPreviewImageView = findViewById(R.id.main_previewImageView);

        findViewById(R.id.main_cameraImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        findViewById(R.id.main_galleryImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mPreviewImageView.setImageBitmap(bmp);

                checkTakenCurrencyCountry(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    mPreviewImageView.setImageBitmap(bitmap);
                    checkTakenCurrencyCountry(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "exception in converting Image to Bitmap", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    private void checkTakenCurrencyCountry(Bitmap bitmap) {
        Toast.makeText(this, "match bitmap with model and show result", Toast.LENGTH_SHORT).show();
    }
}
