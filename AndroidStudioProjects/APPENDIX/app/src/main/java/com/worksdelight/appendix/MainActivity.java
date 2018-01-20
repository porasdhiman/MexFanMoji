package com.worksdelight.appendix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    Map<String, Integer> mapIndex;
    ListView abbreviationsList;
    EditText search;
    ArrayList<String> abberList = new ArrayList<>();
    ArrayList<String> acroList = new ArrayList<>();
    AbbreviationAdapter adapter;
    LinearLayout indexLayout;
    ImageView search_view;
    TextView header_txt;
    RelativeLayout search_view_layout;
    ArrayList<HashMap<String, String>> list = new ArrayList<>();
    ImageView compose_txtView;
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        global = (Global) getApplicationContext();
        compose_txtView = (ImageView) findViewById(R.id.compose_txtView);
        search = (EditText) findViewById(R.id.search);
        search.setCursorVisible(false);
        compose_txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ComposeActivity.class);
                startActivity(i);
            }
        });
        String arr[] = GlobalConstant.otherdata.split(System.lineSeparator());
        for (int i = 0; i < arr.length; i++) {
            HashMap<String, String> map = new HashMap<>();
            String value = arr[i];

            Log.e("value", value + " " + i);
            String aar1[] = arr[i].split(":");

            map.put(GlobalConstant.ABBER, aar1[0]);
            map.put(GlobalConstant.ACRON, aar1[1]);
            list.add(map);
        }
        class MapComparator implements Comparator<Map<String, String>> {
            private final String key;

            public MapComparator(String key) {
                this.key = key;
            }

            public int compare(Map<String, String> first,
                               Map<String, String> second) {
                // TODO: Null checking, both for maps and values
                String firstValue = first.get(key);
                String secondValue = second.get(key);
                return firstValue.compareTo(secondValue);
            }
        }


        Collections.sort(list, new MapComparator(GlobalConstant.ABBER));

        global.setMap(list);
        abbreviationsList = (ListView) findViewById(R.id.list_fruits);
        adapter = new AbbreviationAdapter(this, list);
        abbreviationsList.setAdapter(adapter);

        getIndexList(list);

        displayIndex();
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                search.setCursorVisible(true);
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if (text.length() > 0) {
                    adapter.filter(text);
                    indexLayout.setVisibility(View.GONE);


                } else {
                    adapter.filter(text);

                    indexLayout.setVisibility(View.VISIBLE);

                }

            }
        });


    }

    private void getIndexList(ArrayList<HashMap<String, String>> abbreviations) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < abbreviations.size(); i++) {
            String abber = abbreviations.get(i).get(GlobalConstant.ABBER);


            String index = abber.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        indexLayout = (LinearLayout) findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        abbreviationsList.setSelection(mapIndex.get(selectedIndex.getText()));
    }

    /*public String ReadFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }*/
}