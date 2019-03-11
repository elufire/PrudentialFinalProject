package com.example.prudentialfinalproject.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.prudentialfinalproject.R;
import com.example.prudentialfinalproject.databinding.ItemBinding;
import com.example.prudentialfinalproject.models.Loan;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<Loan> loans;


    public RecyclerViewAdapter(ArrayList<Loan> passedLoans){
        loans = passedLoans;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        ItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Loan loan = loans.get(position);
        viewHolder.itemBinding.setLoan(loan);
    }

    @Override
    public int getItemCount() {
        return loans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBinding itemBinding;
        public ViewHolder(@NonNull ItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
