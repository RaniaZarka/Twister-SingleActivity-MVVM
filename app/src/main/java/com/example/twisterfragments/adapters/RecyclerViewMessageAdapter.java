package com.example.twisterfragments.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twisterfragments.model.Messages;
import com.example.twisterfragments.R;
import java.util.List;

public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<RecyclerViewMessageAdapter.MyViewHolder> {
    private static final String LOG_TAG = "Messages";
    private List<Messages> data;
    private final LayoutInflater mInflater;
    private   ItemClickListener mClickListener;


    public RecyclerViewMessageAdapter(Context context, List<Messages> data) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
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
        if(message.getTotalComments()!=null){
            String comments = Integer.toString(message.getTotalComments());
            holder.TotalTextView.setText(comments + " comments");
        }else{
            holder.TotalTextView.setText("0 comments");
        }
        holder.UsertextView.setText(message.getUser());
        holder.ContenttextView.setText(message.getContent());
        Log.d(LOG_TAG, "onBindViewHolder called " + position);
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        Log.d(LOG_TAG, "getItemCount called: " + count);
        return count;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void addMessage(Messages message) {
        data.add(0, message);
        notifyDataSetChanged();
    }

    public interface ItemClickListener<T> {
        void onItemClick(View view, int position, Messages message);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView UsertextView, ContenttextView, TotalTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UsertextView = itemView.findViewById(R.id.recyclerUser);
            ContenttextView = itemView.findViewById(R.id.recyclerContent);
            TotalTextView = itemView.findViewById(R.id.recyclerTotalComments);
            itemView.setOnClickListener(this);
        }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onItemClick(view, getAdapterPosition(), data.get(getAdapterPosition()));

        }
    }
}
    }








