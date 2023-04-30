package com.example.kiki.fragment.Cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kiki.Model.Cart;
import com.example.kiki.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class CartFragment extends Fragment {

    TextView cartTotal;
    CartAdapter adapter;
    ArrayList<Cart> listCart;
    RecyclerView recyclerView;
    Button rent;

    DatabaseReference db = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

    ActivityResultLauncher<Intent> laucher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                ArrayList<Cart> newListCart = new ArrayList<>();
                newListCart = (ArrayList<Cart>) result.getData().getSerializableExtra("newList");
                listCart.clear();
                listCart = newListCart;

                adapter = new CartAdapter(getContext(), listCart);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // chuyển đổi tiệp nguồn sang đối tượng view
        return inflater.inflate(R.layout.fragment_cart , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view , savedInstanceState);
        cartTotal = view.findViewById(R.id.cartTotal);
        listCart = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recylerview);
        rent = view.findViewById(R.id.rent);
        adapter = new CartAdapter(getContext(), listCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Lấy danh sách Cart
        db.child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCart.clear();
                double _totalCart = 0;
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Cart cart = snapshot1.getValue(Cart.class);
                    listCart.add(cart);
                    adapter.notifyDataSetChanged();
                }
                for(Cart _cart: listCart){
                    if(_cart.isIfRent()){
                        _totalCart += Double.parseDouble(_cart.getTruyen().getPrice()) * _cart.getStock();
                    }
                }
                cartTotal.setText(String.valueOf(_totalCart));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Cart> checkedCarts = new ArrayList<>();
                ArrayList<Cart> listCarts = new ArrayList<>();
                for(Cart _cart: listCart){
                    if(_cart.isIfRent()){
                        checkedCarts.add(_cart);
                    }else{
                        listCarts.add(_cart);
                    }
                }
                cartTotal.setText("0");
                Intent intent = new Intent(getContext(), ConfirmOrderActivity.class);
                intent.putExtra("ListCarts", listCarts);
                intent.putExtra("CheckedCarts", checkedCarts);
                laucher.launch(intent);
            }
        });
    }
}