package com.example.twisterfragments.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twisterfragments.Model.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;

public class AuthenticationViewModel extends ViewModel {
    private Application application;
    private FirebaseAuth firebaseAuth;
   // private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public AuthenticationViewModel(){

    }

    public AuthenticationViewModel (Application application){
        this.application = application;
     this.firebaseAuth = FirebaseAuth.getInstance();
     this.userLiveData = new MutableLiveData<>();
     this.loggedOutLiveData = new MutableLiveData<>();

// this will check if the user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }
    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

   // public MutableLiveData<FirebaseUser> getUserLiveData() {
       // return userLiveData;}


    private MutableLiveData<FirebaseUser> userLiveData;

    public LiveData<FirebaseUser> getUserLiveData() {

        if ( userLiveData== null) {
            userLiveData = new MutableLiveData<>();

            //getAndShowAllMessages();//Log.d(MESSAGE, "in getMessages : " + messages.toString());
        }
        return userLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            Log.d("register", "Current user is  " + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

}
