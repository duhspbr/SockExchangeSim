package com.app.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.data.models.Companies;
import com.app.recyclerviewadapterexample.R;

import java.util.ArrayList;
import java.util.List;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.MViewHolder> {
    private List<Companies> companiesList = new ArrayList<>();
    private Context context;
    private Drawable icon;

    public CompAdapter(Context context) {
        this.context = context;
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        final private TextView textViewMoney,
        textViewCompCode;
        final private ImageView imgLogo;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMoney = itemView.findViewById(R.id.lblVal1);
            textViewCompCode = itemView.findViewById(R.id.lblCompName);
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
        Companies currentItem = companiesList.get(position);

        holder.textViewCompCode.setText(currentItem.getCod());
        holder.textViewMoney.setText(new String ("R$ " + currentItem.getVal()));
        int id = context.getResources().getIdentifier(currentItem.getLogoImg(), "drawable", context.getPackageName());
        icon = context.getResources().getDrawable(id);
        holder.imgLogo.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() { return companiesList.size();
    }

    public void setComps(List<Companies> companies) {
        this.companiesList = companies;
        notifyDataSetChanged();
    }
}
