package com.alexc.petsitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends CustomAppCompatActivity {


    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_login);


        //check for login
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){

            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            return;
        }


        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });



        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(v -> {

            TextInputLayout email = findViewById(R.id.mail);
            TextInputLayout password = findViewById(R.id.password);

            if(email.getEditText().getText() == null || password.getEditText().getText() == null)
                return;

            String emailString = email.getEditText().getText().toString().replaceAll(" ", "");
            String passString = password.getEditText().getText().toString();

            if(emailString.isEmpty() || passString.isEmpty()) {
                setStatus("Completați toate câmpurile");

                return;
            }


            mAuth.signInWithEmailAndPassword(emailString, passString)
                    .addOnCompleteListener(this, task -> {

                        if(!task.isSuccessful()){

                            setStatus("Parola sau emailul nu sunt corecte.");

                            return;
                        }


                        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));

                    });


        });


    }

    public void setStatus(String text){
        TextView status = findViewById(R.id.status);
        status.setText(text);
        status.setTextColor(getColor(R.color.red));

    }



}