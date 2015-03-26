package edu.hooapps.android.parseverification.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import edu.hooapps.android.parseverification.R;


public class MainActivity extends ActionBarActivity {

    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the current ParseUser from cache
        ParseUser currentUser = ParseUser.getCurrentUser();

        /*==========================================================================================
            iOS Code Equivalent
        --------------------------------------------------------------------------------------------
        // OBJECTIVE-C
        PFUser *currentUser = [PFUser currentUser];

        ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // SWIFT
        var currentUser = PFUser.currentUser()

        ==========================================================================================*/

        TextView userGreetingText = (TextView) findViewById(R.id.user_greeting);
        userGreetingText.setText("Welcome " + currentUser.getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Configure the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Check for the logout event
        if (id == R.id.action_logout) {
            // Logout the CurrentUser
            ParseUser.logOut();

            /*=====================================================================================
                iOS Code Equivalent
            ---------------------------------------------------------------------------------------
            // OBJECTIVE-C
            [PFUser logout];

            ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
            // SWIFT
            PFUser.logOut()

            ======================================================================================*/

            // Return to the login screen
            LoginActivity.startLoginActivity(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
