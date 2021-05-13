package com.example.twisterfragments.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.twisterfragments.R;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarToolbar);
        setSupportActionBar(toolbar);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_mesages, R.id.nav_add, R.id.nav_search, R.id.nav_signin).build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

    }

    // this is for the back from comment to message fragment
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.messages_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);}*/

       //switch (item.getItemId()) {
           // case R.id.nav_mesages:
               // Navigation.findNavController(this,R.id.messagesFragment);
                //Fragment messagesFragment = new MessagesFragment();

               // return true;

            //case R.id.nav_profile:
              /*  mAuth = FirebaseAuth.getInstance();
                FirebaseUser userfb = mAuth.getCurrentUser();
                if (userfb == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.NotSignedIn), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);}*/
              /*  Toast.makeText(getApplicationContext(), "not coded yet", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_search:


                 Toast.makeText(getApplicationContext(), "not coded yet", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nav_signin:
                Toast.makeText(getApplicationContext(), "no sign in yet", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}