package com.alexc.petsitter;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public abstract class CustomAppCompatActivity extends AppCompatActivity {

    public long pressTime = 0;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale("ro_RO");
        Locale.setDefault(locale);

        Configuration config = this.getResources().getConfiguration();
        config.setLocale(locale);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("ro");

        onCreate();
    }

    protected abstract void onCreate();

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
