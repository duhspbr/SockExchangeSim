package com.app.ui;

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

    public String get_compCodName() {
        return compCodName;
    }

    public String get_pts() {
        return pts;
    }

    public String get_val_money() {
        return val_money;
    }

    public String get_src() {
        return src;
    }

    public void setVal_money(String val_money) {
        this.val_money = val_money;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

}
