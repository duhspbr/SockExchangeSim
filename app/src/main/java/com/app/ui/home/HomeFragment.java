package com.app.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.app.recyclerviewadapterexample.databinding.FragmentHomeBinding;
import com.app.ui.history.HistoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    private CompAdapter compAdapter;
    private CompaniesViewModel viewModel;
    private NumberFormat nf;
    private AlertDialog alert;
    private float newVal;
    private int mStackLevel = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    private FragmentHomeBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel =
                new ViewModelProvider(this).get(CompaniesViewModel.class);

        //View root = inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.lblSum.setCharacterLists(TickerUtils.provideNumberList());
        binding.lblSum.setAnimationInterpolator(new LinearInterpolator());

        layoutManager = new LinearLayoutManager(getActivity());
        compAdapter = new CompAdapter(getActivity());

        binding.rcv.setLayoutManager(layoutManager);
        binding.rcv.setAdapter(compAdapter);

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
                binding.pctBest.setText(newValf);

                nf = new DecimalFormat("#,##0.00");
                newValf = nf.format(comp.getVal());
                binding.textMoneyBest.setText(newValf);

                binding.idBest.setText(comp.getCod());
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
                binding.percentLow.setText(newValf);

                nf = new DecimalFormat("#,##0.00");
                newValf = nf.format(comp.getVal());
                binding.textMoneyLow.setText(newValf);

                binding.idLow.setText(comp.getCod());
            }
        });

        viewModel.getSum().observe(getViewLifecycleOwner(), aFloat -> {
            nf = new DecimalFormat("#,##0.00");
            String newValf = nf.format(aFloat);
            newValf = "R$ " + newValf;
            binding.lblSum.setText(newValf);
        });

        binding.btnUpdate.setOnClickListener(v -> {

            for (int i = 0; i < compAdapter.getItemCount(); i++) {
                Companies comp = compAdapter.getItem(i);
                comp.setPctBfr(comp.newPct());
                comp.setVal(comp.newVal());
                viewModel.update(comp);
            }
            compAdapter.notifyDataSetChanged();
        });

        binding.btnSortDown.setOnClickListener(v -> {
            viewModel.getAllCompDesc().observe(getViewLifecycleOwner(), companies ->
                    compAdapter.setComps(companies));
        });
        compAdapter.notifyDataSetChanged();

        binding.btnSortUp.setOnClickListener(v -> {
            viewModel.getAllCompAsc().observe(getViewLifecycleOwner(), companies ->
                    compAdapter.setComps(companies));
        });
        compAdapter.notifyDataSetChanged();

        compAdapter.setOnItemClickListener(new CompAdapter.OnItemClickListener() {
            @Override
            public void onItemCardClick(int position) {
                showDialog();
            }
        });

        return view;
    }

    void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = MyDialogFragment.newInstance();
        newFragment.show(ft, "dialog");
    }

//    public void createDialog(int position) {
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View dialogView = inflater.inflate(R.layout.buy_dialog_layout, null);
//        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
//
//        TextView textQtde, textMoney;
//        TextView textBuy, textCancel;
//        SeekBar seekBar;
//
//        // dialog constructor
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        builder.setView(dialogView);
//        seekBar = dialogView.findViewById(R.id.seekBuy);
//        seekBar.setMax(1500);
//        textBuy = dialogView.findViewById(R.id.textBuy);
//        textCancel = dialogView.findViewById(R.id.textCancel);
//        textMoney = dialogView.findViewById(R.id.textMoney);
//        textQtde = dialogView.findViewById(R.id.textQtde);
//
//        Float val = compAdapter.getItem(position).getVal();
//        textMoney.setText("R$ " + new DecimalFormat("#,##0.00").format(val));
//
//        builder.create();
//        builder.show();
//
//        textBuy.setOnClickListener(v -> {
//            Cart cart = new Cart(compAdapter.getItem(position).getName(),
//                    compAdapter.getItem(position).getCod(), newVal/10.0f,
//                    compAdapter.getItem(position).getLogoImg(), compAdapter.getItem(position).getPct_bfr());
//            historyViewModel.insert(cart);
//
//            Toast.makeText(builder.getContext(), "Data Saved!", Toast.LENGTH_LONG).show();
//
//        });
//
//        textCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.setOnDismissListener(DialogInterface::dismiss);
//            }
//        });
//
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                textQtde.setText(String.valueOf(progress) + " / 1500");
//
//                textMoney.setText("R$ " + String.valueOf(new DecimalFormat("##,##00.00")
//                        .format(progress * val)));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                setVal(Float.parseFloat(textMoney.getText().toString().replace("R$", "")
//                        .replace(",", "").replace(".", "")));
//            }
//        });
//
//    }
//
//    private void setVal(float progress) {
//        newVal = progress/10.0f;
//    }

}