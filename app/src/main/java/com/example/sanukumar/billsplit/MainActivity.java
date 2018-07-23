package com.example.sanukumar.billsplit;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Vibrator;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    @SuppressLint("WrongViewCast")

    final int DELAY = 1000; //delay before starting the home screen

    ImageView bill, split;
    CardView signInView, fingerprintView;
    ImageButton gmail;
    ConstraintLayout splashParent;
    LinearLayout signInGroup;
    View line;
    boolean alreadyLoggedIn = false;

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth,getmAuth;

    public static final String TAG="STARTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Main Activity is the splash screen of the application

        bill = findViewById(R.id.bill_logo_splash);
        split = findViewById(R.id.split_logo_splash);
        line = findViewById(R.id.line_splash);
        signInView = findViewById(R.id.signInView);
        gmail = findViewById(R.id.sign_in_button);
        splashParent = findViewById(R.id.splashParent);
        fingerprintView = findViewById(R.id.fingerprintView);
        signInGroup = findViewById(R.id.signInViewGroup);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager =(KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =(FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
                System.out.println("Your device doesn't support fingerprint authentication");
            }
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("Please enable the fingerprint permission");
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                System.out.println("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
            }
            if (!keyguardManager.isKeyguardSecure()) {
                System.out.println("Please enable lock-screen security in your device's Settings");
            } else {
                try {
                    generateKey();
                } catch (MainActivity.FingerprintException e) {
                    e.printStackTrace();
                }
                if (initCipher()) {

                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHelper helper = new FingerprintHelper(MainActivity.this);
                    helper.startAuth(fingerprintManager, cryptoObject);

                }
            }
        }

//        Intent appMainActivity = new Intent(MainActivity.this,login.class);
//        startActivity(appMainActivity);
//        finish();

        mAuth=FirebaseAuth.getInstance();
        getmAuth=FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

                Transition transition = new Slide(Gravity.BOTTOM);
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(splashParent, transition);
                fingerprintView.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser cuser=mAuth.getCurrentUser();
        FirebaseUser currentUser = getmAuth.getCurrentUser();
        if(currentUser!=null||cuser!=null) {

            alreadyLoggedIn = true;

        }

    }

    @Override
    protected void onActivityResult(int req, int resultCode, Intent data) {
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

    @Override
    protected void onResume() {
        super.onResume();

        animateIn();

    }

    private void animateIn() {

        bill.animate().alpha(1f).translationX(Converter.pxFromDp(this,-200)).setDuration(1000).start();
        line.animate().alpha(1f).setDuration(1000).start();
        split.animate().alpha(1f).translationX(Converter.pxFromDp(this,200)).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (alreadyLoggedIn) {

                    bill.animate().alpha(0.4f).translationY(Converter.pxFromDp(MainActivity.this,-200)).setDuration(1000).start();
                    line.animate().alpha(0.4f).translationY(Converter.pxFromDp(MainActivity.this,-200)).setDuration(1000).start();
                    split.animate().alpha(0.4f).translationY(Converter.pxFromDp(MainActivity.this,-200)).setDuration(1000).start();

                    Transition transition = new Slide(Gravity.BOTTOM);
                    transition.setDuration(1000);
                    TransitionManager.beginDelayedTransition(splashParent, transition);
                    fingerprintView.setVisibility(View.VISIBLE);

                } else {

                    bill.animate().alpha(0.4f).translationY(Converter.pxFromDp(MainActivity.this,-200)).setDuration(1000).start();
                    line.animate().alpha(0.4f).translationY(Converter.pxFromDp(MainActivity.this,-200)).setDuration(1000).start();
                    split.animate().alpha(0.4f).translationY(Converter.pxFromDp(MainActivity.this,-200)).setDuration(1000).start();

                    Transition transition = new Slide(Gravity.BOTTOM);
                    transition.setDuration(1000);
                    TransitionManager.beginDelayedTransition(splashParent, transition);
                    signInView.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();

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

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() throws MainActivity.FingerprintException {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);
            keyGenerator.init(new  KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT |KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new MainActivity.FingerprintException(exc);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCipher() {
        try { cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

}
