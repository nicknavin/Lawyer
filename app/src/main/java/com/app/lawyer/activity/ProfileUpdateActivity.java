package com.app.lawyer.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ImageView;

import com.app.lawyer.R;
import com.app.lawyer.api.BaseAsych;
import com.app.lawyer.api.Urls;
import com.app.lawyer.base.BaseActivity;
import com.app.lawyer.customview.CircleImageView;
import com.app.lawyer.customview.CustomEditText;
import com.app.lawyer.customview.CustomTextView;
import com.app.lawyer.interfaces.RequestCallback;
import com.app.lawyer.utility.Constant;
import com.app.lawyer.utility.ConvetBitmap;
import com.app.lawyer.utility.DataPrefrence;
import com.app.lawyer.utility.PermissionsUtils;
import com.app.lawyer.utility.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
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

    CustomEditText edt_name, edt_email;
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
        imgProfile = (CircleImageView) findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(this);
        edt_name = (CustomEditText) findViewById(R.id.edt_name);

        edt_email = (CustomEditText) findViewById(R.id.edt_email);
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
//            Picasso.with(context).load(image_url).memoryPolicy(MemoryPolicy.NO_CACHE)
//                    .networkPolicy(NetworkPolicy.NO_CACHE).placeholder(R.mipmap.profile_pic).into(imgProfile);

            Glide.with(context)
                    .load(image_url)
                    .placeholder(R.mipmap.profile_icon)
                    .error(R.mipmap.profile_icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfile);

        }

       edt_name.setText(user_name);

    }


    public void getUserProfileApi() {

        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.GET_USER, getJson(), new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success) {
                    dismiss_loading();
                    showToast(success);


                    try {
                        JSONObject object = js.getJSONObject("result");
                        String rdescription = object.getString("rdescription");
                        if (rdescription.equals("Success")) {
                            JSONObject userObj = js.getJSONObject("user");
                            String email_address = userObj.getString("email_address");
                            edt_email.setText(email_address);
                            String login_name = userObj.getString("login_name");
                            edt_name.setText(login_name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailed(JSONObject js, String failed, int status) {
                    dismiss_loading();
                    //showToast(failed);
                    showToast("Email or Password is invalid");

                }

                @Override
                public void onNull(JSONObject js, String nullp) {

                }

                @Override
                public void onException(JSONObject js, String exception) {

                }
            }).execute();

        } else {
            showInternetConnectionToast();
        }
    }

    private String getJson() {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("user_cd", userId);
            object.put("lang", "EN");
            JSONObject objinfo = new JSONObject();
            objinfo.put("lang", "EN");
            object.put("info", objinfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
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
                            ByteArrayOutputStream datasecond = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 50, datasecond);
                            ByteArray = datasecond.toByteArray();
                            patientProfile = base64String(datasecond.toByteArray());
                            //imgProfile.setImageBitmap(bm);
                            File file = Utility.getImageFile(context,bm);
                            if(file.exists()){

                                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());



                                imgProfile.setImageBitmap(myBitmap);

                            }
//                            callUpdateProfile(file);
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
            File f = new File(Environment.getExternalStorageDirectory() + "/lawyer.png");
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
        alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        alertDialog.getButton(alertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
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



    private void callUpdateProfile(File file) {
        if (isInternetConnected()) {
            showLoading();
            new BaseAsych(Urls.UPLOADIMAGE, file,user_i_cd,user_type_cd,company_cd, new RequestCallback() {
                @Override
                public void onSuccess(JSONObject js, String success)
                {
                    dismiss_loading();
                    try {
                        String profImage =js.getString("loc_image_name");
                        DataPrefrence.setPref(context, Constant.PROFILE_IMAGE,profImage);
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
    }
}
