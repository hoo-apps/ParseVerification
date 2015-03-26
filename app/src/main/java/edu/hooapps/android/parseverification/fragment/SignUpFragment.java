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

public class SignUpFragment extends Fragment {

    // Callback interface to pass events to the parent activity
    public interface OnSignUpEventListener {
        public void onUserSignUp(String username, String password, String email);
    }
    public OnSignUpEventListener mCallback;

    // Private fields for the Views in the Fragment
    private EditText userText;
    private EditText passText;
    private EditText passReText;
    private EditText emailText;
    private Button signUpButton;

    public SignUpFragment() {
        // Required empty public constructor
    }

    // Set the parent activity as mCallback
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Make sure the activity implements the correct interface
        try {
            mCallback = (OnSignUpEventListener) activity;
        } catch (ClassCastException e) {
            Log.e("TAG", activity + " must implement OnSignUpEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Assign the private views in the fragment
        userText = (EditText) rootView.findViewById(R.id.username);
        passText = (EditText) rootView.findViewById(R.id.password);
        passReText = (EditText) rootView.findViewById(R.id.re_password);
        emailText = (EditText) rootView.findViewById(R.id.email);
        signUpButton = (Button) rootView.findViewById(R.id.button_sign_up);

        // Bind the listener to the signUpButton
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSignUpFields();
            }
        });

        return rootView;
    }

    private void validateSignUpFields() {
        // Retrieve the info from the Views
        String username = userText.getText().toString();
        String pass = passText.getText().toString();
        String repass = passReText.getText().toString();
        String email = emailText.getText().toString();

        // Make sure all fields are valid
        boolean isValid = true;
        if (username.isEmpty()) {
            // Handle empty username
            showErrorHighlighting(userText);
            isValid = false;
        }
        if (pass.isEmpty() || repass.isEmpty() || !pass.equals(repass)) {
            // Handle invalid password
            showErrorHighlighting(passText);
            showErrorHighlighting(passReText);
            isValid = false;
        }
        if (email.isEmpty()) {
            // Handle empty email
            showErrorHighlighting(emailText);
            isValid = false;
        }

        // Could also check for certain field characteristics here
        /* Ex 1: UVA email only
        if (!email.endsWith("@virginia.edu")) {
            // TODO: HANDLE NON-UVA EMAIL
            is_valid = false;
        }
         */
        /* Ex 2: Password with at least one number or symbol
        if (!pass.matches("[a-zA-Z]*([^a-zA-Z][a-zA-Z]*)*")) {
            // TODO: HANDLE PASSWORD FORMATTING
            is_valid = false;
        }
         */

        // Callback to the activity to sign the new user up on Parse
        if (isValid) {
            mCallback.onUserSignUp(username, pass, email);
        }
    }

    // Abstracted method for displaying red underline on EditText
    private void showErrorHighlighting(EditText view) {
        view.getBackground().setColorFilter(getResources().getColor(R.color.error_red),
                PorterDuff.Mode.SRC_ATOP);
    }
}