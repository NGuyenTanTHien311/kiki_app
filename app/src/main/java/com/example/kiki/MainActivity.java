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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


//    RecyclerView recyclerView;
//    ArrayList<User> userArrayList;
//    MyAdapterActivity myAdapterActivity;
//    FirebaseFirestore db;
//    ProgressDialog progressDialog;
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
                }
                return  false;
            }
        });
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching Data...");
//        progressDialog.show();
//
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        db = FirebaseFirestore.getInstance();
//        userArrayList =new ArrayList<User>();
//        myAdapterActivity = new MyAdapterActivity(MainActivity.this,userArrayList);
//
//        recyclerView.setAdapter(myAdapterActivity);
//
//        EventChangeListener();

    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
//    private void EventChangeListener() {
//        db.collection("abc").orderBy("Name", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                if (error != null){
//
//                    if (progressDialog.isShowing())
//                        progressDialog.dismiss();
//
//
//                    Log.e("firestore error",error.getMessage());
//                    return;
//                }
//
//                for (DocumentChange dc : value.getDocumentChanges()){
//                    if (dc.getType() == DocumentChange.Type.ADDED){
//                        userArrayList.add(dc.getDocument().toObject(User.class));
//                    }
//
//                    myAdapterActivity.notifyDataSetChanged();
//                }
//                if (progressDialog.isShowing())
//                    progressDialog.dismiss();
//
//
//            }
//        });
}