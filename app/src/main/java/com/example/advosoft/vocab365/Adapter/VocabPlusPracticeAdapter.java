package com.example.advosoft.vocab365.Adapter;

/**
 * Created by Mukesh on 01-02-2017.
 */

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

import com.example.advosoft.vocab365.Model.QuestionModel;
import com.example.advosoft.vocab365.Model.VocabPlus_lessonModel;
import com.example.advosoft.vocab365.Model.VocabPlus_practiceModel;
import com.example.advosoft.vocab365.R;
import com.example.advosoft.vocab365.activities.ActivityQuestionQuiz;

import java.util.ArrayList;
import java.util.Random;

public class VocabPlusPracticeAdapter extends RecyclerView.Adapter<VocabPlusPracticeAdapter.ViewHolder> {

    private ArrayList<VocabPlus_practiceModel> vocabPlus_practiceModelArrayList;
    private ArrayList<ArrayList<QuestionModel>> vocabPlus_QuestionModelArrayList;
    VocabPlus_practiceModel vocabPlus_practiceModel;
    QuestionModel questionModel;
    Context context;
    int i;

    public VocabPlusPracticeAdapter(Context context, ArrayList<VocabPlus_practiceModel> vocabPlus_practiceModelArrayList,ArrayList<ArrayList<QuestionModel>> vocabPlus_QuestionModelArrayList) {
        this.vocabPlus_practiceModelArrayList=vocabPlus_practiceModelArrayList;
        this.vocabPlus_QuestionModelArrayList=vocabPlus_QuestionModelArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (vocabPlus_QuestionModelArrayList == null)
            return 0;
        return vocabPlus_QuestionModelArrayList.size();
    }

    @Override
    public VocabPlusPracticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocab_plus_lesson_raw, parent, false);
        return new ViewHolder(v,vocabPlus_practiceModelArrayList,context,vocabPlus_QuestionModelArrayList);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        vocabPlus_practiceModel = this.vocabPlus_practiceModelArrayList.get(position);

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

        holder.lesson_no.setText(vocabPlus_practiceModel.getPractice_id()+"");
        holder.lesson_name.setText("Test "+vocabPlus_practiceModel.getPractice_id());
//        Glide.with(context)
//                .load(vocabPlus_subcatModel.getSub_image()).placeholder(R.drawable.logo1)
//                .into(holder.cat_image);
        int finalPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // context.startActivity(new Intent(context, ActivityQuestionQuiz.class).putExtra("data",vocabPlus_QuestionModelArrayList.get(position));
                context.startActivity(new Intent(context,ActivityQuestionQuiz.class).putExtra("data",vocabPlus_QuestionModelArrayList.get(finalPosition)));

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lesson_name,lesson_no;
        ImageView cat_image;
        ArrayList<VocabPlus_practiceModel> vocabPlus_practiceModelArrayList;
        ArrayList<ArrayList<QuestionModel>> vocabPlus_QuestionModelArrayList;
        int pos;
        Context context;
        ViewHolder(View itemView,ArrayList<VocabPlus_practiceModel> vocabPlus_practiceModelArrayList,Context context,ArrayList<ArrayList<QuestionModel>> vocabPlus_QuestionModelArrayList) {
            super(itemView);
            this.context = context;
            this.vocabPlus_practiceModelArrayList = vocabPlus_practiceModelArrayList;
            this.vocabPlus_QuestionModelArrayList = vocabPlus_QuestionModelArrayList;
            lesson_name = (TextView)itemView.findViewById(R.id.lesson_vocab_plus_color_name);
            lesson_no = (TextView)itemView.findViewById(R.id.lesson_vocab_plus_color_tv);
            cat_image =(ImageView)itemView.findViewById(R.id.lesson_vocab_plus_color_lock);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = getAdapterPosition();
                    VocabPlus_practiceModel vocabPlus_practiceModel = vocabPlus_practiceModelArrayList.get(pos);

//                        Intent intent = new Intent(context, Video_Open.class);
//                        intent.putExtra("URL_VIDEO",vocabPlus_lessonModel.getLesson_video());
                        Toast.makeText(context,"Text "+vocabPlus_practiceModel.getPractice_id(),Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, ActivityQuestionQuiz.class).putExtra("data",vocabPlus_QuestionModelArrayList.get(Integer.parseInt(vocabPlus_practiceModel.getPractice_id()))));
                }
            });*/

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