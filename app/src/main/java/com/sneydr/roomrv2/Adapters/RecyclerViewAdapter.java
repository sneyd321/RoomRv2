package com.sneydr.roomrv2.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.ViewHolders.DocumentViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.ViewHolder;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowDocumentBinding;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;

import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> data;
    protected final int EMPTY = 0;
    protected String emptyString;
    protected int emptyImage;




    public RecyclerViewAdapter(List<T> data) {
        this.data = data;
        this.emptyString = "No Data";
        this.emptyImage = R.drawable.emptyhouse;

    }




    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        }
        return this.data.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (data.isEmpty()) {
            return EMPTY;
        }
        else if (data.get(0) != null) {
            return 1;
        }
        return 2;
    }

    public T getItemAtPosition(int position) {
        return this.data.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
            emptyViewHolder.bindText(emptyString);
            emptyViewHolder.bindImage(emptyImage);
        } else {
            T t = this.data.get(position);
            bind(t, holder);
        }
    }

    protected abstract void bind(T t, RecyclerView.ViewHolder holder);

}
