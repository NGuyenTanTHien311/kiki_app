package com.example.kiki.fragment.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kiki.Model.Cart;
import com.example.kiki.Model.Order;
import com.example.kiki.R;
import com.example.kiki.fragment.Home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ConfirmOrderActivity extends AppCompatActivity {
    EditText name, phonenumber, address;
    Button confirm;
    DatabaseReference db = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        name = findViewById(R.id.name);
        phonenumber = findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);
        confirm = findViewById(R.id.confirm);

        Intent receiveIntent = getIntent();
        ArrayList<Cart> listCarts = (ArrayList<Cart>) receiveIntent.getSerializableExtra("ListCarts");
        ArrayList<Cart> checkedCarts = (ArrayList<Cart>) receiveIntent.getSerializableExtra("CheckedCarts");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ordetId = UUID.randomUUID().toString();
                db.child("Order").push().setValue(new Order(ordetId, name.getText().toString(), phonenumber.getText().toString(),
                                                                address.getText().toString(), checkedCarts));
                name.setText("");
                phonenumber.setText("");
                address.setText("");

                Toast.makeText(ConfirmOrderActivity.this, "Xác nhận đơn hàng thành công", Toast.LENGTH_LONG).show();

                Intent intentCallBack = getIntent();
                for(Cart childListCart: listCarts){
                    for(Cart childCheckedCart: checkedCarts){
                        if(childCheckedCart.equals(childListCart)){
                            listCarts.remove(childCheckedCart);
                            removeCheckedCartFromCart(childCheckedCart);
                        }
                    }
                }

                intentCallBack.putExtra("newList", listCarts);
                setResult(RESULT_OK, intentCallBack);
                finish();
            }
        });
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    public void removeCheckedCartFromCart(Cart cart){
        db.child("Cart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    Cart _cart = snap.getValue(Cart.class);
                    String key = snap.getKey();
                    if(_cart.getCartId().equals(cart.getCartId())){
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