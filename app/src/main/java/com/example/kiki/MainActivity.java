package com.example.kiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.kiki.Model.Truyen;
import com.example.kiki.SearchFilter.SearchFilterActivity;
import com.example.kiki.fragment.Cart.CartFragment;
import com.example.kiki.fragment.Home.HomeFragment;
import com.example.kiki.fragment.SettingFragment;
import com.example.kiki.fragment.person.personFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btnNav;
    ArrayList<Truyen> listTruyen = new ArrayList<>();
    FirebaseFirestore db =  FirebaseFirestore.getInstance();;

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
                switch (item.getItemId()) {
                    case R.id.Cart:
                        fragment = new CartFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.Home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.Setting:
                        fragment = new SettingFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.person:
                        fragment = new personFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

        db.collection("abc")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            // Lấy từng field trong document
                            String author = document.get("author").toString();
                            String name = document.get("name").toString();
                            String imagePath = document.get("imagePath").toString();
                            String chapter = document.get("chapter").toString();
                            String price = document.get("price").toString();
                            String description = document.get("description").toString();
                            String type = document.get("type").toString();

                            // set truyen từ thuộc tính đã lấy
                            Truyen truyen = new Truyen(id, chapter, name, type, description, author, imagePath, price);
                            // thêm vào list
                            listTruyen.add(truyen);
                        }
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
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit( String query ) {
                    try {
                        Intent intent = new Intent(MainActivity.this, SearchFilterActivity.class);
                        intent.putExtra("query", query);
                        intent.putExtra("list", listTruyen);
                        startActivity(intent);
                    }catch (Exception e){
                        Log.d("search", e.getMessage());
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange( String newText ) {
                    return false;
                }
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

}