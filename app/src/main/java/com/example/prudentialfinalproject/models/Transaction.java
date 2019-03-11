package com.example.prudentialfinalproject.models;
//fix2
import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {
    private String date;
    private String company;
    private String transactionNumber;
    private String referenceNumber;
    private String chargeAmount;

    public Transaction(String date, String company, String transactionNumber, String referenceNumber, String chargeAmount) {
        this.date = date;
        this.company = company;
        this.transactionNumber = transactionNumber;
        this.referenceNumber = referenceNumber;
        this.chargeAmount = chargeAmount;
    }

    protected Transaction(Parcel in) {
        date = in.readString();
        company = in.readString();
        transactionNumber = in.readString();
        referenceNumber = in.readString();
        chargeAmount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(company);
        dest.writeString(transactionNumber);
        dest.writeString(referenceNumber);
        dest.writeString(chargeAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
}
