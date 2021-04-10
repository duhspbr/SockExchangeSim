package com.app.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.data.models.Companies;
import com.app.data.remote.datasource.CompDatabase;
import com.app.data.remote.datasource.CompaniesDao;
import com.app.data.remote.repository.CompaniesRepository;

import java.util.List;

public class CompaniesViewModel extends AndroidViewModel {
    private CompaniesRepository repository;
    private LiveData<List<Companies>> allComp;
    CompaniesDao companiesDao;
    private LiveData<Float> getSum;
    CompDatabase compDB;

    public CompaniesViewModel(@NonNull Application application) {
        super(application);
        compDB = CompDatabase.getInstance(application);
        repository = new CompaniesRepository(application);
        companiesDao = compDB.companiesDao();
        allComp = repository.getAllComp();

    }

    //updates
    public void insert(Companies companies) { repository.insert(companies); }
    public void update(Companies companies) { repository.update(companies); }

    //selects
    public LiveData<List<Companies>> getAllComp() { return allComp; }
    public LiveData<List<Companies>> getAllCompDesc() { return repository.getAllCompOrderDown(); }
    public LiveData<List<Companies>> getAllCompAsc() { return repository.getAllCompOrderUp(); }
    //public String getImgText(String compCode) { return repository.getImgText(compCode); }

    public LiveData<List<Companies>> getMaxComp() { return repository.getMaxCompValue(); }
    public LiveData<List<Companies>> getMinComp() { return repository.getMinCompValue(); }
    public LiveData<Float> getSum() { return repository.getSum(); }
}
