package com.example.kiki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterActivity extends RecyclerView.Adapter<MyAdapterActivity.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public MyAdapterActivity(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapterActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterActivity.MyViewHolder holder, int position) {

        User user =userArrayList.get(position);

        holder.Name.setText(user.truyen);
        holder.author.setText(user.tacgia);
        holder.chapter.setText(String.valueOf(user.chapter));


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name, author,chapter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvName);
            author = itemView.findViewById(R.id.tvauthorName);
            chapter = itemView.findViewById(R.id.tvchapterName);
        }
    }
}
