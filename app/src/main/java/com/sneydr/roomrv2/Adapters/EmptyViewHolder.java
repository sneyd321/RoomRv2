package com.sneydr.roomrv2.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.R;

public class EmptyViewHolder extends RecyclerView.ViewHolder {

    TextView txtEmpty;
    public EmptyViewHolder(@NonNull View itemView) {
        super(itemView);
        txtEmpty = itemView.findViewById(R.id.txtEmptyRecyclerView);
    }

    public void bindText(String text) {
        txtEmpty.setText(text);
    }
}
