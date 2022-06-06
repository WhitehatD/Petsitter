package com.alexc.petsitter.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexc.petsitter.LoginActivity;
import com.alexc.petsitter.R;
import com.google.firebase.auth.FirebaseAuth;


public class Settings extends Fragment implements View.OnClickListener{


    public FirebaseAuth mAuth;
    public View view;

    public Settings() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);;
        mAuth = FirebaseAuth.getInstance();

        view.findViewById(R.id.logoutButton).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logoutButton: {

                mAuth.signOut();
                startActivity(new Intent(view.getContext(), LoginActivity.class));

                break;
            }
        }
    }
}