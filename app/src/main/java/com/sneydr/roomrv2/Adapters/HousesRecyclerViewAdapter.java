package com.sneydr.roomrv2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.HousesViewHolder;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HousesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<House> data;
    private ItemClickListener onItemClick;
    private final int EMPTY = 0;
    private final int HOUSES = 1;

    public HousesRecyclerViewAdapter(List<House> data) {
        this.data = data;
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY) {
            RowEmptyRecyclerviewBinding emptyBinding = DataBindingUtil.inflate(inflater, R.layout.row_empty_recyclerview, parent, false);
            return new EmptyViewHolder(emptyBinding);
        }
        RowHouseBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_house, parent, false);
        return new HousesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) viewHolder;
            emptyViewHolder.bindText("No Houses");
        } else {
            House house = data.get(position);
            HousesViewHolder housesViewHolder = (HousesViewHolder) viewHolder;
            housesViewHolder.bindHouse(house, onItemClick);
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

    public void setOnClickListener(ItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }



}
