package com.app.lawyer.utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Toast;

import com.app.lawyer.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {

    public static Context appContext;
    private static String PREFERENCE = "Telemedicine";
    private static int MAX_IMAGE_DIMENSION = 720;
    public static Utility utility;

    public static void showInternetConnectionToast(Context context) {
        Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }

    public static Utility getInstant() {

        if (utility == null) {
            utility = new Utility();
        }
        return utility;
    }


    public static String change_DateFormate(String date_formate) {
        String change_date = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DateFormat formatter2 = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US);
        try {
            Date date = formatter.parse(date_formate);
            change_date = formatter2.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return change_date;

    }

    public static String changeTimeFormate(String time_formate) {

        String time = "";
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        DateFormat formatter2 = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            Date date = formatter.parse(time_formate);
            time = formatter2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    public static String TimeFormate(String time_formate) {

        String time = "";

        DateFormat formatter2 = new SimpleDateFormat("hh:mm a", Locale.US);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.US);
        try {
            Date date = formatter2.parse(time_formate);
            time = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }


    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public static String getDecimalFormat(double value) {
        DecimalFormat df2 = new DecimalFormat(".######");
        String output = df2.format(value);
        return output;
    }

    public static String getMediaFile() {

        try {
            File flyerFolder = null;
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String path = "Flyerplane/Media/FlyerplaneImages/sent";
                flyerFolder = new File(sd, path);
                if (!flyerFolder.exists()) {
                    flyerFolder.mkdirs();
                    flyerFolder.getAbsolutePath();
                    System.out.println("fly path " + flyerFolder.getAbsolutePath());
                }

            }
            return flyerFolder.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLang(Context context) {
        Locale locale;

        String lang = "";
        lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");

        if (lang.equals("")) {
            lang = Locale.getDefault().getDisplayLanguage();
        }
        setLang(context, lang);
        String language = Locale.getDefault().getDisplayLanguage();
        return language;
    }

//    public static Drawable getImageFromURL(String photoDomain) {
//        Drawable drawable = null;
//        try {
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpGet request = new HttpGet(photoDomain.trim());
//            HttpResponse response = httpClient.execute(request);
//            InputStream is = response.getEntity().getContent();
//            drawable = Drawable.createFromStream(is, "src");
//        } catch (MalformedURLException e) {
//        } catch (IOException e) {
//        }
//        return drawable;
//    }

//    public static String postParamsAndfindJSON(String url, ArrayList<NameValuePair> params) {
//        String result = "";
//
//        System.out.println("URL comes in jsonparser class is:  " + url + params);
//        try {
//            int TIMEOUT_MILLISEC = 100000; // = 10 seconds
//            HttpParams httpParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
//            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
//
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setEntity(new UrlEncodedFormEntity(params));
//            // httpGet.setURI(new URI(url));
//
//            HttpResponse httpResponse = httpClient.execute(httpPost);
//            InputStream is = httpResponse.getEntity().getContent();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//
//            is.close();
//            result = sb.toString();
//
//        } catch (Exception e) {
//            System.out.println("exception in jsonparser class ........");
//            e.printStackTrace();
//            result = "";
//            return result;
//        }
//        return result;
//    }

    public static void log(String str) {
        // System.out.println(str);
        // Log.i("LOG",str);
    }

    static Locale myLocale;

    public static void setLocale(String lang, Context context) {

        myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    public static void setLang00(Context context, String lang) {
        Locale locale;

        lang = DataPrefrence.getPref(context, Constant.PREFERED_LANGUAGE, "");
        if (lang.equals("hi")) {
            locale = new Locale("hi");
        } else {
            locale = new Locale("en");
        }

        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration, displayMetrics);
        }
    }

    public static void getLanguage(Context context) {


        Locale locale;

        String lang = "";
        lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");

        setLang(context, lang);

    }


    public static void setSystemLang(Context context) {
        Locale locale;
        String lang = DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        if (lang.equals("ar")) {
            locale = new Locale("ar");
        } else {
            locale = new Locale("en");
        }

        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(configuration, locale);
        } else {
            setSystemLocaleLegacy(configuration, locale);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            context.getApplicationContext().getResources().updateConfiguration(configuration,
                    context.getResources().getDisplayMetrics());


    }

    public static void setLang(Context context, String lang) {
        Locale locale;

        //lang=DataPrefrence.getPref(context, Constant.LANG_SELECTION, "");
        if (lang.equals("ar")) {
            locale = new Locale("ar");
        } else {
            locale = new Locale("en");
        }

        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(configuration, locale);
        } else {
            setSystemLocaleLegacy(configuration, locale);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            context.getApplicationContext().getResources().updateConfiguration(configuration,
                    context.getResources().getDisplayMetrics());


    }
/*	public static String multiPart(String url,MultipartEntity entity) {
        String result="";
		HttpClient httpclient;

		try {
	    httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);

	    httppost.setEntity(entity);
		System.out.println("cvsc" + httppost);
		HttpResponse response = httpclient.execute(httppost);
		result = EntityUtils.toString(response.getEntity());
		System.out.println("Given Result to photo  " + result);

	} catch (Exception e) {
		// TODO: handle exception
	}
		return result;

	}*/


    public static Bitmap DownloadImageDirect(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        // String imageEncoded = Base64Coder.encodeTobase64(image);

        // Log.d("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static void alertBoxShow(Context context, int msg) {
        // set dialog for user's current location set for searching theaters.
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Alert");
        dialog.setMessage(msg);
        dialog.setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void ShowStringAlertWithMessage(Context context, int alerttitle,
                                                  int locationvalidation) {
        // Assign the alert builder , this can not be assign in Click events
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(locationvalidation);
        builder.setTitle(alerttitle);
        // Set behavior of negative button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void ShowStringAlert2WithMessage(final Context context, int alerttitle,
                                                   int locationvalidation) {
        // Assign the alert builder , this can not be assign in Click events
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(locationvalidation);
        builder.setTitle(alerttitle);
        // Set behavior of negative button
        builder.setPositiveButton("GET STARTED", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
                //((DashboardPatient) context).moveToNextActivity("GET STARTED");
                //Intent intent=new Intent(context,GetStartedActivity.class);
            }
        });
        builder.setNegativeButton("FAQ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
                //((DashboardPatient) context).moveToNextActivity("FAQ");
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

//    public static String findJSONFromUrl(String url) {
//        String result = "";
//
//        System.out.println("URL comes in jsonparser class is:  " + url);
//        try {
//            int TIMEOUT_MILLISEC = 100000; // = 10 seconds
//            HttpParams httpParams = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParams,
//                    TIMEOUT_MILLISEC);
//            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
//            HttpClient httpClient = new DefaultHttpClient();
//            HttpGet httpGet = new HttpGet(url);
//            // httpGet.setURI(new URI(url));
//
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//            InputStream is = httpResponse.getEntity().getContent();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//
//            is.close();
//            result = sb.toString();
//            System.out.println("result  in jsonparser class ........" + result);
//
//        } catch (Exception e) {
//            System.out.println("exception in jsonparser class ........");
//            result = null;
//        }
//        return result;
//    }

    public static Bitmap getBitmap(String url) {
        Bitmap imageBitmap = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            try {
                imageBitmap = BitmapFactory.decodeStream(new FlushedInputStream(is));
            } catch (OutOfMemoryError error) {
                error.printStackTrace();
                System.out.println("exception in get bitma putility");
            }

            bis.close();
            is.close();
            final int IMAGE_MAX_SIZE = 50;
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
                scale++;
            }
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                // b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = imageBitmap.getHeight();
                int width = imageBitmap.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                double x = (y / height) * width;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, (int) x, (int) y, true);
                imageBitmap.recycle();
                imageBitmap = scaledBitmap;

                System.gc();
            } else {
                // b = BitmapFactory.decodeStream(in);
            }

        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            System.out.println("exception in get bitma putility");
        } catch (Exception e) {
            System.out.println("exception in get bitma putility");
            e.printStackTrace();
        }
        return imageBitmap;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
    }

    public static byte[] scaleImage(Context context, Uri photoUri)
            throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = 0;// getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION
                || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth)
                    / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight)
                    / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
                    srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        /*
         * if (type.equals("image/png")) {
         * srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); } else if
         * (type.equals("image/jpg") || type.equals("image/jpeg")) {
         * srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); }
         */
        byte[] bMapArray = baos.toByteArray();
        baos.close();
        return bMapArray;
    }

    static int mMaxWidth, mMaxHeight;

    @SuppressWarnings("deprecation")
    public static Bitmap loadResizedImage(Context mContext, final File imageFile) {
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mMaxWidth = display.getWidth();
        mMaxHeight = display.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        int scale = calculateInSampleSize(options, mMaxWidth, mMaxHeight);
        while (options.outWidth / scale > mMaxWidth
                || options.outHeight / scale > mMaxHeight) {
            scale++;
        }
        Bitmap bitmap = null;
        Bitmap scaledBitmap = null;
        if (scale > 1) {
            try {
                scale--;
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inPurgeable = true;
                options.inTempStorage = new byte[16 * 100];
                bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),
                        options);
                if (bitmap == null) {
                    return null;
                }

                // resize to desired dimensions
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double newWidth;
                double newHeight;
                if ((double) width / mMaxWidth < (double) height / mMaxHeight) {
                    newHeight = mMaxHeight;
                    newWidth = (newHeight / height) * width;
                } else {
                    newWidth = mMaxWidth;
                    newHeight = (newWidth / width) * height;
                }

                scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                        Math.round((float) newWidth),
                        Math.round((float) newHeight), true);
                bitmap.recycle();
                bitmap = scaledBitmap;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            }
            System.gc();
        } else {
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }

        return rotateImage(bitmap, imageFile);
    }

    public static Bitmap rotateImage(final Bitmap bitmap,
                                     final File fileWithExifInfo) {
        if (bitmap == null) {
            return null;
        }
        Bitmap rotatedBitmap = bitmap;
        int orientation = 0;
        try {
            orientation = getImageOrientation(fileWithExifInfo
                    .getAbsolutePath());
            if (orientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation, (float) bitmap.getWidth() / 2,
                        (float) bitmap.getHeight() / 2);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    public static int getImageOrientation(final String file) throws IOException {
        ExifInterface exif = new ExifInterface(file);
        int orientation = exif
                .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return 0;
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }

    public static Typeface Appttf(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "AE100132.TTF");

    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static String getTimezone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();

        DateFormat date = new SimpleDateFormat("ZZZZZ", Locale.getDefault());
        String localTime = date.format(currentLocalTime);
        System.out.println(localTime + "  TimeZone   ");
        return localTime;
    }

    public static boolean dateCompare(String to, String from) {
        try {
            String result = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(to);
            Date date2 = sdf.parse(from);

            System.out.println("date1 : " + sdf.format(date1));
            System.out.println("date2 : " + sdf.format(date2));

            if (date1.compareTo(date2) > 0) {
                result = "Date1 is after Date2";
                return false;
//                System.out.println("Date1 is after Date2");
            } else if (date1.compareTo(date2) < 0) {
                result = "Date1 is before Date2";
                return true;
//                System.out.println("Date1 is before Date2");
            } else if (date1.compareTo(date2) == 0) {
                result = "Date1 is equal to Date2";
                return true;
//                System.out.println("Date1 is equal to Date2");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
    // remove for preferences

    public static void removepreference(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        settings.edit().remove(name).commit();
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean passwordValidator(String pwd, Context context) {
        if (!pwd.matches("[a-zA-Z0-9]+")) {
            Toast.makeText(context, "Username must be alphanumeric", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pwd.matches("^.*[0-9].*$")) {
            Toast.makeText(context, "it should contain alphanumeric with numbers", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pwd.matches("^.*[a-zA-Z].*$")) {
            Toast.makeText(context, "it should contain alphanumeric with numbers", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkNumber(String str) {

        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(str).matches()) {
            return true;
        }
        return false;
    }


    /* public static void ShowAlertDialog(String msg)
     {
         AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
         builder.setTitle("SwipeMe");
         builder.setMessage(msg);
         builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
                 dialog.dismiss();
             }
         });

 // Create the AlertDialog
         AlertDialog dialog = builder.create();
         dialog.show();

     }
 */
    public static boolean validateFirstName(String firstName) {
        return firstName.matches("[A-Z][a-zA-Z]*");
    } // end

    /**
     * Function to show settings alert dialog
     */
    public static void showSettingsAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

//    public static void showExit(final Context mContext, final Activity activity, final String msg) {
//        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(mContext);
//        // Setting Dialog Title
//        alertDialog.setTitle("FlyerPlane");
//
//        // Setting Dialog Message
//        if (msg.equals("1")) {
//
//            alertDialog.setMessage(mContext.getResources().getString(R.string.msg_exit));
//        } else {
//            alertDialog.setMessage(mContext.getResources().getString(R.string.msg_logut));
//
//        }
//        // Setting Icon to Dialog
//        //  alertDialog.setIcon(R.drawable.);
//        alertDialog.setCancelable(false);
//        // On pressing Settings button
//        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                if (msg.equals("1")) {
//                    activity.finish();
//                } else {
//
//                    removekey(mContext, activity);
//                }
//
//            }
//        });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }


    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
    }

    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static String getKeyboardLang(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodSubtype inputMethodSubtype = inputMethodManager.getCurrentInputMethodSubtype();
        Locale mLocale = new Locale(inputMethodSubtype.getLocale());
        String localeDisplayName = mLocale.getDisplayName();
        //If the device Locale is English
        String sdf = mLocale.getDisplayLanguage();
        String fdsf = mLocale.getISO3Language();
        String dsfdsffsdfs = mLocale.getLanguage();
        if (Locale.getDefault().getISO3Language().equals("eng")) {
            //this is how those languages displayed on English
            System.out.println("s00000dfsdf");
        } else if (localeDisplayName.equals("Arabic")) {
            System.out.println("sdfsdf");
        }


//        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        InputMethodSubtype inputMethodSubtype = inputMethodManager.getCurrentInputMethodSubtype();
//        String lang= inputMethodSubtype.getLocale();
//
//        InputMethodManager imm = (InputMethodManager)
//               context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        List<InputMethodInfo> ims = imm.getEnabledInputMethodList();
//
//        for (InputMethodInfo method : ims){
//            List<InputMethodSubtype> submethods =
//                    imm.getEnabledInputMethodSubtypeList(method, true);
//            for (InputMethodSubtype submethod : submethods){
//                if (submethod.getMode().equals("keyboard")){
//                    String currentLocale = submethod.getLocale();
//                    Log.i("Tag", "Available input method locale: " + currentLocale);
//                    return  currentLocale;
//
//                }
//            }
//        }
        return "";
    }


    public static String readContacts(Context appContext) {

        String phoneNumber = null;
        String email = null;
        String number = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = appContext.getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {

                    output.append("\n First Name:" + name);

                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    int i = 0;

                    System.out.println("total number " + phoneNumber);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        output.append("\n Phone number:" + phoneNumber);
                        System.out.println(output);


                        number = phoneNumber + ",";


                    }

                    phoneCursor.close();

                    // Query and loop for every email of the contact
//                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
//
//                    while (emailCursor.moveToNext()) {
//
//                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
//
//                        output.append("\nEmail:" + email);
//
//                    }
//
//                    emailCursor.close();
                }

                output.append("\n");
            }

            //  outputText.setText(output);
        }
        return number;
    }


    public static void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.ebabu.flyerplane"
                        + "//databases//" + "FlyerplaneDatabase";
                String backupDBPath = "/FlyerplaneDatabase";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (!backupDB.exists()) {
                    backupDB.createNewFile();
                }
                Log.d("DBPath", "backupDB: " + backupDB.getAbsolutePath());

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                System.out.println("the data table is " + backupDB.toString());

            }
        } catch (Exception e) {

            System.out.println("exp " + e.toString());
//            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
//                    .show();

        }
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    File flyerFolder;

    public String makeFile() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String path = "Consultlot/Remark";
                flyerFolder = new File(sd, path);

                if (!flyerFolder.exists()) {
                    flyerFolder.mkdirs();
                    flyerFolder.getAbsolutePath();
                    System.out.println("fly path " + flyerFolder.getAbsolutePath());


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flyerFolder.getAbsolutePath();
    }


//    public static String getMediaFile() {
//
//        try {
//            File flyerFolder = null;
//            File sd = Environment.getExternalStorageDirectory();
//            File data = Environment.getDataDirectory();
//            if (sd.canWrite()) {
//                String path = "Sportot/Media";
//                flyerFolder = new File(sd, path);
//                if (!flyerFolder.exists()) {
//                    flyerFolder.mkdirs();
//                    flyerFolder.getAbsolutePath();
//                    System.out.println("fly path " + flyerFolder.getAbsolutePath());
//                }
//
//            }
//            return flyerFolder.getAbsolutePath();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    public static String getAudioFile() {

        try {
            File ConsultlotFolder = null;
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String path = "Consultlot/Media/ConsultlotImages/media";
                ConsultlotFolder = new File(sd, path);
                if (!ConsultlotFolder.exists()) {
                    ConsultlotFolder.mkdirs();
                    ConsultlotFolder.getAbsolutePath();
                    System.out.println("Consultlot path " + ConsultlotFolder.getAbsolutePath());
                }
            }
            return ConsultlotFolder.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getFile() {

        try {
            File flyerFolder = null;
            File myFile = null;
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String path = "Flyerplane/Media/FlyerplaneImages/sent";
                flyerFolder = new File(sd, path);
                if (!flyerFolder.exists()) {

                    //flyerFolder.mkdirs();
                    //flyerFolder.getParentFile().mkdirs();
                    // flyerFolder.createNewFile();
                    System.out.println("flyerfolder path" + flyerFolder.getAbsolutePath());
                    return flyerFolder;
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void showInternetPop(Context context) {
        MyDialog.iPhone(context.getResources().getString(R.string.connection), context);

    }

    public static void Log(String msg) {
        Log.d("TAG", msg);
    }


    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static boolean checkLink(String str) {
        if (str.contains("facebook") || str.contains("google") || str.contains("youtube") || str.contains("twitter") || str.contains("plus") || str.contains("linkedin")) {
            return true;
        }
        return false;

    }

    private static final Pattern WEB_URL_PATTERN = Patterns.WEB_URL;

    public static boolean isValidURL(String urlString) {
        return WEB_URL_PATTERN.matcher(urlString).matches();

    }

    public static void callWeblink(String url, Context context) {
        if (Utility.isConnectingToInternet(context)) {
            if (Utility.isValidURL(url)) {
                if (!url.startsWith("https://")) {
                    url = "https://" + url;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);
            }
        } else {
            MyDialog.iPhone(context.getResources().getString(R.string.connection), context);
        }
    }


    public static String getDob(String dob) {
        String date_birth = "";
        try {

            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("dd,MMM,yyyy");
            Date date = format1.parse(dob);
            System.out.println(format2.format(date));
            date_birth = format2.format(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date_birth;

    }

    public static String getCurrentDate2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }

    public static String hasNewLine(String str) {
        try {
            boolean hasNewline = str.contains("\\n");
            if (hasNewline) {
                System.out.println("msg have newline");
                str = str.trim().replace("\\n", " \n ");
                return str.trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.trim();
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        return dateFormat.format(date);
    }


    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }

    public static boolean getCurrentTimeCompare(String clinicTime) {
        try {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            dateFormat.format(date);
            System.out.println(dateFormat.format(date));

            if (dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse(clinicTime))) {
                System.out.println("Current time is greater than 12.07");
                return false;
            } else {

                System.out.println("Current time is less than 12.07");
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean compareTime() {

        return true;
    }


    public static String convert24To12(String _24HourTime) {


        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));
            return _12HourSDF.format(_24HourDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return _24HourTime;
    }

    public static void clearDataBase(Context context) {

        DataPrefrence.setPref(context, Constant.USER_ID, "");
        DataPrefrence.setPref(context, Constant.EMAILID, "");
        DataPrefrence.setPref(context, Constant.USER_NAME, "");
        DataPrefrence.setPref(context, Constant.MOBILE_NO, "");
        DataPrefrence.setPref(context, Constant.FULLNAME, "");
        DataPrefrence.setPref(context, Constant.DOC_LOGIN_FLAG, false);
        DataPrefrence.setPref(context, Constant.PATIENT_LOGIN_FLAG, false);

        DataPrefrence.setPref(context, Constant.ACCESS_TOKEN, "");
        DataPrefrence.setPref(context, Constant.LOGIN_FLAG, false);
        DataPrefrence.setPref(context, Constant.LOGIN_TYPE, "");

    }

    public static File getImageFile(Context context, Bitmap bitmap) {
        String name = context.getResources().getString(R.string.app_name);
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {

        }
        return imageFile;
    }






//    public static void unAuthorized(Context context,Activity activity) {
//        clearDataBase(context);
//
//        Intent intent = new Intent(context, SplashActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        activity.startActivity(intent);
//        activity.finish();
//    }

/*
    public static void  clearData(Context context) {
        DataPrefrence.setPref(context, Constant.USER_ID, "");
        DataPrefrence.setPref(context, Constant.ACCESS_TOKEN, "");
    }
*/
//    public static boolean isValidURL(String urlString)
//    {
//        try
//        {
//            URL url = new URL(urlString);
//            url.toURI();
//            return true;
//        } catch (Exception exception)
//        {
//            return false;
//        }
//    }
//    private void copyFile() throws IOException {
//        File file = new File("/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20160513-WA0003.jpg");
//        if (!file.exists()) {
//            return;
//        }
//
//        if (flyerFolder.exists()) {
//
//            FileChannel source = null;
//            FileChannel destination = null;
//            source = new FileInputStream(file).getChannel();
//            File myFile = new File(flyerFolder, "my_image_" + System.currentTimeMillis() + ".jpg");
//            destination = new FileOutputStream(myFile).getChannel();
//            if (destination != null && source != null) {
//                destination.transferFrom(source, 0, source.size());
//            }
//            if (source != null) {
//                source.close();
//            }
//            if (destination != null) {
//                destination.close();
//            }
//        }
//
//
//    }

}// final class ends here

