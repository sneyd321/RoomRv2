package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.Adapters.ButtonState.ApprovedState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.UnapprovedState;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

public class TenantNameViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

    TextView txtTenantName;
    CheckBox chkApproved;
    CardView cardView;
    ButtonStateContext buttonStateContext;
    private ItemClickListener itemClickListener;

    public TenantNameViewHolder(View itemView) {
        super(itemView);
        txtTenantName = itemView.findViewById(R.id.txtTenantName);
        chkApproved = itemView.findViewById(R.id.btnApproveTenant);
        chkApproved.setOnCheckedChangeListener(this);
        Context context = chkApproved.getContext();
        cardView = itemView.findViewById(R.id.crdTenantName);
        buttonStateContext = new ButtonStateContext();
        int[][] states = {{android.R.attr.state_checked}, {}};
        int[] colors = {context.getResources().getColor(R.color.Green), context.getResources().getColor(R.color.Red)};
        CompoundButtonCompat.setButtonTintList(chkApproved, new ColorStateList(states, colors));
        buttonStateContext.setState(new UnapprovedState(buttonStateContext));
        chkApproved.setText(buttonStateContext.getState().getText());
        cardView.setBackground(context.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));
        YoYo.with(Techniques.FadeInUp).duration(500).playOn(cardView);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (itemClickListener != null) {
            buttonStateContext.setState(new UnapprovedState(buttonStateContext));
            if (isChecked){
                buttonStateContext.setState(new ApprovedState(buttonStateContext));
            }
            buttonView.setText(buttonStateContext.getState().getText());
            Context context = cardView.getContext();
            cardView.setBackground(context.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));
            itemClickListener.onItemClick(buttonView, getAdapterPosition());
        }

    }

    public void setItemClickListener(ItemClickListener onItemClickedListener) {
        this.itemClickListener = onItemClickedListener;
    }

    public void bindTenant(Tenant tenant) {
        this.txtTenantName.setText(tenant.getFullName());
        if (tenant.isApproved()) {
            this.chkApproved.setChecked(true);
        }
    }


}
