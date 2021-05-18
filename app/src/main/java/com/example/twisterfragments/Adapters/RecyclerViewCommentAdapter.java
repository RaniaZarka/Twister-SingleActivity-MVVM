package com.example.twisterfragments.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twisterfragments.Model.Comments;
import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;

import java.util.List;

public class RecyclerViewCommentAdapter extends RecyclerView.Adapter<RecyclerViewCommentAdapter.MyViewHolder> {
    private static final String LOG_TAG = "Comments";
    private List<Comments> data;
    private final LayoutInflater mInflater;
    private RecyclerViewCommentAdapter.ItemClickListener mClickListener;
    private ImageButton button;
    private Context mContext;


    public RecyclerViewCommentAdapter(Context context, List<Comments> data) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
        Log.d(LOG_TAG, data.toString());
    }

    @NonNull
    @Override
    public RecyclerViewCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comments_recycler, parent, false);
        Log.d(LOG_TAG, view.toString());
        return new RecyclerViewCommentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCommentAdapter.MyViewHolder holder, int position) {
        Comments comment = data.get(position);
        Log.d(LOG_TAG, "onBindViewHolder comments " + data.toString());
        holder.UsertextView.setText(comment.getUser());
        holder.ContenttextView.setText(comment.getContent());
        Log.d(LOG_TAG, "onBindViewHolder called comments " + position);
    }


    @Override
    public int getItemCount() {
        int count = data.size();
        Log.d(LOG_TAG, "getItemCount called: " + count);
        return count;
    }
    Comments getItem(int id){

        return data.get(id);
    }

    public void addComment(Comments comment) {
        data.add(0, comment);
        notifyDataSetChanged();
    }

    public void addComments(List<Comments> allComments) {
        data.addAll(allComments);
        notifyDataSetChanged();
    }


    public void setClickListener(RecyclerViewCommentAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener<T> {
        void onItemClick(View view, int position, Comments comment);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView UsertextView, ContenttextView;
        final ImageButton button;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UsertextView = itemView.findViewById(R.id.recyclerCommentUser);
            ContenttextView = itemView.findViewById(R.id.recyclerCommentContent);
            button= (ImageButton) itemView.findViewById(R.id.commentImageDelete);
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition(), data.get(getAdapterPosition()));
            }
        }
    }
}
