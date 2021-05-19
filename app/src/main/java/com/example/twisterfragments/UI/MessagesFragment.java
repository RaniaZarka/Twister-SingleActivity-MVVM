package com.example.twisterfragments.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twisterfragments.ViewModel.MessageViewModel;
import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.Adapters.RecyclerViewMessageAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment implements View.OnTouchListener, RecyclerViewMessageAdapter.ItemClickListener {

    public static final String MESSAGE = "message";
    public static final String ID = "id";
    public static final String Email = "user";

    FirebaseAuth mAuth;

    RecyclerView recyclerView;
    MessageViewModel mViewModel;
    Button addButton;

    RecyclerViewMessageAdapter mAdapter;
    ArrayList<Messages> messages= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onStart() {

        super.onStart();
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
     mViewModel.uploadTheMessage().observe(getViewLifecycleOwner(), messages -> {
         if (messages != null) {
             mAdapter.addMessage(messages);
             //populateRecycleView(messages);
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
        mAdapter = new RecyclerViewMessageAdapter(requireContext(),  this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);

        addButton = (Button) findViewById(R.id.AllMessagesAddBtn);
        addButton.setOnClickListener(click);

    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText input = (EditText) findViewById(R.id.messageInput);
            String content = input.getText().toString().trim();
            mAuth = FirebaseAuth.getInstance();
            String email = mAuth.getCurrentUser().getEmail();
            if (content.isEmpty()) {
                Toast.makeText(getContext(),"You did not write a message" , Toast.LENGTH_SHORT).show();

            } else {
                Messages message = new Messages(content, email);
                mViewModel.uploadMessage(message);



            }
        }
    };
    @Override
    public void onItemClick(Messages message) {
        if (getView() != null) {
            mViewModel.setMessages(message);
            Navigation.findNavController(getView()).navigate(R.id.nav_comments);
        }
    }

    private void populateRecycleView(List<Messages> allMessages) {
        mAdapter.addMessages(allMessages);
    }

    private View findViewById(int id) {
        return getView().findViewById(id);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

}