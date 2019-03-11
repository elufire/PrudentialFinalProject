package com.example.prudentialfinalproject.activities;
//fix
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserLoginViewModel;
import com.example.prudentialfinalproject.UserMainViewModel;
import com.example.prudentialfinalproject.adapters.RecyclerViewAdapterTransactions;
import com.example.prudentialfinalproject.databinding.ActivitySignUpBinding;
import com.example.prudentialfinalproject.models.Transaction;
import com.example.prudentialfinalproject.models.User;
import com.example.prudentialfinalproject.utillities.Utilities;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding activitySignUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        activitySignUpBinding.setSignUpActivity(this);
//        UserLoginViewModel userLoginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel.class);
//        activitySignUpBinding.setSignUpViewModel(userLoginViewModel);
        activitySignUpBinding.executePendingBindings();
//        String[] accountTypes = new String[]{"Checking", "Savings", "Loan"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_spinner_dropdown_item,
//                accountTypes);
//        activitySignUpBinding.spAccountType.setAdapter(arrayAdapter);
        SignUpActivity.this.setTitle("");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

//        userLoginViewModel.getAccountType().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String returnedAccountType) {
//                Log.d("TAG", "AccountType Observer onChanged: " + returnedAccountType);
//
//
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //@BindingAdapter("android:onClick")
    public void afterClick(final View view){
        Log.d("TAG", "BUTTON HAS BEEN CLICKED!!!");
        Intent intent = new Intent(this, MainActivity.class);
        switch(view.getId()){
            case R.id.btnGoBackToLogIn:
                startActivity(intent);
                break;
            case R.id.btnCancel:
                startActivity(intent);
                break;
            case R.id.btnSignUp:
                Intent intent2 = new Intent(this, FinishSignUp.class);
                if(Utilities.isAccountNumberValid(activitySignUpBinding.etAccountNumber.getText().toString())
                && !activitySignUpBinding.etSocialSecurity.getText().toString().isEmpty()){
                    intent2.putExtra("account_number", activitySignUpBinding.etAccountNumber.getText().toString());
                    intent2.putExtra("account_type", (String)activitySignUpBinding.spAccountType.getSelectedItem());
                    startActivity(intent2);
                }
                else {
                    Toast.makeText(this, "Please insert a valid Account number and Social Security..."
                    , Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}
