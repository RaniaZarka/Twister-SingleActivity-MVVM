package com.example.twisterfragments.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.WebApi.ApiServices;
import com.example.twisterfragments.WebApi.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMessageFragment extends Fragment {

    public static final String MESSAGE = "message";

   // Messages theMessage = new Messages(content);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //addMessage(theMessage);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_message, container, false);
    }

    public void addMessage(View view) {

        /*fAuth = FirebaseAuth.getInstance();
        FirebaseUser userfb = fAuth.getCurrentUser();
        if (userfb == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.NotSignedIn), Toast.LENGTH_SHORT).show();
        } else {
            String user = fAuth.getCurrentUser().getEmail();*/
        EditText input = (EditText) findViewById(R.id.messageInput);
        String content = input.getText().toString().trim();
            ApiServices services = ApiUtils.getMessagesService();
            Messages message = new Messages(content);
            Call<Messages> saveNewMessageCall = services.saveMessage(message);
            if (content.isEmpty()) {
                Toast.makeText(getContext(), "You did not enter a message", Toast.LENGTH_SHORT).show();
            } else
                saveNewMessageCall.enqueue(new Callback<Messages>() {
                    @Override
                    public void onResponse(Call<Messages> call, Response<Messages> response) {
                        if (response.isSuccessful()) {
                            Messages newMessage = response.body();
                            Log.d(MESSAGE, "the new message is: " + newMessage.toString());
                           Toast.makeText(getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                            input.setText("");
                            LinearLayout layout = (LinearLayout) findViewById(R.id.allMessagesAddLayout);
                            layout.setVisibility(View.INVISIBLE);
                            // the following codes is to make an autorefresh so the added message shows right away
                            //recreate();
                        } else {
                            String problem = "Problem is  " + response.code() + " " + response.message();
                            Log.e(MESSAGE, " the problem is: " + problem);
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Messages> call, Throwable t) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.e(MESSAGE, t.getMessage());
                    }
                });
        }

    private View findViewById(int id) {
        return getView().findViewById(id);}


}