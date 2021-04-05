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

        public PopulateDbAsyncTask(CompDatabase db) { companiesDao = db.companiesDao(); }

        @Override
        protected Void doInBackground(Void... voids) {
            companiesDao.insert(new Companies("Britsh Airways", "BAY", "c1", 245.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Acer", "NOG", "c2", 22.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Adidas", "CAF", "c3", 15.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Armani", "VDS", "c4", 2434.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("BBC", "FGH", "c5", 12.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("ATM", "SHN", "c6", 5.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Bank of America", "BNJ", "c7", 35.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Carl Berg", "SAS", "c8", 255.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Burguer King", "BBK", "c9", 21.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Ann", "GHJ", "c10", 12.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Allianz", "GAT", "c11", 663.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Budwiser", "NHY", "c12", 12.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Barclays", "BGB", "c13", 23.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Boeing", "BFG", "c14", 64.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("American Expr", "SDA", "c15", 233.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Baskin Robbins", "NON", "c16", 564.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("ABInbev", "DVD", "c17", 22.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Bp", "SWW", "c18", 11.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("A&P", "VBB", "c19", 21.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Adobe", "XZZ", "c20", 2.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("CityBank", "BVF", "c21", 55.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("AT&T", "BMF", "c22", 211.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Amazon", "CCC", "c23", 6.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("Beats Electronics", "EWQ", "c24", 3.3f, 0.00f, 0.00f));
            companiesDao.insert(new Companies("AC&M", "LCA", "c25", 25.3f, 0.00f, 0.00f));
            return null;
        }
    }
}
