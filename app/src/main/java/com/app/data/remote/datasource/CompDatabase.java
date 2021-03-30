package com.app.data.remote.datasource;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.data.models.Companies;

@Database(entities = {Companies.class }, version = 1)
public abstract class CompDatabase extends RoomDatabase {

    private static CompDatabase instance;
    public abstract CompaniesDao companiesDao();

    public static synchronized CompDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CompDatabase.class, "comp_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CompaniesDao companiesDao;

        private PopulateDbAsyncTask(CompDatabase db) { companiesDao = db.companiesDao(); }

        @Override
        protected Void doInBackground(Void... voids) {
            companiesDao.insert(new Companies("britshcomp", "BAY", "c1", 245.3f));
            companiesDao.insert(new Companies("acer", "NOG", "c2", 22.3f));
            companiesDao.insert(new Companies("adidas", "CAF", "c3", 15.3f));
            companiesDao.insert(new Companies("armani", "VDS", "c4", 2434.3f));
            companiesDao.insert(new Companies("bbc", "FGH", "c5", 12.3f));
            companiesDao.insert(new Companies("atm", "SHN", "c6", 5.3f));
            companiesDao.insert(new Companies("ameribank", "BNJ", "c7", 35.3f));
            companiesDao.insert(new Companies("carlberg", "SAS", "c8", 255.3f));
            companiesDao.insert(new Companies("bbk", "AAA", "c9", 21.3f));
            companiesDao.insert(new Companies("ann", "GHJ", "c10", 12.3f));
            companiesDao.insert(new Companies("allizan", "GAT", "c11", 663.3f));
            companiesDao.insert(new Companies("budwiser", "NHY", "c12", 12.3f));
            companiesDao.insert(new Companies("barclays", "BGB", "c13", 23.3f));
            companiesDao.insert(new Companies("boring", "BFG", "c14", 64.3f));
            companiesDao.insert(new Companies("ameiexp", "SDA", "c15", 233.3f));
            companiesDao.insert(new Companies("barkingrobins", "NON", "c16", 564.3f));
            companiesDao.insert(new Companies("bp", "DVD", "c17", 22.3f));
            companiesDao.insert(new Companies("aep", "SWW", "c18", 11.3f));
            companiesDao.insert(new Companies("aclu", "VBB", "c19", 21.3f));
            companiesDao.insert(new Companies("ardobe", "XZZ", "c20", 2.3f));
            companiesDao.insert(new Companies("cit", "BVF", "c21", 55.3f));
            companiesDao.insert(new Companies("amaz", "BMF", "c22", 211.3f));
            companiesDao.insert(new Companies("btes", "CCC", "c23", 6.3f));
            companiesDao.insert(new Companies("acm", "EWQ", "c24", 3.3f));
            companiesDao.insert(new Companies("britshcomp", "LCA", "c25", 25.3f));
            return null;
        }
    }
}
