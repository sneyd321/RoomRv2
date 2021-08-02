package com.sneydr.roomrv2.Adapters.ViewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.ButtonState.ButtonState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.DisabledState;
import com.sneydr.roomrv2.Adapters.ButtonState.EnabledState;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.RowDocumentBinding;

import java.util.zip.Inflater;


public class DocumentViewHolder extends RecyclerView.ViewHolder {

    OnCreateButtonClickListener onCreateButtonClickListener;
    OnDownloadButtonClickListener onDownloadButtonClickListener;
    private RowDocumentBinding rowDocumentBinding;



    public DocumentViewHolder(RowDocumentBinding binding) {
        super(binding.getRoot());
        this.rowDocumentBinding = binding;
        rowDocumentBinding.btnBuildDocument.setOnClickListener(onCreateButtonListener);
        rowDocumentBinding.btnDownloadLease.setOnClickListener(onDownloadListener);

    }


    public void binCreateButtonClickListener(OnCreateButtonClickListener itemClickListener) {
        this.onCreateButtonClickListener = itemClickListener;
    }

    public void bindDownloadButtonClickListener(OnDownloadButtonClickListener itemClickListener) {
        this.onDownloadButtonClickListener = itemClickListener;
    }


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


    public void bind(Document document) {
        Context context = rowDocumentBinding.getRoot().getContext();
        ButtonStateContext buttonStateContext = new ButtonStateContext();
        buttonStateContext.setState(new DisabledState());
        rowDocumentBinding.txtDocumentName.setText(document.getName());
        rowDocumentBinding.txtDocumentDescription.setText(document.getDescription());
        if (document.getDocumentURL() != null) {
            buttonStateContext.setState(new EnabledState());
        }
        ButtonState state = buttonStateContext.getState();
        rowDocumentBinding.btnDownloadLease.setTextColor(context.getResources().getColor(state.getColour()));
        rowDocumentBinding.btnDownloadLease.setIconTintResource(state.getBackgroundDrawable());
        rowDocumentBinding.btnDownloadLease.setEnabled(state.getEnabled());
    }
}
