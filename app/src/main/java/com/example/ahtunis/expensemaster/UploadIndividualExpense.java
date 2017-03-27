package com.example.ahtunis.expensemaster;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/// Class to upload individua expenses
public class UploadIndividualExpense extends AppCompatActivity {

    ImageView picture;
    Button capture;
    Button upload;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    String mCurrentPhotoPath;
    String path;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_individual_expense);

        capture = (Button) findViewById(R.id.captureBtn);
        upload = (Button) findViewById(R.id.uploadPicture);

        upload.setVisibility(View.GONE);
        picture = (ImageView)findViewById(R.id.pictureView);




    }

    public void takePhoto(View view){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case REQUEST_IMAGE_CAPTURE:
                if (grantResults.length >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // Go to camera intent if available
                    dispatchTakePictureIntent();
                }
                else{
                        Toast.makeText(this, "External write permission has not been granted", Toast.LENGTH_LONG).show();
                    }
                }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try{
                photoFile = createImageFile();
            }catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                if (hasImageCaptureBug()) {
                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/tmp")));
                } else {
                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String title;

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            //extras = getIntent().getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            upload.setVisibility(View.VISIBLE);
            capture.setText("RETAKE");

            // We could upload image directly to php
            picture.setImageBitmap(imageBitmap);


        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "CAMERA_" + timeStamp + "_WA0001";

        File userFolder = this.getCacheDir();
        File root = new File(Environment.getExternalStorageDirectory()
                + File.separator + "MyApp"+ File.separator);

        if(!root.exists()){
            root.mkdirs();
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                root      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        path=""+image.getAbsolutePath();
        return image;
    }

    public boolean hasImageCaptureBug() {

        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
                + android.os.Build.DEVICE);

    }

}
