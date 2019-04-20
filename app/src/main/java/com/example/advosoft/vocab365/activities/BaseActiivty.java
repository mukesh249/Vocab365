package com.example.advosoft.vocab365.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.BaseFragment;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.views.Utils;


/**
 * Created by Advosoft2 on 2/28/2017.
 */

public  class BaseActiivty extends AppCompatActivity {
    ProgressDialog progressDialog;
    public CharSequence mTitle;
    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();

    }
    public boolean enableBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }
    protected Bundle getBundle() {
        return getIntent().getBundleExtra(Constants.bundleArg);
    }
    @TargetApi(21)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    public void replaceFragment(@IdRes int container, Fragment fragment) {
        replaceFragment(container, fragment, null, false);
    }
    public void replaceFragement(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }
    public void replaceFragement(Fragment fragment, String tagValue, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentTransaction();
        if (addToBackStack)
            transaction.addToBackStack(tagValue);
        transaction.replace(R.id.container, fragment, tagValue).commit();
    }

    public void toast(final String message) {
        Toast.makeText(BaseActiivty.this, message, Toast.LENGTH_SHORT).show();
    }

    public void addFragment(Fragment fragment, String tagValue, boolean addToBackStack) {
        //MyApplication.getApplication().addFragment(fragment);
        FragmentTransaction ft = getFragmentTransaction();
        ft.add(R.id.container, fragment, tagValue);
        if (addToBackStack)
            ft.addToBackStack(tagValue);
        ft.commit();
    }
    public void replaceFragment(@IdRes int container, Fragment fragment, Bundle arguments, boolean addTobackStack) {
        if (addTobackStack) {
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(container, fragment).addToBackStack("back").commit();
        } else {
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
        }

    }

    @SuppressLint("NewApi")
    public void setTitleOnAction(String text) {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setIcon(R.side_menu.email_icon);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        if (text != null)
            mTitle = text;
        try {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(Utils.setTitle(mTitle));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void HideKeyboard(View view, Context context) {
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public void replaceFragment(@IdRes int container, Fragment fragment, Bundle arguments) {

        replaceFragment(container, fragment, arguments, false);
    }

   /* public void replaceFragement(android.app.Fragment fragment) {
        android.app.FragmentTransaction transaction = getFragmentTransactionNormal();
        transaction.replace(R.id.container, fragment).commit();
    }*/

    public FragmentTransaction getFragmentTransaction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up);
        // transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up, R.anim.slide_out_down, R.anim.slide_in_up);
        return transaction;
    }
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public android.app.FragmentTransaction getFragmentTransactionNormal() {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        return transaction;
    }

   /* public void addFragment(Fragment fragment, String tagValue, boolean addToBackStack) {
        //MyApplication.getApplication().addFragment(fragment);
        FragmentTransaction ft = getFragmentTransaction();
        ft.add(R.id.container, fragment, tagValue);
        if (addToBackStack)
            ft.addToBackStack(tagValue);
        ft.commit();
    }

    public void replaceFragement(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

    public void replaceFragement(Fragment fragment, String tagValue, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentTransaction();
        if (addToBackStack)
            transaction.addToBackStack(tagValue);
        transaction.replace(R.id.container, fragment, tagValue).commit();
    }
    public void replaceFragement(Fragment fragment, String tagValue) {
        FragmentTransaction transaction = getFragmentTransaction();
        transaction.replace(R.id.container, fragment, tagValue).commit();
    }*/
    public void enableLoadingBar(boolean enable) {
        if (enable) {
            loadProgressBar(null, getString(R.string.api_hiting), false);
        } else {
            dismissProgressBar();
        }
    }

    public void loadProgressBar(String title, String message, boolean cancellable) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(this, title, message, false, cancellable);
    }

    public void dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    public void configureToolBar(@NonNull final Toolbar toolbar) {
        setSupportActionBar(toolbar);
        setTitle("");
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/" + "Bariol Regular.ttf");
                textView.setTypeface(myCustomFont);
                textView.setTextSize(12f);
                textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
        }
    }

    public Fragment getFragmentByTag(String tAG) {
        return getSupportFragmentManager().findFragmentByTag(tAG);
    }


    public void hideKeyboard(Context context) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public int getBackStackCount() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected boolean disableBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // actionBar.setHomeAsUpIndicator(R.drawable.menu_icon);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            // setTitleOnAction("");
        }

        return true;
    }

    public BaseFragment getTopFragment(boolean isRefreshTitle) {
        BaseFragment fr = null;
        try {
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            if (count > 0) {
                FragmentManager.BackStackEntry en = fm.getBackStackEntryAt(count - 1);
                String name = en.getName();
                fr = (BaseFragment) fm.findFragmentByTag(name);
                if (isRefreshTitle)
                    setTitleOnAction(name);
            }
            return fr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeTopFragement() {
        getSupportFragmentManager().popBackStack();
    }

    public void startMyActivity(Class targetActivity, Bundle myBundle) {
        Intent intent = new Intent(this, targetActivity);
        if (myBundle != null)
            intent.putExtra(Constants.bundleArg, myBundle);
        startActivity(intent);
    }

    public void startMyActivityForResult(Class targetActivity, Bundle myBundle, int requestCode) {
        Intent intent = new Intent(this, targetActivity);
        if (myBundle != null)
            intent.putExtra(Constants.bundleArg, myBundle);
        startActivityForResult(intent, requestCode);
    }



    public AlertDialog.Builder getAlertDialogBuilder(String title, String message, boolean cancellable) {
        return new AlertDialog.Builder(this, R.style.AppTheme_AlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(cancellable);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
