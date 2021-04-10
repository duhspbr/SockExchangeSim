package com.app.data.remote.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.app.data.models.Companies;
import com.app.data.remote.datasource.CompDatabase;
import com.app.data.remote.datasource.CompaniesDao;

import java.util.List;

public class CompaniesRepository {
    private final CompaniesDao companiesDao;
    private final LiveData<List<Companies>> allComp;
    //private final LiveData<Integer> count;
    private final LiveData<List<Companies>> maxComp;
    //private final LiveData<Float> minComp;
    CompDatabase comDB;

    public CompaniesRepository(Application application) {
        comDB = CompDatabase.getInstance(application);
        companiesDao = comDB.companiesDao();
        allComp = companiesDao.getAllComp();
        maxComp = companiesDao.getMaxValues();
    }

    public void insert(Companies companies) { new InsertCompAsyncTask(companiesDao).execute(companies); }
    public void update(Companies companies) { new UpdateAsyncTask(companiesDao).execute(companies); }

    public LiveData<List<Companies>> getAllComp() { return allComp; }
    public LiveData<List<Companies>> getAllCompOrderDown() { return companiesDao.getAllCompDesc(); }
    public LiveData<List<Companies>> getAllCompOrderUp() { return companiesDao.getAllCompUp(); }

    public LiveData<List<Companies>> getMaxCompValue() { return companiesDao.getMaxValues(); }
    public LiveData<List<Companies>> getMinCompValue() { return companiesDao.getMinValues(); }
    public LiveData<Float> getSum() { return companiesDao.getSum(); }

    //public String getImgText(String compCode) { return companiesDao.getImgText(compCode); }


    private static class InsertCompAsyncTask extends AsyncTask<Companies, Void, Void> {
        private final CompaniesDao companiesDao;

        private InsertCompAsyncTask(CompaniesDao companiesDao) { this.companiesDao = companiesDao; }

        @Override
        protected Void doInBackground(Companies... companies) {
            companiesDao.insert(companies[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Companies, Void, Void> {

        CompaniesDao companiesDao;

        UpdateAsyncTask(CompaniesDao companiesDao) { this.companiesDao = companiesDao; }

        @Override
        protected Void doInBackground(Companies... companies) {
            companiesDao.update(companies[0]);
            return null;
        }
    }
}
