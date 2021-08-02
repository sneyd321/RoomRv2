package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.ButtonState.ApprovedState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.UnapprovedState;
import com.sneydr.roomrv2.Adapters.Listeners.OnApproved;
import com.sneydr.roomrv2.Adapters.Listeners.OnSetContacts;
import com.sneydr.roomrv2.Adapters.Listeners.OnUnapproved;
import com.sneydr.roomrv2.App.CircleTransform;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowTenantNameBinding;
import com.squareup.picasso.Picasso;

public class TenantNameViewHolder extends RecyclerView.ViewHolder {


    ButtonStateContext buttonStateContext;

    private OnApproved onApproved;
    private OnUnapproved onUnapproved;
    private OnSetContacts onSetContacts;
    RowTenantNameBinding binding;

    public TenantNameViewHolder(RowTenantNameBinding binding, OnApproved onApproved, OnUnapproved onUnapproved, OnSetContacts onSetContacts){
        super(binding.getRoot());
        this.binding = binding;
        this.onApproved = onApproved;
        this.onUnapproved = onUnapproved;
        this.onSetContacts = onSetContacts;
        binding.btnApprove.setOnClickListener(onApprove);
        binding.btnUnapprove.setOnClickListener(onUnapprove);
        binding.btnAddContact.setOnClickListener(onAddContacts);
        this.buttonStateContext = new ButtonStateContext();
    }

    public void bindOnUnapproved(OnUnapproved itemClickListener) {
        this.onUnapproved = itemClickListener;
    }

    public void bindApproved(OnApproved itemClickListener) {
        this.onApproved = itemClickListener;
    }

    public void bindContacts(OnSetContacts itemClickListener) {
        this.onSetContacts = itemClickListener;
    }




    public void bindTenant(Tenant tenant) {
        binding.txtTenantName.setText(tenant.getFullName());
        if (tenant.getImageURL() == null || tenant.getImageURL().isEmpty()) {
            binding.imgTenantProfile.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(tenant.getImageURL())
                    .transform(new CircleTransform(binding.getRoot().getContext()))
                    .fit()
                    .centerCrop()
                    .into(binding.imgTenantProfile);
        }

        buttonStateContext.setState(new UnapprovedState(buttonStateContext));
        if (tenant.isApproved()) {
            buttonStateContext.setState(new ApprovedState(buttonStateContext));
        }

        binding.imgApproveTenant.setColorFilter(itemView.getResources().getColor(buttonStateContext.getState().getColour()));
        binding.imgApproveTenant.setImageDrawable(itemView.getResources().getDrawable(buttonStateContext.getState().getVectorDrawable()));

        //binding.txtApprovalLabel.setText(buttonStateContext.getState().getText());
        // binding.crdTenantName.setBackground(itemView.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));
    }

    View.OnClickListener onAddContacts = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onSetContacts != null) {
                onSetContacts.onSetContacts(v, getAdapterPosition());
            }
        }
    };

    View.OnClickListener onApprove = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onApproved != null) {
                onApproved.onApprove(v, getAdapterPosition());
                buttonStateContext.setState(new ApprovedState(buttonStateContext));
            }
        }
    };


    View.OnClickListener onUnapprove = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onUnapproved != null) {
                onUnapproved.onUnapprove(v, getAdapterPosition());
                buttonStateContext.setState(new UnapprovedState(buttonStateContext));
            }
        }
    };








}
