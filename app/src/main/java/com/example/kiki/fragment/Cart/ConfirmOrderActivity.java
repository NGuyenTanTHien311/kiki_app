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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ConfirmOrderActivity extends AppCompatActivity {
    EditText name, phonenumber, address, orderTotal;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Button confirm;
    DatabaseReference db = FirebaseDatabase.getInstance("https://kiki-e7120-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

    double total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        name = findViewById(R.id.name);
        phonenumber = findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);
        confirm = findViewById(R.id.confirm);
        orderTotal = findViewById(R.id.orderTotal);

        Intent receiveIntent = getIntent();
        ArrayList<Cart> checkedCarts = (ArrayList<Cart>) receiveIntent.getSerializableExtra("CheckedCarts");

        for(Cart cart: checkedCarts){
            total += (cart.getStock() * Double.parseDouble(cart.getTruyen().getPrice()));
            orderTotal.setText(String.valueOf(total));
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ordetId = UUID.randomUUID().toString();
                String uuid = auth.getUid();
                db.child("Order").push().setValue(new Order(ordetId, auth.getUid(), name.getText().toString(), phonenumber.getText().toString(),
                        address.getText().toString(), checkedCarts));
                name.setText("");
                phonenumber.setText("");
                address.setText("");

                Toast.makeText(ConfirmOrderActivity.this, "Xác nhận đơn hàng thành công", Toast.LENGTH_LONG).show();

                Intent intentCallBack = getIntent();
                for(Cart childCart: checkedCarts){
                    changeStatusInDb(childCart, true);
                }

                finish();
            }
        });
    }
    public void changeStatusInDb(Cart cart, boolean ifChecked){
        db.child("Cart").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    Cart _cart = snap.getValue(Cart.class);
                    if(cart.getCartId().equals(_cart.getCartId())){
                        HashMap<String, Object> taskMap = new HashMap<String, Object>();
                        taskMap.put("ifRent", ifChecked);
                        snap.getRef().updateChildren(taskMap);
                    }
                }
            }
            @Override
            public void onCancelled( @NonNull DatabaseError error ) {
            }
        });
    }
}
