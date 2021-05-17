package com.example.twisterfragments.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.twisterfragments.Model.Comments;
import com.example.twisterfragments.Model.Messages;

import com.example.twisterfragments.UI.MessagesFragment;
import com.example.twisterfragments.WebApiServices.ApiServices;
import com.example.twisterfragments.WebApiServices.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends ViewModel {

    final static String MESSAGE = "message";
    //private LiveData<List<Messages>> messageLiveData;
    //private LiveData<List<Comments>> commentLiveData;


    private Messages selectedMessage;

    public void setMessages(Messages message) {
        selectedMessage=message;
    }

    public Messages getSelected() {
        return selectedMessage;
    }

    // Constructor
    public MessageViewModel() {
        // mRepository = new MessageRepository();
        // this.messageLiveData=  mRepository.getMessages();
        //this.commentLiveData = mRepository.getComments();
    }
    // public LiveData<List<Messages>> getAllMessages() { return messageLiveData; }
    //public LiveData<List<Comments>> getAllComments() { return commentLiveData; }

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

    }*/


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

