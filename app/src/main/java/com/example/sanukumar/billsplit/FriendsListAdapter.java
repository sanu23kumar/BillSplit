package com.example.sanukumar.billsplit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    private ArrayList<FriendsDataModel> DataList;

    public FriendsListAdapter(ArrayList<FriendsDataModel> DataList) {
        this.DataList = DataList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone, toPayReceive;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.friendsListName);
            phone = v.findViewById(R.id.friendsListPhone);
            toPayReceive = v.findViewById(R.id.friendsListPayReceive);
        }
    }

    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendsListAdapter.ViewHolder holder, int position) {
        holder.name.setText(DataList.get(position).getFriendName());
        holder.phone.setText(DataList.get(position).getPhone());
        holder.toPayReceive.setText("+100");

        setAnimation(holder.name, position);
        setAnimation(holder.phone, position);
        setAnimation(holder.toPayReceive, position);
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
