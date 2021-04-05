package com.app.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity(tableName = "comp_data")
public class Companies {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String cod;
    private String logoImg;
    private float val;
    private float pct_aft;
    private float pct_bfr;

    public Companies(String name, String cod, String logoImg, float val, float pct_aft, float pct_bfr) {
        this.name = name;
        this.cod = cod;
        this.logoImg = logoImg;
        this.val = val;
        this.pct_aft = pct_aft;
        this.pct_bfr = pct_bfr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCod() {
        return cod;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public float getVal() {
        return val;
    }

    public float getPct_aft() {
        return pct_aft;
    }

    public float getPct_bfr() {
        return pct_bfr;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public int genRandNum() {
        Random rand = new Random();
        int max = 5;
        boolean positive = true;

        double val = Math.random();
        if (val > 1.0/2.0) {
            positive = true;
        } else {
            positive = false;
        }

        if (positive) {
            return rand.nextInt(max);
        } else {
            return rand.nextInt(max) * (- 1);
        }
    }

    public void setPctBfr(float newPct) {
        this.pct_bfr = newPct;
    }

    public float newPct() {
        return (float) (genRandNum() * Math.random());
    }

    public float newVal() {
        return (float)  getVal() + (getVal() * newPct()/100.0f);
    }
}
