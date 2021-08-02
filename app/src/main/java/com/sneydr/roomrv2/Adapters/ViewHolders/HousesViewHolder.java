package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

public class HousesViewHolder extends RecyclerView.ViewHolder {


    private ItemClickListener itemClickListener;
    private RowHouseBinding rowHouseBinding;

    public HousesViewHolder(RowHouseBinding binding) {
        super(binding.getRoot());
        this.rowHouseBinding = binding;
        rowHouseBinding.btnUpdateHouse.setOnClickListener(onClickListener);
    }



    public void bindHouse(House house, ItemClickListener itemClickListener) {
        if (house.getRentalUnitLocation() != null) {
            rowHouseBinding.txtHouseRowAddress.setText(house.getRentalUnitLocation().getFormattedPrimaryAddress());
            rowHouseBinding.txtHouseRowAddressSecondary.setText(house.getRentalUnitLocation().getFormattedSecondaryAddress());
            rowHouseBinding.txtHouseRowUnitName.setText(house.getRentalUnitLocation().getUnitName());
        }

        rowHouseBinding.txtHouseId.setText(house.getFormattedHouseId());
        Context context = rowHouseBinding.getRoot().getContext();
        rowHouseBinding.imgHouse.setImageDrawable(context.getResources().getDrawable(R.drawable.examplehouse));
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

    public void setVoidItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
