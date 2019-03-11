package com.example.prudentialfinalproject.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.UserMainViewModel;
import com.example.prudentialfinalproject.activities.AccountDisplayActivity;
import com.example.prudentialfinalproject.adapters.RecyclerViewAdapter;
import com.example.prudentialfinalproject.databinding.AccountsFragmentBinding;
import com.example.prudentialfinalproject.models.Loan;
import com.example.prudentialfinalproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {
    AccountsFragmentBinding accountsFragmentBinding;
    String key;
    ArrayList<Loan> loansList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Bundle bundle = getArguments();
//        if(bundle != null){
//            key = bundle.getString("beautiful_userName");
//            Log.d("TAG", "AccountsFragment Received userName!");
//        }else{
//            Log.d("TAG", "AccountsFragment Bundle is NULL!");
//        }

        loansList = new ArrayList<>();
        accountsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.accounts_fragment, container, false);
        View view = accountsFragmentBinding.getRoot();
        UserMainViewModel userMainViewModel = ViewModelProviders.of(requireActivity()).get(UserMainViewModel.class);
        accountsFragmentBinding.setAccountsFragment(this);
        accountsFragmentBinding.executePendingBindings();
        accountsFragmentBinding.rvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        userMainViewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Log.d("TAG", "User Observer onChanged: " + user);
                accountsFragmentBinding.setUser(user);
            }
        });

        userMainViewModel.getLoanLiveData().observe(this, new Observer<ArrayList<Loan>>(){

            @Override
            public void onChanged(@Nullable ArrayList<Loan> loans) {
                Log.d("TAG", "Loan Observer onChanged: " + loans.size());
                loansList = loans;

                Log.d("TAG", "Loan List Length: " + loansList.size());
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(loansList);
                accountsFragmentBinding.rvRecyclerView.setAdapter(recyclerViewAdapter);
            }
        });


        return view;
    }

    public void onClick(View view, User user){
        Intent intent = new Intent(getActivity(), AccountDisplayActivity.class);
        intent.putExtra("transfer_user", user);
        switch (view.getId()){
            case R.id.checkingAccountHolder:
                intent.putExtra("account_type", "Checking");
                startActivity(intent);
                break;

            case R.id.savingsAccountHolder:
                intent.putExtra("account_type", "Savings");
                Log.d("TAG", "HANDLECLICK: SAVINGS");
                startActivity(intent);
                break;
        }


    }

}
