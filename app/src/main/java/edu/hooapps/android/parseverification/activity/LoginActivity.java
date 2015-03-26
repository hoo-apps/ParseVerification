package edu.hooapps.android.parseverification.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import edu.hooapps.android.parseverification.R;
import edu.hooapps.android.parseverification.fragment.LoginFragment;
import edu.hooapps.android.parseverification.fragment.SignUpFragment;


public class LoginActivity extends ActionBarActivity implements
        LoginFragment.OnLoginEventListener,
        SignUpFragment.OnSignUpEventListener {

    private Context context;

    // Static method to help launch the LoginActivity
    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    // Check the provided login credentials on parse
    @Override
    public void onLoginClick(String username, final String password) {
        // Try logging in the User with Parse
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    // Login success, launch the main app
                    MainActivity.startMainActivity(context);
                    finish();
                } else {
                    // Login failed
                    Log.d("TAG", e.getLocalizedMessage());
                }
            }
        });
    }
    /*==========================================================================================
        iOS Code Equivalent
    --------------------------------------------------------------------------------------------
    // OBJECTIVE-C
    - (void) onLoginClick:(NSString*)username :(NSString*)password {
        [PFUser logInWithUsernameInBackground:username password:password
            block:^(PFUser *user, NSError *error) {
            if (user) {
                // Login successful
            } else {
                // Login failed, display error
            }
    }

    ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    // SWIFT
    func onLoginClick(username: String, password: String) {
        PFUser.logInWithUsernameInBackground(username, password:password) {
            (user: PFUser!, error:NSError!) -> Void In
            if user != null {
                // Login successful
            } else {
                // Login failed, display error
            }
        }
    }
    ==========================================================================================*/


    // Swap fragments to the SignUpFragment to allow new users to register
    @Override
    public void onSignUpClick() {
        swapFragment(new SignUpFragment());
    }

    @Override
    public void onUserSignUp(String username, String password, String email) {
        // Create a new ParseUser with the login info
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Sign up the ParseUser in the background
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Success, switch back to the LoginFragment
                    swapFragment(new LoginFragment());
                } else {
                    // Failure
                    // NOTE: Parse requires unique emails for users
                }
            }
        });
    }
    /*==========================================================================================
        iOS Code Equivalent
    --------------------------------------------------------------------------------------------
    // OBJECTIVE-C
    - (void) onUserSignUp:(NSString*)username :(NSString*)password :(NSString*)email {
        PFUser *user = [PFUser user];
        user.username = username;
        user.password = password;
        user.email = email;

        [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
            if (!error) {
                // Success, user has been signed up
            } else {
                // Failure, check error for more info
            }
        }];
    }

    ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    // SWIFT
    func onUserSignUp(username: String, password: String, email: String) {
        var user = PFUser()
        user.username = username
        user.password = password
        user.email = email

        user.signUpInBackgroundWithBlock {
            (succeeded: Bool!, error: NSError!) -> Void in
            if error == nil {
                // Success, user has been signed up
            } else {
                let errorString = error.userInfo["error"] as NSString
                // Failure, check error for more info
            }
        }
    }
    ==========================================================================================*/

    // Helper method to swap generic Fragments
    private void swapFragment(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, frag);
        ft.commit();
    }
}