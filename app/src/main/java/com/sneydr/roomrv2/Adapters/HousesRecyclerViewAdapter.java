package com.sneydr.roomrv2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.ViewHolders.DocumentViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.HousesViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.ViewHolder;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class HousesRecyclerViewAdapter extends RecyclerViewAdapter<House> {

    private ItemClickListener onItemClick;

    public HousesRecyclerViewAdapter(List<House> data) {
        super(data);
        this.emptyString = "No Houses";
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY) {
            RowEmptyRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_empty_recyclerview, parent, false);
            return new EmptyViewHolder(binding);
        }
        RowHouseBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_house, parent, false);
        return new HousesViewHolder(binding);
    }


    @Override
    protected void bind(House house, RecyclerView.ViewHolder holder) {
        HousesViewHolder housesViewHolder = (HousesViewHolder) holder;
        housesViewHolder.bindHouse(house, onItemClick);
    }


    public House getItemAtPosition(int position) {
        return this.data.get(position);
    }

    public void setOnClickListener(ItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }



}
