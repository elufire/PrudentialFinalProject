package com.example.prudentialfinalproject.activities;
//fix
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserLoginViewModel;
import com.example.prudentialfinalproject.databinding.ActivityFinishSignUpBinding;

public class FinishSignUp extends AppCompatActivity {
    ActivityFinishSignUpBinding activityFinishSignUpBinding;
    UserLoginViewModel userLoginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_sign_up);

        activityFinishSignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_finish_sign_up);
        userLoginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel.class);
        userLoginViewModel.init(getApplication());
        activityFinishSignUpBinding.setAccountSetupViewModel(userLoginViewModel);
        Intent intent = getIntent();
        userLoginViewModel.initAccount(intent.getStringExtra("account_type"), intent.getStringExtra("account_number"));

        activityFinishSignUpBinding.executePendingBindings();



        Log.d("TAG", "Account Type: " + intent.getStringExtra("account_type"));
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnAddAccount:
                userLoginViewModel.initAccount(activityFinishSignUpBinding.spAddAccountType.getSelectedItem().toString(),
                                activityFinishSignUpBinding.etAddAccount.getText().toString());
                activityFinishSignUpBinding.etAddAccount.setText("");
                Toast.makeText(this, "Account Number: " + activityFinishSignUpBinding.etAddAccount.getText().toString()
                        + " has been added.", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnFinishSignUp:
                userLoginViewModel.createUserAndAddToDatabase(view, activityFinishSignUpBinding.etSignUpEmail.getText().toString(),
                                        activityFinishSignUpBinding.etSignUpPassword.getText().toString());
                break;
        }
    }
}
