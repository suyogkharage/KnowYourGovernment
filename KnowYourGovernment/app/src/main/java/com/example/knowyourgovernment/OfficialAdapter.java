package com.example.knowyourgovernment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class OfficialAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Official> officialList;
    private MainActivity mainAct;

    public OfficialAdapter(List<Official> ofcList, MainActivity ma) {
        this.officialList = ofcList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.official_list_row, viewGroup, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Official official = officialList.get(i);
        myViewHolder.officeName.setText(official.getOfficeName());
        myViewHolder.nameAndParty.setText(official.getName()+" ("+official.getPartyName()+")");

    }

    @Override
    public int getItemCount() {
        return officialList.size();
    }
}
