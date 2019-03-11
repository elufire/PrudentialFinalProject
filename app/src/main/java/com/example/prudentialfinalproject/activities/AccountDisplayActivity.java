package com.example.prudentialfinalproject.activities;
//fix
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserMainViewModel;
import com.example.prudentialfinalproject.adapters.RecyclerViewAdapter;
import com.example.prudentialfinalproject.adapters.RecyclerViewAdapterTransactions;
import com.example.prudentialfinalproject.databinding.ActivityAccountDisplayBinding;
import com.example.prudentialfinalproject.models.Transaction;
import com.example.prudentialfinalproject.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AccountDisplayActivity extends AppCompatActivity {
    ActivityAccountDisplayBinding activityAccountDisplayBinding;
    String accountType;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        setContentView(R.layout.activity_account_display);
        Intent intent = getIntent();
        accountType = intent.getStringExtra("account_type");
        user = intent.getParcelableExtra("transfer_user");

        activityAccountDisplayBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_display);
        UserMainViewModel userMainViewModel = ViewModelProviders.of(this).get(UserMainViewModel.class);
        activityAccountDisplayBinding.setAccountDisplayViewModel(userMainViewModel);
        userMainViewModel.init(user.getUserName());
        userMainViewModel.initAccountType(accountType);
        activityAccountDisplayBinding.executePendingBindings();
        activityAccountDisplayBinding.rvAccountDisplay.setLayoutManager(new LinearLayoutManager(this));


        Log.d("TAG", "Account Type: " + accountType);
        AccountDisplayActivity.this.setTitle(accountType);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        userMainViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User returnedUser) {
                Log.d("TAG", "User Observer onChanged: " + returnedUser);
                activityAccountDisplayBinding.setDisplayAccountUser(returnedUser);
                ArrayList<Transaction> transactions = new ArrayList<>(returnedUser.getTransactions());
                RecyclerViewAdapterTransactions recyclerViewAdapterTransactions = new RecyclerViewAdapterTransactions(transactions);
                activityAccountDisplayBinding.rvAccountDisplay.setAdapter(recyclerViewAdapterTransactions);

                if(accountType.equals("Checking")){
                    activityAccountDisplayBinding.tvAccountDisplayBalance.setText(returnedUser.getCheckingAccount());
                    activityAccountDisplayBinding.tvAccountDisplayAvailableBalance.setText(returnedUser.getCheckingAccountAvailable());
                }
                else{
                    activityAccountDisplayBinding.tvAccountDisplayBalance.setText(returnedUser.getSavingsAccount());
                    activityAccountDisplayBinding.tvAccountDisplayAvailableBalance.setText(returnedUser.getSavingsAccountAvailable());
                }

            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_details_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.logOutDisplay:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
