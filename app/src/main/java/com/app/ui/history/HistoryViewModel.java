package com.app.ui.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.data.models.Cart;
import com.app.data.models.Companies;
import com.app.data.remote.datasource.CartDao;
import com.app.data.remote.datasource.CartDatabase;
import com.app.data.remote.repository.CartRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private CartRepository repository;
    private LiveData<List<Cart>> allCart;
    CartDao cartDao;
    CartDatabase cartDB;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        cartDB = CartDatabase.getInstance(application);
        repository = new CartRepository(application);
        cartDao = cartDB.cartDao();
        allCart = repository.getAllCart();
    }

    public void insert(Cart cart) { repository.insert(cart); }
    public void update(Cart cart) { repository.update(cart); }

    public LiveData<Float> getSum() { return repository.getSum(); }

    public LiveData<List<Cart>> getAllCart() { return allCart; }
    public LiveData<List<Cart>> getAllCartsDesc() { return repository.getAllCartsOrderDown(); }
    public LiveData<List<Cart>> getAllCartsAsc() { return repository.getAllCartsOrderUp(); }
}
