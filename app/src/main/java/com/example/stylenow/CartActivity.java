package com.example.stylenow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartChangedListener {

    private RecyclerView recyclerView;
    private TextView textViewTotal;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        textViewTotal = findViewById(R.id.textViewTotal);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        adapter = new CartAdapter(this, cartItems);
        recyclerView.setAdapter(adapter);

        updateTotalPrice(); // show initial total

        // Set click listener for the Order Summary button
        Button buttonGoToOrder = findViewById(R.id.buttonGoToOrder);
        buttonGoToOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, OrderSummaryActivity.class);
            startActivity(intent);
        });
    }

    // Called whenever cart quantity changes
    @Override
    public void onCartUpdated() {
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = CartManager.getInstance().getTotalPrice();
        textViewTotal.setText("Total: $" + String.format("%.2f", total));
    }
}
