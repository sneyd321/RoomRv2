package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.sneydr.roomrv2.R;

import java.util.zip.Inflater;

public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

    protected ViewBinding binding;

    public ViewHolder(LayoutInflater inflater, int layoutResource, ViewGroup parent) {
        super(DataBindingUtil.inflate(inflater, layoutResource, parent, false).getRoot());
        this.binding = DataBindingUtil.inflate(inflater, layoutResource, parent, false);
    }

    public abstract void bind(T t);
}
