package com.example.advosoft.vocab365.Adapter;

/**
 * Created by Mukesh on 01-02-2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.advosoft.vocab365.Model.Dictionary_model;
import com.example.advosoft.vocab365.R;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable, SectionIndexer {

//    private List<String> mDataArray;
    private ArrayList<Dictionary_model> mDataArray;
    //abcdefghilmnopqrstuvz
    private static String sections = "ABCDEFGHIGKLMNOPQRSTUVWXYZ";
    ArrayList<Dictionary_model> listItem;
    private ArrayList<Integer> mSectionPositions;
    Dictionary_model dictionary_model;
    Context context;
    int i;

//    private List<UserModel> mDataset;


    public RecyclerViewAdapter(Context context, ArrayList<Dictionary_model> dataset) {
        mDataArray = dataset;
        listItem = dataset;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dictionary_raw, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        dictionary_model = this.mDataArray.get(position);

        holder.first_letter.setBackgroundResource(R.drawable.round);
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        GradientDrawable drawable = (GradientDrawable) holder.first_letter.getBackground();
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        if (i % 2 == 0) {
            drawable.setColor(randomAndroidColor);
        } else {
            drawable.setColor(Color.BLUE);
        }

        holder.key.setText(dictionary_model.getKey());
        holder.first_letter.setText(dictionary_model.getFirst());
        holder.value.setText("("+dictionary_model.getKey_value()+")");
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataArray = listItem;
                } else {
                    ArrayList<Dictionary_model> filteredList = new ArrayList<>();
                    for (Dictionary_model row : listItem) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getKey().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mDataArray = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataArray;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataArray = (ArrayList<Dictionary_model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
      TextView key,value,first_letter;
        ViewHolder(View itemView) {
            super(itemView);
            first_letter = (TextView)itemView.findViewById(R.id.first_letter);
            key = (TextView)itemView.findViewById(R.id.key);
            value =(TextView)itemView.findViewById(R.id.value);

        }
    }
    @Override
    public Object[] getSections() {
        String[] sectionsArr = new String[sections.length()];
        for (int i=0; i<sections.length(); i++)
        sectionsArr[i] = "" + sections.charAt(i);
        return sectionsArr;
    }

    @Override
    public int getPositionForSection(int section) {
        Log.d("ListView", "Get position for section");
        for (int i=0; i < mDataArray.size(); i++) {
            String item = mDataArray.get(i).getKey().toLowerCase();
            if (item.charAt(0) == sections.toLowerCase().charAt(section))
                return i;
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

}