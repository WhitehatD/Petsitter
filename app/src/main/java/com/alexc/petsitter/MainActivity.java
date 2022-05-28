package com.alexc.petsitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public long pressTime = 0;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });



        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(v -> {

            TextInputLayout email = (TextInputLayout) findViewById(R.id.mail);
            TextInputLayout password = (TextInputLayout) findViewById(R.id.password);

            if(email.getEditText().getText() == null || password.getEditText().getText() == null)
                return;

            String emailString = email.getEditText().getText().toString();
            String passString = email.getEditText().getText().toString();

            if(emailString.isEmpty() || passString.isEmpty())
                return;

          //  email.

            mAuth.signInWithEmailAndPassword(emailString, passString)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){

                                TextView status = (TextView) findViewById(R.id.status);
                                status.setText("Parola sau emailul nu sunt corecte.");
                                status.setTextColor(getColor(R.color.red));

                                return;
                            }


                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));

                        }
                    });


        });


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            pressTime = System.currentTimeMillis();
        }
        else if (ev.getAction() == MotionEvent.ACTION_UP) {
            long releaseTime = System.currentTimeMillis();
            if (releaseTime-pressTime < 200) {
                if (getCurrentFocus() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    getCurrentFocus().clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}