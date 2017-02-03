package kidsuke.shoppingapp.presenter;

import android.os.Bundle;

import java.util.List;

import kidsuke.shoppingapp.model.Product;

/**
 * Created by ADMIN on 02-Feb-17.
 */

public interface MainPresenter {
    interface MainView {
        void updateList(List<Product> productList);
        void navigateActivity(Class activityClass, Bundle extras);
        void showAlert();
        void showSumUp(String s);
    }

    void checkCode(String productCode);
    List<Product> getDataFromPref();
    void putDataToPref(List<Product> productList);
    void resume();
    void sumUp();
}
