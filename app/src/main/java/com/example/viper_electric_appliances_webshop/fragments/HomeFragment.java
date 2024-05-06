package com.example.viper_electric_appliances_webshop.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.activities.ShowAllActivity;
import com.example.viper_electric_appliances_webshop.adapters.CategoryAdapter;
import com.example.viper_electric_appliances_webshop.adapters.NewProductsAdapter;
import com.example.viper_electric_appliances_webshop.adapters.PopularProductsAdapter;
import com.example.viper_electric_appliances_webshop.models.CategoryModel;
import com.example.viper_electric_appliances_webshop.models.NewProductsModel;
import com.example.viper_electric_appliances_webshop.models.PopularProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.FirestoreChannel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView catShowAll, newShowAll, allProductShowAll;
    RecyclerView catRecyclerview, newProductRecyclerview, popularProductRecyclerview;

    //Kategória recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //Új Termékek recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Termékek

    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductModel> popularProductModelList;

    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularProductRecyclerview = root.findViewById(R.id.popular_rec);

        catShowAll = root.findViewById(R.id.category_see_more);
        newShowAll = root.findViewById(R.id.newProducts_see_more);
        allProductShowAll = root.findViewById(R.id.all_see_more);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        allProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        //Kategóriák
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CategoryModel categoryModel = document.toObject(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                        categoryAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Hiba: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Új Termékek
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NewProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                        newProductsModelList.add(newProductsModel);
                        newProductsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Hiba: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Termékek
        popularProductRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductModelList);
        popularProductRecyclerview.setAdapter(popularProductsAdapter);

        db.collection("ShowAll").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                        popularProductModelList.add(popularProductModel);
                        popularProductsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Hiba: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

}