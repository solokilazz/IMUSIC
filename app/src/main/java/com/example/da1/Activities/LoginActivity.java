package com.example.da1.Activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.da1.MainActivity;
import com.example.da1.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private static final int GG_SIGN_IN = 33;

    private FirebaseAuth mAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    Button btnLoginGG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set màn hình chờ
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_login);

        mapping();

        //click
        eventClick();


    }

    private void eventClick() {
        btnLoginGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(LoginActivity.this,
                        new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(result.getPendingIntent().getIntentSender(),
                                    GG_SIGN_IN, null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e("ERROR", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                    }
                });
            }
        });

    }

    private void signInWithFirebaseCrendential(AuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task< AuthResult > task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("ERROR", "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.getDisplayName(); // tên hiển thị
                    user.getPhotoUrl(); //link ảnh đại diện
                    user.getPhoneNumber(); // số điện thoại
                    user.getEmail(); // địa chỉ email
                    //lấy thông tin người dùng
                    // sau khi login thành công
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.w("ERROR", "signInWithCredential:failure", task.getException());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GG_SIGN_IN) {
            try {
                SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = googleCredential.getGoogleIdToken();
                if (idToken != null) {
                    // Got an ID token from Google. Use it to authenticate with Firebase.
                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                    signInWithFirebaseCrendential(firebaseCredential);
                }
            } catch (ApiException e) {
                Log.e("ApiException", e.getLocalizedMessage());
            }
        }
    }


    private void mapping() {
        btnLoginGG = findViewById(R.id.btnLoginGG);

        mAuth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false).build()).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //currentUser != null tức là đã đăng nhập
        //rồi chuyển đến thẳng trang chính
        if (currentUser != null) {
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    }
}