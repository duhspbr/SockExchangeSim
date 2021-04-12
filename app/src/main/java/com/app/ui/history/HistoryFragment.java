package com.app.ui.history;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.data.models.Cart;
import com.app.data.models.Companies;
import com.app.recyclerviewadapterexample.R;
import com.app.ui.home.CompAdapter;
import com.app.ui.home.CompaniesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoryAdapter historyAdapter;
    private CompAdapter compAdapter;
    private HistoryViewModel viewModel;
    private ImageView btnUpdate, btnOrderUp, btnOrderDown;
    private TextView lblBestPct, lblLowePct, lblMoneyBest, lblMoneyLow, lblCodBest,
            lblCodLow, lblSum, lblVal2;
    private NumberFormat nf;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        final TickerView lblSum = root.findViewById(R.id.lblSum2);
        lblSum.setCharacterLists(TickerUtils.provideNumberList());
        lblSum.setAnimationInterpolator(new LinearInterpolator());

        lblVal2 = root.findViewById(R.id.lblVal2);
        btnOrderDown = root.findViewById(R.id.btnSortDown);
        btnOrderUp = root.findViewById(R.id.btnSortUp);
        lblBestPct = root.findViewById(R.id.pct_best);
        lblLowePct = root.findViewById(R.id.percent_low);
        lblCodBest = root.findViewById(R.id.id_best);
        lblCodLow = root.findViewById(R.id.id_low);
        lblMoneyBest = root.findViewById(R.id.text_money_best);
        lblMoneyLow = root.findViewById(R.id.text_money_low);
        btnUpdate = root.findViewById(R.id.btnUpdate);
        recyclerView = root.findViewById(R.id.rcv_cart);
        layoutManager = new LinearLayoutManager(getActivity());
        historyAdapter = new HistoryAdapter(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);

        // viewModels
        viewModel.getAllCart().observe(getViewLifecycleOwner(), carts -> {
            historyAdapter.setCartList(carts);
                });

        viewModel.getSum().observe(getViewLifecycleOwner(), aFloat -> {
            nf = new DecimalFormat("#,##0.00");
            String newValf = nf.format(aFloat);
            newValf = "R$ " + newValf;
            lblSum.setText(newValf);
        });

        // btnFunctions
        btnOrderDown.setOnClickListener(v ->
                viewModel.getAllCartsDesc().observe(getViewLifecycleOwner(), carts ->
                historyAdapter.setCartList(carts)));
        historyAdapter.notifyDataSetChanged();

        btnOrderUp.setOnClickListener(v -> {
            viewModel.getAllCartsAsc().observe(getViewLifecycleOwner(), carts ->
                    historyAdapter.setCartList(carts));
        });
        historyAdapter.notifyDataSetChanged();


        return root;
    }
}