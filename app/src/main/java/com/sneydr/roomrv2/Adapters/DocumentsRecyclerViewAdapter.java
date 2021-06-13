package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.Adapters.ViewHolders.DocumentViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.HousesViewHolder;
import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowDocumentBinding;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

import java.util.List;

public class DocumentsRecyclerViewAdapter extends RecyclerView.Adapter {



    private List<Document> data;
    private ItemClickListener onItemClick;
    private OnCreateButtonClickListener onCreateButtonClickListener;
    private OnDownloadButtonClickListener onDownloadButtonClickListener;
    private final int EMPTY = 0;
    private final int DOCUMENTS = 1;

    public DocumentsRecyclerViewAdapter(List<Document> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.isEmpty()) {
            return EMPTY;
        }
        else if (data.get(0) != null) {
            return DOCUMENTS;
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
        RowDocumentBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_document, parent, false);
        return new DocumentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == EMPTY) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) viewHolder;
            emptyViewHolder.bindText("No Documents");
        } else {
            Document document = data.get(position);
            DocumentViewHolder documentViewHolder = (DocumentViewHolder) viewHolder;
            documentViewHolder.bindDocument(document);
            documentViewHolder.bindItemClickListener(onItemClick);
            documentViewHolder.binCreateButtonClickListener(onCreateButtonClickListener);
            documentViewHolder.bindDownloadButtonClickListener(onDownloadButtonClickListener);

        }
    }

    @Override
    public int getItemCount() {
        if (data.isEmpty()) {
            return 1;
        }
        return this.data.size();
    }

    public Document getItemAtPosition(int position) {
        return this.data.get(position);
    }

    public void setOnClickListener(ItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setOnCreateButtonClickListener(OnCreateButtonClickListener onItemClick) {
        this.onCreateButtonClickListener = onItemClick;
    }


    public void setOnDownloadButtonClickListener(OnDownloadButtonClickListener onItemClick) {
        this.onDownloadButtonClickListener = onItemClick;
    }

}
