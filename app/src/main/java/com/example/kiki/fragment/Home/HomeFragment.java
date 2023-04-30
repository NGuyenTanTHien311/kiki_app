package com.example.kiki.fragment.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    ArrayList<Truyen> listTruyen;
    TruyenAdapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    public static Context context;
    ViewPager2Adapter viewPager2Adapter;
    ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2Adapter = new ViewPager2Adapter(this);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.pager);
        viewPager2.setAdapter(viewPager2Adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0){
                    tab.setText("Hành động");
                }else if(position == 1){
                    tab.setText("Phiêu lưu");
                }else{
                    tab.setText("Hài hước");
                }
            }
        });
        tabLayoutMediator.attach();
   }
    public static Context getHomeFragmentContext(){
        return HomeFragment.context;
    }
}