package com.example.viper_electric_appliances_webshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.models.OrderModel;

import java.util.List;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<OrderModel> orderModelList;

    public OrderAdapter(Context context, List<OrderModel> orderModelList) {
        this.context = context;
        this.orderModelList = orderModelList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.price.setText(orderModelList.get(position).getProductPrice()+"Ft");
        holder.name.setText(orderModelList.get(position).getProductName());
        holder.sumPrice.setText(String.valueOf(orderModelList.get(position).getSumPrice()));
        holder.sumQuantity.setText(orderModelList.get(position).getSumQuantity());
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,price,sumQuantity, sumPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_product_name);
            price = itemView.findViewById(R.id.order_product_price);
            sumQuantity = itemView.findViewById(R.id.order_sum_quantity);
            sumPrice = itemView.findViewById(R.id.order_sum_price);
        }
    }

}
