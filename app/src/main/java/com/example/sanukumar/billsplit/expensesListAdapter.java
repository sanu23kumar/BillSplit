package com.example.sanukumar.billsplit;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanukumar on 10/07/18.
 */

class expensesListAdapter extends RecyclerView.Adapter<expensesListAdapter.ViewHolder> {

    private ArrayList<DataModel> DataList;

    public expensesListAdapter(ArrayList<DataModel> DataList) {
        this.DataList = DataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, friendCount, itemAmount;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.productName);
            friendCount = v.findViewById(R.id.friendCount);
            itemAmount = v.findViewById(R.id.itemAmount);
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
        holder.name.setText(DataList.get(position).getArticleName());
        holder.friendCount.setText(DataList.get(position).getFriends());
        holder.itemAmount.setText(DataList.get(position).getCost());

        setAnimation(holder.name, position);
        setAnimation(holder.friendCount, position);
        setAnimation(holder.itemAmount, position);
        lastPosition = position;
    }


    @Override
    public int getItemCount() {
        return DataList.size();
    }

    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(400);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
        }
    }


}
