package com.example.ahtunis.expensemaster;

import android.content.Intent;
import android.hardware.Camera;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class UploadIndividualExpense extends AppCompatActivity {

    ImageView picture;
    Button capture;
    static final int CAM_REQUEST = 1;

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_individual_expense);

        capture = (Button) findViewById(R.id.captureBtn);
        picture = (ImageView)findViewById(R.id.imageView1);

        Camera c = getCameraInstance();
        capture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // take picture
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = getFile();
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                 //   startActivityForResult(cameraIntent, 1337);
                    //startActivityForResult(cameraIntent, CAM_REQUEST);



                    // redirect to picture


                    // implement OCR or upload to webpage

                }
        });
    }

    private File getFile(){

        File folder = new File("sdcard/camera_app");

        if(!folder.exists()){

            folder.mkdir();


        }

        File image = new File(folder, "cam_image.jpg");
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        String path = "sdcard/camera_app/cam_image.jpg";
        picture.setImageDrawable(Drawable.createFromPath(path));
    }

    private void initializeTakePictureIntent(){


        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null){
        //startActivityForResult(pictureIntent, 1, REQUEST_IMAGE_CAPTURE);

        }
    }
}
