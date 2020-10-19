package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sneydr.roomrv2.App.UI.HouseDetail;
import com.sneydr.roomrv2.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HouseDetailUIRecyclerViewAdapter extends RecyclerView.Adapter<HouseDetailUIRecyclerViewAdapter.ViewHolder>{

    private List<HouseDetail> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private Context context;

    public HouseDetailUIRecyclerViewAdapter(Context context, List<HouseDetail> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.house_detail_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HouseDetail houseDetail = data.get(position);
        holder.txtName.setText(houseDetail.getName());
        holder.imgIcon.setImageResource(houseDetail.getResourceId());

    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public HouseDetail getItemAtPosition(int position) {
        return this.data.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName;
        ImageView imgIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtLabel);
            imgIcon = itemView.findViewById(R.id.imgImage);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition());
            }
        }


    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }




}
