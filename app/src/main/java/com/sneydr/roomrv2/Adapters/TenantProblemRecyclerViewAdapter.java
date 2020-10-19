package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.R;

import java.util.List;

public class TenantProblemRecyclerViewAdapter extends RecyclerView.Adapter<TenantProblemRecyclerViewAdapter.ViewHolder> {


    private LayoutInflater inflater;
    private List<Problem> data;

    public TenantProblemRecyclerViewAdapter(Context context, List<Problem> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.fragment_tenant_problem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
