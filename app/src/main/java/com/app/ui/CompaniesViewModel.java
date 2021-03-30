package com.app.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.data.models.Companies;
import com.app.data.remote.repository.CompaniesRepository;

import java.util.List;

public class CompaniesViewModel extends AndroidViewModel {
    private CompaniesRepository repository;
    private LiveData<List<Companies>> allComp;

    public CompaniesViewModel(@NonNull Application application) {
        super(application);
        repository = new CompaniesRepository(application);
        allComp = repository.getAllComp();
    }

    public void insert(Companies companies) { repository.insert(companies); }

    public void update(Companies companies) { repository.update(companies); }

    public LiveData<List<Companies>> getAllComp() { return allComp; }
}
