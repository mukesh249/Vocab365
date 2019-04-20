package com.example.advosoft.vocab365.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.example.advosoft.vocab365.Model.DateItem;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.BaseFragment;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.util.CapturePhoto;
import com.example.advosoft.vocab365.wrapper.Country;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;
import static com.example.advosoft.vocab365.views.ScalingUtilities.calculateInSampleSize;


/**
 * Created by Advosoft2 on 4/28/2017.
 */

public class Utils {
    public static final String PREF_LOGIN_CHK = "login_chk";
    public static final String FIRST_LOGIN = "first_login";
    public static final String PREF_LOGOUT_CHK = "logout_chk";
    public static final String PREF_NAME = "rooomDekho";
    public static final String FIRST_INSTALL = "first_install";
    public static long SEC_PER_DAY = 24 * 60 * 60;
    public static long SEC_PER_HOUR = 60 * 60;
    public static long SEC_PER_MIN = 60;
    public static boolean selectMedia = false;
    public final static int IMAGE_GALLERRY_CHAT = 1006;
    public final static int IMAGE_CAMERA_CHAT = 1007;

    public final static int VIDEO_GALLERRY_CHAT = 1008;
    public final static int VIDEO_CAMERA_CHAT = 1009;

    public final static int AUDEO_GALLERRY_CHAT = 2010;
    public final static int AUDEO_RECORD_CHAT = 2011;
    public static String GROUP_IMAGE_PATH = null;
    public static Uri GROUP_IMAGE_URI = null;

    public static String PROFILE_IMAGE_PATH = null;
    public static Uri PROFILE_IMAGE_URI = null;

    public static String PROFILE_IMAGE_PATH_POST = null;
    public static Uri PROFILE_IMAGE_URI_POST = null;

    public static String IMAGE_GALLERRY_CHAT_PATH = null;
    public static Uri IMAGE_GALLERRY_CHAT_URI = null;

    public static String IMAGE_CAMERA_CHAT_PATH = null;
    public static Uri IMAGE_CAMERA_CHAT_URI = null;

    public static String VIDEO_GALLERRY_CHAT_PATH = null;
    public static Uri VIDEO_GALLERRY_CHAT_URI = null;

    public static String VIDEO_CAMERA_CHAT_PATH = null;
    public static Uri VIDEO_CAMERA_CHAT_URI = null;

    public static String GROUP_EDIT_IMAGE_PATH = null;
    public static Uri GROUP_EDIT_IMAGE_URI = null;

    public static String AUDEO_GALLERRY_CHAT_PATH = null;

    public static void loadImageRound1(Context context, ImageView view, int drawable) {
        //Picasso.with(context).load(drawable).into(view);
        Glide.with(context).load(drawable).crossFade().into(view);
    }

    public static Utils getInstance() {
        return new Utils();
    }

