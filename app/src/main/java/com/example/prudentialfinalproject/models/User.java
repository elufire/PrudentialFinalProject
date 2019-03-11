package com.example.prudentialfinalproject.models;
//fix
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class User implements Parcelable {
    private String userName;
    private String checkingAccount;
    private String savingsAccount;
    private String checkingAccountNumber;
    private String savingsAccountNumber;
    private String checkingAccountAvailable;
    private String savingsAccountAvailable;
    private List<Loan> loans;
    private List<Transaction> transactions;

    public User() {
    }

    public User(String userName, String checkingAccountNumber, String checkingAccount, String checkingAccountAvailable,
                String savingsAccountNumber, String savingsAccount,   String savingsAccountAvailable, List<Loan> loans,
                List<Transaction> transactions) {
        this.userName = userName;
        this.checkingAccount = checkingAccount;
        this.savingsAccount = savingsAccount;
        this.checkingAccountNumber = checkingAccountNumber;
        this.savingsAccountNumber = savingsAccountNumber;
        this.checkingAccountAvailable = checkingAccountAvailable;
        this.savingsAccountAvailable = savingsAccountAvailable;
        this.loans = loans;
        this.transactions = transactions;
    }


    protected User(Parcel in) {
        userName = in.readString();
        checkingAccount = in.readString();
        savingsAccount = in.readString();
        checkingAccountNumber = in.readString();
        savingsAccountNumber = in.readString();
        checkingAccountAvailable = in.readString();
        savingsAccountAvailable = in.readString();
        loans = in.createTypedArrayList(Loan.CREATOR);
        transactions = in.createTypedArrayList(Transaction.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getCheckingAccountNumber() {
        return checkingAccountNumber;
    }

    public void setCheckingAccountNumber(String checkingAccountNumber) {
        this.checkingAccountNumber = checkingAccountNumber;
    }

    public String getSavingsAccountNumber() {
        return savingsAccountNumber;
    }

    public void setSavingsAccountNumber(String savingsAccountNumber) {
        this.savingsAccountNumber = savingsAccountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(String checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public String getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(String savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public String getCheckingAccountAvailable() {
        return checkingAccountAvailable;
    }

    public void setCheckingAccountAvailable(String checkingAccountAvailable) {
        this.checkingAccountAvailable = checkingAccountAvailable;
    }

    public String getSavingsAccountAvailable() {
        return savingsAccountAvailable;
    }

    public void setSavingsAccountAvailable(String savingsAccountAvailable) {
        this.savingsAccountAvailable = savingsAccountAvailable;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(checkingAccount);
        dest.writeString(savingsAccount);
        dest.writeString(checkingAccountNumber);
        dest.writeString(savingsAccountNumber);
        dest.writeString(checkingAccountAvailable);
        dest.writeString(savingsAccountAvailable);
        dest.writeTypedList(loans);
        dest.writeTypedList(transactions);
    }
}
