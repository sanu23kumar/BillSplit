package com.example.sanukumar.billsplit;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

    private SignInButton gmail;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth,getmAuth;

    public static final String TAG="STARTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth=FirebaseAuth.getInstance();
        getmAuth=FirebaseAuth.getInstance();

        gmail = (SignInButton) findViewById(R.id.login11);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }


    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser cuser=mAuth.getCurrentUser();
        FirebaseUser currentUser = getmAuth.getCurrentUser();
        if(currentUser!=null||cuser!=null) {
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(login.this,"You Are logged in",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(login.this, HomeScreenActivityActivity.class));
        finish();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int req, int resultCode, Intent data) {

        super.onActivityResult(req, resultCode, data);



        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (req == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        getmAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent i=new Intent(login.this,HomeScreenActivityActivity.class);
                            startActivity(i);
                            finish();
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(login.this, "default")
                                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused)
                                    .setContentTitle("Login")
                                    .setContentText("User Logged In")
                                    .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText("Mark your attendance"))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true);
                            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            vibe.vibrate(1000);


                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(login.this);

                            // notificationId is a unique int for each notification that you must define
                            notificationManagerCompat.notify(0, mBuilder.build());
                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
