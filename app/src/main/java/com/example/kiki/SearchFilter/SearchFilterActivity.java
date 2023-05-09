package com.example.kiki.SearchFilter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kiki.Model.Truyen;
import com.example.kiki.R;

import java.util.ArrayList;

public class SearchFilterActivity extends AppCompatActivity {
    RecyclerView filterRecylerview;
    SearchFilterAdapter adapter;
    ArrayList<Truyen> list = new ArrayList<>();
    ArrayList<Truyen> queryList = new ArrayList<>();
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        filterRecylerview = findViewById(R.id.filterRecyclerview);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        list = (ArrayList<Truyen>) intent.getSerializableExtra("list");

        for (Truyen item : list) {
            if (item.getName() != null && (item.getName().contains(query) ||
                    item.getAuthor().contains(query))) {
                queryList.add(item);
            }
            adapter = new SearchFilterAdapter(this, queryList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            filterRecylerview.setAdapter(adapter);
            filterRecylerview.setLayoutManager(linearLayoutManager);
        }
    }
}