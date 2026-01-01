package com.example.stylenow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private OnCartChangedListener cartChangedListener;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;

        if (context instanceof OnCartChangedListener) {
            this.cartChangedListener = (OnCartChangedListener) context;
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        // Show name and size
        holder.textName.setText(cartItem.item.name + " (Size: " + cartItem.selectedSize + ")");
        holder.textQuantity.setText(String.valueOf(cartItem.quantity));

        holder.buttonPlus.setOnClickListener(v -> {
            cartItem.quantity++;
            notifyItemChanged(position);
            notifyCartChanged();
        });

        holder.buttonMinus.setOnClickListener(v -> {
            if (cartItem.quantity > 1) {
                cartItem.quantity--;
                notifyItemChanged(position);
                notifyCartChanged();
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textQuantity;
        ImageView buttonPlus, buttonMinus, buttonDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewCartItemName);
            textQuantity = itemView.findViewById(R.id.textViewCartQuantity);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    private void notifyCartChanged() {
        if (cartChangedListener != null) {
            cartChangedListener.onCartUpdated();
        }
    }

    public interface OnCartChangedListener {
        void onCartUpdated();
    }
}
