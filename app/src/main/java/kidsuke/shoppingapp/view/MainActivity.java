package kidsuke.shoppingapp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import kidsuke.shoppingapp.R;
import kidsuke.shoppingapp.presenter.MainPresenter;
import kidsuke.shoppingapp.presenter.impl.MainPresenterImpl;
import kidsuke.shoppingapp.model.Product;
import kidsuke.shoppingapp.view.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainPresenter.MainView{
    public static final String TAG = MainActivity.class.getSimpleName();
    private TextInputEditText input;
    private TextInputLayout inputContainer;
    private MainAdapter adapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        inputContainer = (TextInputLayout) findViewById(R.id.input_container);
        input = (TextInputEditText) findViewById(R.id.input);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        AppCompatButton button = (AppCompatButton) findViewById(R.id.button);

        //Toolbar set up
        setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        //RecyclerView set up
        adapter = new MainAdapter(this, null);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Listeners set up
        button.setOnClickListener(this);

        //Init fields
        mainPresenter = new MainPresenterImpl(getSharedPreferences("storage", MODE_PRIVATE), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_button:
                mainPresenter.sumUp();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.resume();
    }

    @Override
    public void onClick(View v) {
        String productCode = input.getText().toString();
        //If input field is empty, show error. Otherwise, check whether product code is already in the list
        if (productCode.isEmpty()) {
            inputContainer.setError(getString(R.string.input_field_empty));
            inputContainer.setErrorEnabled(true);
        }else{
            inputContainer.setErrorEnabled(false);
            //If code not exist, pass data to FormActivity. Otherwise, show alert
            mainPresenter.checkCode(productCode);
        }
    }

    @Override
    public void updateList(List<Product> productList) {
        adapter.updateList(productList);
    }

    @Override
    public void navigateActivity(Class activityClass, Bundle extras) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(TAG, extras);
        startActivity(intent);
        finish();
    }

    @Override
    public void showAlert() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.code_exist))
                .setPositiveButton(getString(R.string.close_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true).show();
    }

    @Override
    public void showSumUp(String s) {
        new AlertDialog.Builder(this)
                .setMessage(s)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(true).show();
    }

    @Override
    public void onBackPressed() {
        Intent toHome = new Intent(Intent.ACTION_MAIN);
        toHome.addCategory(Intent.CATEGORY_HOME);
        startActivity(toHome);
    }
}
