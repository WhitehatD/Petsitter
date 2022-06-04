package com.alexc.petsitter;


import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends CustomAppCompatActivity {

    @Override
    protected void onCreate() {
        setContentView(R.layout.activity_profile);


        NavigationBarView view = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController controller = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(view, controller);


       /* Button logout = findViewById(R.id.logoffButton);
        logout.setOnClickListener(v -> {

            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));



        });*/
    }
}