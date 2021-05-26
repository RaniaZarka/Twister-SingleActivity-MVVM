package com.example.twisterfragments.ui;

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

import com.example.twisterfragments.viewModel.MessageViewModel;
import com.example.twisterfragments.model.Comments;
import com.example.twisterfragments.R;
import com.example.twisterfragments.adapters.RecyclerViewCommentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class CommentsFragment extends Fragment  {

    private TextView messageTextView;

    FirebaseAuth mAuth;
    public static final String COMMENT = "comment";
    RecyclerViewCommentAdapter adapter;
    MessageViewModel mViewModel;


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

        mViewModel.getComments().observe(getViewLifecycleOwner(), comments -> {
            if (comments != null) {
                populateRecycleView(comments);
            }
        });
      /*  mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (!errorMessage.isEmpty()) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });*/


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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecyclerViewCommentAdapter(requireContext(), allComments);
        recyclerView.setAdapter(adapter);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText input = (EditText) findViewById(R.id.commentInput);
            String content = input.getText().toString().trim();
            Log.d(COMMENT, " the content is  " +content);
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser userfb = mAuth.getCurrentUser();
            if (userfb == null) {
                Toast.makeText(getContext(), "You did to sign in first", Toast.LENGTH_SHORT).show();
            } else {
                String email = mAuth.getCurrentUser().getEmail();
                int messageId = mViewModel.getSelected().getId();
                Log.d(COMMENT, " the id is   " + messageId);
                if (content.isEmpty()) {
                    Toast.makeText(getContext(), "You did not write a comment", Toast.LENGTH_SHORT).show();

                } else {
                    Comments comment = new Comments(messageId, content, email);
                    Log.d(COMMENT, " the comment is  " + comment);
                    mViewModel.UploadComment(messageId, comment);
                    adapter.addComment(comment);
                }
            }
        }
    };

    private View findViewById(int id) {
        return getView().findViewById(id);}

}

