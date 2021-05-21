package com.example.twisterfragments.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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
import com.example.twisterfragments.model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.adapters.RecyclerViewMessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class MessagesFragment extends Fragment {
    FirebaseAuth mAuth;
    MessageViewModel mViewModel;
    RecyclerViewMessageAdapter mAdapter;
    Button addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);

    }
 @Override
 public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
     super.onActivityCreated(savedInstanceState);
     mViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
     mViewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
             if (messages != null) {
                 populateRecycleView(messages);
         }
     });
 }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getAndShowAllMessages();
    }

    @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = (TextView) findViewById(R.id.welcome);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userfb = mAuth.getCurrentUser();
        if (userfb == null) {
            text.setText("Welcome ");
        } else {
            String user= mAuth.getCurrentUser().getEmail();

            text.setText("Welcome, " +  user);}

        addButton = (Button) findViewById(R.id.AllMessagesAddBtn);
        addButton.setOnClickListener(addclick);

    }
    View.OnClickListener addclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText input = (EditText) findViewById(R.id.messageInput);
            String content = input.getText().toString().trim();
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser userfb = mAuth.getCurrentUser();
            if (userfb == null) {
                Toast.makeText(getContext(), "You did to sign in first", Toast.LENGTH_SHORT).show();
            } else {
                String email = mAuth.getCurrentUser().getEmail();
                if (content.isEmpty()) {
                    Toast.makeText(getContext(), "You did not write a message", Toast.LENGTH_SHORT).show();

                } else {
                    Messages message = new Messages(content, email);
                    Log.d("addMessage", " in else the message is  " + message);
                    mViewModel.uploadMessage(message);
                    mAdapter.addMessage(message);
                }
            }
        }
    };

    private void populateRecycleView(List<Messages> allMessages) {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
         mAdapter= new RecyclerViewMessageAdapter(requireContext(), allMessages);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener((view, position, item) -> {
            Messages message = item;
            if (getView() != null) {
                mViewModel.setMessages(message);
                Navigation.findNavController(getView()).navigate(R.id.nav_comments);
            }
        });
    }

    private View findViewById(int id) {
        return getView().findViewById(id);
    }

}