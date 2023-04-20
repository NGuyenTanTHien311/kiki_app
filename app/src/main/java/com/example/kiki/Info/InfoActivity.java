package com.example.kiki.Info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class InfoActivity extends AppCompatActivity {

    EditText inputStock;
    Button submitStock;
    TextView total, name, description;
    ImageView imageView;
    Truyen truyen;
    double money = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent receive = getIntent();
        Truyen sentTruyen = (Truyen) receive.getSerializableExtra("Truyen");

        truyen = sentTruyen;

        name = findViewById(R.id.name);
        description = findViewById(R.id.descripton);
        imageView = findViewById(R.id.truyenImg);
        total = findViewById(R.id.total);

        inputStock = findViewById(R.id.inputStock);
        submitStock = findViewById(R.id.submitStock);

        name.setText(truyen.getName());
        description.setText(truyen.getDescription());

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

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = storageReference.child("kiki/").child("kiki/").child(truyen.getImagePath());
        try {
            File localFile = File.createTempFile("images", "jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getPath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(InfoActivity.this, exception.getMessage() + truyen.getImagePath(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getTruyen(Truyen _truyen){
        truyen = _truyen;
    }
}