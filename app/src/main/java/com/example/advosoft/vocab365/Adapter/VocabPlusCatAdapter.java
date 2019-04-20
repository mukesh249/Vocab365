package com.example.advosoft.vocab365.Adapter;

/**
 * Created by Mukesh on 01-02-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.advosoft.vocab365.Model.Dictionary_model;
import com.example.advosoft.vocab365.Model.VocabPlus_CatModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.Grammar;
import com.example.advosoft.vocab365.fragments.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class VocabPlusCatAdapter extends RecyclerView.Adapter<VocabPlusCatAdapter.ViewHolder> {

    private ArrayList<VocabPlus_CatModel> vocabPlus_catModelArrayList;
    VocabPlus_CatModel vocabPlus_catModel;
    Context context;
    public VocabPlusCatAdapter(Context context, ArrayList<VocabPlus_CatModel> vocabPlus_catModelArrayList) {
        this.vocabPlus_catModelArrayList=vocabPlus_catModelArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (vocabPlus_catModelArrayList == null)
            return 0;
        return vocabPlus_catModelArrayList.size();
    }

    @Override
    public VocabPlusCatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_plus_cat_raw, parent, false);
        return new ViewHolder(v,vocabPlus_catModelArrayList,context);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        vocabPlus_catModel = this.vocabPlus_catModelArrayList.get(position);
//        holder.first_letter.setBackgroundResource(R.drawable.round);
//        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
//        GradientDrawable drawable = (GradientDrawable) holder.first_letter.getBackground();
//        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        if (i % 2 == 0) {
//            drawable.setColor(randomAndroidColor);
//        } else {
//            drawable.setColor(Color.BLUE);
//        }
        holder.cat_name.setText(vocabPlus_catModel.getCat_name());
//        int[] androidColors = context.getResources().getIntArray(R.array.android_card_color);
//        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        holder.cardView.setCardBackgroundColor(randomAndroidColor);

        if (position==0){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.darkblue));
        }else if (position==1){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_3));
        }else if (position==2){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_2));
        }else if (position==3){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_4));
        }else if (position==4) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_5));
        }else if (position==5) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_1));
        }

        Glide.with(context)
                .load(vocabPlus_catModel.getImage()).placeholder(R.drawable.logo1)
                .into(holder.cat_image);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView cat_name;
        ImageView cat_image;
        CardView cardView;
        ArrayList<VocabPlus_CatModel> vocabPlus_catModelArrayList;
        int pos;
        Context context;
        ViewHolder(View itemView,ArrayList<VocabPlus_CatModel> vocabPlus_catModelArrayList,Context context) {
            super(itemView);
            this.context = context;
            this.vocabPlus_catModelArrayList = vocabPlus_catModelArrayList;
            cat_name = (TextView)itemView.findViewById(R.id.vocab_plus_cat_name);
            cat_image =(ImageView)itemView.findViewById(R.id.vocab_plus_cat_image);
            cardView = (CardView) itemView.findViewById(R.id.vocab_vlus_cat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    VocabPlus_CatModel vocabPlus_catModel = vocabPlus_catModelArrayList.get(pos);
                    if (vocabPlus_catModel.getCat_name().compareTo("Videos")==0){
                        Toast.makeText(context,"Coming soon",Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent = new Intent(context, Grammar.class);
                        intent.putExtra("cat_id",vocabPlus_catModel.getCat_id());
                        intent.putExtra("cat_name",vocabPlus_catModel.getCat_name());
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    mDataArray = listItem;
//                } else {
//                    ArrayList<Dictionary_model> filteredList = new ArrayList<>();
//                    for (Dictionary_model row : listItem) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getKey().toLowerCase().startsWith(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    mDataArray = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mDataArray;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mDataArray = (ArrayList<Dictionary_model>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

//    @Override
//    public Object[] getSections() {
//        String[] sectionsArr = new String[sections.length()];
//        for (int i=0; i<sections.length(); i++)
//        sectionsArr[i] = "" + sections.charAt(i);
//        return sectionsArr;
//    }

//    @Override
//    public int getPositionForSection(int section) {
//        Log.d("ListView", "Get position for section");
//        for (int i=0; i < mDataArray.size(); i++) {
//            String item = mDataArray.get(i).getKey().toLowerCase();
//            if (item.charAt(0) == sections.toLowerCase().charAt(section))
//                return i;
//        }
//        return 0;
//    }

//    @Override
//    public int getSectionForPosition(int i) {
//        return 0;
//    }

}