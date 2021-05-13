package com.example.twisterfragments.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.Repositories.MessageRepository;
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

    // Constructor
    public MessageViewModel() {
        mRepository = new MessageRepository();
        this.messageLiveData=  mRepository.getMessages();
    }

    public LiveData<List<Messages>> getAllMessages() {

        return messageLiveData;
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

