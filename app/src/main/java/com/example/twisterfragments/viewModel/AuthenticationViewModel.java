package com.example.twisterfragments.viewModel;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthenticationViewModel extends AndroidViewModel {
    private final Application application;
    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<Boolean> loggedOutLiveData;

    public AuthenticationViewModel (Application application){
        super(application);this.application = application;
     this.firebaseAuth = FirebaseAuth.getInstance();
     this.userLiveData = new MutableLiveData<>();
     this.loggedOutLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    private MutableLiveData<FirebaseUser> userLiveData;

    public LiveData<FirebaseUser> getUserLiveData() {
        if ( userLiveData== null) {
            userLiveData = new MutableLiveData<>();
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
                            Toast.makeText(application.getApplicationContext(), "You are logged in  " , Toast.LENGTH_SHORT).show();
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
