package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowDocumentBinding;

public class DocumentViewHolder extends RecyclerView.ViewHolder {

    OnCreateButtonClickListener onCreateButtonClickListener;
    OnDownloadButtonClickListener onDownloadButtonClickListener;
    ItemClickListener itemClickListener;
    RowDocumentBinding binding;

    public DocumentViewHolder(@NonNull RowDocumentBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        binding.btnBuildDocument.setOnClickListener(onCreateButtonListener);
        binding.btnDownloadLease.setOnClickListener(onDownloadListener);
        binding.crdDocument.setOnClickListener(onCardClickedListener);
    }


    public void bindItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void binCreateButtonClickListener(OnCreateButtonClickListener itemClickListener) {
        this.onCreateButtonClickListener = itemClickListener;
    }

    public void bindDownloadButtonClickListener(OnDownloadButtonClickListener itemClickListener) {
        this.onDownloadButtonClickListener = itemClickListener;
    }


    public void bindDocument(Document document) {
        binding.txtDocumentName.setText(document.getName());
        Context context = binding.getRoot().getContext();
        binding.btnDownloadLease.setEnabled(true);
        binding.btnDownloadLease.setTextColor(context.getResources().getColor(R.color.LightGray));
        binding.btnDownloadLease.setIconTintResource(R.color.LightGray);
        binding.txtDocumentDescription.setText(document.getDescription());
        if (document.getDocumentURL() != null) {
            binding.btnDownloadLease.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            binding.btnDownloadLease.setIconTintResource(R.color.colorPrimary);
            binding.btnDownloadLease.setEnabled(true);
        }


    }

    View.OnClickListener onCardClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                if (binding.txtDocumentDescription.getVisibility() == View.VISIBLE) {
                    binding.txtDocumentDescription.setVisibility(View.GONE);
                }
                else {
                    binding.txtDocumentDescription.setVisibility(View.VISIBLE);
                }
                itemClickListener.onItemClick(v, getAdapterPosition());
            }

        }
    };

    View.OnClickListener onCreateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onCreateButtonClickListener != null) {
                onCreateButtonClickListener.onCreateButtonClick(v, getAdapterPosition());
            }
        }
    };


    View.OnClickListener onDownloadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onDownloadButtonClickListener != null) {
                onDownloadButtonClickListener.onDownloadButtonClick(v, getAdapterPosition());
            }
        }
    };

}
