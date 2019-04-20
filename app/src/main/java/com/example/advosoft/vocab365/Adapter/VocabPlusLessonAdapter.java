package com.example.advosoft.vocab365.Adapter;

/**
 * Created by Mukesh on 01-02-2017.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.advosoft.vocab365.Model.VocabPlus_SubCatModel;
import com.example.advosoft.vocab365.Model.VocabPlus_lessonModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.Lession_and_practice;
import com.example.advosoft.vocab365.activities.Video_Open;

import java.util.ArrayList;
import java.util.Random;

public class VocabPlusLessonAdapter extends RecyclerView.Adapter<VocabPlusLessonAdapter.ViewHolder> {

    private ArrayList<VocabPlus_lessonModel> vocabPlus_lessonModelArrayList;
    VocabPlus_lessonModel vocabPlus_lessonModel;
    Context context;
    int i;

    public VocabPlusLessonAdapter(Context context, ArrayList<VocabPlus_lessonModel> vocabPlus_lessonModelArrayList) {
        this.vocabPlus_lessonModelArrayList=vocabPlus_lessonModelArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (vocabPlus_lessonModelArrayList == null)
            return 0;
        return vocabPlus_lessonModelArrayList.size();
    }

    @Override
    public VocabPlusLessonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_plus_lesson_raw, parent, false);
        return new ViewHolder(v,vocabPlus_lessonModelArrayList,context);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        vocabPlus_lessonModel = this.vocabPlus_lessonModelArrayList.get(position);

        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        GradientDrawable drawable = (GradientDrawable) holder.lesson_no.getBackground();
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        if (i % 2 == 0) {
            holder.cat_image.setColorFilter(randomAndroidColor);
//            drawable.setColor(randomAndroidColor);
            drawable.setStroke(3,randomAndroidColor);
        } else {
            drawable.setColor(Color.BLUE);
        }

        holder.lesson_no.setText(++position+"");
        holder.lesson_name.setText(vocabPlus_lessonModel.getLesson_name());
//        Glide.with(context)
//                .load(vocabPlus_subcatModel.getSub_image()).placeholder(R.drawable.logo1)
//                .into(holder.cat_image);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lesson_name,lesson_no;
        ImageView cat_image;
        ArrayList<VocabPlus_lessonModel> vocabPlus_lessonArrayList;
        int pos;
        Context context;
        ViewHolder(View itemView,ArrayList<VocabPlus_lessonModel> vocabPlus_lessonArrayList,Context context) {
            super(itemView);
            this.context = context;
            this.vocabPlus_lessonArrayList = vocabPlus_lessonArrayList;
            lesson_name = (TextView)itemView.findViewById(R.id.lesson_vocab_plus_color_name);
            lesson_no = (TextView)itemView.findViewById(R.id.lesson_vocab_plus_color_tv);
            cat_image =(ImageView)itemView.findViewById(R.id.lesson_vocab_plus_color_lock);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    VocabPlus_lessonModel vocabPlus_lessonModel = vocabPlus_lessonArrayList.get(pos);
                    if (!vocabPlus_lessonModel.getLesson_pdf().equals("")){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setDataAndType(Uri.parse(vocabPlus_lessonModel.getLesson_pdf()), "application/pdf");
                        //Toast.makeText(context,vocabPlus_lessonModel.getLesson_name(),Toast.LENGTH_SHORT).show();
                        context.startActivity(browserIntent);
                    } else if (!vocabPlus_lessonModel.getLesson_video().equals("")){

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setDataAndType(Uri.parse(vocabPlus_lessonModel.getLesson_video()), "application/video*");
                       // Toast.makeText(context,vocabPlus_lessonModel.getLesson_name(),Toast.LENGTH_SHORT).show();
                        context.startActivity(browserIntent);

//                        Intent intent = new Intent(context, Video_Open.class);
//                        intent.putExtra("URL_VIDEO",vocabPlus_lessonModel.getLesson_video());
//                        Toast.makeText(context,vocabPlus_lessonModel.getLesson_name(),Toast.LENGTH_SHORT).show();
//                        context.startActivity(intent);
                    } else if (!vocabPlus_lessonModel.getLesson_video_link().equals("")){
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(vocabPlus_lessonModel.getLesson_video_link())));
                    } else {
                        Toast.makeText(context,"URL not found",Toast.LENGTH_SHORT).show();
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