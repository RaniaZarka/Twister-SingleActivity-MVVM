package com.example.twisterfragments.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twisterfragments.ViewModel.MessageViewModel;
import com.example.twisterfragments.Model.Comments;
import com.example.twisterfragments.R;
import com.example.twisterfragments.Adapters.RecyclerViewCommentAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class CommentsFragment extends Fragment  {

    private TextView messageTextView;
    FirebaseAuth mAuth;
    private TextView comment;

    private Comments theComment;
    //private ImageButton imageButton;
    //public static final String Email = "user";
    //FirebaseAuth fAuth;
    public static final String COMMENT = "comment";
    RecyclerViewCommentAdapter adapter;
    MessageViewModel mViewModel;
   // private Layout layout;

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
        mViewModel.getComments().observe(getViewLifecycleOwner(), comments ->{

                if (comments != null) {

               populateRecycleView(comments);}
        });
        messageTextView.setText(mViewModel.getSelected().getContent());
    }

   @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       messageTextView = (TextView) findViewById(R.id.commentOriginalMessage);
       Button addButton = (Button) findViewById(R.id.CommentAddBtn);
       addButton.setOnClickListener(click);
    }

    private void populateRecycleView(List<Comments> allComments) {
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecyclerViewCommentAdapter(requireContext(), allComments);
        recyclerView.setAdapter(adapter);
        //private void populateRecycleView(List<Messages> allMessages) {
            //adapter.addComments(allComments);
        }

      /*  adapter.setClickListener((view, position, item) -> {
            theComment = item;
           /* if (position >= 0) {
                DeleteComment(position);*/
    /* Log.d("delete", "position is: "+ position);
            Log.d("delete", "the comment to delete is:  " + item.toString());}
        );*/
    //}

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText input = (EditText) findViewById(R.id.commentInput);
            String content = input.getText().toString().trim();
            Log.d(COMMENT, " the content is  " +content);
            //String email  = input.getText().toString().trim();
            mAuth = FirebaseAuth.getInstance();
            String email = mAuth.getCurrentUser().getEmail();
            int messageId = mViewModel.getSelected().getId();
            Log.d(COMMENT, " the id is   " + messageId);
            if (content.isEmpty()) {
                Toast.makeText(getContext(),"You did not write a comment" , Toast.LENGTH_SHORT).show();

            } else {
                Comments comment = new Comments(messageId, content, email );
                Log.d(COMMENT, " the comment is  " + comment);
                mViewModel.UploadComment( messageId, comment);
                adapter.addComment(comment);
            }
        }
    };

    private View findViewById(int id) {
        return getView().findViewById(id);}

}

