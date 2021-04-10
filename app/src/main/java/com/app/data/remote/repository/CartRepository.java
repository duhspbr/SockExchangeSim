package com.app.data.remote.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.app.data.models.Cart;
import com.app.data.models.Companies;
import com.app.data.remote.datasource.CartDao;
import com.app.data.remote.datasource.CartDatabase;

import java.util.List;

public class CartRepository {
    private final CartDao cartDao;
    private final LiveData<List<Cart>> allCart;
    CartDatabase cartDB;

    public CartRepository(Application application) {
        cartDB = CartDatabase.getInstance(application);
        cartDao = cartDB.cartDao();
        allCart = cartDao.getAllCart();
    }

    public void insert(Cart cart) { new InsertCartAsyncTask(cartDao).execute(cart); }
    public void update(Cart cart) { new UpdateCartAsyncTask(cartDao).execute(cart); }

    public LiveData<List<Cart>> getAllCart() { return allCart; }

    public LiveData<List<Cart>> getAllCartsOrderDown() { return cartDao.getAllCartsDesc(); }
    public LiveData<List<Cart>> getAllCartsOrderUp() { return cartDao.getAllCartsUp(); }

    private static class InsertCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private final CartDao cartDao;

        private InsertCartAsyncTask(CartDao cartDao) { this.cartDao = cartDao; }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.insert(carts[0]);
            return null;
        }
    }

    private static class UpdateCartAsyncTask extends AsyncTask<Cart, Void, Void> {
        private final CartDao cartDao;

        private UpdateCartAsyncTask(CartDao cartDao) { this.cartDao = cartDao; }

        @Override
        protected Void doInBackground(Cart... carts) {
            cartDao.update(carts[0]);
            return null;
        }
    }
}
