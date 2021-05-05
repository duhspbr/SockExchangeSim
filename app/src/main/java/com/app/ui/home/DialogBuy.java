package com.app.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.app.data.models.Cart;
import com.app.recyclerviewadapterexample.R;
import com.app.recyclerviewadapterexample.databinding.BuyDialogLayoutBinding;
import com.app.ui.history.HistoryViewModel;

import java.text.DecimalFormat;

public class DialogBuy extends DialogFragment {

    private int mPosition;
    private Float newVal;
    private CompAdapter mCompAdapter;
    private HistoryViewModel viewModel;
    private BuyDialogLayoutBinding binding = BuyDialogLayoutBinding.inflate(getLayoutInflater());
    private Context mContext;

    public DialogBuy(@NonNull Context context, CompAdapter compAdapter, int position) {
        //super(context);
        mContext = context;
        mCompAdapter = compAdapter;
        mPosition = position;
    }

    public DialogBuy() {}

    //public void onCreate(@Nullable Bundle savedInstanceState) {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Float val = mCompAdapter.getItem(mPosition).getVal();
        BuyDialogLayoutBinding binding = BuyDialogLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        //setContentView(view);

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

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

    private void setVal(float progress) {
        newVal = progress/10.0f;
    }
}
