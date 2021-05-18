package com.example.twisterfragments.ViewModel;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.twisterfragments.Model.Comments;
import com.example.twisterfragments.Model.Messages;

import com.example.twisterfragments.UI.MessagesFragment;
import com.example.twisterfragments.WebApiServices.ApiServices;
import com.example.twisterfragments.WebApiServices.ApiUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {

    final static String MESSAGE = "message";

    private Messages selectedMessage;
    public void setMessages(Messages message) {
        selectedMessage=message;
    }
    public Messages getSelected() {
        return selectedMessage;
    }

    // Constructor
    public MessageViewModel() {

    }

    private MutableLiveData<List<Messages>> messages;

    public LiveData<List<Messages>> getMessages(){
        if(messages == null){
            messages = new MutableLiveData<>();
            getAndShowAllMessages();
            Log.d(MESSAGE, "in getMessages : " + messages.toString());
        }
        return messages;
    }

    public void getAndShowAllMessages() {
        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Messages>> getAllMessagesCall = services.getAllMessages();
        getAllMessagesCall.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                Log.d(MESSAGE, response.raw().toString());
                if (response.isSuccessful()) {
                    messages.setValue(response.body());
                    Log.d(MESSAGE, " the messages are " +messages.getValue());
                } else {
                    messages.setValue(null);
                    String message =response.code() + " " + response.message();
                    Log.d(MESSAGE, "the problem is: " + message);
                }
            }
            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {
                Log.e(MESSAGE, t.getMessage());
            }
        });
    }


    public void uploadMessage(Messages message ) {

        ApiServices services = ApiUtils.getMessagesService();
        Call<Messages> saveNewMessageCall = services.saveMessage(message);
        saveNewMessageCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()) {
                    Messages newMessage = response.body();
                    Log.d(MESSAGE, "the new message is: " + newMessage.toString());

                } else {
                    String problem = "Problem is  " + response.code() + " " + response.message();
                    Log.e(MESSAGE, " the problem is: " + problem);
                }
            }
            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.e(MESSAGE, t.getMessage());
            }
        });
    }

        private MutableLiveData<List<Comments>> comments;

        public LiveData<List<Comments>> getComments () {
            if (comments == null) {
                comments = new MutableLiveData<>();
                getAndShowAllComments();
                Log.d(MESSAGE, "in getMessages : " + comments.toString());
            }
            return comments;
        }

   public void getAndShowAllComments() {

        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Comments>> getAllCommentsCall = services.getCommentById(selectedMessage.getId());
        Log.d("addMessage", "calling all the messge: " + getAllCommentsCall.toString());

        getAllCommentsCall.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                Log.d("addMessage", "the response is: " + response.raw().toString());
                if (response.isSuccessful()) {
                    comments.setValue(response.body());
                    Log.d("addMessage", "list of all comments: " + comments.getValue());

                } else {
                    String message =  response.code() + " " + response.message();
                    Log.d("addMessage", "problem showing: " + message);
                }
            }
            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Log.e("AddMessage", "on failure showing " + t.getMessage());
            }
        });
    }
    public void UploadComment(int messageId, Comments comment) {

            ApiServices services = ApiUtils.getMessagesService();
            Call<Comments> saveNewCommentCall = services.saveCommentBody(messageId, comment);
            Log.d("addMessage", "the whole Comment object is " + comment.toString());

                    saveNewCommentCall.enqueue(new Callback<Comments>() {
                        @Override
                        public void onResponse(Call<Comments> call, Response<Comments> response) {
                            if (response.isSuccessful()) {
                                Comments newComment = response.body();
                                Log.d("addMessage", "the new comment is: " + newComment.toString());

                            } else {
                                String problem =  "Problem is  " + response.code() + " " + response.message();
                                Log.e("addMessage", problem);
                            }
                        }
                        @Override
                        public void onFailure(Call<Comments> call, Throwable t) {

                            Log.e("addMessage", t.getMessage());
                        }
                    });}



    }




