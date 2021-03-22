package com.app.recyclerviewadapterexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.MViewHolder> {

    private List<ItemCardComp> itemCardComps;
    private Context context;
    private Drawable icon;

    public CompAdapter(ArrayList<ItemCardComp> _itemCardComps, Context _context) {
        itemCardComps = _itemCardComps;
        context = _context;
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {

        final private TextView textViewMoney, textViewPoints,
        textViewCompCode;
        final private ImageView imgLogo;

        public MViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewMoney = itemView.findViewById(R.id.lblVal1);
            textViewCompCode = itemView.findViewById(R.id.lblCompName);
            textViewPoints = itemView.findViewById(R.id.lblVal2);
            imgLogo = itemView.findViewById(R.id.img_comp);
        }
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_companies, parent, false);
        MViewHolder mViewHolder = new MViewHolder(v);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        ItemCardComp currentItem = itemCardComps.get(position);
        holder.textViewPoints.setText(currentItem.get_pts());
        holder.textViewCompCode.setText(currentItem.get_compCodName());
        holder.textViewMoney.setText(currentItem.get_val_money());
        int id = context.getResources().getIdentifier(currentItem.get_src(), "drawable", context.getPackageName());
        icon = context.getResources().getDrawable(id);
        holder.imgLogo.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() { return itemCardComps.size();
    }
}
