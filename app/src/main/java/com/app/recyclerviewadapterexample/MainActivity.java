package com.app.recyclerviewadapterexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import com.google.gson.Gson;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CompAdapter compAdapter;

    private ArrayList<ItemCardComp> itemCardComps;
    private List<Integer> randComp;
    private JSONObject listJson;
    private int num = 0;
    org.json.simple.JSONArray jsonObject;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream inputStream = null;
        AssetManager am = getApplicationContext().getAssets();
        ImageView imageView = findViewById(R.id.img_comp);
        //imageView.setImageDrawable(R.drawable.);

        try {
            inputStream = am.open("shares_comp.json");
            JSONParser jsonParser = new JSONParser();
            jsonObject = (org.json.simple.JSONArray) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        randComp = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            num = new Random().nextInt(24);
            randComp.add(num);
        }
        List<Integer> newList = randComp.stream().distinct()
                .collect(Collectors.toList());
        itemCardComps = new ArrayList<>();

        for (int i = 0; i <= newList.size(); i++) {
            JSONObject object = (JSONObject) jsonObject.get(i);

            itemCardComps.add(new ItemCardComp(
                    object.get("compCodName").toString(),
                    object.get("val_money").toString(),
                    object.get("src").toString(),
                    object.get("pts").toString()));
        }

        recyclerView = findViewById(R.id.rcv);
        layoutManager = new LinearLayoutManager(this);
        compAdapter = new CompAdapter(itemCardComps, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(compAdapter);
    }
}