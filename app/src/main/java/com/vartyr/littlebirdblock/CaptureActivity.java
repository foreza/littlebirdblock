package com.vartyr.littlebirdblock;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.vartyr.littlebirdblock.utils.logger;

import java.util.Arrays;
import java.util.List;

public class CaptureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doLoginForUser();
    }


    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );


    private void doLoginForUser(){
        logger.simplelog("beginning login for user");

        // TODO: experiment with all the different types of authentication
        // In the case of oath/sso, can we still get the email and/or phone number?
        // ATS will require email at the very least
        // Investigate the potential issues with
        /*
            - Google
            - Facebook
            - Twitter
            - Phone
         */
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

    // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            logger.simplelog("User successfully signed in: " + user.getEmail());

            // TODO: Do a call to some ATS SDK with the email/phone/etc from the user
            goToLoggedInPage();
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...

            logger.simplelog("User NOT signed in: " + response.getError().getErrorCode());
        }
    }


    private void goToLoggedInPage(){
        // TODO: pass the user UID from firebase authentication so we can pull records for the user
        Intent intent = new Intent(this, LoggedInActivity.class);
        startActivity(intent);
    }

}