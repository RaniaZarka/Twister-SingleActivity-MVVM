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
    final static String AUTHVM = "authvm";
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

    public void register(String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            Log.d("register", "Current user is  " + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
                        } else {
                            String problem = "Problem is  " + task.getException().getMessage();
                            Log.e(AUTHVM, " the problem is: " + problem);
                        }
                    }
                });
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            Log.e(AUTHVM, " User is logged in  " );
                        } else {
                            String problem = "Login problem is  " + task.getException().getMessage();
                            Log.e(AUTHVM, " the problem is: " + problem);
                        }
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

}
