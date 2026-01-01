package com.example.stylenow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextView textViewOrderTotal;
    private EditText editTextAddress;
    private Button buttonPayNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        textViewOrderTotal = findViewById(R.id.textViewOrderTotal);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonPayNow = findViewById(R.id.buttonPayNow);

        // Show the total price
        double total = CartManager.getInstance().getTotalPrice();
        textViewOrderTotal.setText("Order Total: $" + String.format("%.2f", total));

        // Handle Pay Now button
        buttonPayNow.setOnClickListener(v -> {
            String address = editTextAddress.getText().toString().trim();

            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter your delivery address", Toast.LENGTH_SHORT).show();
                editTextAddress.requestFocus();
                return;
            }

            // Proceed to payment with address
            Intent intent = new Intent(OrderSummaryActivity.this, PaymentActivity.class);
            intent.putExtra("deliveryAddress", address);
            intent.putExtra("totalPrice", total);
            startActivity(intent);
        });
    }
}
