package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sneydr.roomrv2.Adapters.Listeners.ButtonClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HousesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<House> data;
    private LayoutInflater inflater;
    private ButtonClickListener btnOnEdit;
    private final int EMPTY = 0;
    private final int HOUSES = 1;

    public HousesRecyclerViewAdapter(Context context, List<House> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (data.isEmpty()) {
            return EMPTY;
        }
        else if (data.get(0) != null) {
            return HOUSES;
        }
        return 2;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == EMPTY) {
            View emptyView = inflater.inflate(R.layout.row_empty_recyclerview, parent, false);
            return new EmptyViewHolder(emptyView);
        }
        View houseView = inflater.inflate(R.layout.row_house, parent, false);
        HousesViewHolder housesViewHolder = new HousesViewHolder(houseView);
        housesViewHolder.setBtnOnEdit(btnOnEdit);
        return housesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) viewHolder;
            emptyViewHolder.bindText("No Houses");
        } else {
            House house = data.get(position);
            HousesViewHolder housesViewHolder = (HousesViewHolder) viewHolder;
            housesViewHolder.bindHouse(house);
        }
    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        }
        return this.data.size();
    }

    public House getItemAtPosition(int position) {
        return this.data.get(position);
    }

    public void setBtnOnEdit(ButtonClickListener buttonClickListener){
        this.btnOnEdit = buttonClickListener;
    }



}
