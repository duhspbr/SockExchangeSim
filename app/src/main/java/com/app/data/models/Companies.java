package com.app.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comp_data")
public class Companies {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String cod;
    private String logoImg;
    private float val;

    public Companies(String name, String cod, String logoImg, float val) {
        this.name = name;
        this.cod = cod;
        this.logoImg = logoImg;
        this.val = val;
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
}
