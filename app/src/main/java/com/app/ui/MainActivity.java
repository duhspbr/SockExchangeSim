package com.app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.models.Companies;
import com.app.recyclerviewadapterexample.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CompAdapter compAdapter;
    private CompaniesViewModel viewModel;
    private ImageView btnUpdate;
    private TextView lblBestPct, lblLowePct, lblMoneyBest, lblMoneyLow, lblCodBest,
    lblCodLow, lblSum;
    private NumberFormat nf;

    private ArrayList<Companies> itemCardComps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblSum = findViewById(R.id.lblSum);
        lblBestPct = findViewById(R.id.pct_best);
        lblLowePct = findViewById(R.id.percent_low);
        lblCodBest = findViewById(R.id.id_best);
        lblCodLow = findViewById(R.id.id_low);
        lblMoneyBest = findViewById(R.id.text_money_best);
        lblMoneyLow = findViewById(R.id.text_money_low);
        btnUpdate = findViewById(R.id.btnUpdate);
        recyclerView = findViewById(R.id.rcv);
        layoutManager = new LinearLayoutManager(this);
        compAdapter = new CompAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(compAdapter);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(CompaniesViewModel.class);

        viewModel.getAllComp().observe(this, companies ->
                compAdapter.setComps(companies));

        viewModel.getMaxComp().observe(this, companies -> {
            if (companies == null || companies.size() == 0) {

            } else {
                Companies comp = companies.get(0);
                String newValf;
                nf = new DecimalFormat("#,##0.00");
                newValf = nf.format(comp.getPct_bfr());
                newValf = newValf + "%";
                lblBestPct.setText(newValf);

                nf = new DecimalFormat("#,##0.00");
                newValf = nf.format(comp.getVal());
                lblMoneyBest.setText(newValf);

                lblCodBest.setText(comp.getCod());
            }
        });

        viewModel.getMinComp().observe(this, companies -> {
            if (companies == null || companies.size() == 0) {

            } else {
                Companies comp = companies.get(0);
                String newValf;
                nf = new DecimalFormat("#,##0.00");
                newValf = nf.format(comp.getPct_bfr());
                newValf = newValf + "%";
                lblLowePct.setText(newValf);

                nf = new DecimalFormat("#,##0.00");
                newValf = nf.format(comp.getVal());
                lblMoneyLow.setText(newValf);

                lblCodLow.setText(comp.getCod());
            }
        });

        viewModel.getSum().observe(this, aFloat -> {
            nf = new DecimalFormat("#,##0.00");
            String newValf = nf.format(aFloat);
            newValf = "R$ " + newValf;
            lblSum.setText(newValf);
        });

        btnUpdate.setOnClickListener(v -> {

            for (int i = 0; i < compAdapter.getItemCount(); i++) {
                Companies comp = compAdapter.getItem(i);
                comp.setPctBfr(comp.newPct());
                comp.setVal(comp.newVal());
                viewModel.update(comp);
            }
            compAdapter.notifyDataSetChanged();
        });

    }
}