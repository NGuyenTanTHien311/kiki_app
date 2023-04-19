package com.example.kiki.Info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kiki.Model.Cart;
import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class InfoActivity extends AppCompatActivity {

    EditText inputStock;
    Button submitStock;
    TextView total;
    Truyen truyen;
    FirebaseFirestore db;
    double money = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        db = FirebaseFirestore.getInstance();

        Intent receive = getIntent();
        String Id = receive.getStringExtra("idTruyen");
        inputStock = findViewById(R.id.inputStock);
        submitStock = findViewById(R.id.submitStock);
        total = findViewById(R.id.total);

        submitStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _inputStock = inputStock.getText().toString();
                int stock = Integer.parseInt(_inputStock);
                money = Double.parseDouble(truyen.getPrice()) * stock;
                total.setText(String.valueOf(money));
                inputStock.setText("");

                databaseReference.child("Cart").getRef().push().setValue(new Cart(truyen, stock, money)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ABC", e.getMessage());
                    }
                });
            }
        });

        db.collection("abc")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String author = document.get("author").toString();
                            String name = document.get("name").toString();
                            String imagePath = document.get("imagePath").toString();
                            String chapter =  document.get("chapter").toString();
                            String price = document.get("price").toString();

                            Truyen _truyen = new Truyen(id , chapter , name , author , imagePath, price);
                            if(_truyen.getID().equals(Id)){
                                truyen = _truyen;
                            }
                        }
                    }
                });
    }
    // alo 1235
}