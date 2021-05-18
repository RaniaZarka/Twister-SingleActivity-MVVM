package com.example.twisterfragments.UI;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.twisterfragments.Model.Messages;
import com.example.twisterfragments.R;
import com.example.twisterfragments.ViewModel.AuthenticationViewModel;
import com.example.twisterfragments.ViewModel.MessageViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationFragment extends Fragment {
    private FirebaseAuth mAuth;
    EditText emailView;
    EditText paswordView;
    Button loginButton;
    Button registerButton;
    Button logoutButton;

    private static  final String TAG ="autoSignin";
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
        return inflater.inflate(R.layout.fragment_authentication, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
       // aViewModel.getUserLiveData().observe(this,Observer){

        //}
   /* observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Navigation.findNavController(getView()).navigate(R.id.nav_mesages);
                }
            }
        });*/
    }

    @Override
    public void onViewCreated (@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         emailView = (EditText)findViewById(R.id.mainEmail);
         paswordView = (EditText)findViewById(R.id.mainPasswordInput);
         loginButton = (Button) findViewById(R.id.mainSignInbtn);
         logoutButton=  (Button) findViewById(R.id.mainSignoutBtn);
         registerButton =(Button) findViewById(R.id.mainSignupBtn);

         registerButton.setOnClickListener(clickRegister);
         loginButton.setOnClickListener(clickLogin);
         logoutButton.setOnClickListener(clickLogout);

    }
    View.OnClickListener clickLogout = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {
            aViewModel.logOut();
        }

    };

    View.OnClickListener clickLogin = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {

            String email = emailView.getText().toString().trim();
            String password = paswordView.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
            } else {

                aViewModel.login(email, password);
            }
        }
    };


    View.OnClickListener clickRegister = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {

            String email = emailView.getText().toString().trim();
            String password = paswordView.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
            } else {

                aViewModel.register(email, password);
            }
        }
    };
    private View findViewById(int id) {
        return getView().findViewById(id);
    }
}