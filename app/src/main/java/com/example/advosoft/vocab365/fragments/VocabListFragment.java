package com.example.advosoft.vocab365.fragments;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.advosoft.vocab365.Adapter.UserHomeAdapter;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.example.advosoft.vocab365.wrapper.HomeDataWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Advosoft2 on 11/1/2017.
 */

public class VocabListFragment extends BaseFragment implements WebCompleteTask {
    static RecyclerView recyclerViewall;
    static SwipeRefreshLayout swipeRefreshLayout;
    static TextView emptyView;
    private static final int VERTICAL_ITEM_SPACE = 5;
    static ArrayList<HomeDataWrapper> feedsListall = new ArrayList();
    static UserHomeAdapter adapterall;
    String profileimg;
    Unbinder unbinder;

    public VocabListFragment() {
    }

    public static VocabListFragment getInstance(Bundle bundle) {
        VocabListFragment chef = new VocabListFragment();
        chef.setArguments(bundle);
        return chef;
    }

    Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    protected void initUi(View view) {
        recyclerViewall = (RecyclerView) view.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
        recyclerViewall.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewall.setLayoutManager(llm);
        recyclerViewall.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        callapiAll();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    boolean isRefreshing = false;

    public void refreshList() {
        isRefreshing = true;
        callapiAll();
    }

    @Override
    protected void setValueOnUi() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean onBackPressedListener() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.coman_layout_2, container, false);
        ButterKnife.bind(this, view);
        disableBackButton();
        initUi(view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("width of screen:-", "" + width);
        Log.d("height of screen:-", "" + height);
        unbinder = ButterKnife.bind(this, view);
        // setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        isRefreshing = true;
    }

    class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }

    private void callapiAll() {
        HashMap objectNew = new HashMap();
        objectNew.put("date","23-8-2018");
        Log.d("auth", MyApplication.getUserType(Constants.userType));

        new WebTask(getActivity(), WebUrls.BASE_URL + "/home_data", objectNew, VocabListFragment.this, RequestCode.CODE_HomeData, 1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        swipeRefreshLayout.setRefreshing(false);
        try {
            Log.e("response", response);
            if (taskcode == RequestCode.CODE_HomeData) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray data = null;
                    data = object.getJSONArray("data");
                    JSONObject jobj = null;
                    feedsListall.clear();
                    for (int i = 0; i < data.length(); i++) {
                        HomeDataWrapper contract = new HomeDataWrapper();
                        try {
                            jobj = data.getJSONObject(i);
                            contract.setId(jobj.getString("id"));
                            contract.setType(jobj.optString("type"));
                            contract.setTitle(jobj.optString("title"));
                            contract.setCourtesy(jobj.optString("courtesy"));
                            contract.setDescription(jobj.optString("description"));
                            contract.setImage(jobj.optString("image"));
                            contract.setVideo(jobj.optString("video"));
                            contract.setVideo_link(jobj.optString("video_link"));
                            contract.setSlug(jobj.optString("slug"));
                            contract.setMeaning(jobj.optString("meaning"));
                            contract.setSynonyms(jobj.optString("synonyms"));
                            contract.setAntonyms(jobj.optString("antonyms"));
                            contract.setExample(jobj.optString("example"));
                            contract.setPartofspeech(jobj.optString("part_of_speech"));
                            contract.setTrick_text(jobj.optString("trick_text"));
                            contract.setRelated_forms(jobj.optString("related_forms"));
                            contract.setStatus(jobj.optString("status"));
                            contract.setAdd_date(jobj.optString("add_date"));

                            feedsListall.add(contract);
                            emptyView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    setList();

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void setList() {
        System.out.println("service_list" + feedsListall.size());
        if (feedsListall.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            adapterall = new UserHomeAdapter(getActivity(), feedsListall);
            recyclerViewall.setAdapter(adapterall);


        }
    }
   /* @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_search_food_new, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            case R.id.addProduct:
                startActivity(new Intent(getActivity(), NotificationActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    /*public void searchsubmit(String text) {
        adapterall.getFilter().filter(text);
    }*/
}
