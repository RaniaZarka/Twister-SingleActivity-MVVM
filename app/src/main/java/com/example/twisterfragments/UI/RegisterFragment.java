package com.example.twisterfragments.UI;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twisterfragments.R;
import com.example.twisterfragments.ViewModel.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    EditText emailView;
    EditText paswordView;
    Button registerButton;
    private AuthenticationViewModel aViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        aViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        aViewModel.getUserLiveData();
            }

    @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailView = (EditText)findViewById(R.id.registerEmail);
        paswordView = (EditText)findViewById(R.id.registerPasswordInput);

        registerButton =(Button) findViewById(R.id.registerSignupBtn);
        registerButton.setOnClickListener(clickRegister);

    }
    View.OnClickListener clickRegister = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {

            String email = emailView.getText().toString().trim();
            String password = paswordView.getText().toString().trim();
            Log.d("Register", "the email is " + email + " the password is " + password);

            //if (mAuth.getCurrentUser() == null) {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("Register", "Current user is  " + user);
                    aViewModel.register(email, password);
                    Toast.makeText(getContext(), "Welcome to TWISTER-PM", Toast.LENGTH_LONG).show();
                }
            }
        
    };
    private View findViewById(int id) {
        return getView().findViewById(id);
    }
}