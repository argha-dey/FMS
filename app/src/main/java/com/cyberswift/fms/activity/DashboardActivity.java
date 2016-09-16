package com.cyberswift.fms.activity;


import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;


import com.cyberswift.fms.R;
import com.cyberswift.fms.callback.AlertDialogCallBack;
import com.cyberswift.fms.model.UserClass;
import com.cyberswift.fms.util.Util;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

    public class DashboardActivity extends AppCompatActivity {
        ImageView logout_img, my_task;
        RelativeLayout home, task_report, task_monitor;
        ImageView user_pic;
        ProgressDialog mProgressDialog;
        Uri fileUri_camera,fileUri_albubm;
        public static final int MEDIA_TYPE_IMAGE = 100;
        private static final int PICTURE_GALLERY_REQUEST = 2;
        private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1;
        private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);

            findViewById();
            init();


        }

        public void findViewById() {

            home = (RelativeLayout) findViewById(R.id.rl_home);
            my_task = (ImageView) findViewById(R.id.iv_my_task1);
            task_report = (RelativeLayout) findViewById(R.id.rl_task_report);
            task_monitor = (RelativeLayout) findViewById(R.id.rl_task_monitor);
            user_pic = (ImageView) findViewById(R.id.iv_dp);

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        public void init() {

            task_report.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(getApplicationContext(),TaskReportingActivity.class);
                                               startActivity(intent);

                                           }

                                       }
            );
        }


        public void onCameraClicked(View mView) {


            final Dialog customDialog = new Dialog(this);

            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));

            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.img_select_dialog,
                    null);

            // WindowManager.LayoutParams wmlp =
            // customDialog.getWindow().getAttributes();
            // wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            // wmlp.x = screenWidth/3; //x position
            // wmlp.y = -44; //y position

            Button btn_album = (Button) view.findViewById(R.id.btn_album);
            btn_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();

                    // --> For Album

                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.setCancelable(false);
                    populatingSelectedPic();


                }
            });

            Button btn_camera = (Button) view.findViewById(R.id.btn_camera);
            btn_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();

                    // --> For Album
                    mProgressDialog.setMessage("Please wait...");
                    mProgressDialog.setCancelable(false);

                    cameraSelectedPic();

                }
            });

            Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });

            customDialog.setCancelable(true);
            customDialog.setContentView(view);
            customDialog.setCanceledOnTouchOutside(false);
            // Start AlertDialog
            customDialog.show();


        }
//TODO camera perpose............

        public void cameraSelectedPic() {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri_camera = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri_camera);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }


        public void populatingSelectedPic() {

            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, PICTURE_GALLERY_REQUEST);

        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            // if the result is capturing Image
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage1();
                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            if (requestCode == PICTURE_GALLERY_REQUEST && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(picturePath);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize =16;
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                Bitmap conv_bm = getCircleBitmap(resized);
                user_pic.setImageBitmap(conv_bm);

            }


        }


        private void previewCapturedImage1() {
            try {
                // hide video preview


                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize =16;

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri_camera.getPath(),
                        options);

                // user_pic.setImageBitmap(bitmap);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                Bitmap conv_bm = getCircleBitmap(resized);
                user_pic.setImageBitmap(conv_bm);


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        /**
         * Creating file uri to store image/video
         */
        public Uri getOutputMediaFileUri(int type) {
            return Uri.fromFile(getOutputMediaFile(type));
        }

        /*
         * returning image / video
         */
        private static File getOutputMediaFile(int type) {

            // External sdcard location
            File mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    IMAGE_DIRECTORY_NAME);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File mediaFile;
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            }
            else {
                return null;
            }

            return mediaFile;
        }
        //TODO ALL.......................................

//TODO Make circle Image

        public static Bitmap getCircleBitmap(Bitmap bitmap) {
            final Bitmap circuleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getWidth(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(circuleBitmap);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();

            return circuleBitmap;
        }






        public void onLogoutClicked(View mView) {
        Util.showCallBackMessageWithOkCancel(DashboardActivity.this,
                "Do you want to log out?", new AlertDialogCallBack() {

                    @Override
                    public void onSubmit() {
                        UserClass user = Util
                                .fetchUserClass(DashboardActivity.this);
                        user.setIsRemember(false);
                        Util.saveUserClass(DashboardActivity.this, user);

                        Intent intent = new Intent(DashboardActivity.this,
                                LoginActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // Do nothing

                    }
                });
    }
}