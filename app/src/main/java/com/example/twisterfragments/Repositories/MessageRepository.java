package com.example.twisterfragments.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twisterfragments.Adapters.RecyclerViewCommentAdapter;
import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.WebApi.ApiServices;
import com.example.twisterfragments.WebApi.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepository {
    final static String MESSAGE = "message";


    private static MessageRepository instance;
    //public MutableLiveData<List<Messages>> mMessages;

  /*public static  MessageRepository getInstance(){

    if(instance==null)
    {
        instance= new MessageRepository();
    }
    return instance;
   }*/

    public MessageRepository() {

       // mMessages = new MutableLiveData<>();
    }


    private MutableLiveData<List<Messages>> messages;


    public LiveData<List<Messages>> getMessages(){
        if(messages == null){
            messages = new MutableLiveData<>();
          getAndShowAllMessages();
            //messages.setValue( mMessages.getValue());
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
                    // List<Messages> allMessages = response.body();
                    //Log.d(MESSAGE, allMessages.toString());
                    //populateRecycleView(allMessages);
                    messages.setValue(response.body());
                    Log.d(MESSAGE, " the messages are " +messages.getValue());

                } else {
                    messages.setValue(null);
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
    }
}
