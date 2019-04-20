package com.example.advosoft.vocab365.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.advosoft.vocab365.Adapter.RecyclerViewAdapter;
import com.example.advosoft.vocab365.Model.Dictionary_model;
import com.example.advosoft.vocab365.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import fastscroll.app.fastscrollalphabetindex.AlphabetIndexFastScrollRecyclerView;

public class Dictionary extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private ArrayList<Dictionary_model> mDataArray = new ArrayList<>();
    ArrayList<String> mylist = new ArrayList<String>();
    EditText search_et;
    ImageView change_list;
    private AlphabetIndexFastScrollRecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;
    Map<String, Integer> mapIndex;
    LinearLayout indexLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dictionary(10K words)");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_blue);
//        indexLayout = (LinearLayout) findViewById(R.id.side_index);

        mRecyclerView = (AlphabetIndexFastScrollRecyclerView)findViewById(R.id.rcx);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setIndexBarColor("#e6e6e6");
        mRecyclerView.setIndexBarTextColor("#000000");
        mRecyclerView.setIndexBarCornerRadius(15);
        search_et =(EditText)findViewById(R.id.search);

        SearchingMethod();
        FetchAllWords();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void FetchAllWords(){
        JSONObject obj = null;
        try {
            obj = new JSONObject(loadJSONFromAsset());
            mDataArray.clear();

            int i = 0;
            Iterator<String> iter = obj.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                Log.d("name: ",key);
                try {
                    Dictionary_model item = new Dictionary_model();
                    Object value = obj.get(key);
                    item.setKey(key+"");
                    item.setKey_value(value+"");
                    item.setFirst(key.toUpperCase().charAt(0)+"");
                    mylist.add(key);
                    item.setCounter(0);
                    mDataArray.add(item);

                    mAdapter = new RecyclerViewAdapter(this,mDataArray);
                    mRecyclerView.setAdapter(mAdapter);


                } catch (JSONException e) {
                    // Something went wrong!
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dictionary,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
//                startActivity(new Intent(this, About.class));
                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                startActivity(Intent.createChooser(intent, "Share URL"));
                return true;
            case R.id.create_shortcut:
                addShortcut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void addShortcut() {
        //Adding shortcut for MainActivity
        //on Home screen
        Intent shortcutIntent = new Intent(getApplicationContext(),
                Dictionary.class);

        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "DemoVocab");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                        R.mipmap.ic_launcher));

        addIntent
                .setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }
    private void SearchingMethod() {
        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(search_et.getText());
            }
        });
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("english_to_hindi.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
