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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.Adapters.ButtonState.ApprovedState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.UnapprovedState;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

import java.util.List;

public class TenantNameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflater;
    private List<Tenant> data;
    private Context context;
    private ItemClickListener itemClickListener;
    private final int EMPTY = 0;
    private final int TENANTS = 1;

    public TenantNameRecyclerViewAdapter(Context context, List<Tenant> data) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.isEmpty()) {
            return EMPTY;
        }
        else if (data.get(0) != null) {
            return TENANTS;
        }
        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == EMPTY) {
            View emptyView = inflater.inflate(R.layout.row_empty_recyclerview, parent, false);
            return new EmptyViewHolder(emptyView);
        }
        View view = inflater.inflate(R.layout.row_tenant_name, parent, false);
        TenantNameViewHolder tenantNameViewHolder = new TenantNameViewHolder(view);
        tenantNameViewHolder.setItemClickListener(itemClickListener);
        return tenantNameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            emptyViewHolder.bindText("No Tenants");
        } else {
            Tenant tenant = data.get(position);
            TenantNameViewHolder tenantNameViewHolder = (TenantNameViewHolder) holder;
            tenantNameViewHolder.bindTenant(tenant);
        }

    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        }
        return this.data.size();
    }

    public void setItemClickListener(ItemClickListener onItemClickedListener) {
        this.itemClickListener = onItemClickedListener;
    }

    public Tenant getTenantAtPosition(int position) {
        return this.data.get(position);
    }



}
