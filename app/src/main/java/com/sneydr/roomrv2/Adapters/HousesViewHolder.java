package com.sneydr.roomrv2.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ButtonClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.squareup.picasso.Picasso;

public class HousesViewHolder extends RecyclerView.ViewHolder {

    TextView txtPrimaryAddress, txtSecondaryAddress, txtUnitName;
    Button btnEditHouse;
    ImageView imgHouse;
    ButtonClickListener btnOnEdit;
    CardView cardView;

    public HousesViewHolder(@NonNull View itemView) {
        super(itemView);
        txtPrimaryAddress = itemView.findViewById(R.id.txtHouseRowAddress);
        txtSecondaryAddress = itemView.findViewById(R.id.txtHouseRowAddressSecondary);
        txtUnitName = itemView.findViewById(R.id.txtHouseRowUnitName);
        btnEditHouse = itemView.findViewById(R.id.btnUpdateHouse);
        btnEditHouse.setOnClickListener(onEditHouse);
        cardView = itemView.findViewById(R.id.crdHouse);
        imgHouse = itemView.findViewById(R.id.imgHouse);
    }

    public void bindHouse(House house) {
        this.txtPrimaryAddress.setText(house.getRentalUnitLocation().getFormattedPrimaryAddress());
        this.txtSecondaryAddress.setText(house.getRentalUnitLocation().getFormattedSecondaryAddress());
        this.txtUnitName.setText(house.getRentalUnitLocation().getUnitName());
        Picasso.get().load(R.drawable.examplehouse).into(this.imgHouse);
    }

    View.OnClickListener onEditHouse = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (btnOnEdit != null){
                btnOnEdit.onButtonClick(view, getAdapterPosition());
            }
        }
    };

    public void setBtnOnEdit(ButtonClickListener buttonClickListener){
        this.btnOnEdit = buttonClickListener;
    }
}
