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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.twisterfragments.Repositories.MessageRepository;
import com.example.twisterfragments.ViewModel.MessageViewModel;
import com.example.twisterfragments.WebApi.ApiServices;
import com.example.twisterfragments.WebApi.ApiUtils;
import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.Adapters.RecyclerViewMessageAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagesFragment extends Fragment implements View.OnTouchListener {

    public static final String MESSAGE = "message";
    public static final String ID = "id";
    private TextView viewMessage;
    private View messagesLayout;
    public static final String Email = "user";
    RecyclerView recyclerView;
    MessageViewModel mViewModel;
    MessageRepository mRepository;
    RecyclerViewMessageAdapter mAdapter;


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
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
                //ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()).create(MessageViewModel.class);
        mViewModel.getAllMessages().observe(getViewLifecycleOwner(), new Observer<List<Messages>>() {
             @Override
             public void onChanged(List<Messages> messages) {
                 if (messages != null) {
                     populateRecycleView(mViewModel.getAllMessages().getValue());

                    // mAdapter.notifyDataSetChanged();
                 }
             }
         });

    }

    private void populateRecycleView(List<Messages> allMessages) {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerViewMessageAdapter adapter= new RecyclerViewMessageAdapter(requireContext(), allMessages);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener((view, position, item) -> {
            Messages message = (Messages) item;

            //CommentsFragment fragment = new CommentsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MESSAGE, message.getContent());
            bundle.putSerializable(ID, message.getId());
            //fragment.setArguments(bundle);
            //NavDirections action = MessagesFragmentDirections.actionMessagesFragmentToCommentsFragment();

            if (getView() != null) {

                Navigation.findNavController(getView()).navigate(R.id.nav_comments, bundle);
            }
            Log.d(MESSAGE, "item is: " + item.toString());

        });

    }
    private View findViewById(int id) {
        return getView().findViewById(id);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

   /* public void getAndShowAllMessages() {
        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Messages>> getAllMessagesCall = services.getAllMessages();

        getAllMessagesCall.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                Log.d(MESSAGE, response.raw().toString());

                if (response.isSuccessful()) {
                    List<Messages> allMessages = response.body();
                    Log.d(MESSAGE, allMessages.toString());
                    populateRecycleView(allMessages);
                    //mMessages.setValue(response.body());
                   // Log.d(MESSAGE, mMessages.toString());

                } else {
                   // mMessages.setValue(null);
                    String message =response.code() + " " + response.message();
                    Log.d(MESSAGE, "the problem is: " + message);
                    //viewMessage.setText(message);
                }
            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {
                Log.e(MESSAGE, t.getMessage());
                //viewMessage.setText(t.getMessage());
            }
        });
    }*/
}