    public static Spanned setTitle(CharSequence mTitle) {
        return Html.fromHtml("<font color='#ffffff'>" + mTitle + "</font>");
        //return Html.fromHtml("<font color='#009f3c'><b>"+mTitle+"</b></font>");
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static void loadImageTopCut(Context context, ImageView imgView, String url) {
        if (url != null && !url.isEmpty()) {
            //Picasso.with(context).load(url).into(imgView);
            //((SelectableRoundedImageView)iv3).setCornerRadiiDP(4, 4, 0, 0);
            Glide.with(context).load(url).crossFade().into(imgView);
        } else
            imgView.setImageResource(0);
    }
    public static void hideKeyboardForFocusedView(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        Log.e("hide kb", "1");
        if (view != null) {
            Log.e("hide kb", "2");
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static String getTimeValueForLastSeen(String timestamp) {
        String time = "";
        long milsec;
        long sec;

        try {

            Date todayDate = new Date();

            Date df = new Date(Long.parseLong(timestamp));

            String dateTime = new SimpleDateFormat("dd/MM/yy hh:mma")
                    .format(df);

            String todayDateTime = new SimpleDateFormat("dd/MM/yy hh:mma")
                    .format(todayDate);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);

            String dateYesterday = new SimpleDateFormat("dd/MM/yy hh:mma")
                    .format(cal.getTime());

            if (dateTime.split(" ")[0].equals(todayDateTime.split(" ")[0])) {
                String str = dateTime.split(" ")[1];

                if (str.startsWith("0"))
                    str = str.substring(1);

                time = "Last seen Today at " + str;
            } else if (dateTime.split(" ")[0]
                    .equals(dateYesterday.split(" ")[0])) {
                String str = dateTime.split(" ")[1];

                if (str.startsWith("0"))
                    str = str.substring(1);

                time = "Last seen Yesterday at " + str;

            } else {
                String year = new SimpleDateFormat("yyyy").format(df);
                String currYear = new SimpleDateFormat("yyyy")
                        .format(todayDate);

                if (year.equals(currYear)) {
                    time = new SimpleDateFormat("E, MMM dd, hh:mma").format(df);

                    String d1 = time.split(" ")[0];
                    String d2 = time.split(" ")[1];
                    String d3 = time.split(" ")[2];
                    String d4 = time.split(" ")[3];

                    if (d4.startsWith("0"))
                        d4 = d4.substring(1);

                    time = d1 + d2 + " " + d3 + " " + d4;

                } else {
                    time = new SimpleDateFormat("E, MMM dd, yyyy hh:mma").format(df);

                    String d1 = time.split(" ")[0];
                    String d2 = time.split(" ")[1];
                    String d3 = time.split(" ")[2];
                    String d4 = time.split(" ")[3];
                    String d5 = time.split(" ")[4];

                    if (d5.startsWith("0"))
                        d5 = d5.substring(1);

                    time = d1 + d2 + d3 + d4 + d5;
                }

                time = "Last seen on " + time;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return time;
    }
    public static long getCurrentTimeInMili(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Day "+day+" Month: "+month+" year:"+year);
        return System.currentTimeMillis();
    }

    public static String getMonthName(int month){
        String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month];
    }
    public static String getDateTime(long time){

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int date = cal.get(Calendar.DATE);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        String fullDate = date+"-"+getMonthName(month)+"-"+year;

        int hour = cal.get(Calendar.HOUR);
        int min = cal.get(Calendar.MINUTE);
        String fullTime = hour+":"+min;

        return fullDate+" "+fullTime;
    }

    public static Country getCountry(Context context, double latitude, double longitude){
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String code = addresses.get(0).getCountryCode();
            String name = addresses.get(0).getCountryName();

            Country country = new Country(context, name, code);
            return country;
        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    public static void showToast(Context activity, String msg) {
        Toast toast = Toast.makeText(activity, msg, 2000);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean validateEmail(String email) {
        final Pattern emailPattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)\\b", 2);
        return emailPattern.matcher(email).matches();
    }

    public static void showAlert(Context context, String title, String message) {
        showAlert(context, title, message, null);
    }

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener okListner) {
        if (alert != null && alert.isShowing())
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialog);
        builder.setMessage(message).setTitle(title)
                .setCancelable(false)
                .setNegativeButton("OK", okListner);
        alert = builder.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }





    public static String getPath(Context context, Uri uri) {
        String[] data = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }




    public boolean hasPermission(Activity context, String permissions[], final int requestCode) {
        for (String per : permissions) {
            boolean has = ActivityCompat.checkSelfPermission(context, per) == PackageManager.PERMISSION_GRANTED;
            if (!has) {
                ActivityCompat.requestPermissions(context, new String[]{per}, requestCode);
            }
        }
        return true;
    }

    public static Boolean checkGPS(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            showGPSDisabledAlertToUser(context);
            return false;
        }
    }

    static AlertDialog alert = null;

    private static void showGPSDisabledAlertToUser(final Context context) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                ((Activity) context).startActivity(callGPSSettingIntent);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert = alertDialogBuilder.create();
        alert.show();
    }

    public static ProgressDialog showProgress(Context context, String title, String msg) {
		/*ProgressBar bar = new ProgressBar(context)
		bar.showContextMenu();*/
        //bar.destroyDrawingCache();

        ProgressDialog dialog = new ProgressDialog(context);
        //dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.show();
        return dialog;
    }

