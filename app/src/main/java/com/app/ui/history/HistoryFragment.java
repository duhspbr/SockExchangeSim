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
import com.app.recyclerviewadapterexample.databinding.FragmentHistoryBinding;
import com.app.ui.home.CompAdapter;
import com.app.ui.home.CompaniesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class HistoryFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private HistoryAdapter historyAdapter;
    private CompAdapter compAdapter;
    private HistoryViewModel viewModel;
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
        FragmentHistoryBinding binding = FragmentHistoryBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        binding.lblSum2.setCharacterLists(TickerUtils.provideNumberList());
        binding.lblSum2.setAnimationInterpolator(new LinearInterpolator());

        layoutManager = new LinearLayoutManager(getActivity());
        historyAdapter = new HistoryAdapter(getActivity());

        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setAdapter(historyAdapter);

        // viewModels
        viewModel.getAllCart().observe(getViewLifecycleOwner(), carts -> historyAdapter.setCartList(carts));

        viewModel.getSum().observe(getViewLifecycleOwner(), aFloat -> {
            nf = new DecimalFormat("#,##0.00");
            String newValf = nf.format(aFloat);
            newValf = "R$ " + newValf;
            binding.lblSum2.setText(newValf);
        });

        // btnFunctions
        binding.btnSortDown.setOnClickListener(v ->
                viewModel.getAllCartsDesc().observe(getViewLifecycleOwner(), carts ->
                historyAdapter.setCartList(carts)));
        historyAdapter.notifyDataSetChanged();

        binding.btnSortUp.setOnClickListener(v -> {
            viewModel.getAllCartsAsc().observe(getViewLifecycleOwner(), carts ->
                    historyAdapter.setCartList(carts));
        });
        historyAdapter.notifyDataSetChanged();

        return view;
    }
}