package com.app.data.remote.repository;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.app.data.models.Companies;
import com.app.data.remote.datasource.CompDatabase;
import com.app.data.remote.datasource.CompaniesDao;

import java.util.List;

public class CompaniesRepository {
    private final CompaniesDao companiesDao;
    private final LiveData<List<Companies>> allComp;

    public CompaniesRepository(Application application) {
        CompDatabase compDatabase = CompDatabase.getInstance(application);
        companiesDao = compDatabase.companiesDao();
        allComp = companiesDao.getAllComp();
    }

    public void insert(Companies companies) { new InsertCompAsyncTask(companiesDao).execute(); }

    public void update(Companies companies) { new UpdateCompAsyncTask(companiesDao).execute(); }

    public LiveData<List<Companies>> getAllComp() { return allComp; }

    private static class InsertCompAsyncTask extends AsyncTask<Companies, Void, Void> {
        private final CompaniesDao companiesDao;

        private InsertCompAsyncTask(CompaniesDao companiesDao) { this.companiesDao = companiesDao; }

        @Override
        protected Void doInBackground(Companies... companies) {
            companiesDao.insert(companies[0]);
            return null;
        }
    }

    private static class UpdateCompAsyncTask extends AsyncTask<Companies, Void, Void> {
        private final CompaniesDao companiesDao;

        private UpdateCompAsyncTask(CompaniesDao companiesDao) { this.companiesDao = companiesDao; }

        @Override
        protected Void doInBackground(Companies... companies) {
            companiesDao.update(companies[0]);
            return null;
        }
    }
}
