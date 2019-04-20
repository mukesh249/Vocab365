package com.example.advosoft.vocab365.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Advosoft2 on 3/8/2017.
 */

public class QuestionSlideAdapterSolution extends RecyclerView.Adapter<QuestionSlideAdapterSolution.MyViewHolder> implements Filterable {
    private List<QuestionModel> feedsListall;
    private List<QuestionModel> mArrayList;
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
    ViewPager mViewPager;
    DrawerLayout drawer;
    String qua_ser[];


    public QuestionSlideAdapterSolution(Context context, List<QuestionModel> feedsListall, ViewPager pager, DrawerLayout drawer, String qua_ser[]) {
        this.context = context;
        this.feedsListall = feedsListall;
        this.mArrayList = feedsListall;
        this.mViewPager = pager;
        this.drawer = drawer;
        this.qua_ser=qua_ser;
      /*  inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pd = new ProgressDialog(context);
        queue = Volley.newRequestQueue(context);*/
    }

    public void setList(List<QuestionModel> BindAllJobList) {
        if (BindAllJobList != null)
            this.feedsListall.addAll(BindAllJobList);
        else
            this.feedsListall = BindAllJobList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutRes = 0;
        layoutRes = R.layout.list_item_solution_side;
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

                    ArrayList<QuestionModel> filteredList = new ArrayList<>();

                    for (QuestionModel androidVersion : mArrayList) {
                        if (androidVersion.getQuestion().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        } else if (androidVersion.getQuestion().toLowerCase().contains(charString)) {
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
                feedsListall = (ArrayList<QuestionModel>) filterResults.values;
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
        final QuestionModel feeds = feedsListall.get(position);
        if (qua_ser[position].compareTo("0")==0){
            holder.tv_num.setBackground(context.getResources().getDrawable(R.drawable.gray_circle));
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.black));
        }else if (qua_ser[position].compareTo("1")==0){
            holder.tv_num.setBackground(context.getResources().getDrawable(R.drawable.green_circle));
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.white));
        }else if (qua_ser[position].compareTo("2")==0){
            holder.tv_num.setBackground(context.getResources().getDrawable(R.drawable.red_circle));
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.tv_num.setText(feeds.getNumber());
        holder.tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(position, true);
                drawer.closeDrawer(Gravity.RIGHT);
            }
        });


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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_num, tv_direction;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_direction = (TextView) itemView.findViewById(R.id.direction);


        }
    }

}
