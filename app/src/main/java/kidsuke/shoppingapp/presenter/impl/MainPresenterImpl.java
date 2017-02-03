package kidsuke.shoppingapp.presenter.impl;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import kidsuke.shoppingapp.presenter.MainPresenter;
import kidsuke.shoppingapp.model.Product;
import kidsuke.shoppingapp.utilities.ConverterUtil;
import kidsuke.shoppingapp.view.FormActivity;

/**
 * Created by ADMIN on 02-Feb-17.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainPresenter.MainView mainView;
    private List<Product> productList;
    private SharedPreferences sharedPreferences;

    public MainPresenterImpl(SharedPreferences sharedPreferences, MainView mainView){
        this.mainView = mainView;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void resume() {
        this.productList = getDataFromPref();
        System.out.println(productList.toString());
        this.mainView.updateList(productList);
    }

    @Override
    public List<Product> getDataFromPref() {
        String jsonData = sharedPreferences.getString("data", "");
        if (jsonData.isEmpty())
            return new ArrayList<>();
        else
            return ConverterUtil.convertFromJson(jsonData, Product.class);
    }

    @Override
    public void putDataToPref(List<Product> productList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", ConverterUtil.convertToJson(productList));
        editor.apply();
        editor.commit();
    }

    @Override
    public void checkCode(String productCode) {
        boolean check = false;
        for (Product product: productList){
            if (product.getProductCode().equals(productCode)) {
                check = true;
                break;
            }
        }
        if (!check) {
            Bundle bundle = new Bundle();
            bundle.putString("code", productCode);
            mainView.navigateActivity(FormActivity.class, bundle);
        }
        else
            mainView.showAlert();
    }

    @Override
    public void sumUp() {
        float totalPrice = 0;
        for (Product product: productList){
            System.out.println(Float.parseFloat(product.getRetailPrice()) * Integer.parseInt(product.getCount()) + " test");
            totalPrice += Float.parseFloat(product.getRetailPrice()) * Integer.parseInt(product.getCount());
        }
        putDataToPref(productList);
        DecimalFormat df = new DecimalFormat("#.#");
        mainView.showSumUp("The total value of the basket is " + df.format(totalPrice));
    }
}
