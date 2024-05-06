package com.example.viper_electric_appliances_webshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.models.NewProductsModel;
import com.example.viper_electric_appliances_webshop.models.PopularProductModel;
import com.example.viper_electric_appliances_webshop.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImage;
    TextView rating,name,desc,price, quantity;
    int sumQuantity = 1, sumPrice = 0;
    Button addToCart;
    ImageView addItem,removeItem;
    Toolbar toolbar;

    //Új termékek
    NewProductsModel newProductsModel = null;

    //Összes termék
    PopularProductModel popularProductModel = null;

    //Több
    ShowAllModel showAllModel = null;

    private FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.detail_toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Object object = getIntent().getSerializableExtra("detail");

        if(object instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) object;
        } else if(object instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) object;
        } else if(object instanceof ShowAllModel){
            showAllModel = (ShowAllModel) object;
        }

        detailImage = findViewById(R.id.detail_img);
        rating = findViewById(R.id.detail_rating);
        name = findViewById(R.id.detail_name);
        desc = findViewById(R.id.detail_desc);
        price = findViewById(R.id.detail_price);
        quantity = findViewById(R.id.detail_quantity);

        addToCart = findViewById(R.id.add_cart);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        //Új termékek
        if(newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailImage);
            name.setText(newProductsModel.getName());
            desc.setText(newProductsModel.getDescription());
            rating.setText(newProductsModel.getRating());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            sumPrice = newProductsModel.getPrice() * sumQuantity;
        }
        //Összes termék
        if(popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailImage);
            name.setText(popularProductModel.getName());
            desc.setText(popularProductModel.getDescription());
            rating.setText(popularProductModel.getRating());
            price.setText(String.valueOf(popularProductModel.getPrice()));

            sumPrice = popularProductModel.getPrice() * sumQuantity;
        }

        //Összes termék
        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailImage);
            name.setText(showAllModel.getName());
            desc.setText(showAllModel.getDescription());
            rating.setText(showAllModel.getRating());
            price.setText(String.valueOf(showAllModel.getPrice()));

            sumPrice = showAllModel.getPrice() * sumQuantity;
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumQuantity++;
                quantity.setText(String.valueOf((sumQuantity)));
                if(newProductsModel != null){
                    sumPrice = newProductsModel.getPrice() * sumQuantity;
                }
                if(popularProductModel != null){
                    sumPrice = popularProductModel.getPrice() * sumQuantity;
                }
                if(showAllModel != null){
                    sumPrice = showAllModel.getPrice() * sumQuantity;
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sumQuantity > 1){
                    sumQuantity--;
                    quantity.setText(String.valueOf((sumQuantity)));
                }
            }
        });

    }

    private void addToCart(){
        String saveCurrentTime, saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("Time",saveCurrentTime);
        cartMap.put("Date",saveCurrentDate);
        cartMap.put("sumQuantity",quantity.getText().toString());
        cartMap.put("sumPrice",sumPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid()).collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailActivity.this, "Hozzáadva  a kosárhoz!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}