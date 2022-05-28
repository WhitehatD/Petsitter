package com.alexc.petsitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public long pressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button back = (Button) findViewById(R.id.backButton);

        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        });


        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(v -> {

            TextInputLayout email = (TextInputLayout) findViewById(R.id.mail);
            TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
            TextInputLayout repeatPass = (TextInputLayout) findViewById(R.id.repet_password);

            if(email.getEditText().getText() == null || password.getEditText().getText() == null ||
                repeatPass.getEditText().getText() == null
            )
                return;

            String emailString = email.getEditText().getText().toString();
            String passString = password.getEditText().getText().toString();
            String repeatPassString =  repeatPass.getEditText().getText().toString();


            if(!isEmailValid(emailString)){
                TextView status = (TextView) findViewById(R.id.status);
                status.setText("Emailul nu este valid.");
                status.setTextColor(getColor(R.color.red));

                return;
            }

            if(emailString.isEmpty() || passString.isEmpty() || repeatPassString.isEmpty())
                return;

            if(!repeatPassString.equals(passString)) {
                TextView status = (TextView) findViewById(R.id.status);
                status.setText("Parolele nu sunt identice");
                status.setTextColor(getColor(R.color.red));

                return;
            }

            mAuth.createUserWithEmailAndPassword(emailString, passString)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){

                                TextView status = (TextView) findViewById(R.id.status);
                                status.setText("Înregistrarea nu s-a putut efectua.");
                                status.setTextColor(getColor(R.color.red));

                                return;
                            }


                            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));

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


    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}