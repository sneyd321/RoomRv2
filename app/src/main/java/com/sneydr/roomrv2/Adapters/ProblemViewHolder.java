package com.sneydr.roomrv2.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Problem.ProblemStatus;
import com.sneydr.roomrv2.R;
import com.squareup.picasso.Picasso;

public class ProblemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imgProblem;
    private TextView txtType;
    private TextView txtStatus;
    private TextView txtDescription;
    private TextView txtDatePosted;
    private TextView txtLastUpdated;
    private ItemClickListener itemClickListener;

    public ProblemViewHolder(@NonNull View itemView) {
        super(itemView);
        imgProblem = itemView.findViewById(R.id.imgHomeownerProblem);
        txtType = itemView.findViewById(R.id.txtHomeownerProblemType);
        txtDescription = itemView.findViewById(R.id.txtHomeownerProblemDescription);
        txtStatus = itemView.findViewById(R.id.txtTenantProblemStatus);
        txtDatePosted = itemView.findViewById(R.id.txtTenantDatePosted);
        txtLastUpdated = itemView.findViewById(R.id.txtTenantLastUpdated);
        itemView.setOnClickListener(this);
    }

    public void bindProblem(Problem problem) {
        Picasso.get()
                .load(problem.getImageUrl())
                .placeholder(R.drawable.ic_baseline_image_search_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fit()
                .centerCrop()
                .into(this.imgProblem);
        this.txtType.setText(problem.getCategory());
        this.txtDescription.setText(problem.getDescription());
        ProblemStatus status = problem.getContext().getStatus();
        this.txtStatus.setText(status.getName());
        this.txtStatus.setTextColor(itemView.getResources().getColor(status.getColour()));
        this.txtDatePosted.setText(problem.getDatePosted());
        this.txtLastUpdated.setText(problem.getLastUpdated());
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
