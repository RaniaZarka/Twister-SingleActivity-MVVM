package com.example.twisterfragments.ui;

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
import com.example.twisterfragments.viewModel.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class SignInOutFragment extends Fragment {
    private FirebaseAuth mAuth;
    EditText emailView;
    EditText paswordView;
    Button loginButton;
    Button logoutButton;
    Button register;

    private AuthenticationViewModel aViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in_out, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        aViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        aViewModel.getUserLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if( firebaseUser !=null){
                logoutButton.setEnabled(true);
            } else {
                logoutButton.setEnabled(false);

            }

      });
    }

    @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         emailView = (EditText)findViewById(R.id.mainEmail);
         paswordView = (EditText)findViewById(R.id.mainPasswordInput);

         register = (Button)findViewById(R.id.mainSignupBtn);
         loginButton = (Button) findViewById(R.id.mainSignInbtn);
         logoutButton=  (Button) findViewById(R.id.mainSignoutBtn);

         loginButton.setOnClickListener(clickLogin);
         logoutButton.setOnClickListener(clickLogout);
         register.setOnClickListener(clickRegister);

    }

    View.OnClickListener clickRegister = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {
            Navigation.findNavController(getView()).navigate(R.id.nav_register);


        }
    };
    View.OnClickListener clickLogout = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {
           if (mAuth.getCurrentUser() != null) {
                aViewModel.logOut();
                Toast.makeText(getContext(), "Your Are Signed Out", Toast.LENGTH_LONG).show();
            }
        }
    };

    View.OnClickListener clickLogin = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {

            String email = emailView.getText().toString().trim();
            String password = paswordView.getText().toString().trim();
            Log.d("Login", "the email is " + email + " the password is " + password);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                } else {
                    aViewModel.login(email, password);
                    Log.d("Login", " calling login from VM: " + " the email is " + email + "the password is " + password);
                }
            }
    };

    private View findViewById(int id) {
        return getView().findViewById(id);
    }
}