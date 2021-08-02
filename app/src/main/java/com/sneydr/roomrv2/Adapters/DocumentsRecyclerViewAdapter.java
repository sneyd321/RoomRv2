package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.Adapters.ViewHolders.DocumentViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.EmptyViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.HousesViewHolder;
import com.sneydr.roomrv2.Adapters.ViewHolders.ViewHolder;
import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowDocumentBinding;
import com.sneydr.roomrv2.databinding.RowEmptyRecyclerviewBinding;
import com.sneydr.roomrv2.databinding.RowHouseBinding;

import java.util.List;

public class DocumentsRecyclerViewAdapter extends RecyclerViewAdapter<Document> {

    private OnCreateButtonClickListener onCreateButtonClickListener;
    private OnDownloadButtonClickListener onDownloadButtonClickListener;

    public DocumentsRecyclerViewAdapter(List<Document> data) {
        super(data);
        this.emptyString = "No Documents";
    }


    public Document getItemAtPosition(int position) {
        return this.data.get(position);
    }


    public void setOnCreateButtonClickListener(OnCreateButtonClickListener onItemClick) {
        this.onCreateButtonClickListener = onItemClick;
    }

    public void setOnDownloadButtonClickListener(OnDownloadButtonClickListener onItemClick) {
        this.onDownloadButtonClickListener = onItemClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY) {
            RowEmptyRecyclerviewBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_empty_recyclerview, parent, false);
            return new EmptyViewHolder(binding);
        }
        RowDocumentBinding binding = DataBindingUtil.inflate(inflater, R.layout.row_document, parent, false);
        return new DocumentViewHolder(binding);
    }


    @Override
    protected void bind(Document document, RecyclerView.ViewHolder holder) {
        DocumentViewHolder documentViewHolder = (DocumentViewHolder) holder;
        documentViewHolder.bind(document);
        documentViewHolder.binCreateButtonClickListener(onCreateButtonClickListener);
        documentViewHolder.bindDownloadButtonClickListener(onDownloadButtonClickListener);
    }
}
