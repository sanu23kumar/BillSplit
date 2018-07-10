package com.example.sanukumar.billsplit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sanukumar on 10/07/18.
 */

class expensesListAdapter extends RecyclerView.Adapter<expensesListAdapter.ViewHolder> {

    private String[] dataset;

    public expensesListAdapter(String[] strings) {
        dataset = strings;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.productName);
        }
    }

    @Override
    public expensesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(dataset[position]);
    }


    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
