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

    @Query("SELECT SUM (val) FROM comp_data")
    LiveData<Float> getSum();

    @Query("SELECT * FROM comp_data ORDER BY cod")
    LiveData<List<Companies>> getAllComp();

    @Query("SELECT * FROM comp_data ORDER BY pct_bfr ASC")
    LiveData<List<Companies>> getAllCompDesc();

    @Query("SELECT * FROM comp_data ORDER BY pct_bfr DESC")
    LiveData<List<Companies>> getAllCompUp();

    @Query("SELECT * FROM  comp_data WHERE pct_bfr = (SELECT MAX (pct_bfr) FROM comp_data)")
    LiveData<List<Companies>> getMaxValues();

    @Query("SELECT * FROM  comp_data WHERE pct_bfr = (SELECT MIN (pct_bfr) FROM comp_data)")
    LiveData<List<Companies>> getMinValues();
}
