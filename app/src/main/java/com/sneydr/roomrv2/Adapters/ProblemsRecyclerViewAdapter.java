package com.sneydr.roomrv2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.ProblemViewHolder;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;
import com.sneydr.roomrv2.databinding.RowProblemBinding;

import java.util.List;

public class ProblemsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Problem> data;
    private ItemClickListener itemClickListener;
    private final int EMPTY = 0;
    private final int PROBLEMS = 1;

    private RowProblemBinding binding;

    public ProblemsRecyclerViewAdapter(List<Problem> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.isEmpty()) {
            return EMPTY;
        }
        else if (data.get(0) != null) {
            return PROBLEMS;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.row_problem, parent, false);
        return new ProblemViewHolder(binding, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            emptyViewHolder.bindText("No Problems");
        } else {
            Problem problem = data.get(position);
            ProblemViewHolder problemViewHolder = (ProblemViewHolder) holder;
            problemViewHolder.bindProblem(problem);
        }
    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        }
        return this.data.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public Problem getItemAtPosition(int position) {
        return this.data.get(position);
    }

}
