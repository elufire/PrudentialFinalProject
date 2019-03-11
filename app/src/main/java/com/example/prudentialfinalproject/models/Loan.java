package com.example.prudentialfinalproject.models;
//fix
import android.os.Parcel;
import android.os.Parcelable;

public class Loan implements Parcelable {
    private String loanNumber;
    private String loanAmount;

    public Loan(String loanNumber, String loanAmount) {
        this.loanNumber = loanNumber;
        this.loanAmount = loanAmount;
    }

    public Loan() {
    }

    protected Loan(Parcel in) {
        loanNumber = in.readString();
        loanAmount = in.readString();
    }

    public static final Creator<Loan> CREATOR = new Creator<Loan>() {
        @Override
        public Loan createFromParcel(Parcel in) {
            return new Loan(in);
        }

        @Override
        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loanNumber);
        dest.writeString(loanAmount);
    }
}
