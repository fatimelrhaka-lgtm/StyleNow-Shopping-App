package com.example.stylenow;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.textViewName.setText(item.name);
        holder.textViewPrice.setText("$" + item.price);

        String imageUrl = "http://10.0.2.2:8080/style_data/" + item.image;
        Glide.with(context).load(imageUrl).into(holder.imageViewItem);

        // Show size selection dialog
        holder.itemView.setOnClickListener(v -> showSizeDialog(item));
    }

    private void showSizeDialog(Item item) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_select_size, null);
        Spinner sizeSpinner = dialogView.findViewById(R.id.spinnerSize);

        // Use sizes from the item object
        List<String> sizesList = item.sizes;
        String[] sizes = sizesList.toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, sizes);
        sizeSpinner.setAdapter(adapter);

        new AlertDialog.Builder(context)
                .setTitle("Select Size")
                .setView(dialogView)
                .setPositiveButton("Add to Cart", (dialog, which) -> {
                    String selectedSize = sizeSpinner.getSelectedItem().toString();
                    CartManager.getInstance().addToCart(item, selectedSize);
                    Toast.makeText(context, item.name + " (Size: " + selectedSize + ") added to cart", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItem;
        TextView textViewName, textViewPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewName = itemView.findViewById(R.id.textViewItemName);
            textViewPrice = itemView.findViewById(R.id.textViewItemPrice);
        }
    }
}
