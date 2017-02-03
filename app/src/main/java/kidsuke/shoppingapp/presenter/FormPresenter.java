package kidsuke.shoppingapp.presenter;

import android.os.Bundle;

import java.util.List;

import kidsuke.shoppingapp.model.Product;

/**
 * Created by ADMIN on 03-Feb-17.
 */

public interface FormPresenter {
    interface FormView {
        void navigateActivity(Class activityClass);
    }

    void addData(String code, String name, String price);
    List<Product> getDataFromPref();
    void putDataToPref(List<Product> productList);
}
