package kidsuke.shoppingapp.presenter.impl;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import kidsuke.shoppingapp.model.Product;
import kidsuke.shoppingapp.presenter.FormPresenter;
import kidsuke.shoppingapp.utilities.ConverterUtil;
import kidsuke.shoppingapp.view.MainActivity;

/**
 * Created by ADMIN on 03-Feb-17.
 */

public class FormPresenterImpl implements FormPresenter {
    private FormPresenter.FormView formView;
    private SharedPreferences sharedPreferences;

    public FormPresenterImpl(SharedPreferences sharedPreferences, FormView formView){
        this.formView = formView;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void addData(String code, String name, String price) {
        List<Product> productList = getDataFromPref();
        productList.add(new Product(code, name, price));
        putDataToPref(productList);
        formView.navigateActivity(MainActivity.class);
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
}
