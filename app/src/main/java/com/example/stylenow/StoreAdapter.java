package com.example.stylenow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private Context context;
    private List<Store> storeList;

    public StoreAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.textViewName.setText(store.name);

        // Load logo image from Tomcat server using Glide
        String imageUrl = "http://10.0.2.2:8080/style_data/" + store.logo;
        Glide.with(context).load(imageUrl).into(holder.imageViewLogo);

        holder.itemView.setOnClickListener(v -> {
            // Open ItemActivity and pass storeId
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("storeId", store.id);
            intent.putExtra("storeName", store.name);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLogo;
        TextView textViewName;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLogo = itemView.findViewById(R.id.imageViewLogo);
            textViewName = itemView.findViewById(R.id.textViewStoreName);
        }
    }
}
