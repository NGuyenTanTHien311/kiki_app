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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiki.Model.Cart;
import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.example.kiki.fragment.Home.HomeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context context;
    private ArrayList<Cart> listCart;
    private DatabaseReference db = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
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

        // Tham chiếu tới storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        // Tham chiếu tới thư mục chứa ảnh
        StorageReference islandRef = storageReference.child("kiki/").child("kiki/").child(truyen.getImagePath());
        try {
            // Tạo file tạm
            File localFile = File.createTempFile("images", "jpg");
            // Ghi ảnh vào file
            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    // Chuyển đổi file thành dạng bitmap
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getPath());
                    // Set ảnh
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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCart.remove(cart);
                ChangeInDatabaseWhenDeleteItem(cart);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView Name, author,chapter, stock, total;
        ImageView truyenImage, delete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvname);
            author = itemView.findViewById(R.id.tvauthorname);
            chapter = itemView.findViewById(R.id.tvchaptername);
            stock = itemView.findViewById(R.id.tvStock);
            total = itemView.findViewById(R.id.tvTotal);
            truyenImage = itemView.findViewById(R.id.truyenImage);
            delete = itemView.findViewById(R.id.deleteItem);
        }
    }
    public void ChangeInDatabaseWhenDeleteItem(Cart _cart){
        // Tham chiếu đến child Cart trong realtime
        db.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    // Lấy cart bằng cách đi qua từng child của Cart
                    Cart cart = snap.getValue(Cart.class);
                    if(cart.getTruyen().getID().equals(_cart.getTruyen().getID())){
                        String key = snap.getKey();
                        // Xóa khỏi realtime
                        db.child("Cart").child(key).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
