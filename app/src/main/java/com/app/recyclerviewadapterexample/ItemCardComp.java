package com.app.recyclerviewadapterexample;

import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.annotation.JsonGetter;

public class ItemCardComp {

    private String compCodName;
    private String val_money;
    private String src;
    private String pts;

    public ItemCardComp (String _compCodName, String _val_money, String _src,
                         String _pts) {
        this.compCodName = _compCodName;
        this.pts = _pts;
        this.val_money = _val_money;
        this.src = _src;
    }

    @JsonGetter("compCodName")
    public String get_compCodName() {
        return compCodName;
    }

    @JsonGetter("pts")
    public String get_pts() {
        return pts;
    }

    @JsonGetter("val_money")
    public String get_val_money() {
        return val_money;
    }

    @JsonGetter("src")
    public String get_src() {
        return src;
    }

}
