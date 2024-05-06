package com.example.viper_electric_appliances_webshop.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viper_electric_appliances_webshop.R;
import com.example.viper_electric_appliances_webshop.activities.DetailActivity;
import com.example.viper_electric_appliances_webshop.models.ShowAllModel;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {

    private Context context;
    private List<ShowAllModel> showAllModelList;

    public ShowAllAdapter(Context context, List<ShowAllModel> showAllModelList) {
        this.context = context;
        this.showAllModelList = showAllModelList;
    }

    @NonNull
    @Override
    public ShowAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(showAllModelList.get(position).getImg_url()).into(holder.allItemImage);
        holder.allItemName.setText(showAllModelList.get(position).getName());
        holder.allItemPrice.setText(String.valueOf(showAllModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", showAllModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showAllModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView allItemImage;
        private TextView allItemPrice, allItemName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            allItemImage = itemView.findViewById(R.id.all_img);
            allItemName = itemView.findViewById(R.id.all_product_name);
            allItemPrice = itemView.findViewById(R.id.all_price);
        }
    }
}
