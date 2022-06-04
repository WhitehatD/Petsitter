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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends CustomAppCompatActivity {

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_register);

        Button back = (Button) findViewById(R.id.backButton);

        back.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });


        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(v -> {

            TextInputLayout email = (TextInputLayout) findViewById(R.id.mail);
            TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
            TextInputLayout repeatPass = (TextInputLayout) findViewById(R.id.repet_password);

            if (email.getEditText().getText() == null || password.getEditText().getText() == null ||
                    repeatPass.getEditText().getText() == null
            )
                return;

            String emailString = email.getEditText().getText().toString().replaceAll(" ", "");
            String passString = password.getEditText().getText().toString();
            String repeatPassString = repeatPass.getEditText().getText().toString();


            if (!isEmailValid(emailString)) {
                setStatus("Emailul nu este valid.");

                return;
            }

            if (emailString.isEmpty() || passString.isEmpty() || repeatPassString.isEmpty()){

                setStatus("Completați toate câmpurile");

                return;

            }

            if (!repeatPassString.equals(passString)) {
                setStatus("Parolele nu sunt identice");

                return;
            }

            if(passString.length() < 6){
                setStatus("Parola trebuie sa aibă minim 6 caractere");

                return;
            }



            mAuth.fetchSignInMethodsForEmail(emailString)
                    .addOnFailureListener(e -> {

                        if(e instanceof FirebaseAuthUserCollisionException){
                            setStatus("Există deja un utilizator cu același email.");
                        } else
                            setStatus("A intervenit o eroare la înregistrare. Vă rugam încercați mai târziu.");

                    })
                    .addOnSuccessListener(s -> {

                        mAuth.createUserWithEmailAndPassword(emailString, passString)
                                .addOnSuccessListener(authResult ->
                                        startActivity(new Intent(RegisterActivity.this, ProfileActivity.class)));
                    });


        });
    }

    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void setStatus(String text){
        TextView status = findViewById(R.id.status);
        status.setText(text);
        status.setTextColor(getColor(R.color.red));

    }
}