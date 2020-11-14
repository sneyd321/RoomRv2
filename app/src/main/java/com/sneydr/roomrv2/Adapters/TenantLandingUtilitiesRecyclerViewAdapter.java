package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Entities.House.Utility.Utility;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

import java.util.List;

public class TenantLandingUtilitiesRecyclerViewAdapter extends RecyclerView.Adapter<TenantLandingUtilitiesRecyclerViewAdapter.ViewHolder> {


    private List<Utility> data;
    private LayoutInflater inflater;

    public TenantLandingUtilitiesRecyclerViewAdapter(Context context, List<Utility> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_tenant_utilities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Utility utility = this.data.get(position);
        holder.txtName.setText(utility.getName() + ": Tenant responsibility");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private Button btnSetNotification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtTenantLandingUtilityName);
            btnSetNotification = itemView.findViewById(R.id.btnNotifyUtility);
        }
    }

}
