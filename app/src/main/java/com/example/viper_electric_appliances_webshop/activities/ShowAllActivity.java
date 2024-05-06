package com.example.viper_electric_appliances_webshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.adapters.ShowAllAdapter;
import com.example.viper_electric_appliances_webshop.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ConstraintLayout constraintLayout;
    ShowAllAdapter showAllAdapter;
    List<ShowAllModel> showAllModelList;
    FirebaseFirestore firestore;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        constraintLayout = findViewById(R.id.gradient_bg);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(100);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        //putExtra-ból átvesszük a type-ot
        String type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.show_all_toolbar_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.show_all_rec);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        showAllModelList = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModelList);
        recyclerView.setAdapter(showAllAdapter);

        //Katgóriának megfelelő megjelenítés
        //nincs kategória, mutat mindent
        if(type == null || type.isEmpty()){
            firestore.collection("ShowAll").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document: task.getResult().getDocuments()){
                            ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                            showAllModelList.add(showAllModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //Kategória mosogep
        if(type != null && type.equalsIgnoreCase("mosogep")){
            firestore.collection("ShowAll").whereEqualTo("type","mosogep").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document: task.getResult().getDocuments()){
                            ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                            showAllModelList.add(showAllModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //Kategória huto
        if(type != null && type.equalsIgnoreCase("huto")){
            firestore.collection("ShowAll").whereEqualTo("type","huto").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document: task.getResult().getDocuments()){
                            ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                            showAllModelList.add(showAllModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //Kategória tv
        if(type != null && type.equalsIgnoreCase("tv")){
            firestore.collection("ShowAll").whereEqualTo("type","tv").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document: task.getResult().getDocuments()){
                            ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                            showAllModelList.add(showAllModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //Kategória suto
        if(type != null && type.equalsIgnoreCase("suto")){
            firestore.collection("ShowAll").whereEqualTo("type","suto").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document: task.getResult().getDocuments()){
                            ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                            showAllModelList.add(showAllModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //Kategória suto
        if(type != null && type.equalsIgnoreCase("egyeb")){
            firestore.collection("ShowAll").whereEqualTo("type","egyeb").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot document: task.getResult().getDocuments()){
                            ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                            showAllModelList.add(showAllModel);
                            showAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        //folyt.... több kategória

    }
}