package com.app.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import com.app.data.models.Companies;
import com.app.recyclerviewadapterexample.R;

import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CompAdapter compAdapter;
    private CompaniesViewModel viewModel;

    private ArrayList<Companies> itemCardComps;
    private List<Integer> randComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcv);
        layoutManager = new LinearLayoutManager(this);
        compAdapter = new CompAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(compAdapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(CompaniesViewModel.class);
        viewModel.getAllComp().observe(this, new Observer<List<Companies>>() {
            @Override
            public void onChanged(List<Companies> companies) {
                compAdapter.setComps(companies);
            }
        });
    }
}