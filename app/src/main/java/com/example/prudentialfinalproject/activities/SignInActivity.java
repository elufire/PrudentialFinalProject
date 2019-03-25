package com.example.prudentialfinalproject.activities;
//fix
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserLoginViewModel;
import com.example.prudentialfinalproject.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding activitySignInBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        UserLoginViewModel userLoginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel.class);
        userLoginViewModel.init(getApplication());
        activitySignInBinding.setViewModel(userLoginViewModel);
        activitySignInBinding.executePendingBindings();
        final Intent intent = new Intent(this, AccountsActivity.class);
        userLoginViewModel.getRetrievedUserName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String userName) {
                intent.putExtra("beautiful_userName", userName);
                startActivity(intent);
                finish();
            }
        });

    }




}
