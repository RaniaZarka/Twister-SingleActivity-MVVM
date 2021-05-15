package com.example.twisterfragments.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.savedstate.SavedStateRegistry;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.twisterfragments.Repositories.MessageRepository;
import com.example.twisterfragments.ViewModel.MessageViewModel;
import com.example.twisterfragments.WebApi.ApiServices;
import com.example.twisterfragments.WebApi.ApiUtils;
import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.Adapters.RecyclerViewMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesFragment extends Fragment implements View.OnTouchListener, RecyclerViewMessageAdapter.ItemClickListener {

    public static final String MESSAGE = "message";
    public static final String ID = "id";

    public static final String Email = "user";
    RecyclerView recyclerView;
    MessageViewModel mViewModel;
    MessageRepository mRepository;
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
        //UpdateRecyclerView(messages);
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
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new RecyclerViewMessageAdapter(requireContext(),  this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(mAdapter);
        Button addButton = (Button) findViewById(R.id.AllMessagesAddBtn);
        addButton.setOnClickListener(click);


    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText input = (EditText) findViewById(R.id.messageInput);
            String content = input.getText().toString().trim();
            String email = "Rania@gmail.com";
            Messages message = new Messages(content, email);
            mViewModel.uploadMessage(message);
            mAdapter.addMessage(message);

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