package com.example.prudentialfinalproject.activities;
//fix
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserLoginViewModel;
import com.example.prudentialfinalproject.UserMainViewModel;
import com.example.prudentialfinalproject.adapters.TabAdapter;
import com.example.prudentialfinalproject.databinding.AccountsActivityBinding;
import com.example.prudentialfinalproject.fragments.AccountsFragment;
import com.example.prudentialfinalproject.fragments.DepositFragment;
import com.example.prudentialfinalproject.fragments.MoreFragment;
import com.example.prudentialfinalproject.fragments.TransferFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AccountsActivity extends AppCompatActivity {

    AccountsActivityBinding accountsActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.accounts_activity);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("beautiful_userName");
        accountsActivityBinding = DataBindingUtil.setContentView(this, R.layout.accounts_activity);
        UserMainViewModel userMainViewModel = ViewModelProviders.of(this).get(UserMainViewModel.class);
        accountsActivityBinding.setAccountInfoViewModel(userMainViewModel);
        userMainViewModel.initViewPager(this);
        userMainViewModel.init(userName);
        accountsActivityBinding.executePendingBindings();

        AccountsActivity.this.setTitle("Accounts");

    }

    @BindingAdapter({ "setUpWithViewpager" })
    public static void setUpWithViewpager(final TabLayout tabLayout, ViewPager viewPager)
    {
        Log.d("TAG", "SETTING UP VIEWPAGER!");
        viewPager.addOnAdapterChangeListener(new ViewPager.OnAdapterChangeListener()
        {
            @Override
            public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter)
            {
                if (oldAdapter == null && (newAdapter == null || newAdapter.getCount() == 0))
                {
                    // this function will helpful when
                    // we don't create viewpager immediately
                    // when view created (this mean we create
                    // will pager after a period time)
                    return;
                }
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
