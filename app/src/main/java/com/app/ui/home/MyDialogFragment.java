package com.app.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.data.models.Cart;
import com.app.recyclerviewadapterexample.R;
import com.app.recyclerviewadapterexample.databinding.BuyDialogLayoutBinding;
import com.app.ui.history.HistoryViewModel;

import java.text.DecimalFormat;

public class MyDialogFragment extends DialogFragment {

    private int mPosition;
    private Float newVal;
    private CompAdapter mCompAdapter;
    private HistoryViewModel viewModel;
    private BuyDialogLayoutBinding binding = BuyDialogLayoutBinding.inflate(getLayoutInflater());
    private Context mContext;

    public MyDialogFragment(@NonNull Context context, CompAdapter compAdapter, int position) {
        //super(context);
        mContext = context;
        mCompAdapter = compAdapter;
        mPosition = position;
    }

    /**
     * Create a new instance of MyDialogFragment
     */
    static MyDialogFragment newInstance() {
//       // MyDialogFragment f = new MyDialogFragment();
//        Bundle args = new Bundle();
//        //f.setArguments(args);
//
//        return;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BuyDialogLayoutBinding binding = BuyDialogLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        binding.textBuy.setOnClickListener(v -> {
            Cart cart = new Cart(mCompAdapter.getItem(mPosition).getName(),
                    mCompAdapter.getItem(mPosition).getCod(), newVal/10.0f,
                    mCompAdapter.getItem(mPosition).getLogoImg(), mCompAdapter.getItem(mPosition).getPct_bfr());
            viewModel.insert(cart);
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_LONG).show();
            dismiss();
        });

        binding.textCancel.setOnClickListener(v -> dismiss());

        binding.seekBuy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textQtde.setText(String.valueOf(progress) + " / 1500");

                binding.textMoney.setText("R$ " + String.valueOf(new DecimalFormat("##,##00.00")
                        .format(progress * val)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setVal(Float.parseFloat(binding.textMoney.getText().toString()
                        .replace("R$", "").replace(",", "")
                        .replace(".", "")));
            }
        });

        return view;
    }
}