package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;

public class EmptyViewHolder extends RecyclerView.ViewHolder {

    RowEmptyRecyclerviewBinding rowEmptyRecyclerviewBinding;


    public EmptyViewHolder(RowEmptyRecyclerviewBinding binding) {
        super(binding.getRoot());
        rowEmptyRecyclerviewBinding = binding;
    }

    public void bindText(String text) {
        rowEmptyRecyclerviewBinding.txtEmptyRecyclerView.setText(text);
    }

    public void bindImage(int resId) {
        rowEmptyRecyclerviewBinding.imageView.setImageResource(resId);
    }


}
