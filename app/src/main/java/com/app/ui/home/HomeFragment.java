package com.app.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
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
import com.app.ui.history.HistoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CompAdapter compAdapter;
    private CompaniesViewModel viewModel;
    private ImageView btnUpdate, btnOrderUp, btnOrderDown;
    private TextView lblBestPct, lblLowePct, lblMoneyBest, lblMoneyLow, lblCodBest,
            lblCodLow, lblSum;
    private NumberFormat nf;
    private AlertDialog alert;
    private float newVal;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel =
                new ViewModelProvider(this).get(CompaniesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        final TickerView lblSum = root.findViewById(R.id.lblSum);
        lblSum.setCharacterLists(TickerUtils.provideNumberList());
        lblSum.setAnimationInterpolator(new LinearInterpolator());

        btnOrderDown = root.findViewById(R.id.btnSortDown);
        btnOrderUp = root.findViewById(R.id.btnSortUp);
        lblBestPct = root.findViewById(R.id.pct_best);
        lblLowePct = root.findViewById(R.id.percent_low);
        lblCodBest = root.findViewById(R.id.id_best);
        lblCodLow = root.findViewById(R.id.id_low);
        lblMoneyBest = root.findViewById(R.id.text_money_best);
        lblMoneyLow = root.findViewById(R.id.text_money_low);
        btnUpdate = root.findViewById(R.id.btnUpdate);
        recyclerView = root.findViewById(R.id.rcv);
        layoutManager = new LinearLayoutManager(getActivity());
        compAdapter = new CompAdapter(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(compAdapter);

        viewModel.getAllComp().observe(getViewLifecycleOwner(), companies -> {
            compAdapter.setComps(companies);
        });

        compAdapter.setOnItemClickListener(position -> { });

        viewModel.getMaxComp().observe(getViewLifecycleOwner(), companies -> {
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

        viewModel.getMinComp().observe(getViewLifecycleOwner(), companies -> {
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

        viewModel.getSum().observe(getViewLifecycleOwner(), aFloat -> {
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

        btnOrderDown.setOnClickListener(v -> {
            viewModel.getAllCompDesc().observe(getViewLifecycleOwner(), companies ->
                    compAdapter.setComps(companies));
        });
        compAdapter.notifyDataSetChanged();

        btnOrderUp.setOnClickListener(v -> {
            viewModel.getAllCompAsc().observe(getViewLifecycleOwner(), companies ->
                    compAdapter.setComps(companies));
        });
        compAdapter.notifyDataSetChanged();

        compAdapter.setOnItemClickListener(new CompAdapter.OnItemClickListener() {
            @Override
            public void onItemCardClick(int position) {
                createDialog(position);
            }
        });

        return root;
    }

    public void createDialog(int position) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.buy_dialog_layout, null);
        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        TextView textQtde, textMoney;
        TextView textBuy, textCancel;
        SeekBar seekBar;

        // dialog constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setView(dialogView);
        seekBar = dialogView.findViewById(R.id.seekBuy);
        seekBar.setMax(1500);
        textBuy = dialogView.findViewById(R.id.textBuy);
        textCancel = dialogView.findViewById(R.id.textCancel);
        textMoney = dialogView.findViewById(R.id.textMoney);
        textQtde = dialogView.findViewById(R.id.textQtde);

        Float val = compAdapter.getItem(position).getVal();
        textMoney.setText("R$ " + new DecimalFormat("#,##0.00").format(val));

        builder.create();
        builder.show();

        textBuy.setOnClickListener(v -> {
            Cart cart = new Cart(compAdapter.getItem(position).getName(),
                    compAdapter.getItem(position).getCod(), newVal,
                    compAdapter.getItem(position).getLogoImg());
            historyViewModel.insert(cart);

            Toast.makeText(builder.getContext(), "Data Saved!", Toast.LENGTH_LONG).show();

        });

        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setOnDismissListener(DialogInterface::dismiss);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textQtde.setText(String.valueOf(progress) + " / 1500");

                textMoney.setText("R$ " + String.valueOf(new DecimalFormat("##,##00.00")
                        .format(progress * val)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setVal(Float.parseFloat(textMoney.getText().toString().replace("R$", "")
                        .replace(",", "")));
            }
        });

    }

    private void setVal(float progress) {
        newVal = progress;
    }

    private void closeDialog() {
        alert.dismiss();
    }

}