    public static String getAddress(Context context, double latitude, double longitude) {
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            StringBuffer sb = new StringBuffer();
            String address = addresses.get(0).getAddressLine(0);

            sb.append(address);

            String subAdd = addresses.get(0).getAddressLine(1);// If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            if (subAdd != null && !subAdd.isEmpty()) {
                sb.append(", ");
                sb.append(subAdd);
            }

            String city = addresses.get(0).getLocality();
            if (city != null && !city.isEmpty()) {
                sb.append(", ");
                sb.append(city);
            }

            return sb.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static void hideKeyBoard(Context context, SearchView myEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }

    public static void hideKeyBoardLocation(Context context, EditText myEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }



    public static void loadLocalFile(Context mContext, ImageView imageView, String path) {
        if (path != null && !path.isEmpty()) {
            Glide.with(mContext)
                    .load(new File(path))
                    .into(imageView);
        } else
            imageView.setImageResource(0);
    }

    public boolean hasCameraPermission(Activity context, int requestCode) {
        boolean has = ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!has) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, requestCode);
        }
        return has;
    }

    public static void loadImage(Context context, ImageView imgView, String url) {
        if (url != null && !url.isEmpty()) {
            //Picasso.with(context).load(url).into(imgView);
            //((SelectableRoundedImageView)iv3).setCornerRadiiDP(4, 4, 0, 0);
            Glide.with(context).load(url).placeholder(R.drawable.logo1).crossFade().into(imgView);
        } else
            imgView.setImageResource(0);
    }

    public static void loadImageAttchment(Context context, final ImageView imgView, String url, final ProgressBar pb) {
        if (url != null && !url.isEmpty()) {
            //Picasso.with(context).load(url).into(imgView);
            //((SelectableRoundedImageView)iv3).setCornerRadiiDP(4, 4, 0, 0);

            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pb.setVisibility(View.GONE);
                            imgView.setImageDrawable(resource);
                            return false;
                        }
                    })
                    .into(imgView);
        } else
            imgView.setImageResource(0);
    }

    public static void loadImageRactangle(final Context context, ImageView view, String url) {

        Picasso.with(context).load(url).into(view);
        // Glide.with(context).load(url).crossFade().into(view);
           /* Glide.with(context).load(url).asBitmap().placeholder(R.drawable.ractangle_corner_cut).into(new BitmapImageViewTarget(view) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    view.setImageDrawable(circularBitmapDrawable);
                    view.setTag(R.id.image, resource);
                }
            });*/
    }

    public static void loadImageRound(final Context context, ImageView view, String url) {
        if (url != null && !url.isEmpty())
            //Picasso.with(context).load(url).transform(new CircleTransform()).into(view);
            //Glide.with(context).load(url).crossFade().into(view);
            Glide.with(context).load(url).placeholder(R.drawable.round_profile_image).transform(new CircleTransformNew(context)).into(view);
            /*Glide.with(context).load(url).asBitmap().placeholder(R.drawable.round_img_backround).into(new BitmapImageViewTarget(view) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    view.setImageDrawable(circularBitmapDrawable);
                    view.setTag(R.id.image, resource);
                }
            });*/
        else
            view.setImageResource(0);
    }
    /**
     * For Samsung picture rotation problem
     */

    /**
     * This method is responsible for solving the rotation issue if exist. Also
     * scale the images to 1024x1024 resolution
     *
     * @param context
     *            The current context
     * @param selectedImage
     *            The Image URI
     * @return Bitmap image results
     * @throws IOException
     */
    public static Bitmap handleSamplingAndRotationBitmap(Context context,
                                                         Uri selectedImage) throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(
                selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH,
                MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(
                selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        String getRealPath = getRealPathFromURI_1(context, selectedImage);

        if (getRealPath == null) {
            getRealPath = getRealPathFromURI_2(context, selectedImage);
        }

        if (getRealPath != null) {
            img = rotateImageIfRequired(img, getRealPath);
        }

        return img;
    }
    // end

    /**
     * Other
     */

    public static String getRealPathFromURI_1(Context context, Uri contentUri) {
        try {
            Cursor cursor = null;
            try {
                String[] proj = { MediaStore.Images.Media.DATA };
                cursor = context.getContentResolver().query(contentUri, proj,
                        null, null, null);
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getRealPathFromURI_2(Context context, Uri contentURI) {
        String result;
        String[] data = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentURI, data,
                null, null, null);

        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    /**
     * Rotate an image if required.
     *
     * @param img
     *            The image bitmap

     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Bitmap img, String realPath)
            throws IOException {

        int orientation = getExifRotation(realPath);
        Log.e("rotateImage", ":" + orientation);
        switch (orientation) {
            case 90:
                return rotateImage(img, 90);
            case 180:
                return rotateImage(img, 180);
            case 270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }
    public static int getExifRotation(String imageRealPath) {
        if (imageRealPath == null) {
            return 0;
        } else {
            try {
                ExifInterface e = new ExifInterface(imageRealPath);
                switch (e.getAttributeInt("Orientation", 0)) {
                    case 3:
                        return 180;
                    case 6:
                        return 90;
                    case 8:
                        return 270;
                    default:
                        return 0;
                }
            } catch (IOException var2) {
                Log.e("Error getting Exif data", "" + var2);
                return 0;
            }
        }
    }


    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public static String changeUpToTwoPrecise(String currentValue){
        String changedValue="";
        if(!TextUtils.isEmpty(currentValue)) {
            Float value=Float.parseFloat(currentValue);
            DecimalFormat formater = new DecimalFormat("#.00");
            changedValue= formater.format(value);
        }
        return  changedValue;
    }

    public static boolean isTodayDate(int year, int month, int day) {
        boolean isToday=false;

        Calendar c = Calendar.getInstance();

// set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        Date today = c.getTime();

// or as a timestamp in milliseconds
        long todayInMillis = c.getTimeInMillis();

// user-specified date which you are testing
// let's say the components come from a form or something


// reuse the calendar to set user specified date
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

// and get that as a Date
        Date dateSpecified = c.getTime();

// test your condition
        if (dateSpecified.after(today)) {
            isToday= false;
        } else {
            isToday= true;
        }
        return isToday;
    }
    public CapturePhoto selectImage(BaseFragment context, EditText et, final boolean isBigImage) {
        final CapturePhoto capture = new CapturePhoto(context);
        final CharSequence[] items = { "Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context.getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    // take photo from camera
                    capture.dispatchTakePictureIntent(CapturePhoto.SHOT_IMAGE, 1, isBigImage);
                } else if (items[item].equals("Choose from Library")) {
                    // take photo from gallery
                    capture.dispatchTakePictureIntent(CapturePhoto.PICK_IMAGE, 2, isBigImage);
                }
            }
        });
        builder.show();

        return capture;
    }



    public static void loadImageRoundAllProduct(final Context context, ImageView view, String url) {

        if (url != null && !url.isEmpty())
            Glide.with(context).load(url).placeholder(R.drawable.round_profile_image).transform(new CircleTransformSignup(context)).into(view);
        else
            view.setImageResource(0);
    }


    public static void loadImageRoundSignup(final Context context, ImageView view, String url) {
        if (url != null && !url.isEmpty())
            Glide.with(context).load(url).placeholder(R.drawable.round_profile_image).transform(new CircleTransformSignup(context)).into(view);
        else
            view.setImageResource(0);
    }

    public static Drawable DrawableChange(Activity ctx, int d, int color) {
        Drawable drawable1 = ctx.getResources().getDrawable(d).mutate();
        drawable1.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        return drawable1;
    }
    public static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
    public static String day(int currentItem) {
        return new SimpleDateFormat("dd").format(Long.valueOf(Date.parse(((DateItem) MyApplication.arrDateItemwithBlank.get(currentItem)).getDate())));
    }

    public static String month(int currentItem) {
        return new SimpleDateFormat("MM").format(Long.valueOf(Date.parse(((DateItem) MyApplication.arrDateItemwithBlank.get(currentItem)).getDate())));
    }

    public static String year(int currentItem) {
        return new SimpleDateFormat("yyyy").format(Long.valueOf(Date.parse(((DateItem) MyApplication.arrDateItemwithBlank.get(currentItem)).getDate())));
    }

}
