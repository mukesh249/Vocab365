package com.example.advosoft.vocab365.activities;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.advosoft.vocab365.Adapter.UserBookmarkAdapter;
import com.example.advosoft.vocab365.Adapter.UserHomeAdapter;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.fragments.PastListFragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by advosoft on 9/14/2018.
 */

public class ActivityBookmarks extends BaseActiivty implements WebCompleteTask {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool)
    LinearLayout tool;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView emptyView;
    boolean isRefreshing = false;
    private static final int VERTICAL_ITEM_SPACE = 25;
    static ArrayList<HomeDataWrapper> feedsListall = new ArrayList();
    static UserBookmarkAdapter adapterall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmars);
        ButterKnife.bind(this);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recycleView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(llm);
        recycleView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        callapiAll();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }
    public void refreshList() {
        isRefreshing = true;
        callapiAll();
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
        objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
        objectNew.put("type", "news");
        Log.d("auth", MyApplication.getUserType(Constants.userType));

        new WebTask(ActivityBookmarks.this, WebUrls.BASE_URL + "/list_bookmarks", objectNew, ActivityBookmarks.this, RequestCode.CODE_HomeData, 1);
    }
    @Override
    public void onComplete(String response, int taskcode) {
        Log.d("response",response);
        swipeRefreshLayout.setRefreshing(false);
        try {
            Log.e("response", response);
            if (taskcode == RequestCode.CODE_HomeData) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    JSONArray data = null;
                    data = object.optJSONArray("data");
                    JSONObject jobj = null;
                    feedsListall.clear();
                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            HomeDataWrapper contract = new HomeDataWrapper();
                            try {
                                jobj = data.getJSONObject(i).getJSONObject("bookmarks");
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
                                contract.setAdd_date(jobj.optString("date"));
                                contract.setLikes(data.getJSONObject(i).optString("likes_count"));
                                contract.setIsLikes(jobj.optString("likes"));

                                feedsListall.add(contract);
                                emptyView.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            adapterall.notifyDataSetChanged();
        } else {
            emptyView.setVisibility(View.GONE);
            adapterall = new UserBookmarkAdapter(ActivityBookmarks.this, feedsListall);
            recycleView.setAdapter(adapterall);


        }
    }

    @Override
    protected void onRestart() {
        callapiAll();
        super.onRestart();
    }
}
