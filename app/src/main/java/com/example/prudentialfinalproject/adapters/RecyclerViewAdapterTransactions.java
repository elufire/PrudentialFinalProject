package com.example.prudentialfinalproject.adapters;
//test
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.databinding.ItemBinding;
import com.example.prudentialfinalproject.databinding.TransactionItemBinding;
import com.example.prudentialfinalproject.models.Transaction;

import java.util.ArrayList;

public class RecyclerViewAdapterTransactions extends RecyclerView.Adapter<RecyclerViewAdapterTransactions.ViewHolder> {
    ArrayList<Transaction> transactions;


    public RecyclerViewAdapterTransactions(ArrayList<Transaction> passedTransactions){
        transactions = passedTransactions;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterTransactions.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        TransactionItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.transaction_item, viewGroup, false);

        RecyclerViewAdapterTransactions.ViewHolder viewHolder = new RecyclerViewAdapterTransactions.ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTransactions.ViewHolder viewHolder, int position) {
        Transaction transaction = transactions.get(position);
        viewHolder.itemBindingTransactions.setTransaction(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TransactionItemBinding itemBindingTransactions;
        public ViewHolder(@NonNull TransactionItemBinding itemBinding) {
            super(itemBinding.getRoot());
            itemBindingTransactions = itemBinding;
        }
    }
}
