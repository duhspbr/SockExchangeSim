package com.app.data.remote.datasource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.data.models.Companies;

import java.util.List;

@Dao
public interface CompaniesDao {
    @Insert
    void insert(Companies companies);

    @Update
    void update(Companies companies);

    @Query("SELECT * FROM comp_data;")
    LiveData<List<Companies>> getAllComp();
}
