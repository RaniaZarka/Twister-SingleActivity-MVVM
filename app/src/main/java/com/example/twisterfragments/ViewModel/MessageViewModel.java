package com.example.twisterfragments.ViewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twisterfragments.Model.Comments;
import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.Repositories.MessageRepository;
import com.example.twisterfragments.UI.MessagesFragment;
import com.example.twisterfragments.WebApi.ApiServices;
import com.example.twisterfragments.WebApi.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {

    final static String MESSAGE = "message";
    private MessageRepository mRepository;
    private LiveData<List<Messages>> messageLiveData;
    private LiveData<List<Comments>> commentLiveData;
    MessagesFragment messagesFragment;

    // Constructor
    public MessageViewModel() {
        mRepository = new MessageRepository();
        this.messageLiveData=  mRepository.getMessages();
        this.commentLiveData = mRepository.getComments();
        //mRepository.uploadMessage();
    }

    private final MutableLiveData<Messages> selectedMessage = new MutableLiveData<Messages>();

    public void select(Messages message) {
        selectedMessage.setValue(message);
    }

    public LiveData<Messages> getSelected() {
        return selectedMessage;
    }

    public LiveData<List<Messages>> getAllMessages() {

        return messageLiveData;
    }

    /*public LiveData<List<Comments>> getAllComments(){
        return commentLiveData;
    }*/

     public  void addMessage(Messages message){

     }
    //public void addMessage(View view) {
    public void uploadMessage(Messages message ) {

        /*fAuth = FirebaseAuth.getInstance();
        FirebaseUser userfb = fAuth.getCurrentUser();
        if (userfb == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.NotSignedIn), Toast.LENGTH_SHORT).show();
        } else {
            String user = fAuth.getCurrentUser().getEmail();*/
        // EditText input = (EditText) view.findViewById(R.id.messageInput);
        //String content = input.getText().toString().trim();
        ApiServices services = ApiUtils.getMessagesService();
        //Messages message = new Messages();
        Call<Messages> saveNewMessageCall = services.saveMessage(message);
       /* if (content.isEmpty()) {
            Toast.makeText(getContext(), "You did not enter a message", Toast.LENGTH_SHORT).show();
        } else*/
        saveNewMessageCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()) {
                    Messages newMessage = response.body();
                    Log.d(MESSAGE, "the new message is: " + newMessage.toString());

                    // Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                    // input.setText("");
                    // LinearLayout layout = (LinearLayout) findViewById(R.id.allMessagesAddLayout);
                    // layout.setVisibility(View.INVISIBLE);
                    // the following codes is to make an autorefresh so the added message shows right away
                    //recreate();
                } else {
                    String problem = "Problem is  " + response.code() + " " + response.message();
                    Log.e(MESSAGE, " the problem is: " + problem);
                    //Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                //Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e(MESSAGE, t.getMessage());
            }
        });
    }

        private MutableLiveData<List<Comments>> comments;

        public LiveData<List<Comments>> getComments () {
            if (comments == null) {
                comments = new MutableLiveData<>();
                Comments comment = new Comments();
                getAndShowAllComments(comment);
                //messages.setValue( mMessages.getValue());
                Log.d(MESSAGE, "in getMessages : " + comments.toString());
            }
            return comments;
        }

   public void getAndShowAllComments(Comments comment) {
      // Bundle bundle = this.getArguments();
       // int Id = bundle.getInt(ID);
       // Log.d("addMessage", "the message id is: " + Id);
        //
        //Comments comment = new Comments();
        ApiServices services = ApiUtils.getMessagesService();

        //Call<List<Comments>> getAllCommentsCall = services.getCommentById(Id);
        Call<List<Comments>> getAllCommentsCall = services.getCommentById(comment.getMessageId());

        Log.d("addMessage", "calling all the messge: " + getAllCommentsCall.toString());
        //comment = (TextView)findViewById(R.id.messageMessages);

        getAllCommentsCall.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                Log.d("addMessage", "the response is: " + response.raw().toString());

                if (response.isSuccessful()) {
                    List<Comments> allComments = response.body();

                    Log.d("addMessage", "list of all comments: " + allComments.toString());
                   // populateRecycleView(allComments);
                } else {
                    String message =  response.code() + " " + response.message();
                    Log.d("addMessage", "problem showing: " + message);
                   // comment.setText(message);
                }
            }
            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {

                Log.e("AddMessage", "on failure showing " + t.getMessage());
                //comment.setText(t.getMessage());
            }
        });
    }

    }




   /* public void getAndShowAllMessages() {
        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Messages>> getAllMessagesCall = services.getAllMessages();

        getAllMessagesCall.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                Log.d(MESSAGE, response.raw().toString());

                if (response.isSuccessful()) {
                    messages.setValue(response.body());
                    Log.d(MESSAGE, String.valueOf(messages.getValue()));
                } else {
                    messages.setValue(null);
                    String message =response.code() + " " + response.message();
                    Log.d(MESSAGE, "the problem is: " + message);
                }
            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {
                Log.e(MESSAGE, t.getMessage());
                //viewMessage.setText(t.getMessage());
            }
        });
    }

    }


   /* public void getAndShowAllMessages() {
        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Messages>> getAllMessagesCall = services.getAllMessages();

        getAllMessagesCall.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                Log.d(MESSAGE, response.raw().toString());

                if (response.isSuccessful()) {
                    // List<Messages> allMessages = response.body();
                    //Log.d(MESSAGE, allMessages.toString());
                    //populateRecycleView(allMessages);
                    messages.setValue(response.body());
                    Log.d(MESSAGE, messages.toString());

                } else {
                    //mMessages.setValue(null);
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

