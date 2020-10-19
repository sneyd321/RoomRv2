package com.sneydr.roomrv2.Adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

import java.util.List;

public class AddTenantRecyclerViewAdapter extends RecyclerView.Adapter<AddTenantRecyclerViewAdapter.ViewHolder> {

    private List<Tenant> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public AddTenantRecyclerViewAdapter(Context context, List<Tenant> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.approve_tenant_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tenant tenant = mData.get(position);
        holder.txtName.setText(tenant.getFullName());
        holder.txtEmail.setText(tenant.getEmail());
        holder.txtHouseAddress.setText("House Id: " + Integer.toString(tenant.getHouseId()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Tenant getItemById(int position) {
        return this.mData.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtEmail, txtHouseAddress;
        ImageView imgApprove;
        private boolean imgToggle = false;

        ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtAddTenantName);
            txtEmail = itemView.findViewById(R.id.txtAddTenantEmail);
            txtHouseAddress = itemView.findViewById(R.id.txtAddTenantHouseAddress);
            imgApprove = itemView.findViewById(R.id.imgApprove);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());

            }
        }
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


}