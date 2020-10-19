package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

import java.util.List;

public class TenantNameRecyclerViewAdapter extends RecyclerView.Adapter<TenantNameRecyclerViewAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<Tenant> data;
    private Context context;
    private ItemClickListener itemClickListener;

    public TenantNameRecyclerViewAdapter(Context context, List<Tenant> data) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_tenant_name, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tenant tenant = data.get(position);
        holder.txtTenantName.setText(tenant.getFullName());
        if (tenant.isApproved()) {
            holder.chkApproved.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setItemClickListener(ItemClickListener onItemClickedListener) {
        this.itemClickListener = onItemClickedListener;
    }

    public Tenant getTenantAtPosition(int position) {
        return this.data.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{

        TextView txtTenantName;
        CheckBox chkApproved;
        CardView cardView;
        private int[][] states = {{android.R.attr.state_checked}, {}};
        private int[] colors = {context.getResources().getColor(R.color.Green), context.getResources().getColor(R.color.Red)};
        ButtonStateContext buttonStateContext;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenantName = itemView.findViewById(R.id.txtTenantName);
            chkApproved = itemView.findViewById(R.id.btnApproveTenant);
            chkApproved.setOnCheckedChangeListener(this);
            cardView = itemView.findViewById(R.id.crdTenantName);
            buttonStateContext = new ButtonStateContext();
            CompoundButtonCompat.setButtonTintList(chkApproved, new ColorStateList(states, colors));
            buttonStateContext.setState(new UnapprovedState(buttonStateContext));
            chkApproved.setText(buttonStateContext.getState().getText());
            cardView.setBackground(context.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (itemClickListener != null) {
                buttonStateContext.setState(new UnapprovedState(buttonStateContext));
                if (isChecked){
                    buttonStateContext.setState(new ApprovedState(buttonStateContext));
                }
                buttonView.setText(buttonStateContext.getState().getText());
                cardView.setBackground(context.getResources().getDrawable(buttonStateContext.getState().getBackgroundDrawable()));
                itemClickListener.onItemClick(buttonView, getAdapterPosition());
            }

        }
    }

}
