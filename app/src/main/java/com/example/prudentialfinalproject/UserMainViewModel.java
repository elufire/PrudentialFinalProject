package com.example.prudentialfinalproject;
//fix
import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.prudentialfinalproject.activities.AccountDisplayActivity;
import com.example.prudentialfinalproject.adapters.TabAdapter;
import com.example.prudentialfinalproject.fragments.AccountsFragment;
import com.example.prudentialfinalproject.fragments.DepositFragment;
import com.example.prudentialfinalproject.fragments.MoreFragment;
import com.example.prudentialfinalproject.fragments.TransferFragment;
import com.example.prudentialfinalproject.models.Loan;
import com.example.prudentialfinalproject.models.Transaction;
import com.example.prudentialfinalproject.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserMainViewModel extends ViewModel implements Observable {
    FirebaseDatabase database;
    DatabaseReference myRef;
    User user;
    String userEmail;
    String accountType;
    ArrayList<Loan> loanList;
    ArrayList<Transaction> transactionList;
    MutableLiveData<ArrayList<Loan>> loanLiveData;
    MutableLiveData<User> userLiveData;
    //ViewPager viewPager;
    TabAdapter tabAdapter;
    FragmentActivity activityContext;

    public void init(String userName) {
        userEmail = userName;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userLiveData = new MutableLiveData<>();
        loanLiveData = new MutableLiveData<>();
        loanList = new ArrayList<>();
        transactionList = new ArrayList<>();
        listenForChanges(userName);
    }

    public void initAccountType(String passedAccountType){
        accountType = passedAccountType;
    }

    public void initViewPager(FragmentActivity context){
        activityContext = context;
        createViewPager();
    }

//    @Bindable
//    public String getAccountNumber(){
//        if(accountType.equals("Checking")){
//            return user.getCheckingAccountNumber();
//        }
//        else{
//            return user.getSavingsAccountNumber();
//        }
//    }



    public void createViewPager(){
        Log.d("TAG", "Creating view pager!");
        tabAdapter = new TabAdapter(activityContext.getSupportFragmentManager());
        AccountsFragment accountsFragment = new AccountsFragment();
        tabAdapter.addFragment(accountsFragment, "Accounts");
        tabAdapter.addFragment(new TransferFragment(), "Transfer&Pay");
        tabAdapter.addFragment(new DepositFragment(), "Deposit");
        tabAdapter.addFragment(new MoreFragment(), "More");
    }

    public void listenForChanges(final String key){
        myRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "onDataChange: " + dataSnapshot.getKey() + " = " + dataSnapshot.getValue());
                loanList.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("loans").getChildren()) {
                    Loan loan = new Loan((String) snapshot.child("loanNumber").getValue(),
                            (String) snapshot.child("loanAmount").getValue());
                    if (loan.getLoanNumber() != null) {
                        loanList.add(loan);
                        //Log.d("TAG", "ONDATACHANGED: LOANLIST - " + loan.getLoanNumber());
                    }
                }
                loanLiveData.setValue(loanList);

                transactionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("transactions").getChildren()) {
                    Transaction transaction = new Transaction((String) snapshot.child("date").getValue(),
                            (String) snapshot.child("company").getValue(), (String) snapshot.child("transactionNumber").getValue(),
                            (String) snapshot.child("referenceNumber").getValue(), (String) snapshot.child("chargeAmount").getValue());
                    if (transaction.getDate() != null) {
                        transactionList.add(transaction);
                        Log.d("TAG", "ONDATACHANGED: TRANSACTIONLIST - " + transaction.getDate());
                    }
                }

                user = new User(key, (String)dataSnapshot.child("checkingAccountNumber").getValue(),
                        (String)dataSnapshot.child("checkingAccount").getValue(),
                        (String)dataSnapshot.child("checkingAccountAvailable").getValue(),
                        (String)dataSnapshot.child("savingsAccountNumber").getValue(),
                        (String)dataSnapshot.child("savingsAccount").getValue(),
                        (String)dataSnapshot.child("savingsAccountAvailable").getValue(),
                        loanList, transactionList);
                userLiveData.setValue(user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public LiveData<User> getUserLiveData(){
        return userLiveData;
    }

    public LiveData<ArrayList<Loan>> getLoanLiveData(){
        return loanLiveData;
    }

    @Bindable
    public TabAdapter getTabAdapter(){
        Log.d("TAG", "Getting tab adapter!");
        return tabAdapter;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}
