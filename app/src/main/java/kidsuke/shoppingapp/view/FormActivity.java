package kidsuke.shoppingapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import kidsuke.shoppingapp.R;
import kidsuke.shoppingapp.presenter.FormPresenter;
import kidsuke.shoppingapp.presenter.impl.FormPresenterImpl;

/**
 * Created by ADMIN on 02-Feb-17.
 */

public class FormActivity extends AppCompatActivity implements View.OnClickListener, FormPresenter.FormView{
    public static final String TAG = FormActivity.class.getSimpleName();
    private TextInputEditText nameInput;
    private TextInputEditText priceInput;
    private TextInputLayout nameInputContainer;
    private TextInputLayout priceInputContainer;
    private String productCode;
    private FormPresenter formPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Init view
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        nameInput = (TextInputEditText) findViewById(R.id.name);
        priceInput = (TextInputEditText) findViewById(R.id.price);
        nameInputContainer = (TextInputLayout) findViewById(R.id.name_container);
        priceInputContainer = (TextInputLayout) findViewById(R.id.price_container);
        AppCompatButton saveButton = (AppCompatButton) findViewById(R.id.save_button);

        //Toolbar set up
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white_48dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Listeners set up
        saveButton.setOnClickListener(this);

        //Init fields
        formPresenter = new FormPresenterImpl(getSharedPreferences("storage", MODE_PRIVATE), this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                navigateActivity(MainActivity.class);
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null && extras.containsKey(MainActivity.TAG)){
            Bundle bundle = extras.getBundle(MainActivity.TAG);

            if (bundle != null){
                if (!bundle.getString("code", "").isEmpty())
                    productCode = bundle.getString("code");
                else
                    Log.e(TAG, "No product code receive from MainActivity");
            }
        }else{
            Log.i(TAG, "No data receive from MainActivity");
        }
    }

    @Override
    public void onClick(View v) {
        String name = nameInput.getText().toString();
        String price = priceInput.getText().toString();

        //If input field is empty, show error. Otherwise, pass data to MainActivity
        if (name.isEmpty() || price.isEmpty()){
            if (name.isEmpty()){
                nameInputContainer.setError(getString(R.string.input_field_empty));
                nameInputContainer.setErrorEnabled(true);
            }

            if (price.isEmpty()){
                priceInputContainer.setError(getString(R.string.input_field_empty));
                priceInputContainer.setErrorEnabled(true);
            }
        }else{
            nameInputContainer.setErrorEnabled(false);
            priceInputContainer.setErrorEnabled(false);

            formPresenter.addData(productCode, name, price);
        }
    }

    @Override
    public void navigateActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        navigateActivity(MainActivity.class);
    }
}
