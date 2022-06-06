package com.alexc.petsitter.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alexc.petsitter.LoginActivity;
import com.alexc.petsitter.R;
import com.google.firebase.auth.FirebaseAuth;


public class Home extends Fragment {

    public Home() {

    }

    public View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }


}