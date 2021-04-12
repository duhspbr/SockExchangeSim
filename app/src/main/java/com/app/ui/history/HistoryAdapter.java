package com.app.ui.history;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.data.models.Cart;
import com.app.recyclerviewadapterexample.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MViewHolder> {

    private Cart currentItem;
    public Context context;
    private Drawable icon;
    private List<Cart> cartList = new ArrayList<>();

    public HistoryAdapter(Context context) {
        super();
        this.context = context;
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        final private TextView textMoney, textCodComp, textPercent,
        textCompName;
        final private ImageView imgLogo;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);

            textCompName = itemView.findViewById(R.id.lblTituloComp);
            textPercent = itemView.findViewById(R.id.lblPercent);
            textCodComp = itemView.findViewById(R.id.lblCompCod);
            textMoney = itemView.findViewById(R.id.lblVal1);

            imgLogo = itemView.findViewById(R.id.img_comp);
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cart, parent, false);
        MViewHolder mViewHolder = new MViewHolder(v);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MViewHolder holder, int position) {
        currentItem = cartList.get(position);

        holder.textCodComp.setText(currentItem.getCompCode());
        holder.textCompName.setText(currentItem.getCompName());
        holder.textMoney.setText(new String("R$ " + new DecimalFormat("##,##00.00")
                .format(currentItem.getVal())));
        holder.textPercent.setText(new DecimalFormat("##,##00.00").format(currentItem.getPct()) + "%");
        int id = context.getResources().getIdentifier(currentItem.getCompImg(), "drawable", context.getPackageName());
        icon = context.getResources().getDrawable(id);
        holder.imgLogo.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() { return cartList.size(); }

    public void setCartList(List<Cart> cart) {
        this.cartList = cart;
        notifyDataSetChanged();
    }

    public Cart getItem(int pos) { return cartList.get(pos); }
}
