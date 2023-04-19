package com.example.kiki.fragment.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiki.IOnItemClickListener;
import com.example.kiki.Model.Cart;
import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.TruyenViewHolder> implements IOnItemClickListener {

    Context context;
    ArrayList<Truyen> listTruyen;
    IOnItemClickListener listener;
    DatabaseReference db = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    public HomeAdapter(Context context, ArrayList<Truyen> listTruyen, IOnItemClickListener listener) {
        this.context = context;
        this.listTruyen = listTruyen;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TruyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,
                parent, false);
        return new TruyenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenViewHolder holder, int position) {
        Truyen truyen = listTruyen.get(position);
        holder.author.setText(truyen.getAuthor());
        holder.chapter.setText(String.valueOf(truyen.getChapter()));
        holder.Name.setText(truyen.getName());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = storageReference.child("kiki/").child("kiki/").child(truyen.getImagePath());
        try {
            File localFile = File.createTempFile("images", "jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getPath());
                    holder.truyenImage.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(context, exception.getMessage() + truyen.getImagePath(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemCLickListener(truyen);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.child("Cart").getRef().push().setValue(new Cart(truyen, 1, Double.parseDouble(truyen.getPrice()))).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    }

    @Override
    public int getItemCount() {
        return listTruyen.size();
    }
    @Override
    public void onItemCLickListener(Truyen truyen) {

    }

    public static class TruyenViewHolder extends RecyclerView.ViewHolder{
        TextView Name, author,chapter;
        ImageView truyenImage, addToCart;
        public TruyenViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvName);
            author = itemView.findViewById(R.id.tvauthorName);
            chapter = itemView.findViewById(R.id.tvchapterName);
            truyenImage = itemView.findViewById(R.id.truyenImage);
            addToCart = itemView.findViewById(R.id.addToCart);
        }
    }
}
