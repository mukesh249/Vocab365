package com.example.advosoft.vocab365.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.HomeDetailActivity;
import com.example.advosoft.vocab365.activities.VocabDetailActivity;
import com.example.advosoft.vocab365.fragments.BaseFragment;
import com.example.advosoft.vocab365.fragments.MyApplication;
import com.example.advosoft.vocab365.views.Constants;
import com.example.advosoft.vocab365.views.Utils;
import com.example.advosoft.vocab365.webutility.RequestCode;
import com.example.advosoft.vocab365.webutility.WebCompleteTask;
import com.example.advosoft.vocab365.webutility.WebTask;
import com.example.advosoft.vocab365.webutility.WebUrls;
import com.example.advosoft.vocab365.wrapper.HomeDataWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Advosoft2 on 3/8/2017.
 */

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.MyViewHolder> implements Filterable {
    private List<HomeDataWrapper> feedsListall;
    private List<HomeDataWrapper> mArrayList;
    private Context context;
    private LayoutInflater inflater;
    int selectedPosition = -1;
    public int deleteCartPos = -1;
    int position_select = 0;
    BaseFragment baseFragment = null;
    ArrayList<String> imglist;
    String urlAllJob = "";
    private final static int HEADER_VIEW = 0;
    private final static int CONTENT_VIEW = 1;
    private final static int CONTENT_VIEW2 = 2;
    public static final String KEY_propertyID = "id";
    String id;
    RequestQueue queue;
    ProgressDialog pd;
    private int mOffset;


    public UserHomeAdapter(Context context, List<HomeDataWrapper> feedsListall) {
        this.context = context;
        this.feedsListall = feedsListall;
        this.mArrayList = feedsListall;
      /*  inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pd = new ProgressDialog(context);
        queue = Volley.newRequestQueue(context);*/
    }

    public void setList(List<HomeDataWrapper> BindAllJobList) {
        if (BindAllJobList != null)
            this.feedsListall.addAll(BindAllJobList);
        else
            this.feedsListall = BindAllJobList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = 0;
        layoutRes = R.layout.home_page_item;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    feedsListall = mArrayList;
                } else {

                    ArrayList<HomeDataWrapper> filteredList = new ArrayList<>();

                    for (HomeDataWrapper androidVersion : mArrayList) {
                        if (androidVersion.getCourtesy().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        } else if (androidVersion.getTitle().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    feedsListall = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = feedsListall;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                feedsListall = (ArrayList<HomeDataWrapper>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void clearList() {
        if (feedsListall != null)
            feedsListall.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HomeDataWrapper feeds = feedsListall.get(position);
        holder.title.setText(feeds.getTitle());
        if (feeds.getType().compareTo("Vocab")==0){
            holder.discription.setText(feeds.getMeaning());
        }else {
            holder.discription.setText(feeds.getDescription());
        }
        holder.home_frg_upvotes_count.setText(feeds.getLikes()+" likes");
        if (feeds.getIsLikes().compareTo("1")==0){
            holder.likes_news.setBackground(context.getResources().getDrawable(R.drawable.upvotes_click));
        }else {
            holder.likes_news.setBackground(context.getResources().getDrawable(R.drawable.upvotes));
        }

        if (feeds.getType().compareTo("Video") == 0) {

                 Bitmap ThumbnailUtilscdc = ThumbnailUtils.createVideoThumbnail(feeds.getVideo(), MediaStore.Video.Thumbnails.MINI_KIND);
                 BitmapDrawable bitmapDrawable = new BitmapDrawable(ThumbnailUtilscdc);

                  if (ThumbnailUtilscdc!=null){
                      holder.image.setImageBitmap(ThumbnailUtilscdc);
                  }else {
                      holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.logo1));
                  }



        } else {
             Utils.loadImage(context, holder.image, feeds.getImage());
        }

        try {
            String last_select = feeds.getAdd_date();

            String[] date_data=feeds.getAdd_date().split("\\s+");
           /* String last_date1;
            Date startDate;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm aa", Locale.ENGLISH);
            DateFormat target_date = new SimpleDateFormat("HH:mm aa", Locale.ENGLISH);
            startDate = df.parse(last_select);
            last_date1 = target_date.format(startDate);
            // String datata[] = last_date1.split("-");
            Log.d("date_current",startDate+"");
            Log.d("date_change",last_date1+"");*/
            holder.date.setText(date_data[1]+" "+date_data[2]);
            holder.type.setText(feeds.getType());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (feeds.getType().compareTo("Vocab") == 0) {
                        context.startActivity(new Intent(context, VocabDetailActivity.class).putExtra("id", feeds.getId()).putExtra("word", feeds.getTitle()).putExtra("type", feeds.getType()));
                    } else {
                        context.startActivity(new Intent(context, HomeDetailActivity.class).putExtra("id", feeds.getId()).putExtra("type", feeds.getType()).putExtra("data",feeds));
                    }

                }
            });


        } catch (Exception e) {
            Log.d("date_exception", e.toString());

        }


    }

    public void removeBindAllJob() {
        if (deleteCartPos >= 0) {
            feedsListall.remove(deleteCartPos);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return feedsListall.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,WebCompleteTask {
        private TextView title, discription, date,type,home_frg_upvotes_count;
        private ImageView image,likes_news;
        LinearLayout home_frg_upvotes;
        int pos;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            type = (TextView) itemView.findViewById(R.id.type);
            home_frg_upvotes = (LinearLayout)itemView.findViewById(R.id.home_frg_upvotes);
            likes_news = (ImageView)itemView.findViewById(R.id.likes_news);
            home_frg_upvotes_count = (TextView) itemView.findViewById(R.id.home_frg_upvotes_count);
            home_frg_upvotes.setOnClickListener(this);
            discription = (TextView) itemView.findViewById(R.id.discription);
            date = (TextView) itemView.findViewById(R.id.date);
            image = (ImageView) itemView.findViewById(R.id.image_thumb);
        }
        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.home_frg_upvotes){
                LikeMethod(pos);
            }
        }

        public void LikeMethod(int poss){
            poss = getAdapterPosition();
            HomeDataWrapper homeDataWrapper = feedsListall.get(poss);
            HashMap objectNew = new HashMap();
            objectNew.put("user_id", MyApplication.getUserID(Constants.UserID));
            objectNew.put("type","news");
            objectNew.put("b_id",homeDataWrapper.getId());
            new WebTask(context, WebUrls.BASE_URL + "/like", objectNew, this, RequestCode.CODE_Upvotes, 1);
        }
        @Override
        public void onComplete(String response, int taskcode) {
            if (taskcode==RequestCode.CODE_Upvotes){

                try {
                    JSONObject object = new JSONObject(response);

                    if (object.optString("status").equals("success")){
                        home_frg_upvotes_count.setText(object.optString("data")+" likes");
                        Toast.makeText(context,object.optString("message"),Toast.LENGTH_SHORT).show();

                        if (object.optString("message").equalsIgnoreCase("Like")){
                            likes_news.setBackground(context.getResources().getDrawable(R.drawable.upvotes_click));
                        }else {
                            likes_news.setBackground(context.getResources().getDrawable(R.drawable.upvotes));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);

            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

}
