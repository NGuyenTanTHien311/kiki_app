package com.example.kiki.SearchFilter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kiki.Model.Truyen;
import com.example.kiki.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterAdapter.NewsViewHolder> {
    ArrayList<Truyen> truyenList;
    Context context;

    public SearchFilterAdapter(Context context, ArrayList<Truyen> newsList) {
        this.truyenList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder( @NonNull ViewGroup parent ,int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.truyen_filtered,
                parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull NewsViewHolder holder ,int position ) {
        Truyen truyen = truyenList.get(position);
        holder.name.setText(truyen.getName());
        holder.chapter.setText(truyen.getChapter());
        holder.author.setText(truyen.getAuthor());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = storageReference.child("kiki/").child("kiki/").child(truyen.getImagePath());

        islandRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.truyenImg);
            }
        });
    }

    @Override
    public int getItemCount() {
        return truyenList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        ImageView truyenImg;
        TextView name, author, chapter;
        public NewsViewHolder( @NonNull View itemView ) {
            super(itemView);
            truyenImg = itemView.findViewById(R.id.truyenImage);
            name = itemView.findViewById(R.id.tvName);
            author = itemView.findViewById(R.id.tvauthorName);
            chapter = itemView.findViewById(R.id.tvchapterName);
        }
    }
}
