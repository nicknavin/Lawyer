package com.app.amanrow.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.amanrow.R;
import com.app.amanrow.api.BaseAsych;
import com.app.amanrow.api.Urls;
import com.app.amanrow.base.BaseActivity;
import com.app.amanrow.customview.CircleImageView;
import com.app.amanrow.customview.CustomTextView;
import com.app.amanrow.interfaces.RequestCallback;
import com.app.amanrow.utility.Constant;
import com.app.amanrow.utility.ConvetBitmap;
import com.app.amanrow.utility.DataPrefrence;
import com.app.amanrow.utility.PermissionsUtils;
import com.app.amanrow.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.core.content.FileProvider;

public class ProfileUpdateActivity extends BaseActivity implements View.OnClickListener {


    TextInputEditText edt_name, edt_email;
    CustomTextView txt_nationality;
    private static final int PHONE_CODE_INT = 100;
    String numberStr, countryAlpha, countryCode, phone_code;
    CustomTextView btn_update;

    CircleImageView imgProfile;
    private Uri mCameraImageUri, mImageCaptureUri;
    byte[] ByteArray;
    private Dialog dialogSelect;
    String patientProfile = "", PicturePath = "";
    public boolean isForCamera = false;
    TextView btn_english, btn_arabic;
    CheckBox indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        initView();

    }

    public void initView() {
        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ((CustomTextView)findViewById(R.id.toolbar_header)).setText(context.getResources().getString(R.string.profil_update));

        btn_english = (TextView) findViewById(R.id.btn_english);

        btn_arabic = (TextView) findViewById(R.id.btn_arabic);

        indicator = (CheckBox) findViewById(R.id.indicator);


        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        edt_name = (TextInputEditText) findViewById(R.id.edt_name);

        edt_email = (TextInputEditText) findViewById(R.id.edt_email);
        btn_update = (CustomTextView) findViewById(R.id.btn_update);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    callProfileUpdateApi();
                }
            }
        });

        if(!DataPrefrence.getPref(context, Constant.PROFILE_IMAGE,"").isEmpty())
        {
            String image_url = DataPrefrence.getPref(context, Constant.PROFILE_IMAGE, "");
            Log.d(TAG,"profile url : "+image_url);

            Glide.with(context)
                    .load(image_url)
                    .placeholder(R.mipmap.profile_icon)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.mipmap.profile_icon)
                    .into(imgProfile);


        }
        if(!DataPrefrence.getPref(context, Constant.EMAILID,"").isEmpty())
        {
           String email = DataPrefrence.getPref(context, Constant.EMAILID, "");
           edt_email.setText(email);
        }
       edt_name.setText(user_name);
        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CheckBox checkBox= (CheckBox) view;
                String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
                if (lang.toLowerCase().equals("en") || lang.isEmpty())
                {
                    pageRefersh("ar");
                }
                else
                {
                    pageRefersh("en");
                }
