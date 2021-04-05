package com.app.ui;

import android.content.Context;
import android.graphics.Color;
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

import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.MViewHolder> {
    private List<Companies> companiesList = new ArrayList<>();
    private Context context;
    private Drawable icon;
    private Companies currentItem;

    public CompAdapter(Context context) {
        this.context = context;
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {
        final private TextView textViewMoney,
        textViewCompCode, lblPercent, lblTituloComp;
        final private ImageView imgLogo;

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMoney = itemView.findViewById(R.id.lblVal1);
            textViewCompCode = itemView.findViewById(R.id.lblCompName);
            imgLogo = itemView.findViewById(R.id.img_comp);
            lblPercent = itemView.findViewById(R.id.lblPercent);
            lblTituloComp = itemView.findViewById(R.id.lblTituloComp);
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
        currentItem = companiesList.get(position);

        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        NumberFormat nf2 = new DecimalFormat("#,##0.00");
        String newVal = nf.format(currentItem.getVal());
        String begin = "+";
        int color;

        if (currentItem.getPct_bfr() < 0.0) {
            color = Color.parseColor("#B71C1C");
            holder.lblPercent.setTextColor(color);
            currentItem.setPctBfr(currentItem.getPct_bfr() * (-1));
            begin = "-";
        }
        else {
            color = Color.parseColor("#4CAF50");
            holder.lblPercent.setTextColor(color);
            begin = "+";
        }

        String newPct = nf2.format(currentItem.getPct_bfr());

        holder.textViewCompCode.setText(currentItem.getCod());
        holder.textViewMoney.setText(new String ("R$ " + newVal));
        holder.lblPercent.setText(new String (begin + newPct + "%"));
        holder.lblTituloComp.setText(currentItem.getName());
        int id = context.getResources().getIdentifier(currentItem.getLogoImg(), "drawable", context.getPackageName());
        icon = context.getResources().getDrawable(id);
        holder.imgLogo.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() { return companiesList.size(); }

    public void setComps(List<Companies> companies) {
        this.companiesList = companies;
        notifyDataSetChanged();
    }

    public Companies getItem(int pos) {
        return companiesList.get(pos);
    }
}
