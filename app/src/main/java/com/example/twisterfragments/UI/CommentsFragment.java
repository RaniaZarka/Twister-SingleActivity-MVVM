package com.example.twisterfragments.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.ViewModel.MessageViewModel;
import com.example.twisterfragments.WebApi.ApiServices;
import com.example.twisterfragments.WebApi.ApiUtils;
import com.example.twisterfragments.Model.Comments;
import com.example.twisterfragments.R;
import com.example.twisterfragments.Adapters.RecyclerViewCommentAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentsFragment extends Fragment  {

    private static final String ID = "id";
    private TextView message;
    private TextView comment;
   // private TextView user;
   // private Messages theMessage;
    private Comments theComment;
    //private ImageButton imageButton;
    //public static final String Email = "user";
    //FirebaseAuth fAuth;
    public static final String MESSAGE = "message";
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
           // @Override
            //public void onChanged(List<Comments> comments) {
                if (comments != null) {
                    populateRecycleView(comments);
                }
        });
        message.setText(mViewModel.getSelected().getContent());
    }

   @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       message = (TextView) findViewById(R.id.commentOriginalMessage);
    }

    private void populateRecycleView(List<Comments> allComments) {
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.commentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecyclerViewCommentAdapter(requireContext(), allComments);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener((view, position, item) -> {
            theComment = item;
           /* if (position >= 0) {
                DeleteComment(position);*/
            Log.d("delete", "position is: "+ position);
            Log.d("delete", "the comment to delete is:  " + item.toString());}
        );
    }

    private View findViewById(int id) {
        return getView().findViewById(id);}

}

   /* public void getAllComments() {
        Bundle bundle = this.getArguments();
        int Id = bundle.getInt(ID);
        Log.d("addMessage", "the message id is: " + Id);
        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Comments>> getAllCommentsCall = services.getCommentById(Id);
        Log.d("addMessage", "calling all the messge: " + getAllCommentsCall.toString());
        comment = (TextView)findViewById(R.id.messageMessages);

        getAllCommentsCall.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                Log.d("addMessage", "the response is: " + response.raw().toString());

                if (response.isSuccessful()) {
                    List<Comments> allComments = response.body();

                    Log.d("addMessage", "list of all comments: " + allComments.toString());
                    populateRecycleView(allComments);
                } else {
                    String message =  response.code() + " " + response.message();
                    Log.d("addMessage", "problem showing: " + message);
                    comment.setText(message);
                }
            }
            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {

                Log.e("AddMessage", "on failure showing " + t.getMessage());
                comment.setText(t.getMessage());
            }
        });
    }
*/

