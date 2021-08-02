package com.sneydr.roomrv2.Adapters;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sneydr.roomrv2.Adapters.Listeners.OnApproved;
import com.sneydr.roomrv2.Adapters.Listeners.OnSetContacts;
import com.sneydr.roomrv2.Adapters.Listeners.OnUnapproved;
import com.sneydr.roomrv2.Adapters.Listeners.StatefulItemClickListener;
import com.sneydr.roomrv2.Adapters.ViewHolders.DocumentViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.TenantNameViewHolder;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;
import com.sneydr.roomrv2.databinding.RowTenantNameBinding;

import java.util.List;

public class TenantNameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Tenant> data;
    private StatefulItemClickListener itemClickListener;
    private final int EMPTY = 0;
    private final int TENANTS = 1;
    private RowTenantNameBinding binding;
    private OnApproved onApproved;
    private OnUnapproved onUnapproved;
    private OnSetContacts onSetContacts;

    public TenantNameRecyclerViewAdapter(List<Tenant> data) {
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
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY) {
            RowEmptyRecyclerviewBinding emptyBinding = DataBindingUtil.inflate(inflater, R.layout.row_empty_recyclerview, parent, false);
            return new EmptyViewHolder(emptyBinding);
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.row_tenant_name, parent, false);
        return new TenantNameViewHolder(binding, onApproved, onUnapproved, onSetContacts);
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
            tenantNameViewHolder.bindApproved(onApproved);
            tenantNameViewHolder.bindOnUnapproved(onUnapproved);
        }

    }

    public List<Tenant> getData() {
        return this.data;
    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        }
        return this.data.size();
    }

    public void setOnApproved(OnApproved onItemClickedListener) {
        this.onApproved = onItemClickedListener;
    }

    public void setOnUnapproved(OnUnapproved onItemClickedListener) {
        this.onUnapproved = onItemClickedListener;
    }

    public void setOnSetContacts(OnSetContacts onItemClickedListener) {
        this.onSetContacts = onItemClickedListener;
    }

    public Tenant getTenantAtPosition(int position) {
        return this.data.get(position);
    }



}
