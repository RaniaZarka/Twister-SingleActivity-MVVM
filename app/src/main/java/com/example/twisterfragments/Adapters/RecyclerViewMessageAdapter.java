package com.example.twisterfragments.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<RecyclerViewMessageAdapter.MyViewHolder> {
    private static final String LOG_TAG = "Messages";
    private List<Messages> data;
    private final LayoutInflater mInflater;
    private final ItemClickListener mClickListener;


    public RecyclerViewMessageAdapter(Context context, ItemClickListener mClickListener) {
        this.data = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        this.mClickListener = mClickListener;
        Log.d(LOG_TAG, data.toString());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.message_recycler, parent, false);
        Log.d(LOG_TAG, view.toString());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMessageAdapter.MyViewHolder holder, int position) {
        Messages message = data.get(position);
        Log.d(LOG_TAG, "onBindViewHolder " + data.toString());
        String comments = Integer.toString(message.getTotalComments());
        holder.UsertextView.setText(message.getUser());
        holder.ContenttextView.setText(message.getContent());
        holder.TotalTextView.setText(comments + " comments");
        Log.d(LOG_TAG, "onBindViewHolder called " + position);
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        Log.d(LOG_TAG, "getItemCount called: " + count);
        return count;
    }

   /* public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }*/

    public void addMessage(Messages message) {
        data.add(0, message);
        notifyDataSetChanged();
    }

    public void addMessages(List<Messages> allMessages) {
        data.addAll(allMessages);
        notifyDataSetChanged();
    }

    //public interface ItemClickListener <T> {
    //void onItemClick(View view, int position, Messages message);
    public interface ItemClickListener {
        void onItemClick(Messages message);
    }

    //public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView UsertextView, ContenttextView, TotalTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UsertextView = itemView.findViewById(R.id.recyclerUser);
            ContenttextView = itemView.findViewById(R.id.recyclerContent);
            TotalTextView = itemView.findViewById(R.id.recyclerTotalComments);
            itemView.setOnClickListener(v -> mClickListener.onItemClick(data.get(getAdapterPosition())));

        }

   /* @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onItemClick(view, getAdapterPosition(), data.get(getAdapterPosition()));

        }
    }
}*/
    }
}







