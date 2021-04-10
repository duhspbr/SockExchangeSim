package com.app.data.remote.datasource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.data.models.Cart;
import com.app.data.models.Companies;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart cart);

    @Update
    void update(Cart cart);

    @Query("SELECT * FROM cart_data ORDER BY compCode")
    LiveData<List<Cart>> getAllCart();

    @Query("SELECT * FROM cart_data ORDER BY val ASC")
    LiveData<List<Cart>> getAllCartsDesc();

    @Query("SELECT * FROM cart_data ORDER BY val DESC")
    LiveData<List<Cart>> getAllCartsUp();
}
