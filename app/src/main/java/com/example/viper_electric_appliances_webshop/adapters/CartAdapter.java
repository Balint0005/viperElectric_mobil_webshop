package com.example.viper_electric_appliances_webshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.models.CartModel;
import com.google.firestore.v1.TargetOrBuilder;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<CartModel> cartModelList;
    int totalPrice;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.price.setText(cartModelList.get(position).getProductPrice()+"Ft");
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.sumPrice.setText(String.valueOf(cartModelList.get(position).getSumPrice()));
        holder.sumQuantity.setText(cartModelList.get(position).getSumQuantity());

        totalPrice = totalPrice + cartModelList.get(position).getSumPrice();
        Intent intent = new Intent("TotalPrice");
        intent.putExtra("totalprice",totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }



    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,price,sumQuantity, sumPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            sumQuantity = itemView.findViewById(R.id.sum_quantity);
            sumPrice = itemView.findViewById(R.id.sum_price);
        }
    }
}
