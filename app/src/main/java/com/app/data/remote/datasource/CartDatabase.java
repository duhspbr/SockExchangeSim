package com.app.data.remote.datasource;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.data.models.Cart;

@Database(entities = { Cart.class }, version = 1)
public abstract class CartDatabase extends RoomDatabase {

    private static CartDatabase instance;
    public abstract CartDao cartDao();

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CartDatabase.class, "purshed_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private final CartDao cartDao;

        public PopulateDbAsyncTask(CartDatabase db) { cartDao = db.cartDao(); }


        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.insert(new Cart("Britsh Airways", "CCC", 1233.2f, "c4"));
            return null;
        }
    }
}
