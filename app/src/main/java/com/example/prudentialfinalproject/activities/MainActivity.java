package com.example.prudentialfinalproject.activities;
//fix
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserLoginViewModel;
import com.example.prudentialfinalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setViewModel(new UserLoginViewModel(getApplication()));
        activityMainBinding.executePendingBindings();

    }




}
