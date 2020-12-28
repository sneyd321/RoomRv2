package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.ButtonState.ApprovedState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.UnapprovedState;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.StatefulItemClickListener;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowTenantNameBinding;

public class TenantNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ButtonStateContext buttonStateContext;
    private StatefulItemClickListener itemClickListener;
    RowTenantNameBinding binding;

    public TenantNameViewHolder(RowTenantNameBinding binding, StatefulItemClickListener itemClickListener){
        super(binding.getRoot());
        this.binding = binding;
        this.itemClickListener = itemClickListener;
        this.buttonStateContext = new ButtonStateContext();
        binding.getRoot().setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            binding.imgApproveTenant.setColorFilter(itemView.getResources().getColor(buttonStateContext.getState().getColour()));
            binding.txtApprovalLabel.setText(buttonStateContext.getState().getText());
            binding.crdTenantName.setBackground(itemView.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));
            binding.imgApproveTenant.setImageDrawable(itemView.getResources().getDrawable(buttonStateContext.getState().getVectorDrawable()));
            itemClickListener.onItemClick(v, getAdapterPosition(), buttonStateContext);
        }
    }



    public void bindTenant(Tenant tenant) {
        binding.txtTenantName.setText(tenant.getFullName());
        buttonStateContext.setState(new UnapprovedState(buttonStateContext));
        if (tenant.isApproved()) {
            buttonStateContext.setState(new ApprovedState(buttonStateContext));
        }
        binding.imgApproveTenant.setColorFilter(itemView.getResources().getColor(buttonStateContext.getState().getColour()));
        binding.imgApproveTenant.setImageDrawable(itemView.getResources().getDrawable(buttonStateContext.getState().getVectorDrawable()));
        binding.txtApprovalLabel.setText(buttonStateContext.getState().getText());
        binding.crdTenantName.setBackground(itemView.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));
    }



}
