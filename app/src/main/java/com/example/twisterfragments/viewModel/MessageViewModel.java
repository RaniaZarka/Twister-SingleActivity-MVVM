package com.example.twisterfragments.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twisterfragments.model.Comments;
import com.example.twisterfragments.model.Messages;

import com.example.twisterfragments.webApiServices.ApiServices;
import com.example.twisterfragments.webApiServices.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {

    final static String MESSAGE = "message";
    private Messages selectedMessage;

    public void setMessages(Messages message) {
        selectedMessage = message;
    }

    public Messages getSelected() {
        return selectedMessage;
    }

    // Constructor
    public MessageViewModel() {

    }

    private MutableLiveData<String> errorMessage;
    public LiveData<String> getErrorMessage(){
        if (errorMessage == null) {
            errorMessage = new MutableLiveData<>();
            getAndShowAllMessages();
            Log.d(MESSAGE, "in getErrorMessages : " + errorMessage.toString());
        }
        return errorMessage;
    }


    public MutableLiveData<List<Messages>> messages;

    public MutableLiveData<List<Messages>> getMessages() {
            messages = new MutableLiveData<>();
            getAndShowAllMessages();
            Log.d(MESSAGE, "in getMessages : " + messages.toString());
        return messages;
    }

    public void getAndShowAllMessages() {
        if(errorMessage != null){
            errorMessage.setValue("");
        }
        ApiServices services = ApiUtils.getMessagesService();
        Call<List<Messages>> getAllMessagesCall = services.getAllMessages();
        getAllMessagesCall.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                Log.d(MESSAGE, response.raw().toString());
                if (response.isSuccessful()) {
                    messages.setValue(response.body());
                    Log.d(MESSAGE, " the messages are " + messages.getValue());
                } else {
                    String errorMessageString = response.code() + " " + response.message();
                    errorMessage.setValue(errorMessageString);
                    Log.d(MESSAGE, "the problem is: " + errorMessageString);
                }
            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {
                Log.e(MESSAGE, t.getMessage());
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void uploadMessage(Messages message) {
        if(errorMessage != null){
            errorMessage.setValue("");
        }
        ApiServices services = ApiUtils.getMessagesService();
        Call<Messages> saveNewMessageCall = services.saveMessage(message);
        saveNewMessageCall.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                if (response.isSuccessful()) {
                    Messages newMessage = response.body();
                    Log.d(MESSAGE, "the new message is: " + newMessage.toString());
                } else {
                    String problem =  response.code() + " " + response.message();
                    Log.e(MESSAGE, " the problem is: " + problem);
                    errorMessage.setValue(problem);
                    Log.d(MESSAGE, "the problem is: " + problem);
                }
            }
            @Override
            public void onFailure(Call<Messages> call, Throwable t) {
                Log.e(MESSAGE, t.getMessage());
                errorMessage.setValue(t.getMessage());
            }
        });
    }



    private MutableLiveData<List<Comments>> comments;

    public LiveData<List<Comments>> getComments() {
            comments = new MutableLiveData<>();
            getAndShowAllComments();
            Log.d(MESSAGE, "in getMessages : " + comments.toString());

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
                    Log.d("addComment", "list of all comments: " + comments.getValue());

                } else {
                    String message = response.code() + " " + response.message();
                    Log.d("addComment", "problem showing: " + message);
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                Log.e("AddComment", "on failure showing " + t.getMessage());
            }
        });
    }

    public void UploadComment(int messageId, Comments comment) {

        ApiServices services = ApiUtils.getMessagesService();
        Call<Comments> saveNewCommentCall = services.saveCommentBody(messageId, comment);
        Log.d("addComment", "the whole Comment object is " + comment.toString());

        saveNewCommentCall.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                if (response.isSuccessful()) {
                    Comments newComment = response.body();
                    Log.d("addComment", "the new comment is: " + newComment.toString());

                } else {
                    String problem = "Problem is  " + response.code() + " " + response.message();
                    Log.e("addComment", problem);
                }
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {

                Log.e("addComment", t.getMessage());
            }
        });
    }


}




