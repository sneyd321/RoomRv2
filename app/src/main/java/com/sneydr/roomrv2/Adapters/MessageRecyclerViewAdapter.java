package com.sneydr.roomrv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.App.CircleTransform;
import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private LayoutInflater inflater;
    private List<Message> data;
    private String email;
    private String userType;
    private final int TO = 0;
    private final int FROM = 1;
    private Context context;



    public MessageRecyclerViewAdapter(Context context, List<Message> data, String userType, String email) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.userType = userType;
        this.email = email;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = data.get(position);
        if (message.getEmail().equals(this.email) && message.getUserType().equals(this.userType)){
            return TO;
        }
        return FROM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TO) {
            View toView = inflater.inflate(R.layout.row_message_to, parent, false);
            return new ToMessageViewHolder(toView);
        }
        else {
            View fromView = inflater.inflate(R.layout.row_message_from, parent, false);
            return new FromMessageViewHolder(fromView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = data.get(position);
        if (holder.getItemViewType() == TO) {
            ToMessageViewHolder viewHolder = (ToMessageViewHolder) holder;
            viewHolder.txtTimestamp.setText(message.getTimestamp());
            viewHolder.txtMessage.setText(message.getMessage());
            viewHolder.txtFullName.setText(message.getUserName());
            Picasso.get().load("https://storage.googleapis.com/roomr-222721.appspot.com/Problems/Problem_118.jpg")
                    .transform(new CircleTransform(context))
                    .fit()
                    .centerCrop()
                    .into(viewHolder.imgProfile);

        } else {
            FromMessageViewHolder viewHolder = (FromMessageViewHolder) holder;
            viewHolder.txtTimestamp.setText(message.getTimestamp());
            viewHolder.txtMessage.setText(message.getMessage());
            viewHolder.txtFullName.setText(message.getUserName());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(Message message) {
        this.data.add(message);
        notifyDataSetChanged();
    }

    public void refresh(List<Message> messages) {
        this.data = messages;
        notifyDataSetChanged();
    }

    public class ToMessageViewHolder extends RecyclerView.ViewHolder {

        private TextView txtFullName;
        private TextView txtMessage;
        private TextView txtTimestamp;
        private ImageView imgProfile;


        public ToMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtTimestamp = itemView.findViewById(R.id.txtTimestamp);
        }
    }

    public class FromMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFullName;
        private TextView txtMessage;
        private TextView txtTimestamp;

        public FromMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtTimestamp = itemView.findViewById(R.id.txtTimestamp);
        }
    }

}
