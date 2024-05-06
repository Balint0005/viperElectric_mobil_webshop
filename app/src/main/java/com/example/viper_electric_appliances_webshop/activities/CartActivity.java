package com.example.viper_electric_appliances_webshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.adapters.CartAdapter;
import com.example.viper_electric_appliances_webshop.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    TextView totalPriceText;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<CartModel> cartModelList;
    CartAdapter cartAdapter;
    Button buy, delete;
    int totalPriceinCartNumber;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private static final String CHANNEL_ID = "my_channel_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.cart_toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,new IntentFilter("TotalPrice"));

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalPriceText = findViewById(R.id.cart_sum_price);

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);

        buy = findViewById(R.id.cart_buy);
        delete = findViewById(R.id.cart_delete);

        firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc: task.getResult().getDocuments()){
                                CartModel cartModel = doc.toObject(CartModel.class);
                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cartModelList.isEmpty()) {
                    Intent intent = new Intent(v.getContext(), OrderActivity.class);
                    intent.putExtra("itemInCart", (Serializable) cartModelList);
                    startActivity(intent);
                    createNotification();
                    Toast.makeText(CartActivity.this, "Rendelés leadva! :)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCart();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalPriceGet = intent.getIntExtra("totalprice", 0);
            totalPriceText.setText("Teljes összeg: " + totalPriceGet + " Ft");
        }
    };

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
                    Toast.makeText(CartActivity.this, "Sikeres törlés!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "HIBA!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.viper_icon)
                .setContentTitle("Viper Electric Shop")
                .setContentText("Köszönjük a vásárlást! :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }



}