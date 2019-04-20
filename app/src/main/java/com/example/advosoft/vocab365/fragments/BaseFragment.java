package com.example.advosoft.vocab365.fragments;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.BaseActiivty;
import com.example.advosoft.vocab365.views.Constants;

import java.util.Locale;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment extends Fragment implements  View.OnClickListener {
    protected static final String ARG_SECTION_NUMBER = "section_number";
    protected Bundle myBundle = null;
    ProgressDialog progressDialog;
    Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    protected abstract void initUi(View view);

    protected abstract void setValueOnUi();

    protected abstract void setListener();

    public abstract boolean onBackPressedListener();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mAppComponent = DaggerAppComponent.builder()
//                .appComponent(getApp().getAppComponent()).build();;
        // setStatusBarColor(getActivity(), R.color.colorPrimary);
        // activity = (HomeActivity) getActivity();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Configuration config = getActivity().getResources().getConfiguration();
        String lang = settings.getString("LANG", "");
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * Method to set status bar color
     *
     * @param activity context
     * @param color    color id
     */
    @TargetApi(21)
    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*try {
            getActionBar().show();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        enableBackButton();
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        //this.setTitleOnAction(title);	in
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NewApi")
    public void setTitleOnAction(String text, boolean isUpperCase) {
        //title  = text;
        ((BaseActiivty) getActivity()).setTitleOnAction(text);
    }
    public void startMyActivityForResult(Class targetActivity, Bundle myBundle, int requestCode) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (myBundle != null)
            intent.putExtra(Constants.bundleArg, myBundle);
        startActivityForResult(intent, requestCode);
    }
    public void startMyActivity(Class targetActivity, Bundle myBundle) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (myBundle != null)
            intent.putExtra(Constants.bundleArg, myBundle);
        startActivity(intent);
    }



    public void enableLoadingBar(boolean enable) {
        if (enable) {
            loadProgressBar(null, getString(R.string.api_hiting), false);
        } else {
            dismissProgressBar();
        }
    }



    public void loadProgressBar(String title, String message, boolean cancellable) {
        if (progressDialog == null)
            progressDialog = ProgressDialog.show(getActivity(), title, message, false, cancellable);
    }

    public void dismissProgressBar() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
    public void addFragment(Fragment fragment, String value, boolean addToBackStack) {
        ((BaseActiivty) getActivity()).addFragment(fragment, value, addToBackStack);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        ((BaseActiivty) getActivity()).replaceFragement(fragment);
    }

    public void replaceFragment(Fragment fragment, String tag, boolean backStack) {
        ((BaseActiivty) getActivity()).replaceFragement(fragment, tag, backStack);
    }

    public void replaceFragment(@IdRes int container, Fragment fragment) {
        replaceFragment(container, fragment, null);
    }

    public void replaceFragment(@IdRes int container, Fragment fragment, Bundle arguments) {
        fragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();

    }
    protected void removeTopFragment() {
        ((BaseActiivty) getActivity()).removeTopFragement();
    }
    public void toast(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    protected boolean enableBackButton() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }

    protected boolean disableBackButton() {
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    protected ActionBar getActionBar() {
        return ((BaseActiivty) getActivity()).getSupportActionBar();
    }

}