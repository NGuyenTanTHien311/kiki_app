package com.example.kiki.fragment.Cart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiki.Model.Cart;
import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.example.kiki.fragment.Home.HomeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context context;
    private ArrayList<Cart> listCart;

    public CartAdapter(Context context, ArrayList<Cart> listCart){
        this.context = context;
        this.listCart = listCart;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,
                parent, false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder , int position) {
        Cart cart = listCart.get(position);
        Truyen truyen = cart.getTruyen();
        holder.chapter.setText(truyen.getChapter());
        holder.author.setText(truyen.getAuthor());
        holder.Name.setText(truyen.getName());
        holder.total.setText(String.valueOf(cart.getTotal()));
        holder.stock.setText(String.valueOf(cart.getStock()));

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
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView Name, author,chapter, stock, total;
        ImageView truyenImage;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvname);
            author = itemView.findViewById(R.id.tvauthorname);
            chapter = itemView.findViewById(R.id.tvchaptername);
            stock = itemView.findViewById(R.id.tvStock);
            total = itemView.findViewById(R.id.tvTotal);
            truyenImage = itemView.findViewById(R.id.truyenImage);

        }
    }
}