//                if(checkBox.isChecked())
//                {
//
//                }
//                else
//                {
//
//                }

            }

        });
    }


    private void pageRefersh(String lang)
    {
//        if (lang.equals("en")) {
//            checkBoxArab.setChecked(false);
//
//        } else if (lang.equals("hi")) {
//
//            checkBoxEng.setChecked(false);
//        }

        DataPrefrence.setPref(context, Constant.LANG_SELECTION, lang);
        Utility.setLang(context, lang);
        Intent refresh=null;
        refresh = new Intent(this, LawyerLandingActivity.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(refresh);
        finish();
    }


    private boolean validation() {
        String name = edt_name.getText().toString();
        String email = edt_email.getText().toString();


        if (TextUtils.isEmpty(name)) {
            edt_name.setError(context.getResources().getString(R.string.msg_name));
            showToast(context.getResources().getString(R.string.msg_name));
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edt_email.setError(context.getResources().getString(R.string.msg_email));
            return false;
        }
        if (!Utility.emailValidator(email)) {
            showToast("please enter your valid email address");
            return false;
        }


        return true;
    }


    public void callProfileUpdateApi() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.UPDATEUSER, getjsonProfileUpdate(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    showToast(success);
                    DataPrefrence.setPref(context,Constant.EMAILID,edt_email.getText().toString());
                    Intent intent = new Intent(context, LawyerLandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    showToast(failed);
                }

                @Override
                public void onNull(JSONObject js, String nullp) {
                    dismiss_loading();
                }

                @Override
                public void onException(JSONObject js, String exception) {
                    dismiss_loading();
                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }

    private String getjsonProfileUpdate() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_cd", userId);
            jsonObject.put("user_type_cd", user_type_cd);
            jsonObject.put("email", edt_email.getText().toString());
            jsonObject.put("login_name", edt_name.getText().toString());
            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", lang);
            jsonObject.put("info", objinfo);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case Constant.REQUEST_CODE_CAMERA:

                        CropImage.activity(mCameraImageUri)
                                .setAspectRatio(1, 1)
                                .setFixAspectRatio(true)
                                .start(this);

                        break;

                    case Constant.REQUEST_CODE_ALBUM:
                        try {
                            mImageCaptureUri = data.getData();
                            CropImage.activity(mImageCaptureUri)
                                    .setAspectRatio(1, 1)
                                    .setFixAspectRatio(true)
                                    .start(this);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        if (resultCode == RESULT_OK) {
                            Uri resultUri = result.getUri();
                            try {
                                ByteArray = null;
    //                            Bitmap bm = BitmapFactory.decodeFile(PicturePath);
                                Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), resultUri);

                                bm = ConvetBitmap.Mytransform(bm);
                                bm = Utility.rotateImage(bm, new File(PicturePath));

                                imgProfile.setImageBitmap(bm);
                                ByteArrayOutputStream datasecond = new ByteArrayOutputStream();
                                bm.compress(Bitmap.CompressFormat.JPEG, 50, datasecond);
                                ByteArray = datasecond.toByteArray();
                                patientProfile = base64String(datasecond.toByteArray());
                                //imgProfile.setImageBitmap(bm);
                                File file = Utility.getImageFile(context,bm);
                                if(file.exists()){

                                    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());




                                }
                                callUpdateProfile(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                            Exception error = result.getError();
                        }
                        break;


                    default:
                        break;


                }
            }

        }


        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case PermissionsUtils.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                    Log.d("Checking", "permissions: " + Arrays.asList(permissions) + ", grantResults:" + Arrays.asList(grantResults));
                    if (PermissionsUtils.getInstance(context).areAllPermissionsGranted(grantResults)) {
                        if (isForCamera)
                            TakePic();
                        else
                            Gallery();
                    } else {
                        checkIfPermissionsGranted();
                    }
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }


        private String base64String(byte[] b) {
            String imgString = Base64.encodeToString(b, Base64.NO_WRAP);
            System.out.println("imgString " + imgString);
            return imgString;
        }


        public void setCameraPermission() {
            if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
                if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.CAMERA, "Camera")) {
                    return;
                }

                if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
                    if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write External Storage")) {
                        return;
                    }
                }
            }
            TakePic();
        }

        public void setGalleryPermission() {
            if (Build.VERSION.SDK_INT >= PermissionsUtils.SDK_INT_MARSHMALLOW) {
                if (!PermissionsUtils.getInstance(context).isPermissionGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE, "Read External Storage")) {
                    return;
                }
            }

            Gallery();
        }

        public void dialog() {
            dialogSelect = new Dialog(context, R.style.MaterialDialogSheet);
            dialogSelect.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogSelect.setContentView(R.layout.dialog_g);
            dialogSelect.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogSelect.getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialogSelect.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            int i = size.x;
            ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_photo)).setTranslationX(-i);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_gallery)).setTranslationX(-i);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtCancel)).setTranslationX(-i);
            ((View) dialogSelect.findViewById(R.id.view1)).setTranslationX(-i);
            ((View) dialogSelect.findViewById(R.id.view2)).setTranslationX(-i);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_photo)).animate().translationX(0).setDuration(500).setStartDelay(400);
            ((View) dialogSelect.findViewById(R.id.view1)).animate().translationX(0).setDuration(500).setStartDelay(500);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_gallery)).animate().translationX(0).setDuration(500).setStartDelay(600);
            ((View) dialogSelect.findViewById(R.id.view2)).animate().translationX(0).setDuration(500).setStartDelay(700);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtCancel)).animate().translationX(0).setDuration(500).setStartDelay(800);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_photo)).setOnClickListener(this);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtTake_gallery)).setOnClickListener(this);
            ((CustomTextView) dialogSelect.findViewById(R.id.txtCancel)).setOnClickListener(this);
            dialogSelect.show();
        }

        public void TakePic() {

            try {
                isForCamera = true;
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(getExternalCacheDir()+ "/lawyer.png");

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    mCameraImageUri = Uri.fromFile(f);
                } else {
                    mCameraImageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", f);
                }
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImageUri);
                cameraIntent.putExtra("return-data", true);
                startActivityForResult(cameraIntent, Constant.REQUEST_CODE_CAMERA);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        //REQUEST_CODE_ALBUM
        public void Gallery() {

            if (Build.VERSION.SDK_INT < 19) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Constant.REQUEST_CODE_ALBUM);
            } else {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Constant.REQUEST_CODE_ALBUM);
            }
        }

        private void goToSettings() {
            Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
            myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myAppSettings);
        }

        public void checkIfPermissionsGranted() {
            AlertDialog alertDialog;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(getString(R.string.permission));
            alertDialogBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    goToSettings();
                }
            });


            alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
            alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgProfile:
                dialog();
                break;

            case R.id.txtTake_photo:
                setCameraPermission();
                isForCamera = true;
                if (dialogSelect != null && dialogSelect.isShowing())
                    dialogSelect.dismiss();
                break;

            case R.id.txtTake_gallery:
                setGalleryPermission();
                isForCamera = false;
                if (dialogSelect != null && dialogSelect.isShowing())
                    dialogSelect.dismiss();
                break;

            case R.id.txtCancel:
                if (dialogSelect != null && dialogSelect.isShowing())
                    dialogSelect.dismiss();
                break;

        }

    }


    private void persistImage(Bitmap bitmap, String name) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

    }



    private void callUpdateProfile(File file)
    {
        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.UPLOADIMAGE, file,userId,user_type_cd,company_cd, new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success)
                {
                    dismiss_loading();
                    try {
                        String profImage =js.getString("loc_image_name");

                        DataPrefrence.setPref(context, Constant.PROFILE_IMAGE,profImage);

                        tag("updated profile url "+profImage);

//                        Glide.with(context)
//                                .load(profImage)
//                                .placeholder(R.mipmap.profile_icon)
//                                .error(R.mipmap.profile_icon)
//                              .into(imgProfile);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    showToast(failed);
                }

                @Override
                public void onNull(JSONObject js, String nullp) {
                    dismiss_loading();
                }

                @Override
                public void onException(JSONObject js, String exception) {
                    dismiss_loading();
                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }

    private Map<String, Object> getParamOffer(File file) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_type_cd", user_type_cd);
        params.put("user_i_cd",    user_i_cd);
        params.put("company_cd",   company_cd);
         params.put("file", file);
        return params;
    }



    private File getImageFile(Bitmap finalBitmap)
    {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();


        String fname = "lawyer"+ ".png";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        if (lang.toLowerCase().equals("en") || lang.isEmpty())
        {
            //indicator.setChecked(false);

        } else {
            // indicator.setChecked(true);

        }
    }


}
