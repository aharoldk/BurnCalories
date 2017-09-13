package com.aharoldk.burncalories;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aharoldk.burncalories.fragment.ProfileFragment;
import com.aharoldk.burncalories.helper.DatabaseHelper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivProfilePhoto) CircleImageView ivProfilePhoto;
    @BindView(R.id.btnProfileChange) Button btnProfileChange;
    @BindView(R.id.etProfileName) EditText etProfileName;
    @BindView(R.id.btnProfileSaveName) Button btnProfileSaveName;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private File photoFile = null;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        declarate();

        showImages();
    }

    private void declarate() {
        databaseHelper = new DatabaseHelper(this);

        btnProfileChange.setOnClickListener(this);
        btnProfileSaveName.setOnClickListener(this);

        Cursor cursor = databaseHelper.selectUser();

        cursor.moveToFirst();

        etProfileName.setText(String.valueOf(cursor.getString(1)));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProfileChange:
                dispatchTakePictureIntent();
                break;

            case R.id.btnProfileSaveName:
                saveName();
                break;

            default:
                break;
        }
    }

    private void saveName() {
        String getName = etProfileName.getText().toString();

        if(databaseHelper.updateUser(getName)) {
            Toast.makeText(this, "Update Name Profile Complete", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    static final String[] EXTENSIONS = new String[]{
            "jpg" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    private void showImages() {
        File dir = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));

        File[] filelist = dir.listFiles(IMAGE_FILTER );
        for (File f : filelist) {

            mCurrentPhotoPath = f.getAbsolutePath();

            Log.i("current", mCurrentPhotoPath);

            Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mCurrentPhotoPath);

            ivProfilePhoto.setImageBitmap(photoReducedSizeBitmp);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent();
        takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.aharoldk.burncalories.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.v("currentPhoto", mCurrentPhotoPath);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            setReducedImageSize();
        }
    }

    private void setReducedImageSize() {
        int targetImageViewWidth = ivProfilePhoto.getWidth();
        int targetImageViewHeight = ivProfilePhoto.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth / targetImageViewWidth, cameraImageHeight / targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap photoReducedSizeBitmp = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        ivProfilePhoto.setImageBitmap(photoReducedSizeBitmp);

        Toast.makeText(this, "Update Photo Profile Complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
