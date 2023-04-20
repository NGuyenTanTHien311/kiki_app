package com.example.kiki.fragment.Home;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kiki.IOnItemClickListener;
import com.example.kiki.Info.InfoActivity;
import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    ArrayList<Truyen> listTruyen;
    HomeAdapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.homeRecylerview);
        listTruyen = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // tạo adapter, đồng thời xử lý sự kiện khi nhấn từng item
        adapter = new HomeAdapter(getContext() , listTruyen , new IOnItemClickListener() {
            @Override
            public void onItemCLickListener(Truyen truyen) {
                // Chuyển từ trang home sang trang info
                Intent intent = new Intent(getContext(), InfoActivity.class);
                // Chuyển dữ liệu của item sang trang info
                intent.putExtra("Truyen",  truyen);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        //Lấy danh sách truyện
        // Lay id cua document
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
                            String chapter =  document.get("chapter").toString();
                            String price = document.get("price").toString();
                            String description = document.get("description").toString();

                            // set truyen từ thuộc tính đã lấy
                            Truyen truyen = new Truyen(id , chapter , name , description, author , imagePath, price);
                            // thêm vào list
                            listTruyen.add(truyen);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}