package com.example.sanukumar.billsplit;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)

/**
 * Created by Sudeep Vig on 12-03-2018.
 */

public class FingerprintHelper extends FingerprintManager.AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private Context context;

    public FingerprintHelper(Context mContext) {
        context = mContext;
    }
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        //Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationFailed() {
        //Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        //Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }@Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        //Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        onAuthSuccess();

    }
    private void onAuthSuccess() {
        context.startActivity(new Intent(context, HomeScreenActivityActivity.class));
        ((AppCompatActivity) context).finish();
        System.out.println("After");


}}

