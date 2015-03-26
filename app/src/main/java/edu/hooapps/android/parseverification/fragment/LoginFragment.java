package edu.hooapps.android.parseverification.fragment;


import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.hooapps.android.parseverification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    // Callback interface to pass events to the parent activity
    public interface OnLoginEventListener {
        public void onLoginClick(String username, String password);
        public void onSignUpClick();
    }
    public OnLoginEventListener mCallback;

    // Private fields for the Views in the Fragment
    private EditText userText;
    private EditText passText;
    private Button loginButton;
    private Button signUpButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    // Set the parent activity as mCallback
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure the activity implements the correct interface
        try {
            mCallback = (OnLoginEventListener) activity;
        } catch (ClassCastException e) {
            Log.e("Login", activity + " must implement OnLoginEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Assign the private views in the fragment
        userText = (EditText) rootView.findViewById(R.id.username);
        passText = (EditText) rootView.findViewById(R.id.password);
        loginButton = (Button) rootView.findViewById(R.id.button_login);
        signUpButton = (Button) rootView.findViewById(R.id.button_sign_up);

        // Bind the listeners to the buttons
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLoginFields();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Callback to the parent activity to switch fragments
                mCallback.onSignUpClick();
            }
        });

        return rootView;
    }

    // Check to make sure valid username and password provided
    // This does NOT log the user in to Parse, it just handles input parameters
    private void validateLoginFields() {
        // Retrieve the info from the Views
        String username = userText.getText().toString();
        String password = passText.getText().toString();

        // Check if either the username or password is empty
        boolean isValid = true;
        if (username.isEmpty()) {
            showErrorHighlighting(userText);
            isValid = false;
        }
        if (password.isEmpty()) {
            showErrorHighlighting(passText);
            isValid = false;
        }

        if (isValid) {
            // Callback to the activity to login the user with Parse
            mCallback.onLoginClick(username, password);
        }

    }

    // Abstracted method for displaying red underline on EditText
    private void showErrorHighlighting(EditText view) {
        view.getBackground().setColorFilter(getResources().getColor(R.color.error_red),
                PorterDuff.Mode.SRC_ATOP);
    }

}