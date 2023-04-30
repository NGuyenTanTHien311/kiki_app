package com.example.kiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.kiki.fragment.Cart.CartFragment;
import com.example.kiki.fragment.Home.HomeFragment;
import com.example.kiki.fragment.SettingFragment;
import com.example.kiki.fragment.person.personFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btnNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNav = findViewById(R.id.bottomNav);

        loadFragment(new HomeFragment());

        btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.Cart:
                        fragment = new CartFragment();
                        loadFragment(fragment);
                        return  true;
                    case R.id.Home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return  true;
                    case R.id.Setting:
                        fragment = new SettingFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.person:
                        fragment = new personFragment();
                        loadFragment(fragment);
                        return true;
                }
                return  false;
            }
        });

    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}