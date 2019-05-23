package com.example.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView officeName;
    public TextView nameAndParty;

    public MyViewHolder(View view) {
        super(view);
        officeName = view.findViewById(R.id.officeName);
        nameAndParty = view.findViewById(R.id.nameAndParty);
    }
}
