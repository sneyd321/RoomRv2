package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

public class HousesViewHolder extends RecyclerView.ViewHolder {


    ItemClickListener itemClickListener;
    RowHouseBinding binding;

    public HousesViewHolder(@NonNull RowHouseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.btnUpdateHouse.setOnClickListener(onClickListener);
    }


    public void bindHouse(House house, ItemClickListener itemClickListener) {
        if (house.getRentalUnitLocation() != null) {
            binding.txtHouseRowAddress.setText(house.getRentalUnitLocation().getFormattedPrimaryAddress());
            binding.txtHouseRowAddressSecondary.setText(house.getRentalUnitLocation().getFormattedSecondaryAddress());
            binding.txtHouseRowUnitName.setText(house.getRentalUnitLocation().getUnitName());
        }

        binding.txtHouseId.setText(house.getFormattedHouseId());
        Context context = binding.getRoot().getContext();
        binding.imgHouse.setImageDrawable(context.getResources().getDrawable(R.drawable.examplehouse));
        this.itemClickListener = itemClickListener;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    };



}
