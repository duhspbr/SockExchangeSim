package com.app.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_data")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String compName;
    private String compCode;
    private float val, pct;
    private String compImg;

    public Cart(String compName, String compCode, float val, String compImg, float pct) {
        this.compName = compName;
        this.compCode = compCode;
        this.compImg = compImg;
        this.val = val;
        this.pct = pct;
    }

    public String getCompName() {
        return compName;
    }

    public String getCompCode() {
        return compCode;
    }

    public float getPct() { return pct; }

    public float getVal() {
        return val;
    }

    public String getCompImg() {
        return compImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
