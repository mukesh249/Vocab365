package com.example.advosoft.vocab365.Adapter;

/**
 * Created by Mukesh on 01-02-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.advosoft.vocab365.Model.VocabPlus_CatModel;
import com.example.advosoft.vocab365.Model.VocabPlus_SubCatModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.Grammar;
import com.example.advosoft.vocab365.activities.Lession_and_practice;
import com.example.advosoft.vocab365.activities.MockTestYearPaper;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class VocabPlusSubCatAdapter extends RecyclerView.Adapter<VocabPlusSubCatAdapter.ViewHolder> {

    private ArrayList<VocabPlus_SubCatModel> vocabPlus_subcatModelArrayList;
    VocabPlus_SubCatModel vocabPlus_subcatModel;
    Context context;
    int i;

    public VocabPlusSubCatAdapter(Context context, ArrayList<VocabPlus_SubCatModel> vocabPlus_catModelArrayList) {
        this.vocabPlus_subcatModelArrayList=vocabPlus_catModelArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (vocabPlus_subcatModelArrayList == null)
            return 0;
        return vocabPlus_subcatModelArrayList.size();
    }

    @Override
    public VocabPlusSubCatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_plus_subcat_raw, parent, false);
        return new ViewHolder(v,vocabPlus_subcatModelArrayList,context);
    }

    int j=0;
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        vocabPlus_subcatModel = this.vocabPlus_subcatModelArrayList.get(position);

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        GradientDrawable drawable = (GradientDrawable) holder.sub_cat_no.getBackground();
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        if (i % 2 == 0) {
            holder.cat_image.setColorFilter(randomAndroidColor);
//            drawable.setColor(randomAndroidColor);
            holder.sub_cat_no.setTextColor(randomAndroidColor);
            drawable.setStroke(3,randomAndroidColor);
        } else {
            drawable.setColor(Color.BLUE);
        }

        holder.sub_cat_no.setText(++position+"");
        holder.sub_cat_name.setText(vocabPlus_subcatModel.getSub_cat_name());
//        Glide.with(context)
//                .load(vocabPlus_subcatModel.getSub_image()).placeholder(R.drawable.logo1)
//                .into(holder.cat_image);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_cat_name,sub_cat_no;
        ImageView cat_image;
        ArrayList<VocabPlus_SubCatModel> vocabPlus_subcatModelArrayList;
        int pos;
        Context context;
        ViewHolder(View itemView,ArrayList<VocabPlus_SubCatModel> vocabPlus_subcatModelArrayList,Context context) {
            super(itemView);
            this.context = context;
            this.vocabPlus_subcatModelArrayList = vocabPlus_subcatModelArrayList;
            sub_cat_name = (TextView)itemView.findViewById(R.id.sub_cat_vocab_plus_color_name);
            sub_cat_no = (TextView)itemView.findViewById(R.id.sub_cat_vocab_plus_color_tv);
            cat_image =(ImageView)itemView.findViewById(R.id.sub_cat_vocab_plus_color_arrow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    VocabPlus_SubCatModel vocabPlus_subcatModel = vocabPlus_subcatModelArrayList.get(pos);
                    if (vocabPlus_subcatModel.getParent_id().compareTo("5")==0){
                        //MockText
                        Intent intent = new Intent(context, MockTestYearPaper.class);
                        intent.putExtra("MockTest_id",vocabPlus_subcatModel.getSub_cat_id());
                        intent.putExtra("MockTest_name",vocabPlus_subcatModel.getSub_cat_name());
                        context.startActivity(intent);
                    }else if (vocabPlus_subcatModel.getParent_id().compareTo("4")==0){
                        //Previous Year Paper
                        Intent intent = new Intent(context, MockTestYearPaper.class);
                        intent.putExtra("MockTest_id",vocabPlus_subcatModel.getSub_cat_id());
                        intent.putExtra("MockTest_name",vocabPlus_subcatModel.getSub_cat_name());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, Lession_and_practice.class);
                        intent.putExtra("subcat_id",vocabPlus_subcatModel.getSub_cat_id());
                        intent.putExtra("subcat_name",vocabPlus_subcatModel.getSub_cat_name());
                        context.startActivity(intent);
                    }


//                    Toast.makeText(context,vocabPlus_subcatModel.getSub_cat_name(),Toast.LENGTH_SHORT).show();

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