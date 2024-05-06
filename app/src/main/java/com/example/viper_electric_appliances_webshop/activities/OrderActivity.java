package com.example.viper_electric_appliances_webshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.adapters.OrderAdapter;
import com.example.viper_electric_appliances_webshop.models.CartModel;
import com.example.viper_electric_appliances_webshop.models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<OrderModel> orderModelList;
    OrderAdapter orderAdapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.order_toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.order_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderModelList);
        recyclerView.setAdapter(orderAdapter);

        firestore.collection("Order").document(firebaseAuth.getCurrentUser().getUid()).collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult().getDocuments()){
                        OrderModel orderModel = doc.toObject(OrderModel.class);
                        orderModelList.add(orderModel);
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        List<CartModel> list = (ArrayList<CartModel>) getIntent().getSerializableExtra("itemInCart");

        if(list != null && !list.isEmpty()) {
            for(CartModel model : list){
                final HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("productName",model.getProductName());
                orderMap.put("productPrice",model.getProductPrice());
                orderMap.put("sumQuantity",model.getSumQuantity());
                orderMap.put("sumPrice",model.getSumPrice());

                firestore.collection("Order").document(firebaseAuth.getCurrentUser().getUid()).collection("User").add(orderMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        deleteCart();
                        Toast.makeText(OrderActivity.this, "Sikeres vásárlás!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderActivity.this, MainActivity.class));
                        finish();
                    }
                });

            }
        }

    }

    private void deleteCart(){
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference cartItemsRef = firestore.collection("AddToCart").document(currentUserUid).collection("User");

        cartItemsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()){
                        document.getReference().delete();
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "HIBA!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}