package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnHouseClick;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

public class HousesViewHolder extends RecyclerView.ViewHolder {


    ItemClickListener itemClickListener;
    RowHouseBinding binding;

    public HousesViewHolder(@NonNull RowHouseBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.btnUpdateHouse.setOnClickListener(onEditHouse);
    }


    public void bindHouse(House house, ItemClickListener itemClickListener) {
        binding.txtHouseRowAddress.setText(house.getRentalUnitLocation().getFormattedPrimaryAddress());
        binding.txtHouseRowAddressSecondary.setText(house.getRentalUnitLocation().getFormattedSecondaryAddress());
        binding.txtHouseRowUnitName.setText(house.getRentalUnitLocation().getUnitName());
        binding.txtHouseId.setText(binding.txtHouseId.getText().toString() + " " + Integer.toString(house.getHouseId()));
        Context context = binding.getRoot().getContext();

        binding.imgHouse.setImageDrawable(context.getResources().getDrawable(R.drawable.examplehouse));
        this.itemClickListener = itemClickListener;
    }

    View.OnClickListener onEditHouse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    };

}
